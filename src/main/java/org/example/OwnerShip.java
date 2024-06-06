package org.example;

import javafx.util.Pair;
import org.example.models.Member;
import org.example.models.Team;
import org.example.models.User;
import org.example.parsers.CodeOwners.BitBucketParser;
import org.example.parsers.CodeOwners.CodeOwnersParser;
import org.example.parsers.CodeOwners.GitHubParser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;
import java.util.regex.Pattern;

public class OwnerShip {

    private final String repositoryRoot;
    private CodeOwnersDialect dialect = CodeOwnersDialect.GITHUB;

    public OwnerShip(String root, CodeOwnersDialect dialect) {
        repositoryRoot = root;
        this.dialect = dialect;
    }

    public OwnerShip(String root) {
        repositoryRoot = root;
    }

    public List<String> checkForFilesWithoutOwners(boolean teamsOnly) {
        List <String> filesWithoutOwners = new ArrayList<>(Collections.emptyList());
        try {
            Files.walkFileTree(Path.of(repositoryRoot), new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                    Member member = defineOwner(String.valueOf(file));
                    if (member == null || (teamsOnly && (member.getClass() == User.class))) {
                        filesWithoutOwners.add(String.valueOf(file));
                    }
                    return FileVisitResult.CONTINUE;
                }
            }
        );
        } catch (IOException ignored) {

        }
        return filesWithoutOwners;
    }

    public void requestReview(List<String> changedFiles) {
        for (String changedFile : changedFiles) {
            defineOwner(changedFile).requestReview();
        }
    }

    public void requestReview(String changedFile) {
        requestReview(Collections.singletonList(changedFile));
    }

    private Member defineOwner(String pathToChangedFile) {
        if (!pathToChangedFile.startsWith(repositoryRoot)) {
            throw new IllegalArgumentException("File is not a part of repository");
        }
        CodeOwnersParser parser;
        List<String> relativePath = new ArrayList<>(
                Arrays.stream(pathToChangedFile
                                .replace(repositoryRoot, "")
                                .split(Pattern.quote(File.separator)))
                        .toList());
        String currentPath = repositoryRoot;
        // Ensure the root of repository is checked no matter the format
        if (!Objects.equals(relativePath.get(0), "")) {
            relativePath.add(0, "");
        }
        Pair<String, Member> result= new Pair<>(null, null);
        // Look for CODEOWNERS files from root to the directory of a changed file
        for (String s : relativePath) {
            currentPath = currentPath + s;
            System.out.println(currentPath);
            String currentPathToCodeOwners = currentPath + File.separator + "CODEOWNERS";
            if (Files.exists(Path.of(currentPathToCodeOwners))) {
                switch (dialect) {
                    case GITHUB:
                        parser = new GitHubParser(currentPathToCodeOwners);
                        break;
                    case BITBUCKET:
                        parser = new BitBucketParser(currentPathToCodeOwners);
                        break;
                    default:
                        parser = new GitHubParser(currentPathToCodeOwners);
                        break;
                }
                try {
                    result = parser.parseCodeOwners(pathToChangedFile);
                } catch (FileNotFoundException ignored) {

                }
            }
            currentPath = currentPath + File.separator;
        }
        return result.getValue();
    }
}

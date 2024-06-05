package org.example;

import javafx.util.Pair;
import org.example.models.Member;
import org.example.parsers.CodeOwners.CodeOwnersParser;
import org.example.parsers.CodeOwners.GitHubParser;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.regex.Pattern;

public class OwnerShip {

    private String repositoryRoot;
    private CodeOwnersDialect dialect = CodeOwnersDialect.GITHUB;

    public OwnerShip(String root, CodeOwnersDialect dialect) {
        repositoryRoot = root;
        this.dialect = dialect;
    }

    public OwnerShip(String root) {
        repositoryRoot = root;
    }

    public List<String> checkForOwners(boolean teamsOnly) {
        //TODO
        return Collections.emptyList();
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

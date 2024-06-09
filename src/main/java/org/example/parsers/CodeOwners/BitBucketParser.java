package org.example.parsers.CodeOwners;

import javafx.util.Pair;
import org.example.models.Member;
import org.example.models.Team;
import org.example.models.User;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class BitBucketParser implements CodeOwnersParser {

    String path;

    public BitBucketParser(String path) {
        this.path = path;
    }

    @Override
    public Pair<String, Member> parseCodeOwners(String pathToChangedFile) {
        File file = new File(path);
        String addressMask = null;
        Member reviewer = null;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while (true) {
                line = reader.readLine();
                // EoF
                if (line == null) {
                    break;
                }
                // Delete and ignore comments
                line = line.replaceAll("#.*$","").strip();
                System.out.println(line);
                if (line.equals("")) {
                    continue;
                }
                String[] lineSeparated = line.split("\\s+", 2);
                // /address
                if (lineSeparated[0].matches("^(/[a-zA-Z0-9_\\.\\-\\+=]+)+(/(\\*\\.[a-zA-Z0-9]+)?)?$")) {
                    String replacement = "[a-zA-Z0-9_\\\\\\\\.\\\\\\\\-\\\\\\\\+=]+";
                    String mask = lineSeparated[0].replaceAll("\\*", replacement);
                    if (!pathToChangedFile.matches(mask)) {
                        continue;
                    }
                    addressMask = lineSeparated[0];
                }
                // **/address/
                else if (lineSeparated[0].matches("^\\*\\*(/[a-zA-Z0-9_\\.\\-\\+=]+)+(/(\\*\\.[a-zA-Z0-9]+)?)?$")) {
                    String replacementAddress = "([a-zA-Z0-9_\\\\\\\\.\\\\\\\\-\\\\\\\\+=]+)?(\\\\\\\\[a-zA-Z0-9_\\\\\\\\.\\\\\\\\-\\\\\\\\+=]+)+";
                    String replacementExtension = "[a-zA-Z0-9_\\\\\\\\.\\\\\\\\-\\\\\\\\+=]+";
                    String mask = lineSeparated[0]
                            .replaceAll("\\*\\*", replacementAddress)
                            .replaceAll("\\*", replacementExtension);
                    if (!pathToChangedFile.matches(mask)) {
                        continue;
                    }
                    addressMask = lineSeparated[0];
                }
                // address/
                else if (lineSeparated[0].matches("^([a-zA-Z0-9_\\.\\-\\+=]+)+(/(\\*\\.[a-zA-Z0-9]+)?)?$")) {
                    String replacement = "[a-zA-Z0-9_\\\\\\\\.\\\\\\\\-\\\\\\\\+=]+";
                    String mask = lineSeparated[0].replaceAll("\\*", replacement);
                    if (!pathToChangedFile.matches(mask)) {
                        continue;
                    }
                    addressMask = lineSeparated[0];
                }
                // *
                if (lineSeparated[0].matches("^\\*(\\.[a-zA-Z0-9]+)?$")) {
                    String replacement = "([a-zA-Z0-9_\\\\\\\\.\\\\\\\\-\\\\\\\\+=]+)?(\\\\\\\\[a-zA-Z0-9_\\\\\\\\.\\\\\\\\-\\\\\\\\+=]+)+";
                    String mask = lineSeparated[0].replaceAll("\\*", replacement);
                    if (!pathToChangedFile.matches(mask)) {
                        continue;
                    }
                    addressMask = lineSeparated[0];
                }
                // **/address
                else if (lineSeparated[0].matches("^\\*\\*(/[a-zA-Z0-9_\\.\\-\\+=]+)+(\\.[a-zA-Z0-9]+)?$")) {
                    String replacement = "([a-zA-Z0-9_\\\\\\\\.\\\\\\\\-\\\\\\\\+=]+)?(\\\\\\\\[a-zA-Z0-9_\\\\\\\\.\\\\\\\\-\\\\\\\\+=]+)+";
                    String mask = lineSeparated[0].replaceAll("\\*\\*", replacement);
                    if (!pathToChangedFile.matches(mask)) {
                        continue;
                    }
                    addressMask = lineSeparated[0];
                }
                // address/*
                else if (lineSeparated[0].matches("^([a-zA-Z0-9_\\.\\-\\+=]+)+/\\*(\\.[a-zA-Z0-9]+)?$")) {
                    String replacement = "[a-zA-Z0-9_\\\\\\\\.\\\\\\\\-\\\\\\\\+=]+";
                    String mask = lineSeparated[0].replaceAll("\\*", replacement);
                    if (!pathToChangedFile.matches(mask)) {
                        continue;
                    }
                    addressMask = lineSeparated[0];
                }
                // WTF - What a Terrible Failure
                else {
                    System.out.println("WARNING: incorrect filepath");
                    continue;
                }
                if (lineSeparated.length < 2) {
                    System.out.println("WARNING: no owner specified");
                }
                // Check owners
                String[] members = lineSeparated[1].split("\\s+");
                // Multiple users described
                if (members.length > 1) {
                    Team team = new Team("");
                    for (String member : members) {
                        // Individual member is either username or email
                        if (member.matches("^@([a-zA-Z0-9_\\-\\+=]+)+$") ||
                                member.matches("^[^@]+@[^@]+\\.[^@]+$")) {
                            team.addMemeber(new User(member));
                        }
                    }
                    reviewer = team;
                }
                // User
                else if (lineSeparated[1].matches("^@([a-zA-Z0-9_\\-\\+=]+)+$")) {
                    reviewer = new User(lineSeparated[1]);
                }
                // Email
                else if (lineSeparated[1].matches("^[^@]+@[^@]+\\.[^@]+$")) {
                    reviewer = new User(lineSeparated[1]);
                }
                // WTF - What a Terrible Failure
                else {
                    System.out.println("WARNING: incorrect owner format");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new Pair<>(addressMask, reviewer);
    }
}
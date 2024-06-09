package org.example.parsers.Git;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class MRChange {

    public List<FileChange> MRChanges = new ArrayList<>();

    public MRChange(Path gitDiff) {
        // There goes "git diff" parser, splitting "gitDiff" into separate file info
        try {
            Scanner scanner = new Scanner(gitDiff);
            int i = -1;
            List<String> lines = new ArrayList<>();
            while (scanner.hasNext()) {
                String newLine = scanner.nextLine();
                if (Pattern.matches("^diff --git .+", newLine)) {
                    if (i != -1) {
                        FileChange fileChange = new FileChange(lines);
                        MRChanges.add(fileChange);
                    }
                    i++;
                }
                lines.add(newLine);
            }
            if (!lines.isEmpty()) {
                FileChange fileChange = new FileChange(lines);
                MRChanges.add(fileChange);
            }
        } catch (IOException e) {
            throw new RuntimeException("No \"git diff\" file exists!");
        }

    }

    public void addChange(FileChange fileChange) {
        MRChanges.add(fileChange);
    }
}

package org.example;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class FileChange {

    public ChangeType changeType = ChangeType.CHANGE;
    public Path fileLocation;
    public List<CodeChange> codeChanges = new ArrayList<>();

    public FileChange(List<String> gitDiff) {
        CodeChange codeChange = new CodeChange();
        int j = -1;
        boolean upcomingChanges = false;
        // There goes "git diff" parser, splitting "gitDiff" into separate file changes
        for (int i = 0; i < gitDiff.size(); i++) {
            if (Pattern.matches("^diff --git.*", gitDiff.get(i))) {
                upcomingChanges = false;
            }

            // Deletion
            if (Pattern.matches("^\\+\\+\\+ /dev/null$", gitDiff.get(i))) {
                changeType = ChangeType.DELETE;
            }
            // Addition
            else if (Pattern.matches("^new file mode.+", gitDiff.get(i))) {
                changeType = ChangeType.ADD;
            }

            if (Pattern.matches("^@@ -\\d+,\\d+ \\+\\d+,\\d+ @@.*", gitDiff.get(i))) {
                if (j != -1) {
                    codeChanges.add(codeChange);
                    codeChange = new CodeChange();
                }
                upcomingChanges = true;
                j++;
            }

            if (Pattern.matches("^\\+.*", gitDiff.get(i)) && upcomingChanges) {
                codeChange.addition.add(gitDiff.get(i));
            }
            else if (Pattern.matches("^-.*", gitDiff.get(i)) && upcomingChanges) {
                codeChange.deletion.add(gitDiff.get(i));
            }
        }
        if (!codeChange.addition.isEmpty() || !codeChange.deletion.isEmpty()) {
            codeChanges.add(codeChange);
        }
    }

}

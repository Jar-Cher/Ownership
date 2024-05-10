package org.example.parsers;

import java.nio.file.Path;
import java.nio.file.Paths;
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
        for (String s : gitDiff) {
            if (Pattern.matches("^diff --git.*", s)) {
                upcomingChanges = false;
                String path = s.split(" ")[2];
                fileLocation = Paths.get((String) path.subSequence(2, path.length()));
            }

            // Deletion
            if (Pattern.matches("^\\+\\+\\+ /dev/null$", s)) {
                changeType = ChangeType.DELETE;
            }
            // Addition
            else if (Pattern.matches("^new file mode.+", s)) {
                changeType = ChangeType.ADD;
            }

            if (Pattern.matches("^@@ -\\d+,\\d+ \\+\\d+,\\d+ @@.*", s)) {
                if (j != -1) {
                    codeChanges.add(codeChange);
                    codeChange = new CodeChange();
                }
                upcomingChanges = true;
                j++;
            }

            if (Pattern.matches("^\\+.*", s) && upcomingChanges) {
                codeChange.addition.add(s);
            } else if (Pattern.matches("^-.*", s) && upcomingChanges) {
                codeChange.deletion.add(s);
            }
        }
        if (!codeChange.addition.isEmpty() || !codeChange.deletion.isEmpty()) {
            codeChanges.add(codeChange);
        }
    }

}

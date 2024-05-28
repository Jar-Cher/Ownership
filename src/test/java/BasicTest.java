import org.example.parsers.Git.ChangeType;
import org.example.parsers.Git.CommitChange;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import java.nio.file.Paths;

public class BasicTest {

    @Test
    public void ExampleTest() {
        CommitChange commitChange = new CommitChange(Paths.get("src/test/resources/ChangeExamples/5.txt"));
        Assertions.assertEquals(commitChange.commitChanges.size(), 2);
        Assertions.assertEquals(commitChange.commitChanges.get(0).codeChanges.size(), 0);
        Assertions.assertSame(commitChange.commitChanges.get(0).changeType, ChangeType.ADD);
        Assertions.assertEquals(commitChange.commitChanges.get(0).fileLocation.toString(), "input\\Test.txt");
        Assertions.assertEquals(commitChange.commitChanges.get(1).codeChanges.get(0).deletion.size(), 9);
        Assertions.assertSame(commitChange.commitChanges.get(1).changeType, ChangeType.DELETE);
        Assertions.assertEquals(commitChange.commitChanges.get(1).fileLocation.toString(), "input\\top20.txt");
    }
}

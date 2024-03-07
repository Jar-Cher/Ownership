import org.example.ChangeType;
import org.example.CommitChange;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import java.nio.file.Paths;

public class BasicTest {

    @Test
    public void ExampleTest() {
        CommitChange commitChange = new CommitChange(Paths.get("src/test/resources/5.txt"));
        Assertions.assertEquals(commitChange.commitChanges.size(), 2);
        Assertions.assertEquals(commitChange.commitChanges.get(0).codeChanges.size(), 0);
        Assertions.assertTrue(commitChange.commitChanges.get(0).changeType == ChangeType.ADD);
        Assertions.assertEquals(commitChange.commitChanges.get(1).codeChanges.get(0).deletion.size(), 9);
        Assertions.assertTrue(commitChange.commitChanges.get(1).changeType == ChangeType.DELETE);
    }
}

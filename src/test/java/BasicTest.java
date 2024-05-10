import org.example.parsers.ChangeType;
import org.example.parsers.MergeRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import java.nio.file.Paths;

public class BasicTest {

    @Test
    public void ExampleTest() {
        MergeRequest mergeRequest = new MergeRequest(Paths.get("src/test/resources/5.txt"));
        Assertions.assertEquals(mergeRequest.commitChanges.size(), 2);
        Assertions.assertEquals(mergeRequest.commitChanges.get(0).codeChanges.size(), 0);
        Assertions.assertSame(mergeRequest.commitChanges.get(0).changeType, ChangeType.ADD);
        Assertions.assertEquals(mergeRequest.commitChanges.get(0).fileLocation.toString(), "input\\Test.txt");
        Assertions.assertEquals(mergeRequest.commitChanges.get(1).codeChanges.get(0).deletion.size(), 9);
        Assertions.assertSame(mergeRequest.commitChanges.get(1).changeType, ChangeType.DELETE);
        Assertions.assertEquals(mergeRequest.commitChanges.get(1).fileLocation.toString(), "input\\top20.txt");
    }
}

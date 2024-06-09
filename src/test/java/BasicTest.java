import org.example.OwnerShip;
import org.example.parsers.Git.ChangeType;
import org.example.parsers.Git.MRChange;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class BasicTest {

    @Test
    public void GitParserTest() {
        MRChange MRChange = new MRChange(Paths.get("src/test/resources/ChangeExamples/diff1.txt"));
        Assertions.assertEquals(MRChange.MRChanges.size(), 2);
        Assertions.assertEquals(MRChange.MRChanges.get(0).codeChanges.size(), 0);
        Assertions.assertSame(MRChange.MRChanges.get(0).changeType, ChangeType.ADD);
        Assertions.assertEquals(MRChange.MRChanges.get(0).fileLocation.toString(), "input\\Test.txt");
        Assertions.assertEquals(MRChange.MRChanges.get(1).codeChanges.get(0).deletion.size(), 9);
        Assertions.assertSame(MRChange.MRChanges.get(1).changeType, ChangeType.DELETE);
        Assertions.assertEquals(MRChange.MRChanges.get(1).fileLocation.toString(), "input\\top20.txt");
    }

    @Test
    public void defineOwnerTest() {
        OwnerShip ownership = new OwnerShip("src\\test\\resources");
        String randomExample = "src\\test\\resources\\CodeOwnersExamples\\GitHubExample";
        Assertions.assertNull(ownership.defineOwner(randomExample, true));
        Assertions.assertEquals(ownership.defineOwner(randomExample, false).toString(),
                "[@global-owner1, @global-owner2]");

        String JSExample = "src\\test\\resources\\CodeOwnersExamples\\SomeFolder\\interface.js";
        Assertions.assertEquals(ownership.defineOwner(JSExample, false).toString(),
                "@js-owner");
    }

    @Test
    public void checkForFilesWithoutOwners() {
        OwnerShip ownership = new OwnerShip("src\\test\\resources");
        String JSExample = "src\\test\\resources\\CodeOwnersExamples\\SomeFolder\\interface.js";
        Assertions.assertEquals(ownership.defineOwner(JSExample, false).toString(),
                "@js-owner");
    }
}

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: Simon Kotlinski
 * Date: 2013-10-12
 * Time: 11:05
 * To change this template use File | Settings | File Templates.
 */
public class FileSystemProviderTest {

    @Before
    public void setUp() throws Exception {
        System.out.println("--- Setup ---");
    }

    @After
    public void tearDown() throws Exception {

    }
    @Test
    public void readSettingsFromFileTest() throws Exception {
		String testPath = System.getProperty("user.dir")+"\\CreateFolderText";
		FileUtils.deleteDirectory(new File(testPath));
		boolean pathCreated = FileSystemProvider.createFolder(testPath);
        Assert.assertTrue(pathCreated);
		pathCreated = FileSystemProvider.createFolder(testPath);
		Assert.assertFalse(pathCreated);
		FileUtils.deleteDirectory(new File(testPath));

	}
}

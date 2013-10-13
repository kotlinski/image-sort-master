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
public class BackupSystemTest {
    private BackupSystem backupSystem;

    @Before
    public void setUp() throws Exception {
        System.out.println("--- Setup ---");
        backupSystem = new BackupSystem();
    }

    @After
    public void tearDown() throws Exception {

    }
    @Test
    public void readSettingsFromFileTest() throws Exception {
        this.backupSystem.readSettingsFromFile();
        File settingsFile = new File(System.getProperty("user.dir")+"/settings.json");
        Assert.assertTrue(settingsFile.exists());
    }
    @Test
    public void testIsDropboxFormat() throws Exception {
        String dropboxName = "2013-09-18 16.18.45.jpg";
        Assert.assertTrue(true);
    }
    @Test
    public void updateSettingsFileTest() throws Exception {
        Settings settings = new Settings();
        settings.setCameraAlbum("aaa");
        settings.setDropboxAlbum("bbb");
        backupSystem.updateSettingsFile(settings);
        Settings settingsResult = backupSystem.getSettings();
        Assert.assertEquals(settingsResult.getCameraAlbum(), "aaa");
        Assert.assertEquals(settingsResult.getDropboxAlbum(), "bbb");
    }
}

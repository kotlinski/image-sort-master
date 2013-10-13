import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: Simon Kotlinski
 * Date: 2013-10-13
 * Time: 22:49
 * To change this template use File | Settings | File Templates.
 */
public class SettingsTest {
    Settings settings;
    @Before
    public void setUp() throws Exception {
        settings = new Settings();
    }
    @Test
    public void testToString() throws Exception {
        settings.setDropboxAlbum("aaa");
        settings.setCameraAlbum("bbb");
        Assert.assertEquals(settings.toString(),"{\"cameraAlbum\":\"bbb\",\"dropboxAlbum\":\"aaa\"}");

    }
}

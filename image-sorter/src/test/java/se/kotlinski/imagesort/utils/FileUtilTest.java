package se.kotlinski.imagesort.utils;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

public class FileUtilTest {

  private MediaFileUtil mediaFileUtil;
  private MediaFileTestUtil mediaFileTestUtil;

  @Before
  public void setUp() throws Exception {
  }

  @Test
  public void testIsValidFolder() throws Exception {
    mediaFileUtil = new MediaFileUtil();

    Assert.assertFalse(mediaFileUtil.isValidFolder(new File("invalid path")));
  }

  @Test
  public void testPathBuild() throws Exception {
    MediaFileUtil fileUtilMock = mock(MediaFileUtil.class);
    when(fileUtilMock.getSystemPath()).thenReturn("system.path" + File.separator);

    mediaFileTestUtil = new MediaFileTestUtil(fileUtilMock);

    Assert.assertEquals("system.path" +
                        File.separator +
                        "image-sorter" + File.separator + "src" + File.separator + "test" +
                        File.separator + "resources" + File.separator + "inputImages",
                        mediaFileTestUtil.getTestInputPath());


  }


  public void testPathBuildWithImageClients() throws Exception {
    MediaFileUtil fileUtilMock = mock(MediaFileUtil.class);

    when(fileUtilMock.getSystemPath()).thenReturn("system.path" +
                                                 File.separator +
                                                 "image-clients" +
                                                 File.separator);

    mediaFileUtil = new MediaFileUtil();
    mediaFileTestUtil = new MediaFileTestUtil(mediaFileUtil);

    Assert.assertEquals("system.path" +
                        File.separator +
                        "image-sorter" + File.separator + "src" + File.separator + "test" +
                        File.separator + "resources" + File.separator + "output",
                        mediaFileTestUtil.getTestOutputPath());
  }
}
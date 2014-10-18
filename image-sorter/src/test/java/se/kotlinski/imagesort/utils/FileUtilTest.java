package se.kotlinski.imagesort.utils;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

public class FileUtilTest {

  @Before
  public void setUp() throws Exception {
  }

  @Test
  public void testIsValidFolder() throws Exception {
    Assert.assertFalse(ImageFileUtil.isValidFolder(new File("invalid path")));
  }

  @Test
  public void testPathBuild() throws Exception {
    ImageFileUtil fileUtilSpy = spy(new ImageFileUtil());
    when(fileUtilSpy.getSystemPath()).thenReturn("system.path" + File.separator);
    Assert.assertEquals("system.path" +
                        File.separator +
                        "image-sorter" + File.separator + "src" + File.separator + "test" +
                        File.separator + "resources" + File.separator + "inputImages",
                        fileUtilSpy.getTestInputPath());

    when(fileUtilSpy.getSystemPath()).thenReturn("system.path" +
                                                 File.separator +
                                                 "image-clients" +
                                                 File.separator);
    Assert.assertEquals("system.path" +
                        File.separator +
                        "image-sorter" + File.separator + "src" + File.separator + "test" +
                        File.separator + "resources" + File.separator + "output",
                        fileUtilSpy.getTestOutputPath());
  }
}
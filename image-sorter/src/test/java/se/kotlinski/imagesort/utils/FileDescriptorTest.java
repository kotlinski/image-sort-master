package se.kotlinski.imagesort.utils;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;


import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;

public class FileDescriptorTest {

  FileDescriptor fileDescriptor;

  @Before
  public void setUp() throws Exception {
    fileDescriptor = new FileDescriptor();
  }

  @Test
  public void testGetFlavour() throws Exception {
    File baseFolder = mock(File.class);
    File resourceFile = mock(File.class);

    String basePathString = "C:" + File.separator + File.separator + "images" + File.separator;
    when(baseFolder.getAbsolutePath()).thenReturn(basePathString);

    String fileFlavour = "jul";
    String resourceFileString = "C:" + File.separator + File.separator + "images" + File.separator +
                                fileFlavour + File.separator + "image.jpg";
    when(baseFolder.getAbsolutePath()).thenReturn(basePathString);
    when(resourceFile.getAbsolutePath()).thenReturn(resourceFileString);

    when(resourceFile.getName()).thenReturn(fileFlavour);
    String flavour = fileDescriptor.getFlavour(baseFolder.getAbsolutePath(), resourceFile);

    assertThat("jul", is("jul"));
  }

  @Test
  public void testRemoveMonth() {
    String input = File.separator + "22" + File.separator;
    String s = fileDescriptor.removeDigitFolders(input, 2);
    Assert.assertEquals(File.separator, s);
  }

}
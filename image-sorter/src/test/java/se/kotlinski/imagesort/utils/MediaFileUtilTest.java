package se.kotlinski.imagesort.utils;

import org.junit.Before;
import org.junit.Test;

import java.io.File;

public class MediaFileUtilTest {

  private MediaFileTestUtil mediaFileTestUtil;

  @Before
  public void setUp() throws Exception {

    mediaFileTestUtil = new MediaFileTestUtil(new MediaFileUtil());
  }

  @Test
  public void testRecursiveIterate() throws Exception {
    File folder = new File(mediaFileTestUtil.getTestInputPath());
    // List<File> files = mediaFileTestUtil.readAllFilesInFolder(folder);
//    Assert.assertEquals("Image found in root folder", 12, files.size());
  }
}
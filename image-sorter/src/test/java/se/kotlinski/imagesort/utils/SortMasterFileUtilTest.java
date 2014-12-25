package se.kotlinski.imagesort.utils;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.List;

public class SortMasterFileUtilTest {

  private SortMasterFileUtil sortMasterFileUtil;

  @Before
  public void setUp() throws Exception {

    sortMasterFileUtil = new SortMasterFileUtil();
  }

  @Test
  public void testRecursiveIterate() throws Exception {
    File folder = new File(sortMasterFileUtil.getTestInputPath());
    List<File> files = sortMasterFileUtil.readAllFilesInFolder(folder);
    Assert.assertEquals("Image found in root folder", 12, files.size());
  }
}
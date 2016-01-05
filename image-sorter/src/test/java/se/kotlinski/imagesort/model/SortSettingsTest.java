package se.kotlinski.imagesort.model;

import org.junit.Before;
import org.junit.Test;
import se.kotlinski.imagesort.data.SortSettings;
import se.kotlinski.imagesort.utils.MediaFileTestUtil;
import se.kotlinski.imagesort.utils.MediaFileUtil;

import java.io.File;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SortSettingsTest {

  private SortSettings sortSettings;
  private MediaFileTestUtil mediaFileTestUtil;

  @Before
  public void setUp() throws Exception {
    mediaFileTestUtil = new MediaFileTestUtil(new MediaFileUtil());
    setSortSettings(new SortSettings());
  }

  @Test
  public void testToString() throws Exception {
    String folderIOString = sortSettings.toString();
    assertEquals("To String with null values", "Master Folder not set", folderIOString);
    sortSettings.masterFolder = mediaFileTestUtil.getTestInputFile();

    String filePart = File.separator +
                      "image-sorter" +
                      File.separator +
                      "src" +
                      File.separator +
                      "test" +
                      File.separator +
                      "resources" +
                      File.separator +
                      "inputImages";
    boolean contains = sortSettings.toString().contains(filePart);
    assertTrue("Check toString", contains);
  }


  void setSortSettings(final SortSettings sortSettings) {
    this.sortSettings = sortSettings;
  }
}
package se.kotlinski.imagesort.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import se.kotlinski.imagesort.exception.CouldNotParseDateException;
import se.kotlinski.imagesort.utils.ImageFileUtil;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class FileDescriberTest {

  private FileDescriber imageDescriber;
  private FileDescriber imageDescriber2;

  @Before
  public void setup() {
    Calendar calendar = new GregorianCalendar();
    ImageFileUtil imageFileUtil = new ImageFileUtil();

    File file = new File(imageFileUtil.getTestInputPath() +
                         "//structure//2013-10-03 13.43.20-kaffe.jpg");
    File file2 = new File(imageFileUtil.getTestInputPath() +
                          "//structure//2013-10-26 20.20.46-kottbullar.jpg");
    imageDescriber = new FileDescriber(file,
                                       new Date(0),
                                       "a",
                                       imageFileUtil.getTestInputPath(), calendar);
    imageDescriber2 = new FileDescriber(file2,
                                        new Date(0),
                                        "b",
                                        imageFileUtil.getTestInputPath(), calendar);
  }

  @Test
  public void testGetOriginalFileName() {
    String originalFileName = imageDescriber.getOriginalFileName();
    Assert.assertEquals(originalFileName, "2013-10-03 13.43.20-kaffe.jpg");
    originalFileName = imageDescriber2.getOriginalFileName();
    Assert.assertEquals(originalFileName, "2013-10-26 20.20.46-kottbullar.jpg");
  }

  @Test
  public void testGetFlavour() {
    String input = File.separator + "22" + File.separator;
    String s = imageDescriber.removeDigitFolders(input, 2);
    Assert.assertEquals(File.separator, s);
  }

  @Test
  public void testGetDateFilename() throws CouldNotParseDateException {
    String dateFilename = imageDescriber.getDateFilename(false);
    Assert.assertTrue(dateFilename.contains(".00.00.jpg"));
    dateFilename = imageDescriber.getDateFilename(true);
    Assert.assertTrue(dateFilename.contains(".00.00-a.jpg"));
  }
}
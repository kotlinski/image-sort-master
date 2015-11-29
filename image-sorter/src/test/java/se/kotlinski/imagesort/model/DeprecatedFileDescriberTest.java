package se.kotlinski.imagesort.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import se.kotlinski.imagesort.utils.MediaFileTestUtil;
import se.kotlinski.imagesort.utils.MediaFileUtil;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DeprecatedFileDescriberTest {

  private DeprecatedFileDescriber imageDescriber;
  private DeprecatedFileDescriber imageDescriber2;

  @Before
  public void setup() {
    Calendar calendar = new GregorianCalendar();
    MediaFileTestUtil mediaFileTestUtil = new MediaFileTestUtil(new MediaFileUtil());

    File file = new File(mediaFileTestUtil.getTestInputPath() +
                         "//structure//2013-10-03 13.43.20-kaffe.jpg");
    File file2 = new File(mediaFileTestUtil.getTestInputPath() +
                          "//structure//2013-10-26 20.20.46-kottbullar.jpg");
    imageDescriber = new DeprecatedFileDescriber(file,
                                       new Date(0),
                                       "a",
                                       mediaFileTestUtil.getTestInputPath(), calendar);
    imageDescriber2 = new DeprecatedFileDescriber(file2,
                                        new Date(0),
                                        "b",
                                        mediaFileTestUtil.getTestInputPath(), calendar);
  }

  @Test
  public void testGetOriginalFileName() {
    String originalFileName = imageDescriber.getOriginalFileName();
    Assert.assertEquals(originalFileName, "2013-10-03 13.43.20-kaffe.jpg");
    originalFileName = imageDescriber2.getOriginalFileName();
    Assert.assertEquals(originalFileName, "2013-10-26 20.20.46-kottbullar.jpg");
  }

}
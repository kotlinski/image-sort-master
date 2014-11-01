package se.kotlinski.imagesort.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import se.kotlinski.imagesort.exception.CouldNotGenerateIDException;
import se.kotlinski.imagesort.utils.ImageFileUtil;

import java.io.File;
import java.util.Date;

public class FileDescriberTest {

  private FileDescriber imageDescriber;
  private FileDescriber imageDescriber2;
  private ImageFileUtil imageFileUtil;

  @Before
  public void setup() {
    imageFileUtil = new ImageFileUtil();

    File file = new File(imageFileUtil.getTestInputPath() +
                         "//structure//2013-10-03 13.43.20-kaffe.jpg");
    File file2 = new File(imageFileUtil.getTestInputPath() +
                          "//structure//2013-10-26 20.20.46-kottbullar.jpg");
    imageDescriber = new FileDescriber(file, new Date(0), "a");
    imageDescriber2 = new FileDescriber(file2, new Date(0), "b");
  }

  @Test
  public void testGetOriginalFileName() {
    String originalFileName = imageDescriber.getOriginalFileName();
    Assert.assertEquals(originalFileName, "2013-10-03 13.43.20-kaffe.jpg");
    originalFileName = imageDescriber2.getOriginalFileName();
    Assert.assertEquals(originalFileName, "2013-10-26 20.20.46-kottbullar.jpg");
  }
}
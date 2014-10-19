package se.kotlinski.imagesort.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import se.kotlinski.imagesort.exception.CouldNotGenerateIDException;
import se.kotlinski.imagesort.utils.ImageFileUtil;

import java.io.File;

public class ImageDescriberTest {

  private ImageDescriber imageDescriber;
  private ImageDescriber imageDescriber2;
  private ImageFileUtil imageFileUtil;

  @Before
  public void setup() {
    imageFileUtil = new ImageFileUtil();

    File file = new File(imageFileUtil.getTestInputPath() +
                         "//structure//2013-10-03 13.43.20-kaffe.jpg");
    File file2 = new File(imageFileUtil.getTestInputPath() +
                          "//structure//2013-10-26 20.20.46-kottbullar.jpg");
    try {
      imageDescriber = new ImageDescriber(file);
      imageDescriber2 = new ImageDescriber(file2);
    }
    catch (CouldNotGenerateIDException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testGetMd5() throws Exception {
    Assert.assertEquals("Test MD5", "10a16e061aba9bdf721cce382756b1bc", imageDescriber.getMd5());
    Assert.assertEquals("Test MD5 testfile 2",
                        "e53209c079f84e775ee561ab65ae0589",
                        imageDescriber2.getMd5());

    try {
      new ImageDescriber(new File("fake file"));
      assert false;
    }
    catch (CouldNotGenerateIDException e) {
      assert true;
    }
  }

  @Test
  public void testToString() {
    try {
      File inputPath = new File(imageFileUtil.getTestInputPath() + File.separator + "1.jpg");
      ImageDescriber imageDescriber = new ImageDescriber(inputPath);
      Assert.assertTrue(imageDescriber.toString().contains("1.jpg"));
    }
    catch (CouldNotGenerateIDException e) {
      e.printStackTrace();
      assert false;
    }
  }


  @Test
  public void compareTo() {
    File inputPath = new File(imageFileUtil.getTestInputPath() + File.separator + "1.jpg");
    File inputPath2 = new File(imageFileUtil.getTestInputPath() +
                               File.separator +
                               "image-without-dates.jpg");
    try {
      ImageDescriber imageDescriber = new ImageDescriber(inputPath);
      ImageDescriber imageDescriber2 = new ImageDescriber(inputPath);
      Assert.assertEquals(0, imageDescriber.compareTo(imageDescriber2));

      imageDescriber2 = new ImageDescriber(inputPath2);
      Assert.assertEquals(-1, imageDescriber.compareTo(imageDescriber2));
    }
    catch (CouldNotGenerateIDException e) {
      e.printStackTrace();
    }

  }
}
package se.kotlinski.imagesort.mapper;

import org.junit.Before;
import org.junit.Test;
import se.kotlinski.imagesort.model.FileDescriber;
import se.kotlinski.imagesort.utils.ImageFileUtil;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static org.junit.Assert.*;

public class FileDescriberPathComparatorTest {

  private ImageFileUtil imageFileUtil;
  private FileDescriber imageDescriber;
  private FileDescriber imageDescriber2;
  private Calendar calendar;

  @Before
  public void setUp() throws Exception {

    calendar = new GregorianCalendar();
    imageFileUtil = new ImageFileUtil();

    File file = new File(imageFileUtil.getTestInputPath() +
                         "//structure//2013-10-03 13.43.20-kaffe.jpg");
    File file2 = new File(imageFileUtil.getTestInputPath() +
                          "//structure//2013-10-26 20.20.46-kottbullar.jpg");
    imageDescriber = new FileDescriber(file,
                                       new Date(0),
                                       "a",
                                       imageFileUtil.getTestInputPath(),
                                       calendar);
    imageDescriber2 = new FileDescriber(file2,
                                        new Date(0),
                                        "b",
                                        imageFileUtil.getTestInputPath(),
                                        calendar);
  }

  @Test
  public void testCompare() throws Exception {

    imageFileUtil = new ImageFileUtil();

    File file = new File(imageFileUtil.getTestInputPath() +
                         "//structure//2013-10-03 13.43.20-kaffe.jpg");
    File file2 = new File(imageFileUtil.getTestInputPath() +
                          "//structure//2013-10-26 20.20.46-kottbullar.jpg");
    imageDescriber = new FileDescriber(file, new Date(0), "a", imageFileUtil.getTestInputPath(),
                                       calendar);
    imageDescriber2 = new FileDescriber(file2, new Date(0), "b", imageFileUtil.getTestInputPath(),
                                        calendar);
    FileDescriber imageDescriber3 = new FileDescriber(file2, null, "b", imageFileUtil.getTestInputPath(),
                                                      calendar);

    FileDescriberPathComparator fileDescriberPathComparator = new FileDescriberPathComparator();
    int compareResult = fileDescriberPathComparator.compare(imageDescriber, imageDescriber2);
    assertEquals(0, compareResult);

    compareResult = fileDescriberPathComparator.compare(imageDescriber, imageDescriber3);
    assertEquals(-1, compareResult);
  }
}
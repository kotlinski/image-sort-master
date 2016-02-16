package se.kotlinski.imagesort.utils;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import se.kotlinski.imagesort.exception.CouldNotParseDateException;
import se.kotlinski.imagesort.forecaster.date.FileDateInterpreter;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class FileDateInterpreterTest {

  private FileDateInterpreter fileDateInterpreter;
  private File imageWithDate;
  private File videoWithDate;
  private File imageWithoutDate;
  private File trickySnapchatImage;

  @Before
  public void setUp() throws Exception {

    MediaFileTestUtil mediaFileTestUtil = new MediaFileTestUtil(new MediaFileUtil());
    imageWithDate = new File(mediaFileTestUtil.getTestInputPath() +
                             File.separator + "2013" +
                             File.separator + "snapchat" +
                             File.separator + "2013-10-03 13.43.20-kaffe.jpg");

    imageWithoutDate = new File(mediaFileTestUtil.getTestInputPath() +
                                File.separator +
                                "nixon on raindeer - no date.jpg");
    videoWithDate = new File(mediaFileTestUtil.getTestInputPath() +
                             File.separator +
                             "2014-03-16 10.45.09.mp4");

    trickySnapchatImage = new File(mediaFileTestUtil.getTestInputPath() +
                                   File.separator +
                                   "tricky-snapchat-image.jpg");

    fileDateInterpreter = new FileDateInterpreter();
  }

  @Test
  public void testGetFileDate_ImageWithDate() {
    try {
      Date date = fileDateInterpreter.getDate(imageWithDate);
      Date expectedDate = getExpectedDate("2013-10-03 13.43.20", null);
      Assert.assertEquals("Date from date with file", expectedDate, date);
    }
    catch (CouldNotParseDateException e) {
      assert false;
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testGetFileDate_VideoWithDate() {
    try {
      Date date = fileDateInterpreter.getDate(videoWithDate);
      Date expectedDate = getExpectedDate("2014-03-16 11.45.09", null);
      Assert.assertEquals("Date from date with file", expectedDate, date);
    }
    catch (CouldNotParseDateException e) {
      assert false;
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testGetFileDate_TrickySnapchatImage() {
    try {
      fileDateInterpreter.getDate(trickySnapchatImage);
    }
    catch (CouldNotParseDateException e) {
      assert true;
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }


  @Test
  public void testGetFileDate_VideoWithoutDate() {
    try {
      fileDateInterpreter.getDate(imageWithoutDate);
      assert false;
    }
    catch (CouldNotParseDateException e) {
      assert true;
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  private Date getExpectedDate(String source, TimeZone timeZone) {
    DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH.mm.ss");
    if (timeZone != null) {
      formatter.setTimeZone(timeZone);
    }

    Date expectedDate = null;
    try {
      expectedDate = formatter.parse(source);
    }
    catch (ParseException e) {
      assert false;
    }
    return expectedDate;
  }
}
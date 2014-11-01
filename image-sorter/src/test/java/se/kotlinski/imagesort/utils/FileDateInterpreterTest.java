package se.kotlinski.imagesort.utils;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import se.kotlinski.imagesort.exception.CouldNotParseDateException;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class FileDateInterpreterTest {

  private FileDateInterpreter fileDateInterpreter;
  private ImageFileUtil imageFileUtil;
  private File imageWithDate;
  private File videoWithDate;
  private File imageWitouthDate;

  @Before
  public void setUp() throws Exception {

    imageFileUtil = new ImageFileUtil();
    imageWithDate = new File(imageFileUtil.getTestInputPath() +
                             File.separator + "2013" +
                             File.separator + "snapchat" +
                             File.separator + "2013-10-03 13.43.20-kaffe.jpg");

    imageWitouthDate = new File(imageFileUtil.getTestInputPath() +
                                File.separator +
                                "image-without-dates.jpg");
    videoWithDate = new File(imageFileUtil.getTestInputPath() +
                             File.separator +
                             "2014-03-16 10.45.09.mp4");

    fileDateInterpreter = new FileDateInterpreter();
  }

  @Test
  public void testGetFileDate() {
    Date date = null;
    try {
      date = fileDateInterpreter.getDate(imageWithDate);
      Date expectedDate = getExpectedDate("2013-10-03 13.43.20", null);
      Assert.assertEquals("Date from date with file", expectedDate, date);
    }
    catch (CouldNotParseDateException e) {
      e.printStackTrace();
      assert false;
    }

    try {
      date = fileDateInterpreter.getDate(videoWithDate);
      Date expectedDate = getExpectedDate("2014-03-16 10.45.09", TimeZone.getTimeZone("UTC"));
      Assert.assertEquals("Date from date with file", expectedDate, date);
    }
    catch (CouldNotParseDateException e) {
      e.printStackTrace();
      assert false;
    }

    try {
      date = fileDateInterpreter.getDate(imageWitouthDate);
      assert false;
    }
    catch (CouldNotParseDateException e) {
      assert true;
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
      e.printStackTrace();
    }
    return expectedDate;
  }
}
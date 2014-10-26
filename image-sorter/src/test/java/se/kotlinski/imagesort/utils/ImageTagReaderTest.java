package se.kotlinski.imagesort.utils;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import static org.junit.Assert.*;

public class ImageTagReaderTest {

  @Before
  public void setUp() throws Exception {

  }

  @Test
  public void testFormatFileDate() throws Exception {
    GregorianCalendar calendar = new GregorianCalendar();
    calendar.setTimeZone(TimeZone.getTimeZone("UTC"));
    String fomattedDate = ImageTagReader.formatFileDate(new Date(0), calendar);
    assertEquals("1970-01-01 00.00.00", fomattedDate);
    fomattedDate = ImageTagReader.formatFileDate(new Date(1356900595000l), calendar);
    assertEquals("2012-12-30 20.49.55", fomattedDate);

  }

  @Test
  public void testFormatPathDate() throws Exception {
    GregorianCalendar calendar = new GregorianCalendar();
    calendar.setTimeZone(TimeZone.getTimeZone("UTC"));
    String fomattedDate = ImageTagReader.formatPathDate(new Date(0));
    assertEquals("1970" + File.separator + "01", fomattedDate);
    fomattedDate = ImageTagReader.formatPathDate(new Date(1356900595000l));
    assertEquals("2012" + File.separator + "12", fomattedDate);
  }
}
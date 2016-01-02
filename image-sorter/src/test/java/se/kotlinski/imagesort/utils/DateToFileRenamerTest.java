package se.kotlinski.imagesort.utils;

import org.junit.Before;
import org.junit.Test;
import se.kotlinski.imagesort.forecaster.date.DateToFileRenamer;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import static org.junit.Assert.assertEquals;

public class DateToFileRenamerTest {

  private DateToFileRenamer dateToFileRenamer;

  @Before
  public void setUp() throws Exception {
    Calendar calendar = new GregorianCalendar();
    dateToFileRenamer = new DateToFileRenamer(calendar);
  }
  
  @Test
  public void testFormatPathDate() throws Exception {
    GregorianCalendar calendar = new GregorianCalendar();
    calendar.setTimeZone(TimeZone.getTimeZone("UTC"));
    String formattedDate = dateToFileRenamer.formatPathDate(new Date(0));
    assertEquals("1970" + File.separator + "01", formattedDate);
    formattedDate = dateToFileRenamer.formatPathDate(new Date(1356900595000l));
    assertEquals("2012" + File.separator + "12", formattedDate);
  }
}
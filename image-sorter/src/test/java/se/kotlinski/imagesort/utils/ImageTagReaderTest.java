package se.kotlinski.imagesort.utils;

import org.junit.Before;
import org.junit.Test;
import se.kotlinski.imagesort.exception.CouldNotParseDateException;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import static org.junit.Assert.*;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

public class ImageTagReaderTest {

  @Before
  public void setUp() throws Exception {
  }

  @Test
  public void testGetImageDate() throws Exception {
    File dateMock = spy(new File("mock file name"));

    try{
      ImageTagReader.getImageDate(dateMock);
      assert false;
    } catch (CouldNotParseDateException e){
      assert true;
    }
  }

  @Test
  public void testFormatPathDate() throws Exception {

  }

  @Test
  public void testFormatFileDate() throws Exception {
    Date date = spy(new Date());
    when(date.getTime()).thenReturn(1000000000000l);
    Calendar calendar = new GregorianCalendar();
    TimeZone timeZone = TimeZone.getTimeZone("GMT");
    calendar.setTimeZone(timeZone);
    String formattedString = ImageTagReader.formatFileDate(date, calendar);

    assertEquals("formatted date", "2001-09-09_01.46.40", formattedString);
  }

  @Test
  public void testIsValidImageFile() throws Exception {
    File fileMock = spy(new File("fail file"));
    boolean validImageFile = ImageTagReader.isValidImageFile(fileMock);
    assertFalse(validImageFile);
  }
}
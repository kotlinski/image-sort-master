package se.kotlinski.imagesort.utils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created with IntelliJ IDEA. User: Simon Kotlinski Date: 2014-02-10 Time: 21:40 To change this
 * template use File | Settings | File Templates.
 */
public class DateToFileRenamer {
  private static Calendar calendar = new GregorianCalendar();

  public String formatPathDate(Date date) {
    calendar.setTime(date);
    SimpleDateFormat format1 = new SimpleDateFormat("yyyy" + File.separator + "MM");
    return format1.format(calendar.getTime());
  }

  public String formatFileDate(Date date, Calendar calendar) {
    calendar.setTime(date);
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH.mm.ss");
    format.setTimeZone(calendar.getTimeZone());
    return format.format(calendar.getTime());
  }
}

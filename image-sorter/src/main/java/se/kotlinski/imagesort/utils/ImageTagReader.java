package se.kotlinski.imagesort.utils;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import com.drew.metadata.exif.ExifIFD0Directory;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import jdk.nashorn.internal.ir.annotations.Ignore;
import se.kotlinski.imagesort.exception.CouldNotParseDateException;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created with IntelliJ IDEA. User: Simon Kotlinski Date: 2014-02-10 Time: 21:40 To change this
 * template use File | Settings | File Templates.
 */
public class ImageTagReader {

  public static String formatPathDate(Date date) {
    Calendar calendar = new GregorianCalendar();
    calendar.setTime(date);
    SimpleDateFormat format1 = new SimpleDateFormat("yyyy" + File.separator + "MM");
    String formatted = format1.format(calendar.getTime());
    return formatted;
  }

  public static String formatFileDate(Date date, Calendar calendar) {
    calendar.setTime(date);
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH.mm.ss");
    format.setTimeZone(calendar.getTimeZone());
    String formatted = format.format(calendar.getTime());
    return formatted;
  }
}

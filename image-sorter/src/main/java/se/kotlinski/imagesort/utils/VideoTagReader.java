package se.kotlinski.imagesort.utils;

import com.coremedia.iso.IsoFile;
import com.coremedia.iso.boxes.MovieBox;
import com.coremedia.iso.boxes.MovieHeaderBox;
import se.kotlinski.imagesort.exception.CouldNotParseDateException;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class VideoTagReader {

  public static Date printVideoData(String videoFile) throws CouldNotParseDateException {
    try {
      IsoFile isoFile = new IsoFile(videoFile);
      MovieBox moov = isoFile.getMovieBox();
      MovieHeaderBox movieHeaderBox = moov.getMovieHeaderBox();
      return movieHeaderBox.getCreationTime();
    }
    catch (IOException e) {
      e.printStackTrace();
      throw new CouldNotParseDateException();
    }
  }

  public static String formatPathDate(Date date) {
    Calendar calendar = new GregorianCalendar();
    calendar.setTime(date);
    SimpleDateFormat format1 = new SimpleDateFormat("yyyy\\MM");
    String formatted = format1.format(calendar.getTime());
    return formatted;
  }

  public static String formatFileDate(Date date, Calendar calendar) {
    calendar.setTime(date);
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd_hh.mm.ss");
    format.setTimeZone(calendar.getTimeZone());
    String formatted = format.format(calendar.getTime());
    return formatted;
  }

  public static Date getVideoDate(File videoFile) throws CouldNotParseDateException {
    try {
      IsoFile isoFile = new IsoFile(videoFile.getAbsolutePath());
      MovieBox moov = isoFile.getMovieBox();
      MovieHeaderBox movieHeaderBox = moov.getMovieHeaderBox();
      return movieHeaderBox.getCreationTime();
    }
    catch (IOException | NullPointerException e) {
      e.printStackTrace();
      throw new CouldNotParseDateException();
    }
  }

  public static boolean isValidVideoFile(File file) {
    try {
      Date fileDate = getVideoDate(file);
      return fileDate != null;
    }
    catch (CouldNotParseDateException e) {
      e.printStackTrace();
      return false;
    }
  }

}

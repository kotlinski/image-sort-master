package se.kotlinski.imagesort.model;

import org.apache.commons.io.FileUtils;
import se.kotlinski.imagesort.exception.CouldNotParseDateException;
import se.kotlinski.imagesort.utils.ImageTagReader;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Simon on 2014-01-01.
 */
public class FileDescriber {
  private final File file;
  private final ImageTagReader imageTagReader;
  private String md5;
  private Date date;


  public FileDescriber(File file, Date date, String md5) {
    this.md5 = md5;
    this.file = file;
    this.date = date;

    imageTagReader = new ImageTagReader();
  }

  public File getFile() {
    return file;
  }

  public String getMd5() {
    return md5;
  }

  public String getRenamedFilePath() throws CouldNotParseDateException {
    if (date != null) {
      return imageTagReader.formatPathDate(date);
    }
    throw new CouldNotParseDateException();
  }

  public String getRenamedFile() throws CouldNotParseDateException {
    if (date != null) {
      Calendar calendar = new GregorianCalendar();
      String formattedDate = imageTagReader.formatFileDate(date, calendar);
      formattedDate += "." + FileUtils.extension(file.getName());
      return formattedDate;
    }
    else {
      return file.getName();
    }
  }

  public String getOriginalFileName() {
    return file.getName();
  }

  @Override
  public String toString() {
    return file.getName();
  }

  public Date getDate() {
    return date;
  }
}

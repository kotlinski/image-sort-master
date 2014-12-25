package se.kotlinski.imagesort.model;

import com.google.inject.Inject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import se.kotlinski.imagesort.exception.CouldNotParseDateException;
import se.kotlinski.imagesort.utils.DateToFileRenamer;

import java.io.File;
import java.util.Calendar;
import java.util.Date;

public class FileDescriber {
  private static final Logger logger = LogManager.getLogger(FileDescriber.class);
  private final File file;
  private final DateToFileRenamer dateToFileRenamer;
  private final String rootPath;
  private final String md5;
  private final Date date;
  private final Calendar calendar;


  @Inject
  public FileDescriber(File file, Date date, String md5, String rootPath, final Calendar calendar) {
    this.md5 = md5;
    this.file = file;
    this.date = date;
    this.rootPath = rootPath;
    this.calendar = calendar;

    dateToFileRenamer = new DateToFileRenamer(calendar);
  }

  public File getFile() {
    return file;
  }

  public String getMd5() {
    return md5;
  }



  public String getOriginalFileName() {
    return file.getName();
  }

  @Override
  public String toString() {
    return file.getName();
  }

}

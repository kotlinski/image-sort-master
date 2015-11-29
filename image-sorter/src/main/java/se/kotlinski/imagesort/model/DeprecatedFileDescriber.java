package se.kotlinski.imagesort.model;

import com.google.inject.Inject;

import java.io.File;
import java.util.Calendar;
import java.util.Date;

public class DeprecatedFileDescriber {
  private final File file;
  private final String md5;


  @Inject
  public DeprecatedFileDescriber(File file,
                                 Date date,
                                 String md5,
                                 String rootPath,
                                 final Calendar calendar) {
    this.md5 = md5;
    this.file = file;

    //DateToFileRenamer dateToFileRenamer = new DateToFileRenamer(calendar);
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

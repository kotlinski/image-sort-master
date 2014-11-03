package se.kotlinski.imagesort.model;

import org.apache.commons.io.FileUtils;
import se.kotlinski.imagesort.exception.CouldNotParseDateException;
import se.kotlinski.imagesort.utils.DateToFileRenamer;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileDescriber {
  private final File file;
  private final DateToFileRenamer dateToFileRenamer;
  private final String rootPath;
  private String md5;
  private Date date;

  private static Calendar calendar = new GregorianCalendar();


  public FileDescriber(File file, Date date, String md5, String rootPath) {
    this.md5 = md5;
    this.file = file;
    this.date = date;
    this.rootPath = rootPath;

    dateToFileRenamer = new DateToFileRenamer();
  }

  public File getFile() {
    return file;
  }

  public String getMd5() {
    return md5;
  }

  public String getRenamedFilePath() throws CouldNotParseDateException {
    if (date != null) {
      return dateToFileRenamer.formatPathDate(date);
    }
    throw new CouldNotParseDateException();
  }

  public String getFlavour() {
    String absolutePath = file.getAbsolutePath();
    System.out.println(absolutePath);

    String flavour = absolutePath.replace(rootPath, "");
    flavour = flavour.replace(file.getName(), "");
    flavour = flavour.replace("other" + File.separator, "");

    int monthSequence = 2;
    flavour = removeDigitFolders(flavour, monthSequence);
    int yearSequence = 4;
    flavour = removeDigitFolders(flavour, yearSequence);
    System.out.println("return flavour: " + flavour);
    return flavour;
  }


  public String removeDigitFolders(String flavour, int sequence) {
    String pattern = Pattern.quote(File.separator) + "\\d{"+sequence+"}" + Pattern.quote(File.separator);
    System.err.println("pattern: " +  pattern);
    flavour = flavour.replaceAll(pattern, Matcher.quoteReplacement(File.separator));
    return flavour;
  }

  public String getDateFilename(boolean appendMD5) throws CouldNotParseDateException {
    if (date != null) {
      String formattedDate = dateToFileRenamer.formatFileDate(date, calendar);
      if (appendMD5) {
        formattedDate += "-" + md5;
      }
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

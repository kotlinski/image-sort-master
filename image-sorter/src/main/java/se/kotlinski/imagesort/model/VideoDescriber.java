package se.kotlinski.imagesort.model;

import org.apache.commons.io.FileUtils;
import se.kotlinski.imagesort.exception.CouldNotGenerateIDException;
import se.kotlinski.imagesort.exception.CouldNotParseDateException;
import se.kotlinski.imagesort.utils.VideoTagReader;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.GregorianCalendar;

/**
 * Created by Simon on 2014-01-01.
 */
public class VideoDescriber implements Describer, Comparable<Describer> {
  private final File file;
  private String md5;


  public VideoDescriber(File file) throws CouldNotGenerateIDException {
    md5 = generateMd5(file);
    this.file = file;
    //System.out.println(file.getName() + " md5: " + md5);
  }

  private static String byteArray2Hex(byte[] hash) {
    Formatter formatter = new Formatter();
    for (byte b : hash) {
      formatter.format("%02x", b);
    }
    return formatter.toString();
  }

  public File getFile() {
    return file;
  }

  public String getMd5() {
    return md5;
  }

  public String getRenamedFilePath() throws CouldNotParseDateException {
    //TODO build a new string of month, year and formattad name.
    Date date = VideoTagReader.getVideoDate(file);
    return VideoTagReader.formatPathDate(date);
  }

  public String getRenamedFile() throws CouldNotParseDateException {
    //TODO build a new string of month, year and formattad name.
    Date date = VideoTagReader.getVideoDate(file);
    Calendar calendar = new GregorianCalendar();
    String formattedDate = VideoTagReader.formatFileDate(date, calendar);
    formattedDate += "." + FileUtils.extension(file.getName());
    System.out.println("New file name: " + formattedDate);
    return formattedDate;
  }

  public String getOriginalFileName() {
    return file.getName();
  }


  public String generateMd5(final File file) throws CouldNotGenerateIDException {
    FileInputStream fis = null;
    byte[] hash = null;
    try {
      MessageDigest messageDigest = MessageDigest.getInstance("Md5");
      fis = new FileInputStream(file);
      BufferedInputStream bis = new BufferedInputStream(fis);
      DigestInputStream dis = new DigestInputStream(bis, messageDigest);

      // read the file and update the hash calculation
      while (dis.read() != -1) {
        ;
      }

      // get the hash value as byte array
      hash = messageDigest.digest();
    }
    catch (NoSuchAlgorithmException | IOException e) {
      e.printStackTrace();
      throw new CouldNotGenerateIDException();
    }
    return byteArray2Hex(hash);
  }

  @Override
  public String toString() {
    return file.getName();
  }

  @Override
  public int compareTo(final Describer describer) {
    String renamedFilePath = null;
    try {
      renamedFilePath = getRenamedFilePath();
      return renamedFilePath.compareTo(describer.getRenamedFilePath());
    }
    catch (CouldNotParseDateException e) {
      e.printStackTrace();
    }
    return -1;
  }
}

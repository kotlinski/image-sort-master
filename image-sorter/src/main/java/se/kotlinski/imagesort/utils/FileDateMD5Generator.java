package se.kotlinski.imagesort.utils;

import com.coremedia.iso.IsoFile;
import com.coremedia.iso.boxes.MovieBox;
import com.coremedia.iso.boxes.MovieHeaderBox;
import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifIFD0Directory;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import se.kotlinski.imagesort.exception.CouldNotGenerateIDException;
import se.kotlinski.imagesort.exception.CouldNotParseDateException;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Formatter;

/**
 * Date: 2014-10-24
 *
 * @author Simon Kotlinski
 */
public class FileDateMD5Generator {
  public FileDateMD5Generator() {
  }


  public String generateMd5(final File file) {
    try {
      return generateImageMD5(file);
    }
    catch (CouldNotGenerateIDException e) {
      e.printStackTrace();
    }
    // If no image file
    return file.getAbsolutePath();
  }

  private String generateImageMD5(final File file) throws CouldNotGenerateIDException {
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

  private static String byteArray2Hex(byte[] hash) {
    Formatter formatter = new Formatter();
    for (byte b : hash) {
      formatter.format("%02x", b);
    }
    return formatter.toString();
  }
}

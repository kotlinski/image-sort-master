package se.kotlinski.imagesort.utils;

import se.kotlinski.imagesort.exception.CouldNotGenerateIDException;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

public class FileDateUniqueGenerator {
  public FileDateUniqueGenerator() {
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
    try {
      MessageDigest messageDigest = MessageDigest.getInstance("Md5");
      FileInputStream fis = new FileInputStream(file);
      BufferedInputStream bis = new BufferedInputStream(fis);
      DigestInputStream dis = new DigestInputStream(bis, messageDigest);

      // read the file and update the hash calculation
      boolean haveMore = true;
      while (haveMore) {
        haveMore = dis.read() != -1;
      }

      // get the hash value as byte array
      byte[] hash = messageDigest.digest();
      return byteArray2Hex(hash);
    }
    catch (NoSuchAlgorithmException | IOException e) {
      e.printStackTrace();
      throw new CouldNotGenerateIDException();
    }
  }

  private String byteArray2Hex(byte[] hash) {
    Formatter formatter = new Formatter();
    for (byte b : hash) {
      formatter.format("%02x", b);
    }
    return formatter.toString();
  }
}

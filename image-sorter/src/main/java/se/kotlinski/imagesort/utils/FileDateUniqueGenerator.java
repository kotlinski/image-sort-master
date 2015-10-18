package se.kotlinski.imagesort.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
  private static final Logger LOGGER = LogManager.getLogger(FileDateUniqueGenerator.class);

  public String generateMd5(final File file) {
    try {
      return generateImageMD5(file);
    }
    catch (CouldNotGenerateIDException e) {
      LOGGER.error("Could not generate id for file: " + file, e);
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    // If no image file
    return file.getAbsolutePath();
  }

  private String generateImageMD5(final File file) throws Exception {
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

      dis.close();

      // get the hash value as byte array
      byte[] hash = messageDigest.digest();
      return byteArray2Hex(hash);
    }
    catch (NoSuchAlgorithmException | IOException e) {
      LOGGER.error("Could not generate image unique id", e);
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

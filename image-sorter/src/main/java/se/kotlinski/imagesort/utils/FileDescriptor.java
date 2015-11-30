package se.kotlinski.imagesort.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileDescriptor {
  private static final Logger LOGGER = LogManager.getLogger(FileDescriptor.class);


  public String getFlavour(final String absoluteRootPath, final File resourceFile) {
    String absolutePath = resourceFile.getAbsolutePath();
    LOGGER.debug(absolutePath);

    String flavour = absolutePath.replace(absoluteRootPath, "");
    if (flavour != null) {
      flavour = flavour.replace(resourceFile.getName(), "");
    }
    flavour = flavour.replace("other" + File.separator, "");

    int monthSequence = 2;
    flavour = removeDigitFolders(flavour, monthSequence);
    int yearSequence = 4;
    flavour = removeDigitFolders(flavour, yearSequence);

    LOGGER.debug("return flavour: " + flavour);
    return flavour;
  }

  String removeDigitFolders(String flavour, int sequence) {
    String pattern = Pattern.quote(File.separator) +
                     "\\d{" +
                     sequence +
                     "}" +
                     Pattern.quote(File.separator);
    LOGGER.debug("pattern: " + pattern);
    return flavour.replaceAll(pattern, Matcher.quoteReplacement(File.separator));
  }
/*
  public String getFileExtension(final File file) {
    return "." + FilenameUtils.getExtension(file.getName());
  }*/

}

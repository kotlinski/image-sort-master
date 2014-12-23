package se.kotlinski.imagesort.mapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import se.kotlinski.imagesort.exception.CouldNotParseDateException;
import se.kotlinski.imagesort.model.FileDescriber;

import java.util.Comparator;


public class FileDescriberPathComparator implements Comparator<FileDescriber> {
  private static final Logger logger = LogManager.getLogger(FileDescriberPathComparator.class);

  @Override
  public int compare(final FileDescriber describer1, final FileDescriber describer2) {
    try {
      String renamedFilePath = describer1.getRenamedFilePath();
      return renamedFilePath.compareTo(describer2.getRenamedFilePath());
    }
    catch (CouldNotParseDateException e) {
      logger.error("Could not parse date: " + describer1.getFile() + ", " + describer2.getFile(),
                   e);
    }
    return -1;
  }
}



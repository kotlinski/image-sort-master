package se.kotlinski.imagesort.mapper;

import se.kotlinski.imagesort.exception.CouldNotParseDateException;
import se.kotlinski.imagesort.model.FileDescriber;

import java.util.Comparator;

/**
 * Date: 2014-11-01
 *
 * @author Simon Kotlinski
 */
public class FileDescriberPathComperator implements Comparator<FileDescriber> {
  @Override
  public int compare(final FileDescriber describer1, final FileDescriber describer2) {
    String renamedFilePath = null;
    try {
      renamedFilePath = describer1.getRenamedFilePath();
      return renamedFilePath.compareTo(describer2.getRenamedFilePath());
    }
    catch (CouldNotParseDateException e) {
      e.printStackTrace();
    }
    return -1;
  }
}



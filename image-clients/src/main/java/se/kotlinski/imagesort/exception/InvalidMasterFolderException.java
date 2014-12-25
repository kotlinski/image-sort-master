package se.kotlinski.imagesort.exception;

import java.io.File;

public class InvalidMasterFolderException extends Exception {

  private final File masterFolder;

  public InvalidMasterFolderException(final String message, final File masterFolder) {
    super(message);
    this.masterFolder = masterFolder;
  }

  public File getMasterFolder() {
    return masterFolder;
  }
}

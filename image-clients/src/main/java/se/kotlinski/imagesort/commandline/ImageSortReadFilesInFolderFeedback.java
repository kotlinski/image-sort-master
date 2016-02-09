package se.kotlinski.imagesort.commandline;

import se.kotlinski.imagesort.main.ClientReadFilesInFolderInterface;

public class ImageSortReadFilesInFolderFeedback implements ClientReadFilesInFolderInterface {

  @Override
  public void parsedFilesInMasterFolderProgress(final int size) {
    System.out.print("Files found: " + size + "\r");
  }

}

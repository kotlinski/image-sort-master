package se.kotlinski.imagesort.commandline.listeners;

import se.kotlinski.imagesort.feedback.ReadFilesFeedbackInterface;

public class ImageSortReadFilesInFolderFeedback implements ReadFilesFeedbackInterface {

  @Override
  public void parsedFilesInMasterFolderProgress(final int size) {
    System.out.print("Files found: " + size + "\r");
  }

}

package se.kotlinski.imagesort.javafx.controllers.listeners;

import se.kotlinski.imagesort.data.MediaFileDataHash;
import se.kotlinski.imagesort.feedback.FindDuplicatesFeedbackInterface;

import java.io.File;
import java.util.List;
import java.util.Map;

public class FindDuplicatesGUIFeedback implements FindDuplicatesFeedbackInterface {

  private final FindDuplicatesGUIFeedback analyzeTabController;

  public FindDuplicatesGUIFeedback(final FindDuplicatesGUIFeedback analyzeTabController) {
    this.analyzeTabController = analyzeTabController;
  }

  @Override
  public void startGroupFilesByContent() {

  }

  @Override
  public void groupFilesByContentProgress(final int total, final int progress) {

  }

  @Override
  public void masterFolderSuccessfulParsed(final Map<MediaFileDataHash, List<File>> mediaFilesInFolder) {
    analyzeTabController.masterFolderSuccessfulParsed(mediaFilesInFolder);

  }

}

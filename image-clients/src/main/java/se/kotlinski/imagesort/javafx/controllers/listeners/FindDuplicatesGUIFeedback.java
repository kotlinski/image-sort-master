package se.kotlinski.imagesort.javafx.controllers.listeners;

import se.kotlinski.imagesort.data.MediaFileDataHash;
import se.kotlinski.imagesort.feedback.FindDuplicatesFeedbackInterface;
import se.kotlinski.imagesort.feedback.ReadFilesFeedbackInterface;
import se.kotlinski.imagesort.javafx.controllers.tabs.FindDuplicatesTabController;

import java.io.File;
import java.util.List;
import java.util.Map;

public class FindDuplicatesGUIFeedback
    implements FindDuplicatesFeedbackInterface, ReadFilesFeedbackInterface {

  private final FindDuplicatesTabController tabController;

  public FindDuplicatesGUIFeedback(final FindDuplicatesTabController findDuplicatesTabController) {
    this.tabController = findDuplicatesTabController;
  }

  @Override
  public void startGroupFilesByContent() {
    tabController.startGroupingFilesByContent();

  }

  @Override
  public void groupFilesByContentProgress(final int total, final int progress) {
    tabController.groupFilesByContentProgress(total, progress);
  }


  @Override
  public void parsedFilesInMasterFolderProgress(final int size) {
    tabController.parsedFilesInMasterFolderProgress(size);
  }


  @Override
  public void masterFolderSuccessfulParsed(final Map<MediaFileDataHash, List<File>> mediaFilesInFolder) {
    tabController.masterFolderSuccessfulParsed(mediaFilesInFolder);
  }
}

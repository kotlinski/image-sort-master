package se.kotlinski.imagesort.javafx.controllers.listeners;

import se.kotlinski.imagesort.javafx.controllers.tabs.PreMoveTabController;
import se.kotlinski.imagesort.feedback.ReadFilesFeedbackInterface;

public class ReadFilesGUIFeedback implements ReadFilesFeedbackInterface {

  private final PreMoveTabController preMoveTabController;

  public ReadFilesGUIFeedback(final PreMoveTabController preMoveTabController) {
    this.preMoveTabController = preMoveTabController;
  }

  @Override
  public void parsedFilesInMasterFolderProgress(final int size) {
    preMoveTabController.readFilesProgressFeedback(size);
  }

}

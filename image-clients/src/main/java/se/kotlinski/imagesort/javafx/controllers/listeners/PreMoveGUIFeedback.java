package se.kotlinski.imagesort.javafx.controllers.listeners;

import se.kotlinski.imagesort.data.RelativeMediaFolderOutput;
import se.kotlinski.imagesort.feedback.PreMoveFeedbackInterface;
import se.kotlinski.imagesort.javafx.controllers.TabGroupController;
import se.kotlinski.imagesort.javafx.controllers.tabs.PreMoveTabController;

import java.io.File;
import java.util.List;
import java.util.Map;

public class PreMoveGUIFeedback implements PreMoveFeedbackInterface {

  private final TabGroupController tabSwitcher;
  private final PreMoveTabController preMoveTabController;

  public PreMoveGUIFeedback(final TabGroupController tabSwitcher,
                            final PreMoveTabController preMoveTabController) {
    this.tabSwitcher = tabSwitcher;
    this.preMoveTabController = preMoveTabController;
  }


  @Override
  public void initiatePreMovePhase() {
    preMoveTabController.initiatingPreMoveFeedback();
    preMoveTabController.startCalculatingOutputDirectories();
  }

  @Override
  public void calculatedDestinationForEachFile(final Map<RelativeMediaFolderOutput, List<File>> mediaFileDestinations) {
    preMoveTabController.doneCalculatingDestionationEveryFile(mediaFileDestinations);
  }

  @Override
  public void groupingFilesByContentProgress(final int total, final int progress) {
    preMoveTabController.groupFilesByContentProgress(total, progress);
  }

  @Override
  public void conflictFound(final RelativeMediaFolderOutput outputDirectory) {
    //Ignore
  }

  @Override
  public void fileGroupedByContent(final Map<List<File>, RelativeMediaFolderOutput> filesGroupedByContent) {
    preMoveTabController.doneWithMovePhase(filesGroupedByContent);
  }
}

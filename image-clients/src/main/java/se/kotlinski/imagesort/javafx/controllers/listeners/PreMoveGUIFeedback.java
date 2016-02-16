package se.kotlinski.imagesort.javafx.controllers.listeners;

import se.kotlinski.imagesort.data.RelativeMediaFolderOutput;
import se.kotlinski.imagesort.data.SortSettings;
import se.kotlinski.imagesort.feedback.PreMoveFeedbackInterface;
import se.kotlinski.imagesort.feedback.ReadFilesFeedbackInterface;
import se.kotlinski.imagesort.javafx.controllers.tabs.PreMoveTabController;

import java.io.File;
import java.util.List;
import java.util.Map;

public class PreMoveGUIFeedback implements PreMoveFeedbackInterface, ReadFilesFeedbackInterface {

  private final PreMoveTabController preMoveTabController;

  public PreMoveGUIFeedback(final PreMoveTabController preMoveTabController) {
    this.preMoveTabController = preMoveTabController;
  }


  @Override
  public void preMovePhaseInitiated() {
    preMoveTabController.preMoveFeedbackInitiated();
    preMoveTabController.startCalculatingOutputDirectories();
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
  public void calculatedDestinationForEachFile(final Map<RelativeMediaFolderOutput, List<File>> mediaFileDestinations) {
    preMoveTabController.doneCalculatingDestinationEveryFile(mediaFileDestinations);
  }

  @Override
  public void preMovePhaseComplete(final Map<List<File>, RelativeMediaFolderOutput> filesGroupedByContent,
                                   final SortSettings sortSettings) {
    preMoveTabController.preMovePhaseCompleted(filesGroupedByContent, sortSettings);

  }


  @Override
  public void parsedFilesInMasterFolderProgress(final int size) {
    preMoveTabController.readFilesProgressFeedback(size);
  }
}

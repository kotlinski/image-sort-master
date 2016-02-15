package se.kotlinski.imagesort.javafx.controllers.listeners;

import se.kotlinski.imagesort.data.RelativeMediaFolderOutput;
import se.kotlinski.imagesort.feedback.MoveFeedbackInterface;
import se.kotlinski.imagesort.javafx.controllers.tabs.MoveTabController;

import java.io.File;
import java.util.List;
import java.util.Map;

public class MoveGUIFeedback implements MoveFeedbackInterface {

  private final MoveTabController moveTabController;

  public MoveGUIFeedback(final MoveTabController moveTabController) {
    this.moveTabController = moveTabController;
  }

  @Override
  public void startResolvingConflicts() {
    moveTabController.startResolvingConflicts();
  }


  @Override
  public void successfulResolvedOutputConflicts(final Map<List<File>, RelativeMediaFolderOutput> resolvedFilesToOutputMap) {
    moveTabController.successfulResolvedOutputConflicts(resolvedFilesToOutputMap);
  }

  @Override
  public void skippingFilesToMove(final int skippedFiles, final int filesToMove) {

  }

  @Override
  public void prepareMovePhase() {

  }


  @Override
  public void startMovingFiles() {
    moveTabController.startMovingFiles();
  }


  @Override
  public void copyingFile(final int numberOfCopiedFiles, final int size) {
    moveTabController.copyingFile(numberOfCopiedFiles, size);
  }

  @Override
  public void deletingFile(final int filesDeleted, final int size) {
    moveTabController.deletingFile(filesDeleted, size);
  }

  @Override
  public void movePhaseComplete() {
    moveTabController.movePhaseComplete();
  }

}

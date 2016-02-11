package se.kotlinski.imagesort.javafx.controllers.listeners;

import se.kotlinski.imagesort.data.RelativeMediaFolderOutput;
import se.kotlinski.imagesort.feedback.MoveFeedbackInterface;

import java.io.File;
import java.util.List;
import java.util.Map;

public class MoveGUIFeedback implements MoveFeedbackInterface {


  @Override
  public void startResolvingConflicts() {
  }


  @Override
  public void successfulResolvedOutputConflicts(final Map<List<File>, RelativeMediaFolderOutput> resolvedFilesToOutputMap) {

  }

  @Override
  public void startMovingFiles() {

  }

  @Override
  public void skippingFilesToMove(final int skippedFiles, final int filesToMove) {

  }

  @Override
  public void prepareMovePhase() {

  }
}

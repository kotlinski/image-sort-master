package se.kotlinski.imagesort.javafx.controllers;

import se.kotlinski.imagesort.data.RelativeMediaFolderOutput;
import se.kotlinski.imagesort.main.ClientMovePhaseInterface;

import java.io.File;
import java.util.List;
import java.util.Map;

public class ClientMovePhaseImplementation implements ClientMovePhaseInterface {


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

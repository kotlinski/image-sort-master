package se.kotlinski.imagesort.main;

import se.kotlinski.imagesort.data.RelativeMediaFolderOutput;

import java.io.File;
import java.util.List;
import java.util.Map;

public interface ClientMovePhaseInterface {

  void startResolvingConflicts();

  void successfulResolvedOutputConflicts(final Map<List<File>, RelativeMediaFolderOutput> resolvedFilesToOutputMap);

  void startMovingFiles();

  void skippingFilesToMove(final int skippedFiles, final int filesToMove);

  void prepareMovePhase();

}

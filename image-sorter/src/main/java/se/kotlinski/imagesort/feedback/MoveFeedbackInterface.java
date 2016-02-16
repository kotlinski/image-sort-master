package se.kotlinski.imagesort.feedback;

import se.kotlinski.imagesort.data.RelativeMediaFolderOutput;

import java.io.File;
import java.util.List;
import java.util.Map;

public interface MoveFeedbackInterface {

  void startResolvingConflicts();

  void successfulResolvedOutputConflicts(final Map<List<File>, RelativeMediaFolderOutput> resolvedFilesToOutputMap);

  void startMovingFiles();

  void skippingFilesToMove(final int skippedFiles, final int filesToMove);

  void prepareMovePhase();

  void copyingFile(int numberOfCopiedFiles, int size);

  void deletingFile(int filesDeleted, int size);

  void movePhaseComplete();

}

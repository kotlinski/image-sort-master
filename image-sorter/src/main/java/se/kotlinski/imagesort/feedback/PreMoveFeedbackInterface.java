package se.kotlinski.imagesort.feedback;

import se.kotlinski.imagesort.data.RelativeMediaFolderOutput;

import java.io.File;
import java.util.List;
import java.util.Map;

public interface PreMoveFeedbackInterface {

  void initiatePreMovePhase();

  void calculatedDestinationForEachFile(final Map<RelativeMediaFolderOutput, List<File>> mediaFileDestinations);

  void groupingFilesByContentProgress(final int total, final int progress);

  void conflictFound(RelativeMediaFolderOutput outputDirectory);

  void fileGroupedByContent(Map<List<File>, RelativeMediaFolderOutput> filesGroupedByContent);
}

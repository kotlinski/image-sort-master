package se.kotlinski.imagesort.feedback;

import se.kotlinski.imagesort.data.RelativeMediaFolderOutput;
import se.kotlinski.imagesort.data.SortSettings;

import java.io.File;
import java.util.List;
import java.util.Map;

public interface PreMoveFeedbackInterface {

  void preMovePhaseInitiated();

  void calculatedDestinationForEachFile(final Map<RelativeMediaFolderOutput, List<File>> mediaFileDestinations);

  void groupingFilesByContentProgress(final int total, final int progress);

  void conflictFound(RelativeMediaFolderOutput outputDirectory);

  void preMovePhaseComplete(Map<List<File>, RelativeMediaFolderOutput> filesGroupedByContent,
                            final SortSettings sortSettings);
}

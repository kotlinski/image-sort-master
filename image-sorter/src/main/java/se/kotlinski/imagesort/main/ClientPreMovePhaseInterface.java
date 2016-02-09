package se.kotlinski.imagesort.main;

import se.kotlinski.imagesort.data.RelativeMediaFolderOutput;

import java.io.File;
import java.util.List;
import java.util.Map;

public interface ClientPreMovePhaseInterface {

  void initiateMediaFileParsingPhase();

  void startCalculatingOutputDirectories();

  void successfulCalculatedOutputDestinations(final Map<RelativeMediaFolderOutput, List<File>> mediaFileDestinations);

  void searchingForConflictsProgress(final int total, final int progress);

  void conflictFound(RelativeMediaFolderOutput outputDirectory);
}

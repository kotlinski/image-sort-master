package se.kotlinski.imagesort.main;

import se.kotlinski.imagesort.data.MediaFileDataHash;
import se.kotlinski.imagesort.data.RelativeMediaFolderOutput;

import java.io.File;
import java.util.List;
import java.util.Map;

public interface ClientInterface {
  void initiateMediaFileParsingPhase();

  void masterFolderFailedParsed();

  void parsedFilesInMasterFolderProgress(final int size);

  boolean masterFolderSuccessfulParsed(final Map<MediaFileDataHash, List<File>> mediaFilesInFolder);


  void startCalculatingOutputDirectories();

  void successfulCalculatedOutputDestinations(final Map<RelativeMediaFolderOutput, List<File>> mediaFileDestinations);


  void startResolvingConflicts();

  void startGroupFilesByContent();

  void groupFilesByContentProgress(final int total, final int progress);

  void successfulResolvedOutputConflicts(final Map<List<File>, RelativeMediaFolderOutput> resolvedFilesToOutputMap);


  void startMovingFiles();

  void searchingForConflictsProgress(final int total, final int progress);

  void conflictFound(final RelativeMediaFolderOutput outputDirectory);

  void skippingFilesToMove(final int skippedFiles, final int filesToMove);

  void prepareMovePhase();


}

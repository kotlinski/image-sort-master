package se.kotlinski.imagesort.executor;

import se.kotlinski.imagesort.data.MediaFileDataHash;

import java.io.File;
import java.util.List;
import java.util.Map;

public abstract class ClientInterface {
  public abstract void initiateMediaFileParsingPhase();

  public abstract void masterFolderFailedParsed();
  public abstract void parsedFilesInMasterFolderProgress(final int size);

  public abstract boolean masterFolderSuccessfulParsed(final Map<MediaFileDataHash, List<File>> mediaFilesInFolder);




  public abstract void startCalculatingOutputDirectories();

  public abstract void successfulCalculatedOutputDestinations(final Map<String, List<File>> mediaFileDestinations);



  public abstract void startResolvingConflicts();

  public abstract void startGroupFilesByContent();

  public abstract void groupFilesByContentProgress(final int total, final int progress);

  public abstract void successfulResolvedOutputConflicts(final Map<List<File>, String> resolvedFilesToOutputMap);



  public abstract void startMovingFiles();

  public abstract void searchingForConflictsProgress(final int total, final int progress);

  public abstract void conflictFound(final String outputDirectory);

  public abstract void skippingFilesToMove(final int skippedFiles, final int filesToMove);

  public abstract void prepareMovePhase();


}

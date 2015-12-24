package se.kotlinski.imagesort.executor;

import java.io.File;
import java.util.List;
import java.util.Map;

public abstract class ClientInterface {

  public abstract boolean masterFolderSuccessfulParsed(final Map<String, List<File>> mediaFilesInFolder);

  public abstract void masterFolderFailedParsed();

  public abstract void successfulCalculatedOutputDestinations(final Map<String, List<File>> mediaFileDestinations);

  public abstract void successfulResolvedOutputConflicts(final Map<List<File>, String> resolvedFilesToOutputMap);

  public abstract void startParsingMasterFolder();

  public abstract void parsedFilesInMasterFolderProgress(final int size);

  public abstract void startGroupFilesByContent();

  public abstract void groupFilesByContentProgress(final int total, final int progress);

  public abstract void startMovingFiles();
}

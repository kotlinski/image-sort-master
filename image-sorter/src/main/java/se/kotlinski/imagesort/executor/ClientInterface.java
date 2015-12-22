package se.kotlinski.imagesort.executor;

import java.io.File;
import java.util.List;
import java.util.Map;

public abstract class ClientInterface {

  public abstract boolean masterFolderSuccessfulParsed(final Map<String, List<File>> mediaFilesInFolder);

  public abstract void masterFolderFailedParsed();

  public abstract void successfulCalculatedOutputDestinations(final Map<String, List<File>> mediaFileDestinations);

  public abstract void successfulResolvedOutputConflicts(final Map<List<File>, String> resolvedFilesToOutputMap);
}

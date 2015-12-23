package se.kotlinski.imagesort.commandline;

import com.google.inject.Inject;
import se.kotlinski.imagesort.calculator.MediaInFolderCalculator;
import se.kotlinski.imagesort.data.MediaFileDataInFolder;
import se.kotlinski.imagesort.executor.ClientInterface;

import java.io.File;
import java.util.List;
import java.util.Map;

public class ImageSortProgressFeedback extends ClientInterface {
  private final FileSystemPrettyPrinter fileSystemPrettyPrinter;

  @Inject
  public ImageSortProgressFeedback(final FileSystemPrettyPrinter fileSystemPrettyPrinter) {
    this.fileSystemPrettyPrinter = fileSystemPrettyPrinter;
  }

  @Override
  public boolean masterFolderSuccessfulParsed(final Map<String, List<File>> mediaFilesInFolder) {
    printMediaFilesInFolderData(mediaFilesInFolder);
    return true;
  }

  @Override
  public void masterFolderFailedParsed() {
    System.out.println("Invalid input folders, try again");
  }

  @Override
  public void successfulCalculatedOutputDestinations(final Map<String, List<File>> mediaFileDestinations) {
    fileSystemPrettyPrinter.convertFolderStructureToString(mediaFileDestinations);
  }

  @Override
  public void successfulResolvedOutputConflicts(final Map<List<File>, String> resolvedFilesToOutputMap) {
    //TODO: implement this
  }

  private void printMediaFilesInFolderData(final Map<String, List<File>> mediaFilesInFolder) {
    MediaInFolderCalculator mediaInFolderCalculator = new MediaInFolderCalculator(); // TODO: inject
    MediaFileDataInFolder mediaDataBeforeExecution;
    mediaDataBeforeExecution = mediaInFolderCalculator.calculateMediaFileDataInFolder(mediaFilesInFolder);

    System.out.println(mediaDataBeforeExecution.toString());
  }
}

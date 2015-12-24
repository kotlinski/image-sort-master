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
  public void startParsingMasterFolder() {
    System.out.println("Parsing input folder for videos and images...");
  }

  @Override
  public void parsedFilesInMasterFolderProgress(final int size) {
    System.out.print("Files found: " + size + "\r");
  }

  @Override
  public void startGroupFilesByContent() {
    System.out.println();
    System.out.println("Grouping files by content...");
  }

  @Override
  public void groupFilesByContentProgress(final int total, final int progress) {
    float percentageProgress = progress * 1f / total * 1f;

    System.out.print("Current progress: " +
                     (int) (percentageProgress * 100f) +
                     "%, " +
                     progress +
                     " of " +
                     total +
                     "\r");
  }

  @Override
  public boolean masterFolderSuccessfulParsed(final Map<String, List<File>> mediaFilesInFolder) {
    System.out.println();
    System.out.println("Folder successfully parse!");
    System.out.println();
    System.out.println("Folder data stats: ");
    printMediaFilesInFolderData(mediaFilesInFolder);
    System.out.println();
    return true;
  }

  @Override
  public void masterFolderFailedParsed() {
    System.out.println("Invalid input folders, try again");
  }

  @Override
  public void successfulCalculatedOutputDestinations(final Map<String, List<File>> mediaFileDestinations) {
    String outputTree = fileSystemPrettyPrinter.convertFolderStructureToString(mediaFileDestinations);
    System.out.println();
    System.out.println(outputTree);
  }

  @Override
  public void successfulResolvedOutputConflicts(final Map<List<File>, String> resolvedFilesToOutputMap) {
    //TODO: implement this
  }


  private void printMediaFilesInFolderData(final Map<String, List<File>> mediaFilesInFolder) {
    MediaInFolderCalculator mediaInFolderCalculator = new MediaInFolderCalculator(); // TODO: inject
    MediaFileDataInFolder mediaDataBeforeExecution;
    mediaDataBeforeExecution = mediaInFolderCalculator.calculateMediaFileDataInFolder(
        mediaFilesInFolder);

    System.out.println(mediaDataBeforeExecution.toString());
  }
}

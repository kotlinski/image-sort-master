package se.kotlinski.imagesort.commandline;

import com.google.inject.Inject;
import se.kotlinski.imagesort.statistics.MediaFilesInFolderCalculator;
import se.kotlinski.imagesort.data.MediaFileDataHash;
import se.kotlinski.imagesort.data.MediaFileDataInFolder;
import se.kotlinski.imagesort.main.ClientInterface;

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
  public void initiateMediaFileParsingPhase() {
    System.out.println("Parsing input folder for videos and images...");
  }

  @Override
  public void masterFolderFailedParsed() {
    System.out.println("Invalid input folders, try again");
  }

  @Override
  public void parsedFilesInMasterFolderProgress(final int size) {
    System.out.print("Files found: " + size + "\r");
  }

  @Override
  public boolean masterFolderSuccessfulParsed(final Map<MediaFileDataHash, List<File>> mediaFilesInFolder) {
    System.out.println();
    System.out.println("Folder data stats: ");
    printMediaFilesInFolderData(mediaFilesInFolder);
    System.out.println();
    return true;
  }

  @Override
  public void startCalculatingOutputDirectories() {
    System.out.println("");
    System.out.println("Start calculating new output directories.");
    System.out.println("");
  }

  @Override
  public void successfulCalculatedOutputDestinations(final Map<String, List<File>> mediaFileDestinations) {
    System.out.println("");
    System.out.println("Total number of output destinations: " + mediaFileDestinations.size());
    System.out.println("The new output tree: ");
    String outputTree;
    outputTree = fileSystemPrettyPrinter.convertFolderStructureToString(mediaFileDestinations,
                                                                        false);
    System.out.println();
    System.out.println(outputTree);
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
  public void startResolvingConflicts() {
    System.out.println("Resolving conflicts...");
    System.out.println();
  }

  @Override
  public void searchingForConflictsProgress(final int total, final int progress) {
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
  public void conflictFound(final String outputDirectory) {
    System.out.println("Multiple files want this output: " +
                       outputDirectory +
                       ",\n ...conflict resolved.");
  }

  @Override
  public void successfulResolvedOutputConflicts(final Map<List<File>, String> resolvedFilesToOutputMap) {
    System.out.println("");
    System.out.println("Resolved output conflicts!");
    System.out.println("");
  }

  @Override
  public void startMovingFiles() {
    System.out.println("Preparing files to be renamed and moved to new folder(s)...");
    System.out.println("");
  }


  @Override
  public void skippingFilesToMove(final int skippedFiles, final int filesToMove) {
    System.out.print(filesToMove +
                     " files will be renamed and/or moved. Skipping " +
                     skippedFiles +
                     " files." +
                     "\r");
  }

  @Override
  public void prepareMovePhase() {
    System.out.println("");
    System.out.println("Moving files...");
  }


  private void printMediaFilesInFolderData(final Map<MediaFileDataHash, List<File>> mediaFilesInFolder) {
    MediaFilesInFolderCalculator mediaFilesInFolderCalculator = new MediaFilesInFolderCalculator(); // TODO: inject
    MediaFileDataInFolder mediaDataBeforeExecution;
    mediaDataBeforeExecution = mediaFilesInFolderCalculator.calculateMediaFileDataInFolder(
        mediaFilesInFolder);

    System.out.println(mediaDataBeforeExecution.toString());
  }
}

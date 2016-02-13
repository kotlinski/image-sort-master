package se.kotlinski.imagesort.commandline.listeners;

import com.google.inject.Inject;
import se.kotlinski.imagesort.commandline.FileSystemPrettyPrinter;
import se.kotlinski.imagesort.data.RelativeMediaFolderOutput;
import se.kotlinski.imagesort.data.SortSettings;
import se.kotlinski.imagesort.feedback.PreMoveFeedbackInterface;

import java.io.File;
import java.util.List;
import java.util.Map;

public class ImageSortPreMoveProgressFeedback implements PreMoveFeedbackInterface {


  private final FileSystemPrettyPrinter fileSystemPrettyPrinter;

  @Inject
  public ImageSortPreMoveProgressFeedback(final FileSystemPrettyPrinter fileSystemPrettyPrinter) {
    this.fileSystemPrettyPrinter = fileSystemPrettyPrinter;
  }

  @Override
  public void preMovePhaseInitiated() {
    System.out.println("");
    System.out.println("Parsing input folder for videos and images...");
    System.out.println("");
    System.out.println("Start calculating new output directories.");
    System.out.println("");
  }

  @Override
  public void calculatedDestinationForEachFile(final Map<RelativeMediaFolderOutput, List<File>> mediaFileDestinations) {
    System.out.println("");
    System.out.println("Total number of output destinations: " + mediaFileDestinations.size());

  }


  @Override
  public void groupingFilesByContentProgress(final int total, final int progress) {
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
  public void conflictFound(final RelativeMediaFolderOutput outputDirectory) {
    System.out.println("Multiple files want this output: " +
                       outputDirectory.relativePath +
                       ",\n ...conflict resolved.");
  }

  @Override
  public void preMovePhaseComplete(final Map<List<File>, RelativeMediaFolderOutput> filesGroupedByContent,
                                   final SortSettings sortSettings) {
    System.out.println("Files grouped by content...\n");
    System.out.println("The new output tree: ");
    String outputTree;
    outputTree = fileSystemPrettyPrinter.convertFolderStructureToString(filesGroupedByContent, false);
    System.out.println();
    System.out.println(outputTree);
  }

}

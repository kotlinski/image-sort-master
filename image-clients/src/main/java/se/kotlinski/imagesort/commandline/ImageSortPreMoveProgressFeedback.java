package se.kotlinski.imagesort.commandline;

import com.google.inject.Inject;
import se.kotlinski.imagesort.data.RelativeMediaFolderOutput;
import se.kotlinski.imagesort.main.ClientPreMovePhaseInterface;

import java.io.File;
import java.util.List;
import java.util.Map;

public class ImageSortPreMoveProgressFeedback implements ClientPreMovePhaseInterface {


  private final FileSystemPrettyPrinter fileSystemPrettyPrinter;

  @Inject
  public ImageSortPreMoveProgressFeedback(final FileSystemPrettyPrinter fileSystemPrettyPrinter) {
    this.fileSystemPrettyPrinter = fileSystemPrettyPrinter;
  }

  @Override
  public void initiateMediaFileParsingPhase() {
    System.out.println("Parsing input folder for videos and images...");
  }

  @Override
  public void startCalculatingOutputDirectories() {
    System.out.println("");
    System.out.println("Start calculating new output directories.");
    System.out.println("");
  }

  @Override
  public void successfulCalculatedOutputDestinations(final Map<RelativeMediaFolderOutput, List<File>> mediaFileDestinations) {
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
  public void conflictFound(final RelativeMediaFolderOutput outputDirectory) {
    System.out.println("Multiple files want this output: " +
                       outputDirectory.relativePath +
                       ",\n ...conflict resolved.");
  }

}

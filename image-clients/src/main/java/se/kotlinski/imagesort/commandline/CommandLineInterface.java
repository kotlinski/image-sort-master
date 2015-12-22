package se.kotlinski.imagesort.commandline;

import com.google.inject.Inject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import se.kotlinski.imagesort.calculator.MediaInFolderCalculator;
import se.kotlinski.imagesort.commandline.argument.Interpreter;
import se.kotlinski.imagesort.data.MediaFileDataInFolder;
import se.kotlinski.imagesort.data.SortSettings;
import se.kotlinski.imagesort.executor.ClientInterface;
import se.kotlinski.imagesort.executor.ImageSorter;
import se.kotlinski.imagesort.utils.DateToFileRenamer;
import se.kotlinski.imagesort.utils.FileDateInterpreter;

import java.io.File;
import java.util.List;
import java.util.Map;

public class CommandLineInterface extends ClientInterface {
  private static final Logger LOGGER = LogManager.getLogger(CommandLineInterface.class);
  private final Interpreter interpreter;
  private final DateToFileRenamer dateToFileRenamer;
  private final FileDateInterpreter fileDateInterpreter;
  private final ImageSorter imageSorter;
  private final FileSystemPrettyPrinter fileSystemPrettyPrinter;

  @Inject
  public CommandLineInterface(final Interpreter interpreter,
                              final DateToFileRenamer dateToFileRenamer,
                              final FileDateInterpreter fileDateInterpreter,
                              final ImageSorter imageSorter,
                              final FileSystemPrettyPrinter fileSystemPrettyPrinter) {
    this.interpreter = interpreter;
    this.dateToFileRenamer = dateToFileRenamer;
    this.fileDateInterpreter = fileDateInterpreter;
    this.imageSorter = imageSorter;
    this.fileSystemPrettyPrinter = fileSystemPrettyPrinter;
  }

  public final void runCommandLine(String[] arguments) {
    SortSettings sortSettings;

    try {
      sortSettings = interpreter.transformArguments(arguments);
    }
    catch (Exception e) {
      System.out.println("Check your parameters...");
      LOGGER.error("Invalid parameters");
      return;
    }

    imageSorter.sortImages(sortSettings);


    //TODO: Make a conflict handler.
    // When several files have the same destination, some salutary checks have to be made.
    // * what if the are different images, etc.
    //
    // Calculate a new "Map-tree" Based on exports.
    //    Each key is : "2014<fileseparator>05<fileseparator>filename followed by a file-list

    // Run Move/delete for each export with tracking on each operation.


    // ReRun groupFilesByMediaContent and Print size of FilesByMediaContent after running move/delete.
    // + Duplicates for each file.


/////////////////////


    //Print all images and videos read from inputFolders + master folder
    //filePrinter.printTotalNumberOfFiles(deprecatedExportFileDataMap.totalNumberOfFiles());

    //Some files may be the same, but with different flavours
    // deprecatedExportCollector.tagUniqueFilesWithSameName(deprecatedExportFileDataMap);

//    printExportFolders(deprecatedExportFileDataMap);

   /* filePrinter.printTotalNumberOfDuplicates(deprecatedExportFileDataMap.totalNumberOfFiles(),
                                             deprecatedExportFileDataMap.getNumberOfUniqueImages(),
                                             deprecatedExportFileDataMap.getNumberOfRemovableFiles());
*/
  }


  private void printMediaFilesInFolderData(final Map<String, List<File>> mediaFilesInFolder) {
    MediaInFolderCalculator mediaInFolderCalculator = new MediaInFolderCalculator(); // TODO: inject
    MediaFileDataInFolder mediaDataBeforeExecution;
    mediaDataBeforeExecution = mediaInFolderCalculator.calculateMediaFileDataInFolder(mediaFilesInFolder);

    System.out.println(mediaDataBeforeExecution.toString());
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
}

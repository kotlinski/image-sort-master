package se.kotlinski.imagesort.commandline;

import com.google.inject.Inject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import se.kotlinski.imagesort.DeprecatedExportCollector;
import se.kotlinski.imagesort.calculator.MediaInFolderCalculator;
import se.kotlinski.imagesort.commandline.argument.Interpreter;
import se.kotlinski.imagesort.data.MediaFileDataInFolder;
import se.kotlinski.imagesort.data.SortSettings;
import se.kotlinski.imagesort.exception.InvalidInputFolders;
import se.kotlinski.imagesort.forecaster.MediaFileForecaster;
import se.kotlinski.imagesort.forecaster.MediaFilesOutputForecaster;
import se.kotlinski.imagesort.mapper.DeprecatedExportFileDataMap;
import se.kotlinski.imagesort.parser.MediaFileParser;
import se.kotlinski.imagesort.resolver.OutputConflictResolver;
import se.kotlinski.imagesort.utils.DateToFileRenamer;
import se.kotlinski.imagesort.utils.FileDateInterpreter;

import java.io.File;
import java.util.List;
import java.util.Map;

public class CommandLineInterface {
  private static final Logger LOGGER = LogManager.getLogger(CommandLineInterface.class);
  private final MediaFileParser mediaFileParser;
  private final FilePrinter filePrinter;
  private final DeprecatedExportCollector deprecatedExportCollector;
  private final Interpreter interpreter;
  private final DateToFileRenamer dateToFileRenamer;
  private final FileDateInterpreter fileDateInterpreter;
  private final FileSystemPrettyPrinter fileSystemPrettyPrinter;
  private final OutputConflictResolver outputConflictResolver;

  @Inject
  public CommandLineInterface(final MediaFileParser mediaFileParser,
                              final FilePrinter filePrinter,
                              final DeprecatedExportCollector deprecatedExportCollector,
                              final Interpreter interpreter,
                              final DateToFileRenamer dateToFileRenamer,
                              final FileDateInterpreter fileDateInterpreter,
                              final FileSystemPrettyPrinter fileSystemPrettyPrinter,
                              final OutputConflictResolver outputConflictResolver) {
    this.mediaFileParser = mediaFileParser;
    this.filePrinter = filePrinter;
    this.deprecatedExportCollector = deprecatedExportCollector;
    this.interpreter = interpreter;
    this.dateToFileRenamer = dateToFileRenamer;
    this.fileDateInterpreter = fileDateInterpreter;
    this.fileSystemPrettyPrinter = fileSystemPrettyPrinter;
    this.outputConflictResolver = outputConflictResolver;
  }

  public final void runCommandLine(String[] arguments) {
    SortSettings sortSettings;
    DeprecatedExportFileDataMap deprecatedExportFileDataMap;

    try {
      sortSettings = interpreter.transformArguments(arguments);
    }
    catch (Exception e) {
      System.out.println("Check your parameters...");
      LOGGER.error("Invalid parameters");
      return;
    }

    Map<String, List<File>> mediaFilesInFolder;
    try {
      mediaFilesInFolder = mediaFileParser.getMediaFilesInFolder(sortSettings.masterFolder);
    }
    catch (InvalidInputFolders invalidInputFolders) {
      System.out.println("Invalid input folders, try again");
      LOGGER.error("Invalid input folders, try again", invalidInputFolders);
      return;
    }
    catch (Exception e) {
      e.printStackTrace();
      return;
    }

    printMediaFilesInFolderData(mediaFilesInFolder);

    Map<String, List<File>> mediaFileDestinations;
    mediaFileDestinations = calculateOutputDirectories(mediaFilesInFolder,
                                                       sortSettings.masterFolder.getAbsolutePath());

    fileSystemPrettyPrinter.convertFolderStructureToString(mediaFileDestinations);

    outputConflictResolver.resolveOutputConflicts(mediaFileDestinations);


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

  private Map<String, List<File>> calculateOutputDirectories(final Map<String, List<File>> mediaFilesInFolder,
                                                             final String masterFolder) {
    MediaFileForecaster mediaFileForecaster;
    mediaFileForecaster = new MediaFileForecaster(dateToFileRenamer, fileDateInterpreter);
    MediaFilesOutputForecaster mediaOutputCalculator;
    mediaOutputCalculator = new MediaFilesOutputForecaster(mediaFileForecaster);

    return mediaOutputCalculator.calculateOutputDestinations(mediaFilesInFolder, masterFolder);
  }

  private void printMediaFilesInFolderData(final Map<String, List<File>> mediaFilesInFolder) {
    MediaInFolderCalculator mediaInFolderCalculator = new MediaInFolderCalculator(); // TODO: inject
    MediaFileDataInFolder mediaDataBeforeExecution;
    mediaDataBeforeExecution =
        mediaInFolderCalculator.calculateMediaFileDataInFolder(mediaFilesInFolder);

    System.out.println(mediaDataBeforeExecution.toString());
  }

}

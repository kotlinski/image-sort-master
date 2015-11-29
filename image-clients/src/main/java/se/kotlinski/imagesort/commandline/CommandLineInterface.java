package se.kotlinski.imagesort.commandline;

import com.google.inject.Inject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import se.kotlinski.imagesort.calculator.MediaInFolderCalculator;
import se.kotlinski.imagesort.commandline.argument.Interpreter;
import se.kotlinski.imagesort.DeprecatedExportCollector;
import se.kotlinski.imagesort.data.MediaFileDataInFolder;
import se.kotlinski.imagesort.forecaster.MediaFileForecaster;
import se.kotlinski.imagesort.forecaster.MediaFilesOutputForecaster;
import se.kotlinski.imagesort.mapper.DeprecatedExportFileDataMap;
import se.kotlinski.imagesort.parser.MediaFileParser;
import se.kotlinski.imagesort.exception.InvalidInputFolders;
import se.kotlinski.imagesort.data.SortSettings;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CommandLineInterface {
  private static final Logger LOGGER = LogManager.getLogger(CommandLineInterface.class);
  private final MediaFileParser mediaFileParser;
  private final FilePrinter filePrinter;
  private final DeprecatedExportCollector deprecatedExportCollector;
  private final Interpreter interpreter;

  @Inject
  public CommandLineInterface(final MediaFileParser mediaFileParser,
                              final FilePrinter filePrinter,
                              final DeprecatedExportCollector deprecatedExportCollector,
                              final Interpreter interpreter) {
    this.mediaFileParser = mediaFileParser;
    this.filePrinter = filePrinter;
    this.deprecatedExportCollector = deprecatedExportCollector;
    this.interpreter = interpreter;
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


    //TODO: 27/11

    // Print size of FilesByMediaContent before running.
    // + Duplicates for each file.
    // (DONE BELOW)
    MediaInFolderCalculator mediaInFolderCalculator = new MediaInFolderCalculator(); // TODO: inject
    MediaFileDataInFolder mediaDataBeforeExecution;
    mediaDataBeforeExecution = mediaInFolderCalculator.calculateMediaFileDataInFolder(mediaFilesInFolder);

    System.out.println(mediaDataBeforeExecution.toString());

    //TODO: 27/11
    // For each (Media?) File.
    //   Calculate their new destinations and decide where they will end up.

    MediaFileForecaster mediaFileForecaster = new MediaFileForecaster(dateToFileRenamer,
                                                                      fileDateInterpreter); // TODO: Inject
    MediaFilesOutputForecaster mediaOutputCalculator = new MediaFilesOutputForecaster(mediaFileForecaster);

    Map<String, List<File>> mediaFileDestinations;
    mediaFileDestinations= mediaOutputCalculator.calculateOutputDestinations(mediaFilesInFolder);


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

  private void printExportFolders(final DeprecatedExportFileDataMap deprecatedExportFileDataMap) {
    Set<String> foldersToExport = deprecatedExportCollector.collectFoldersToExport(deprecatedExportFileDataMap);
    filePrinter.printExportPaths(foldersToExport);
  }


}

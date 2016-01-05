package se.kotlinski.imagesort.commandline;

import com.google.inject.Inject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import se.kotlinski.imagesort.commandline.argument.Interpreter;
import se.kotlinski.imagesort.data.SortSettings;
import se.kotlinski.imagesort.main.ClientInterface;
import se.kotlinski.imagesort.main.ImageSorter;

public class CommandLineInterface {
  private static final Logger LOGGER = LogManager.getLogger(CommandLineInterface.class);
  private final Interpreter interpreter;
  private final ImageSorter imageSorter;

  @Inject
  public CommandLineInterface(final Interpreter interpreter, final ImageSorter imageSorter) {
    this.interpreter = interpreter;
    this.imageSorter = imageSorter;
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

    ClientInterface clientInterface = new ImageSortProgressFeedback(new FileSystemPrettyPrinter());
    imageSorter.sortImages(clientInterface, sortSettings);


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
}






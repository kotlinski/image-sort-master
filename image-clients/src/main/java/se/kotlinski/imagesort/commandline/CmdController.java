package se.kotlinski.imagesort.commandline;

import com.google.inject.Inject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import se.kotlinski.imagesort.controller.ExportCollector;
import se.kotlinski.imagesort.controller.ExportPoints;
import se.kotlinski.imagesort.controller.FileAnalyzer;
import se.kotlinski.imagesort.controller.FileExecutor;
import se.kotlinski.imagesort.exception.CouldNotCreateMasterFolderException;
import se.kotlinski.imagesort.exception.InvalidFolderArgumentsException;
import se.kotlinski.imagesort.exception.InvalidInputFolders;
import se.kotlinski.imagesort.exception.InvalidMasterFolderException;
import se.kotlinski.imagesort.mapper.ExportFileDataMap;
import se.kotlinski.imagesort.model.FolderIO;

import java.util.Set;

public class CmdController {
  private static final Logger logger = LogManager.getLogger(CmdController.class);
  private final FileExecutor fileExecutor;
  private final CmdInterpreter cmdInterpreter;
  private final FileAnalyzer fileAnalyzer;
  private final FilePrinter filePrinter;
  private final ExportCollector exportCollector;

  @Inject
  public CmdController(final FileExecutor fileExecutor,
                       final CmdInterpreter cmdInterpreter,
                       final FileAnalyzer fileAnalyzer,
                       final FilePrinter filePrinter,
                       final ExportCollector exportCollector) {
    this.fileExecutor = fileExecutor;
    this.cmdInterpreter = cmdInterpreter;
    this.fileAnalyzer = fileAnalyzer;
    this.filePrinter = filePrinter;
    this.exportCollector = exportCollector;
  }

  public void startCmd(String[] arguments) {
    FolderIO folderIO = null;
    FileAnalyzer fileAnalyzer = null;

    try {
      folderIO = getFolderIO(arguments);
    }
    catch (CouldNotCreateMasterFolderException e) {
      logger.error("Could not create master folder");
      System.out.println("Check your parameters...");
      return;
    }

    ExportFileDataMap exportFileDataMap;
    try {
      //Read all folder/files from filesystem
      exportFileDataMap = this.fileAnalyzer.createParsedFileMap(folderIO);
    }
    catch (InvalidInputFolders invalidInputFolders) {
      System.out.print("Invalid input folders, try again");
      logger.error("Invalid input folders, try again", invalidInputFolders);
      return;
    }

    //Print all images and videos read from inputfolders + master folder
    filePrinter.printTotalNumberOfFiles(exportFileDataMap.totalNumberOfFiles());

    //Some files may be the same, but with different flavours
    exportCollector.tagUniqueFilesWithSameName(exportFileDataMap);

    // First naive implementation of flavour merge,
    // There is big potential for a better solution here
    //exportFileDataMap.mergedFlavoursForDuplicates();  <-- automatically merged into one flavour


    // Todo
    // Trim the fileGroup -flavour
/*
    // Do this in filegroup
    String exportPath = exportForecaster.getExportPath(folderIO.masterFolder.getAbsolutePath(),
                                                       parsedFileData);
    String exportPathName; // < -- move this to after flavour merge
    exportPathName = exportForecaster.getExportPathName(folderIO.masterFolder.getAbsolutePath(),
                                                        parsedFileData);*/




    //Print all export files will look like in master folder
    // printExportPoints(exportFileDataMap);

    //Print what all export folders will look like in master folder
    printExportFolders(exportFileDataMap);

    // Print out total number of files Unique Images,
    // duplicates will get merged into master folders
    filePrinter.printTotalNumberOfDuplicates(exportFileDataMap.totalNumberOfFiles(),
                                             exportFileDataMap.getNumberOfUniqueImages(),
                                             exportFileDataMap.getNumberOfRemovableFiles());


//    filePrinter.printFolderStructure(parsedFileDataMap);
//    filePrinter.printFlavours(parsedFileDataMap);

    // Run or copy files
    // fileExecutor.copyFiles(parsedFileDataMap, folderIO);

  }

  private void printExportFolders(final ExportFileDataMap exportFileDataMap) {
    Set<String> foldersToExport = exportCollector.collectFoldersToExport(exportFileDataMap);
    filePrinter.printExportPaths(foldersToExport);
  }

  private void printExportPoints(final ExportFileDataMap exportFileDataMap) {
    Set<String> finalExportPoints = exportCollector.collectFilesToExport(exportFileDataMap);
    filePrinter.printExportDestinations(finalExportPoints);
  }

  private FolderIO getFolderIO(final String[] arguments) throws
                                                         CouldNotCreateMasterFolderException {
    FolderIO folderIO = null;
    try {
      folderIO = cmdInterpreter.getFolderIO(arguments);
    }
    catch (InvalidFolderArgumentsException e) {
      System.out.println("Wrong input/output folder arguments, try again");
      logger.error("Could not create input/output-folders, invlaid arguments", e);
    }
    catch (InvalidMasterFolderException e) {
      logger.error(e);
      boolean masterFolderCreated = cmdInterpreter.createMasterFolder(e.getMasterFolder());
      if (masterFolderCreated) {
        //lets try again
        getFolderIO(arguments);
      }
      else {
        // epic fail
        throw new CouldNotCreateMasterFolderException("Could not create master folder, " +
                                                      e.getMasterFolder());
      }
    }
    return folderIO;
  }
}

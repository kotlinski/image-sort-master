package se.kotlinski.imagesort.controller;

import com.google.inject.Inject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import se.kotlinski.imagesort.data.ExportFileData;
import se.kotlinski.imagesort.data.ParsedFileData;
import se.kotlinski.imagesort.exception.CouldNotParseDateException;
import se.kotlinski.imagesort.exception.InvalidInputFolders;
import se.kotlinski.imagesort.mapper.ExportFileDataMap;
import se.kotlinski.imagesort.model.SortSettings;
import se.kotlinski.imagesort.utils.DateToFileRenamer;
import se.kotlinski.imagesort.utils.FileDateInterpreter;
import se.kotlinski.imagesort.utils.FileDateUniqueGenerator;
import se.kotlinski.imagesort.utils.FileDescriptor;
import se.kotlinski.imagesort.utils.SortMasterFileUtil;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MediaFileParser {
  private static final Logger LOGGER = LogManager.getLogger(MediaFileParser.class);
  private final SortMasterFileUtil sortMasterFileUtil;
  private final Calendar calendar;
  private final FileDateUniqueGenerator fileDateUniqueGenerator;
  private final FileDateInterpreter fileDateInterpreter;
  private final FileDescriptor fileDescriptor;
  private final DateToFileRenamer dateToFileRenamer;
  private final ExportForecaster exportForecaster;

  @Inject
  public MediaFileParser(final SortMasterFileUtil sortMasterFileUtil,
                         final Calendar calendar,
                         final FileDateUniqueGenerator fileDateUniqueGenerator,
                         final FileDateInterpreter fileDateInterpreter,
                         final FileDescriptor fileDescriptor,
                         final DateToFileRenamer dateToFileRenamer,
                         final ExportForecaster exportForecaster) {
    this.sortMasterFileUtil = sortMasterFileUtil;
    this.calendar = calendar;
    this.fileDateUniqueGenerator = fileDateUniqueGenerator;
    this.fileDateInterpreter = fileDateInterpreter;
    this.fileDescriptor = fileDescriptor;
    this.dateToFileRenamer = dateToFileRenamer;
    this.exportForecaster = exportForecaster;
  }

  public ExportFileDataMap parseSettingsToExportData(final SortSettings sortSettings) throws
      Exception {
    if (sortSettings == null ||
        sortSettings.masterFolder == null) {
      throw new InvalidInputFolders();
    }
    if (sortMasterFileUtil.isValidFolder(sortSettings.masterFolder)) {
      return analyzeFiles(sortSettings);
    }
    else {
      throw new InvalidInputFolders();
    }
  }


  private ExportFileDataMap analyzeFiles(final SortSettings sortSettings) {
    ExportFileDataMap exportFileDataMap = new ExportFileDataMap(dateToFileRenamer);

    Map<File, List<File>> filesFromFolder = getFilesInRootFolders(sortSettings.masterFolder);

    for (Map.Entry<File, List<File>> fileListEntry : filesFromFolder.entrySet()) {
      File rootFolder = fileListEntry.getKey();
      System.out.println("");
      System.out.printf("Parsing files in: %s... %n", rootFolder);

      for (File file : fileListEntry.getValue()) {

        try {
          Date date = fileDateInterpreter.getDate(file);
          addExportFileData(sortSettings, exportFileDataMap, rootFolder, file, date);
        }
        catch (CouldNotParseDateException e) {
          System.out.println("Could not parse date for file: " + file);
          LOGGER.error("Could not parse date for file: " + file, e);
          System.out.println("ERRRRR, cant get date for file: " + file.getName());
        }
        catch (Exception e) {
          e.printStackTrace();
        }
      }
    }
    return exportFileDataMap;
  }

  private void addExportFileData(final SortSettings sortSettings,
                                 final ExportFileDataMap exportFileDataMap,
                                 final File rootFolder,
                                 final File file,
                                 final Date date) {
    boolean fileFromMasterFolder = rootFolder == sortSettings.masterFolder;
    ParsedFileData parsedFileData = getParsedFileData(file, rootFolder, date, fileFromMasterFolder);

    String exportName = exportForecaster.getFileName(parsedFileData);

    String exportExtension = exportForecaster.getFileNameExtension(parsedFileData);


    ExportFileData exportFileData = new ExportFileData(parsedFileData.uniqueId,
                                                       parsedFileData.originFile,
                                                       exportExtension,
                                                       exportName,
                                                       parsedFileData.flavour,
                                                       parsedFileData.masterFolderFile,
                                                       date);

    exportFileDataMap.addExportFileData(exportFileData);
  }

  private ParsedFileData getParsedFileData(final File file,
                                           final File parentFolder,
                                           final Date date,
                                           final boolean fileFromMasterFolder) {
    String imageIdentifier = fileDateUniqueGenerator.generateMd5(file);
    String flavour = fileDescriptor.getFlavour(parentFolder, file);
    String fileExtension = fileDescriptor.getFileExtension(file);
    String fileDateName = null;
    String datePathFlavour = null;
    try {
      fileDateName = dateToFileRenamer.formatFileDate(date, calendar);
      datePathFlavour = dateToFileRenamer.formatPathDate(date);
    }
    catch (CouldNotParseDateException e) {
      System.out.println("Could not parse when " + file.getAbsoluteFile() + " was taken");
      LOGGER.error(e);
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return new ParsedFileData(file,
                              parentFolder,
                              flavour,
                              imageIdentifier,
                              fileExtension,
                              date,
                              fileDateName,
                              datePathFlavour,
                              fileFromMasterFolder);
  }


  private Map<File, List<File>> getFilesInRootFolders(final File rootFolder) {
    Map<File, List<File>> filesFromFolder = new HashMap<>();
    List<File> files = sortMasterFileUtil.readAllFilesInFolder(rootFolder);
    filesFromFolder.put(rootFolder, files);

    return filesFromFolder;
  }

}

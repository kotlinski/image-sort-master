package se.kotlinski.imagesort.parser;

import com.google.inject.Inject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import se.kotlinski.imagesort.ExportForecaster;
import se.kotlinski.imagesort.calculator.MediaInFolderCalculator;
import se.kotlinski.imagesort.data.ExportFileData;
import se.kotlinski.imagesort.data.MediaDataFolder;
import se.kotlinski.imagesort.data.ParsedFileData;
import se.kotlinski.imagesort.exception.CouldNotParseDateException;
import se.kotlinski.imagesort.exception.InvalidInputFolders;
import se.kotlinski.imagesort.mapper.ExportFileDataMap;
import se.kotlinski.imagesort.model.SortSettings;
import se.kotlinski.imagesort.transformer.MediaFileTransformer;
import se.kotlinski.imagesort.utils.DateToFileRenamer;
import se.kotlinski.imagesort.utils.FileDateInterpreter;
import se.kotlinski.imagesort.utils.MD5Generator;
import se.kotlinski.imagesort.utils.FileDescriptor;
import se.kotlinski.imagesort.utils.MediaFileUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MediaFileParser {
  private static final Logger LOGGER = LogManager.getLogger(MediaFileParser.class);
  private final MediaFileUtil mediaFileUtil;
  private final Calendar calendar;
  private final MD5Generator MD5Generator;
  private final FileDateInterpreter fileDateInterpreter;
  private final FileDescriptor fileDescriptor;
  private final DateToFileRenamer dateToFileRenamer;
  private final ExportForecaster exportForecaster;
  private final MediaFileTransformer mediaFileTransformer;

  @Inject
  public MediaFileParser(final MediaFileUtil mediaFileUtil,
                         final Calendar calendar,
                         final MD5Generator MD5Generator,
                         final FileDateInterpreter fileDateInterpreter,
                         final FileDescriptor fileDescriptor,
                         final DateToFileRenamer dateToFileRenamer,
                         final ExportForecaster exportForecaster,
                         final MediaFileTransformer mediaFileTransformer) {
    this.mediaFileUtil = mediaFileUtil;
    this.calendar = calendar;
    this.MD5Generator = MD5Generator;
    this.fileDateInterpreter = fileDateInterpreter;
    this.fileDescriptor = fileDescriptor;
    this.dateToFileRenamer = dateToFileRenamer;
    this.exportForecaster = exportForecaster;
    this.mediaFileTransformer = mediaFileTransformer;
  }

  public Map<String, List<File>> getMediaFilesInFolder(final File masterFolder) throws Exception {
    if (!mediaFileUtil.isValidFolder(masterFolder)) {
      throw new InvalidInputFolders();
    }

    List<File> filesInMasterFolder = mediaFileUtil.getFilesInFolder(masterFolder);
    return groupFilesByMediaContent(filesInMasterFolder);
  }

  private Map<String, List<File>> groupFilesByMediaContent(final List<File> files) {

    Map<String, List<File>> fileMap = new HashMap<>();
    for (File file : files) {
      if (!mediaFileUtil.isValidMediaFile(file)) {
        //Ignore Files
        // TODO: Something smart for logging etc.
      }
      else {
        addMediaFileToMap(fileMap, file);
      }
    }
    return fileMap;
  }

  void addMediaFileToMap(final Map<String, List<File>> fileMap, final File file) {
    try {
      String fileContentIdentifier = MD5Generator.generateMd5(file);
      if (!fileMap.containsKey(fileContentIdentifier)) {
        fileMap.put(fileContentIdentifier, new ArrayList<>());
      }
      List<File> imageFileList = fileMap.get(fileContentIdentifier);
      imageFileList.add(file);
    }
    catch (Exception e) {
      //Ignore Files
    }
  }

/*
  private ExportFileDataMap analyzeFilesDeprecated(final File masterFolder) {
    ExportFileDataMap exportFileDataMap = new ExportFileDataMap(dateToFileRenamer);

    Map<File, List<File>> filesFromFolder = getFilesInRootFolders(masterFolder);

    for (Map.Entry<File, List<File>> fileListEntry : filesFromFolder.entrySet()) {
      File rootFolder = fileListEntry.getKey();
      System.out.println("");
      System.out.printf("Parsing files in: %s... %n", rootFolder);

      for (File file : fileListEntry.getValue()) {

        try {
          Date date = fileDateInterpreter.getDate(file);
          addExportFileData(masterFolder, exportFileDataMap, rootFolder, file, date);
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
  }*/

  private void addExportFileData(final SortSettings sortSettings,
                                 final ExportFileDataMap exportFileDataMap,
                                 final File rootFolder,
                                 final File file,
                                 final Date date) {
    boolean fileFromMasterFolder = rootFolder == sortSettings.masterFolder;
    ParsedFileData parsedFileData = getParsedFileData(file, rootFolder, date, fileFromMasterFolder);

    String exportName = exportForecaster.getFileName(parsedFileData);

    String exportExtension = exportForecaster.getFileNameExtension(parsedFileData);


    ExportFileData exportFileData =
        new ExportFileData(parsedFileData.uniqueId, parsedFileData.originFile, exportExtension, exportName, parsedFileData.flavour, parsedFileData.masterFolderFile, date);

    exportFileDataMap.addExportFileData(exportFileData);
  }

  private ParsedFileData getParsedFileData(final File file,
                                           final File parentFolder,
                                           final Date date,
                                           final boolean fileFromMasterFolder) {
    String imageIdentifier = MD5Generator.generateMd5(file);
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
    return new ParsedFileData(file, parentFolder, flavour, imageIdentifier, fileExtension, date, fileDateName, datePathFlavour, fileFromMasterFolder);
  }

}

package se.kotlinski.imagesort.parser;

import com.google.inject.Inject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import se.kotlinski.imagesort.DeprecatedExportForecaster;
import se.kotlinski.imagesort.data.DeprecatedExportFileData;
import se.kotlinski.imagesort.data.DeprecatedParsedFileData;
import se.kotlinski.imagesort.data.SortSettings;
import se.kotlinski.imagesort.mapper.DeprecatedExportFileDataMap;
import se.kotlinski.imagesort.utils.DateToFileRenamer;
import se.kotlinski.imagesort.utils.MD5Generator;

import java.io.File;
import java.util.Calendar;
import java.util.Date;

public class DeprecatedMediaFileExportParser {
  private static final Logger LOGGER = LogManager.getLogger(DeprecatedMediaFileExportParser.class);
  private final Calendar calendar;
  private final MD5Generator MD5Generator;
  private final DateToFileRenamer dateToFileRenamer;
  private final DeprecatedExportForecaster deprecatedExportForecaster;

  @Inject
  public DeprecatedMediaFileExportParser(final Calendar calendar,
                                         final MD5Generator MD5Generator,
                                         final DateToFileRenamer dateToFileRenamer,
                                         final DeprecatedExportForecaster deprecatedExportForecaster) {
    this.calendar = calendar;
    this.MD5Generator = MD5Generator;
    this.dateToFileRenamer = dateToFileRenamer;
    this.deprecatedExportForecaster = deprecatedExportForecaster;

  }


  private void addExportFileData(final SortSettings sortSettings,
                                 final DeprecatedExportFileDataMap deprecatedExportFileDataMap,
                                 final File rootFolder,
                                 final File file,
                                 final Date date) {
    boolean fileFromMasterFolder = rootFolder == sortSettings.masterFolder;
    DeprecatedParsedFileData deprecatedParsedFileData =
        getParsedFileData(file, rootFolder, date, fileFromMasterFolder);

    String exportName = deprecatedExportForecaster.getFileName(deprecatedParsedFileData);

    String exportExtension =
        deprecatedExportForecaster.getFileNameExtension(deprecatedParsedFileData);


    DeprecatedExportFileData deprecatedExportFileData =
        new DeprecatedExportFileData(deprecatedParsedFileData.uniqueId, deprecatedParsedFileData.originFile, exportExtension, exportName, deprecatedParsedFileData.flavour, deprecatedParsedFileData.masterFolderFile, date);

    deprecatedExportFileDataMap.addExportFileData(deprecatedExportFileData);
  }

  private DeprecatedParsedFileData getParsedFileData(final File file,
                                                     final File parentFolder,
                                                     final Date date,
                                                     final boolean fileFromMasterFolder) {
    String imageIdentifier = MD5Generator.generateMd5(file);
    //String flavour = fileDescriptor.getFlavour(parentFolder, file);
    String flavour = null;
    // String fileExtension = fileDescriptor.getFileExtension(file);
    String fileExtension = null;
    String fileDateName = null;
    String datePathFlavour = null;

    fileDateName = dateToFileRenamer.formatFileDate(date);
    datePathFlavour = dateToFileRenamer.formatPathDate(date);

    return new DeprecatedParsedFileData(file, parentFolder, flavour, imageIdentifier, fileExtension, date, fileDateName, datePathFlavour, fileFromMasterFolder);
  }

}

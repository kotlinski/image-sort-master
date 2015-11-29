package se.kotlinski.imagesort;

import com.google.inject.Inject;
import se.kotlinski.imagesort.data.DeprecatedParsedFileData;
import se.kotlinski.imagesort.utils.DateToFileRenamer;

import java.io.File;
import java.util.Date;

public class DeprecatedExportForecaster {
  private final DateToFileRenamer dateToFileRenamer;

  @Inject
  public DeprecatedExportForecaster(final DateToFileRenamer dateToFileRenamer) {
    this.dateToFileRenamer = dateToFileRenamer;
  }

  public final String getExportPathName(final String masterFolderPath,
                                  final DeprecatedParsedFileData deprecatedParsedFileData) {

    String fullExportPath = buildExportPath(masterFolderPath, deprecatedParsedFileData);
    String fileName = getFileName(deprecatedParsedFileData);
    return fullExportPath + fileName;
  }

  public final String getExportPath(final String masterFolderPath, final DeprecatedParsedFileData deprecatedParsedFileData) {

    return buildExportPath(masterFolderPath, deprecatedParsedFileData);
  }


  private String buildExportPath(final String masterFolderPath,
                                 final DeprecatedParsedFileData deprecatedParsedFileData) {
    String exportPath;
    if (deprecatedParsedFileData.fileDateName == null) {
      exportPath = deprecatedParsedFileData.flavour;
    }
    else {
      String cleanFlavour = cleanUpYearFolders(deprecatedParsedFileData.dateTaken, deprecatedParsedFileData.flavour);
      cleanFlavour = cleanUpMonthFolders(deprecatedParsedFileData.dateTaken, cleanFlavour);
      exportPath = deprecatedParsedFileData.datePathFlavour + cleanFlavour;
    }

    return String.format("%s%s%s", masterFolderPath, File.separator, exportPath);
  }


  private String cleanUpYearFolders(final Date dateTaken, final String flavour) {
    String flavourWithFileSeparator = File.separator + flavour;
    String yearFolder = dateToFileRenamer.getYearFolder(dateTaken);
    String replacedYears = flavourWithFileSeparator.replace(yearFolder, File.separator);
    return replacedYears.substring(1);
  }

  private String cleanUpMonthFolders(final Date dateTaken, final String flavour) {
    String flavourWithFileSeparator = File.separator + flavour;
    String monthFolder = dateToFileRenamer.getMonthFolder(dateTaken);
    String replacedMonths = flavourWithFileSeparator.replace(monthFolder, File.separator);
    return replacedMonths.substring(1);
  }

  public String getFileName(final DeprecatedParsedFileData deprecatedParsedFileData) {
    String fileName;
    if (deprecatedParsedFileData.dateTaken == null) {
      fileName = deprecatedParsedFileData.originFile.getName();
      fileName = fileName.replace(getFileNameExtension(deprecatedParsedFileData), "");
    }
    else {
      fileName = deprecatedParsedFileData.fileDateName;
    }
    return fileName;
  }
  public String getFileNameExtension(final DeprecatedParsedFileData deprecatedParsedFileData) {
    return deprecatedParsedFileData.fileNameExtension;
  }
}

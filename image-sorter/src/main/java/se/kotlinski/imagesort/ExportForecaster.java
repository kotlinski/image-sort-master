package se.kotlinski.imagesort;

import com.google.inject.Inject;
import se.kotlinski.imagesort.data.ParsedFileData;
import se.kotlinski.imagesort.utils.DateToFileRenamer;

import java.io.File;
import java.util.Date;

public class ExportForecaster {
  private final DateToFileRenamer dateToFileRenamer;

  @Inject
  public ExportForecaster(final DateToFileRenamer dateToFileRenamer) {
    this.dateToFileRenamer = dateToFileRenamer;
  }

  public final String getExportPathName(final String masterFolderPath,
                                  final ParsedFileData parsedFileData) {

    String fullExportPath = buildExportPath(masterFolderPath, parsedFileData);
    String fileName = getFileName(parsedFileData);
    return fullExportPath + fileName;
  }

  public final String getExportPath(final String masterFolderPath, final ParsedFileData parsedFileData) {

    return buildExportPath(masterFolderPath, parsedFileData);
  }


  private String buildExportPath(final String masterFolderPath,
                                 final ParsedFileData parsedFileData) {
    String exportPath;
    if (parsedFileData.fileDateName == null) {
      exportPath = parsedFileData.flavour;
    }
    else {
      String cleanFlavour = cleanUpYearFolders(parsedFileData.dateTaken, parsedFileData.flavour);
      cleanFlavour = cleanUpMonthFolders(parsedFileData.dateTaken, cleanFlavour);
      exportPath = parsedFileData.datePathFlavour + cleanFlavour;
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

  public String getFileName(final ParsedFileData parsedFileData) {
    String fileName;
    if (parsedFileData.dateTaken == null) {
      fileName = parsedFileData.originFile.getName();
      fileName = fileName.replace(getFileNameExtension(parsedFileData), "");
    }
    else {
      fileName = parsedFileData.fileDateName;
    }
    return fileName;
  }
  public String getFileNameExtension(final ParsedFileData parsedFileData) {
    return parsedFileData.fileNameExtension;
  }
}

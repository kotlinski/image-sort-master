package se.kotlinski.imagesort.mapper;

import com.google.inject.Inject;
import se.kotlinski.imagesort.data.ExportFileData;
import se.kotlinski.imagesort.utils.DateToFileRenamer;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

public class FileGroup {

  private final String id;
  private final Set<ExportFileData> exportFileDataList = new HashSet<>();
  private String fileGroupFlavour;
  private ArrayList<ExportFileData> arrayOfExportData;
  private String flavour;
  private final DateToFileRenamer dateToFileRenamer;
  private final Date dateTaken;

  @Inject
  public FileGroup(final String id,
                   final ExportFileData exportFileData,
                   final DateToFileRenamer dateToFileRenamer,
                   final Date dateTaken) {
    this.id = id;
    this.dateToFileRenamer = dateToFileRenamer;
    this.dateTaken = dateTaken;

    exportFileDataList.add(exportFileData);
    fileGroupFlavour = exportFileData.flavour;
  }

  public void add(ExportFileData exportFileData) {
    exportFileDataList.add(exportFileData);
    updateGroupFlavour();
  }

  private void updateGroupFlavour() {
    Set<String> splitSet = new HashSet<>();

    for (ExportFileData fileData : exportFileDataList) {
      if (fileData.isMasterFolderFile) {
        fileGroupFlavour = fileData.flavour;
        return;
      }
      else {
        System.out.println();
        System.out.println("Original: " + fileData.originFile.getAbsoluteFile());
        System.out.println("Flavour: " + fileData.flavour);
        if (fileData.flavour != null) {
          String pattern = Pattern.quote(System.getProperty("file.separator"));
          String[] splittedFlavour = fileData.flavour.split(pattern);
          Collections.addAll(splitSet, splittedFlavour); // get rid of duplicates
        }
      }
    }
    String flavour = "";
    // Make some mismatch of all flavours =D room for improvement!!!
    // Hope set is deterministic
    for (String flavourPart : splitSet) {
      flavour += flavourPart + File.separator;
    }
    if (dateTaken != null) {
      String cleanYearFlavour = cleanUpYearFolders(dateTaken, flavour);
      flavour = cleanUpMonthFolders(dateTaken, cleanYearFlavour);
    }
    fileGroupFlavour = flavour;

  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    FileGroup fileGroup = (FileGroup) o;

    return id.equals(fileGroup.id);

  }

  @Override
  public int hashCode() {
    return id.hashCode();
  }

  public ArrayList<ExportFileData> getArrayOfExportData() {
    return arrayOfExportData;
  }

  public String getFlavour() {
    return flavour;
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

}

package se.kotlinski.imagesort.mapper;

import com.google.inject.Inject;
import se.kotlinski.imagesort.data.DeprecatedExportFileData;
import se.kotlinski.imagesort.utils.DateToFileRenamer;

import java.io.File;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

public class DeprecatedFileGroup {

  private final String id;
  private final Set<DeprecatedExportFileData> deprecatedExportFileDataList = new HashSet<>();
  //not yet used variable.
  // private String fileGroupFlavour;
  // private ArrayList<DeprecatedExportFileData> arrayOfExportData;
  // private String flavour;
  private final DateToFileRenamer dateToFileRenamer;
  private final Date dateTaken;

  @Inject
  public DeprecatedFileGroup(final String id,
                             final DeprecatedExportFileData deprecatedExportFileData,
                             final DateToFileRenamer dateToFileRenamer,
                             final Date dateTaken) {
    this.id = id;
    this.dateToFileRenamer = dateToFileRenamer;
    this.dateTaken = dateTaken;

    deprecatedExportFileDataList.add(deprecatedExportFileData);
    //fileGroupFlavour = deprecatedExportFileData.flavour;
  }

  public void add(DeprecatedExportFileData deprecatedExportFileData) {
    deprecatedExportFileDataList.add(deprecatedExportFileData);
    updateGroupFlavour();
  }

  private void updateGroupFlavour() {
    Set<String> splitSet = new HashSet<>();

    int i = 0;
    for (DeprecatedExportFileData fileData : deprecatedExportFileDataList) {
      i++;
      if (fileData.isMasterFolderFile) {
//        fileGroupFlavour = fileData.flavour;
        return;
      }
      else {
        if (deprecatedExportFileDataList.size() > 1 && i == 1) {
          System.out.println("");
        }
        System.out.printf("(%s/%s)Original: %s%n",
                          i,
                          deprecatedExportFileDataList.size(),
                          fileData.originFile.getAbsoluteFile());
        // System.out.println("Flavour: " + fileData.flavour);
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
      flavour = String.format("%s%s%s", flavour, flavourPart, File.separator);
    }
    if (dateTaken != null) {
      String cleanYearFlavour = cleanUpYearFolders(dateTaken, flavour);
      flavour = cleanUpMonthFolders(dateTaken, cleanYearFlavour);
    }
 //   fileGroupFlavour = flavour;

  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    DeprecatedFileGroup deprecatedFileGroup = (DeprecatedFileGroup) o;

    return id.equals(deprecatedFileGroup.id);

  }

  @Override
  public int hashCode() {
    return id.hashCode();
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

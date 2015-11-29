package se.kotlinski.imagesort;

import se.kotlinski.imagesort.data.DeprecatedExportFileData;
import se.kotlinski.imagesort.mapper.DeprecatedExportFileDataMap;
import se.kotlinski.imagesort.mapper.DeprecatedFileGroup;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class DeprecatedExportCollector {

  public Set<String> collectFilesToExport(final DeprecatedExportFileDataMap deprecatedExportFileDataMap) {
    List<DeprecatedExportFileData> allFiles = deprecatedExportFileDataMap.getAllFiles();
    Set<String> sortedExportFolders = new TreeSet<>();

    for (DeprecatedExportFileData fileData : allFiles) {
      String exportDestination = "";
      if (fileData.appendUniqueID) {
        exportDestination = fileData.exportName + " - " + fileData.uniqueId + fileData.exportExtension;
      } else {
        exportDestination = fileData.exportName + fileData.exportExtension;
      }
      sortedExportFolders.add(exportDestination);
    }
    return sortedExportFolders;
  }


  public Set<String> collectFoldersToExport(final DeprecatedExportFileDataMap deprecatedExportFileDataMap) {

    Set<String> sortedExportFolders = new TreeSet<>();
    for (DeprecatedFileGroup deprecatedFileGroup : deprecatedExportFileDataMap.values()) {
    //  sortedExportFolders.add(deprecatedFileGroup.getFlavour());
    }
    return sortedExportFolders;
  }


  public void tagUniqueFilesWithSameName(final DeprecatedExportFileDataMap deprecatedExportFileDataMap) {
    for (String fileId : deprecatedExportFileDataMap.keySet()) {
      findOtherImagesWithSameDateName(deprecatedExportFileDataMap, fileId);
    }
  }

  private void findOtherImagesWithSameDateName(final DeprecatedExportFileDataMap deprecatedExportFileDataMap,
                                               final String fileId) {
    for (String fileId2 : deprecatedExportFileDataMap.keySet()) {
      if (!fileId2.equals(fileId)) {
     //   List<DeprecatedExportFileData> exportFileData1 = deprecatedExportFileDataMap.getFileNamesForId(fileId);
     //   List<DeprecatedExportFileData> exportFileData2 = deprecatedExportFileDataMap.getFileNamesForId(fileId2);
     //   tagDuplicateFileNames(exportFileData1, exportFileData2);
      }
    }
      //TODO continue here, search if there is another unique id with same file-name
    //Unique ID's for videos should be calculated by file size etc.
  }

  private void tagDuplicateFileNames(final List<DeprecatedExportFileData> deprecatedExportFileData1,
                                     final List<DeprecatedExportFileData> deprecatedExportFileData2) {
    for (DeprecatedExportFileData deprecatedExportFileData : deprecatedExportFileData1) {
      for (DeprecatedExportFileData fileData : deprecatedExportFileData2) {
        if (deprecatedExportFileData.exportName.equals(fileData.exportName)) {
          deprecatedExportFileData.appendUniqueID = true;
          fileData.appendUniqueID = true;
        }
      }
    }
  }

}

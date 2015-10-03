package se.kotlinski.imagesort.controller;

import se.kotlinski.imagesort.data.ExportFileData;
import se.kotlinski.imagesort.mapper.ExportFileDataMap;
import se.kotlinski.imagesort.mapper.FileGroup;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class ExportCollector {

  public Set<String> collectFilesToExport(final ExportFileDataMap exportFileDataMap) {
    List<ExportFileData> allFiles = exportFileDataMap.getAllFiles();
    Set<String> sortedExportFolders = new TreeSet<>();

    for (ExportFileData fileData : allFiles) {
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


  public Set<String> collectFoldersToExport(final ExportFileDataMap exportFileDataMap) {

    Set<String> sortedExportFolders = new TreeSet<>();
    for (FileGroup fileGroup : exportFileDataMap.values()) {
      sortedExportFolders.add(fileGroup.getFlavour());
    }
    return sortedExportFolders;
  }


  public void tagUniqueFilesWithSameName(final ExportFileDataMap exportFileDataMap) {
    for (String fileId : exportFileDataMap.keySet()) {
      findOtherImagesWithSameDateName(exportFileDataMap, fileId);
    }
  }

  private void findOtherImagesWithSameDateName(final ExportFileDataMap exportFileDataMap,
                                               final String fileId) {
    for (String fileId2 : exportFileDataMap.keySet()) {
      if (!fileId2.equals(fileId)) {
        List<ExportFileData> exportFileData1 = exportFileDataMap.getFileNamesForId(fileId);
        List<ExportFileData> exportFileData2 = exportFileDataMap.getFileNamesForId(fileId2);
        tagDuplicateFileNames(exportFileData1, exportFileData2);
      }
    }
      //TODO continue here, search if there is another unique id with same file-name
    //Unique ID's for videos should be calculated by file size etc.
  }

  private void tagDuplicateFileNames(final List<ExportFileData> exportFileData1,
                                     final List<ExportFileData> exportFileData2) {
    for (ExportFileData exportFileData : exportFileData1) {
      for (ExportFileData fileData : exportFileData2) {
        if (exportFileData.exportName.equals(fileData.exportName)) {
          exportFileData.appendUniqueID = true;
          fileData.appendUniqueID = true;
        }
      }
    }
  }

}

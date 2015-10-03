package se.kotlinski.imagesort.mapper;

import com.google.inject.Inject;
import se.kotlinski.imagesort.data.ExportFileData;
import se.kotlinski.imagesort.utils.DateToFileRenamer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ExportFileDataMap {
  private final DateToFileRenamer dateToFileRenamer;
  private Map<String, FileGroup> fileIdMap;

  @Inject
  public ExportFileDataMap(final DateToFileRenamer dateToFileRenamer) {
    this.dateToFileRenamer = dateToFileRenamer;
    fileIdMap = new HashMap<>();
  }

  public void addExportFileData(ExportFileData exportFileData) {
    FileGroup fileGroup;
    if (fileIdMap.containsKey(exportFileData.uniqueId)) {
      fileGroup = fileIdMap.get(exportFileData.uniqueId);
    }
    else {
      fileGroup = new FileGroup(exportFileData.uniqueId, exportFileData, dateToFileRenamer, exportFileData.date);
      fileIdMap.put(exportFileData.uniqueId, fileGroup);
    }
    fileGroup.add(exportFileData);
  }

  @Override
  public String toString() {
    String retString = "Files in input folder: \n";
    for (String fileGroupId : fileIdMap.keySet()) {
      retString += fileGroupId + ", including files: " + "\n";
      for (Map.Entry<String, FileGroup> stringFileGroupEntry : fileIdMap.entrySet()) {
        retString = String.format("%s\t%s\n", retString, stringFileGroupEntry.getKey());
      }
    }
    return retString;
  }
/*
  public List<FileDescriber> getRedundantFiles() {
    List<FileDescriber> imageDescribers = new ArrayList<>();
    for (String s : fileIdMap.keySet()) {
      if (fileIdMap.get(s).size() > 1) {
        imageDescribers.addAll(fileIdMap.get(s));
      }
    }
    Collections.sort(imageDescribers, fileDescriberPathComparator);
    return imageDescribers;
  }*/

  public int getNumberOfUniqueImages() {
    return fileIdMap.size();
  }

  public int size() {
    return fileIdMap.size();
  }

  public List<ExportFileData> getAllFiles() {
    ArrayList<ExportFileData> parsedFileDatas = new ArrayList<>();
    for (FileGroup fileGroup : fileIdMap.values()) {
      ArrayList<ExportFileData> exportFileDataFromFolder = fileGroup.getArrayOfExportData();
      parsedFileDatas.addAll(exportFileDataFromFolder);

    }
    return parsedFileDatas;
  }

  public Map<String, ArrayList<ExportFileData>> getGroupsOfDuplicates() {
    Map<String, ArrayList<ExportFileData>> groupsOfDuplicates = new HashMap<>();

    for (Map.Entry<String, ArrayList<ExportFileData>> stringArrayListEntry : groupsOfDuplicates.entrySet()) {
      String fileID = stringArrayListEntry.getKey();
      ArrayList<ExportFileData> exportFileData = fileIdMap.get(fileID).getArrayOfExportData();
      if (exportFileData.size() > 1) {
        groupsOfDuplicates.put(fileID, exportFileData);
      }
    }

    return groupsOfDuplicates;
  }

  public int totalNumberOfFiles() {
    return getAllFiles().size();
  }

  public int getNumberOfRemovableFiles() {
    Map<String, ArrayList<ExportFileData>> groupsOfDuplicates = getGroupsOfDuplicates();
    int numberOfRemovableFiles = 0;
    for (Map.Entry<String, ArrayList<ExportFileData>> stringArrayListEntry : groupsOfDuplicates.entrySet()) {
      ArrayList<ExportFileData> parsedFileData = groupsOfDuplicates.get(stringArrayListEntry.getKey());
      numberOfRemovableFiles += parsedFileData.size() - 1;
    }
    return numberOfRemovableFiles;
  }

  public List<ExportFileData> getFileNamesForId(final String fileId) {
    return fileIdMap.get(fileId).getArrayOfExportData();
  }

  public Set<String> keySet() {
    return fileIdMap.keySet();
  }

  public Collection<FileGroup> values() {
    return fileIdMap.values();
  }

}

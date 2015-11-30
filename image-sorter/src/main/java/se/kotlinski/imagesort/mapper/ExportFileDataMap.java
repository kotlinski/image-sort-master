package se.kotlinski.imagesort.mapper;

import com.google.inject.Inject;
import se.kotlinski.imagesort.data.DeprecatedExportFileData;
import se.kotlinski.imagesort.utils.DateToFileRenamer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ExportFileDataMap {
  private final DateToFileRenamer dateToFileRenamer;
  private Map<String, DeprecatedFileGroup> fileIdMap;

  @Inject
  public ExportFileDataMap(final DateToFileRenamer dateToFileRenamer) {
    this.dateToFileRenamer = dateToFileRenamer;
    fileIdMap = new HashMap<>();
  }

  public void addExportFileData(DeprecatedExportFileData exportFileData) {
    DeprecatedFileGroup fileGroup;
    if (fileIdMap.containsKey(exportFileData.uniqueId)) {
      fileGroup = fileIdMap.get(exportFileData.uniqueId);
    }
    else {
      fileGroup = new DeprecatedFileGroup(exportFileData.uniqueId, exportFileData, dateToFileRenamer, exportFileData.date);
      fileIdMap.put(exportFileData.uniqueId, fileGroup);
    }
    fileGroup.add(exportFileData);
  }

  @Override
  public String toString() {
    String retString = "Files in input folder: \n";
    for (String fileGroupId : fileIdMap.keySet()) {
      retString += fileGroupId + ", including files: " + "\n";
      for (Map.Entry<String, DeprecatedFileGroup> stringFileGroupEntry : fileIdMap.entrySet()) {
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

  public List<DeprecatedExportFileData> getAllFiles() {
    ArrayList<DeprecatedExportFileData> parsedFileDatas = new ArrayList<>();
    for (DeprecatedFileGroup fileGroup : fileIdMap.values()) {
      //ArrayList<DeprecatedExportFileData> exportFileDataFromFolder = fileGroup.getArrayOfExportData();
      //parsedFileDatas.addAll(exportFileDataFromFolder);
    }
    return parsedFileDatas;
  }

  public Map<String, ArrayList<DeprecatedExportFileData>> getGroupsOfDuplicates() {
    Map<String, ArrayList<DeprecatedExportFileData>> groupsOfDuplicates = new HashMap<>();

    for (Map.Entry<String, ArrayList<DeprecatedExportFileData>> stringArrayListEntry : groupsOfDuplicates.entrySet()) {
      String fileID = stringArrayListEntry.getKey();
      //ArrayList<ExportFileData> exportFileData = fileIdMap.get(fileID).getArrayOfExportData();
      //if (exportFileData.size() > 1) {
        //groupsOfDuplicates.put(fileID, exportFileData);
      //}
    }

    return groupsOfDuplicates;
  }

  public int totalNumberOfFiles() {
    return getAllFiles().size();
  }

  public int getNumberOfRemovableFiles() {
    Map<String, ArrayList<DeprecatedExportFileData>> groupsOfDuplicates = getGroupsOfDuplicates();
    int numberOfRemovableFiles = 0;
    for (Map.Entry<String, ArrayList<DeprecatedExportFileData>> stringArrayListEntry : groupsOfDuplicates.entrySet()) {
      ArrayList<DeprecatedExportFileData> parsedFileData = groupsOfDuplicates.get(stringArrayListEntry.getKey());
      numberOfRemovableFiles += parsedFileData.size() - 1;
    }
    return numberOfRemovableFiles;
  }

/*  public List<DeprecatedExportFileData> getFileNamesForId(final String fileId) {
    return fileIdMap.get(fileId).getArrayOfExportData();
  }*/

  public Set<String> keySet() {
    return fileIdMap.keySet();
  }

  public Collection<DeprecatedFileGroup> values() {
    return fileIdMap.values();
  }

}

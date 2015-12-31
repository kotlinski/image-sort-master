package se.kotlinski.imagesort.forecaster;

import com.google.inject.Inject;
import se.kotlinski.imagesort.data.MediaFileDataHash;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MediaFilesOutputForecaster {

  private final MediaFileForecaster mediaFileForecaster;

  @Inject
  public MediaFilesOutputForecaster(final MediaFileForecaster mediaFileForecaster) {
    this.mediaFileForecaster = mediaFileForecaster;
  }

  public Map<String, List<File>> calculateOutputDestinations(final Map<MediaFileDataHash, List<File>> mediaFilesInFolder,
                                                             final String masterFolderPath) {
    Map<String, List<File>> exportFiles = new HashMap<>();

    for (List<File> files : mediaFilesInFolder.values()) {
      for (File file : files) {
        addMediaFileToMap(exportFiles, file, masterFolderPath);
      }
    }
    return exportFiles;
  }

  void addMediaFileToMap(final Map<String, List<File>> fileMap,
                         final File file,
                         final String masterFolderPath) {
    String outputDestination;
    outputDestination = mediaFileForecaster.forecastOutputDestination(file, masterFolderPath);

    if (!fileMap.containsKey(outputDestination)) {
      fileMap.put(outputDestination, new ArrayList<>());
    }
    List<File> imageFileList = fileMap.get(outputDestination);
    imageFileList.add(file);
  }
}

package se.kotlinski.imagesort.mapper;

import com.google.inject.Inject;
import se.kotlinski.imagesort.forecaster.MediaFileOutputForecaster;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OutputMapper {

  private final MediaFileOutputForecaster mediaFileOutputForecaster;

  @Inject
  public OutputMapper(final MediaFileOutputForecaster mediaFileOutputForecaster) {
    this.mediaFileOutputForecaster = mediaFileOutputForecaster;
  }

  public Map<String, List<File>> calculateOutputDestinations(final File masterFolderFile,
                                                             final List<File> mediaFiles) {
    Map<String, List<File>> exportFiles = new HashMap<>();

    for (File file : mediaFiles) {
      addMediaFileToMap(exportFiles, masterFolderFile, file);
    }

    return exportFiles;
  }

  void addMediaFileToMap(final Map<String, List<File>> fileMap,
                         final File masterFolderFile,
                         final File file) {
    String outputDestination;
    outputDestination = mediaFileOutputForecaster.forecastOutputDestination(masterFolderFile,
                                                                            file);

    if (!fileMap.containsKey(outputDestination)) {
      fileMap.put(outputDestination, new ArrayList<>());
    }
    List<File> imageFileList = fileMap.get(outputDestination);
    imageFileList.add(file);
  }
}

package se.kotlinski.imagesort.statistics;

import se.kotlinski.imagesort.data.MediaFileDataHash;
import se.kotlinski.imagesort.data.MediaFileDataInFolder;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MediaFilesInFolderCalculator {

  public final MediaFileDataInFolder calculateMediaFileDataInFolder(final Map<MediaFileDataHash, List<File>> filesByMediaContent) {
    final Map<MediaFileDataHash, List<File>> filesWithDuplicates = new HashMap<>();
    int totalNumberOfFiles = 0;
    int numberOfUniqueFiles = filesByMediaContent.size();

    for (MediaFileDataHash mediaFileDataHash : filesByMediaContent.keySet()) {
      List<File> files = filesByMediaContent.get(mediaFileDataHash);
      totalNumberOfFiles += files.size();
      if (files.size() > 1) {
        filesWithDuplicates.put(mediaFileDataHash, files);
      }
    }

    return new MediaFileDataInFolder(numberOfUniqueFiles, filesWithDuplicates, totalNumberOfFiles);
  }

}

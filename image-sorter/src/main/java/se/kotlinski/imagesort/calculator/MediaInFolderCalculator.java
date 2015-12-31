package se.kotlinski.imagesort.calculator;

import se.kotlinski.imagesort.data.MediaFileDataHash;
import se.kotlinski.imagesort.data.MediaFileDataInFolder;

import java.io.File;
import java.util.List;
import java.util.Map;

public class MediaInFolderCalculator {

  public final MediaFileDataInFolder calculateMediaFileDataInFolder(final Map<MediaFileDataHash, List<File>> filesByMediaContent) {
    int numberOfFilesWithDuplicates = 0;
    int totalNumberOfFiles = 0;
    int numberOfUniqueFiles = filesByMediaContent.size();

    for (MediaFileDataHash mediaFileDataHash : filesByMediaContent.keySet()) {
      List<File> files = filesByMediaContent.get(mediaFileDataHash);
      totalNumberOfFiles += files.size();
      if (files.size() > 1) {
        numberOfFilesWithDuplicates++;
      }
    }

    return new MediaFileDataInFolder(numberOfUniqueFiles, numberOfFilesWithDuplicates, totalNumberOfFiles);
  }
}

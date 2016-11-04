package se.kotlinski.imagesort.statistics;

import se.kotlinski.imagesort.data.PixelHash;
import se.kotlinski.imagesort.data.MediaFileDataInFolder;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MediaFilesInFolderCalculator {

  public final MediaFileDataInFolder calculateMediaFileDataInFolder(final Map<PixelHash, List<File>> filesByMediaContent) {
    final Map<PixelHash, List<File>> filesWithDuplicates = new HashMap<>();
    int totalNumberOfFiles = 0;
    int numberOfUniqueFiles = filesByMediaContent.size();

    for (PixelHash pixelHash : filesByMediaContent.keySet()) {
      List<File> files = filesByMediaContent.get(pixelHash);
      totalNumberOfFiles += files.size();
      if (files.size() > 1) {
        filesWithDuplicates.put(pixelHash, files);
      }
    }

    return new MediaFileDataInFolder(numberOfUniqueFiles, filesWithDuplicates, totalNumberOfFiles);
  }

}

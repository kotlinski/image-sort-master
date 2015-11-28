package se.kotlinski.imagesort.calculator;

import se.kotlinski.imagesort.data.MediaDataFolder;

import java.io.File;
import java.util.List;
import java.util.Map;

public class MediaInFolderCalculator {

  public MediaDataFolder calculateMediaFileDataInFolder(final Map<String, List<File>> filesByMediaContent) {
    int numberOfFilesWithDuplicates = 0;

    for (String s : filesByMediaContent.keySet()) {
      List<File> files = filesByMediaContent.get(s);
      if (files.size() > 1) {
        numberOfFilesWithDuplicates++;
      }
    }

    return new MediaDataFolder(numberOfFilesWithDuplicates);
  }
}

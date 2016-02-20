package se.kotlinski.imagesort.data;

import java.io.File;
import java.util.List;
import java.util.Map;

public class MediaFileDataInFolder {
  public final int numberOfUniqueFiles;
  public final Map<MediaFileDataHash, List<File>> filesWithDuplicates;
  public final int totalNumberOfFiles;

  public MediaFileDataInFolder(final int numberOfUniqueFiles,
                               final Map<MediaFileDataHash, List<File>> filesWithDuplicates,
                               final int totalNumberOfFiles) {
    this.numberOfUniqueFiles = numberOfUniqueFiles;
    this.filesWithDuplicates = filesWithDuplicates;
    this.totalNumberOfFiles = totalNumberOfFiles;
  }

  @Override
  public String toString() {
    return "Number of unique files: " + numberOfUniqueFiles + "\n" +
           "Number of duplicates: " + filesWithDuplicates.size() + "\n" +
           "Total number of files: " + totalNumberOfFiles;
  }


  public String printAllDuplicatedFiles() {
    String returnString = "[\n";
    for (MediaFileDataHash mediaFileDataHash : filesWithDuplicates.keySet()) {
      List<File> files = filesWithDuplicates.get(mediaFileDataHash);
      returnString += "  {\n";
      for (File file : files) {
        returnString += "    " + file.toString() + ",\n";
      }
      returnString += "  },\n";
    }
    returnString += "]\n";


    return returnString;
  }
}

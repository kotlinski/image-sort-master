package se.kotlinski.imagesort.data;

public class MediaFileDataInFolder {
  public final int numberOfUniqueFiles;
  public final int numberOfMediaFilesWithDuplicates;
  public final int totalNumberOfFiles;

  public MediaFileDataInFolder(final int numberOfUniqueFiles,
                               final int numberOfFilesWithDuplicates,
                               final int totalNumberOfFiles) {
    this.numberOfUniqueFiles = numberOfUniqueFiles;
    this.numberOfMediaFilesWithDuplicates = numberOfFilesWithDuplicates;
    this.totalNumberOfFiles = totalNumberOfFiles;
  }

  @Override
  public String toString() {
    return "MediaFileDataInFolder{" +
           "numberOfUniqueFiles=" + numberOfUniqueFiles +
           ", numberOfMediaFilesWithDuplicates=" + numberOfMediaFilesWithDuplicates +
           ", totalNumberOfFiles=" + totalNumberOfFiles +
           '}';
  }
}

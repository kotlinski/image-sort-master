package se.kotlinski.imagesort.data;

public class MediaDataFolder {
  private final int numberOfFilesWithDuplicates;

  public MediaDataFolder(final int numberOfFilesWithDuplicates) {
    this.numberOfFilesWithDuplicates = numberOfFilesWithDuplicates;
  }

  @Override
  public String toString() {
    return "MediaDataFolder{" +
           "numberOfFilesWithDuplicates=" + numberOfFilesWithDuplicates +
           '}';
  }
}

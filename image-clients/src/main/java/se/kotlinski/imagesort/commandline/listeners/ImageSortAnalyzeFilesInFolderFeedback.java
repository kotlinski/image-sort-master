package se.kotlinski.imagesort.commandline.listeners;

import se.kotlinski.imagesort.data.MediaFileDataHash;
import se.kotlinski.imagesort.data.MediaFileDataInFolder;
import se.kotlinski.imagesort.main.ClientAnalyzeFilesInFolderInterface;
import se.kotlinski.imagesort.statistics.MediaFilesInFolderCalculator;

import java.io.File;
import java.util.List;
import java.util.Map;

public class ImageSortAnalyzeFilesInFolderFeedback implements ClientAnalyzeFilesInFolderInterface {

  @Override
  public void startGroupFilesByContent() {
    System.out.println();
    System.out.println("Grouping files by content...");
  }

  @Override
  public void groupFilesByContentProgress(final int total, final int progress) {
    float percentageProgress = progress * 1f / total * 1f;

    System.out.print("Current progress: " +
                     (int) (percentageProgress * 100f) +
                     "%, " +
                     progress +
                     " of " +
                     total +
                     "\r");
  }

  @Override
  public void masterFolderSuccessfulParsed(final Map<MediaFileDataHash, List<File>> mediaFilesInFolder) {
    System.out.println();
    System.out.println("Folder data stats: ");
    printMediaFilesInFolderData(mediaFilesInFolder);
    System.out.println();
  }

  private void printMediaFilesInFolderData(final Map<MediaFileDataHash, List<File>> mediaFilesInFolder) {
    MediaFilesInFolderCalculator mediaFilesInFolderCalculator = new MediaFilesInFolderCalculator(); // TODO: inject
    MediaFileDataInFolder mediaDataBeforeExecution;
    mediaDataBeforeExecution = mediaFilesInFolderCalculator.calculateMediaFileDataInFolder(
        mediaFilesInFolder);

    System.out.println(mediaDataBeforeExecution.toString());
  }

}

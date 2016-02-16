package se.kotlinski.imagesort.commandline.listeners;

import se.kotlinski.imagesort.data.MediaFileDataHash;
import se.kotlinski.imagesort.data.MediaFileDataInFolder;
import se.kotlinski.imagesort.data.RelativeMediaFolderOutput;
import se.kotlinski.imagesort.feedback.MoveFeedbackInterface;
import se.kotlinski.imagesort.statistics.MediaFilesInFolderCalculator;

import java.io.File;
import java.util.List;
import java.util.Map;

public class ImageSortMoveFeedbackProgressFeedback implements MoveFeedbackInterface {


  @Override
  public void successfulResolvedOutputConflicts(final Map<List<File>, RelativeMediaFolderOutput> resolvedFilesToOutputMap) {
    System.out.println("");
    System.out.println("Resolved output conflicts!");
    System.out.println("");
  }


  @Override
  public void startResolvingConflicts() {
    System.out.println("Resolving conflicts...");
    System.out.println();
  }


  @Override
  public void startMovingFiles() {
    System.out.println("Preparing files to be renamed and moved to new folder(s)...");
    System.out.println("");
  }


  @Override
  public void skippingFilesToMove(final int skippedFiles, final int filesToMove) {
    System.out.print(filesToMove +
                     " files will be renamed and/or moved. Skipping " +
                     skippedFiles +
                     " files." +
                     "\r");
  }

  @Override
  public void prepareMovePhase() {
    System.out.println("");
    System.out.println("Moving files...");
  }

  @Override
  public void copyingFile(final int numberOfCopiedFiles, final int size) {
    float percentageProgress = numberOfCopiedFiles * 1f / size * 1f;

    System.out.print("Current progress: " +
                     (int) (percentageProgress * 100f) +
                     "%, " +
                     numberOfCopiedFiles +
                     " of " +
                     size +
                     "\r");

  }

  @Override
  public void deletingFile(final int filesDeleted, final int size) {
    float percentageProgress = filesDeleted * 1f / size * 1f;

    System.out.print("Current progress: " +
                     (int) (percentageProgress * 100f) +
                     "%, " +
                     filesDeleted +
                     " of " +
                     size +
                     "\r");
  }

  @Override
  public void movePhaseComplete() {
    System.out.println("Done.");
  }


  private void printMediaFilesInFolderData(final Map<MediaFileDataHash, List<File>> mediaFilesInFolder) {
    MediaFilesInFolderCalculator mediaFilesInFolderCalculator = new MediaFilesInFolderCalculator(); // TODO: inject
    MediaFileDataInFolder mediaDataBeforeExecution;
    mediaDataBeforeExecution = mediaFilesInFolderCalculator.calculateMediaFileDataInFolder(
        mediaFilesInFolder);

    System.out.println(mediaDataBeforeExecution.toString());
  }
}

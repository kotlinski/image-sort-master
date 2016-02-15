package se.kotlinski.imagesort.executor;

import org.apache.commons.io.FileUtils;
import se.kotlinski.imagesort.data.RelativeMediaFolderOutput;
import se.kotlinski.imagesort.feedback.MoveFeedbackInterface;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class FileMover {

  public void moveFilesToNewDestination(final MoveFeedbackInterface moveFeedback,
                                        final File masterFolderFile,
                                        final Map<List<File>, RelativeMediaFolderOutput> resolvedFilesToOutputMap) {

    moveFeedback.prepareMovePhase();

    copyFilesToNewDestinations(moveFeedback, masterFolderFile, resolvedFilesToOutputMap);

    cleanUpOldFiles(moveFeedback, masterFolderFile, resolvedFilesToOutputMap);

    deleteEmptyDirectories(FileUtils.getFile(masterFolderFile));
  }


  private void cleanUpOldFiles(final MoveFeedbackInterface moveFeedback,
                               final File masterFolderFile,
                               final Map<List<File>, RelativeMediaFolderOutput> resolvedFilesToOutputMap) {
    Set<File> filesToNotCleanUp = new HashSet<>();
    for (RelativeMediaFolderOutput relativeOutputPath : resolvedFilesToOutputMap.values()) {
      String newOutPutPath = masterFolderFile.getAbsolutePath() + relativeOutputPath;
      filesToNotCleanUp.add(FileUtils.getFile(newOutPutPath));
    }

    int deletedFiles = 0;
    for (List<File> files : resolvedFilesToOutputMap.keySet()) {
      moveFeedback.deletingFile(deletedFiles++, resolvedFilesToOutputMap.size());
      for (File file : files) {
        if (!filesToNotCleanUp.contains(file)) {
          deleteFile(file);
        }
      }
    }
  }

  private void copyFilesToNewDestinations(final MoveFeedbackInterface moveFeedback,
                                          final File masterFolderFile,
                                          final Map<List<File>, RelativeMediaFolderOutput> resolvedFilesToOutputMap) {
    int numberOfCopiedFiles = 0;
    for (Map.Entry<List<File>, RelativeMediaFolderOutput> fileListToNewOutput : resolvedFilesToOutputMap.entrySet()) {
      moveFeedback.copyingFile(numberOfCopiedFiles++, resolvedFilesToOutputMap.size());

      List<File> fileList = fileListToNewOutput.getKey();
      String newOutputPath = masterFolderFile.getAbsolutePath() + fileListToNewOutput.getValue();
      if (fileList.size() > 0) {
        File fileToCopy = fileList.get(0);
        copyFileToNewDestination(fileToCopy, newOutputPath);
      }
    }
  }

  private void deleteEmptyDirectories(final File folderPath) {
    File[] files = folderPath.listFiles();
    if (files != null && files.length == 0) {
      deleteFolder(folderPath);
      return;
    }

    for (File file : files) {
      if (file.isDirectory()) {
        deleteEmptyDirectories(file);
      }
    }

  }

  private void deleteFolder(final File folderPath) {
    try {
      FileUtils.deleteDirectory(folderPath);
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void deleteFile(final File file) {
    deleteFile(file, 0);
  }

  private void deleteFile(final File file, int attempts) {

    if (attempts > 5) {
      return;
    }
    try {
      FileUtils.forceDelete(file);
    }
    catch (IOException e) {
      System.gc();//Added this part
      try {
        Thread.sleep(1500);
      }
      catch (InterruptedException e2) {
        e2.printStackTrace();
      }
    }
  }

  private void copyFileToNewDestination(final File fileToCopy, final String newOutput) {
    try {
      File toFile = FileUtils.getFile(newOutput);
      FileUtils.copyFile(fileToCopy, toFile, true);
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }
}

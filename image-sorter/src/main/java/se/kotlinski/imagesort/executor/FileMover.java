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

  public void moveFilesToNewDestination(final MoveFeedbackInterface moveFeedbackInterface,
                                        final File masterFolderFile,
                                        final Map<List<File>, RelativeMediaFolderOutput> resolvedFilesToOutputMap) {

    moveFeedbackInterface.prepareMovePhase();

    copyFilesToNewDestinations(masterFolderFile, resolvedFilesToOutputMap);

    cleanUpOldFiles(masterFolderFile, resolvedFilesToOutputMap);

    deleteEmptyDirectories(FileUtils.getFile(masterFolderFile));
  }


  private void cleanUpOldFiles(final File masterFolderFile,
                               final Map<List<File>, RelativeMediaFolderOutput> resolvedFilesToOutputMap) {
    Set<File> filesToNotCleanUp = new HashSet<>();
    for (RelativeMediaFolderOutput relativeOutputPath : resolvedFilesToOutputMap.values()) {
      String newOutPutPath = masterFolderFile.getAbsolutePath() + relativeOutputPath;
      filesToNotCleanUp.add(FileUtils.getFile(newOutPutPath));
    }

    for (List<File> files : resolvedFilesToOutputMap.keySet()) {
      for (File file : files) {
        if (!filesToNotCleanUp.contains(file)) {
          deleteFile(file);
        }
      }
    }
  }

  private void copyFilesToNewDestinations(final File masterFolderFile,
                                          final Map<List<File>, RelativeMediaFolderOutput> resolvedFilesToOutputMap) {
    for (Map.Entry<List<File>, RelativeMediaFolderOutput> fileListToNewOutput : resolvedFilesToOutputMap.entrySet()) {
      List<File> fileList = fileListToNewOutput.getKey();

      String newOutputPath = masterFolderFile.getAbsolutePath() + fileListToNewOutput.getValue();
      if (fileList.size() > 0) {
        File fileToCopy = fileList.get(0);
        copyFileToNewDestionation(fileToCopy, newOutputPath);
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

  private void copyFileToNewDestionation(final File fileToCopy, final String newOutput) {
    try {
      File toFile = FileUtils.getFile(newOutput);
      FileUtils.copyFile(fileToCopy, toFile, true);
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }
}

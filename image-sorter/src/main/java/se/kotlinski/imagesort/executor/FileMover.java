package se.kotlinski.imagesort.executor;

import org.apache.commons.io.FileUtils;
import se.kotlinski.imagesort.utils.MediaFileUtil;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class FileMover {

  private final MediaFileUtil mediaFileUtil;

  public FileMover(final MediaFileUtil mediaFileUtil) {
    this.mediaFileUtil = mediaFileUtil;
  }

  public void moveFilesToNewDestionation(final Map<List<File>, String> resolvedFilesToOutputMap,
                                         final String masterFolderPath) {

    skipFilesAlreadyNamedAsOutput(resolvedFilesToOutputMap, masterFolderPath);


    //TODO: run recusivley, Move to Conflict resolver.
    boolean noMoreConflicts = resolveOutputConflictsWithOldFiles(resolvedFilesToOutputMap,
                                                                 masterFolderPath);

    copyFilesToNewDestinations(resolvedFilesToOutputMap, masterFolderPath);

    cleanUpOldFiles(resolvedFilesToOutputMap, masterFolderPath);

    deleteEmptyDirectories(FileUtils.getFile(masterFolderPath));
  }

  private boolean resolveOutputConflictsWithOldFiles(final Map<List<File>, String> resolvedFilesToOutputMap,
                                                     final String masterFolderPath) {

    for (List<File> files : resolvedFilesToOutputMap.keySet()) {
      for (File file : files) {
        boolean conflictFound = checkIfFileConflictsWithOutputs(resolvedFilesToOutputMap, file, masterFolderPath);
        if (conflictFound) {
          String randomString = new BigInteger(130, new SecureRandom()).toString(32);
          String newAbsolutePath = mediaFileUtil.appendToFileName(file.getAbsolutePath(), randomString);
          try {
            FileUtils.moveFile(file, FileUtils.getFile(newAbsolutePath));
          }
          catch (IOException e) {
            e.printStackTrace();
          }
          file = FileUtils.getFile(newAbsolutePath);
        }
      }
    }


    return false;
  }

  private boolean checkIfFileConflictsWithOutputs(final Map<List<File>, String> resolvedFilesToOutputMap,
                                                  final File file,
                                                  final String masterFolder) {

    for (Map.Entry<List<File>, String> filesOutputEntry : resolvedFilesToOutputMap.entrySet()) {
      if (checkIfFileIsInList(file, filesOutputEntry)) {
        return false;
      }
      String absolutOutput = masterFolder + filesOutputEntry.getValue();
      if (file.getAbsolutePath().equals(absolutOutput)) {
        return true;
      }
    }
    return false;
  }

  private boolean checkIfFileIsInList(final File file,
                                      final Map.Entry<List<File>, String> filesOutputEntry) {
    List<File> files = filesOutputEntry.getKey();
    for (File fileInEntry : files) {
      if (file.equals(fileInEntry)) {
        return true;
      }
    }
    return false;
  }


  private void skipFilesAlreadyNamedAsOutput(final Map<List<File>, String> resolvedFilesToOutputMap,
                                             final String masterFolderPath) {
    for (Map.Entry<List<File>, String> listStringEntry : resolvedFilesToOutputMap.entrySet()) {

      List<File> fileList = listStringEntry.getKey();

      for (Iterator<File> it = fileList.iterator(); it.hasNext(); ) {
        File file = it.next();

        String folderPath = masterFolderPath + listStringEntry.getValue();
        if (file.getAbsolutePath().equals(folderPath)) {
          it.remove();
        }
      }
    }
  }

  private void cleanUpOldFiles(final Map<List<File>, String> resolvedFilesToOutputMap,
                               final String masterFolderPath) {
    Set<File> filesToNotCleanUp = new HashSet<>();
    for (String relativeOutputPath : resolvedFilesToOutputMap.values()) {
      String newOutPutPath = masterFolderPath + relativeOutputPath;
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

  private void copyFilesToNewDestinations(final Map<List<File>, String> resolvedFilesToOutputMap,
                                          final String masterFolderPath) {
    for (Map.Entry<List<File>, String> fileListToNewOutput : resolvedFilesToOutputMap.entrySet()) {
      List<File> fileList = fileListToNewOutput.getKey();

      String newOutputPath = masterFolderPath + fileListToNewOutput.getValue();
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
    try {
      FileUtils.forceDelete(file);
    }
    catch (IOException e) {
      e.printStackTrace();
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

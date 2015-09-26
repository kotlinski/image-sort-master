package se.kotlinski.imagesort.controller;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import se.kotlinski.imagesort.mapper.ExportFileDataMap;
import se.kotlinski.imagesort.model.FileCopyReport;
import se.kotlinski.imagesort.model.FileDescriber;
import se.kotlinski.imagesort.model.SortSettings;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class FileExecutor {

  private static final Logger LOGGER = LogManager.getLogger(FileExecutor.class);

  public FileCopyReport copyFiles(ExportFileDataMap exportFileDataMap, SortSettings sortSettings) {
/*    Map<String, String> copiedFiles = new HashMap<>();

    ArrayList<FileDescriber> uniqueFileDescribers = parsedFileDataMap.getUniqueImageDescribers();
    FileCopyReport fileCopyReport = new FileCopyReport();
    for (FileDescriber uniqueFileDescriber : uniqueFileDescribers) {
      String masterRoot = sortSettings.masterFolder.getAbsolutePath() + File.separator;
      String newFolder = getFolderStructure(uniqueFileDescriber);
      String fullPath = masterRoot + newFolder;
      FileUtils.mkdir(fullPath);

      String filePathName = getFileName(uniqueFileDescriber, fullPath, false);
      boolean appendMD5 = shouldAppendMD5(copiedFiles, uniqueFileDescriber, filePathName);
      LOGGER.debug("AppendMD5" + appendMD5);
      filePathName = getFileName(uniqueFileDescriber, fullPath, appendMD5);

      boolean success = copyFileToNewFolder(uniqueFileDescriber, filePathName);
      if (success) {
        fileCopyReport.fileCopySuccess();
      }
      else {
        fileCopyReport.fileCopyFailed(uniqueFileDescriber);
      }

      copiedFiles.put(filePathName, uniqueFileDescriber.getMd5());

    }*/
    return null;
  }

  private boolean shouldAppendMD5(final Map<String, String> copiedFiles,
                                  final FileDescriber uniqueFileDescriber,
                                  final String filePathName) {
    return copiedFiles.containsKey(filePathName) &&
           !copiedFiles.containsValue(uniqueFileDescriber.getMd5());
  }

  private String getFileName(final FileDescriber uniqueFileDescriber,
                             final String fullPath,
                             final boolean appendMD5) {
    String newFileName = null;
    //newFileName = fullPath + File.separator + uniqueFileDescriber.getDateFilename(appendMD5);
    return newFileName;
  }

  private boolean copyFileToNewFolder(final FileDescriber uniqueFileDescriber,
                                      final String newFolder) {
    try {
      createNewFile(uniqueFileDescriber.getFile(), newFolder);
      return true;
    }
    catch (IOException e) {
      LOGGER.error("Could not copy file: " + uniqueFileDescriber.getFile(), e);
    }
    return false;
  }

  public void createNewFile(final File oldFile, final String pathname) throws IOException {
    File file = new File(pathname);
    boolean fileCreated = file.createNewFile();
    if (fileCreated) {
      FileUtils.copyFile(oldFile, file);
    }
    else {
      LOGGER.error("\ncouldn't move File: " + oldFile.getName());
      LOGGER.error("\ncouldn't move File: " + file.getAbsolutePath());
    }
  }

  private String getFolderStructure(final FileDescriber uniqueFileDescriber) {
    String newFolder = null;
    /*try {
      newFolder += uniqueFileDescriber.getRenamedFilePath();
    }
    catch (CouldNotParseDateException e) {
      newFolder += "other";
      LOGGER.error("Could not parse date: " + uniqueFileDescriber.getFile(), e);
    }*/
    //newFolder += uniqueFileDescriber.getFlavour();
    return newFolder;
  }
}

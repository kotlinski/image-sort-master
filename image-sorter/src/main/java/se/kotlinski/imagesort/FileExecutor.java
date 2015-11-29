package se.kotlinski.imagesort;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import se.kotlinski.imagesort.mapper.DeprecatedExportFileDataMap;
import se.kotlinski.imagesort.model.DeprecatedFileCopyReport;
import se.kotlinski.imagesort.model.DeprecatedFileDescriber;
import se.kotlinski.imagesort.data.SortSettings;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class FileExecutor {

  private static final Logger LOGGER = LogManager.getLogger(FileExecutor.class);

  public DeprecatedFileCopyReport copyFiles(DeprecatedExportFileDataMap deprecatedExportFileDataMap, SortSettings sortSettings) {
/*    Map<String, String> copiedFiles = new HashMap<>();

    ArrayList<DeprecatedFileDescriber> uniqueFileDescribers = parsedFileDataMap.getUniqueImageDescribers();
    DeprecatedFileCopyReport fileCopyReport = new DeprecatedFileCopyReport();
    for (DeprecatedFileDescriber uniqueFileDescriber : uniqueFileDescribers) {
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
                                  final DeprecatedFileDescriber uniqueDeprecatedFileDescriber,
                                  final String filePathName) {
    return copiedFiles.containsKey(filePathName) &&
           !copiedFiles.containsValue(uniqueDeprecatedFileDescriber.getMd5());
  }

  private String getFileName(final DeprecatedFileDescriber uniqueDeprecatedFileDescriber,
                             final String fullPath,
                             final boolean appendMD5) {
    String newFileName = null;
    //newFileName = fullPath + File.separator + uniqueDeprecatedFileDescriber.getDateFilename(appendMD5);
    return newFileName;
  }

  private boolean copyFileToNewFolder(final DeprecatedFileDescriber uniqueDeprecatedFileDescriber,
                                      final String newFolder) {
    try {
      createNewFile(uniqueDeprecatedFileDescriber.getFile(), newFolder);
      return true;
    }
    catch (IOException e) {
      LOGGER.error("Could not copy file: " + uniqueDeprecatedFileDescriber.getFile(), e);
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

  private String getFolderStructure(final DeprecatedFileDescriber uniqueDeprecatedFileDescriber) {
    String newFolder = null;
    /*try {
      newFolder += uniqueDeprecatedFileDescriber.getRenamedFilePath();
    }
    catch (CouldNotParseDateException e) {
      newFolder += "other";
      LOGGER.error("Could not parse date: " + uniqueDeprecatedFileDescriber.getFile(), e);
    }*/
    //newFolder += uniqueDeprecatedFileDescriber.getFlavour();
    return newFolder;
  }
}

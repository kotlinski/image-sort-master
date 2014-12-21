package se.kotlinski.imagesort.controller;

import com.google.inject.Inject;
import org.apache.commons.io.FileUtils;
import se.kotlinski.imagesort.exception.CouldNotParseDateException;
import se.kotlinski.imagesort.mapper.ImageMapper;
import se.kotlinski.imagesort.model.FileCopyReport;
import se.kotlinski.imagesort.model.FileDescriber;
import se.kotlinski.imagesort.model.FolderIO;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Date: 2014-10-19
 *
 * @author Simon Kotlinski
 */
public class FileExecutor implements IFileExecutor{

  @Inject
  public FileExecutor() {
  }

  public FileCopyReport copyFiles(ImageMapper imageMapper, FolderIO folderIO) {
    Map<String, String> copiedFiles = new HashMap<String, String>();

    ArrayList<FileDescriber> uniqueFileDescribers = imageMapper.getUniqueImageDescribers();
    FileCopyReport fileCopyReport = new FileCopyReport();
    for (FileDescriber uniqueFileDescriber : uniqueFileDescribers) {
      String masterRoot = folderIO.masterFolder.getAbsolutePath() + File.separator;
      String newFolder = getFolderStructure(uniqueFileDescriber);
      String fullPath = masterRoot + newFolder;
      FileUtils.mkdir(fullPath);

      String filePathName = getFileName(uniqueFileDescriber, fullPath, false);
      boolean appendMD5 = shouldAppendMD5(copiedFiles, uniqueFileDescriber, filePathName);
      System.err.println("AppendMD5" + appendMD5);
      filePathName = getFileName(uniqueFileDescriber, fullPath, appendMD5);

      boolean success = copyFileToNewFolder(uniqueFileDescriber, filePathName);
      if (success) {
        fileCopyReport.fileCopySuccess();
      }
      else {
        fileCopyReport.fileCopyFailed(uniqueFileDescriber);
      }

      copiedFiles.put(filePathName, uniqueFileDescriber.getMd5());

    }
    return fileCopyReport;
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
    String newFileName;
    try {
      newFileName = fullPath + File.separator + uniqueFileDescriber.getDateFilename(appendMD5);
    }
    catch (CouldNotParseDateException e) {
      newFileName = fullPath + File.separator + uniqueFileDescriber.getOriginalFileName();
    }
    return newFileName;
  }

  private boolean copyFileToNewFolder(final FileDescriber uniqueFileDescriber,
                                      final String newFolder) {
    try {
      createNewFile(uniqueFileDescriber.getFile(), newFolder);
      return true;
    }
    catch (IOException | CouldNotParseDateException e) {
      e.printStackTrace();
    }
    return false;
  }

  public void createNewFile(final File oldFile, final String pathname) throws
                                                                       IOException,
                                                                       CouldNotParseDateException {
    File file = new File(pathname);
    boolean fileCreated = file.createNewFile();
    if (fileCreated) {
      FileUtils.copyFile(oldFile, file);
    }
    else {
      System.err.println("\ncouldn't move File: " + oldFile.getName());
      System.err.println("\ncouldn't move File: " + file.getAbsolutePath());
    }
  }

  private String getFolderStructure(final FileDescriber uniqueFileDescriber) {
    String newFolder = "";
    try {
      newFolder += uniqueFileDescriber.getRenamedFilePath();
    }
    catch (CouldNotParseDateException e) {
      newFolder += "other";
      e.printStackTrace();
    }
    newFolder += uniqueFileDescriber.getFlavour();
    return newFolder;
  }
}

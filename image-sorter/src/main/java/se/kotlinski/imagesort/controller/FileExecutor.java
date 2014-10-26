package se.kotlinski.imagesort.controller;

import org.apache.commons.io.FileUtils;
import se.kotlinski.imagesort.exception.CouldNotParseDateException;
import se.kotlinski.imagesort.mapper.ImageMapper;
import se.kotlinski.imagesort.model.FileCopyReport;
import se.kotlinski.imagesort.model.FileDescriber;
import se.kotlinski.imagesort.model.FolderIO;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Date: 2014-10-19
 *
 * @author Simon Kotlinski
 */
public class FileExecutor {

  public FileCopyReport copyFiles(ImageMapper imageMapper, FolderIO folderIO) {
    ArrayList<FileDescriber> uniqueFileDescribers = imageMapper.getUniqueImageDescribers();
    FileCopyReport fileCopyReport = new FileCopyReport();
    for (FileDescriber uniqueFileDescriber : uniqueFileDescribers) {
      try {
        String newFolder = folderIO.masterFolder.getAbsolutePath() + File.separator +
                           uniqueFileDescriber.getRenamedFilePath();
        FileUtils.mkdir(newFolder);

        createNewFile(uniqueFileDescriber, newFolder);
        fileCopyReport.fileCopySuccess();
      }
      catch (IOException | CouldNotParseDateException e) {
        e.printStackTrace();
        fileCopyReport.fileCopyFailed(uniqueFileDescriber);
      }
    }
    return fileCopyReport;
  }

  public void createNewFile(final FileDescriber uniqueFileDescriber,
                            final String newFileFolder) throws
                                                        IOException,
                                                        CouldNotParseDateException {
    File file = new File(newFileFolder + File.separator + uniqueFileDescriber.getRenamedFile());
    boolean fileCreated = file.createNewFile();
    if (fileCreated) {
      FileUtils.copyFile(uniqueFileDescriber.getFile(), file);
    }
    else {
      System.err.println("\ncouldn't move File: " + uniqueFileDescriber.getFile().getName());
      System.err.println("\ncouldn't move File: " + file.getAbsolutePath());

    }
  }
}

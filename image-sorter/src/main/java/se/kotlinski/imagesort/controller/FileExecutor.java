package se.kotlinski.imagesort.controller;

import org.apache.commons.io.FileUtils;
import se.kotlinski.imagesort.exception.CouldNotParseDateException;
import se.kotlinski.imagesort.mapper.ImageMapper;
import se.kotlinski.imagesort.model.Describer;
import se.kotlinski.imagesort.model.FileCopyReport;
import se.kotlinski.imagesort.model.FolderIO;
import se.kotlinski.imagesort.model.VideoDescriber;

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
    ArrayList<Describer> uniqueFileDescribers = imageMapper.getUniqueImageDescribers();
    FileCopyReport fileCopyReport = new FileCopyReport();
    for (Describer uniqueFileDescriber : uniqueFileDescribers) {
      try {
        String newFolder = folderIO.masterFolder.getAbsolutePath() + "\\" +
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

  public void createNewFile(final Describer uniqueFileDescriber, final String newFileFolder) throws
                                                           IOException,
                                                           CouldNotParseDateException {
    File file;

    if (uniqueFileDescriber instanceof VideoDescriber) {
      System.out.println("\nTry Execute Vid : " + newFileFolder + "\\" + uniqueFileDescriber.getRenamedFile());
    } else {
      System.out.println("\nTry Execute Img : " + newFileFolder + "\\" + uniqueFileDescriber.getRenamedFile());
    }
    file = new File(newFileFolder + "\\" + uniqueFileDescriber.getRenamedFile());
    boolean fileCreated = file.createNewFile();
    if (fileCreated) {
      FileUtils.copyFile(uniqueFileDescriber.getFile(), file);
    }
    else {
      if (uniqueFileDescriber instanceof VideoDescriber) {
        System.out.println("\ncouldn't move Video: " + uniqueFileDescriber.getFile().getName());
        System.out.println("\ncouldn't move Video: " + file.getAbsolutePath());
      } else {
        System.out.println("\ncouldn't move image: " + uniqueFileDescriber.getFile().getName());
        System.out.println("\ncouldn't move image: " + file.getAbsolutePath());
      }
    }
  }
}

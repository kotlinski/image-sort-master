package se.kotlinski.imagesort.controller;

import com.google.inject.Inject;
import se.kotlinski.imagesort.exception.InvalidInputFolders;
import se.kotlinski.imagesort.mapper.ImageMapper;
import se.kotlinski.imagesort.model.FolderIO;
import se.kotlinski.imagesort.utils.ImageFileUtil;

import java.io.File;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA. User: Simon Kotlinski Date: 2014-02-10 Time: 21:40 To change this
 * template use File | Settings | File Templates.
 */
public class FileIndexer {
  /**
   * Take input folders, and run indexing on files
   *
   * @param folderIO
   */
  public ImageMapper runIndexing(final FolderIO folderIO) throws InvalidInputFolders {
    if (folderIO == null || folderIO.masterFolder == null || folderIO.inputFolders == null) {
      throw new InvalidInputFolders();
    }
    if (isValidInputFolders(folderIO.inputFolders)) {
      ImageMapper imageMapper = new ImageMapper();
      imageMapper.populateWithImages(folderIO.inputFolders);
      return imageMapper;
    }
    else {
      throw new InvalidInputFolders();
    }
  }

  private static boolean isValidInputFolders(final ArrayList<File> inputFolders) {
    for (File inputFolder : inputFolders) {
      if (!ImageFileUtil.isValidFolder(inputFolder)) {
        return false;
      }
    }
    return true;
  }
}

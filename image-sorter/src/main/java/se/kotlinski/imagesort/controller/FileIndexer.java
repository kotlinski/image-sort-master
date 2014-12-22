package se.kotlinski.imagesort.controller;

import com.google.inject.Inject;
import se.kotlinski.imagesort.exception.InvalidInputFolders;
import se.kotlinski.imagesort.mapper.FileDescriberPathComparator;
import se.kotlinski.imagesort.mapper.ImageMapper;
import se.kotlinski.imagesort.model.FolderIO;
import se.kotlinski.imagesort.utils.ImageFileUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;

class FileIndexer {
  private final ImageFileUtil imageFileUtil;
  private final Calendar calendar;
  private final FileDescriberPathComparator fileDescriberPathComparator;

  @Inject
  public FileIndexer(final ImageFileUtil imageFileUtil,
                     final Calendar calendar,
                     final FileDescriberPathComparator fileDescriberPathComparator) {
    this.imageFileUtil = imageFileUtil;
    this.calendar = calendar;
    this.fileDescriberPathComparator = fileDescriberPathComparator;
  }

  public ImageMapper runIndexing(final FolderIO folderIO) throws InvalidInputFolders {
    if (folderIO == null || folderIO.masterFolder == null || folderIO.inputFolders == null) {
      throw new InvalidInputFolders();
    }
    if (isValidInputFolders(folderIO.inputFolders)) {
      ImageMapper imageMapper = new ImageMapper(calendar, fileDescriberPathComparator);
      imageMapper.populateWithImages(folderIO.inputFolders);
      return imageMapper;
    }
    else {
      throw new InvalidInputFolders();
    }
  }

  private boolean isValidInputFolders(final ArrayList<File> inputFolders) {
    for (File inputFolder : inputFolders) {
      if (!imageFileUtil.isValidFolder(inputFolder)) {
        return false;
      }
    }
    return true;
  }
}

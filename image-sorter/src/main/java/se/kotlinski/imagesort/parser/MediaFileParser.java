package se.kotlinski.imagesort.parser;

import com.google.inject.Inject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import se.kotlinski.imagesort.exception.InvalidInputFolders;
import se.kotlinski.imagesort.utils.MD5Generator;
import se.kotlinski.imagesort.utils.MediaFileUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MediaFileParser {
  private static final Logger LOGGER = LogManager.getLogger(MediaFileParser.class);
  private final MediaFileUtil mediaFileUtil;
  private final MD5Generator MD5Generator;

  @Inject
  public MediaFileParser(final MediaFileUtil mediaFileUtil, final MD5Generator MD5Generator) {
    this.mediaFileUtil = mediaFileUtil;
    this.MD5Generator = MD5Generator;
  }

  public Map<String, List<File>> getMediaFilesInFolder(final File masterFolder) throws Exception {
    if (!mediaFileUtil.isValidFolder(masterFolder)) {
      throw new InvalidInputFolders();
    }

    List<File> filesInMasterFolder = mediaFileUtil.getFilesInFolder(masterFolder);
    return groupFilesByMediaContent(filesInMasterFolder);
  }

  private Map<String, List<File>> groupFilesByMediaContent(final List<File> files) {

    Map<String, List<File>> fileMap = new HashMap<>();
    for (File file : files) {
      if (!mediaFileUtil.isValidMediaFile(file)) {
        //Ignore Files
        // TODO: Something smart for logging etc.
      }
      else {
        addMediaFileToMap(fileMap, file);
      }
    }
    return fileMap;
  }

  void addMediaFileToMap(final Map<String, List<File>> fileMap, final File file) {
    try {
      String fileContentIdentifier = MD5Generator.generateMd5(file);
      if (!fileMap.containsKey(fileContentIdentifier)) {
        fileMap.put(fileContentIdentifier, new ArrayList<>());
      }
      List<File> imageFileList = fileMap.get(fileContentIdentifier);
      imageFileList.add(file);
    }
    catch (Exception e) {
      //Ignore Files
    }
  }

}

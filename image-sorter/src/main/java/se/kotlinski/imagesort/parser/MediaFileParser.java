package se.kotlinski.imagesort.parser;

import com.google.inject.Inject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import se.kotlinski.imagesort.exception.InvalidInputFolders;
import se.kotlinski.imagesort.executor.ClientInterface;
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

  public Map<String, List<File>> getMediaFilesInFolder(final ClientInterface clientInterface,
                                                       final File masterFolder) throws Exception {
    if (!mediaFileUtil.isValidFolder(masterFolder)) {
      throw new InvalidInputFolders();
    }

    List<File> filesInMasterFolder = mediaFileUtil.getFilesInFolder(masterFolder, clientInterface);
    return groupFilesByMediaContent(clientInterface, filesInMasterFolder);
  }

  private Map<String, List<File>> groupFilesByMediaContent(final ClientInterface clientInterface,
                                                           final List<File> files) {

    clientInterface.startGroupFilesByContent();
    Map<String, List<File>> fileMap = new HashMap<>();
    int progress = 0;
    for (File file : files) {
      progress++;
      clientInterface.groupFilesByContentProgress(files.size(), progress);
      addMediaFileToMap(fileMap, file);
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
      e.printStackTrace();
      //Ignore Files
    }
  }

}

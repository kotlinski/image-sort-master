package se.kotlinski.imagesort.parser;

import com.google.inject.Inject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import se.kotlinski.imagesort.executor.ClientInterface;
import se.kotlinski.imagesort.utils.MediaFileUtil;

import java.io.File;
import java.util.List;

public class MediaFileParser {
  private static final Logger LOGGER = LogManager.getLogger(MediaFileParser.class);
  private final MediaFileUtil mediaFileUtil;

  @Inject
  public MediaFileParser(final MediaFileUtil mediaFileUtil) {
    this.mediaFileUtil = mediaFileUtil;
  }

  public List<File> getMediaFilesInFolder(final ClientInterface clientInterface,
                                          final File masterFolder) {

    if (!mediaFileUtil.isValidFolder(masterFolder)) {
      LOGGER.error("Invalid input folders, try again");
      clientInterface.masterFolderFailedParsed();
    }
    return mediaFileUtil.getMediaFilesInFolder(masterFolder, clientInterface);
  }

}

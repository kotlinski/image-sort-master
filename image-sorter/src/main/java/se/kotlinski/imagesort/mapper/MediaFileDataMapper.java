package se.kotlinski.imagesort.mapper;

import com.google.inject.Inject;
import se.kotlinski.imagesort.data.PixelHash;
import se.kotlinski.imagesort.feedback.FindDuplicatesFeedbackInterface;
import se.kotlinski.imagesort.utils.MediaFileHashGenerator;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MediaFileDataMapper {

  private final MediaFileHashGenerator mediaFileHashGenerator;
  private final MediaFileCacher mediaFileCacher;

  @Inject
  public MediaFileDataMapper(final MediaFileHashGenerator mediaFileHashGenerator,
                             final MediaFileCacher mediaFileCacher) {
    this.mediaFileHashGenerator = mediaFileHashGenerator;
    this.mediaFileCacher = mediaFileCacher;
  }

  public Map<PixelHash, List<File>> mapOnMediaFileData(FindDuplicatesFeedbackInterface
                                                           findDuplicatesFeedback,
                                                       List<File> files) {

    findDuplicatesFeedback.startGroupFilesByContent();
    Map<PixelHash, List<File>> fileMap = new HashMap<>();
    int progress = 0;
    for (File file : files) {
      progress++;
      findDuplicatesFeedback.groupFilesByContentProgress(files.size(), progress);
      addMediaFileToMap(fileMap, file);
    }
    return fileMap;
  }

  public void addMediaFileToMap(final Map<PixelHash, List<File>> fileMap, final File file) {
    try {
      PixelHash pixelHash;
      if (mediaFileCacher.isFileCached(file)) {
        pixelHash = mediaFileCacher.getMediaFilePixelHash(file);
      }
      else {
        pixelHash = mediaFileHashGenerator.generatePixelDataHash(file);
        mediaFileCacher.setMediaFileCache(file, pixelHash);
      }

      if (!fileMap.containsKey(pixelHash)) {
        fileMap.put(pixelHash, new ArrayList<>());
      }
      List<File> imageFileList = fileMap.get(pixelHash);
      imageFileList.add(file);
    }
    catch (Exception e) {
      e.printStackTrace();
      //Ignore Files
    }
  }
}

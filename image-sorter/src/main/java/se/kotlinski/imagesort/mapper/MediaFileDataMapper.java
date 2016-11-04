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

  @Inject
  public MediaFileDataMapper(final MediaFileHashGenerator mediaFileHashGenerator) {
    this.mediaFileHashGenerator = mediaFileHashGenerator;
  }

  public Map<PixelHash, List<File>> mapOnMediaFileData(FindDuplicatesFeedbackInterface findDuplicatesFeedback,
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
      pixelHash = mediaFileHashGenerator.generatePixelDataHash(file);
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

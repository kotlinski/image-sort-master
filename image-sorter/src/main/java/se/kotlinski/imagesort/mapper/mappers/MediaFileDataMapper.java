package se.kotlinski.imagesort.mapper.mappers;

import com.google.inject.Inject;
import se.kotlinski.imagesort.data.MediaFileDataHash;
import se.kotlinski.imagesort.main.ClientAnalyzeFilesInFolderInterface;
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

  public Map<MediaFileDataHash, List<File>> mapOnMediaFileData(ClientAnalyzeFilesInFolderInterface clientAnalyzeFilesInFolderInterface,
                                                               List<File> files) {

    clientAnalyzeFilesInFolderInterface.startGroupFilesByContent();
    Map<MediaFileDataHash, List<File>> fileMap = new HashMap<>();
    int progress = 0;
    for (File file : files) {
      progress++;
      clientAnalyzeFilesInFolderInterface.groupFilesByContentProgress(files.size(), progress);
      addMediaFileToMap(fileMap, file);
    }
    return fileMap;
  }

  public void addMediaFileToMap(final Map<MediaFileDataHash, List<File>> fileMap, final File file) {
    try {
      MediaFileDataHash fileContentIdentifier;
      fileContentIdentifier = mediaFileHashGenerator.generateMediaFileDataHash(file);
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

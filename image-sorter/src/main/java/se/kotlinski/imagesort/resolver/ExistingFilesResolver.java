package se.kotlinski.imagesort.resolver;

import org.apache.commons.io.FileUtils;
import se.kotlinski.imagesort.data.RelativeMediaFolderOutput;
import se.kotlinski.imagesort.utils.MediaFileUtil;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.List;
import java.util.Map;

public class ExistingFilesResolver {


  private final MediaFileUtil mediaFileUtil;

  public ExistingFilesResolver(final MediaFileUtil mediaFileUtil) {
    this.mediaFileUtil = mediaFileUtil;
  }

  public boolean resolveOutputConflictsWithOldFiles(final File masterFolderFile,
                                                    final Map<List<File>, RelativeMediaFolderOutput> resolvedFilesToOutputMap) {
    boolean foundConflicts = false;
    for (List<File> files : resolvedFilesToOutputMap.keySet()) {
      for (File file : files) {
        boolean conflictFound = checkIfFileConflictsWithOutputs(resolvedFilesToOutputMap,
                                                                file,
                                                                masterFolderFile);

        if (conflictFound) {
          foundConflicts = true;
          String randomString = new BigInteger(130, new SecureRandom()).toString(32);
          String newAbsolutePath = mediaFileUtil.appendToFileName(file.getAbsolutePath(),
                                                                  randomString);
          try {
            FileUtils.moveFile(file, FileUtils.getFile(newAbsolutePath));
          }
          catch (IOException e) {
            e.printStackTrace();
          }
          // file = FileUtils.getFile(newAbsolutePath);
        }
      }
    }

    return foundConflicts;
  }


  private boolean checkIfFileConflictsWithOutputs(final Map<List<File>, RelativeMediaFolderOutput> resolvedFilesToOutputMap,
                                                  final File file,
                                                  final File masterFolder) {

    for (Map.Entry<List<File>, RelativeMediaFolderOutput> filesOutputEntry : resolvedFilesToOutputMap.entrySet()) {
      if (checkIfFileIsInList(file, filesOutputEntry)) {
        return false;
      }
      String absolutOutput = masterFolder.getAbsolutePath() + filesOutputEntry.getValue();
      if (file.getAbsolutePath().equals(absolutOutput)) {
        return true;
      }
    }
    return false;
  }

  private boolean checkIfFileIsInList(final File file,
                                      final Map.Entry<List<File>, RelativeMediaFolderOutput> filesOutputEntry) {
    List<File> files = filesOutputEntry.getKey();
    for (File fileInEntry : files) {
      if (file.equals(fileInEntry)) {
        return true;
      }
    }
    return false;
  }
}

package se.kotlinski.imagesort.utils;

import se.kotlinski.imagesort.data.RelativeMediaFolderOutput;
import se.kotlinski.imagesort.main.ClientInterface;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class MediaFileUtil {

  public boolean isValidFolder(final File folder) {
    return folder != null && folder.exists() && folder.isDirectory();
  }

  String getSystemPath() {
    return System.getProperty("user.dir") + File.separator;
  }

  public List<File> getMediaFilesInFolder(final ClientInterface clientInterface, final File folder) {
    List<File> files = new ArrayList<>();
    if (folder == null || folder.listFiles() == null) {
      return files;
    }
    for (File file : folder.listFiles()) {
      if (file.isDirectory()) {
        files.addAll(getMediaFilesInFolder(clientInterface, file));
        clientInterface.parsedFilesInMasterFolderProgress(files.size());
      }
      else {
        if (isValidMediaFile(file)) {
          files.add(file);
          clientInterface.parsedFilesInMasterFolderProgress(files.size());
        }
      }
    }
    clientInterface.parsedFilesInMasterFolderProgress(files.size());
    return files;
  }

  public boolean isValidMediaFile(File file) {
    Path path = file.toPath();
    FileTypeDetector ftd = new FileTypeDetector();

    try {
      String type = ftd.probeContentType(path);
      if (type == null) {
        return false;
      }
      else if (type.contains("image")) {
        return true;
      }
      else if (type.contains("video")) {
        return true;
      }
    }
    catch (IOException err) {
      err.printStackTrace();
    }
    return false;
  }

  public RelativeMediaFolderOutput appendToFileName(final RelativeMediaFolderOutput outputDirectory, final String appendPart) {

    String outputDirectoryString = outputDirectory.relativePath;
    String outputPathWithoutExtension = outputDirectoryString.substring(0, outputDirectoryString.lastIndexOf('.'));
    String extension = outputDirectoryString.substring(outputDirectoryString.lastIndexOf('.'), outputDirectoryString.length());

    return new RelativeMediaFolderOutput(outputPathWithoutExtension + appendPart + extension);
  }

}

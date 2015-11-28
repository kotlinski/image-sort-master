package se.kotlinski.imagesort.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class MediaFileUtil {
  public MediaFileUtil() {

  }

  public boolean isValidFolder(final File folder) {
    return folder != null && folder.exists() && folder.isDirectory();
  }

  String getSystemPath() {
    return System.getProperty("user.dir") + File.separator;
  }

  public List<File> getFilesInFolder(final File folder) {
    List<File> files = new ArrayList<>();
    if (folder == null || folder.listFiles() == null) {
      return files;
    }
    for (File file : folder.listFiles()) {
      if (file.isDirectory()) {
        files.addAll(getFilesInFolder(file));
      }
      else {
        files.add(file);
      }
    }
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
      System.err.println(err);
    }
    return false;
  }

}

package se.kotlinski.imagesort.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SortMasterFileUtil {
  public SortMasterFileUtil() {
  }

  public String getTestInputPath(){
    return modifyPath(getSystemPath(), true);
  }

  public String getTestOutputPath(){
    return modifyPath(getSystemPath(), false);
  }

  public boolean isValidFolder(final File folder) {
		return folder != null && folder.exists() && folder.isDirectory();
	}

  String getSystemPath() {
    return System.getProperty("user.dir") + File.separator;
  }

  private final String modifyPath(String path, boolean input) {
    path = path.replace("image-clients" + File.separator, "");
    if (path.contains("image-sorter")) {
      return path + appendPathSpecifier(input);
    }
    else {
      return path + "image-sorter" + File.separator + appendPathSpecifier(input);
    }
  }

  private String appendPathSpecifier(final boolean input) {
    String folder;
    if(input) {
      folder = "inputImages";
    } else {
      folder = "output";
    }
    return "src" + File.separator +
           "test" + File.separator +
           "resources" + File.separator +
           folder;
  }

  public List<File> readAllFilesInFolder(final File folder) {
    List<File> imageDescriber = new ArrayList<>();
    if (folder == null) {
      return imageDescriber;
    }
    File[] files = folder.listFiles();
    if (files == null) {
      return imageDescriber;
    }
    for (File file : files) {
      if (file.isDirectory()) {
        imageDescriber.addAll(readAllFilesInFolder(file));
      }
      else {
        imageDescriber.add(file);
      }
    }
    return imageDescriber;
  }
}

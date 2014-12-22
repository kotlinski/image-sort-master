package se.kotlinski.imagesort.utils;

import java.io.File;

/**
 * Describe class/interface here.
 *
 * Date: 2014-10-13
 *
 * @author Simon Kotlinski
 * @version $Revision: 1.1 $
 */
public class ImageFileUtil {
  public ImageFileUtil() {
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
}

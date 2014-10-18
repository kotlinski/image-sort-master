package se.kotlinski.imagesort.utils;

import java.io.File;

/**
 * Created with IntelliJ IDEA. User: Simon Date: 2013-12-23 Time: 23:30 To change this template use
 * File | MainRenamer.imagesort.Settings | File Templates.
 */
public class Constants {
  public static final String USER_FOLDER = "user.folder";
  public static final String PATH_INPUT_TEST = modifyPath(System.getProperty("user.dir") + File.separator, true);

  public static final String PATH_OUTPUT_TEST = modifyPath(System.getProperty("user.dir") + File.separator, false);

  private Constants() {
  }

  private static final String modifyPath(String path, boolean input) {
    path = path.replace("image-clients" + File.separator, "");
    if (path.contains("image-sorter")) {
      return path + appendPathSpecifier(input);
    }
    else {
      return path + "image-sorter" + File.separator + appendPathSpecifier(input);
    }
  }

  private static String appendPathSpecifier(final boolean input) {
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


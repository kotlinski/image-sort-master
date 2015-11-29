package se.kotlinski.imagesort.utils;

import java.io.File;

public class MediaFileTestUtil {
  private final MediaFileUtil mediaFileUtils;

  public MediaFileTestUtil(MediaFileUtil mediaFileUtils) {
    this.mediaFileUtils = mediaFileUtils;
  }

  public File getTestInputFile() {
    String path = modifyPath(mediaFileUtils.getSystemPath(), true);
    return new File(path);
  }

  public String getTestInputPath() {
    return modifyPath(mediaFileUtils.getSystemPath(), true);
  }

  public String getTestOutputPath() {
    return modifyPath(mediaFileUtils.getSystemPath(), false);
  }

  private String modifyPath(String path, boolean input) {
    String pathWithoutRoot = path.replace("image-clients" + File.separator, "");
    if (pathWithoutRoot.contains("image-sorter")) {
      return pathWithoutRoot + appendPathSpecifier(input);
    }
    else {
      return pathWithoutRoot + "image-sorter" + File.separator + appendPathSpecifier(input);
    }
  }

  private String appendPathSpecifier(final boolean input) {
    String folder;
    if (input) {
      folder = "inputImages";
    }
    else {
      folder = "output";
    }
    return "src" + File.separator +
           "test" + File.separator +
           "resources" + File.separator +
           folder;
  }

  public File getAJpegFile() {
    return new File(getTestInputPath() +
                    File.separator +
                    "2014" +
                    File.separator +
                    "A 2014-02-22 11.48.48-1-anteckning(1.31MB).jpg");
  }

  public File getMp4File() {
    return new File(getTestInputPath() + File.separator + "2014-03-16 10.45.09.mp4");
  }

  public File getJpegWitouthDate() {
    return new File(getTestInputPath() + File.separator + "noxon on raindeer - no date.jpg");
  }

  public File getSnapchatFile() {
    return new File(getTestInputPath() +
                    File.separator +
                    "2013" +
                    File.separator +
                    "snapchat" +
                    File.separator + "2013-10-03 13.43.20-kaffe.jpg");
  }

  public File getInstagramFile() {
    return new File(getTestInputPath() +
                    File.separator +
                    "2013" +
                    File.separator +
                    "instagram" +
                    File.separator + "2013-10-26 20.20.46-kottbullar.jpg");
  }
}

package se.kotlinski.imagesort.utils;

import org.apache.commons.io.FileUtils;
import se.kotlinski.imagesort.parser.MediaFileParser;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class MediaFileTestUtil {
  private final MediaFileUtil mediaFileUtils;
  private MediaFileParser mediaFileParser;

  private final String defaultTestFolderName = "inputImages";
  private final String restoreableTestFolderName = "restoreable_master_folder";

  public MediaFileTestUtil(MediaFileUtil mediaFileUtils) {
    this.mediaFileUtils = mediaFileUtils;
    mediaFileParser = new MediaFileParser(mediaFileUtils, new MD5Generator());
  }

  public File getTestInputFile() {
    String path = modifyPath(mediaFileUtils.getSystemPath(), defaultTestFolderName);
    return new File(path);
  }

  public String getTestInputPath() {
    return modifyPath(mediaFileUtils.getSystemPath(), defaultTestFolderName);
  }

  public File getRestorableTestMasterFile() {
    String path = modifyPath(mediaFileUtils.getSystemPath(), restoreableTestFolderName);
    return new File(path);
  }

  public String getRestorableTestMasterPath() {
    return modifyPath(mediaFileUtils.getSystemPath(), restoreableTestFolderName);
  }

  private String modifyPath(String path, final String folderName) {
    String pathWithoutRoot = path.replace("image-clients" + File.separator, "");
    if (pathWithoutRoot.contains("image-sorter")) {
      return pathWithoutRoot + appendPathSpecifier(folderName);
    }
    else {
      return pathWithoutRoot + "image-sorter" + File.separator + appendPathSpecifier(folderName);
    }
  }

  private String appendPathSpecifier(final String folderName) {

    return "src" + File.separator +
           "test" + File.separator +
           "resources" + File.separator +
           folderName;
  }

  public File getAJpegFile() {
    return new File(getTestInputPath() +
                    File.separator +
                    "2014" +
                    File.separator +
                    "A 2014-02-22 11.48.48-1-anteckning(1.31MB).jpg");
  }

  public File getAJpegFileFromASubFolder() {
    return new File(getTestInputPath() +
                    File.separator +
                    "2014" +
                    File.separator +
                    "duplicate in subfolder" +
                    File.separator +
                    "A 2014-02-22 11.48.48-1-anteckning(1.31MB) - Copy.jpg");
  }

  public File getMp4File() {
    return new File(getTestInputPath() + File.separator + "2014-03-16 10.45.09.mp4");
  }

  public File getJpegWitouthDate() {
    return new File(getTestInputPath() + File.separator + "noxon on raindeer - no date.jpg");
  }

  public File getJpegWitouthDateInSubfolder() {
    return new File(getTestInputPath() + File.separator + "2014" + File.separator + "nixon on raindeer - no date.jpg");
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

  public Map<String, List<File>> getParsedMediaFiles(final File testInputFile) {
    try {
      return mediaFileParser.getMediaFilesInFolder(testInputFile);
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  public void cleanRestoreableMasterFolder() {
    File restorableTestMasterFile = getRestorableTestMasterFile();
    try {
      FileUtils.cleanDirectory(restorableTestMasterFile);
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }


  public void copyTestFilesToRestoreableDirectory() {
    File testInputFile = getTestInputFile();
    File restorableTestMasterFile = getRestorableTestMasterFile();
    try {
      FileUtils.copyDirectory(testInputFile, restorableTestMasterFile);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}

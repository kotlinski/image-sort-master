package se.kotlinski.imagesort.data;

import java.io.File;


public class SortSettings {
  public File masterFolder;

  @Override
  public String toString() {
    String returnString = "";
    if (masterFolder == null) {
      return "Master Folder not set";
    }
    else {
      returnString += "Output: " + masterFolder.getAbsolutePath();
    }
    return returnString;
  }
}

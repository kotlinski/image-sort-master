package se.kotlinski.imagesort.model;

import java.io.File;
import java.util.ArrayList;


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

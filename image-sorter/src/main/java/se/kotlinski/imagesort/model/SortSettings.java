package se.kotlinski.imagesort.model;

import java.io.File;
import java.util.ArrayList;


public class SortSettings {
  public ArrayList<File> inputFolders;
  public File masterFolder;

  @Override
  public String toString() {
    String returnString = "";
    if (inputFolders == null || masterFolder == null) {
      return "Folders not set";
    }
    else {
      for (File inputFolder : inputFolders) {
        returnString = String.format("%sInput: %s\n", returnString, inputFolder);
      }
      returnString += "Output: " + masterFolder.getAbsolutePath();
    }
    return returnString;
  }
}

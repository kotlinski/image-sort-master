package se.kotlinski.imagesort.mapper;

import se.kotlinski.imagesort.exception.CouldNotParseDateException;
import se.kotlinski.imagesort.model.FileDescriber;
import se.kotlinski.imagesort.utils.FileDateInterpreter;
import se.kotlinski.imagesort.utils.FileDateMD5Generator;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by Simon Kotlinski on 2014-05-07.
 */
public class ImageMapper {
  HashMap<String, ArrayList<FileDescriber>> imageMap;

  public ImageMapper() {
    imageMap = new HashMap<String, ArrayList<FileDescriber>>();
  }

  public static ArrayList<File> recursiveIterate(final File folder) {
    ArrayList<File> imageDescriber = new ArrayList<File>();
    for (File file : folder.listFiles()) {
      if (file.isDirectory()) {
        imageDescriber.addAll(recursiveIterate(file));
      }
      else {
        imageDescriber.add(file);
      }
    }
    return imageDescriber;
  }

  public void addValidDescriberFile(FileDescriber fileDescriber) {
    String md5 = fileDescriber.getMd5();
    ArrayList<FileDescriber> imageArray = getImageArrayForMd5(fileDescriber, md5);
    imageArray.add(fileDescriber);
  }

  private ArrayList<FileDescriber> getImageArrayForMd5(FileDescriber imageDescriber, String md5) {
    ArrayList<FileDescriber> imageArray;
    if (imageMap.containsKey(md5)) {
      imageArray = imageMap.get(md5);
    }
    else {
      imageArray = new ArrayList<FileDescriber>();
      imageMap.put(md5, imageArray);
    }
    return imageArray;
  }

  public void populateWithImages(ArrayList<File> inputFolders) {
    ArrayList<File> files = new ArrayList<File>();
    for (File rootFolder : inputFolders) {
      files.addAll(recursiveIterate(rootFolder));
    }

    FileDateInterpreter fileDateInterpreter = new FileDateInterpreter();
    FileDateMD5Generator fileDateMD5Generator = new FileDateMD5Generator();
    for (File file : files) {
      Date date = null;
      try {
        date = fileDateInterpreter.getDate(file);
      }
      catch (CouldNotParseDateException e) {
        e.printStackTrace();
      }
      String imageIdentifier = fileDateMD5Generator.generateMd5(file);
      FileDescriber fileDescriber = new FileDescriber(file, date, imageIdentifier);
      addValidDescriberFile(fileDescriber);
    }
  }


  public ArrayList<FileDescriber> getUniqueImageDescribers() {
    ArrayList<FileDescriber> imageDescribers = new ArrayList<FileDescriber>();
    for (String s : imageMap.keySet()) {
      imageDescribers.add(imageMap.get(s).get(0));
    }
    Collections.sort(imageDescribers, new FileDescriberPathComperator());
    return imageDescribers;
  }

  public int getSizeOfUniqueImages() {
    return imageMap.size();
  }

  @Override
  public String toString() {
    String retString = "Files in input folder: \n";
    for (String s : imageMap.keySet()) {
      retString += s + ", including files: " + "\n";
      for (FileDescriber imageDescriber : imageMap.get(s)) {
        retString += "\t" + imageDescriber.toString() + "\n";
      }
    }
    return retString;
  }


  public ArrayList<FileDescriber> getRedundantFiles() {
    ArrayList<FileDescriber> imageDescribers = new ArrayList<FileDescriber>();
    for (String s : imageMap.keySet()) {
      for (int i = 1; i < imageMap.get(s).size(); i++) {
        imageDescribers.add(imageMap.get(s).get(i));
      }
    }
    Collections.sort(imageDescribers, new FileDescriberPathComperator());
    return imageDescribers;
  }
}

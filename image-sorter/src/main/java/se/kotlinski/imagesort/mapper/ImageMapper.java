package se.kotlinski.imagesort.mapper;

import se.kotlinski.imagesort.exception.CouldNotGenerateIDException;
import se.kotlinski.imagesort.model.Describer;
import se.kotlinski.imagesort.model.ImageDescriber;
import se.kotlinski.imagesort.model.VideoDescriber;
import se.kotlinski.imagesort.utils.ImageTagReader;
import se.kotlinski.imagesort.utils.VideoTagReader;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

/**
 * Created by Simon Kotlinski on 2014-05-07.
 */
public class ImageMapper {
  HashMap<String, ArrayList<Describer>> imageMap;

  public ImageMapper() {
    imageMap = new HashMap<String, ArrayList<Describer>>();
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

  public void addValidDescriberFile(Describer fileDescriber) {
    String md5 = fileDescriber.getMd5();
    ArrayList<Describer> imageArray = getImageArrayForMd5(fileDescriber, md5);
    imageArray.add(fileDescriber);
  }

  private ArrayList<Describer> getImageArrayForMd5(Describer imageDescriber, String md5) {
    ArrayList<Describer> imageArray;
    if (imageMap.containsKey(md5)) {
      imageArray = imageMap.get(md5);
    }
    else {
      imageArray = new ArrayList<Describer>();
      imageMap.put(md5, imageArray);
    }
    return imageArray;
  }

  public void populateWithImages(ArrayList<File> inputFolders) {
    ArrayList<File> files = new ArrayList<File>();
    for (File rootFolder : inputFolders) {
      files.addAll(recursiveIterate(rootFolder));
    }

    for (File file : files) {
      if (ImageTagReader.isValidImageFile(file)) {
        try {
          ImageDescriber imageDescriber = new ImageDescriber(file);
          addValidDescriberFile(imageDescriber);
        }
        catch (CouldNotGenerateIDException e) {
          e.printStackTrace();
        }
      }
      else if (VideoTagReader.isValidVideoFile(file)) {
        try {
          VideoDescriber videoDescriber = new VideoDescriber(file);
          addValidDescriberFile(videoDescriber);
        }
        catch (CouldNotGenerateIDException e) {
          e.printStackTrace();
        }
      }
      else {
        System.out.println(file.getAbsoluteFile() + " is not valid");
      }
    }
  }


  public ArrayList<Describer> getUniqueImageDescribers() {
    ArrayList<Describer> imageDescribers = new ArrayList<Describer>();
    for (String s : imageMap.keySet()) {
      imageDescribers.add(imageMap.get(s).get(0));
    }
    Collections.sort(imageDescribers, new FileDescriberComperator());
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
      for (Describer imageDescriber : imageMap.get(s)) {
        retString += "\t" + imageDescriber.toString() + "\n";
      }
    }
    return retString;
  }


  public ArrayList<Describer> getRedundantFiles() {
    ArrayList<Describer> imageDescribers = new ArrayList<Describer>();
    for (String s : imageMap.keySet()) {
      for (int i = 1; i < imageMap.get(s).size(); i++) {
        imageDescribers.add(imageMap.get(s).get(i));
      }
    }
    Collections.sort(imageDescribers, new FileDescriberComperator());
    return imageDescribers;
  }


  private class FileDescriberComperator implements Comparator<Describer> {
    @Override
    public int compare(final Describer describer1, final Describer describer2) {
      return describer1.compareTo(describer2);
    }
  }
}

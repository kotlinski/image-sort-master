package se.kotlinski.imagesort.mapper;

import se.kotlinski.imagesort.model.ImageDescriber;
import se.kotlinski.imagesort.utils.ImageTagReader;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

/**
 * Created by Simon Kotlinski on 2014-05-07.
 */
public class ImageMapper {
  HashMap<String, ArrayList<ImageDescriber>> imageMap;

  public ImageMapper() {
    imageMap = new HashMap<String, ArrayList<ImageDescriber>>();
  }

  public static ArrayList<ImageDescriber> recursiveIterate(final File folder) {
    ArrayList<ImageDescriber> imageDescriber = new ArrayList<ImageDescriber>();
    for (File file : folder.listFiles()) {
      if (file.isDirectory()) {
        imageDescriber.addAll(recursiveIterate(file));
      }
      else {
        imageDescriber.add(new ImageDescriber(file));
      }
    }
    return imageDescriber;
  }

  public void addImage(ImageDescriber imageDescriber) {
    String md5 = imageDescriber.getMd5();
    ArrayList<ImageDescriber> imageArray = getImageArrayForMd5(imageDescriber, md5);
    imageArray.add(imageDescriber);
  }

  private ArrayList<ImageDescriber> getImageArrayForMd5(ImageDescriber imageDescriber, String md5) {
    ArrayList<ImageDescriber> imageArray;
    if (imageMap.containsKey(md5)) {
      imageArray = imageMap.get(md5);
    }
    else {
      imageArray = new ArrayList<ImageDescriber>();
      imageMap.put(md5, imageArray);
    }
    return imageArray;
  }

  public void populateWithImages(ArrayList<File> inputFolders) {
    ArrayList<ImageDescriber> images = new ArrayList<ImageDescriber>();
    for (File rootFolder : inputFolders) {
      images.addAll(recursiveIterate(rootFolder));
    }
    for (ImageDescriber image : images) {
      if (ImageTagReader.isValidImageFile(image.getFile())) {
        addImage(image);
      }
      else {
        System.out.println(image.getFile().getAbsoluteFile() + " is not valid");
      }
    }
  }


  public ArrayList<ImageDescriber> getUniqueImageDescribers() {
    ArrayList<ImageDescriber> imageDescribers = new ArrayList<ImageDescriber>();
    for (String s : imageMap.keySet()) {
      imageDescribers.add(imageMap.get(s).get(0));
    }
    Collections.sort(imageDescribers, new ImageDescriberComperator());
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
      for (ImageDescriber imageDescriber : imageMap.get(s)) {
        retString += "\t" + imageDescriber.toString() + "\n";
      }
    }
    return retString;
  }


  public ArrayList<ImageDescriber> getRedundantFiles() {
    ArrayList<ImageDescriber> imageDescribers = new ArrayList<ImageDescriber>();
    for (String s : imageMap.keySet()) {
      for (int i = 1; i < imageMap.get(s).size(); i++) {
        imageDescribers.add(imageMap.get(s).get(i));
      }
    }
    Collections.sort(imageDescribers, new ImageDescriberComperator());
    return imageDescribers;
  }


  private class ImageDescriberComperator implements Comparator<ImageDescriber> {
    @Override
    public int compare(final ImageDescriber describer1, final ImageDescriber describer2) {
      return describer1.compareTo(describer2);
    }
  }
}

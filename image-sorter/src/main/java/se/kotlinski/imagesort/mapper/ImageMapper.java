package se.kotlinski.imagesort.mapper;

import com.google.inject.Inject;
import se.kotlinski.imagesort.exception.CouldNotParseDateException;
import se.kotlinski.imagesort.model.FileDescriber;
import se.kotlinski.imagesort.utils.FileDateInterpreter;
import se.kotlinski.imagesort.utils.FileDateUniqueGenerator;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ImageMapper {
  private final Map<String, ArrayList<FileDescriber>> imageMap;

  @Inject
  private final Calendar calendar;
  @Inject
  private final FileDescriberPathComparator fileDescriberPathComparator;

  public ImageMapper(final Calendar calendar,
                     final FileDescriberPathComparator fileDescriberPathComparator) {
    this.calendar = calendar;
    this.fileDescriberPathComparator = fileDescriberPathComparator;
    imageMap = new HashMap<>();
  }

  public List<File> recursiveIterate(final File folder) {
    List<File> imageDescriber = new ArrayList<>();
    if (folder == null) {
      return imageDescriber;
    }
    File[] files = folder.listFiles();
    if (files == null) {
      return imageDescriber;
    }
    for (File file : files) {
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
    ArrayList<FileDescriber> imageArray = getImageArrayForMd5(md5);
    imageArray.add(fileDescriber);
  }

  private ArrayList<FileDescriber> getImageArrayForMd5(String md5) {
    ArrayList<FileDescriber> imageArray;
    if (imageMap.containsKey(md5)) {
      imageArray = imageMap.get(md5);
    }
    else {
      imageArray = new ArrayList<>();
      imageMap.put(md5, imageArray);
    }
    return imageArray;
  }

  public void populateWithImages(ArrayList<File> inputFolders) {
    Map<String, List<File>> filesFromFolder = new HashMap<>();
    for (File rootFolder : inputFolders) {
      filesFromFolder.put(rootFolder.getAbsolutePath(), recursiveIterate(rootFolder));
    }

    FileDateInterpreter fileDateInterpreter = new FileDateInterpreter();
    FileDateUniqueGenerator fileDateUniqueGenerator = new FileDateUniqueGenerator();

    for (String rootFolder : filesFromFolder.keySet()) {

      for (File file : filesFromFolder.get(rootFolder)) {
        Date date = null;
        try {
          date = fileDateInterpreter.getDate(file);
        }
        catch (CouldNotParseDateException e) {
          e.printStackTrace();
        }
        String imageIdentifier = fileDateUniqueGenerator.generateMd5(file);
        FileDescriber fileDescriber = new FileDescriber(file,
                                                        date,
                                                        imageIdentifier,
                                                        rootFolder,
                                                        calendar);
        addValidDescriberFile(fileDescriber);
      }

    }
  }

  public ArrayList<FileDescriber> getUniqueImageDescribers() {
    ArrayList<FileDescriber> imageDescribers = new ArrayList<>();
    for (String s : imageMap.keySet()) {
      imageDescribers.add(imageMap.get(s).get(0));
    }
    Collections.sort(imageDescribers, fileDescriberPathComparator);
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

  public List<FileDescriber> getRedundantFiles() {
    List<FileDescriber> imageDescribers = new ArrayList<>();
    for (String s : imageMap.keySet()) {
      if (imageMap.get(s).size() > 1) {
        imageDescribers.addAll(imageMap.get(s));
      }
    }
    Collections.sort(imageDescribers, fileDescriberPathComparator);
    return imageDescribers;
  }
}

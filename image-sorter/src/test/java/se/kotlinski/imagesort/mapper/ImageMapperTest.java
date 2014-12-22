package se.kotlinski.imagesort.mapper;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import se.kotlinski.imagesort.model.FileDescriber;
import se.kotlinski.imagesort.utils.FileDateInterpreter;
import se.kotlinski.imagesort.utils.ImageFileUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class ImageMapperTest {

  private ImageMapper imageMapper;
  private ImageFileUtil imageFileUtil;

  private Calendar calendar;

  @Before
  public void setUp() throws Exception {
    imageFileUtil = new ImageFileUtil();
    FileDescriberPathComparator fileDescriberPathComparator = new FileDescriberPathComparator();
    calendar = new GregorianCalendar();
    imageMapper = new ImageMapper(calendar, fileDescriberPathComparator);
    calendar = new GregorianCalendar();
  }


  @Test
  public void testPopulateWithImages() throws Exception {
    File file = new File(imageFileUtil.getTestInputPath());
    ArrayList<File> inputFolders = new ArrayList<>();
    inputFolders.add(file);
    imageMapper.populateWithImages(inputFolders);
    Assert.assertEquals("Number of unique images in test folder",
                        9,
                        imageMapper.getSizeOfUniqueImages());
    //System.out.println("Expecting 6 files: \n" + imageMapper.toString());
  }

  @Test
  public void testRecursiveIterate() throws Exception {
    File folder = new File(imageFileUtil.getTestInputPath());
    List<File> imageList = imageMapper.recursiveIterate(folder);
    Assert.assertEquals("Image found in root folder", 11, imageList.size());
  }

  @Test
  public void testToString() throws Exception {
    File inputFile = new File(imageFileUtil.getTestInputPath() + File.separator + "1.jpg");
    FileDateInterpreter fileDateInterpreter = new FileDateInterpreter();

    Date date = fileDateInterpreter.getDate(inputFile);
    FileDescriber fileDescriber;
    fileDescriber = new FileDescriber(inputFile,
                                      date,
                                      "abc",
                                      imageFileUtil.getTestInputPath(),
                                      calendar);
    imageMapper.addValidDescriberFile(fileDescriber);
    Assert.assertEquals("Files in input folder: \n" +
                        "abc, including files: \n" +
                        "\t1.jpg\n", imageMapper.toString());

  }

  @Test
  public void testNewFilesNames() throws Exception {
    File file = new File(imageFileUtil.getTestInputPath());
    ArrayList<File> inputFolderList = new ArrayList<>();
    inputFolderList.add(file);
    imageMapper.populateWithImages(inputFolderList);

    ArrayList<FileDescriber> imageDescribers = imageMapper.getUniqueImageDescribers();

    Assert.assertEquals("Unique image describer sizes", 9, imageDescribers.size());
  }

  @Test
  public void testGetRedundantFiles() throws Exception {
    Assert.assertEquals("To string function", "Files in input folder: \n", imageMapper.toString());
    Assert.assertEquals("Size of redundant file list", 0, imageMapper.getRedundantFiles().size());


    File folder = new File(imageFileUtil.getTestInputPath());
    ArrayList<File> inputFolders = new ArrayList<>();
    inputFolders.add(folder);
    imageMapper.populateWithImages(inputFolders);
    List<FileDescriber> redundantFiles = imageMapper.getRedundantFiles();
    Assert.assertEquals(3, redundantFiles.size());
  }
}
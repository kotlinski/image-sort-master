package se.kotlinski.imagesort.mapper;


import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import se.kotlinski.imagesort.model.FileDescriber;
import se.kotlinski.imagesort.utils.FileDateInterpreter;
import se.kotlinski.imagesort.utils.ImageFileUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

public class ImageMapperTest {

  ImageMapper imageMapper;
  private ImageFileUtil imageFileUtil;

  @Before
  public void setUp() throws Exception {
    imageFileUtil = new ImageFileUtil();
    imageMapper = new ImageMapper();
  }

  @After
  public void tearDown() throws Exception {

  }

  @Test
  public void testAddImage() throws Exception {

  }

  @Test
  public void testPopulateWithImages() throws Exception {
    File file = new File(imageFileUtil.getTestInputPath());
    ArrayList<File> inputFolders = new ArrayList<File>();
    inputFolders.add(file);
    imageMapper.populateWithImages(inputFolders);
    Assert.assertEquals("Number of unique images in testfolder",
                        9,
                        imageMapper.getSizeOfUniqueImages());
    //System.out.println("Expecting 6 files: \n" + imageMapper.toString());
  }

  @Test
  public void testRecursiveIterate() throws Exception {
    ArrayList<File> imageList;
    imageList = ImageMapper.recursiveIterate(new File(imageFileUtil.getTestInputPath()));
    Assert.assertEquals("Image found in root folder", 10, imageList.size());
  }

  @Test
  public void testToString() throws Exception {
    File inputFile = new File(imageFileUtil.getTestInputPath() + File.separator + "1.jpg");
    FileDateInterpreter fileDateInterpreter = new FileDateInterpreter();

    Date date = fileDateInterpreter.getDate(inputFile);
    FileDescriber fileDescriber = new FileDescriber(inputFile, date, "abc");
    imageMapper.addValidDescriberFile(fileDescriber);
    Assert.assertEquals("Files in input folder: \n" +
                        "abc, including files: \n" +
                        "\t1.jpg\n", imageMapper.toString());

  }

  @Test
  public void testNewFilesNames() throws Exception {
    File file = new File(imageFileUtil.getTestInputPath());
    ArrayList<File> inputFolderList = new ArrayList<File>();
    inputFolderList.add(file);
    imageMapper.populateWithImages(inputFolderList);

    ArrayList<FileDescriber> imageDescribers = imageMapper.getUniqueImageDescribers();

    Assert.assertEquals("Unique image describer sizes", 9, imageDescribers.size());
  }

  @Test
  public void testGetRedundantFiles() throws Exception {
    Assert.assertEquals("To string function", "Files in input folder: \n", imageMapper.toString());
    Assert.assertEquals("Size of reduntant file list", 0, imageMapper.getRedundantFiles().size());
  }
}
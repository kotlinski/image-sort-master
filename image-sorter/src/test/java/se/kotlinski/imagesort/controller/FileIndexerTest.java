package se.kotlinski.imagesort.controller;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import se.kotlinski.imagesort.exception.InvalidInputFolders;
import se.kotlinski.imagesort.mapper.FileDescriberPathComperator;
import se.kotlinski.imagesort.mapper.ImageMapper;
import se.kotlinski.imagesort.model.FolderIO;
import se.kotlinski.imagesort.utils.ImageFileUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;

public class FileIndexerTest {
  private FileIndexer fileIndexer;
  private ImageFileUtil imageFileUtil;
  private FolderIO folderIO;
  private Calendar calendar;
  private FileDescriberPathComperator fileDescriberPathComperator;

  @Before
  public void setUp() {
    imageFileUtil = new ImageFileUtil();

    folderIO = new FolderIO();
    File file = new File(imageFileUtil.getTestInputPath());
    ArrayList<File> inputFolders = new ArrayList<File>();
    inputFolders.add(file);
    folderIO.inputFolders = inputFolders;
    folderIO.masterFolder = new File(imageFileUtil.getTestOutputPath());
    calendar = new GregorianCalendar();
    fileDescriberPathComperator = new FileDescriberPathComperator();
    setFileIndexer(new FileIndexer(imageFileUtil, calendar, fileDescriberPathComperator));
  }

  @Test
  public void testRunIndex() throws Exception {
    ImageMapper imageMapper = getFileIndexer().runIndexing(folderIO);
    Assert.assertEquals("Number of Unique images", 9, imageMapper.getSizeOfUniqueImages());
  }

  @Test
  public void testRunIndexInvalidInput() throws Exception {
    setFileIndexer(new FileIndexer(imageFileUtil, calendar, fileDescriberPathComperator));
    try {
      getFileIndexer().runIndexing(null);
      assert false;
    } catch (InvalidInputFolders e) {
      assert true;
    }

    FolderIO folderIO = new FolderIO();
    setFileIndexer(new FileIndexer(imageFileUtil, calendar, fileDescriberPathComperator));
    try {
      getFileIndexer().runIndexing(folderIO);
      assert false;
    }
    catch (InvalidInputFolders e) {
      assert true;
    }

    folderIO.masterFolder = new File("SomeInvalidFilePath");
    ArrayList<File> inputFolders = new ArrayList<File>();
    inputFolders.add(folderIO.masterFolder);
    folderIO.inputFolders = inputFolders;
    setFileIndexer(new FileIndexer(imageFileUtil, calendar, fileDescriberPathComperator));
    try{
      getFileIndexer().runIndexing(folderIO);
      assert false;
    } catch (InvalidInputFolders e){
      assert true;
    }

    folderIO.masterFolder = new File(imageFileUtil.getTestOutputPath());
    inputFolders = new ArrayList<File>();
    inputFolders.add(folderIO.masterFolder);
    folderIO.inputFolders = inputFolders;
    setFileIndexer(new FileIndexer(imageFileUtil, calendar, fileDescriberPathComperator));
    Assert.assertNotNull("Valid folderIO", getFileIndexer().runIndexing(folderIO));
  }

  public FileIndexer getFileIndexer() {
    return fileIndexer;
  }

  public void setFileIndexer(final FileIndexer fileIndexer) {
    this.fileIndexer = fileIndexer;
  }
}

package se.kotlinski.imagesort.controller;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import se.kotlinski.imagesort.exception.InvalidInputFolders;
import se.kotlinski.imagesort.mapper.ImageMapper;
import se.kotlinski.imagesort.model.FolderIO;
import se.kotlinski.imagesort.utils.ImageFileUtil;

import java.io.File;
import java.util.ArrayList;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;

/**
 * Created by Simon Kotlinski on 2014-02-19.
 */
public class FileIndexerTest {
  FileIndexer fileIndexer;
  private ImageFileUtil imageFileUtil;
  private FolderIO folderIO;

  @Before
  public void setUp() {
    imageFileUtil = new ImageFileUtil();

    folderIO = new FolderIO();
    File file = new File(imageFileUtil.getTestInputPath());
    ArrayList<File> inputFolders = new ArrayList<File>();
    inputFolders.add(file);
    folderIO.inputFolders = inputFolders;
    folderIO.masterFolder = new File(imageFileUtil.getTestOutputPath());
    fileIndexer = new FileIndexer();
  }

  @Test
  public void testRunIndex() throws Exception {
    ImageMapper imageMapper = fileIndexer.runIndexing(folderIO);
    Assert.assertEquals("Number of Unique images", 9, imageMapper.getSizeOfUniqueImages());
  }

  @Test
  public void testRunIndexInvalidInput() throws Exception {
    fileIndexer = new FileIndexer();
    try {
      fileIndexer.runIndexing(null);
      assert false;
    } catch (InvalidInputFolders e) {
      assert true;
    }

    FolderIO folderIO = new FolderIO();
    fileIndexer = new FileIndexer();
    try {
      fileIndexer.runIndexing(folderIO);
      assert false;
    }
    catch (InvalidInputFolders e) {
      assert true;
    }

    folderIO.masterFolder = new File("SomeInvalidFilePath");
    ArrayList<File> inputFolders = new ArrayList<File>();
    inputFolders.add(folderIO.masterFolder);
    folderIO.inputFolders = inputFolders;
    fileIndexer = new FileIndexer();
    try{
      fileIndexer.runIndexing(folderIO);
      assert false;
    } catch (InvalidInputFolders e){
      assert true;
    }

    folderIO.masterFolder = new File(imageFileUtil.getTestOutputPath());
    inputFolders = new ArrayList<File>();
    inputFolders.add(folderIO.masterFolder);
    folderIO.inputFolders = inputFolders;
    fileIndexer = new FileIndexer();
    Assert.assertNotNull("Valid folderIO", fileIndexer.runIndexing(folderIO));
  }

}

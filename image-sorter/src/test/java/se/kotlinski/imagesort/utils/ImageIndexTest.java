package se.kotlinski.imagesort.utils;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import se.kotlinski.imagesort.mapper.ImageMapper;
import se.kotlinski.imagesort.model.FolderIO;
import se.kotlinski.imagesort.model.ImageDescriber;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.spy;

/**
 * Created by Simon Kotlinski on 2014-02-19.
 */
public class ImageIndexTest {
  ImageIndex imageIndex;
  private ImageFileUtil imageFileUtil;

  @Before
  public void setUp() {
    imageFileUtil = new ImageFileUtil();

    FolderIO folderIO = new FolderIO();
    File file = new File(imageFileUtil.getTestInputPath());
    ArrayList<File> inputFolders = new ArrayList<File>();
    inputFolders.add(file);
    folderIO.inputFolders = inputFolders;
    folderIO.masterFolder = new File(imageFileUtil.getTestOutputPath());
    imageIndex = new ImageIndex(folderIO);
  }


  @Test
  public void testRunIndex() throws Exception {
    ImageMapper imageMapper = imageIndex.runIndexing();
    Assert.assertEquals("Number of Unique images", 7, imageMapper.getSizeOfUniqueImages());
  }

  @Test
  public void testRunIndexInvalidInput() throws Exception {
    imageIndex = new ImageIndex(null);
    Assert.assertNull("Null for folderIO", imageIndex.runIndexing());
    FolderIO folderIO = new FolderIO();
    imageIndex = new ImageIndex(folderIO);
    Assert.assertNull("Invalid folders in folderIO", imageIndex.runIndexing());

    folderIO.masterFolder = new File("SomeInvalidFilePath");
    ArrayList<File> inputFolders = new ArrayList<File>();
    inputFolders.add(folderIO.masterFolder);
    folderIO.inputFolders = inputFolders;
    imageIndex = new ImageIndex(folderIO);
    Assert.assertNull("Invalid folders in folderIO", imageIndex.runIndexing());

    folderIO.masterFolder = new File(imageFileUtil.getTestOutputPath());
    inputFolders = new ArrayList<File>();
    inputFolders.add(folderIO.masterFolder);
    folderIO.inputFolders = inputFolders;
    imageIndex = new ImageIndex(folderIO);
    Assert.assertNotNull("Valid folderIO", imageIndex.runIndexing());
  }


  @Test
  public void testCopyFiles() throws Exception {
    ImageIndex imageIndexSpy = spy(imageIndex);
    imageIndexSpy.runIndexing();
    doThrow(new IOException()).when(imageIndexSpy).createImageFile(any(ImageDescriber.class), any(
        String.class));
    FileCopyReport fileCopyReport = imageIndexSpy.copyFiles();
    Assert.assertEquals(0, fileCopyReport.getNumberOfFilesCopied());
    Assert.assertEquals(7, fileCopyReport.getFilesNotCopied().size());
  }


}

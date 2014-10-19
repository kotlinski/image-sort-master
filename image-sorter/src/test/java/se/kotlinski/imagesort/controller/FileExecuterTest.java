package se.kotlinski.imagesort.controller;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import se.kotlinski.imagesort.controller.FileExecutor;
import se.kotlinski.imagesort.controller.FileIndexer;
import se.kotlinski.imagesort.exception.InvalidInputFolders;
import se.kotlinski.imagesort.mapper.ImageMapper;
import se.kotlinski.imagesort.model.Describer;
import se.kotlinski.imagesort.model.FileCopyReport;
import se.kotlinski.imagesort.model.FolderIO;
import se.kotlinski.imagesort.utils.ImageFileUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.spy;

public class FileExecuterTest {

  private ImageFileUtil imageFileUtil;
  private FileIndexer fileIndexer;
  private FolderIO folderIO;

  @Before
  public void setUp() throws Exception {
    imageFileUtil = new ImageFileUtil();

    folderIO = new FolderIO();
    File file = new File(imageFileUtil.getTestInputPath());
    ArrayList<File> inputFolders = new ArrayList<File>();
    inputFolders.add(file);
    folderIO.inputFolders = inputFolders;
    folderIO.masterFolder = new File(imageFileUtil.getTestOutputPath());
    fileIndexer = new FileIndexer(folderIO);

  }

  @Test
  public void testCopyFiles() throws Exception {
    FileExecutor fileExecutor = spy(new FileExecutor());
    doThrow(new IOException())
        .when(fileExecutor).createNewFile(any(Describer.class), any(String.class));
    ImageMapper imageMapper = fileIndexer.runIndexing();
    FileCopyReport fileCopyReport = fileExecutor.copyFiles(imageMapper, folderIO);
    Assert.assertEquals(0, fileCopyReport.getNumberOfFilesCopied());
    Assert.assertEquals(8, fileCopyReport.getFilesNotCopied().size());

    File outputFolder = new File(new ImageFileUtil().getTestOutputPath());
    deleteFolderContent(outputFolder);
    imageMapper = fileIndexer.runIndexing();
    fileExecutor.copyFiles(imageMapper, folderIO);
    String[] list = outputFolder.list();
    Assert.assertEquals(4, list.length);
  }

  @Test
  public void sunShineTest() throws InvalidInputFolders {
    FileExecutor fileExecutor = spy(new FileExecutor());

    File outputFolder = new File(new ImageFileUtil().getTestOutputPath());
    deleteFolderContent(outputFolder);
    ImageMapper imageMapper = fileIndexer.runIndexing();
    fileExecutor.copyFiles(imageMapper, folderIO);
    String[] list = outputFolder.list();
    Assert.assertEquals(4, list.length);
  }

  @Test
  public void testCreateImageFile() throws Exception {

  }

  private void deleteFolderContent(File folder) {
    File[] files = folder.listFiles();
    if (files != null) { //some JVMs return null for empty dirs
      for (File file : files) {
        if (file.isDirectory()) {
          deleteFolderContent(file);
        }
        else {
          if(!file.getName().equals(".gitignore"))
          file.delete();
        }
      }
    }
  }
}
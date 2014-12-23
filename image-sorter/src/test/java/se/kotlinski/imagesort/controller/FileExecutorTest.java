package se.kotlinski.imagesort.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import se.kotlinski.imagesort.exception.InvalidInputFolders;
import se.kotlinski.imagesort.mapper.FileDescriberPathComparator;
import se.kotlinski.imagesort.mapper.ImageMapper;
import se.kotlinski.imagesort.model.FileCopyReport;
import se.kotlinski.imagesort.model.FolderIO;
import se.kotlinski.imagesort.utils.ImageFileUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.spy;

public class FileExecutorTest {

  private FileIndexer fileIndexer;
  private FolderIO folderIO;
  private static final Logger logger = LogManager.getLogger(FileExecutorTest.class);


  @Before
  public void setUp() throws Exception {
    ImageFileUtil imageFileUtil = new ImageFileUtil();

    folderIO = new FolderIO();
    File file = new File(imageFileUtil.getTestInputPath());
    ArrayList<File> inputFolders = new ArrayList<>();
    inputFolders.add(file);
    folderIO.inputFolders = inputFolders;
    folderIO.masterFolder = new File(imageFileUtil.getTestOutputPath());
    Calendar calendar = new GregorianCalendar();
    FileDescriberPathComparator fileDescriberPathComparator = new FileDescriberPathComparator();
    fileIndexer = new FileIndexer(imageFileUtil, calendar, fileDescriberPathComparator);

    File outputFolder = new File(new ImageFileUtil().getTestOutputPath());
    deleteFolderContent(outputFolder);
  }

  @After
  public void tearDown() throws Exception {
    File outputFolder = new File(new ImageFileUtil().getTestOutputPath());
    deleteFolderContent(outputFolder);
  }

  @Test
  public void testCopyFiles() throws Exception {
    FileExecutor fileExecutor = spy(new FileExecutor());
    doThrow(new IOException()).when(fileExecutor).createNewFile(any(File.class), any(String.class));
    ImageMapper imageMapper = fileIndexer.runIndexing(folderIO);
    FileCopyReport fileCopyReport = fileExecutor.copyFiles(imageMapper, folderIO);
    Assert.assertEquals(0, fileCopyReport.getNumberOfFilesCopied());
    Assert.assertEquals(9, fileCopyReport.getFilesNotCopied().size());

    File outputFolder = new File(new ImageFileUtil().getTestOutputPath());
    deleteFolderContent(outputFolder);
    imageMapper = fileIndexer.runIndexing(folderIO);
    fileExecutor.copyFiles(imageMapper, folderIO);
    String[] list = outputFolder.list();
    for (String file : list) {
      logger.debug(file);
    }
    Assert.assertEquals(5, list.length);
  }

  @Test
  public void sunShineTest() throws InvalidInputFolders {
    FileExecutor fileExecutor = spy(new FileExecutor());

    File outputFolder = new File(new ImageFileUtil().getTestOutputPath());
    deleteFolderContent(outputFolder);
    ImageMapper imageMapper = fileIndexer.runIndexing(folderIO);
    fileExecutor.copyFiles(imageMapper, folderIO);
    String[] list = outputFolder.list();
    for (String file : list) {
      logger.debug(file);
    }
    Assert.assertEquals(5, list.length);
  }

  private void deleteFolderContent(File folder) {
    File[] files = folder.listFiles();
    if (files != null) { //some JVMs return null for empty dirs
      for (File file : files) {
        if (file.isDirectory()) {
          deleteFolderContent(file);
        }
        else {
          if (!".gitignore".equals(file.getName())) {
            boolean delete = file.delete();
            if (delete) {
              logger.debug("Delete: " + file.getName());
            }
            else {
              logger.debug("Could not delete: " + file.getName());
            }

          }
        }
      }
    }
  }
}
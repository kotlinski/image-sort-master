package se.kotlinski.imagesort.controller;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import se.kotlinski.imagesort.exception.InvalidInputFolders;
import se.kotlinski.imagesort.mapper.FileDescriberPathComperator;
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

public class FileExecuterTest {

  private ImageFileUtil imageFileUtil;
  private FileIndexer fileIndexer;
  private FolderIO folderIO;
  private Calendar calendar;
  private FileDescriberPathComperator fileDescriberPathComperator;

  @Before
  public void setUp() throws Exception {
    imageFileUtil = new ImageFileUtil();

    folderIO = new FolderIO();
    File file = new File(imageFileUtil.getTestInputPath());
    ArrayList<File> inputFolders = new ArrayList<File>();
    inputFolders.add(file);
    folderIO.inputFolders = inputFolders;
    folderIO.masterFolder = new File(imageFileUtil.getTestOutputPath());
    calendar = new GregorianCalendar();
    fileDescriberPathComperator = new FileDescriberPathComperator();
    fileIndexer = new FileIndexer(imageFileUtil, calendar, fileDescriberPathComperator);

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
    doThrow(new IOException()).when(fileExecutor).createNewFile(any(File.class),
                                                                any(String.class));
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
      System.out.println(file);
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
      System.out.println(file);
    }
    Assert.assertEquals(5, list.length);
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
          if (!".gitignore".equals(file.getName())) {
            boolean delete = file.delete();
            if (delete) {
              System.out.println("Delete: " + file.getName());
            }
            else {
              System.out.println("Could not delete: " + file.getName());
            }

          }
        }
      }
    }
  }
}
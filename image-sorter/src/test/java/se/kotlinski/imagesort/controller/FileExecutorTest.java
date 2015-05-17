package se.kotlinski.imagesort.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import se.kotlinski.imagesort.exception.InvalidInputFolders;
import se.kotlinski.imagesort.mapper.ExportFileDataMap;
import se.kotlinski.imagesort.model.FileCopyReport;
import se.kotlinski.imagesort.model.FolderIO;
import se.kotlinski.imagesort.utils.DateToFileRenamer;
import se.kotlinski.imagesort.utils.FileDateInterpreter;
import se.kotlinski.imagesort.utils.FileDateUniqueGenerator;
import se.kotlinski.imagesort.utils.FileDescriptor;
import se.kotlinski.imagesort.utils.SortMasterFileUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.nullValue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class FileExecutorTest {

  private static final Logger logger = LogManager.getLogger(FileExecutorTest.class);
  private FileAnalyzer fileAnalyzer;
  private FolderIO folderIO;
  private FileDateUniqueGenerator fileDateUniqueGenerator;
  private FileDateInterpreter fileDateInterpreter;
  private DateToFileRenamer dateToFileRenamer;
  private FileDescriptor fileDescriptor;


  @Before
  public void setUp() throws Exception {
    SortMasterFileUtil sortMasterFileUtil = new SortMasterFileUtil();

    folderIO = new FolderIO();
    File file = new File(sortMasterFileUtil.getTestInputPath());
    ArrayList<File> inputFolders = new ArrayList<>();
    inputFolders.add(file);
    folderIO.inputFolders = inputFolders;
    folderIO.masterFolder = new File(sortMasterFileUtil.getTestOutputPath());
    Calendar calendar = new GregorianCalendar();
    ExportForecaster exportForecaster = mock(ExportForecaster.class);
    fileDateUniqueGenerator = spy(new FileDateUniqueGenerator());
    fileDateInterpreter = mock(FileDateInterpreter.class);
    fileDescriptor = mock(FileDescriptor.class);
    dateToFileRenamer = mock(DateToFileRenamer.class);
    fileAnalyzer = new FileAnalyzer(sortMasterFileUtil,
                                    calendar,
                                    fileDateUniqueGenerator,
                                    fileDateInterpreter,
                                    fileDescriptor,
                                    dateToFileRenamer,
                                    exportForecaster);

    File outputFolder = new File(new SortMasterFileUtil().getTestOutputPath());
    deleteFolderContent(outputFolder);
  }

  @After
  public void tearDown() throws Exception {
    File outputFolder = new File(new SortMasterFileUtil().getTestOutputPath());
    deleteFolderContent(outputFolder);
  }

  @Test
  public void testCopyFiles() throws Exception {
    FileExecutor fileExecutor = spy(new FileExecutor());
    doThrow(new IOException()).when(fileExecutor).createNewFile(any(File.class), any(String.class));
    ExportFileDataMap exportFileDataMap = fileAnalyzer.createParsedFileMap(folderIO);
    FileCopyReport fileCopyReport = fileExecutor.copyFiles(exportFileDataMap, folderIO);
    assertThat(fileCopyReport, is(nullValue()));
/*    Assert.assertEquals(0, fileCopyReport.getNumberOfFilesCopied());
    Assert.assertEquals(9, fileCopyReport.getFilesNotCopied().size());

    File outputFolder = new File(new SortMasterFileUtil().getTestOutputPath());
    deleteFolderContent(outputFolder);
    ExportFileDataMap exportFileDataMap2 = fileAnalyzer.createParsedFileMap(folderIO);
    fileExecutor.copyFiles(exportFileDataMap2, folderIO);
    String[] list = outputFolder.list();
    for (String file : list) {
      logger.debug(file);
    }
    Assert.assertEquals(5, list.length);*/
  }

  @Test
  public void sunShineTest() throws InvalidInputFolders {
    FileExecutor fileExecutor = spy(new FileExecutor());

    File outputFolder = new File(new SortMasterFileUtil().getTestOutputPath());
    deleteFolderContent(outputFolder);

    ExportFileDataMap exportFileDataMap = fileAnalyzer.createParsedFileMap(folderIO);
    fileExecutor.copyFiles(exportFileDataMap, folderIO);
    String[] list = outputFolder.list();
    for (String file : list) {
      logger.debug(file);
    }
    Assert.assertEquals(1, list.length);
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
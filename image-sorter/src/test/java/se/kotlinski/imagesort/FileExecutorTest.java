package se.kotlinski.imagesort;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import se.kotlinski.imagesort.data.SortSettings;
import se.kotlinski.imagesort.forecaster.date.DateToFileRenamer;
import se.kotlinski.imagesort.forecaster.date.FileDateInterpreter;
import se.kotlinski.imagesort.utils.MediaFileHashGenerator;
import se.kotlinski.imagesort.utils.MediaFileTestUtil;
import se.kotlinski.imagesort.utils.MediaFileUtil;

import java.io.File;
import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

public class FileExecutorTest {

  private static final Logger LOGGER = LogManager.getLogger(FileExecutorTest.class);
  private SortSettings sortSettings;
  private MediaFileTestUtil mediaFileTestUtil;


  @Before
  public void setUp() throws Exception {
    MediaFileUtil mediaFileUtil = new MediaFileUtil();
    mediaFileTestUtil = new MediaFileTestUtil(mediaFileUtil);

    sortSettings = new SortSettings();
    File file = new File(mediaFileTestUtil.getTestInputPath());
    sortSettings.masterFolder = new File(mediaFileTestUtil.getRestorableTestMasterPath());
    Calendar calendar = new GregorianCalendar();
    MediaFileHashGenerator mediaFileHashGenerator = spy(new MediaFileHashGenerator());
    FileDateInterpreter fileDateInterpreter = mock(FileDateInterpreter.class);
    DateToFileRenamer dateToFileRenamer = mock(DateToFileRenamer.class);

  }


  @After
  public void tearDown() throws Exception {
    mediaFileTestUtil.cleanRestoreableMasterFolder();
  }

  @Test
  public void testCopyFiles() throws Exception {
/*    FileExecutor fileExecutor = spy(new FileExecutor());
    doThrow(new IOException()).when(fileExecutor).createNewFile(any(File.class), any(String.class));
    DeprecatedExportFileDataMap exportFileDataMap = mediaFileParser.transformFilesToMediaFiles(sortSettings);
    DeprecatedFileCopyReport fileCopyReport = fileExecutor.copyFiles(exportFileDataMap, sortSettings);
    assertThat(fileCopyReport, is(nullValue()));
    Assert.assertEquals(0, fileCopyReport.getNumberOfFilesCopied());
    Assert.assertEquals(9, fileCopyReport.getFilesNotCopied().size());

    File outputFolder = new File(new MediaFileUtil().getRestorableTestMasterPath());
    deleteFolderContent(outputFolder);
    DeprecatedExportFileDataMap exportFileDataMap2 = mediaFileParser.transformFilesToMediaFiles(sortSettings);
    fileExecutor.copyFiles(exportFileDataMap2, sortSettings);
    String[] list = outputFolder.list();
    for (String file : list) {
      LOGGER.debug(file);
    }
    Assert.assertEquals(5, list.length);*/
  }

  @Test
  public void sunShineTest() throws Exception {
/*
    DeprecatedExportFileDataMap exportFileDataMap = mediaFileParser.transformFilesToMediaFiles(sortSettings);
    fileExecutor.copyFiles(exportFileDataMap, sortSettings);
    String[] list = outputFolder.list();
    for (String file : list) {
      LOGGER.debug(file);
    }
    Assert.assertEquals(1, list.length);*/
  }

}
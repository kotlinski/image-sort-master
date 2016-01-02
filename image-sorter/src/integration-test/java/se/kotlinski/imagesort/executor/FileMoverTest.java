package se.kotlinski.imagesort.executor;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import se.kotlinski.imagesort.data.RelativeMediaFolderOutput;
import se.kotlinski.imagesort.data.SortSettings;
import se.kotlinski.imagesort.forecaster.MediaFileOutputForecaster;
import se.kotlinski.imagesort.forecaster.date.DateToFileRenamer;
import se.kotlinski.imagesort.forecaster.date.FileDateInterpreter;
import se.kotlinski.imagesort.main.ClientInterface;
import se.kotlinski.imagesort.main.ImageSorter;
import se.kotlinski.imagesort.mapper.MediaFileDataMapper;
import se.kotlinski.imagesort.mapper.OutputMapper;
import se.kotlinski.imagesort.resolver.OutputConflictResolver;
import se.kotlinski.imagesort.utils.MediaFileHashGenerator;
import se.kotlinski.imagesort.utils.MediaFileTestUtil;
import se.kotlinski.imagesort.utils.MediaFileUtil;

import java.io.File;
import java.util.GregorianCalendar;
import java.util.List;

import static org.mockito.Mockito.mock;

public class FileMoverTest {

  private MediaFileUtil mediaFileUtil;
  private MediaFileTestUtil mediaFileTestUtil;
  private ImageSorter imageSorter;
  private FileMover fileMover;
  private MediaFileOutputForecaster mediaFileOutputForecaster;
  private ClientInterface clientInterface;

  @Before
  public void setUp() throws Exception {
    clientInterface = mock(ClientInterface.class);

    mediaFileUtil = new MediaFileUtil();
    mediaFileTestUtil = new MediaFileTestUtil(mediaFileUtil);


    mediaFileTestUtil.cleanRestoreableMasterFolder();
    mediaFileTestUtil.copyTestFilesToRestoreableDirectory();

    // TODO :
    // Clean output-folder
    // Copy all files from inputImages-folder to output-folder.
    // Do the move-operations on output-folder.

    OutputConflictResolver outputConflictResolver = new OutputConflictResolver(new MediaFileHashGenerator(),
                                                                               mediaFileUtil);
    fileMover = new FileMover();
    MediaFileDataMapper mediaFileHashMapTransformer;
    mediaFileHashMapTransformer = new MediaFileDataMapper(new MediaFileHashGenerator());

    DateToFileRenamer dateToFileRenamer = new DateToFileRenamer(new GregorianCalendar());
    mediaFileOutputForecaster = new MediaFileOutputForecaster(dateToFileRenamer,
                                                              new FileDateInterpreter());

    OutputMapper mediaOutputCalculator;
    mediaOutputCalculator = new OutputMapper(mediaFileOutputForecaster);

    imageSorter = new ImageSorter(mediaFileUtil,
                                  mediaFileHashMapTransformer,
                                  mediaOutputCalculator,
                                  outputConflictResolver,
                                  fileMover);
  }

  @After
  public void tearDown() throws Exception {
    mediaFileTestUtil.cleanRestoreableMasterFolder();
  }

  @Test
  public void testMoveFilesToNewDestionationWhenHavingConflictingNames() throws Exception {
    //TODO the test defined below

    mediaFileTestUtil.cleanRestoreableMasterFolder();

    File testInputFile = mediaFileTestUtil.getTestInputFile();
    File restorableTestMasterPath = mediaFileTestUtil.getRestorableTestMasterFile();

    File snapchatFile = mediaFileTestUtil.getSnapchatFile();
    File instagramFile = mediaFileTestUtil.getInstagramFile();


    RelativeMediaFolderOutput snapchatOutputDestination;
    snapchatOutputDestination = mediaFileOutputForecaster.forecastOutputDestination(testInputFile,
                                                                                    snapchatFile);
    RelativeMediaFolderOutput instagramOutputDestination;
    instagramOutputDestination = mediaFileOutputForecaster.forecastOutputDestination(testInputFile,
                                                                                     instagramFile);

    File instagramOuputFile = FileUtils.getFile(restorableTestMasterPath +
                                                instagramOutputDestination.relativePath);
    File snapchatOutputFile = FileUtils.getFile(restorableTestMasterPath +
                                                snapchatOutputDestination.relativePath);
    FileUtils.copyFile(snapchatFile, instagramOuputFile);
    FileUtils.copyFile(instagramFile, snapchatOutputFile);

    SortSettings sortSettings = new SortSettings();
    sortSettings.masterFolder = FileUtils.getFile(restorableTestMasterPath);
    imageSorter.sortImages(clientInterface, sortSettings);

    List<File> filesInFolder = mediaFileUtil.getMediaFilesInFolder(clientInterface,
                                                                   sortSettings.masterFolder);
    System.out.println(filesInFolder);
    for (File file : filesInFolder) {
      System.out.println(file);
      RelativeMediaFolderOutput dateOutput;
      dateOutput = mediaFileOutputForecaster.forecastOutputDestination(restorableTestMasterPath,
                                                                       file);
      System.out.println(dateOutput);
    }
    //assertThat();
    //fileMover.moveFilesToNewDestionation();
  }


}
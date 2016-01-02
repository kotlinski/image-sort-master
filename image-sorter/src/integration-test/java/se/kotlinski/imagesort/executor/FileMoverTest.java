package se.kotlinski.imagesort.executor;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import se.kotlinski.imagesort.calculator.MediaInFolderCalculator;
import se.kotlinski.imagesort.data.SortSettings;
import se.kotlinski.imagesort.forecaster.MediaFileForecaster;
import se.kotlinski.imagesort.forecaster.MediaFilesOutputForecaster;
import se.kotlinski.imagesort.main.ImageSorter;
import se.kotlinski.imagesort.mapper.MediaFileDataMapper;
import se.kotlinski.imagesort.parser.MediaFileParser;
import se.kotlinski.imagesort.resolver.OutputConflictResolver;
import se.kotlinski.imagesort.utils.DateToFileRenamer;
import se.kotlinski.imagesort.utils.FileDateInterpreter;
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
  private DateToFileRenamer dateToFileRenamer;
  private MediaFileForecaster mediaFileForecaster;
  private ClientInterface clientInterface;

  @Before
  public void setUp() throws Exception {
    MediaInFolderCalculator mediaInFolderCalculator = new MediaInFolderCalculator();
    clientInterface = mock(ClientInterface.class);

    mediaFileUtil = new MediaFileUtil();
    mediaFileTestUtil = new MediaFileTestUtil(mediaFileUtil);


    mediaFileTestUtil.cleanRestoreableMasterFolder();
    mediaFileTestUtil.copyTestFilesToRestoreableDirectory();

    // TODO :
    // Clean output-folder
    // Copy all files from inputImages-folder to output-folder.
    // Do the move-operations on output-folder.

    ClientInterface clientInterface = mock(ClientInterface.class);
    MediaFileParser mediaFileParser = new MediaFileParser(mediaFileUtil);
    dateToFileRenamer = new DateToFileRenamer(new GregorianCalendar());
    FileDateInterpreter fileDateInterpreter = new FileDateInterpreter();
    OutputConflictResolver outputConflictResolver = new OutputConflictResolver(new MediaFileHashGenerator(),
                                                                               mediaFileUtil);
    fileMover = new FileMover(mediaFileUtil);
    MediaFileDataMapper mediaFileHashMapTransformer;
    mediaFileHashMapTransformer = new MediaFileDataMapper(new MediaFileHashGenerator());

    DateToFileRenamer dateToFileRenamer = new DateToFileRenamer(new GregorianCalendar());
    mediaFileForecaster = new MediaFileForecaster(dateToFileRenamer, new FileDateInterpreter());

    MediaFilesOutputForecaster mediaOutputCalculator;
    mediaOutputCalculator = new MediaFilesOutputForecaster(mediaFileForecaster);

    imageSorter = new ImageSorter(clientInterface,
                                  mediaFileParser,
                                  mediaFileHashMapTransformer,
                                  mediaOutputCalculator, outputConflictResolver,
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

    String testInputPath = mediaFileTestUtil.getTestInputPath();
    String restorableTestMasterPath = mediaFileTestUtil.getRestorableTestMasterPath();

    File snapchatFile = mediaFileTestUtil.getSnapchatFile();
    File instagramFile = mediaFileTestUtil.getInstagramFile();


    String snapchatOutputDestination = mediaFileForecaster.forecastOutputDestination(snapchatFile,
                                                                                     testInputPath);
    String instagramOutputDestination = mediaFileForecaster.forecastOutputDestination(instagramFile,
                                                                                      testInputPath);

    FileUtils.copyFile(snapchatFile,
                       FileUtils.getFile(restorableTestMasterPath + instagramOutputDestination));
    FileUtils.copyFile(instagramFile,
                       FileUtils.getFile(restorableTestMasterPath + snapchatOutputDestination));

    SortSettings sortSettings = new SortSettings();
    sortSettings.masterFolder = FileUtils.getFile(restorableTestMasterPath);
    imageSorter.sortImages(sortSettings);

    List<File> filesInFolder = mediaFileUtil.getMediaFilesInFolder(sortSettings.masterFolder,
                                                                   clientInterface);
    System.out.println(filesInFolder);
    for (File file : filesInFolder) {
      System.out.println(file);
      String dateOutput = mediaFileForecaster.forecastOutputDestination(file,
                                                                        restorableTestMasterPath);
      System.out.println(dateOutput);
    }
    //assertThat();
    //fileMover.moveFilesToNewDestionation();
  }


}
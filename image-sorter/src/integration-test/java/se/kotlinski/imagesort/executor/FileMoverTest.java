package se.kotlinski.imagesort.executor;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import se.kotlinski.imagesort.calculator.MediaInFolderCalculator;
import se.kotlinski.imagesort.data.MediaFileDataInFolder;
import se.kotlinski.imagesort.forecaster.MediaFileForecaster;
import se.kotlinski.imagesort.forecaster.MediaFilesOutputForecaster;
import se.kotlinski.imagesort.parser.MediaFileParser;
import se.kotlinski.imagesort.resolver.OutputConflictResolver;
import se.kotlinski.imagesort.utils.DateToFileRenamer;
import se.kotlinski.imagesort.utils.FileDateInterpreter;
import se.kotlinski.imagesort.utils.MD5Generator;
import se.kotlinski.imagesort.utils.MediaFileTestUtil;
import se.kotlinski.imagesort.utils.MediaFileUtil;

import java.io.File;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

public class FileMoverTest {

  private MediaInFolderCalculator mediaInFolderCalculator;
  private MediaFileUtil mediaFileUtil;
  private MD5Generator md5Generator;
  private Map<String, List<File>> mediaFilesInFolder;
  private MediaFileTestUtil mediaFileTestUtil;
  private FileMover fileMover;
  private OutputConflictResolver outputConflictResolver;
  private MediaFilesOutputForecaster mediaFilesOutputForecaster;
  private Map<List<File>, String> resolvedOutputConflicts;

  @Before
  public void setUp() throws Exception {
    mediaInFolderCalculator = new MediaInFolderCalculator();

    mediaFileUtil = new MediaFileUtil();
    mediaFileTestUtil = new MediaFileTestUtil(mediaFileUtil);


    mediaFileTestUtil.cleanRestoreableMasterFolder();
    mediaFileTestUtil.copyTestFilesToOutputDirectory();

    // TODO :
    // Clean output-folder
    // Copy all files from inputImages-folder to output-folder.
    // Do the move-operations on output-folder.

    md5Generator = new MD5Generator();

    File masterFolder = mediaFileTestUtil.getRestorableTestMasterFile();

    MediaFileParser mediaFileParser = new MediaFileParser(mediaFileUtil, md5Generator);
    mediaFilesInFolder = mediaFileParser.getMediaFilesInFolder(masterFolder);


    File restorableTestMasterFile = mediaFileTestUtil.getRestorableTestMasterFile();
    String restoreableMasterPath = mediaFileTestUtil.getRestorableTestMasterPath();

    Map<String, List<File>> parsedMediaFiles;
    parsedMediaFiles = mediaFileTestUtil.getParsedMediaFiles(restorableTestMasterFile);


    MediaFileForecaster mediaFileForecaster = new MediaFileForecaster(new DateToFileRenamer(new GregorianCalendar()),
                                                                      new FileDateInterpreter());
    mediaFilesOutputForecaster = new MediaFilesOutputForecaster(mediaFileForecaster);
    Map<String, List<File>> mediaFileDestinations;
    mediaFileDestinations = mediaFilesOutputForecaster.calculateOutputDestinations(parsedMediaFiles,
                                                                                   restoreableMasterPath);

    outputConflictResolver = new OutputConflictResolver(new MD5Generator(), new MediaFileUtil());
    resolvedOutputConflicts = outputConflictResolver.resolveOutputConflicts(mediaFileDestinations);


    fileMover = new FileMover(mediaFileUtil);

  }

  @After
  public void tearDown() throws Exception {
    // mediaFileTestUtil.cleanRestoreableMasterFolder();


  }

  @Test
  public void testMoveFilesToNewDestionation() throws Exception {
    String restoreableMasterPath = mediaFileTestUtil.getRestorableTestMasterPath();

    fileMover.moveFilesToNewDestionation(resolvedOutputConflicts, restoreableMasterPath);
  }

  @Test
  public void testMoveFilesToNewDestionationWhenHavingConflictingNames() throws Exception {
    //TODO the test defined below

    File masterFolder = mediaFileTestUtil.getRestorableTestMasterFile();
    File inputMasterFolderFile = mediaFileTestUtil.getTestInputFile();

    File snapchatFile = mediaFileTestUtil.getSnapchatFile();
    File instagramFile = mediaFileTestUtil.getInstagramFile();



/*
    Fila A;
    File B;

    String exportDestionationA;
    String exportDestionationB;

    moveA to exportDestionationB;
    moveB to exportDestionationA;*/

    // TODO
    // Don't pass around relative strings, try to pass around absolute string-paths.
    //    or even try to pass around files.


  }


}
package se.kotlinski.imagesort.resolver;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import se.kotlinski.imagesort.data.MediaFileDataHash;
import se.kotlinski.imagesort.data.RelativeMediaFolderOutput;
import se.kotlinski.imagesort.feedback.MoveFeedbackInterface;
import se.kotlinski.imagesort.feedback.PreMoveFeedbackInterface;
import se.kotlinski.imagesort.feedback.ReadFilesFeedbackInterface;
import se.kotlinski.imagesort.forecaster.MediaFileOutputForecaster;
import se.kotlinski.imagesort.forecaster.date.DateToFileRenamer;
import se.kotlinski.imagesort.forecaster.date.FileDateInterpreter;
import se.kotlinski.imagesort.mapper.MediaFileToOutputMapper;
import se.kotlinski.imagesort.mapper.OutputToMediaFileMapper;
import se.kotlinski.imagesort.utils.MediaFileHashGenerator;
import se.kotlinski.imagesort.utils.MediaFileTestUtil;
import se.kotlinski.imagesort.utils.MediaFileUtil;

import java.io.File;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;

public class ExistingFilesResolverTest {

  private MediaFileUtil mediaFileUtil;
  private MediaFileTestUtil mediaFileTestUtil;
  private MediaFileOutputForecaster mediaFileOutputForecaster;
  private PreMoveFeedbackInterface preMoveFeedbackInterface;
  private MoveFeedbackInterface moveFeedbackInterface;
  private ReadFilesFeedbackInterface readFilesFeedbackInterface;

  @Before
  public void setUp() throws Exception {
    preMoveFeedbackInterface = mock(PreMoveFeedbackInterface.class);
    readFilesFeedbackInterface = mock(ReadFilesFeedbackInterface.class);
    moveFeedbackInterface = mock(MoveFeedbackInterface.class);

    mediaFileUtil = new MediaFileUtil();
    mediaFileTestUtil = new MediaFileTestUtil(mediaFileUtil);


    mediaFileTestUtil.cleanRestoreableMasterFolder();
    mediaFileTestUtil.copyTestFilesToRestoreableDirectory();


    DateToFileRenamer dateToFileRenamer = new DateToFileRenamer(new GregorianCalendar());
    FileDateInterpreter fileDateInterpreter = new FileDateInterpreter();
    mediaFileOutputForecaster = new MediaFileOutputForecaster(dateToFileRenamer,
                                                              fileDateInterpreter);


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
    File restorableTestMasterFile = mediaFileTestUtil.getRestorableTestMasterFile();

    File snapchatFile = mediaFileTestUtil.getSnapchatFile();
    File instagramFile = mediaFileTestUtil.getInstagramFile();


    RelativeMediaFolderOutput snapchatOutputDestination;

    snapchatOutputDestination = mediaFileOutputForecaster.forecastOutputDestination(testInputFile,
                                                                                    snapchatFile);
    String newOutput;
    newOutput = snapchatOutputDestination.relativePath.replace("snapchat" + File.separator, "");
    snapchatOutputDestination = new RelativeMediaFolderOutput(newOutput);


    RelativeMediaFolderOutput instagramOutputDestination;
    instagramOutputDestination = mediaFileOutputForecaster.forecastOutputDestination(testInputFile,
                                                                                     instagramFile);
    newOutput = instagramOutputDestination.relativePath.replace("instagram" + File.separator, "");
    instagramOutputDestination = new RelativeMediaFolderOutput(newOutput);


    File instagramOutputFile = FileUtils.getFile(restorableTestMasterFile +
                                                 instagramOutputDestination.relativePath);
    File snapchatOutputFile = FileUtils.getFile(restorableTestMasterFile +
                                                snapchatOutputDestination.relativePath);
    FileUtils.copyFile(snapchatFile, instagramOutputFile);
    FileUtils.copyFile(instagramFile, snapchatOutputFile);


    MediaFileHashGenerator mediaFileHashGenerator = new MediaFileHashGenerator();


    List<File> mediaFiles = mediaFileUtil.getMediaFilesInFolder(readFilesFeedbackInterface,
                                                                restorableTestMasterFile);

    OutputToMediaFileMapper mediaOutputMapper;
    mediaOutputMapper = new OutputToMediaFileMapper(mediaFileOutputForecaster);
    MediaFileToOutputMapper mediaFileToOutputMapper;
    mediaFileToOutputMapper = new MediaFileToOutputMapper(mediaFileHashGenerator, mediaFileUtil);


    Map<RelativeMediaFolderOutput, List<File>> mediaFileDestinations;
    mediaFileDestinations = mediaOutputMapper.calculateOutputDestinations(preMoveFeedbackInterface,
                                                                          restorableTestMasterFile,
                                                                          mediaFiles);

    Map<List<File>, RelativeMediaFolderOutput> fileMapWithResolvedConflicts;
    fileMapWithResolvedConflicts = mediaFileToOutputMapper.mapRelativeOutputsToFiles(preMoveFeedbackInterface,
                                                                              mediaFileDestinations);

    moveFeedbackInterface.startResolvingConflicts();
    ConflictResolver conflictResolver = new ConflictResolver(mediaFileToOutputMapper,
                                                             new FileSkipper(),
                                                             new ExistingFilesResolver(mediaFileUtil));

    conflictResolver.resolveOutputConflicts(moveFeedbackInterface,
                                            restorableTestMasterFile,
                                            fileMapWithResolvedConflicts);

    assertThat(fileMapWithResolvedConflicts.size(), is(2));
    for (Map.Entry<List<File>, RelativeMediaFolderOutput> listRelativeMediaFolderOutputEntry : fileMapWithResolvedConflicts.entrySet()) {
      List<File> key = listRelativeMediaFolderOutputEntry.getKey();
      String relativePath = listRelativeMediaFolderOutputEntry.getValue().relativePath;

      if (relativePath.equals(snapchatOutputDestination.relativePath)) {
        File file = key.get(0);
        MediaFileDataHash newFileHash = mediaFileHashGenerator.generateMediaFileDataHash(file);
        MediaFileDataHash originalFileHash;
        originalFileHash = mediaFileHashGenerator.generateMediaFileDataHash(snapchatFile);
        assertThat(newFileHash, is(originalFileHash));
      }
      else if (relativePath.equals(instagramOutputDestination.relativePath)) {
        File file = key.get(0);
        MediaFileDataHash newFileHash = mediaFileHashGenerator.generateMediaFileDataHash(file);
        MediaFileDataHash originalFileHash;
        originalFileHash = mediaFileHashGenerator.generateMediaFileDataHash(instagramFile);
        assertThat(newFileHash, is(originalFileHash));
      }
      else {
        assert false;
      }
    }

  }

}
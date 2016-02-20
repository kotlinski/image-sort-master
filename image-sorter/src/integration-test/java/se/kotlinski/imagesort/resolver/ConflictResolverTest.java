package se.kotlinski.imagesort.resolver;

import com.mixpanel.mixpanelapi.MessageBuilder;
import com.mixpanel.mixpanelapi.MixpanelAPI;
import org.junit.Before;
import org.junit.Test;
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
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;

public class ConflictResolverTest {

  Map<RelativeMediaFolderOutput, List<File>> mediaFileDestinations;
  private ConflictResolver conflictResolver;
  private ReadFilesFeedbackInterface readFilesFeedbackInterface;
  private PreMoveFeedbackInterface preMoveFeedback;
  private MoveFeedbackInterface moveFeedbackInterface;
  private Map<List<File>, RelativeMediaFolderOutput> fileMapWithResolvedConflicts;

  @Before
  public void setUp() throws Exception {
    readFilesFeedbackInterface = mock(ReadFilesFeedbackInterface.class);
    preMoveFeedback = mock(PreMoveFeedbackInterface.class);
    moveFeedbackInterface = mock(MoveFeedbackInterface.class);

    MediaFileUtil mediaFileUtil = new MediaFileUtil();
    MediaFileToOutputMapper mediaFileToOutputMapper;
    MediaFileHashGenerator mediaFileDataHash = new MediaFileHashGenerator();
    mediaFileToOutputMapper = new MediaFileToOutputMapper(mediaFileDataHash, mediaFileUtil);
    FileSkipper fileSkipper = new FileSkipper();
    ExistingFilesResolver existingFilesResolver = new ExistingFilesResolver(mediaFileUtil);
    conflictResolver = new ConflictResolver(mediaFileToOutputMapper,
                                            fileSkipper,
                                            existingFilesResolver);
    MediaFileTestUtil mediaFileTestUtil = new MediaFileTestUtil(mediaFileUtil);

    Calendar calendar = new GregorianCalendar();
    DateToFileRenamer dateToFileRenamer = new DateToFileRenamer(calendar);


    MessageBuilder messageBuilder = mock(MessageBuilder.class);
    MixpanelAPI mixpanel = mock(MixpanelAPI.class);
    FileDateInterpreter fileDateInterpreter = new FileDateInterpreter(mixpanel,
                                                                      "",
                                                                      messageBuilder);
    MediaFileOutputForecaster mediaFileOutputForecaster;
    mediaFileOutputForecaster = new MediaFileOutputForecaster(dateToFileRenamer,
                                                              fileDateInterpreter);

    OutputToMediaFileMapper outputToMediaFileMapper = new OutputToMediaFileMapper(
        mediaFileOutputForecaster);

    File testInputFile = mediaFileTestUtil.getTestInputFile();

    List<File> mediaFiles = mediaFileTestUtil.getMediaFiles(readFilesFeedbackInterface,
                                                            testInputFile);

    mediaFileDestinations = outputToMediaFileMapper.calculateOutputDestinations(preMoveFeedback,
                                                                                testInputFile,
                                                                                mediaFiles);

    fileMapWithResolvedConflicts = mediaFileToOutputMapper.mapRelativeOutputsToFiles(preMoveFeedback,
                                                                                     mediaFileDestinations);
  }

  @Test
  public void testResolveOutputConflicts() throws Exception {
    MediaFileTestUtil mediaFileTestUtil = new MediaFileTestUtil(new MediaFileUtil());

    conflictResolver.resolveOutputConflicts(moveFeedbackInterface,
                                            mediaFileTestUtil.getTestInputFile(),
                                            fileMapWithResolvedConflicts);

    for (RelativeMediaFolderOutput relativeMediaFolderOutput : fileMapWithResolvedConflicts.values()) {
      System.out.println(relativeMediaFolderOutput);
    }
    Collection<RelativeMediaFolderOutput> outputPaths = fileMapWithResolvedConflicts.values();
    assertThat(fileMapWithResolvedConflicts.size(), is(13));
    assertThat(outputPaths.size(), is(13));

    RelativeMediaFolderOutput mediaFile = new RelativeMediaFolderOutput(File.separator +
                                                                        "2015" +
                                                                        File.separator +
                                                                        "06" +
                                                                        File.separator +
                                                                        "printscreens" +
                                                                        File.separator +
                                                                        "2015-06-05 21.19.28.png");
    assertThat(outputPaths.contains(mediaFile), is(true));

    mediaFile = new RelativeMediaFolderOutput(File.separator + "2013" +
                                              File.separator + "10" +
                                              File.separator + "snapchat" +
                                              File.separator + "2013-10-03 13.43.20.jpg");
    assertThat(outputPaths.contains(mediaFile), is(true));

    mediaFile = new RelativeMediaFolderOutput(File.separator + "2014" +
                                              File.separator + "03" +
                                              File.separator + "2014-03-02 01.09.34.jpg");
    assertThat(outputPaths.contains(mediaFile), is(true));

    mediaFile = new RelativeMediaFolderOutput(File.separator + "2014" +
                                              File.separator + "02" +
                                              File.separator + "2014-02-22 11.48.47_1.jpg");
    assertThat(outputPaths.contains(mediaFile), is(true));

    mediaFile = new RelativeMediaFolderOutput(File.separator + "2013" +
                                              File.separator + "10" +
                                              File.separator + "instagram" +
                                              File.separator + "2013-10-26 20.20.46.jpg");
    assertThat(outputPaths.contains(mediaFile), is(true));

    mediaFile = new RelativeMediaFolderOutput(File.separator + "2007" +
                                              File.separator + "06" +
                                              File.separator + "2007-06-15 17.41.19.jpg");
    assertThat(outputPaths.contains(mediaFile), is(true));

    mediaFile = new RelativeMediaFolderOutput(File.separator + "2014" +
                                              File.separator + "02" +
                                              File.separator + "2014-02-22 11.48.48.jpg");
    assertThat(outputPaths.contains(mediaFile), is(true));

    mediaFile = new RelativeMediaFolderOutput(File.separator + "2014" +
                                              File.separator + "nixon on raindeer - no date.jpg");
    assertThat(outputPaths.contains(mediaFile), is(true));

    mediaFile = new RelativeMediaFolderOutput(File.separator + "noxon on raindeer - no date.jpg");
    assertThat(outputPaths.contains(mediaFile), is(true));

    mediaFile = new RelativeMediaFolderOutput(File.separator + "2014" +
                                              File.separator + "03" +
                                              File.separator + "2014-03-16 11.45.09.mp4");
    assertThat(outputPaths.contains(mediaFile), is(true));

    mediaFile = new RelativeMediaFolderOutput(File.separator + "2014" +
                                              File.separator + "02" +
                                              File.separator + "duplicate in subfolder" +
                                              File.separator + "2014-02-22 11.48.48.jpg");
    assertThat(outputPaths.contains(mediaFile), is(true));

    mediaFile = new RelativeMediaFolderOutput(File.separator + "2014" +
                                              File.separator + "02" +
                                              File.separator + "2014-02-22 11.48.47_2.jpg");
    assertThat(outputPaths.contains(mediaFile), is(true));
  }
}
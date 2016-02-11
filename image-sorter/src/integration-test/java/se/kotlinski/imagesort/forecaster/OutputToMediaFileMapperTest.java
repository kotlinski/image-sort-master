package se.kotlinski.imagesort.forecaster;

import org.junit.Before;
import org.junit.Test;
import se.kotlinski.imagesort.data.RelativeMediaFolderOutput;
import se.kotlinski.imagesort.forecaster.date.DateToFileRenamer;
import se.kotlinski.imagesort.forecaster.date.FileDateInterpreter;
import se.kotlinski.imagesort.feedback.ReadFilesFeedbackInterface;
import se.kotlinski.imagesort.mapper.OutputToMediaFileMapper;
import se.kotlinski.imagesort.utils.MediaFileTestUtil;
import se.kotlinski.imagesort.utils.MediaFileUtil;

import java.io.File;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;


public class OutputToMediaFileMapperTest {
  private OutputToMediaFileMapper outputToMediaFileMapper;
  private MediaFileTestUtil mediaFileTestUtil;
  private ReadFilesFeedbackInterface readFilesFeedbackInterface;

  @Before
  public void setUp() throws Exception {
    readFilesFeedbackInterface = mock(ReadFilesFeedbackInterface.class);

    MediaFileUtil mediaFileUtil = new MediaFileUtil();
    mediaFileTestUtil = new MediaFileTestUtil(mediaFileUtil);

    Calendar calendar = new GregorianCalendar();
    DateToFileRenamer dateToFileRenamer = new DateToFileRenamer(calendar);
    FileDateInterpreter fileDateInterpreter = new FileDateInterpreter();
    MediaFileOutputForecaster mediaFileOutputForecaster;
    mediaFileOutputForecaster = new MediaFileOutputForecaster(dateToFileRenamer,
                                                              fileDateInterpreter);

    outputToMediaFileMapper = new OutputToMediaFileMapper(mediaFileOutputForecaster);
  }

  @Test
  public void testCalculateOutputDestinations() throws Exception {

    File testInputFile = mediaFileTestUtil.getTestInputFile();
    List<File> mediaFiles = mediaFileTestUtil.getMediaFiles(readFilesFeedbackInterface, testInputFile);

    Map<RelativeMediaFolderOutput, List<File>> relativeOutputMap;
    relativeOutputMap = outputToMediaFileMapper.calculateOutputDestinations(preMoveFeedback,
                                                                            testInputFile,
                                                                            mediaFiles);
    assertThat(relativeOutputMap.size(), is(11));
  }

}
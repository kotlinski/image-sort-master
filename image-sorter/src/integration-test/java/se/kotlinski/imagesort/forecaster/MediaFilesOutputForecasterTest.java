package se.kotlinski.imagesort.forecaster;

import org.junit.Before;
import org.junit.Test;
import se.kotlinski.imagesort.utils.DateToFileRenamer;
import se.kotlinski.imagesort.utils.FileDateInterpreter;
import se.kotlinski.imagesort.utils.MediaFileTestUtil;
import se.kotlinski.imagesort.utils.MediaFileUtil;

import java.io.File;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;


public class MediaFilesOutputForecasterTest {
  private MediaFilesOutputForecaster mediaFilesOutputForecaster;
  private MediaFileTestUtil mediaFileTestUtil;
  private MediaFileForecaster mediaFileForecaster;

  @Before
  public void setUp() throws Exception {
    MediaFileUtil mediaFileUtil = new MediaFileUtil();
    mediaFileTestUtil = new MediaFileTestUtil(mediaFileUtil);

    Calendar calendar = new GregorianCalendar();
    DateToFileRenamer dateToFileRenamer = new DateToFileRenamer(calendar);
    FileDateInterpreter fileDateInterpreter = new FileDateInterpreter();
    mediaFileForecaster = new MediaFileForecaster(dateToFileRenamer, fileDateInterpreter);

    mediaFilesOutputForecaster = new MediaFilesOutputForecaster(mediaFileForecaster);
  }

  @Test
  public void testCalculateOutputDestinations() throws Exception {

    File testInputFile = mediaFileTestUtil.getTestInputFile();
    String testInputPath = mediaFileTestUtil.getTestInputPath();
    Map<String, List<File>> parsedMediaFiles = mediaFileTestUtil.getParsedMediaFiles(testInputFile,
                                                                                     clientInterface);

    Map<String, List<File>> stringListMap;
    stringListMap = mediaFilesOutputForecaster.calculateOutputDestinations(parsedMediaFiles,
                                                                           testInputPath);
    assertThat(stringListMap.size(), is(11));
  }

}
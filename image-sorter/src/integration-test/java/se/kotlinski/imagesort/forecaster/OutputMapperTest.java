package se.kotlinski.imagesort.forecaster;

import org.junit.Before;
import org.junit.Test;
import se.kotlinski.imagesort.forecaster.date.DateToFileRenamer;
import se.kotlinski.imagesort.forecaster.date.FileDateInterpreter;
import se.kotlinski.imagesort.main.ClientInterface;
import se.kotlinski.imagesort.mapper.OutputMapper;
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


public class OutputMapperTest {
  private OutputMapper outputMapper;
  private MediaFileTestUtil mediaFileTestUtil;
  private ClientInterface clientInterface;

  @Before
  public void setUp() throws Exception {
    clientInterface = mock(ClientInterface.class);

    MediaFileUtil mediaFileUtil = new MediaFileUtil();
    mediaFileTestUtil = new MediaFileTestUtil(mediaFileUtil);

    Calendar calendar = new GregorianCalendar();
    DateToFileRenamer dateToFileRenamer = new DateToFileRenamer(calendar);
    FileDateInterpreter fileDateInterpreter = new FileDateInterpreter();
    MediaFileOutputForecaster mediaFileOutputForecaster;
    mediaFileOutputForecaster = new MediaFileOutputForecaster(dateToFileRenamer,
                                                              fileDateInterpreter);

    outputMapper = new OutputMapper(mediaFileOutputForecaster);
  }

  @Test
  public void testCalculateOutputDestinations() throws Exception {

    File testInputFile = mediaFileTestUtil.getTestInputFile();
    List<File> mediaFiles = mediaFileTestUtil.getMediaFiles(clientInterface, testInputFile);

    Map<String, List<File>> stringListMap;
    stringListMap = outputMapper.calculateOutputDestinations(testInputFile, mediaFiles);
    assertThat(stringListMap.size(), is(11));
  }

}
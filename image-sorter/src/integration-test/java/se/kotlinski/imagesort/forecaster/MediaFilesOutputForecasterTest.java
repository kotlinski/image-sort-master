package se.kotlinski.imagesort.forecaster;

import org.junit.Before;
import org.junit.Test;
import se.kotlinski.imagesort.data.MediaFileDataHash;
import se.kotlinski.imagesort.executor.ClientInterface;
import se.kotlinski.imagesort.transformer.MediaFileHashDataMapTransformer;
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
import static org.mockito.Mockito.mock;


public class MediaFilesOutputForecasterTest {
  private MediaFilesOutputForecaster mediaFilesOutputForecaster;
  private MediaFileTestUtil mediaFileTestUtil;
  private MediaFileForecaster mediaFileForecaster;
  private ClientInterface clientInterface;

  @Before
  public void setUp() throws Exception {
    clientInterface = mock(ClientInterface.class);

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
    Map<MediaFileDataHash, List<File>> parsedMediaFiles;
    parsedMediaFiles = mediaFileTestUtil.getParsedMediaFiles(clientInterface, testInputFile);

    Map<String, List<File>> stringListMap;
    stringListMap = mediaFilesOutputForecaster.calculateOutputDestinations(parsedMediaFiles,
                                                                           testInputPath);
    assertThat(stringListMap.size(), is(11));
  }

}
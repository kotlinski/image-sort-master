package se.kotlinski.imagesort.resolver;

import org.junit.Before;
import org.junit.Test;
import se.kotlinski.imagesort.forecaster.MediaFileOutputForecaster;
import se.kotlinski.imagesort.main.ClientInterface;
import se.kotlinski.imagesort.mapper.OutputMapper;
import se.kotlinski.imagesort.forecaster.date.DateToFileRenamer;
import se.kotlinski.imagesort.forecaster.date.FileDateInterpreter;
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

public class OutputConflictResolverTest {

  Map<String, List<File>> mediaFileDestinations;
  private OutputConflictResolver outputConflictResolver;
  private OutputMapper outputMapper;
  private MediaFileTestUtil mediaFileTestUtil;
  private MediaFileOutputForecaster mediaFileOutputForecaster;
  private MediaFileUtil mediaFileUtil;
  private ClientInterface clientInterface;

  @Before
  public void setUp() throws Exception {
    clientInterface = mock(ClientInterface.class);

    mediaFileUtil = new MediaFileUtil();
    outputConflictResolver = new OutputConflictResolver(new MediaFileHashGenerator(),
                                                        mediaFileUtil);
    MediaFileUtil mediaFileUtil = new MediaFileUtil();
    mediaFileTestUtil = new MediaFileTestUtil(mediaFileUtil);

    Calendar calendar = new GregorianCalendar();
    DateToFileRenamer dateToFileRenamer = new DateToFileRenamer(calendar);
    FileDateInterpreter fileDateInterpreter = new FileDateInterpreter();
    mediaFileOutputForecaster = new MediaFileOutputForecaster(dateToFileRenamer, fileDateInterpreter);

    outputMapper = new OutputMapper(mediaFileOutputForecaster);

    File testInputFile = mediaFileTestUtil.getTestInputFile();

    List<File> mediaFiles = mediaFileTestUtil.getMediaFiles(clientInterface, testInputFile);

    mediaFileDestinations = outputMapper.calculateOutputDestinations(testInputFile, mediaFiles);
  }

  @Test
  public void testResolveOutputConflicts() throws Exception {
    Map<List<File>, String> listStringMap;
    listStringMap = outputConflictResolver.resolveOutputConflicts(clientInterface,
                                                                  mediaFileDestinations);

    for (String s : listStringMap.values()) {
      System.out.println(s);
    }
    Collection<String> outputPaths = listStringMap.values();
    assertThat(listStringMap.size(), is(12));
    assertThat(outputPaths.size(), is(12));

    assertThat(outputPaths.contains(File.separator + "2015" +
                                    File.separator + "06" +
                                    File.separator + "printscreens" +
                                    File.separator + "2015-06-05 21.19.28.png"), is(true));
    assertThat(outputPaths.contains(File.separator + "2013" +
                                    File.separator + "10" +
                                    File.separator + "snapchat" +
                                    File.separator + "2013-10-03 13.43.20.jpg"), is(true));
    assertThat(outputPaths.contains(File.separator + "2014" +
                                    File.separator + "03" +
                                    File.separator + "2014-03-02 01.09.34.jpg"), is(true));
    assertThat(outputPaths.contains(File.separator + "2014" +
                                    File.separator + "02" +
                                    File.separator + "2014-02-22 11.48.47_1.jpg"), is(true));
    assertThat(outputPaths.contains(File.separator + "2013" +
                                    File.separator + "10" +
                                    File.separator + "instagram" +
                                    File.separator + "2013-10-26 20.20.46.jpg"), is(true));
    assertThat(outputPaths.contains(File.separator + "2007" +
                                    File.separator + "06" +
                                    File.separator + "2007-06-15 17.41.19.jpg"), is(true));
    assertThat(outputPaths.contains(File.separator + "2014" +
                                    File.separator + "02" +
                                    File.separator + "2014-02-22 11.48.48.jpg"), is(true));
    assertThat(outputPaths.contains(File.separator + "2014" +
                                    File.separator + "nixon on raindeer - no date.jpg"), is(true));
    assertThat(outputPaths.contains(File.separator + "noxon on raindeer - no date.jpg"), is(true));
    assertThat(outputPaths.contains(File.separator + "2014" +
                                    File.separator + "03" +
                                    File.separator + "2014-03-16 11.45.09.mp4"), is(true));
    assertThat(outputPaths.contains(File.separator + "2014" +
                                    File.separator + "02" +
                                    File.separator + "duplicate in subfolder" +
                                    File.separator + "2014-02-22 11.48.48.jpg"), is(true));
    assertThat(outputPaths.contains(File.separator + "2014" +
                                    File.separator + "02" +
                                    File.separator + "2014-02-22 11.48.47_2.jpg"), is(true));
  }
}
package se.kotlinski.imagesort.resolver;

import org.junit.Before;
import org.junit.Test;
import se.kotlinski.imagesort.forecaster.MediaFileForecaster;
import se.kotlinski.imagesort.forecaster.MediaFilesOutputForecaster;
import se.kotlinski.imagesort.utils.DateToFileRenamer;
import se.kotlinski.imagesort.utils.FileDateInterpreter;
import se.kotlinski.imagesort.utils.MD5Generator;
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

public class OutputConflictResolverTest {

  private OutputConflictResolver outputConflictResolver;
  private MediaFilesOutputForecaster mediaFilesOutputForecaster;
  private MediaFileTestUtil mediaFileTestUtil;
  private MediaFileForecaster mediaFileForecaster;
  Map<String, List<File>> mediaFileDestinations;
  private MediaFileUtil mediaFileUtil;

  @Before
  public void setUp() throws Exception {
    mediaFileUtil = new MediaFileUtil();
    outputConflictResolver = new OutputConflictResolver(new MD5Generator(), mediaFileUtil);
    MediaFileUtil mediaFileUtil = new MediaFileUtil();
    mediaFileTestUtil = new MediaFileTestUtil(mediaFileUtil);

    Calendar calendar = new GregorianCalendar();
    DateToFileRenamer dateToFileRenamer = new DateToFileRenamer(calendar);
    FileDateInterpreter fileDateInterpreter = new FileDateInterpreter();
    mediaFileForecaster = new MediaFileForecaster(dateToFileRenamer, fileDateInterpreter);

    mediaFilesOutputForecaster = new MediaFilesOutputForecaster(mediaFileForecaster);

    File testInputFile = mediaFileTestUtil.getTestInputFile();
    String testInputPath = mediaFileTestUtil.getTestInputPath();
    Map<String, List<File>> parsedMediaFiles = mediaFileTestUtil.getParsedMediaFiles(testInputFile);

    mediaFileDestinations = mediaFilesOutputForecaster.calculateOutputDestinations(parsedMediaFiles,
                                                                                   testInputPath);
  }

  @Test
  public void testResolveOutputConflicts() throws Exception {
    Map<List<File>, String> listStringMap;
    listStringMap = outputConflictResolver.resolveOutputConflicts(mediaFileDestinations);

    for (String s : listStringMap.values()) {
      System.out.println(s);
    }
    Collection<String> outputPaths = listStringMap.values();
    assertThat(listStringMap.size(), is(11));
    assertThat(outputPaths.size(), is(11));

    assertThat(outputPaths.contains("/2013/10/snapchat/2013-10-03 13.43.20.jpg"), is(true));
    assertThat(outputPaths.contains("/2014/03/2014-03-02 01.09.34.jpg"), is(true));
    assertThat(outputPaths.contains("/2014/02/2014-02-22 11.48.47_1.jpg"), is(true));
    assertThat(outputPaths.contains("/2013/10/instagram/2013-10-26 20.20.46.jpg"), is(true));
    assertThat(outputPaths.contains("/2007/06/2007-06-15 17.41.19.jpg"), is(true));
    assertThat(outputPaths.contains("/2014/02/2014-02-22 11.48.48.jpg"), is(true));
    assertThat(outputPaths.contains("/2014/nixon on raindeer - no date.jpg"), is(true));
    assertThat(outputPaths.contains("/noxon on raindeer - no date.jpg"), is(true));
    assertThat(outputPaths.contains("/2014/03/2014-03-16 11.45.09.mp4"), is(true));
    assertThat(outputPaths.contains("/2014/02/duplicate in subfolder/2014-02-22 11.48.48.jpg"), is(true));
    assertThat(outputPaths.contains("/2014/02/2014-02-22 11.48.47_2.jpg"), is(true));
}
}
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
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

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

  }
}
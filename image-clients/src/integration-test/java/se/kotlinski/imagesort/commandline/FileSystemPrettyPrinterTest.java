package se.kotlinski.imagesort.commandline;

import com.mixpanel.mixpanelapi.MessageBuilder;
import org.junit.Before;
import org.junit.Test;
import se.kotlinski.imagesort.commandline.listeners.ImageSortPreMoveProgressFeedback;
import se.kotlinski.imagesort.commandline.listeners.ImageSortReadFilesInFolderFeedback;
import se.kotlinski.imagesort.data.RelativeMediaFolderOutput;
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
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;

public class FileSystemPrettyPrinterTest {

  private FileSystemPrettyPrinter fileSystemPrettyPrinter;
  private OutputToMediaFileMapper outputToMediaFileMapper;
  private MediaFileTestUtil mediaFileTestUtil;
  private ReadFilesFeedbackInterface readFilesFeedbackInterface;
  private PreMoveFeedbackInterface preMoveFeedback;
  private MediaFileToOutputMapper mediaFileToOutputMapper;


  @Before
  public void setUp() throws Exception {
    fileSystemPrettyPrinter = new FileSystemPrettyPrinter();
    MediaFileUtil mediaFileUtil = new MediaFileUtil();
    mediaFileTestUtil = new MediaFileTestUtil(mediaFileUtil);

    Calendar calendar = new GregorianCalendar();
    DateToFileRenamer dateToFileRenamer = new DateToFileRenamer(calendar);

    MessageBuilder messageBuilder = mock(MessageBuilder.class);
    FileDateInterpreter fileDateInterpreter = new FileDateInterpreter(mixpanel,
                                                                      sessionUniqueID,
                                                                      messageBuilder);
    MediaFileOutputForecaster mediaFileOutputForecaster;
    mediaFileOutputForecaster = new MediaFileOutputForecaster(dateToFileRenamer,
                                                              fileDateInterpreter);

    readFilesFeedbackInterface = new ImageSortReadFilesInFolderFeedback();
    preMoveFeedback = new ImageSortPreMoveProgressFeedback(new FileSystemPrettyPrinter());

    outputToMediaFileMapper = new OutputToMediaFileMapper(mediaFileOutputForecaster);
    mediaFileToOutputMapper = new MediaFileToOutputMapper(new MediaFileHashGenerator(),
                                                          mediaFileUtil);
  }

  @Test
  public void testPrettyPrintFolderStructure() throws Exception {
    File testInputFile = mediaFileTestUtil.getTestInputFile();

    List<File> mediaFiles = mediaFileTestUtil.getMediaFiles(readFilesFeedbackInterface,
                                                            testInputFile);

    Map<RelativeMediaFolderOutput, List<File>> mediaFileDestinations;
    mediaFileDestinations = outputToMediaFileMapper.calculateOutputDestinations(preMoveFeedback,
                                                                                testInputFile,
                                                                                mediaFiles);

    for (Map.Entry<RelativeMediaFolderOutput, List<File>> stringListEntry : mediaFileDestinations.entrySet()) {
      System.out.println(stringListEntry.getKey());
      System.out.println(stringListEntry.getValue());
      System.out.println("-");
    }

    Map<List<File>, RelativeMediaFolderOutput> filesGroupedByContent;
    filesGroupedByContent = mediaFileToOutputMapper.mapRelativeOutputsToFiles(preMoveFeedback,
                                                                              mediaFileDestinations);


    String fileSystem = fileSystemPrettyPrinter.convertFolderStructureToString(filesGroupedByContent,
                                                                               true);


    String expectedOutput = " |-tricky-snapchat-image.jpg\n" +
                            " |-noxon on raindeer - no date.jpg\n" +
                            " |-2015\n" +
                            " | |-06\n" +
                            " | | |-printscreens\n" +
                            " | | | |-2015-06-05 21.19.28.png\n" +
                            " |-2014\n" +
                            " | |-nixon on raindeer - no date.jpg\n" +
                            " | |-03\n" +
                            " | | |-2014-03-16 11.45.09.mp4\n" +
                            " | | |-2014-03-02 01.09.34.jpg\n" +
                            " | |-02\n" +
                            " | | |-duplicate in subfolder\n" +
                            " | | | |-2014-02-22 11.48.48.jpg\n" +
                            " | | |-2014-02-22 11.48.48.jpg\n" +
                            " | | |-2014-02-22 11.48.47_2.jpg\n" +
                            " | | |-2014-02-22 11.48.47_1.jpg\n" +
                            " |-2013\n" +
                            " | |-10\n" +
                            " | | |-snapchat\n" +
                            " | | | |-2013-10-03 13.43.20.jpg\n" +
                            " | | |-instagram\n" +
                            " | | | |-2013-10-26 20.20.46.jpg\n" +
                            " |-2007\n" +
                            " | |-06\n" +
                            " | | |-2007-06-15 17.41.19.jpg\n";
    System.out.println("filesystem: " + fileSystem);
    System.out.println("expected: " + expectedOutput);
    assertThat(fileSystem, is(expectedOutput));
  }
}
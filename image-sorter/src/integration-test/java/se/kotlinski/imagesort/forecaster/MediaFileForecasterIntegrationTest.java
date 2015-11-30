package se.kotlinski.imagesort.forecaster;

import org.junit.Before;
import org.junit.Test;
import se.kotlinski.imagesort.utils.DateToFileRenamer;
import se.kotlinski.imagesort.utils.FileDateInterpreter;
import se.kotlinski.imagesort.utils.FileDescriptor;
import se.kotlinski.imagesort.utils.MediaFileTestUtil;
import se.kotlinski.imagesort.utils.MediaFileUtil;

import java.io.File;
import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;


public class MediaFileForecasterIntegrationTest {

  private FileDateInterpreter fileDateInterpreter;
  private DateToFileRenamer dateToFileRenamer;
  private Calendar calendar;
  private MediaFileForecaster mediaFileForecaster;
  private MediaFileTestUtil mediaFileTestUtil;
  private FileDescriptor fileDescriptor;

  @Before
  public void setUp() throws Exception {
    MediaFileUtil mediaFileUtil = new MediaFileUtil();
    mediaFileTestUtil = new MediaFileTestUtil(mediaFileUtil);

    calendar = new GregorianCalendar();
    dateToFileRenamer = new DateToFileRenamer(calendar);
    fileDateInterpreter = new FileDateInterpreter();
    fileDescriptor = new FileDescriptor();
    mediaFileForecaster =
        new MediaFileForecaster(dateToFileRenamer, fileDateInterpreter, fileDescriptor);
  }

  @Test
  public void testForecastOutputDestination() throws Exception {
    File aJpegFile = mediaFileTestUtil.getAJpegFile();
    String testInputPath = mediaFileTestUtil.getTestInputPath();

    String jpegDestionationPath;
    jpegDestionationPath = mediaFileForecaster.forecastOutputDestination(aJpegFile, testInputPath);
    assertThat(jpegDestionationPath, is("2014" +
                                        File.separator +
                                        "02" +
                                        File.separator +
                                        "2014-02-22 12.48.48.jpg"));
  }

  @Test
  public void testForecastOutputDestination_FileInSubfolder() throws Exception {
    File aJpegFile = mediaFileTestUtil.getAJpegFileFromASubFolder();
    String testInputPath = mediaFileTestUtil.getTestInputPath();

    String jpegDestionationPath;
    jpegDestionationPath = mediaFileForecaster.forecastOutputDestination(aJpegFile, testInputPath);
    assertThat(jpegDestionationPath, is("2014" +
                                        File.separator +
                                        "02" +
                                        File.separator +
                                        "duplicate in subfolder" +
                                        File.separator +
                                        "2014-02-22 12.48.48.jpg"));
  }

  @Test
  public void testForecastOutputDestination_FileWithoutDate() throws Exception {
    File aJpegFile = mediaFileTestUtil.getJpegWitouthDate();
    String testInputPath = mediaFileTestUtil.getTestInputPath();


    String jpegDestionationPath = mediaFileForecaster.forecastOutputDestination(aJpegFile, testInputPath);
    assertThat(jpegDestionationPath, is("2014" +
                                        File.separator +
                                        "02" +
                                        File.separator +
                                        "2014-02-22 12.48.48.jpg"));
  }
}
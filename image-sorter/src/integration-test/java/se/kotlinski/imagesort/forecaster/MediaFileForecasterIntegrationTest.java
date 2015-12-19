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

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;


public class MediaFileForecasterIntegrationTest {

  private MediaFileForecaster mediaFileForecaster;
  private MediaFileTestUtil mediaFileTestUtil;

  @Before
  public void setUp() throws Exception {
    MediaFileUtil mediaFileUtil = new MediaFileUtil();
    mediaFileTestUtil = new MediaFileTestUtil(mediaFileUtil);

    Calendar calendar = new GregorianCalendar();
    DateToFileRenamer dateToFileRenamer = new DateToFileRenamer(calendar);
    FileDateInterpreter fileDateInterpreter = new FileDateInterpreter();
    mediaFileForecaster = new MediaFileForecaster(dateToFileRenamer, fileDateInterpreter);
  }

  @Test
  public void testForecastOutputDestination() throws Exception {
    File aJpegFile = mediaFileTestUtil.getAJpegFile();
    String testInputPath = mediaFileTestUtil.getTestInputPath();

    String jpegDestionationPath;
    jpegDestionationPath = mediaFileForecaster.forecastOutputDestination(aJpegFile, testInputPath);
    String expectedValue = File.separator + "2014" +
                           File.separator + "02" +
                           File.separator + "2014-02-22 12.48.48.jpg";

    assertThat(jpegDestionationPath, is(expectedValue));
  }

  @Test
  public void testForecastOutputDestination_FileInSubfolder() throws Exception {
    File aJpegFile = mediaFileTestUtil.getAJpegFileFromASubFolder();
    String testInputPath = mediaFileTestUtil.getTestInputPath();

    String jpegDestionationPath;
    jpegDestionationPath = mediaFileForecaster.forecastOutputDestination(aJpegFile, testInputPath);
    String expectedValue = File.separator + "2014" +
                           File.separator + "02" +
                           File.separator + "duplicate in subfolder" +
                           File.separator + "2014-02-22 12.48.48.jpg";
    System.out.println(jpegDestionationPath);
    System.out.println(expectedValue);
    assertThat(jpegDestionationPath, is(expectedValue));
  }

  @Test
  public void testForecastOutputDestination_FileWithoutDate() throws Exception {
    File aJpegFile = mediaFileTestUtil.getJpegWitouthDate();
    String testInputPath = mediaFileTestUtil.getTestInputPath();


    String jpegDestionationPath = mediaFileForecaster.forecastOutputDestination(aJpegFile,
                                                                                testInputPath);
    assertThat(jpegDestionationPath, is(File.separator + "noxon on raindeer - no date.jpg"));
  }

  @Test
  public void testGetFlavour_withoutDate() throws Exception {
    File aJpegFile = mediaFileTestUtil.getJpegWitouthDate();
    String masterFolderPath = mediaFileTestUtil.getTestInputPath();

    String flavour = mediaFileForecaster.getFlavour(masterFolderPath, aJpegFile);
    assertThat(flavour, is(""));
  }

  @Test
  public void testGetFlavour_withoutDateInSubfolder() throws Exception {
    File aJpegFile = mediaFileTestUtil.getJpegWitouthDateInSubfolder();
    String masterFolderPath = mediaFileTestUtil.getTestInputPath();

    String flavour = mediaFileForecaster.getFlavour(masterFolderPath, aJpegFile);
    assertThat(flavour, is(File.separator + "2014"));
  }

  @Test
  public void testGetFlavour_imageFromSubFolder() throws Exception {
    File fileFromASubFolder = mediaFileTestUtil.getAJpegFileFromASubFolder();
    String masterFolderPath = mediaFileTestUtil.getTestInputPath();

    String flavour = mediaFileForecaster.getFlavour(masterFolderPath, fileFromASubFolder);
    assertThat(flavour, is(File.separator + "2014" + File.separator + "duplicate in subfolder"));
  }

}

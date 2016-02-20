package se.kotlinski.imagesort.forecaster;

import com.mixpanel.mixpanelapi.MessageBuilder;
import org.junit.Before;
import org.junit.Test;
import se.kotlinski.imagesort.data.RelativeMediaFolderOutput;
import se.kotlinski.imagesort.forecaster.date.DateToFileRenamer;
import se.kotlinski.imagesort.forecaster.date.FileDateInterpreter;
import se.kotlinski.imagesort.utils.MediaFileTestUtil;
import se.kotlinski.imagesort.utils.MediaFileUtil;

import java.io.File;
import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;


public class MediaFileOutputForecasterIntegrationTest {

  private MediaFileOutputForecaster mediaFileOutputForecaster;
  private MediaFileTestUtil mediaFileTestUtil;

  @Before
  public void setUp() throws Exception {
    MediaFileUtil mediaFileUtil = new MediaFileUtil();
    mediaFileTestUtil = new MediaFileTestUtil(mediaFileUtil);

    Calendar calendar = new GregorianCalendar();
    DateToFileRenamer dateToFileRenamer = new DateToFileRenamer(calendar);

    MessageBuilder messageBuilder = mock(MessageBuilder.class);
    FileDateInterpreter fileDateInterpreter = new FileDateInterpreter(mixpanel,
                                                                      sessionUniqueID,
                                                                      messageBuilder);
    mediaFileOutputForecaster = new MediaFileOutputForecaster(dateToFileRenamer,
                                                              fileDateInterpreter);
  }

  @Test
  public void testForecastOutputDestination() throws Exception {
    File aJpegFile = mediaFileTestUtil.getAJpegFile();
    File testInputFile = mediaFileTestUtil.getTestInputFile();

    RelativeMediaFolderOutput jpegDestionationPath;
    jpegDestionationPath = mediaFileOutputForecaster.forecastOutputDestination(testInputFile,
                                                                               aJpegFile);
    String expectedValue = File.separator + "2014" +
                           File.separator + "02" +
                           File.separator + "2014-02-22 11.48.48.jpg";

    assertThat(jpegDestionationPath.relativePath, is(expectedValue));
  }

  @Test
  public void testForecastOutputDestination_FileInSubfolder() throws Exception {
    File aJpegFile = mediaFileTestUtil.getAJpegFileFromASubFolder();
    File testInputFile = mediaFileTestUtil.getTestInputFile();

    RelativeMediaFolderOutput jpegDestionationPath;
    jpegDestionationPath = mediaFileOutputForecaster.forecastOutputDestination(testInputFile,
                                                                               aJpegFile);
    String expectedValue = File.separator + "2014" +
                           File.separator + "02" +
                           File.separator + "duplicate in subfolder" +
                           File.separator + "2014-02-22 11.48.48.jpg";
    System.out.println(jpegDestionationPath);
    System.out.println(expectedValue);
    assertThat(jpegDestionationPath.relativePath, is(expectedValue));
  }

  @Test
  public void testForecastOutputDestination_FileWithoutDate() throws Exception {
    File aJpegFile = mediaFileTestUtil.getJpegWitouthDate();
    File testInputFile = mediaFileTestUtil.getTestInputFile();


    RelativeMediaFolderOutput jpegDestionationPath;
    jpegDestionationPath = mediaFileOutputForecaster.forecastOutputDestination(testInputFile,
                                                                               aJpegFile);
    String expectedValue = File.separator + "noxon on raindeer - no date.jpg";
    assertThat(jpegDestionationPath.relativePath, is(expectedValue));
  }

  @Test
  public void testGetFlavour_withoutDate() throws Exception {
    File aJpegFile = mediaFileTestUtil.getJpegWitouthDate();
    File masterFolderFile = mediaFileTestUtil.getTestInputFile();

    String flavour = mediaFileOutputForecaster.getFlavour(masterFolderFile, aJpegFile);
    assertThat(flavour, is(""));
  }

  @Test
  public void testGetFlavour_withoutDateInSubfolder() throws Exception {
    File aJpegFile = mediaFileTestUtil.getJpegWitouthDateInSubfolder();
    File masterFolderFile = mediaFileTestUtil.getTestInputFile();

    String flavour = mediaFileOutputForecaster.getFlavour(masterFolderFile, aJpegFile);
    assertThat(flavour, is(File.separator + "2014"));
  }

  @Test
  public void testGetFlavour_imageFromSubFolder() throws Exception {
    File fileFromASubFolder = mediaFileTestUtil.getAJpegFileFromASubFolder();
    File masterFolderFile = mediaFileTestUtil.getTestInputFile();

    String flavour = mediaFileOutputForecaster.getFlavour(masterFolderFile, fileFromASubFolder);
    assertThat(flavour, is(File.separator + "2014" + File.separator + "duplicate in subfolder"));
  }

}

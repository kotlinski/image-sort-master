package se.kotlinski.imagesort.controller;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import se.kotlinski.imagesort.exception.InvalidInputFolders;
import se.kotlinski.imagesort.mapper.ExportFileDataMap;
import se.kotlinski.imagesort.model.SortSettings;
import se.kotlinski.imagesort.utils.DateToFileRenamer;
import se.kotlinski.imagesort.utils.FileDateInterpreter;
import se.kotlinski.imagesort.utils.FileDateUniqueGenerator;
import se.kotlinski.imagesort.utils.FileDescriptor;
import se.kotlinski.imagesort.utils.SortMasterFileUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.mockito.Mockito.spy;

public class MediaFileParserTest {
  private MediaFileParser mediaFileParser;
  private SortMasterFileUtil sortMasterFileUtil;
  private SortSettings sortSettings;
  private Calendar calendar;
  private FileDateUniqueGenerator fileDateUniqueGenerator;
  private FileDateInterpreter fileDateInterpreter;
  private FileDescriptor fileDescriptor;
  private DateToFileRenamer dateToFileRenamer;
  private ExportForecaster exportForecaster;

  @Before
  public void setUp() {
    sortMasterFileUtil = new SortMasterFileUtil();

    sortSettings = new SortSettings();
    sortSettings.masterFolder = new File(sortMasterFileUtil.getTestOutputPath());
    calendar = new GregorianCalendar();
    fileDateUniqueGenerator = spy(new FileDateUniqueGenerator());
    fileDateInterpreter = spy(new FileDateInterpreter());
    fileDescriptor = spy(new FileDescriptor());
    dateToFileRenamer = spy(new DateToFileRenamer(calendar));
    exportForecaster = spy(new ExportForecaster(dateToFileRenamer));
    MediaFileParser mediaFileParser = new MediaFileParser(sortMasterFileUtil,
                                                          calendar,
                                                          fileDateUniqueGenerator,
                                                          fileDateInterpreter,
                                                          fileDescriptor,
                                                          dateToFileRenamer,
                                                          exportForecaster);
    setMediaFileParser(mediaFileParser);
  }

  @Test
  public void testRunIndex() throws Exception {
    ExportFileDataMap exportFileDataMap = getMediaFileParser().parseSettingsToExportData(sortSettings);
    Assert.assertEquals("Number of Unique images", 8, exportFileDataMap.getNumberOfUniqueImages());
  }

  @Test
  public void testRunIndexInvalidInput() throws Exception {
    setMediaFileParser(new MediaFileParser(sortMasterFileUtil,
                                           calendar,
                                           fileDateUniqueGenerator,
                                           fileDateInterpreter,
                                           fileDescriptor,
                                           dateToFileRenamer,
                                           exportForecaster));
    try {
      getMediaFileParser().parseSettingsToExportData(null);
      assert false;
    }
    catch (InvalidInputFolders e) {
      assert true;
    }

    SortSettings sortSettings = new SortSettings();
    setMediaFileParser(new MediaFileParser(sortMasterFileUtil,
                                           calendar,
                                           fileDateUniqueGenerator,
                                           fileDateInterpreter,
                                           fileDescriptor,
                                           dateToFileRenamer,
                                           exportForecaster));
    try {
      getMediaFileParser().parseSettingsToExportData(sortSettings);
      assert false;
    }
    catch (InvalidInputFolders e) {
      assert true;
    }

    sortSettings.masterFolder = new File("SomeInvalidFilePath");
    ArrayList<File> inputFolders = new ArrayList<File>();
    inputFolders.add(sortSettings.masterFolder);
    setMediaFileParser(new MediaFileParser(sortMasterFileUtil,
                                           calendar,
                                           fileDateUniqueGenerator,
                                           fileDateInterpreter,
                                           fileDescriptor,
                                           dateToFileRenamer,
                                           exportForecaster));
    try {
      getMediaFileParser().parseSettingsToExportData(sortSettings);
      assert false;
    }
    catch (InvalidInputFolders e) {
      assert true;
    }

    sortSettings.masterFolder = new File(sortMasterFileUtil.getTestOutputPath());
    inputFolders = new ArrayList<File>();
    inputFolders.add(sortSettings.masterFolder);
    setMediaFileParser(new MediaFileParser(sortMasterFileUtil,
                                           calendar,
                                           fileDateUniqueGenerator,
                                           fileDateInterpreter,
                                           fileDescriptor,
                                           dateToFileRenamer,
                                           exportForecaster));
    Assert.assertNotNull("Valid sortSettings", getMediaFileParser().parseSettingsToExportData(sortSettings));
  }

  MediaFileParser getMediaFileParser() {
    return mediaFileParser;
  }

  void setMediaFileParser(final MediaFileParser mediaFileParser) {
    this.mediaFileParser = mediaFileParser;
  }

  @Test
  public void testPopulateWithImages() throws Exception {
    File file = new File(sortMasterFileUtil.getTestInputPath());
    ExportFileDataMap exportFileDataMap = mediaFileParser.parseSettingsToExportData(sortSettings);
    Assert.assertEquals("Number of unique images in test folder",
                        8,
                        exportFileDataMap.getNumberOfUniqueImages());
  }
}

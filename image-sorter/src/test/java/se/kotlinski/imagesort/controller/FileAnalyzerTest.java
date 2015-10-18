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

public class FileAnalyzerTest {
  private FileAnalyzer fileAnalyzer;
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
    File file = new File(sortMasterFileUtil.getTestInputPath());
    ArrayList<File> inputFolders = new ArrayList<File>();
    inputFolders.add(file);
    sortSettings.inputFolders = inputFolders;
    sortSettings.masterFolder = new File(sortMasterFileUtil.getTestOutputPath());
    calendar = new GregorianCalendar();
    fileDateUniqueGenerator = spy(new FileDateUniqueGenerator());
    fileDateInterpreter = spy(new FileDateInterpreter());
    fileDescriptor = spy(new FileDescriptor());
    dateToFileRenamer = spy(new DateToFileRenamer(calendar));
    exportForecaster = spy(new ExportForecaster(dateToFileRenamer));
    FileAnalyzer fileAnalyzer = new FileAnalyzer(sortMasterFileUtil,
                                                 calendar,
                                                 fileDateUniqueGenerator,
                                                 fileDateInterpreter,
                                                 fileDescriptor,
                                                 dateToFileRenamer,
                                                 exportForecaster);
    setFileAnalyzer(fileAnalyzer);
  }

  @Test
  public void testRunIndex() throws Exception {
    ExportFileDataMap exportFileDataMap = getFileAnalyzer().createParsedFileMap(sortSettings);
    Assert.assertEquals("Number of Unique images", 8, exportFileDataMap.getNumberOfUniqueImages());
  }

  @Test
  public void testRunIndexInvalidInput() throws Exception {
    setFileAnalyzer(new FileAnalyzer(sortMasterFileUtil,
                                     calendar,
                                     fileDateUniqueGenerator,
                                     fileDateInterpreter,
                                     fileDescriptor,
                                     dateToFileRenamer,
                                     exportForecaster));
    try {
      getFileAnalyzer().createParsedFileMap(null);
      assert false;
    }
    catch (InvalidInputFolders e) {
      assert true;
    }

    SortSettings sortSettings = new SortSettings();
    setFileAnalyzer(new FileAnalyzer(sortMasterFileUtil,
                                     calendar,
                                     fileDateUniqueGenerator,
                                     fileDateInterpreter,
                                     fileDescriptor,
                                     dateToFileRenamer,
                                     exportForecaster));
    try {
      getFileAnalyzer().createParsedFileMap(sortSettings);
      assert false;
    }
    catch (InvalidInputFolders e) {
      assert true;
    }

    sortSettings.masterFolder = new File("SomeInvalidFilePath");
    ArrayList<File> inputFolders = new ArrayList<File>();
    inputFolders.add(sortSettings.masterFolder);
    sortSettings.inputFolders = inputFolders;
    setFileAnalyzer(new FileAnalyzer(sortMasterFileUtil,
                                     calendar,
                                     fileDateUniqueGenerator,
                                     fileDateInterpreter,
                                     fileDescriptor,
                                     dateToFileRenamer,
                                     exportForecaster));
    try {
      getFileAnalyzer().createParsedFileMap(sortSettings);
      assert false;
    }
    catch (InvalidInputFolders e) {
      assert true;
    }

    sortSettings.masterFolder = new File(sortMasterFileUtil.getTestOutputPath());
    inputFolders = new ArrayList<File>();
    inputFolders.add(sortSettings.masterFolder);
    sortSettings.inputFolders = inputFolders;
    setFileAnalyzer(new FileAnalyzer(sortMasterFileUtil,
                                     calendar,
                                     fileDateUniqueGenerator,
                                     fileDateInterpreter,
                                     fileDescriptor,
                                     dateToFileRenamer,
                                     exportForecaster));
    Assert.assertNotNull("Valid sortSettings", getFileAnalyzer().createParsedFileMap(sortSettings));
  }

  FileAnalyzer getFileAnalyzer() {
    return fileAnalyzer;
  }

  void setFileAnalyzer(final FileAnalyzer fileAnalyzer) {
    this.fileAnalyzer = fileAnalyzer;
  }

  @Test
  public void testPopulateWithImages() throws Exception {
    File file = new File(sortMasterFileUtil.getTestInputPath());
    ExportFileDataMap exportFileDataMap = fileAnalyzer.createParsedFileMap(sortSettings);
    Assert.assertEquals("Number of unique images in test folder",
                        8,
                        exportFileDataMap.getNumberOfUniqueImages());
  }
}

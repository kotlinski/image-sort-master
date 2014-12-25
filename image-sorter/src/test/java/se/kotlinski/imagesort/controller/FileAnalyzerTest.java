package se.kotlinski.imagesort.controller;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import se.kotlinski.imagesort.exception.InvalidInputFolders;
import se.kotlinski.imagesort.mapper.ExportFileDataMap;
import se.kotlinski.imagesort.model.FolderIO;
import se.kotlinski.imagesort.utils.DateToFileRenamer;
import se.kotlinski.imagesort.utils.FileDateInterpreter;
import se.kotlinski.imagesort.utils.FileDateUniqueGenerator;
import se.kotlinski.imagesort.utils.FileDescriptor;
import se.kotlinski.imagesort.utils.SortMasterFileUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

public class FileAnalyzerTest {
  private FileAnalyzer fileAnalyzer;
  private SortMasterFileUtil sortMasterFileUtil;
  private FolderIO folderIO;
  private Calendar calendar;
  private FileDateUniqueGenerator fileDateUniqueGenerator;
  private FileDateInterpreter fileDateInterpreter;
  private FileDescriptor fileDescriptor;
  private DateToFileRenamer dateToFileRenamer;
  private ExportForecaster exportForecaster;

  @Before
  public void setUp() {
    sortMasterFileUtil = new SortMasterFileUtil();

    folderIO = new FolderIO();
    File file = new File(sortMasterFileUtil.getTestInputPath());
    ArrayList<File> inputFolders = new ArrayList<>();
    inputFolders.add(file);
    folderIO.inputFolders = inputFolders;
    folderIO.masterFolder = new File(sortMasterFileUtil.getTestOutputPath());
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
    ExportFileDataMap exportFileDataMap = getFileAnalyzer().createParsedFileMap(folderIO);
    Assert.assertEquals("Number of Unique images", 9, exportFileDataMap.getNumberOfUniqueImages());
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

    FolderIO folderIO = new FolderIO();
    setFileAnalyzer(new FileAnalyzer(sortMasterFileUtil,
                                     calendar,
                                     fileDateUniqueGenerator,
                                     fileDateInterpreter,
                                     fileDescriptor,
                                     dateToFileRenamer,
                                     exportForecaster));
    try {
      getFileAnalyzer().createParsedFileMap(folderIO);
      assert false;
    }
    catch (InvalidInputFolders e) {
      assert true;
    }

    folderIO.masterFolder = new File("SomeInvalidFilePath");
    ArrayList<File> inputFolders = new ArrayList<>();
    inputFolders.add(folderIO.masterFolder);
    folderIO.inputFolders = inputFolders;
    setFileAnalyzer(new FileAnalyzer(sortMasterFileUtil,
                                     calendar,
                                     fileDateUniqueGenerator,
                                     fileDateInterpreter,
                                     fileDescriptor,
                                     dateToFileRenamer,
                                     exportForecaster));
    try {
      getFileAnalyzer().createParsedFileMap(folderIO);
      assert false;
    }
    catch (InvalidInputFolders e) {
      assert true;
    }

    folderIO.masterFolder = new File(sortMasterFileUtil.getTestOutputPath());
    inputFolders = new ArrayList<>();
    inputFolders.add(folderIO.masterFolder);
    folderIO.inputFolders = inputFolders;
    setFileAnalyzer(new FileAnalyzer(sortMasterFileUtil,
                                     calendar,
                                     fileDateUniqueGenerator,
                                     fileDateInterpreter,
                                     fileDescriptor,
                                     dateToFileRenamer,
                                     exportForecaster));
    Assert.assertNotNull("Valid folderIO", getFileAnalyzer().createParsedFileMap(folderIO));
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
    ExportFileDataMap exportFileDataMap = fileAnalyzer.createParsedFileMap(folderIO);
    Assert.assertEquals("Number of unique images in test folder",
                        9,
                        exportFileDataMap.getNumberOfUniqueImages());
  }
}

package se.kotlinski.imagesort.mapper;

import org.junit.Before;
import se.kotlinski.imagesort.utils.DateToFileRenamer;
import se.kotlinski.imagesort.utils.SortMasterFileUtil;

import java.util.Calendar;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

public class ParsedFileDataMapTest {

  private SortMasterFileUtil sortMasterFileUtil;
  private Calendar calendar;
  private ExportFileDataMap exportFileDataMap;
  private DateToFileRenamer dateToFileRenamer;

  @Before
  public void setUp() throws Exception {

    sortMasterFileUtil = new SortMasterFileUtil();
    calendar = mock(Calendar.class);
    dateToFileRenamer = spy(new DateToFileRenamer(calendar));
    exportFileDataMap = new ExportFileDataMap(dateToFileRenamer);
  }
/*
  @Test
  public void testToString() throws Exception {
    File inputFile = new File(sortMasterFileUtil.getTestInputPath() + File.separator + "1.jpg");
    FileDateInterpreter fileDateInterpreter = new FileDateInterpreter();

    Date date = fileDateInterpreter.getDate(inputFile);
    FileDescriber fileDescriber;
    fileDescriber = new FileDescriber(inputFile,
                                      date,
                                      "abc",
                                      sortMasterFileUtil.getTestInputPath(),
                                      calendar);
    parsedFileDataMap.addExportFileData(fileDescriber);
    Assert.assertEquals("Files in input folder: \n" +
                        "abc, including files: \n" +
                        "\t1.jpg\n", parsedFileDataMap.toString());

  }*/

/*  @Test
  public void testGetSizeOfUniqueImageMap() throws Exception {
    File file = new File(sortMasterFileUtil.getTestInputPath());

    FileDescriber f1 = mock(FileDescriber.class);
    when(f1.getMd5()).thenReturn("a");
    when(f1.getRenamedFilePath()).thenReturn("a");
    parsedFileDataMap.addExportFileData(f1);

    FileDescriber f2 = mock(FileDescriber.class);
    when(f2.getMd5()).thenReturn("b");
    when(f2.getRenamedFilePath()).thenReturn("b");
    parsedFileDataMap.addExportFileData(f2);

    FileDescriber f3 = mock(FileDescriber.class);
    when(f3.getMd5()).thenReturn("a");
    when(f3.getRenamedFilePath()).thenReturn("a");
    parsedFileDataMap.addExportFileData(f3);

    ArrayList<FileDescriber> imageDescribers = parsedFileDataMap.getUniqueImageDescribers();

    Assert.assertEquals("Unique image describer sizes", 2, imageDescribers.size());
  }*/

/*  @Test
  public void testGetRedundantFiles() throws Exception {
    Assert.assertEquals("To string function", "Files in input folder: \n", parsedFileDataMap.toString());
    Assert.assertEquals("Size of redundant file list", 0, parsedFileDataMap.getRedundantFiles().size());

    FileDescriber f1 = mock(FileDescriber.class);
    when(f1.getMd5()).thenReturn("a");
    when(f1.getRenamedFilePath()).thenReturn("a");
    parsedFileDataMap.addExportFileData(f1);

    FileDescriber f2 = mock(FileDescriber.class);
    when(f2.getMd5()).thenReturn("b");
    when(f2.getRenamedFilePath()).thenReturn("b");
    parsedFileDataMap.addExportFileData(f2);

    FileDescriber f3 = mock(FileDescriber.class);
    when(f3.getMd5()).thenReturn("a");
    when(f3.getRenamedFilePath()).thenReturn("a");
    parsedFileDataMap.addExportFileData(f3);

    List<FileDescriber> redundantFiles = parsedFileDataMap.getRedundantFiles();
    Assert.assertEquals(2, redundantFiles.size());
  }*/

}
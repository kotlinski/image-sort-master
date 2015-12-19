package se.kotlinski.imagesort.mapper;

import org.junit.Before;
import se.kotlinski.imagesort.utils.DateToFileRenamer;
import se.kotlinski.imagesort.utils.MediaFileUtil;

import java.util.Calendar;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

public class ParsedFileDataMapTest {

  private MediaFileUtil mediaFileUtil;
  private Calendar calendar;
  private DateToFileRenamer dateToFileRenamer;

  @Before
  public void setUp() throws Exception {

    mediaFileUtil = new MediaFileUtil();
    calendar = mock(Calendar.class);
    dateToFileRenamer = spy(new DateToFileRenamer(calendar));
  }
/*
  @Test
  public void testToString() throws Exception {
    File inputFile = new File(mediaFileUtil.getTestInputPath() + File.separator + "background.jpg");
    FileDateInterpreter fileDateInterpreter = new FileDateInterpreter();

    Date date = fileDateInterpreter.getDate(inputFile);
    DeprecatedFileDescriber fileDescriber;
    fileDescriber = new DeprecatedFileDescriber(inputFile,
                                      date,
                                      "abc",
                                      mediaFileUtil.getTestInputPath(),
                                      calendar);
    parsedFileDataMap.addExportFileData(fileDescriber);
    Assert.assertEquals("Files in input folder: \n" +
                        "abc, including files: \n" +
                        "\tbackground.jpg\n", parsedFileDataMap.toString());

  }*/

/*  @Test
  public void testGetSizeOfUniqueImageMap() throws Exception {
    File file = new File(mediaFileUtil.getTestInputPath());

    DeprecatedFileDescriber f1 = mock(DeprecatedFileDescriber.class);
    when(f1.getMd5()).thenReturn("a");
    when(f1.getRenamedFilePath()).thenReturn("a");
    parsedFileDataMap.addExportFileData(f1);

    DeprecatedFileDescriber f2 = mock(DeprecatedFileDescriber.class);
    when(f2.getMd5()).thenReturn("b");
    when(f2.getRenamedFilePath()).thenReturn("b");
    parsedFileDataMap.addExportFileData(f2);

    DeprecatedFileDescriber f3 = mock(DeprecatedFileDescriber.class);
    when(f3.getMd5()).thenReturn("a");
    when(f3.getRenamedFilePath()).thenReturn("a");
    parsedFileDataMap.addExportFileData(f3);

    ArrayList<DeprecatedFileDescriber> imageDescribers = parsedFileDataMap.getUniqueImageDescribers();

    Assert.assertEquals("Unique image describer sizes", 2, imageDescribers.size());
  }*/

/*  @Test
  public void testGetRedundantFiles() throws Exception {
    Assert.assertEquals("To string function", "Files in input folder: \n", parsedFileDataMap.toString());
    Assert.assertEquals("Size of redundant file list", 0, parsedFileDataMap.getRedundantFiles().size());

    DeprecatedFileDescriber f1 = mock(DeprecatedFileDescriber.class);
    when(f1.getMd5()).thenReturn("a");
    when(f1.getRenamedFilePath()).thenReturn("a");
    parsedFileDataMap.addExportFileData(f1);

    DeprecatedFileDescriber f2 = mock(DeprecatedFileDescriber.class);
    when(f2.getMd5()).thenReturn("b");
    when(f2.getRenamedFilePath()).thenReturn("b");
    parsedFileDataMap.addExportFileData(f2);

    DeprecatedFileDescriber f3 = mock(DeprecatedFileDescriber.class);
    when(f3.getMd5()).thenReturn("a");
    when(f3.getRenamedFilePath()).thenReturn("a");
    parsedFileDataMap.addExportFileData(f3);

    List<DeprecatedFileDescriber> redundantFiles = parsedFileDataMap.getRedundantFiles();
    Assert.assertEquals(2, redundantFiles.size());
  }*/

}
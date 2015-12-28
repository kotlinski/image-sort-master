package se.kotlinski.imagesort.calculator;

import org.junit.Before;
import org.junit.Test;
import se.kotlinski.imagesort.data.MediaFileDataInFolder;
import se.kotlinski.imagesort.executor.ClientInterface;
import se.kotlinski.imagesort.parser.MediaFileParser;
import se.kotlinski.imagesort.utils.MD5Generator;
import se.kotlinski.imagesort.utils.MediaFileTestUtil;
import se.kotlinski.imagesort.utils.MediaFileUtil;

import java.io.File;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

public class MediaInFolderCalculatorTest {

  private MediaInFolderCalculator mediaInFolderCalculator;
  private MediaFileUtil mediaFileUtil;
  private MD5Generator md5Generator;
  private Map<String, List<File>> mediaFilesInFolder;
  private ClientInterface clientInterface;

  @Before
  public void setUp() throws Exception {
    clientInterface = mock(ClientInterface.class);

    mediaInFolderCalculator = new MediaInFolderCalculator();

    mediaFileUtil = new MediaFileUtil();
    MediaFileTestUtil mediaFileTestUtil = new MediaFileTestUtil(mediaFileUtil);
    md5Generator = new MD5Generator();

    File masterFolder = mediaFileTestUtil.getTestInputFile();

    MediaFileParser mediaFileParser = new MediaFileParser(mediaFileUtil, md5Generator);
    mediaFilesInFolder = mediaFileParser.getMediaFilesInFolder(clientInterface, masterFolder);
  }

  @Test
  public void testCalculateMediaFileDataInFolder() throws Exception {
    MediaFileDataInFolder mediaFileDataInFolder;
    mediaFileDataInFolder = mediaInFolderCalculator.calculateMediaFileDataInFolder(mediaFilesInFolder);

    assertThat(mediaFileDataInFolder.numberOfUniqueFiles, is(10));
    assertThat(mediaFileDataInFolder.numberOfMediaFilesWithDuplicates, is(2));
    assertThat(mediaFileDataInFolder.totalNumberOfFiles, is(13));
  }
}
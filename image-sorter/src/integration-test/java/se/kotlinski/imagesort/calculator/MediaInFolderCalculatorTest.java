package se.kotlinski.imagesort.calculator;

import org.junit.Before;
import org.junit.Test;
import se.kotlinski.imagesort.data.MediaFileDataHash;
import se.kotlinski.imagesort.data.MediaFileDataInFolder;
import se.kotlinski.imagesort.executor.ClientInterface;
import se.kotlinski.imagesort.parser.MediaFileParser;
import se.kotlinski.imagesort.mapper.MediaFileDataMapper;
import se.kotlinski.imagesort.utils.MediaFileHashGenerator;
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
  private MediaFileHashGenerator mediaFileHashGenerator;
  private Map<MediaFileDataHash, List<File>> mediaFilesInFolder;
  private ClientInterface clientInterface;
  private MediaFileDataMapper mediaFileDataMapper;

  @Before
  public void setUp() throws Exception {
    clientInterface = mock(ClientInterface.class);

    mediaInFolderCalculator = new MediaInFolderCalculator();

    mediaFileUtil = new MediaFileUtil();
    MediaFileTestUtil mediaFileTestUtil = new MediaFileTestUtil(mediaFileUtil);
    mediaFileHashGenerator = new MediaFileHashGenerator();

    File masterFolder = mediaFileTestUtil.getTestInputFile();

    MediaFileParser mediaFileParser = new MediaFileParser(mediaFileUtil);
    List<File> mediaFiles = mediaFileParser.getMediaFilesInFolder(clientInterface, masterFolder);

    mediaFileDataMapper = new MediaFileDataMapper(mediaFileHashGenerator);
    mediaFilesInFolder = mediaFileDataMapper.mapOnMediaFileData(clientInterface, mediaFiles);

  }

  @Test
  public void testCalculateMediaFileDataInFolder() throws Exception {
    MediaFileDataInFolder mediaFileDataInFolder;
    mediaFileDataInFolder = mediaInFolderCalculator.calculateMediaFileDataInFolder(
        mediaFilesInFolder);

    assertThat(mediaFileDataInFolder.numberOfUniqueFiles, is(10));
    assertThat(mediaFileDataInFolder.numberOfMediaFilesWithDuplicates, is(2));
    assertThat(mediaFileDataInFolder.totalNumberOfFiles, is(13));
  }
}
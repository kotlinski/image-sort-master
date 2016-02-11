package se.kotlinski.imagesort.statistics;

import org.junit.Before;
import org.junit.Test;
import se.kotlinski.imagesort.data.MediaFileDataHash;
import se.kotlinski.imagesort.data.MediaFileDataInFolder;
import se.kotlinski.imagesort.feedback.FindDuplicatesFeedbackInterface;
import se.kotlinski.imagesort.feedback.MoveFeedbackInterface;
import se.kotlinski.imagesort.feedback.ReadFilesFeedbackInterface;
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

public class MediaFilesInFolderCalculatorTest {

  private MediaFilesInFolderCalculator mediaFilesInFolderCalculator;
  private ReadFilesFeedbackInterface readFilesFeedbackInterface;
  private FindDuplicatesFeedbackInterface findDuplicatesFeedbackInterface;
  private Map<MediaFileDataHash, List<File>> mediaFilesInFolder;

  @Before
  public void setUp() throws Exception {
    MoveFeedbackInterface moveFeedbackInterface = mock(MoveFeedbackInterface.class);

    mediaFilesInFolderCalculator = new MediaFilesInFolderCalculator();

    MediaFileUtil mediaFileUtil = new MediaFileUtil();
    MediaFileTestUtil mediaFileTestUtil = new MediaFileTestUtil(mediaFileUtil);
    MediaFileHashGenerator mediaFileHashGenerator = new MediaFileHashGenerator();
    readFilesFeedbackInterface = mock(ReadFilesFeedbackInterface.class);
    findDuplicatesFeedbackInterface = mock(FindDuplicatesFeedbackInterface.class);

    File masterFolder = mediaFileTestUtil.getTestInputFile();

    List<File> mediaFiles = mediaFileUtil.getMediaFilesInFolder(readFilesFeedbackInterface, masterFolder);

    MediaFileDataMapper mediaFileDataMapper = new MediaFileDataMapper(mediaFileHashGenerator);
    mediaFilesInFolder = mediaFileDataMapper.mapOnMediaFileData(findDuplicatesFeedbackInterface, mediaFiles);

  }

  @Test
  public void testCalculateMediaFileDataInFolder() throws Exception {
    MediaFileDataInFolder mediaFileDataInFolder;
    mediaFileDataInFolder = mediaFilesInFolderCalculator.calculateMediaFileDataInFolder(
        mediaFilesInFolder);

    assertThat(mediaFileDataInFolder.numberOfUniqueFiles, is(10));
    assertThat(mediaFileDataInFolder.numberOfMediaFilesWithDuplicates, is(2));
    assertThat(mediaFileDataInFolder.totalNumberOfFiles, is(13));
  }
}
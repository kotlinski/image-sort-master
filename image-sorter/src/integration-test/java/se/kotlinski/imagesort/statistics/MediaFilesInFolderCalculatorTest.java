package se.kotlinski.imagesort.statistics;

import org.junit.Before;
import org.junit.Test;
import se.kotlinski.imagesort.data.MediaFileDataHash;
import se.kotlinski.imagesort.data.MediaFileDataInFolder;
import se.kotlinski.imagesort.main.ClientAnalyzeFilesInFolderInterface;
import se.kotlinski.imagesort.main.ClientMovePhaseInterface;
import se.kotlinski.imagesort.main.ClientReadFilesInFolderInterface;
import se.kotlinski.imagesort.mapper.mappers.MediaFileDataMapper;
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
  private ClientReadFilesInFolderInterface clientReadFilesInFolderInterface;
  private ClientAnalyzeFilesInFolderInterface clientAnalyzeFilesInFolderInterface;
  private Map<MediaFileDataHash, List<File>> mediaFilesInFolder;

  @Before
  public void setUp() throws Exception {
    ClientMovePhaseInterface clientMovePhaseInterface = mock(ClientMovePhaseInterface.class);

    mediaFilesInFolderCalculator = new MediaFilesInFolderCalculator();

    MediaFileUtil mediaFileUtil = new MediaFileUtil();
    MediaFileTestUtil mediaFileTestUtil = new MediaFileTestUtil(mediaFileUtil);
    MediaFileHashGenerator mediaFileHashGenerator = new MediaFileHashGenerator();
    clientReadFilesInFolderInterface = mock(ClientReadFilesInFolderInterface.class);
    clientAnalyzeFilesInFolderInterface = mock(ClientAnalyzeFilesInFolderInterface.class);

    File masterFolder = mediaFileTestUtil.getTestInputFile();

    List<File> mediaFiles = mediaFileUtil.getMediaFilesInFolder(clientReadFilesInFolderInterface, masterFolder);

    MediaFileDataMapper mediaFileDataMapper = new MediaFileDataMapper(mediaFileHashGenerator);
    mediaFilesInFolder = mediaFileDataMapper.mapOnMediaFileData(clientAnalyzeFilesInFolderInterface, mediaFiles);

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
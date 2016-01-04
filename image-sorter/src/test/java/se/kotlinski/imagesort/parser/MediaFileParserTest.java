package se.kotlinski.imagesort.parser;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import se.kotlinski.imagesort.data.MediaFileDataHash;
import se.kotlinski.imagesort.data.SortSettings;
import se.kotlinski.imagesort.mapper.mappers.MediaFileDataMapper;
import se.kotlinski.imagesort.utils.MediaFileHashGenerator;
import se.kotlinski.imagesort.utils.MediaFileTestUtil;
import se.kotlinski.imagesort.utils.MediaFileUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.spy;

public class MediaFileParserTest {
  private MediaFileUtil mediaFileUtil;
  private MediaFileTestUtil mediaFileTestUtil;
  private MediaFileHashGenerator mediaFileHashGenerator;
  private MediaFileDataMapper mediaFileDataMapper;
  private Map<MediaFileDataHash, List<File>> fileMap;

  @Before
  public void setUp() {
    fileMap = new HashMap<>();

    mediaFileUtil = new MediaFileUtil();
    mediaFileTestUtil = new MediaFileTestUtil(mediaFileUtil);

    SortSettings sortSettings = new SortSettings();
    sortSettings.masterFolder = new File(mediaFileTestUtil.getRestorableTestMasterPath());
    mediaFileHashGenerator = spy(new MediaFileHashGenerator());

    mediaFileDataMapper = new MediaFileDataMapper(mediaFileHashGenerator);
  }

  @After
  public void tearDown() throws Exception {
    mediaFileTestUtil.cleanRestoreableMasterFolder();
  }

  @Test
  public void testAddFileToEmptyMap() throws Exception {
    File imageFile = mediaFileTestUtil.getInstagramFile();
    mediaFileDataMapper.addMediaFileToMap(fileMap, imageFile);
    assertThat(fileMap.size(), is(1));
  }

  @Test
  public void testAddTwoDifferentFiles() throws Exception {
    File instagramFile = mediaFileTestUtil.getInstagramFile();
    File snapchatFile = mediaFileTestUtil.getSnapchatFile();

    mediaFileDataMapper.addMediaFileToMap(fileMap, instagramFile);
    mediaFileDataMapper.addMediaFileToMap(fileMap, snapchatFile);

    MediaFileDataHash snapchatFileIdentifier = mediaFileHashGenerator.generateMediaFileDataHash(
        snapchatFile);
    assertThat(fileMap.get(snapchatFileIdentifier).size(), is(1));

    MediaFileDataHash instagramFileIdentifier = mediaFileHashGenerator.generateMediaFileDataHash(
        snapchatFile);
    assertThat(fileMap.get(instagramFileIdentifier).size(), is(1));
  }

  @Test
  public void testAddTwoImageFilesToMap() throws Exception {
    File imageFile = mediaFileTestUtil.getInstagramFile();

    mediaFileDataMapper.addMediaFileToMap(fileMap, imageFile);
    mediaFileDataMapper.addMediaFileToMap(fileMap, imageFile);

    MediaFileDataHash imageDataIdentifier = mediaFileHashGenerator.generateMediaFileDataHash(
        imageFile);
    assertThat(fileMap.get(imageDataIdentifier).size(), is(2));
  }

  @Test
  public void testAddTwoVideoFilesToMap() throws Exception {
    File videoFile = mediaFileTestUtil.getMp4File();

    mediaFileDataMapper.addMediaFileToMap(fileMap, videoFile);
    mediaFileDataMapper.addMediaFileToMap(fileMap, videoFile);

    MediaFileDataHash videoDataIdentifier = mediaFileHashGenerator.generateMediaFileDataHash(
        videoFile);
    assertThat(fileMap.get(videoDataIdentifier).size(), is(2));
  }

  @Test
  public void testRunIndex() throws Exception {/*
    DeprecatedExportFileDataMap exportFileDataMap = getMediaFileParser().transformFilesToMediaFiles(sortSettings);
    Assert.assertEquals("Number of Unique images", 8, exportFileDataMap.getNumberOfUniqueImages());*/
  }

  @Test
  public void testRunIndexInvalidInput() throws Exception {
    // new MediaFileParser(mediaFileUtil);

    SortSettings sortSettings = new SortSettings();
    // new MediaFileParser(mediaFileUtil);

    sortSettings.masterFolder = new File("SomeInvalidFilePath");
    ArrayList<File> inputFolders = new ArrayList<>();
    inputFolders.add(sortSettings.masterFolder);
    // new MediaFileParser(mediaFileUtil);

    sortSettings.masterFolder = new File(mediaFileTestUtil.getRestorableTestMasterPath());
    inputFolders = new ArrayList<>();
    inputFolders.add(sortSettings.masterFolder);
    // new MediaFileParser(mediaFileUtil);
  }


}

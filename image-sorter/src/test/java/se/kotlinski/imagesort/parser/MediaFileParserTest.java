package se.kotlinski.imagesort.parser;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import se.kotlinski.imagesort.data.PixelHash;
import se.kotlinski.imagesort.data.SortSettings;
import se.kotlinski.imagesort.mapper.MediaFileDataMapper;
import se.kotlinski.imagesort.utils.MediaFileHashGenerator;
import se.kotlinski.imagesort.utils.MediaFileTestUtil;
import se.kotlinski.imagesort.utils.MediaFileUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.mockito.Mockito.spy;

public class MediaFileParserTest {
  private MediaFileUtil mediaFileUtil;
  private MediaFileTestUtil mediaFileTestUtil;
  private MediaFileHashGenerator mediaFileHashGenerator;
  private MediaFileDataMapper mediaFileDataMapper;
  private Map<PixelHash, List<File>> fileMap;

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
  public void testGettingFileInfoHashForTwoImages() throws Exception {
    File instagramFile = mediaFileTestUtil.getInstagramFile();
    File snapchatFile = mediaFileTestUtil.getSnapchatFile();

    String snapchatFileInfoHash = mediaFileHashGenerator.generateFileInfoHash(snapchatFile);
    assertThat(snapchatFileInfoHash,
               containsString("/2013/snapchat/2013-10-03 13.43.20-kaffe.jpg:1774007"));

    String instagramFileInfoHash = mediaFileHashGenerator.generateFileInfoHash(instagramFile);
    assertThat(instagramFileInfoHash,
               containsString("2013/instagram/2013-10-26 20.20.46-kottbullar.jpg:1855859"));

    assertThat(snapchatFileInfoHash, not(instagramFileInfoHash));
  }

  @Test
  public void testGettingFileInfoHashForOneImages() throws Exception {
    File instagramFile = mediaFileTestUtil.getInstagramFile();
    File snapchatFile = mediaFileTestUtil.getSnapchatFile();

    String snapchatFileInfoHash = mediaFileHashGenerator.generateFileInfoHash(snapchatFile);
    assertThat(snapchatFileInfoHash,
               containsString("/2013/snapchat/2013-10-03 13.43.20-kaffe.jpg:1774007"));

    String instagramFileInfoHash2 = mediaFileHashGenerator.generateFileInfoHash(instagramFile);
    assertThat(instagramFileInfoHash2,
               containsString("2013/instagram/2013-10-26 20.20.46-kottbullar.jpg:1855859"));

    assertThat(snapchatFileInfoHash, not(instagramFileInfoHash2));
  }

  @Test
  public void testGettingFileInfoHashForOneVideo() throws Exception {
    File mp4File = mediaFileTestUtil.getMp4File();

    String mp4FileInfoHash = mediaFileHashGenerator.generateFileInfoHash(mp4File);
    assertThat(mp4FileInfoHash,
               containsString(
                   "/Users/simon/dev/github/image-sort-master/image-sorter/src/test/resources" +
                   "/inputImages/2014-03-16 10.45.09.mp4:9203152"));
  }

  @Test
  public void testAddTwoDifferentFiles() throws Exception {
    File instagramFile = mediaFileTestUtil.getInstagramFile();
    File snapchatFile = mediaFileTestUtil.getSnapchatFile();

    mediaFileDataMapper.addMediaFileToMap(fileMap, instagramFile);
    mediaFileDataMapper.addMediaFileToMap(fileMap, snapchatFile);

    PixelHash snapchatPixelHash = mediaFileHashGenerator.generatePixelDataHash(snapchatFile);
    assertThat(fileMap.get(snapchatPixelHash).size(), is(1));

    PixelHash instagramFileIdentifier = mediaFileHashGenerator.generatePixelDataHash(snapchatFile);
    assertThat(fileMap.get(instagramFileIdentifier).size(), is(1));
  }

  @Test
  public void testAddTwoImageFilesToMap() throws Exception {
    File imageFile = mediaFileTestUtil.getInstagramFile();

    mediaFileDataMapper.addMediaFileToMap(fileMap, imageFile);
    mediaFileDataMapper.addMediaFileToMap(fileMap, imageFile);

    PixelHash pixelHash = mediaFileHashGenerator.generatePixelDataHash(imageFile);
    assertThat(fileMap.get(pixelHash).size(), is(2));
  }

  @Test
  public void testAddTwoVideoFilesToMap() throws Exception {
    File videoFile = mediaFileTestUtil.getMp4File();

    mediaFileDataMapper.addMediaFileToMap(fileMap, videoFile);
    mediaFileDataMapper.addMediaFileToMap(fileMap, videoFile);

    PixelHash pixelHash = mediaFileHashGenerator.generatePixelDataHash(videoFile);
    assertThat(fileMap.get(pixelHash).size(), is(2));
  }

  @Test
  public void testRunIndex() throws Exception {/*
    DeprecatedExportFileDataMap exportFileDataMap = getMediaFileParser()
    .transformFilesToMediaFiles(sortSettings);
    Assert.assertEquals("Number of Unique images", 8, exportFileDataMap.getNumberOfUniqueImages()
    );*/
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

package se.kotlinski.imagesort.parser;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import se.kotlinski.imagesort.data.SortSettings;
import se.kotlinski.imagesort.transformer.MediaFileTransformer;
import se.kotlinski.imagesort.utils.DateToFileRenamer;
import se.kotlinski.imagesort.utils.FileDateInterpreter;
import se.kotlinski.imagesort.utils.MD5Generator;
import se.kotlinski.imagesort.utils.MediaFileTestUtil;
import se.kotlinski.imagesort.utils.MediaFileUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

public class MediaFileParserTest {
  private MediaFileParser mediaFileParser;
  private MediaFileUtil mediaFileUtil;
  private MediaFileTestUtil mediaFileTestUtil;
  private SortSettings sortSettings;
  private Calendar calendar;
  private MD5Generator MD5Generator;
  private FileDateInterpreter fileDateInterpreter;
  private DateToFileRenamer dateToFileRenamer;
  private MediaFileTransformer mediaFileTransform;

  @Before
  public void setUp() {
    mediaFileUtil = new MediaFileUtil();
    mediaFileTestUtil = new MediaFileTestUtil(mediaFileUtil);

    sortSettings = new SortSettings();
    sortSettings.masterFolder = new File(mediaFileTestUtil.getRestorableTestMasterPath());
    calendar = new GregorianCalendar();
    MD5Generator = spy(new MD5Generator());
    fileDateInterpreter = spy(new FileDateInterpreter());
    dateToFileRenamer = spy(new DateToFileRenamer(calendar));
    mediaFileTransform = mock(MediaFileTransformer.class);
    MediaFileParser mediaFileParser = new MediaFileParser(mediaFileUtil, MD5Generator);
    setMediaFileParser(mediaFileParser);
  }

  @After
  public void tearDown() throws Exception {
    mediaFileTestUtil.cleanRestoreableMasterFolder();
  }

  @Test
  public void testAddFileToEmptyMap() throws Exception {
    Map<String, List<File>> fileMap = new HashMap<>();
    File imageFile = mediaFileTestUtil.getInstagramFile();
    mediaFileParser.addMediaFileToMap(fileMap, imageFile);
    assertThat(fileMap.size(), is(1));
  }

  @Test
  public void testAddTwoDifferentFiles() throws Exception {
    Map<String, List<File>> fileMap = new HashMap<>();
    File instagramFile = mediaFileTestUtil.getInstagramFile();
    File snapchatFile = mediaFileTestUtil.getSnapchatFile();

    mediaFileParser.addMediaFileToMap(fileMap, instagramFile);
    mediaFileParser.addMediaFileToMap(fileMap, snapchatFile);

    String snapchatFileIdentifier = MD5Generator.generateMd5(snapchatFile);
    assertThat(fileMap.get(snapchatFileIdentifier).size(), is(1));

    String instagramFileIdentifier = MD5Generator.generateMd5(snapchatFile);
    assertThat(fileMap.get(instagramFileIdentifier).size(), is(1));
  }

  @Test
  public void testAddTwoImageFilesToMap() throws Exception {
    Map<String, List<File>> fileMap = new HashMap<>();
    File imageFile = mediaFileTestUtil.getInstagramFile();

    mediaFileParser.addMediaFileToMap(fileMap, imageFile);
    mediaFileParser.addMediaFileToMap(fileMap, imageFile);

    String imageDataIdentifier = MD5Generator.generateMd5(imageFile);
    assertThat(fileMap.get(imageDataIdentifier).size(), is(2));
  }

  @Test
  public void testAddTwoVideoFilesToMap() throws Exception {
    Map<String, List<File>> fileMap = new HashMap<>();
    File videoFile = mediaFileTestUtil.getMp4File();

    mediaFileParser.addMediaFileToMap(fileMap, videoFile);
    mediaFileParser.addMediaFileToMap(fileMap, videoFile);

    String videoDataIdentifier = MD5Generator.generateMd5(videoFile);
    assertThat(fileMap.get(videoDataIdentifier).size(), is(2));
  }

  @Test
  public void testRunIndex() throws Exception {/*
    DeprecatedExportFileDataMap exportFileDataMap = getMediaFileParser().transformFilesToMediaFiles(sortSettings);
    Assert.assertEquals("Number of Unique images", 8, exportFileDataMap.getNumberOfUniqueImages());*/
  }

  @Test
  public void testRunIndexInvalidInput() throws Exception {
    setMediaFileParser(new MediaFileParser(mediaFileUtil, MD5Generator));
 /*   try {
     // getMediaFileParser().transformFilesToMediaFiles(null);
      assert false;
    }
    catch (InvalidInputFolders e) {
      assert true;
    }*/

    SortSettings sortSettings = new SortSettings();
    setMediaFileParser(new MediaFileParser(mediaFileUtil, MD5Generator));
/*    try {
      getMediaFileParser().transformFilesToMediaFiles(sortSettings);
      assert false;
    }
    catch (InvalidInputFolders e) {
      assert true;
    }*/

    sortSettings.masterFolder = new File("SomeInvalidFilePath");
    ArrayList<File> inputFolders = new ArrayList<File>();
    inputFolders.add(sortSettings.masterFolder);
    setMediaFileParser(new MediaFileParser(mediaFileUtil, MD5Generator));
/*    try {
      getMediaFileParser().transformFilesToMediaFiles(sortSettings);
      assert false;
    }
    catch (InvalidInputFolders e) {
      assert true;
    }*/

    sortSettings.masterFolder = new File(mediaFileTestUtil.getRestorableTestMasterPath());
    inputFolders = new ArrayList<File>();
    inputFolders.add(sortSettings.masterFolder);
    setMediaFileParser(new MediaFileParser(mediaFileUtil, MD5Generator));
    //Assert.assertNotNull("Valid sortSettings", getMediaFileParser().transformFilesToMediaFiles(sortSettings));
  }

  MediaFileParser getMediaFileParser() {
    return mediaFileParser;
  }

  void setMediaFileParser(final MediaFileParser mediaFileParser) {
    this.mediaFileParser = mediaFileParser;
  }

  @Test
  public void testPopulateWithImages() throws Exception {
/*    File file = new File(mediaFileUtil.getTestInputPath());
    DeprecatedExportFileDataMap exportFileDataMap = mediaFileParser.transformFilesToMediaFiles(sortSettings);
    Assert.assertEquals("Number of unique images in test folder",
                        8,
                        exportFileDataMap.getNumberOfUniqueImages());*/
  }
}

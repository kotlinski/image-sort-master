package se.kotlinski.imagesort.parser;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import se.kotlinski.imagesort.data.MediaFileDataHash;
import se.kotlinski.imagesort.data.SortSettings;
import se.kotlinski.imagesort.transformer.MediaFileHashDataMapTransformer;
import se.kotlinski.imagesort.utils.DateToFileRenamer;
import se.kotlinski.imagesort.utils.FileDateInterpreter;
import se.kotlinski.imagesort.utils.MediaFileHashGenerator;
import se.kotlinski.imagesort.utils.MediaFileTestUtil;
import se.kotlinski.imagesort.utils.MediaFileUtil;

import javax.print.attribute.standard.Media;
import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.spy;

public class MediaFileParserTest {
  private MediaFileParser mediaFileParser;
  private MediaFileUtil mediaFileUtil;
  private MediaFileTestUtil mediaFileTestUtil;
  private SortSettings sortSettings;
  private Calendar calendar;
  private MediaFileHashGenerator mediaFileHashGenerator;
  private FileDateInterpreter fileDateInterpreter;
  private DateToFileRenamer dateToFileRenamer;
  private MediaFileHashDataMapTransformer mediaFileHashDataMapTransformer;
  private Map<MediaFileDataHash, List<File>> fileMap;

  @Before
  public void setUp() {
    fileMap = new HashMap<>();

    mediaFileUtil = new MediaFileUtil();
    mediaFileTestUtil = new MediaFileTestUtil(mediaFileUtil);

    sortSettings = new SortSettings();
    sortSettings.masterFolder = new File(mediaFileTestUtil.getRestorableTestMasterPath());
    calendar = new GregorianCalendar();
    mediaFileHashGenerator = spy(new MediaFileHashGenerator());
    fileDateInterpreter = spy(new FileDateInterpreter());
    dateToFileRenamer = spy(new DateToFileRenamer(calendar));
    mediaFileParser = new MediaFileParser(mediaFileUtil);

    mediaFileHashDataMapTransformer = new MediaFileHashDataMapTransformer(mediaFileHashGenerator);
  }

  @After
  public void tearDown() throws Exception {
    mediaFileTestUtil.cleanRestoreableMasterFolder();
  }

  @Test
  public void testAddFileToEmptyMap() throws Exception {
    File imageFile = mediaFileTestUtil.getInstagramFile();
    mediaFileHashDataMapTransformer.addMediaFileToMap(fileMap, imageFile);
    assertThat(fileMap.size(), is(1));
  }

  @Test
  public void testAddTwoDifferentFiles() throws Exception {
    File instagramFile = mediaFileTestUtil.getInstagramFile();
    File snapchatFile = mediaFileTestUtil.getSnapchatFile();

    mediaFileHashDataMapTransformer.addMediaFileToMap(fileMap, instagramFile);
    mediaFileHashDataMapTransformer.addMediaFileToMap(fileMap, snapchatFile);

    MediaFileDataHash snapchatFileIdentifier = mediaFileHashGenerator.generateMediaFileDataHash(snapchatFile);
    assertThat(fileMap.get(snapchatFileIdentifier).size(), is(1));

    MediaFileDataHash instagramFileIdentifier = mediaFileHashGenerator.generateMediaFileDataHash(snapchatFile);
    assertThat(fileMap.get(instagramFileIdentifier).size(), is(1));
  }

  @Test
  public void testAddTwoImageFilesToMap() throws Exception {
    File imageFile = mediaFileTestUtil.getInstagramFile();

    mediaFileHashDataMapTransformer.addMediaFileToMap(fileMap, imageFile);
    mediaFileHashDataMapTransformer.addMediaFileToMap(fileMap, imageFile);

    MediaFileDataHash imageDataIdentifier = mediaFileHashGenerator.generateMediaFileDataHash(imageFile);
    assertThat(fileMap.get(imageDataIdentifier).size(), is(2));
  }

  @Test
  public void testAddTwoVideoFilesToMap() throws Exception {
    File videoFile = mediaFileTestUtil.getMp4File();

    mediaFileHashDataMapTransformer.addMediaFileToMap(fileMap, videoFile);
    mediaFileHashDataMapTransformer.addMediaFileToMap(fileMap, videoFile);

    MediaFileDataHash videoDataIdentifier = mediaFileHashGenerator.generateMediaFileDataHash(videoFile);
    assertThat(fileMap.get(videoDataIdentifier).size(), is(2));
  }

  @Test
  public void testRunIndex() throws Exception {/*
    DeprecatedExportFileDataMap exportFileDataMap = getMediaFileParser().transformFilesToMediaFiles(sortSettings);
    Assert.assertEquals("Number of Unique images", 8, exportFileDataMap.getNumberOfUniqueImages());*/
  }

  @Test
  public void testRunIndexInvalidInput() throws Exception {
    new MediaFileParser(mediaFileUtil);

    SortSettings sortSettings = new SortSettings();
    new MediaFileParser(mediaFileUtil);

    sortSettings.masterFolder = new File("SomeInvalidFilePath");
    ArrayList<File> inputFolders = new ArrayList<File>();
    inputFolders.add(sortSettings.masterFolder);
    new MediaFileParser(mediaFileUtil);

    sortSettings.masterFolder = new File(mediaFileTestUtil.getRestorableTestMasterPath());
    inputFolders = new ArrayList<File>();
    inputFolders.add(sortSettings.masterFolder);
    new MediaFileParser(mediaFileUtil);
  }



}

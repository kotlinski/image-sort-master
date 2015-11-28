package se.kotlinski.imagesort.utils;

import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class MediaFileUtilIntegrationTest {

  private MediaFileTestUtil mediaFileTestUtil;
  private MediaFileUtil mediaFileUtil;

  @Before
  public void setUp() throws Exception {
    mediaFileTestUtil = new MediaFileTestUtil();
    mediaFileUtil = new MediaFileUtil();
  }

  @Test
  public void testValidityOfARegularFile() throws Exception {
    File jpegImage = mediaFileTestUtil.getAJpegFile();
    boolean isValidMediaFile = mediaFileUtil.isValidMediaFile(jpegImage);
    assertThat(isValidMediaFile, is(true));
  }

  @Test
  public void testIsValidityOfAnFileWitouthDate() throws Exception {
    File jpegImage = mediaFileTestUtil.getJpegWitouthDate();
    boolean isValidMediaFile = mediaFileUtil.isValidMediaFile(jpegImage);
    assertThat(isValidMediaFile, is(true));
  }

  @Test
  public void testIsValididityOfVideo() throws Exception {
    File mp4File = mediaFileTestUtil.getMp4File();
    boolean isValidMediaFile = mediaFileUtil.isValidMediaFile(mp4File);
    assertThat(isValidMediaFile, is(true));
  }

  @Test
  public void testValidityOfInstragramFile() throws Exception {
    File instagramFile = mediaFileTestUtil.getInstagramFile();
    boolean isValidMediaFile = mediaFileUtil.isValidMediaFile(instagramFile);
    assertThat(isValidMediaFile, is(true));
  }

  @Test
  public void testValidityOfSnapchatFile() throws Exception {
    File snapchatFile = mediaFileTestUtil.getSnapchatFile();
    boolean isValidMediaFile = mediaFileUtil.isValidMediaFile(snapchatFile);
    assertThat(isValidMediaFile, is(true));
  }

  @Test
  public void testValidityOfAFolder() throws Exception {
    File folder = new File(mediaFileTestUtil.getTestInputPath());
    boolean isValidMediaFile = mediaFileUtil.isValidMediaFile(folder);
    assertThat(isValidMediaFile, is(false));
  }
}
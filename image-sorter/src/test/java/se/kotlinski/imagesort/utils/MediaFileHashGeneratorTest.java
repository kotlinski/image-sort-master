package se.kotlinski.imagesort.utils;

import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNot.not;


public class MediaFileHashGeneratorTest {
  private MediaFileHashGenerator mediaFileHashGenerator;
  private MediaFileTestUtil mediaFileTestUtil;


  @Before
  public void setUp() throws Exception {

    MediaFileUtil mediaFileUtil = new MediaFileUtil();
    mediaFileTestUtil = new MediaFileTestUtil(mediaFileUtil);
    mediaFileHashGenerator = new MediaFileHashGenerator();
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
    assertThat(mp4FileInfoHash, containsString("2014-03-16 10.45.09.mp4:9203152"));
  }

}
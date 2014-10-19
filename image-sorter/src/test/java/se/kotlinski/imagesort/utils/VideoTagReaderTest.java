package se.kotlinski.imagesort.utils;

import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

public class VideoTagReaderTest {

  @Test
  public void testPrintVideoData() throws Exception {
    ImageFileUtil imageFileUtil = new ImageFileUtil();
    VideoTagReader.printVideoData(imageFileUtil.getTestInputPath() + File.separator + "2014-03-16 10.45.09.mp4");
  }
}
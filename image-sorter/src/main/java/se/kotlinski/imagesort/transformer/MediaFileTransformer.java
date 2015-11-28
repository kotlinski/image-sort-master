package se.kotlinski.imagesort.transformer;


import se.kotlinski.imagesort.data.MediaFile;

import java.io.File;

public class MediaFileTransformer {

  public MediaFile transformFileToMediaFile(File file) {
    return new MediaFile(file);
  }

}

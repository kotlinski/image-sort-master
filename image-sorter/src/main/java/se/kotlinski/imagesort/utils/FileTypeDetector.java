package se.kotlinski.imagesort.utils;

import org.apache.tika.Tika;

import java.io.IOException;
import java.nio.file.Path;

public class FileTypeDetector extends java.nio.file.spi.FileTypeDetector {

  private final Tika tika = new Tika();

  @Override
  public String probeContentType(Path path) throws IOException {
    return tika.detect(path.toFile());
  }
}
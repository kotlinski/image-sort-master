package se.kotlinski.imagesort.model;

import se.kotlinski.imagesort.exception.CouldNotGenerateIDException;
import se.kotlinski.imagesort.exception.CouldNotParseDateException;

import java.io.File;

/**
 * Date: 2014-10-19
 *
 * @author Simon Kotlinski
 */
public interface Describer {
  String getMd5();

  File getFile();

  String generateMd5(final File file) throws CouldNotGenerateIDException;

  String getRenamedFilePath() throws CouldNotParseDateException;

  String getRenamedFile() throws CouldNotParseDateException;

  String getOriginalFileName();

  int compareTo(Describer describer2);
}

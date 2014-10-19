package se.kotlinski.imagesort.exception;

import java.io.File;

/**
 * Date: 2014-10-19
 *
 * @author Simon Kotlinski
 */
public class CouldNotParseDateException extends Exception {
  public CouldNotParseDateException() {
    super();
  }

  public CouldNotParseDateException(final String s) {
    super(s);
  }
}

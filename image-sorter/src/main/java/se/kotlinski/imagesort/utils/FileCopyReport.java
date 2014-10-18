package se.kotlinski.imagesort.utils;

import se.kotlinski.imagesort.model.ImageDescriber;

import java.util.Collection;
import java.util.HashSet;

/**
 * Date: 2014-10-18
 *
 * @author Simon Kotlinski
 */
public class FileCopyReport {
  private Collection<ImageDescriber> filesNotCopied;
  private int numberOfFilesCopied;

  public FileCopyReport() {
    filesNotCopied = new HashSet<ImageDescriber>();
    numberOfFilesCopied = 0;
  }

  public void fileCopyFailed(ImageDescriber imageDescriber) {
    getFilesNotCopied().add(imageDescriber);
  }

  public void fileCopySuccess() {
    numberOfFilesCopied++;
  }

  public int getNumberOfFilesCopied() {
    return numberOfFilesCopied;
  }

  public Collection<ImageDescriber> getFilesNotCopied() {
    return filesNotCopied;
  }

  @Override
  public String toString() {
    String out = "Files Copied: " + numberOfFilesCopied;
    if (!filesNotCopied.isEmpty()) {
      out += "Files failed: ";
    }
    for (ImageDescriber imageDescriber : filesNotCopied) {
      out += imageDescriber.getFile().getName();
    }
    return out;
  }
}

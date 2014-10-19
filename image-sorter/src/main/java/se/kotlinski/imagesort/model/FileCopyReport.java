package se.kotlinski.imagesort.model;

import se.kotlinski.imagesort.model.ImageDescriber;

import java.util.Collection;
import java.util.HashSet;

/**
 * Date: 2014-10-18
 *
 * @author Simon Kotlinski
 */
public class FileCopyReport {
  private Collection<Describer> filesNotCopied;
  private int numberOfFilesCopied;

  public FileCopyReport() {
    filesNotCopied = new HashSet<Describer>();
    numberOfFilesCopied = 0;
  }

  public void fileCopyFailed(Describer imageDescriber) {
    getFilesNotCopied().add(imageDescriber);
  }

  public void fileCopySuccess() {
    numberOfFilesCopied++;
  }

  public int getNumberOfFilesCopied() {
    return numberOfFilesCopied;
  }

  public Collection<Describer> getFilesNotCopied() {
    return filesNotCopied;
  }

  @Override
  public String toString() {
    String out = "Files Copied: " + numberOfFilesCopied + "\n";
    if (!filesNotCopied.isEmpty()) {
      out += "Files failed: \n";
    }
    for (Describer imageDescriber : filesNotCopied) {
      out += imageDescriber.getOriginalFileName() + "\n";
    }
    return out;
  }
}

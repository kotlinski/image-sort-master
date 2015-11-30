package se.kotlinski.imagesort.model;

import java.util.Collection;
import java.util.HashSet;

/**
 * Date: 2014-10-18
 *
 * @author Simon Kotlinski
 */
public class FileCopyReport {
  private final Collection<DeprecatedFileDescriber> filesNotCopied;
  private int numberOfFilesCopied;

  public FileCopyReport() {
    filesNotCopied = new HashSet<>();
    numberOfFilesCopied = 0;
  }

  public void fileCopyFailed(DeprecatedFileDescriber imageDescriber) {
    getFilesNotCopied().add(imageDescriber);
  }

  public void fileCopySuccess() {
    numberOfFilesCopied++;
  }

  public int getNumberOfFilesCopied() {
    return numberOfFilesCopied;
  }

  public Collection<DeprecatedFileDescriber> getFilesNotCopied() {
    return filesNotCopied;
  }

  @Override
  public String toString() {
    String out = "Files Copied: " + numberOfFilesCopied + "\n";
    if (!filesNotCopied.isEmpty()) {
      out += "Files failed: \n";
    }
    for (DeprecatedFileDescriber imageDescriber : filesNotCopied) {
      out = String.format("%s%s\n", out, imageDescriber.getOriginalFileName());
    }
    return out;
  }
}

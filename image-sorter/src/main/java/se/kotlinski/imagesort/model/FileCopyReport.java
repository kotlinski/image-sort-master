package se.kotlinski.imagesort.model;

import java.util.Collection;
import java.util.HashSet;

/**
 * Date: 2014-10-18
 *
 * @author Simon Kotlinski
 */
public class FileCopyReport {
  private final Collection<FileDescriber> filesNotCopied;
  private int numberOfFilesCopied;

  public FileCopyReport() {
    filesNotCopied = new HashSet<>();
    numberOfFilesCopied = 0;
  }

  public void fileCopyFailed(FileDescriber imageDescriber) {
    getFilesNotCopied().add(imageDescriber);
  }

  public void fileCopySuccess() {
    numberOfFilesCopied++;
  }

  public int getNumberOfFilesCopied() {
    return numberOfFilesCopied;
  }

  public Collection<FileDescriber> getFilesNotCopied() {
    return filesNotCopied;
  }

  @Override
  public String toString() {
    String out = "Files Copied: " + numberOfFilesCopied + "\n";
    if (!filesNotCopied.isEmpty()) {
      out += "Files failed: \n";
    }
    for (FileDescriber imageDescriber : filesNotCopied) {
      out = String.format("%s%s\n", out, imageDescriber.getOriginalFileName());
    }
    return out;
  }
}

package se.kotlinski.imagesort.data;

public class RelativeMediaFolderOutput {
  public final String relativePath;

  public RelativeMediaFolderOutput(final String relativePath) {
    this.relativePath = relativePath;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    RelativeMediaFolderOutput that = (RelativeMediaFolderOutput) o;

    return relativePath.equals(that.relativePath);

  }

  @Override
  public int hashCode() {
    return relativePath.hashCode();
  }

  @Override
  public String toString() {
    return relativePath;
  }
}

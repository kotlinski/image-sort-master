package se.kotlinski.imagesort.data;

public class PixelHash {
  public final String hash;

  public PixelHash(final String hash) {
    this.hash = hash;
  }


  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    PixelHash that = (PixelHash) o;

    return hash != null ? hash.equals(that.hash) : that.hash == null;

  }

  @Override
  public int hashCode() {
    return hash != null ? hash.hashCode() : 0;
  }
}

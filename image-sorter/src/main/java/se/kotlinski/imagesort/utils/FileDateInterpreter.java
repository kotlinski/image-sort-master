package se.kotlinski.imagesort.utils;

import com.coremedia.iso.IsoFile;
import com.coremedia.iso.boxes.MovieBox;
import com.coremedia.iso.boxes.MovieHeaderBox;
import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifIFD0Directory;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import se.kotlinski.imagesort.exception.CouldNotParseDateException;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Date: 2014-10-24
 *
 * @author Simon Kotlinski
 */
public class FileDateInterpreter {
  private String testInputPath;

  public Date getImageDate(File file) throws CouldNotParseDateException {
    try {
      Metadata metadata = ImageMetadataReader.readMetadata(file);
      ExifSubIFDDirectory exifSubIFDDirectory = metadata.getDirectory(ExifSubIFDDirectory.class);
      int tagDatetimeOriginal = ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL;
      ExifIFD0Directory exifIFD0Directory = metadata.getDirectory(ExifIFD0Directory.class);
      int tagDatetime = ExifIFD0Directory.TAG_DATETIME;
      if (exifSubIFDDirectory != null && exifSubIFDDirectory.getDate(tagDatetimeOriginal) != null) {
        return exifSubIFDDirectory.getDate(tagDatetimeOriginal);
      }
      if (exifIFD0Directory != null && exifIFD0Directory.getDate(tagDatetime) != null) {
        return exifIFD0Directory.getDate(tagDatetime);
      }
      throw new CouldNotParseDateException();
    }
    catch (ImageProcessingException | IOException e) {
      System.err.println("Could not parse: " + file.getName());
      //e.printStackTrace();
      throw new CouldNotParseDateException("File: " + file.getName());
    }
  }

  public String getTestInputPath() {
    return testInputPath;
  }

  public Date getDate(final File file) throws CouldNotParseDateException {
    try {
      return getImageDate(file);
    }
    catch (CouldNotParseDateException e) {
      System.err.println("File is not an image with meta data");
    }
    try {
      return getVideoDate(file);
    }
    catch (CouldNotParseDateException e) {
      System.err.println("File is not an video with meta data");
    }
    throw new CouldNotParseDateException();
  }

  private Date getVideoDate(File videoFile) throws CouldNotParseDateException {
    try {
      IsoFile isoFile = new IsoFile(videoFile.getAbsolutePath());
      MovieBox moov = isoFile.getMovieBox();
      MovieHeaderBox movieHeaderBox = moov.getMovieHeaderBox();
      return movieHeaderBox.getCreationTime();
    }
    catch (IOException | NullPointerException e) {
      System.err.println("File is not a parsable mp4");
      throw new CouldNotParseDateException();
    }
  }
}

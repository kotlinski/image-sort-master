package se.kotlinski.imagesort.forecaster.date;

import com.coremedia.iso.IsoFile;
import com.coremedia.iso.boxes.MovieBox;
import com.coremedia.iso.boxes.MovieHeaderBox;
import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifIFD0Directory;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import se.kotlinski.imagesort.exception.CouldNotParseDateException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class FileDateInterpreter {

  private static final Logger LOGGER = LogManager.getLogger(FileDateInterpreter.class);

  Date getImageDate(File file) throws Exception {
    try {
      Metadata metadata = ImageMetadataReader.readMetadata(file);
      ExifSubIFDDirectory exifSubIFDDirectory = metadata.getFirstDirectoryOfType(ExifSubIFDDirectory.class);
      int tagDatetimeOriginal = ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL;

      ExifIFD0Directory exifIFD0Directory;
      exifIFD0Directory = metadata.getFirstDirectoryOfType(ExifIFD0Directory.class);
      int tagDatetime = ExifIFD0Directory.TAG_DATETIME;
      if (exifSubIFDDirectory != null && exifSubIFDDirectory.getDate(tagDatetimeOriginal,
                                                                     TimeZone.getDefault()) !=
                                         null) {
        return exifSubIFDDirectory.getDate(tagDatetimeOriginal, TimeZone.getDefault());
      }
      if (exifIFD0Directory != null && exifIFD0Directory.getDate(tagDatetime,
                                                                 TimeZone.getDefault()) != null) {
        return exifIFD0Directory.getDate(tagDatetime, TimeZone.getDefault());
      }
      throw new CouldNotParseDateException();
    }
    catch (ImageProcessingException | IOException e) {
      throw new CouldNotParseDateException("File: " + file.getName());
    }
  }

  @SuppressWarnings ("PMD.EmptyCatchBlock")
  public Date getDate(final File file) throws Exception {
    Calendar calendar = Calendar.getInstance();
    calendar.clear();
    calendar.set(Calendar.YEAR, 2000);
    Date threshold_for_valid_dates = calendar.getTime();
    try {
      Date imageDate = getImageDate(file);
      if (imageDate.after(threshold_for_valid_dates)) {
        return imageDate;
      }
    }

    catch (Exception e) {
      // This is expected
    }
    try {
      Date videoDate = getVideoDate(file);
      if (videoDate.after(threshold_for_valid_dates)) {
        return videoDate;
      }
    }
    catch (Exception e) {
      // This is expected
    }

    try {
      Date dateOutOfFileName = getDateOutOfFileName(file);
      if (dateOutOfFileName.after(threshold_for_valid_dates)) {
        return dateOutOfFileName;
      }
    }
    catch (Exception e) {
      // This is expected
    }

    try {
      BasicFileAttributes attr = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
      Date dateFromFileAttributes = new Date(attr.creationTime().toMillis());
      if (dateFromFileAttributes.after(threshold_for_valid_dates)) {
        return dateFromFileAttributes;
      }
    }
    catch (Exception err) {
      // This is expected
    }

    throw new CouldNotParseDateException("Could not Parse: " + file);
  }

  private Date getDateOutOfFileName(final File file) throws CouldNotParseDateException {
    String fileName = file.getName();
    String fileNameWithoutExtension = fileName.substring(0, fileName.lastIndexOf('.'));

    String dropbox_printscreen_format = "yyyy-MM-dd HH.mm.ss";
    String instagram_format = "'IMG'_yyyyMMdd_HHmmss";
    String screenshot_format = "'Screenshot'_yyyy-MM-dd-HH-mm-ss";
    String datePatterns[] = {"yyyy:MM:dd HH:mm:ss",
                             "yyyy:MM:dd HH:mm",
                             "yyyy-MM-dd HH:mm:ss",
                             "yyyy-MM-dd HH:mm",
                             "yyyy.MM.dd HH:mm:ss",
                             "yyyy.MM.dd HH:mm",
                             instagram_format,
                             screenshot_format,
                             dropbox_printscreen_format};
    for (String datePattern : datePatterns) {
      try {
        DateFormat parser = new SimpleDateFormat(datePattern);
        return parser.parse(fileNameWithoutExtension);
      }
      catch (ParseException e) {
        LOGGER.debug("Trying to parse date from string");
      }

    }
    throw new CouldNotParseDateException();
  }

  private Date getVideoDate(File videoFile) throws Exception {
    TimeZone.setDefault(TimeZone.getTimeZone("CET"));
    try {
      IsoFile isoFile = new IsoFile(videoFile.getAbsolutePath());
      MovieBox movieBox = isoFile.getMovieBox();
      MovieHeaderBox movieHeaderBox = movieBox.getMovieHeaderBox();
      return movieHeaderBox.getCreationTime();
    }
    catch (IOException | NullPointerException e) {
      //LOGGER.error("File is not a parcelable mp4");
      throw new CouldNotParseDateException();
    }
  }
}

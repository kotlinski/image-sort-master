package se.kotlinski.imagesort.forecaster;

import org.apache.commons.io.FilenameUtils;

import se.kotlinski.imagesort.utils.DateToFileRenamer;
import se.kotlinski.imagesort.utils.FileDateInterpreter;
import se.kotlinski.imagesort.utils.FileDescriptor;

import java.io.File;
import java.util.Date;

public class MediaFileForecaster {
  private final DateToFileRenamer dateToFileRenamer;
  private final FileDateInterpreter fileDateInterpreter;
  private final FileDescriptor fileDescriptor;

  public MediaFileForecaster(final DateToFileRenamer dateToFileRenamer,
                             final FileDateInterpreter fileDateInterpreter,
                             final FileDescriptor fileDescriptor) {
    this.dateToFileRenamer = dateToFileRenamer;
    this.fileDateInterpreter = fileDateInterpreter;
    this.fileDescriptor = fileDescriptor;
  }

  public String forecastOutputDestination(final File file, final String masterFolderPath) {
    Date date = getMediaFileDate(file);
    String datePath = getPath(file, date);
    String fileName = getFileName(file, date);

    String flavour = fileDescriptor.getFlavour(masterFolderPath, file);

    String fileExtension = FilenameUtils.getExtension(file.getName());
    return datePath + flavour + fileName + "." + fileExtension;
  }

  private String getPath(final File file, Date date) {
    if (date == null) {
      return file.getPath();
    }
    else {
      return dateToFileRenamer.formatPathDate(date);
    }
  }

  private String getFileName(final File file, Date date) {
    if (date == null) {
      return file.getName();
    }
    else {
      return dateToFileRenamer.formatFileDate(date);
    }
  }

  private Date getMediaFileDate(File file) {
    try {
      return fileDateInterpreter.getDate(file);
    }
    catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

}

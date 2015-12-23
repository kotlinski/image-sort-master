package se.kotlinski.imagesort.forecaster;

import org.apache.commons.io.FilenameUtils;
import se.kotlinski.imagesort.utils.DateToFileRenamer;
import se.kotlinski.imagesort.utils.FileDateInterpreter;

import java.io.File;
import java.util.Date;

public class MediaFileForecaster {
  private final DateToFileRenamer dateToFileRenamer;
  private final FileDateInterpreter fileDateInterpreter;

  public MediaFileForecaster(final DateToFileRenamer dateToFileRenamer,
                             final FileDateInterpreter fileDateInterpreter) {
    this.dateToFileRenamer = dateToFileRenamer;
    this.fileDateInterpreter = fileDateInterpreter;
  }

  public String forecastOutputDestination(final File file, final String masterFolderPath) {

    String flavour = getFlavour(masterFolderPath, file);

    Date date = getMediaFileDate(file);
    if (date != null) {
      String flavourDatePrefix = getFlavourDatePrefix(date);
      String reducedFlavour = reduceOldFlavourWithDateFlavour(flavour, flavourDatePrefix);
      flavour = File.separator  + flavourDatePrefix + reducedFlavour;
    }

    String filename;
    if (date == null) {
      filename = file.getName();
    }
    else {
      filename = getDateFilename(date, file);
    }

    return flavour + File.separator + filename;
  }

  String getFlavour(final String masterFolderPath, final File file) {
    String flavourWithFileName = file.getPath().replace(masterFolderPath, "");
    return flavourWithFileName.replace(File.separator + file.getName(), "");
  }

  private String reduceOldFlavourWithDateFlavour(final String flavour,
                                                 final String flavourDatePrefix) {
    String[] dateFlavours = flavourDatePrefix.split(File.separator);
    String reducedFlavour = flavour;
    for (String dateFlavour : dateFlavours) {
      reducedFlavour = reducedFlavour.replace(File.separator + dateFlavour, "");
    }
    return reducedFlavour;
  }

  private String getDateFilename(final Date date, final File file) {
    String dateFilename = dateToFileRenamer.formatFileDate(date);
    String fileExtension = FilenameUtils.getExtension(file.getName());
    return dateFilename + "." + fileExtension;
  }

  private String getFlavourDatePrefix(final Date date) {
    return dateToFileRenamer.formatPathDate(date);
  }

  private Date getMediaFileDate(File file) {
    try {
      return fileDateInterpreter.getDate(file);
    }
    catch (Exception e) {
      return null;
    }
  }

}

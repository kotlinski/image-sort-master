package se.kotlinski.imagesort.forecaster;

import com.google.inject.Inject;
import org.apache.commons.io.FilenameUtils;
import se.kotlinski.imagesort.data.RelativeMediaFolderOutput;
import se.kotlinski.imagesort.forecaster.date.DateToFileRenamer;
import se.kotlinski.imagesort.forecaster.date.FileDateInterpreter;

import java.io.File;
import java.util.Date;
import java.util.regex.Pattern;

public class MediaFileOutputForecaster {
  private final DateToFileRenamer dateToFileRenamer;
  private final FileDateInterpreter fileDateInterpreter;

  @Inject
  public MediaFileOutputForecaster(final DateToFileRenamer dateToFileRenamer,
                                   final FileDateInterpreter fileDateInterpreter) {
    this.dateToFileRenamer = dateToFileRenamer;
    this.fileDateInterpreter = fileDateInterpreter;
  }

  public RelativeMediaFolderOutput forecastOutputDestination(final File masterFolderFile, final File file) {

    String flavour = getFlavour(masterFolderFile, file);

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

    return new RelativeMediaFolderOutput(flavour + File.separator + filename);
  }

  String getFlavour(final File masterFolderFile, final File file) {
    String flavourWithFileName = file.getPath().replace(masterFolderFile.getAbsolutePath(), "");

    return flavourWithFileName.replace(File.separator + file.getName(), "");
  }

  private String reduceOldFlavourWithDateFlavour(final String flavour,
                                                 final String flavourDatePrefix) {
    String[] dateFlavours = flavourDatePrefix.split(Pattern.quote(File.separator));
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

package se.kotlinski.imagesort.forecaster;

import se.kotlinski.imagesort.model.DeprecatedFileDescriber;
import se.kotlinski.imagesort.utils.DateToFileRenamer;
import se.kotlinski.imagesort.utils.FileDateInterpreter;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MediaFileForecaster {
  private final DateToFileRenamer dateToFileRenamer;
  private final FileDateInterpreter fileDateInterpreter;

  public MediaFileForecaster(final DateToFileRenamer dateToFileRenamer,
                             final FileDateInterpreter fileDateInterpreter) {
    this.dateToFileRenamer = dateToFileRenamer;
    this.fileDateInterpreter = fileDateInterpreter;
  }

  public String forecastOutputDestination(final File file) {

    // TODO : Continue Here 29/11.
    // Find file date. And deside what the new folder and file-name should be.
    try {
      Date date = fileDateInterpreter.getDate(file);
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    DeprecatedFileDescriber fileDescriber;
    //dateToFileRenamer.

    return null;
  }

}

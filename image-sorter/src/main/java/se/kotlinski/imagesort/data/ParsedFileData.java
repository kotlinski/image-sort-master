package se.kotlinski.imagesort.data;

import java.io.File;
import java.util.Date;

public final class ParsedFileData {
  public final File originFile;
  public final File originBaseFolder;
  public final String flavour;
  public final String uniqueId;
  public final String fileNameExtension;
  public final Date dateTaken;
  public final String fileDateName;
  public final String datePathFlavour;
  public final boolean masterFolderFile;

  public ParsedFileData(final File originFile,
                        final File originBaseFolder,
                        final String flavour,
                        final String uniqueId,
                        final String fileNameExtension,
                        final Date dateTaken,
                        final String fileDateName,
                        final String datePathFlavour,
                        final boolean masterFolderFile) {
    this.originFile = originFile;
    this.originBaseFolder = originBaseFolder;
    this.flavour = flavour;
    this.uniqueId = uniqueId;
    this.fileNameExtension = fileNameExtension;
    this.dateTaken = dateTaken;
    this.fileDateName = fileDateName;
    this.datePathFlavour = datePathFlavour;
    this.masterFolderFile = masterFolderFile;
  }

  @Override
  public String toString() {
    return
           "originFile=" + originFile +
           ", \nflavour='" + flavour + '\'' +
           ", \ndateTaken=" + dateTaken +
           ", " + fileDateName +
           ", \nfileNameExtension='" + fileNameExtension + '\'' +
           ", \nuniqueId='" + uniqueId + '\'';
  }
}

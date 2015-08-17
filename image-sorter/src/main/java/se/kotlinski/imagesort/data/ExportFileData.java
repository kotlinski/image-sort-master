package se.kotlinski.imagesort.data;

import se.kotlinski.imagesort.utils.DateToFileRenamer;

import java.io.File;
import java.util.Date;

public class ExportFileData {
  public final String uniqueId;
  public final File originFile;
  public final String exportExtension;
  public final String exportName;
  public final String flavour;
  public final boolean isMasterFolderFile;
  public boolean appendUniqueID;
  public Object flavours;
  public final Date date;

  public ExportFileData(final String uniqueId,
                        final File originFile,
                        final String exportExtension,
                        final String exportName,
                        final String flavour,
                        final boolean isMasterFolderFile,
                        final Date date) {
    this.uniqueId = uniqueId;

    this.originFile = originFile;
    this.exportExtension = exportExtension;
    this.exportName = exportName;
    this.flavour = flavour;
    this.isMasterFolderFile = isMasterFolderFile;
    this.date = date;

    appendUniqueID = false;
  }

  @Override
  public String toString() {
    return "ExportFileData{\n" +
           ", \n\tappendUniqueID=" + appendUniqueID +
           ", \n\flavour=" + flavour +
           ", \n\tappendUniqueID=" + uniqueId  +
           "\n}";
  }
}
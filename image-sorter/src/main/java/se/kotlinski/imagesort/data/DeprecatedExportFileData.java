package se.kotlinski.imagesort.data;

import java.io.File;
import java.util.Date;

public class DeprecatedExportFileData {
  public final String uniqueId;
  public final File originFile;
  public final String exportExtension;
  public final String exportName;
  public final String flavour;
  public final boolean isMasterFolderFile;
  public boolean appendUniqueID;
  public Object flavours;
  public final Date date;

  public DeprecatedExportFileData(final String uniqueId,
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
    return "DeprecatedExportFileData{\n" +
           ", \n\tappendUniqueID=" + appendUniqueID +
           ", \n\flavour=" + flavour +
           ", \n\tappendUniqueID=" + uniqueId  +
           "\n}";
  }
}

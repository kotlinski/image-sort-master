package se.kotlinski.imagesort.commandline;

import java.util.Set;

public class FilePrinter {

  public final void printExportDestinations(final Set<String> exportDestinations) {
    System.out.println();
    System.out.println("Export destinations: ");
    for (String exportDestination : exportDestinations) {
      System.out.println(exportDestination);
    }
  }

  public final void printExportPaths(final Set<String> exportDestinations) {
    System.out.println();
    System.out.println("Export folders: ");
    for (String exportDestination : exportDestinations) {
      System.out.println(exportDestination);
    }
  }

  public final void printTotalNumberOfFiles(final int totalFiles) {
    System.out.println();
    System.out.println("Total number of parsed files: " + totalFiles);
  }

  public void printTotalNumberOfDuplicates(final int totalFiles,
                                           final int unique,
                                           final int removable) {

    System.out.println();
    System.out.println("Of " + totalFiles + " number of total files, are " + unique + " unique" +
                       ", " + removable + " files could be removed");
  }
}

package se.kotlinski.imagesort.commandline;

import se.kotlinski.imagesort.data.ExportFileData;
import se.kotlinski.imagesort.mapper.ExportFileDataMap;
import se.kotlinski.imagesort.mapper.FileGroup;

import java.util.Set;

public class FilePrinter {
  public void printFolderStructure(final ExportFileDataMap fileAnalyzer) {
    for (ExportFileData exportFileData : fileAnalyzer.getAllFiles()) {
      System.out.println();
      System.out.println(exportFileData.toString());
    }
  }

  public void printFlavours(final ExportFileDataMap exportFileDataMap) {
    System.out.println("Flavours: ");
    for (FileGroup fileGroup : exportFileDataMap.values()) {
      System.out.println(fileGroup.getFlavour());
    }
  }

  public void printExportDestinations(final Set<String> exportDestinations) {
    System.out.println();
    System.out.println("Export destinations: ");
    for (String exportDestination : exportDestinations) {
      System.out.println(exportDestination);
    }
  }

  public void printExportPaths(final Set<String> exportDestinations) {
    System.out.println();
    System.out.println("Export folders: ");
    for (String exportDestination : exportDestinations) {
      System.out.println(exportDestination);
    }
  }

  public void printTotalNumberOfFiles(final int totalFiles) {

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

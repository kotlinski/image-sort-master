package se.kotlinski.imagesort.javafx.controllers;

import se.kotlinski.imagesort.javafx.controllers.tabs.AnalyzeTabController;
import se.kotlinski.imagesort.main.ClientReadFilesInFolderInterface;

public class ClientReadFilesInFolderImplementation implements ClientReadFilesInFolderInterface {

  private final AnalyzeTabController analyzeTabController;

  public ClientReadFilesInFolderImplementation(final AnalyzeTabController analyzeTabController) {
    this.analyzeTabController = analyzeTabController;
  }

  @Override
  public void parsedFilesInMasterFolderProgress(final int size) {
    analyzeTabController.parsedFilesInMasterFolderProgress(size);
  }

}

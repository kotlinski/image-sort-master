package se.kotlinski.imagesort.javafx.controllers;

import se.kotlinski.imagesort.data.MediaFileDataHash;
import se.kotlinski.imagesort.main.ClientAnalyzeFilesInFolderInterface;

import java.io.File;
import java.util.List;
import java.util.Map;

public class AnalyzeFilesInFolderImplementation implements ClientAnalyzeFilesInFolderInterface {

  private final AnalyzeFilesInFolderImplementation analyzeTabController;

  public AnalyzeFilesInFolderImplementation(final AnalyzeFilesInFolderImplementation analyzeTabController) {
    this.analyzeTabController = analyzeTabController;
  }

  @Override
  public void startGroupFilesByContent() {

  }

  @Override
  public void groupFilesByContentProgress(final int total, final int progress) {
    analyzeTabController.groupFilesByContentProgress(total, progress);
  }

  @Override
  public void masterFolderSuccessfulParsed(final Map<MediaFileDataHash, List<File>> mediaFilesInFolder) {
    analyzeTabController.masterFolderSuccessfulParsed(mediaFilesInFolder);
  }

}

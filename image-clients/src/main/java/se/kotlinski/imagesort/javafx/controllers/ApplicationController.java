package se.kotlinski.imagesort.javafx.controllers;

import com.google.inject.Guice;
import com.google.inject.Injector;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import se.kotlinski.imagesort.data.MediaFileDataHash;
import se.kotlinski.imagesort.data.RelativeMediaFolderOutput;
import se.kotlinski.imagesort.data.SortSettings;
import se.kotlinski.imagesort.javafx.view.FolderSelector;
import se.kotlinski.imagesort.main.ClientInterface;
import se.kotlinski.imagesort.main.ImageSorter;
import se.kotlinski.imagesort.module.ImageModule;

import java.io.File;
import java.util.List;
import java.util.Map;

public class ApplicationController extends Application implements ClientInterface {

  private DirectoryChooser directoryChooser;
  private JavaFXUtils javaFxUtil;
  private ImageSorter imageSorter;


  public void startApplication() {
    Application.launch();
  }

  @Override
  public void start(final Stage primaryStage) throws Exception {
    injectFields();


    FolderSelector folderSelector = new FolderSelector(primaryStage);
    inputFolderSetup(folderSelector, primaryStage);
    folderSelector.setupGridLayout(primaryStage);

    primaryStage.setOnCloseRequest(e -> Platform.exit());
  }

  private void injectFields() {
    Injector injector = Guice.createInjector(new ImageModule());
    javaFxUtil = injector.getInstance(JavaFXUtils.class);
    directoryChooser = injector.getInstance(DirectoryChooser.class);
    imageSorter = injector.getInstance(ImageSorter.class);
  }

  private void inputFolderSetup(final FolderSelector folderSelector, final Stage primaryStage) {
    EventHandler<ActionEvent> selectInputEvent = e -> {
      File folder = directoryChooser.showDialog(primaryStage);
      if (folder != null) {
        imageSortFolder(folder);
      }
    };
    folderSelector.setupInputButton(selectInputEvent);
  }

  private void imageSortFolder(final File folder) {

    SortSettings sortSettings = new SortSettings();
    sortSettings.masterFolder = folder;
    imageSorter.sortImages(this, sortSettings);
  }

  @Override
  public void initiateMediaFileParsingPhase() {

  }

  @Override
  public void masterFolderFailedParsed() {

  }

  @Override
  public void parsedFilesInMasterFolderProgress(final int size) {

  }

  @Override
  public boolean masterFolderSuccessfulParsed(final Map<MediaFileDataHash, List<File>> mediaFilesInFolder) {
    return false;
  }

  @Override
  public void startCalculatingOutputDirectories() {

  }

  @Override
  public void successfulCalculatedOutputDestinations(final Map<RelativeMediaFolderOutput, List<File>> mediaFileDestinations) {

  }

  @Override
  public void startResolvingConflicts() {

  }

  @Override
  public void startGroupFilesByContent() {

  }

  @Override
  public void groupFilesByContentProgress(final int total, final int progress) {

  }

  @Override
  public void successfulResolvedOutputConflicts(final Map<List<File>, RelativeMediaFolderOutput> resolvedFilesToOutputMap) {

  }

  @Override
  public void startMovingFiles() {

  }

  @Override
  public void searchingForConflictsProgress(final int total, final int progress) {

  }

  @Override
  public void conflictFound(final RelativeMediaFolderOutput outputDirectory) {

  }

  @Override
  public void skippingFilesToMove(final int skippedFiles, final int filesToMove) {

  }

  @Override
  public void prepareMovePhase() {

  }
}

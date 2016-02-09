package se.kotlinski.imagesort.javafx.controllers.tabs;

import com.google.inject.Guice;
import com.google.inject.Injector;
import javafx.application.Platform;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import se.kotlinski.imagesort.commandline.FileSystemPrettyPrinter;
import se.kotlinski.imagesort.data.MediaFileDataHash;
import se.kotlinski.imagesort.data.RelativeMediaFolderOutput;
import se.kotlinski.imagesort.javafx.controllers.TabSwitcher;
import se.kotlinski.imagesort.main.ClientPreMovePhaseInterface;
import se.kotlinski.imagesort.main.ClientMovePhaseInterface;
import se.kotlinski.imagesort.main.ImageSorter;
import se.kotlinski.imagesort.module.ImageModule;

import java.io.File;
import java.util.List;
import java.util.Map;

public class AnalyzeTabController {

  private final ClientMovePhaseInterface clientMovePhaseInterface;
  private final TabSwitcher tabSwitcher;
  private final Tab analyzeTab;
  private final AnchorPane analyzeLoadingScene;
  private final AnchorPane analyzeResultScene;
  private final Text analyzeTabLoadingText;
  private final ProgressBar analyzeTabProgressBar;
  private final TextArea analyzeFolderTextArea;
  private final ImageSorter imageSorter;
  private final FileSystemPrettyPrinter fileSystemPrettyPrinter;

  public AnalyzeTabController(final ClientPreMovePhaseInterface clientAnalyzePhaseImplementation,
                              final ClientMovePhaseInterface clientMovePhaseInterface,
                              final TabSwitcher tabSwitcher,
                              final Tab analyzeTab,
                              final AnchorPane analyzeLoadingScene,
                              final AnchorPane analyzeResultScene,
                              final Text analyzeTabLoadingText,
                              final ProgressBar analyzeTabProgressBar,
                              final TextArea analyzeFolderTextArea) {
    this.clientMovePhaseInterface = clientMovePhaseInterface;
    this.tabSwitcher = tabSwitcher;
    this.analyzeTab = analyzeTab;
    this.analyzeLoadingScene = analyzeLoadingScene;
    this.analyzeResultScene = analyzeResultScene;
    this.analyzeTabLoadingText = analyzeTabLoadingText;
    this.analyzeTabProgressBar = analyzeTabProgressBar;
    this.analyzeFolderTextArea = analyzeFolderTextArea;

    Injector injector = Guice.createInjector(new ImageModule());
    imageSorter = injector.getInstance(ImageSorter.class);
    fileSystemPrettyPrinter = injector.getInstance(FileSystemPrettyPrinter.class);

    setUpListeners();
  }

  public void resetTab() {
    analyzeLoadingScene.setVisible(true);
    analyzeResultScene.setVisible(false);
    analyzeTab.setDisable(true);

    analyzeTabLoadingText.setText("");
    analyzeTabProgressBar.setProgress(0.0);
    analyzeFolderTextArea.setText("");
  }

  private void setUpListeners() {

  }

  public void setStageAndSetupListeners(final Stage primaryStage) {
  }


  public void initiateMediaFileParsingPhase() {
    updateLoadingFromSeparateThread("Initating...");
    analyzeTabProgressBar.setProgress(0.0);
  }

  public void masterFolderFailedParsed() {
    analyzeTabProgressBar.setProgress(0.0);
    updateLoadingFromSeparateThread("Failed parsing selected folder...");

  }

  public void parsedFilesInMasterFolderProgress(final int size) {
    analyzeTabProgressBar.setProgress(0.0);
    updateLoadingFromSeparateThread("Parsed " + size + " files in folder.");
  }

  public void masterFolderSuccessfulParsed(final Map<MediaFileDataHash, List<File>> mediaFilesInFolder) {

  }

  public void groupFilesByContentProgress(final int total, final int progress) {
    analyzeTabProgressBar.setProgress((double) progress / (double) total);
    updateLoadingFromSeparateThread("Parsing files... (" + progress + " of " + total + ")");
  }

  public void startCalculatingOutputDirectories() {
    updateLoadingFromSeparateThread("Calculating output directories...");
  }

  public void successfulCalculatedOutputDestinations(final Map<RelativeMediaFolderOutput, List<File>> mediaFileDestinations) {
    String folderStructureString;
    folderStructureString = fileSystemPrettyPrinter.convertFolderStructureToString(
        mediaFileDestinations,
        false);
    analyzeFolderTextArea.setText(folderStructureString);
  }

  private void updateLoadingFromSeparateThread(String value) {
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        analyzeTabLoadingText.setText(value);
      }
    });
  }

}

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
import se.kotlinski.imagesort.data.RelativeMediaFolderOutput;
import se.kotlinski.imagesort.feedback.MoveFeedbackInterface;
import se.kotlinski.imagesort.javafx.controllers.TabSwitcher;
import se.kotlinski.imagesort.main.ImageSorter;
import se.kotlinski.imagesort.module.ImageModule;

import java.io.File;
import java.util.List;
import java.util.Map;

public class PreMoveTabController {

  private final MoveFeedbackInterface moveFeedbackInterface;
  private final TabSwitcher tabSwitcher;
  private final Tab analyzeTab;
  private final AnchorPane analyzeLoadingScene;
  private final AnchorPane analyzeResultScene;
  private final Text analyzeTabLoadingText;
  private final ProgressBar analyzeTabProgressBar;
  private final TextArea analyzeFolderTextArea;
  private final ImageSorter imageSorter;
  private final FileSystemPrettyPrinter fileSystemPrettyPrinter;

  public PreMoveTabController(final MoveFeedbackInterface moveFeedbackInterface,
                              final TabSwitcher tabSwitcher,
                              final Tab analyzeTab,
                              final AnchorPane analyzeLoadingScene,
                              final AnchorPane analyzeResultScene,
                              final Text analyzeTabLoadingText,
                              final ProgressBar analyzeTabProgressBar,
                              final TextArea analyzeFolderTextArea) {
    this.moveFeedbackInterface = moveFeedbackInterface;
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

  private void updateLoadingFromSeparateThread(String value) {
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        analyzeTabLoadingText.setText(value);
      }
    });
  }


  public void setStageAndSetupListeners(final Stage primaryStage) {
  }


  public void startCalculatingOutputDirectories() {
    updateLoadingFromSeparateThread("Calculating output directories...");
  }

  public void initiatingPreMoveFeedback() {
    updateLoadingFromSeparateThread("Initating...");
    analyzeTabProgressBar.setProgress(0.0);
  }


  public void readFilesProgressFeedback(final int size) {
    analyzeTabProgressBar.setProgress(0.0);
    updateLoadingFromSeparateThread("Parsed " + size + " files in folder.");
  }


  public void doneCalculatingDestionationEveryFile(final Map<RelativeMediaFolderOutput, List<File>> mediaFileDestinations) {
    //DO somethinge?
  }

  public void groupFilesByContentProgress(final int total, final int progress) {
    analyzeTabProgressBar.setProgress((double) progress / (double) total);
    updateLoadingFromSeparateThread("Parsing files... (" + progress + " of " + total + ")");
  }



  public void successfulCalculatedOutputDestinations(final Map<List<File>, RelativeMediaFolderOutput> mediaFileDestinations) {
    String folderStructureString;
    folderStructureString = fileSystemPrettyPrinter.convertFolderStructureToString(mediaFileDestinations, false);
    analyzeFolderTextArea.setText(folderStructureString);
  }

  public void doneWithMovePhase(final Map<List<File>, RelativeMediaFolderOutput> filesGroupedByContent) {
    tabSwitcher.setTabsInAnalyzeModeDone(filesGroupedByContent);
  }


}

package se.kotlinski.imagesort.javafx.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import se.kotlinski.imagesort.data.MediaFileDataHash;
import se.kotlinski.imagesort.data.RelativeMediaFolderOutput;
import se.kotlinski.imagesort.javafx.controllers.tabs.AnalyzeTabController;
import se.kotlinski.imagesort.javafx.controllers.tabs.SelectFolderTabController;
import se.kotlinski.imagesort.main.ClientInterface;

import java.io.File;
import java.util.List;
import java.util.Map;

public class TabGroupController implements ClientInterface, TabSwitcher {

  //TABS
  @FXML
  public TabPane tabGroup;
  @FXML
  public Tab analyzeTab;
  @FXML
  public Tab selectFolderTab;
  @FXML
  public Tab moveTab;
  @FXML
  public Button selectFolderButton;
  @FXML
  public Button selectFolderContinueButton;
  //Analyze tab
  @FXML
  public AnchorPane analyzeLoadingScene;
  @FXML
  public AnchorPane analyzeResultScene;
  @FXML
  public Text selectedFolderPathText;
  @FXML
  public Text analyzeTabLoadingText;
  @FXML
  public ProgressBar analyzeTabProgressBar;
  @FXML
  public TextArea analyzeFolderTextArea;
  //Select folder tab
  private SelectFolderTabController selectFolderTabController;
  //Result tab
  private SingleSelectionModel<Tab> selectionModel;
  private AnalyzeTabController analyzeTabController;

  public TabGroupController() {
  }


  @FXML
  private void initialize() {
    selectionModel = tabGroup.getSelectionModel();


    selectFolderTabController = new SelectFolderTabController(this,
                                                              this,
                                                              selectFolderButton,
                                                              selectFolderContinueButton,
                                                              selectedFolderPathText);

    analyzeTabController = new AnalyzeTabController(this,
                                                    this,
                                                    analyzeTab,
                                                    analyzeLoadingScene,
                                                    analyzeResultScene,
                                                    analyzeTabLoadingText,
                                                    analyzeTabProgressBar,
                                                    analyzeFolderTextArea);
    analyzeTabController.resetTab();
  }


  public void setStageAndSetupListeners(final Stage primaryStage) {
    selectFolderTabController.setStageAndSetupListeners(primaryStage);
  }

  @Override
  public void switchToAnalyzeTab() {
    analyzeTab.setDisable(false);
    selectionModel.select(analyzeTab);
  }

  @Override
  public void resetTabs() {
    analyzeTabController.resetTab();
  }

  @Override
  public void initiateMediaFileParsingPhase() {
    analyzeTabController.initiateMediaFileParsingPhase();
  }

  @Override
  public void masterFolderFailedParsed() {
    analyzeTabController.masterFolderFailedParsed();
  }

  @Override
  public void parsedFilesInMasterFolderProgress(final int size) {
    analyzeTabController.parsedFilesInMasterFolderProgress(size);
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

  @Override
  public void startCalculatingOutputDirectories() {
    analyzeTabController.startCalculatingOutputDirectories();
  }

  @Override
  public void successfulCalculatedOutputDestinations(final Map<RelativeMediaFolderOutput, List<File>> mediaFileDestinations) {
    analyzeTabController.successfulCalculatedOutputDestinations(mediaFileDestinations);
  }

  @Override
  public void startResolvingConflicts() {
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

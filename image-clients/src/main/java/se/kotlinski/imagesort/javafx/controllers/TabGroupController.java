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
import se.kotlinski.imagesort.data.RelativeMediaFolderOutput;
import se.kotlinski.imagesort.javafx.controllers.tabs.AnalyzeTabController;
import se.kotlinski.imagesort.javafx.controllers.tabs.SelectFolderTabController;
import se.kotlinski.imagesort.main.ClientReadFilesInFolderInterface;

import java.io.File;
import java.util.List;
import java.util.Map;

public class TabGroupController implements TabSwitcher {

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
    ClientPreMovePhaseImplementation clientMoveAnalyzePhaseImplementation;
    clientMoveAnalyzePhaseImplementation = new ClientPreMovePhaseImplementation(this,
                                                                                analyzeTabController);
    ClientMovePhaseImplementation clientMovePhaseImplementation = new ClientMovePhaseImplementation();
    selectionModel = tabGroup.getSelectionModel();

    ClientReadFilesInFolderInterface clientReadFilesInFolderInterface = new ClientReadFilesInFolderImplementation(analyzeTabController);

    selectFolderTabController = new SelectFolderTabController(clientMoveAnalyzePhaseImplementation,
                                                              clientReadFilesInFolderInterface,
                                                              this,
                                                              selectFolderButton,
                                                              selectFolderContinueButton,
                                                              selectedFolderPathText);

    analyzeTabController = new AnalyzeTabController(clientMoveAnalyzePhaseImplementation,
                                                    clientMovePhaseImplementation,
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
  public void resetTabs() {
    analyzeTabController.resetTab();

    selectionModel.select(selectFolderTab);

    selectFolderTab.setDisable(false);
    analyzeTab.setDisable(true);
  }

  @Override
  public void setTabsInAnalyzeMode() {
    analyzeTabController.resetTab();

    selectionModel.select(analyzeTab);

    analyzeTab.setDisable(true);
    selectFolderTab.setDisable(true);
  }

  @Override
  public void setTabsInAnalyzeModeDone(Map<RelativeMediaFolderOutput, List<File>> mediaFileDestinations) {
    analyzeTabController.successfulCalculatedOutputDestinations(mediaFileDestinations);

    analyzeLoadingScene.setVisible(false);
    analyzeResultScene.setVisible(true);

    analyzeTab.setDisable(false);
    selectFolderTab.setDisable(false);
  }

}

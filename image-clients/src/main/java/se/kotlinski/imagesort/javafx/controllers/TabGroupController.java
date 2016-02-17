package se.kotlinski.imagesort.javafx.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import se.kotlinski.imagesort.data.RelativeMediaFolderOutput;
import se.kotlinski.imagesort.feedback.ReadFilesFeedbackInterface;
import se.kotlinski.imagesort.javafx.controllers.listeners.FindDuplicatesGUIFeedback;
import se.kotlinski.imagesort.javafx.controllers.listeners.MoveGUIFeedback;
import se.kotlinski.imagesort.javafx.controllers.listeners.PreMoveGUIFeedback;
import se.kotlinski.imagesort.javafx.controllers.tabs.FindDuplicatesTabController;
import se.kotlinski.imagesort.javafx.controllers.tabs.MoveTabController;
import se.kotlinski.imagesort.javafx.controllers.tabs.PreMoveTabController;
import se.kotlinski.imagesort.javafx.controllers.tabs.SelectFolderTabController;
import se.kotlinski.imagesort.secrets.Passwords;

import java.io.File;
import java.util.List;
import java.util.Map;

public class TabGroupController implements TabSwitcher {

  //TABS
  @FXML
  public TabPane tabGroup;
  @FXML
  public Tab preMoveTab;
  @FXML
  public Tab selectFolderTab;
  @FXML
  public Tab moveTab;
  @FXML
  public Tab findDuplicatesTab;
  @FXML
  public Button selectFolderButton;
  @FXML
  public Button selectFolderContinueButton;
  @FXML
  public Button findDuplicatesContinueButton;
  @FXML
  public AnchorPane findDuplicatesLoadingScene;
  @FXML
  public AnchorPane preMoveLoadingScene;
  @FXML
  public AnchorPane moveLoadingScene;
  @FXML
  public AnchorPane findDuplicatesResultScene;
  @FXML
  public AnchorPane preMoveResultScene;
  @FXML
  public AnchorPane moveResultScene;
  @FXML
  public Text selectedFolderPathText;
  @FXML
  public Text findDuplicatesTabLoadingText;
  @FXML
  public Text preMoveTabLoadingText;
  @FXML
  public Text moveTabLoadingText;
  @FXML
  public ProgressBar findDuplicatesTabProgressBar;
  @FXML
  public ProgressBar moveTabProgressBar;
  @FXML
  public ProgressBar preMoveTabProgressBar;
  @FXML
  public TextArea findDuplicatesFolderTextArea;
  @FXML
  public TextArea preMoveFolderTextArea;
  @FXML
  public ButtonBase moveImagesButton;

  //Select folder tab
  private SelectFolderTabController selectFolderTabController;
  //Result tab
  private SingleSelectionModel<Tab> tabSelector;
  private PreMoveTabController preMoveTabController;
  private MoveTabController moveTabController;
  private FindDuplicatesTabController findDuplicatesTabController;

  public TabGroupController() {
  }


  @FXML
  private void initialize() {

    findDuplicatesTabController = new FindDuplicatesTabController(this,
                                                                  findDuplicatesTab,
                                                                  findDuplicatesLoadingScene,
                                                                  findDuplicatesResultScene,
                                                                  findDuplicatesTabLoadingText,
                                                                  findDuplicatesTabProgressBar,
                                                                  findDuplicatesFolderTextArea);

    FindDuplicatesGUIFeedback findDuplicatesFeedbackInterface;
    findDuplicatesFeedbackInterface = new FindDuplicatesGUIFeedback(findDuplicatesTabController);


    moveTabController = new MoveTabController(moveTab,
                                              this,
                                              moveLoadingScene,
                                              moveResultScene,
                                              moveTabLoadingText,
                                              moveTabProgressBar);

    MoveGUIFeedback moveGUIFeedback = new MoveGUIFeedback(moveTabController);
    tabSelector = tabGroup.getSelectionModel();

    preMoveTabController = new PreMoveTabController(moveGUIFeedback,
                                                    this,
                                                    preMoveTab,
                                                    preMoveLoadingScene,
                                                    preMoveResultScene,
                                                    preMoveTabLoadingText,
                                                    preMoveTabProgressBar,
                                                    preMoveFolderTextArea,
                                                    moveImagesButton);
    preMoveTabController.resetTab();

    PreMoveGUIFeedback preMoveGUIFeedback;
    preMoveGUIFeedback = new PreMoveGUIFeedback(preMoveTabController);

    selectFolderTabController = new SelectFolderTabController(preMoveGUIFeedback,
                                                              preMoveGUIFeedback,
                                                              findDuplicatesFeedbackInterface,
                                                              this,
                                                              selectFolderButton,
                                                              selectFolderContinueButton,
                                                              selectedFolderPathText,
                                                              findDuplicatesContinueButton);

    Passwords passwords = new Passwords();
    System.out.println("Pass: " + passwords.getPassword());

  }

  public void setStageAndSetupListeners(final Stage primaryStage) {
    selectFolderTabController.setStageAndSetupListeners(primaryStage);
  }


  @Override
  public void resetTabs() {
    preMoveTabController.resetTab();
    moveTabController.resetTab();

    tabSelector.select(selectFolderTab);

    selectFolderTab.setDisable(false);
    preMoveTab.setDisable(true);
    moveTab.setDisable(true);
    findDuplicatesTab.setDisable(true);
  }

  @Override
  public void setTabsInPreMoveMode() {
    preMoveTabController.resetTab();

    tabSelector.select(preMoveTab);

    preMoveTab.setDisable(false);
    selectFolderTab.setDisable(true);
    moveTab.setDisable(true);
    findDuplicatesTab.setDisable(true);
  }

  @Override
  public void setTabsInPreMoveModeDone(Map<List<File>, RelativeMediaFolderOutput> mediaFileDestinations) {
    preMoveTabController.successfulCalculatedOutputDestinations(mediaFileDestinations);

    selectFolderTab.setDisable(false);
    preMoveTab.setDisable(false);
    moveTab.setDisable(true);
    findDuplicatesTab.setDisable(false);
  }

  @Override
  public void setTabsInMoveMode() {
    moveTabController.resetTab();

    tabSelector.select(moveTab);
    moveTab.setDisable(false);
    preMoveTab.setDisable(true);
    selectFolderTab.setDisable(true);
    findDuplicatesTab.setDisable(true);
  }

  @Override
  public void setTabsInMoveModeDone() {
    preMoveTab.setDisable(false);
    selectFolderTab.setDisable(false);
    findDuplicatesTab.setDisable(false);
  }

  @Override
  public void setTabsInFindDuplicatesMode() {
    findDuplicatesTabController.resetTab();

    tabSelector.select(findDuplicatesTab);
    findDuplicatesTab.setDisable(false);

    selectFolderTab.setDisable(true);
    moveTab.setDisable(true);
    preMoveTab.setDisable(true);
  }

  @Override
  public void setTabsInFindDuplicatesModeDone() {
    findDuplicatesTab.setDisable(false);
    selectFolderTab.setDisable(false);
    moveTab.setDisable(true);
    preMoveTab.setDisable(true);

  }

}

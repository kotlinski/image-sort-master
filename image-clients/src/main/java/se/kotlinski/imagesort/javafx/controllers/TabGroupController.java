package se.kotlinski.imagesort.javafx.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;

public class TabGroupController {

  //TABS
  @FXML
  public TabPane tabGroup;
  @FXML
  public Tab analyzeTab;
  @FXML
  public Tab selectFolderTab;
  @FXML
  public Tab moveTab;

  //Select folder tab
  @FXML
  public Button selectFolderButton;
  @FXML
  public Button selectFolderContinueButton;

  //Analyze tab
  @FXML
  public AnchorPane analyzeLoadingScene;
  @FXML
  public AnchorPane analyzeResultScene;

  //Result tab
  private SingleSelectionModel<Tab> selectionModel;

  public TabGroupController() {
  }

  @FXML
  private void initialize() {
    selectionModel = tabGroup.getSelectionModel();

    selectFolderContinueButton.setOnAction((event) -> {
      analyzeTab.setDisable(false);
      selectionModel.select(analyzeTab);
    });

    selectFolderButton.setOnAction((event) -> {
      analyzeLoadingScene.setVisible(false);
      analyzeResultScene.setVisible(true);
    });
  }
}

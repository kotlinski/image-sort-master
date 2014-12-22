package se.kotlinski.imagesort.controller;

import com.google.inject.Inject;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import se.kotlinski.imagesort.view.FolderSelector;
import se.kotlinski.imagesort.utils.JavaFXUtils;

import java.io.File;

public class ApplicationController extends Application {
	private final DirectoryChooser directoryChooser;
  private final JavaFXUtils javaFxUtil;

  @Inject
  public ApplicationController(final DirectoryChooser directoryChooser, final JavaFXUtils
      javaFxUtil) {
    this.directoryChooser = directoryChooser;
    this.javaFxUtil = javaFxUtil;
  }


  public void startApplication(final String[] args) {
		//go to start()
		Application.launch(args);
	}

	@Override
	public void start(final Stage primaryStage) throws Exception {
		FolderSelector folderSelector = new FolderSelector(primaryStage);
		inputFolderSetup(folderSelector, primaryStage);
		outputFolderSetup(folderSelector, primaryStage);
		folderSelector.setupGridLayout();
	}

	private void inputFolderSetup(final FolderSelector folderSelector, final Stage primaryStage) {
		EventHandler<ActionEvent> selectInputEvent = e -> {
      File folder =	directoryChooser.showDialog(primaryStage);
      if (folder != null) {
        javaFxUtil.alert(folder.getName());
      }
    };
		folderSelector.setupInputButton(selectInputEvent);
	}

	private void outputFolderSetup(final FolderSelector folderSelector, final Stage primaryStage) {
		EventHandler<ActionEvent> selectOutputEvent = e -> {
      File folder = directoryChooser.showDialog(primaryStage);
      if (folder != null) {
        javaFxUtil.alert(folder.getName());
      }
    };
		folderSelector.setupOutputButton(selectOutputEvent);
	}
}

package se.kotlinski.imageRenamer.controllers;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import se.kotlinski.imageRenamer.views.FolderSelector;
import se.kotlinski.imageRenamer.utils.JavaFXUtils;

import java.io.File;

/**
 * Run the Image Renamer via Application.
 *
 * @author Simon Kotlinski
 * @version $Revision: 1.1 $
 */
public class ApplicationController extends Application {
	private File inputFolder;
	private File outputFolder;
	private static DirectoryChooser directoryChooser;


	public static void startApplication(final String[] args) {
		directoryChooser = new DirectoryChooser();
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

	private static void inputFolderSetup(final FolderSelector folderSelector, final Stage primaryStage) {
		EventHandler<ActionEvent> selectInputEvent = new EventHandler<ActionEvent>() {
			@Override
			public void handle(final ActionEvent e) {
				File folder =	directoryChooser.showDialog(primaryStage);
				if (folder != null) {
					//FolderSelector.openFile(folder);
					JavaFXUtils.alert(folder.getName());

				}
			}
		};
		folderSelector.setupInputButton(selectInputEvent);
	}

	private static void outputFolderSetup(final FolderSelector folderSelector, final Stage primaryStage) {
		EventHandler<ActionEvent> selectOutputEvent = new EventHandler<ActionEvent>() {
			@Override
			public void handle(final ActionEvent e) {
				File folder = directoryChooser.showDialog(primaryStage);
				if (folder != null) {
					//FolderSelector.openFile(folder);
					JavaFXUtils.alert(folder.getName());
				}
			}
		};
		folderSelector.setupOutputButton(selectOutputEvent);
	}
}

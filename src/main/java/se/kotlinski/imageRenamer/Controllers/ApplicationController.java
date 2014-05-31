package se.kotlinski.imageRenamer.Controllers;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Run the Image Renamer via Application.
 *
 * @author Simon Kotlinski
 * @version $Revision: 1.1 $
 */
public class ApplicationController extends Application {
	private Desktop desktop = Desktop.getDesktop();


	public static void startApplication(final String[] args) {
		//go to start()
		Application.launch(args);

	}

	@Override
	public void start(final Stage primaryStage) throws Exception {
		primaryStage.setTitle("File Chooser Sample");

		final FileChooser fileChooser = new FileChooser();

		final Button openButton = new Button("Open a Picture...");
		final Button openMultipleButton = new Button("Open Pictures...");

		openButton.setOnAction(
				new EventHandler<ActionEvent>() {
					@Override
					public void handle(final ActionEvent e) {
						File file = fileChooser.showOpenDialog(primaryStage);
						if (file != null) {
							openFile(file);
						}
					}
				}
		);

		openMultipleButton.setOnAction(
				new EventHandler<ActionEvent>() {
					@Override
					public void handle(final ActionEvent e) {
						List<File> list =
								fileChooser.showOpenMultipleDialog(primaryStage);
						if (list != null) {
							for (File file : list) {
								openFile(file);
							}
						}
					}
				}
		);

		final GridPane inputGridPane = new GridPane();

		GridPane.setConstraints(openButton, 0, 0);
		GridPane.setConstraints(openMultipleButton, 1, 0);
		inputGridPane.setHgap(6);
		inputGridPane.setVgap(6);
		inputGridPane.getChildren().addAll(openButton, openMultipleButton);

		final Pane rootGroup = new VBox(12);
		rootGroup.getChildren().addAll(inputGridPane);
		rootGroup.setPadding(new Insets(12, 12, 12, 12));

		primaryStage.setScene(new Scene(rootGroup));
		primaryStage.show();
	}

	private void openFile(File file) {
		try {
			desktop.open(file);
		}
		catch (IOException ex) {
			Logger.getLogger(
					ApplicationController.class.getName()).log(
					Level.SEVERE, null, ex
			);
		}
	}
}

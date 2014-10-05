package se.kotlinski.imageRenamer.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * View class for select input output folder
 *
 * @author Simon Kotlinski
 * @version $Revision: 1.1 $
 */
public class FolderSelector {
	private final Stage primaryStage;
	private Button inputButton;
	private Button outputButton;

	public FolderSelector(final Stage primaryStage) {
		this.primaryStage = primaryStage;
		primaryStage.setTitle("Set input/output folders");
	}

	public static void openFile(File file) {
		Desktop desktop = Desktop.getDesktop();

		try {
			desktop.open(file);
		}
		catch (IOException ex) {
			Logger.getLogger(
					FolderSelector.class.getName()).log(
					Level.SEVERE, null, ex
			);
		}
	}
	public void setupInputButton(final EventHandler<ActionEvent> selectInputEvent){
		inputButton = new Button("Select source folder");
		inputButton.setOnAction(selectInputEvent);
	}
	public void setupOutputButton(final EventHandler<ActionEvent> selectOutputEvent){
		outputButton = new Button("Select Output Folder");
		outputButton.setOnAction(selectOutputEvent);
	}

	public void setupGridLayout(){

		final GridPane inputGridPane = new GridPane();

		GridPane.setConstraints(inputButton, 0, 0);
		GridPane.setConstraints(outputButton, 1, 0);
		inputGridPane.setHgap(6);
		inputGridPane.setVgap(6);
		inputGridPane.getChildren().addAll(inputButton, outputButton);

		final Pane rootGroup = new VBox(12);
		rootGroup.getChildren().addAll(inputGridPane);
		rootGroup.setPadding(new Insets(12, 12, 12, 12));

		primaryStage.setScene(new Scene(rootGroup));
		primaryStage.show();
	}
}

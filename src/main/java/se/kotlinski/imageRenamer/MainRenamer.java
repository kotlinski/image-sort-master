package se.kotlinski.imageRenamer;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import se.kotlinski.imageRenamer.utils.ImageIndex;

import javax.swing.*;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainRenamer extends Application {
	private static ImageIndex imageIndex;
	private static File inputRoot;
	private static File outputFolder;

	private Desktop desktop = Desktop.getDesktop();

	public static void main(String[] args) {

		Application.launch(args);
/*
		imageIndex = new ImageIndex();
		Options options = createOptions();
		CommandLineParser parser = new GnuParser();
		CommandLine cmd = null;
		try {
			cmd = parser.parse(options, argv);
		}
		catch (ParseException e) {
			System.err.println("Parsing failed.  Reason: " + e.getMessage());
		}
		runCmd(options, cmd);*/
	}

	private static void runCmd(Options options, CommandLine cmd) {
		if (inputRoot != null && outputFolder != null) {
			imageIndex.runIndexing(inputRoot);
		}
		else if (cmd == null) {
			printHelp(options);
		}
		else if (cmd.hasOption("source") && cmd.hasOption("output")) {
			String sourcePath = cmd.getOptionValue("source");
			if (validSource(sourcePath)) {
				File rootFile = new File(sourcePath);
				imageIndex.runIndexing(rootFile);
			}
			else {
				System.out.print("SourcePath not ok!");
			}
/*
			FileSystemProvider fileSystemProvider = new FileSystemProvider();
			//fileSystemProvider.createFolder("");

			FileRenamer fileRenamer = new FileRenamer();
*/
		}
		else {
			printHelp(options);
		}
	}

	private static boolean validSource(final String sourcePath) {
		return true;
	}

	private static Options createOptions() {
		Options options = new Options();
		options.addOption("s", "source", true, "Import from this folder.");
		options.getOption("s").setRequired(true);
		options.addOption("o", "output", true, "Export to this folder");
		options.getOption("o").setRequired(true);

		options.addOption("h", "help", false, "MainRenamer usage\n" +
		                                      "Main purpose is to read all images from a source-path and\n" +
		                                      "export them to a given destionation. \n\n" +
		                                      "When you have your images backed up via dropbox and manually, \n" +
		                                      "it may be hard giving them smart names. Sometimes you will get\n" +
		                                      "duplicated images on your back-up drive." +
		                                      "java -jar ImageRename <sourcePath> <outputPath>. \n\n" +
		                                      "The sourcePath read folders and files recursivly, so you can put all" +
		                                      "your folders in the same directary. For example Dropbox-folders, etc"
		);
		return options;
	}

	private static void printHelp(Options options) {
		HelpFormatter formatter = new HelpFormatter();
		formatter.printHelp("MainRenamer", options);
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
					MainRenamer.class.getName()).log(
					Level.SEVERE, null, ex
			);
		}
	}
}
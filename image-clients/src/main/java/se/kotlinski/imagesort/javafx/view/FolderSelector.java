package se.kotlinski.imagesort.javafx.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * View class for select input output folder
 *
 * @author Simon Kotlinski
 * @version $Revision: 1.1 $
 */
public class FolderSelector {
  public static final int INPUT_BUTTON_COLUMN_INDEX = 0;
  public static final int INPUT_BUTTON_ROW_INDEX = 0;
  public static final int OUTPUT_BUTTON_COLUMN_INDEX = 1;
  public static final int OUTPUT_BUTTON_ROW_INDEX = 0;
  public static final int INPUT_HORIZONTAL_GAP = 6;
  public static final int INPUT_VERTICAL_GAP = 6;
  private static final int ROOT_GROUP_SPACING = 12;
  private final Stage primaryStage;
  private Button inputButton;
  private Button outputButton;

  public FolderSelector(final Stage primaryStage) {
    this.primaryStage = primaryStage;
    primaryStage.setTitle("Set input/output folders");
  }

  public final void setupInputButton(final EventHandler<ActionEvent> selectInputEvent) {
    inputButton = new Button("Select source folder");
    inputButton.setOnAction(selectInputEvent);
  }

  public final void setupOutputButton(final EventHandler<ActionEvent> selectOutputEvent) {
    outputButton = new Button("Select Output Folder");
    outputButton.setOnAction(selectOutputEvent);
  }

  public final void setupGridLayout() {

    final GridPane inputGridPane = new GridPane();

    GridPane.setConstraints(inputButton, INPUT_BUTTON_COLUMN_INDEX, INPUT_BUTTON_ROW_INDEX);
    GridPane.setConstraints(outputButton, OUTPUT_BUTTON_COLUMN_INDEX, OUTPUT_BUTTON_ROW_INDEX);
    inputGridPane.setHgap(INPUT_HORIZONTAL_GAP);
    inputGridPane.setVgap(INPUT_VERTICAL_GAP);
    inputGridPane.getChildren().addAll(inputButton, outputButton);

    final Pane rootGroup = new VBox(ROOT_GROUP_SPACING);
    rootGroup.getChildren().addAll(inputGridPane);
    rootGroup.setPadding(new Insets(ROOT_GROUP_SPACING,
                                    ROOT_GROUP_SPACING,
                                    ROOT_GROUP_SPACING,
                                    ROOT_GROUP_SPACING));

    primaryStage.setScene(new Scene(rootGroup));
    primaryStage.show();
  }
}

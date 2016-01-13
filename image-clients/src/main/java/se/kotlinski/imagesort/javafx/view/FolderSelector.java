package se.kotlinski.imagesort.javafx.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
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
  final Float[] values = new Float[]{-1.0f, 0f, 0.6f, 1.0f};
  final Label[] labels = new Label[values.length];
  final ProgressBar[] pbs = new ProgressBar[values.length];
  final ProgressIndicator[] pins = new ProgressIndicator[values.length];
  final HBox hbs[] = new HBox[values.length];
  private final Stage primaryStage;
  private Button inputButton;

  public FolderSelector(final Stage primaryStage) {
    this.primaryStage = primaryStage;
    primaryStage.setTitle("Set input/output folders");
  }

  public final void setupInputButton(final EventHandler<ActionEvent> selectInputEvent) {
    inputButton = new Button("Select source folder");
    inputButton.setOnAction(selectInputEvent);
  }

  public final void setupGridLayout(final Stage stage) {


    final GridPane inputGridPane = new GridPane();
    GridPane.setConstraints(inputButton, INPUT_BUTTON_COLUMN_INDEX, INPUT_BUTTON_ROW_INDEX);
//    GridPane.setConstraints(outputButton, OUTPUT_BUTTON_COLUMN_INDEX, OUTPUT_BUTTON_ROW_INDEX);
    inputGridPane.setHgap(INPUT_HORIZONTAL_GAP);
    inputGridPane.setVgap(INPUT_VERTICAL_GAP);
    inputGridPane.getChildren().addAll(inputButton);

    final Pane rootGroup = new VBox(ROOT_GROUP_SPACING);
    rootGroup.getChildren().addAll(inputGridPane);
    rootGroup.setPadding(new Insets(ROOT_GROUP_SPACING,
                                    ROOT_GROUP_SPACING,
                                    ROOT_GROUP_SPACING,
                                    ROOT_GROUP_SPACING));


    stage.setTitle("Progress Controls");


    for (int i = 0; i < values.length; i++) {
      final Label label = labels[i] = new Label();
      label.setText("progress:" + values[i]);

      final ProgressBar pb = pbs[i] = new ProgressBar();
      pb.setProgress(values[i]);

      final ProgressIndicator pin = pins[i] = new ProgressIndicator();
      pin.setProgress(values[i]);
      final HBox hb = hbs[i] = new HBox();
      hb.setSpacing(5);
      hb.setAlignment(Pos.CENTER);
      hb.getChildren().addAll(label, pb, pin);
    }

    final VBox vb = new VBox();
    vb.setSpacing(5);
    vb.getChildren().addAll(hbs);


    Scene scene = new Scene(rootGroup, 300, 150);
    rootGroup.getChildren().addAll(vb);

    stage.show();


    primaryStage.setScene(scene);
    primaryStage.show();
  }
}

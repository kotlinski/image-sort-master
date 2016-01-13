package se.kotlinski.imagesort.javafx.controllers;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class JavaFXUtils {

  public static final double MIN_HEIGHT = 120.0d;
  public static final double MIN_WIDTH = 200.0d;
  public static final int SCENE_X = 25;
  public static final int SCENE_Y = 25;

  final void alert(String text) {
    Stage dialog = new Stage();
    dialog.initStyle(StageStyle.UTILITY);

    Scene scene = new Scene(new Group(new Text(SCENE_X, SCENE_Y, text)));
    dialog.setScene(scene);
    dialog.setMinWidth(MIN_WIDTH);
    dialog.setMinHeight(MIN_HEIGHT);
    dialog.show();
  }
}

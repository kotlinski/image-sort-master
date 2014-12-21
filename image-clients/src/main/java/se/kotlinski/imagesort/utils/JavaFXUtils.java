package se.kotlinski.imagesort.utils;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class JavaFXUtils {
  public void alert(String text) {
    Stage dialog = new Stage();
    dialog.initStyle(StageStyle.UTILITY);

    Scene scene = new Scene(new Group(new Text(25, 25, text)));
    dialog.setScene(scene);
    dialog.setMinWidth(200.0d);
    dialog.setMinHeight(120.0d);
    dialog.show();
  }
}

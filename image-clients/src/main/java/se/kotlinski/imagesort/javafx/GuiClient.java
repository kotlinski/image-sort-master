package se.kotlinski.imagesort.javafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import se.kotlinski.imagesort.javafx.controllers.TabGroupController;


public class GuiClient extends Application {

  public void startApplication() {
    Application.launch();
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/sample.fxml"));
    Parent root = loader.load();
    TabGroupController controller = loader.getController();
    controller.setStageAndSetupListeners(primaryStage); // or what you want to do

    primaryStage.setTitle("Image Sorter");
    primaryStage.setScene(new Scene(root));
    primaryStage.setResizable(false);
    primaryStage.show();
  }

}

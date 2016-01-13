package se.kotlinski.imagesort.javafx.controllers;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class GuiClient extends Application {

  public void startApplication() {
    Application.launch();
  }

  @Override
  public void start(Stage primaryStage) throws Exception {

    URL resource = getClass().getResource("/fxml/sample.fxml");
    Parent root = FXMLLoader.load(resource);


    primaryStage.setTitle("Hello World");
    primaryStage.setScene(new Scene(root));
    primaryStage.setResizable(false);

    primaryStage.show();
  }
}

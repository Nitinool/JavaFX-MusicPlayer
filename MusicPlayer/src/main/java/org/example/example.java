package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;

public class example extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(new URL("file:///D:\\Program_code\\Java\\javafxdemo\\MusicPlayer\\src\\main\\java\\org\\example\\Controller.fxml"));

        VBox vbox = fxmlLoader.load();
        Scene scene = new Scene(vbox);
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}

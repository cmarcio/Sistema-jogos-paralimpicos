package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("start.fxml"));
        primaryStage.setTitle("Jogos Paralímpicos");
        primaryStage.setScene(new Scene(root, 800, 500));
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("../resources/icons/jogos_logo.png")));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}

package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.db.Athlete;
import main.db.Bridge;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
        //Bridge conection = new Bridge();
        //conection.insertTime();
        ///Athlete atl = new Athlete("test1", "futebol", "Brasil", 112, "M", LocalDate.now());
        //atl.insertIntoDB();
        //Bridge bd =  new Bridge();
        //ArrayList<Athlete> list = Bridge.getAthletes();
        //for(int i = 0; i < list.size(); i++)
         //   System.out.println(i + ": " + list.get(i).getName() + list.get(i).getCountry() + list.get(i).getSport());
        launch(args);
    }
}

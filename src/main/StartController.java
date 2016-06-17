package main;

import com.sun.istack.internal.NotNull;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class StartController implements Initializable {
    /*
    * Barra de menu
    * */

    @FXML
    private Button btnAthletes;

    /*
    * Barra de buscas
    * */

    @FXML
    private VBox searchBar;

    @FXML
    private TextField searchField;

    @FXML
    private Button btnSearch;

    @FXML
    private RadioButton rbtn1;

    @FXML
    private RadioButton rbtn2;

    @FXML
    private RadioButton rbtn3;

    /*
    * Area de conteudo
    * */

    @FXML
    private BorderPane contentArea;

    @FXML
    private TableView<?> table;

    @FXML
    private Label entityName;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //searchBar.setVisible(false);
        //contentArea.setVisible(false);

        btnAthletes.setOnAction(event -> {
            openWindow("register/athlete.fxml");
        });
    }

    private void openWindow(String fxml){
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource(fxml));
            Stage stage = new Stage();
            stage.setTitle("Cadastrar");
            stage.setScene(new Scene(root, 400, 600));
            stage.getIcons().add(new Image(getClass().getResourceAsStream("../resources/icons/jogos_logo.png")));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("ERRO AO ABRIR JANELA DE CADASTRO");
        }
    }
}

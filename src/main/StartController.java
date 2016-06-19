package main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.db.Athlete;
import main.db.Bridge;

import java.io.IOException;
import java.time.LocalDate;

public class StartController {
    /*
    * Barra de menu
    * */
    @FXML private Button btnAthletes;

    /*
    * Barra de buscas
    * */
    @FXML private VBox searchBar;
    @FXML private TextField searchField;
    @FXML private Button btnSearch;
    @FXML private RadioButton rbtn1;
    @FXML private RadioButton rbtn2;
    @FXML private RadioButton rbtn3;
    @FXML private Button btnAdd;

    /*
    * Area de conteudo
    * */
    @FXML private BorderPane contentArea;
    @FXML private Label entityName;

    /*
    * Tabelas e colunas
    * */
    private ObservableList<Athlete> athleteData = FXCollections.observableArrayList();
    @FXML private TableView<Athlete> tableAthlete;
    @FXML private TableColumn<Athlete, String> athleteName;
    @FXML private TableColumn<Athlete, String> athleteCountry;
    @FXML private TableColumn<Athlete, String> athleteGenre;
    @FXML private TableColumn<Athlete, Number> athleteNumber;
    @FXML private TableColumn<Athlete, String> athleteSport;
    @FXML private TableColumn<Athlete, LocalDate> athleteBirth;

    @FXML
    public void initialize() {
        // Inicializa tela inicial
        //searchBar.setVisible(false);
        //contentArea.setVisible(false);

        // Inicializa Tabelas
        // Tabela Atleta
        athleteName.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        athleteSport.setCellValueFactory(cellData -> cellData.getValue().sportProperty());
        athleteNumber.setCellValueFactory(cellData -> cellData.getValue().numberProperty());
        athleteGenre.setCellValueFactory(cellData -> cellData.getValue().genreProperty());
        athleteBirth.setCellValueFactory(cellData -> cellData.getValue().birthProperty());
        athleteCountry.setCellValueFactory(cellData -> cellData.getValue().countryProperty());
        tableAthlete.setItems(athleteData);


        // Define os handlers
        btnAthletes.setOnAction(event -> {
            entityName.setText("Atleta");
            athleteData.clear();
            athleteData.addAll(Bridge.getAthletes());
        });

        btnAdd.setOnAction(event -> {
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

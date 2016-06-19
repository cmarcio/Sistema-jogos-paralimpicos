package main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.db.Athlete;
import main.db.Bridge;

import java.io.IOException;
import java.time.LocalDate;

public class StartController {
    // Variáveis de controle
    private int contentDisplay = 0;
    final ToggleGroup fieldGroup = new ToggleGroup();

    // Barra de menu
    @FXML private Button btnAthletes;

    // Barra de buscas
    @FXML private VBox searchBar;
    @FXML private TextField searchField;
    @FXML private Button btnSearch;
    @FXML private RadioButton rbtn1;
    @FXML private RadioButton rbtn2;
    @FXML private RadioButton rbtn3;
    @FXML private Button btnAdd;

    // Area de conteudo
    @FXML private BorderPane contentArea;
    @FXML private Label entityName;
    @FXML private ProgressIndicator progress;
    @FXML private BorderPane progressPane;

    // Tabelas e colunas
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

        // Inicializa radio buttons
        rbtn1.setToggleGroup(fieldGroup);
        rbtn2.setToggleGroup(fieldGroup);
        rbtn3.setToggleGroup(fieldGroup);
        rbtn1.setSelected(true);

        // Inicializa Tabelas
        // Tabela Atleta
        athleteName.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        athleteSport.setCellValueFactory(cellData -> cellData.getValue().sportProperty());
        athleteNumber.setCellValueFactory(cellData -> cellData.getValue().numberProperty());
        athleteGenre.setCellValueFactory(cellData -> cellData.getValue().genderProperty());
        athleteBirth.setCellValueFactory(cellData -> cellData.getValue().birthProperty());
        athleteCountry.setCellValueFactory(cellData -> cellData.getValue().countryProperty());
        tableAthlete.setItems(athleteData);


        // Define os handlers
        // Botão Atletas
        btnAthletes.setOnAction(event -> {
            startAthleteView();
            searchAthletes();
        });

        // Botão buscar
        btnSearch.setOnAction(event -> {
            switch (contentDisplay){
                case 1:
                    startAthleteView();
                    searchAthletes();
                    break;
            }
        });

        // Botão adicionar
        btnAdd.setOnAction(event -> {
            switch (contentDisplay){
                case 1:
                    openWindow("register/athlete.fxml");
            }
        });
    }

    // Abre uma janela de registros
    private void openWindow(String fxml){
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource(fxml));
            Stage stage = new Stage();
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(btnAdd.getScene().getWindow());
            stage.setTitle("Cadastrar");
            stage.setScene(new Scene(root, 400, 600));
            stage.getIcons().add(new Image(getClass().getResourceAsStream("../resources/icons/jogos_logo.png")));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("ERRO AO ABRIR JANELA DE CADASTRO");
        }
    }

    private void startAthleteView(){
        contentDisplay = 1;
        entityName.setText("Atleta");
        rbtn1.setText("Nome");
        rbtn2.setText("Número");
        rbtn3.setVisible(false);
        progressPane.setVisible(true);
        progress.setVisible(true);
    }
    private void searchAthletes(){
        new Thread(){
            public void run(){
                athleteData.clear();
                if (searchField.getText().isEmpty())
                    athleteData.addAll(Bridge.getAthletes(null));
                else if (rbtn1.isSelected())
                    athleteData.addAll(Bridge.getAthletes("WHERE nome = '" + searchField.getText() + "'"));
                else
                    athleteData.addAll(Bridge.getAthletes("WHERE numero = '" + searchField.getText() + "'"));
                progress.setVisible(false);
                progressPane.setVisible(false);
            }
        }.start();
    }
}

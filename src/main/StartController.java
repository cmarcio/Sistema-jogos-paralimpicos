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
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.db.Athlete;
import main.db.Bridge;
import main.db.Country;
import main.db.Sport;

import java.io.IOException;
import java.time.LocalDate;

public class StartController {
    // Variáveis de controle
    private int contentDisplay = 0;
    final ToggleGroup fieldGroup = new ToggleGroup();

    // Barra de menu
    @FXML private Button btnAthletes;
    @FXML private Button btnSport;
    @FXML private Button btnCountry;

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
    // Tabela Atleta
    private ObservableList<Athlete> athleteData = FXCollections.observableArrayList();
    @FXML private TableView<Athlete> tableAthlete;
    @FXML private TableColumn<Athlete, String> athleteName;
    @FXML private TableColumn<Athlete, String> athleteCountry;
    @FXML private TableColumn<Athlete, String> athleteGenre;
    @FXML private TableColumn<Athlete, Number> athleteNumber;
    @FXML private TableColumn<Athlete, String> athleteSport;
    @FXML private TableColumn<Athlete, LocalDate> athleteBirth;
    // Tabela Esporte
    private ObservableList<Sport> sportData = FXCollections.observableArrayList();
    @FXML private TableView<Sport> tableSport;
    @FXML private TableColumn<Sport, String> sportName;
    // Tabela de países
    private ObservableList<Country> countryData = FXCollections.observableArrayList();
    @FXML private TableView<Country> tableCountry;
    @FXML private TableColumn<Country, String> countryName;

    @FXML
    public void initialize() {
        // Inicializa tela inicial
        searchBar.setVisible(false);
        contentArea.setVisible(false);

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
        // Tabela Esporte
        sportName.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        tableSport.setItems(sportData);
        // Tabela País
        countryName.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        tableCountry.setItems(countryData);

        // Define os handlers
        // Botão Atletas
        btnAthletes.setOnAction(event -> {
            startAthleteView();
            searchAthletes();
        });
        // Botão Esporte
        btnSport.setOnAction(event -> {
            startSportView();
            searchSports();
        });
        // Botão países
        btnCountry.setOnAction(event -> {
            startCountryView();
            searchCountries();
        });

        // Botão buscar
        btnSearch.setOnAction(event -> {
            switch (contentDisplay){
                case 1:
                    startAthleteView();
                    searchAthletes();
                    break;
                case 2:
                    startSportView();
                    searchSports();
                    break;
                case 3:
                    startCountryView();
                    searchCountries();
                    break;
            }
        });

        // Botão adicionar
        btnAdd.setOnAction(event -> {
            switch (contentDisplay){
                case 1:
                    openWindow("register/athlete.fxml");
                    break;
                case 2:
                    openWindow("register/sport.fxml");
                    break;
                case 3:
                    openWindow("register/country.fxml");
                    break;
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
            stage.setScene(new Scene(root));
            stage.getIcons().add(new Image(getClass().getResourceAsStream("../resources/icons/jogos_logo.png")));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("ERRO AO ABRIR JANELA DE CADASTRO");
        }
    }

    private void hideTables(){
        tableAthlete.setVisible(false);
        tableSport.setVisible(false);
        tableCountry.setVisible(false);
    }

    private void startAthleteView(){
        contentDisplay = 1;
        hideTables();
        tableAthlete.setVisible(true);
        searchBar.setVisible(true);
        contentArea.setVisible(true);
        entityName.setText("Atleta");
        rbtn1.setText("Nome");
        rbtn2.setText("Número");
        rbtn2.setVisible(true);
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
    private void startSportView(){
        contentDisplay = 2;
        hideTables();
        tableSport.setVisible(true);
        searchBar.setVisible(true);
        contentArea.setVisible(true);
        entityName.setText("Esporte");
        rbtn1.setText("Nome");
        rbtn2.setVisible(false);
        rbtn3.setVisible(false);
        progressPane.setVisible(true);
        progress.setVisible(true);
    }
    private void searchSports(){
        new Thread(){
            public void run(){
                sportData.clear();
                if (searchField.getText().isEmpty())
                    sportData.addAll(Bridge.getSports(null));
                else
                    sportData.addAll(Bridge.getSports("WHERE nome = '" + searchField.getText() + "'"));
                progress.setVisible(false);
                progressPane.setVisible(false);
            }
        }.start();
    }

    private void startCountryView() {
        contentDisplay = 3;
        hideTables();
        tableCountry.setVisible(true);
        searchBar.setVisible(true);
        contentArea.setVisible(true);
        entityName.setText("País");
        rbtn1.setText("Nome");
        rbtn2.setVisible(false);
        rbtn3.setVisible(false);
        progressPane.setVisible(true);
        progress.setVisible(true);
    }
    private void searchCountries() {
        new Thread(){
            public void run(){
                countryData.clear();
                if (searchField.getText().isEmpty())
                    countryData.addAll(Bridge.getCountry(null));
                else
                    countryData.addAll(Bridge.getCountry("WHERE nome = '" + searchField.getText() + "'"));
                progress.setVisible(false);
                progressPane.setVisible(false);
            }
        }.start();
    }
}

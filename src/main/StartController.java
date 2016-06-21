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
import main.db.*;

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
    @FXML private Button btnDisability;

    // Barra de buscas
    @FXML private VBox searchBar;
    @FXML private TextField searchField;
    @FXML private Button btnSearch;
    @FXML private RadioButton rbtn1;
    @FXML private RadioButton rbtn2;
    @FXML private RadioButton rbtn3;
    @FXML private Button btnAdd;
    @FXML private Button btnDelete;
    //@FXML private Button btnUpdate;
    @FXML private Button btnAddDesability;


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
    @FXML private TableColumn<Country, Number> countryPlace;
    @FXML private TableColumn<Country, String> countryName;
    @FXML private TableColumn<Country, Number> countryGold;
    @FXML private TableColumn<Country, Number> countrySilver;
    @FXML private TableColumn<Country, Number> countryCopper;
    @FXML private TableColumn<Country, Number> countryTotal;
    // Tabela deficiencia
    private ObservableList<Disability> disabilityData = FXCollections.observableArrayList();
    @FXML private TableView<Disability> tableDisability;
    @FXML private TableColumn<Disability, String> disabilityName;

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
        countryGold.setCellValueFactory(cellData -> cellData.getValue().goldProperty());
        countrySilver.setCellValueFactory(cellData -> cellData.getValue().silverProperty());
        countryCopper.setCellValueFactory(cellData -> cellData.getValue().bronzeProperty());
        countryTotal.setCellValueFactory(cellData -> cellData.getValue().totalProperty());
        countryPlace.setCellValueFactory(cellData -> cellData.getValue().placeProperty());
        tableCountry.setItems(countryData);
        // Tabela Deficiencia
        disabilityName.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        tableDisability.setItems(disabilityData);

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
        // Botão deficiencias
        btnDisability.setOnAction(event -> {
            startDisabilityView();
            searchDisabilities();
        });

        // Campo de busca
        searchField.setOnAction(event -> {
            switch (contentDisplay){
                case 1:
                    searchAthletes();
                    break;
                case 2:
                    searchSports();
                    break;
                case 3:
                    searchCountries();
                    break;
                case 4:
                    searchDisabilities();
                    break;
            }
        });

        // Botão buscar
        btnSearch.setOnAction(event -> {
            switch (contentDisplay){
                case 1:
                    searchAthletes();
                    break;
                case 2:
                    searchSports();
                    break;
                case 3:
                    searchCountries();
                    break;
                case 4:
                    searchDisabilities();
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
                case 4:
                    openWindow("register/disability.fxml");
                    break;
            }
        });
        // Botão remover
        btnDelete.setOnAction(event ->{
            switch (contentDisplay){
                case 1:
                    deleteAthlete();
                    break;
                case 2:
                    deleteSport();
                    break;
                case 3:
                    deleteCountry();
                    break;
                case 4:
                    deleteDisability();
                    break;
            }
            // Botão atualizar
            /*btnUpdate.setOnAction(event1 -> {
                switch (contentDisplay){
                    case 1:
                        updateAthlete();
                }
            });*/
        });

        btnAddDesability.setOnAction(event -> {
            int idx = tableAthlete.getSelectionModel().getSelectedIndex();
            if(idx >= 0) {
                Athlete.staticAthleteName = athleteData.get(idx).getName();
                Athlete.staticNumber = athleteData.get(idx).getNumber();
                openWindow("register/athlete-disability.fxml");
            }
            else{
                showAlert(Alert.AlertType.INFORMATION, "Atenção!", "Nenhuma linha selecionada", "Para cadastrar " +
                        "uma deficiencia de um atleta selecione a linha correspondente da tabela e em seguida clique" +
                        " no botão 'Add Deficiência'");
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
            //stage.getIcons().add(new Image(getClass().getResourceAsStream("../resources/icons/jogos_logo.png")));
            stage.setResizable(false);
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
        tableDisability.setVisible(false);
    }

    private void startView(){
        hideTables();
        searchField.setText("");
        searchBar.setVisible(true);
        contentArea.setVisible(true);
        rbtn2.setVisible(false);
        rbtn3.setVisible(false);
        btnAddDesability.setVisible(false);
    }

    private void startAthleteView(){
        contentDisplay = 1;
        startView();
        tableAthlete.setVisible(true);
        entityName.setText("Atleta");
        rbtn1.setText("Nome");
        rbtn2.setText("Número");
        rbtn2.setVisible(true);
        progressPane.setVisible(true);
        progress.setVisible(true);
        btnAddDesability.setVisible(true);
    }

    private void searchAthletes(){
        new Thread(){
            public void run(){
                athleteData.clear();
                if (searchField.getText().isEmpty())
                    athleteData.addAll(Bridge.getAthletes(0, null));
                else if (rbtn1.isSelected())
                    athleteData.addAll(Bridge.getAthletes(1, searchField.getText()));
                else
                    athleteData.addAll(Bridge.getAthletes(2, searchField.getText()));
                progress.setVisible(false);
                progressPane.setVisible(false);
            }
        }.start();
    }

    private void deleteAthlete(){
        int idx = tableAthlete.getSelectionModel().getSelectedIndex();
        if(idx >= 0) {
            progressPane.setVisible(true);
            progress.setVisible(true);
            new Thread(){
                public void run(){
                    Bridge.removeAthlete(athleteData.get(idx).getNumber());
                    athleteData.remove(idx);
                    progressPane.setVisible(false);
                    progress.setVisible(false);
                }
            }.start();
        }
        else{
            showAlert(Alert.AlertType.INFORMATION, "Atenção!", "Nenhuma linha selecionada", "Para remover um atleta " +
                    "selecione a linha correspondente da tabela e em seguida clique no botão 'Excluir'");
        }
    }

    private void startSportView(){
        contentDisplay = 2;
        startView();
        tableSport.setVisible(true);
        entityName.setText("Esporte");
        rbtn1.setText("Nome");
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
                    sportData.addAll(Bridge.getSports(searchField.getText()));
                progress.setVisible(false);
                progressPane.setVisible(false);
            }
        }.start();
    }
    private void deleteSport(){
        int idx = tableSport.getSelectionModel().getSelectedIndex();
        if(idx >= 0) {
            progressPane.setVisible(true);
            progress.setVisible(true);
            new Thread(){
                public void run(){
                    Bridge.removeSport(sportData.get(idx).getName());
                    sportData.remove(idx);
                    progressPane.setVisible(false);
                    progress.setVisible(false);
                }
            }.start();
        }
        else{
            showAlert(Alert.AlertType.INFORMATION, "Atenção!", "Nenhuma linha selecionada", "Para remover um esporte " +
                    "selecione a linha correspondente da tabela e em seguida clique no botão 'Excluir'");
        }
    }

    private void startCountryView() {
        contentDisplay = 3;
        startView();
        tableCountry.setVisible(true);
        entityName.setText("País");
        rbtn1.setText("Nome");
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
                    countryData.addAll(Bridge.getCountry(searchField.getText()));
                progress.setVisible(false);
                progressPane.setVisible(false);
            }
        }.start();
    }
    private void deleteCountry(){
        int idx = tableCountry.getSelectionModel().getSelectedIndex();
        if(idx >= 0) {
            progressPane.setVisible(true);
            progress.setVisible(true);
            new Thread(){
                public void run(){
                    Bridge.removeCountry(countryData.get(idx).getName());
                    countryData.remove(idx);
                    progressPane.setVisible(false);
                    progress.setVisible(false);
                }
            }.start();
        }
        else{
            showAlert(Alert.AlertType.INFORMATION, "Atenção!", "Nenhuma linha selecionada", "Para remover um país " +
                    "selecione a linha correspondente da tabela e em seguida clique no botão 'Excluir'");
        }
    }

    private void startDisabilityView() {
        contentDisplay = 4;
        startView();
        tableDisability.setVisible(true);
        entityName.setText("Deficiência");
        rbtn1.setText("Nome");
        progressPane.setVisible(true);
        progress.setVisible(true);
    }

    private void searchDisabilities() {
        new Thread(){
            public void run(){
                disabilityData.clear();
                if (searchField.getText().isEmpty())
                    disabilityData.addAll(Bridge.getDisability(null));
                else
                    disabilityData.addAll(Bridge.getDisability(searchField.getText()));
                progress.setVisible(false);
                progressPane.setVisible(false);
            }
        }.start();
    }
    private void deleteDisability(){
        int idx = tableDisability.getSelectionModel().getSelectedIndex();
        if(idx >= 0) {
            progressPane.setVisible(true);
            progress.setVisible(true);
            new Thread(){
                public void run(){
                    Bridge.removeDisability(disabilityData.get(idx).getName());
                    disabilityData.remove(idx);
                    progressPane.setVisible(false);
                    progress.setVisible(false);
                }
            }.start();
        }
        else{
            showAlert(Alert.AlertType.INFORMATION, "Atenção!", "Nenhuma linha selecionada", "Para remover um país " +
                    "selecione a linha correspondente da tabela e em seguida clique no botão 'Excluir'");
        }
    }


    private void showAlert(Alert.AlertType type, String title, String header, String message){
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

package main.register;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.db.Athlete;
import main.db.Bridge;
import main.db.Disability;

import java.time.LocalDate;

public class AthleteDisabilityController {
    // Tela de deficiencias
    @FXML private Button add;
    @FXML private BorderPane progressPane;
    @FXML private Text name;
    @FXML private TextField disabilityField;
    @FXML private ProgressIndicator progress;
    @FXML private TableView<Disability> tableDisability;
    @FXML private TableColumn<Disability, String> disabilityName;

    private ObservableList<Disability> disabilityData = FXCollections.observableArrayList();

    @FXML public void initialize() {
        name.setText(Athlete.staticAthleteName);

        disabilityName.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        tableDisability.setItems(disabilityData);
        disabilityData.addAll(Bridge.getAthleteDisabilities(Athlete.staticNumber));

        // Event Handlers
        add.setOnAction(event -> {
            // Cria instancia atleta
            Athlete.staticDisabilityName = disabilityField.getText();

            class DBService extends Service<String> {
                @Override
                protected Task<String> createTask() {
                    return new Task<String>() {
                        @Override
                        protected String call() throws Exception {
                            Athlete.insertAthleteDisabilityDB();
                            return "ok";
                        }
                    };
                }
            }

            // Serviço de inserção no banco de dados
            DBService service = new DBService();
            progressPane.visibleProperty().bind(service.runningProperty());
            progress.visibleProperty().bind(service.runningProperty());
            service.setOnSucceeded(workerStateEvent -> {
                showAlert(Alert.AlertType.INFORMATION, "Concluído!", null, "O registro foi inserido no banco de dados");
                Stage stage = (Stage) add.getScene().getWindow();
                stage.close();
            });
            service.setOnFailed(workerStateEvent -> {
                showAlert(Alert.AlertType.ERROR, "Erro!", "Não foi possível inserir o registro no banco de dados",
                        "Detalhes:\n" + service.getException().getMessage());
            });
            service.restart();

        });
    }

    private void showAlert(Alert.AlertType type, String title, String header, String message){
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

package main.register;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import main.db.Disability;
import main.db.Sport;

public class DisabilityController {
    @FXML private BorderPane progressPane;
    @FXML private TextField nameField;
    @FXML private ProgressIndicator progress;
    @FXML private Button ok;

    @FXML
    public void initialize(){
        // Event Handlers
        ok.setOnAction(event -> {
            // Le os campos
            String name = nameField.getText();

            // Cria instancia deficiencia
            Disability disability = new Disability(name);

            class DBService extends Service<String> {
                @Override
                protected Task<String> createTask() {
                    return new Task<String>() {
                        @Override
                        protected String call() throws Exception {
                            disability.insertIntoDB();
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
                Stage stage = (Stage) ok.getScene().getWindow();
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


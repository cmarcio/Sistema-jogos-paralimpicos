package main.register;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.db.Athlete;

import java.time.LocalDate;

public class AthleteController{
    final ToggleGroup genderGroup = new ToggleGroup();

    // Tela de cadastro
    @FXML private RadioButton radioBtnMan;
    @FXML private RadioButton radioBtnWoman;
    @FXML private TextField sportField;
    @FXML private TextField numberField;
    @FXML private TextField nameField;
    @FXML private TextField countryField;
    @FXML private DatePicker bornDate;
    @FXML private Button ok;
    @FXML private ProgressIndicator progress;
    @FXML private BorderPane progressPane;

    @FXML public void initialize() {
        // Initializing variables
        // Radio button
        radioBtnMan.setToggleGroup(genderGroup);
        radioBtnWoman.setToggleGroup(genderGroup);
        radioBtnMan.setSelected(true);
        // Datepicker
        bornDate.setValue(LocalDate.now().minusYears(14));
        bornDate.setPromptText(bornDate.getConverter().toString(bornDate.getValue()));
        bornDate.setValue(null);
        // Numeric textfield
        TextFormatter<String> textFormatter = new TextFormatter<>(change -> {
            String text = change.getText();
            if (text.matches("[0-9]*")) {
                return change;
            }
            return null;
        });
        numberField.setTextFormatter(textFormatter);

        // Event Handlers
        ok.setOnAction(event -> {
            // Le os campos
            String name = nameField.getText();
            String country = countryField.getText();
            String sport = sportField.getText();
            int number = Integer.parseInt(numberField.getText());
            LocalDate birth = bornDate.getValue();
            String gender;
            if(radioBtnMan.isSelected())
                gender = "M";
            else gender = "F";

            // Cria instancia atleta
            Athlete athlete = new Athlete(name, sport, country, number, gender);
            if(birth != null)
                athlete.setBirth(birth);

            class DBService extends Service<String> {
                @Override
                protected Task<String> createTask() {
                    return new Task<String>() {
                        @Override
                        protected String call() throws Exception {
                            athlete.insertIntoDB();
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

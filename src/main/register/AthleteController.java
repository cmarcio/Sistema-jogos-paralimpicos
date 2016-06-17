package main.register;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

/**
 * Created by Marcio on 17/06/2016.
 */
public class AthleteController implements Initializable{

    @FXML
    private Button ok;

    @FXML
    private DatePicker bornDate;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ok.setOnAction(event -> {
            /* Chama método de cadastro de atleta*/
        });

        bornDate.setValue(LocalDate.now().minusYears(14));

        bornDate.setOnAction(event -> {
            LocalDate date = bornDate.getValue();
            //System.out.println("Selected date: " + date);
        });

    }
}

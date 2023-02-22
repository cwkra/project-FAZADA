package ku.cs.controllers;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class SignUpSuccessController {
    @FXML private Button submitButton;

    public void initialize() {
        setButtonEffect(submitButton);
    }

    public void setButtonEffect(Button button) {
        button.setOnMouseEntered(mouseEvent -> {
            TranslateTransition transition = new TranslateTransition(Duration.millis(300));
            transition.setNode(button);
            transition.setToY(-2);
            transition.playFromStart();
        });
        button.setOnMouseExited(mouseEvent -> {
            TranslateTransition transition = new TranslateTransition(Duration.millis(300));
            transition.setNode(button);
            transition.setToY(2);
            transition.playFromStart();
        });
    }

    @FXML public void submit(ActionEvent event) {
        Stage stage = (Stage) submitButton.getScene().getWindow();
        stage.close();
        try {
            com.github.saacsos.FXRouter.goTo("sign_in");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า sign_in ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }
}

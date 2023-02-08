package ku.cs.controllers;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class SignUpController {

    @FXML private Button signInButton;
    @FXML private Button signUpButton;
    @FXML private TextField firstNameTextField;
    @FXML private TextField lastNameTextField;
    @FXML private TextField usernameTextField;
    @FXML private PasswordField passwordField;
    @FXML private PasswordField confirmPasswordField;
    @FXML private Button closeButton;

    @FXML public void initialize() {
        setButtonEffect(signUpButton);
        setButtonEffect(closeButton);
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

    @FXML public void clickCloseButton() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

//    @FXML public void handleSignUpButton(ActionEvent event) throws IOException {
//        try {
//            com.github.saacsos.FXRouter.goTo("sign_up");
//        } catch (IOException e) {
//            System.err.println("ไปที่หน้า sign_up ไม่ได้");
//            System.err.println("ให้ตรวจสอบการกำหนด route");
//        }
//    }
}

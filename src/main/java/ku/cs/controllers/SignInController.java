package ku.cs.controllers;

import javafx.animation.FadeTransition;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;
import ku.cs.models.User;
import ku.cs.models.UserList;
import ku.cs.services.DataSource;
import ku.cs.services.UserFileDataSource;

import java.io.IOException;
import java.time.LocalDateTime;

public class SignInController {

    @FXML private Button signInButton;
    @FXML private TextField usernameTextField;
    @FXML private PasswordField passwordField;
    @FXML private PasswordField confirmPasswordField;
    @FXML private Button closeButton;
    @FXML private Button signUpButton;
    @FXML private Label messageLabel;
    private DataSource<UserList> userDataSource = new UserFileDataSource();
    private UserList userList = userDataSource.readData();
    @FXML public void initialize() {
        messageLabel.setManaged(false);
        messageLabel.setText("");
        setButtonEffect(signInButton);
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

    @FXML public void handleSignUpButton(ActionEvent event) throws IOException {
        messageLabel.setManaged(false);
        messageLabel.setText("");
        try {
            com.github.saacsos.FXRouter.goTo("sign_up", null);
        } catch (IOException e) {
            System.err.println("ไปที่หน้า sign_up ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    @FXML public void handleSignInButton(ActionEvent event) throws IOException {
        String username = usernameTextField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();
        if (username.equals("") || password.equals("") || confirmPassword.equals("")) {
            messageLabel.setManaged(true);
            messageLabel.setText("กรุณากรอกข้อมูลให้ครบ");
            messageLabel.setStyle("-fx-background-color: #F394AF");
        }
        else if (!userList.checkUsername(username)){
            messageLabel.setManaged(true);
            messageLabel.setText("ชื่อผู้ใช้ไม่ถูกต้อง กรุณาตรวจสอบข้อมูลอีกครั้ง");
            messageLabel.setStyle("-fx-background-color: #F394AF");
        }
        else {
            User user = userList.searchUsername(username);
            if (!user.isPassword(password) || !password.equals(confirmPassword)) {
                messageLabel.setManaged(true);
                messageLabel.setText("รหัสผ่านไม่ถูกต้อง กรุณาตรวจสอบข้อมูลอีกครั้ง");
                messageLabel.setStyle("-fx-background-color: #F394AF");
            }
            else if (user.isBanned()) {
                messageLabel.setManaged(true);
                messageLabel.setText("บัญชีของคุณถูกระงับการใช้งาน โปรดติดต่อผู้ดูแล");
                messageLabel.setStyle("-fx-background-color: #F394AF");
            }
            else {
                user.setSignInTime(LocalDateTime.now());
                userDataSource.writeData(userList);
                try {
                    com.github.saacsos.FXRouter.goTo("marketplace", user);
                } catch (IOException e) {
                    System.err.println("ไปที่หน้า marketplace ไม่ได้");
                    System.err.println("ให้ตรวจสอบการกำหนด route");
                }
            }
        }
    }
}

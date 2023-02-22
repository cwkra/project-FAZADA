package ku.cs.controllers;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import ku.cs.models.User;
import ku.cs.models.UserList;
import ku.cs.services.DataSource;
import ku.cs.services.UserFileDataSource;

import java.io.IOException;

public class SignUpController {
    @FXML private Button signInButton;
    @FXML private Button signUpButton;
    @FXML private Label messageLabel;
    @FXML private TextField firstNameTextField;
    @FXML private TextField lastNameTextField;
    @FXML private TextField usernameTextField;
    @FXML private PasswordField passwordField;
    @FXML private PasswordField confirmPasswordField;
    @FXML private Button closeButton;
    private DataSource<UserList> userDataSource = new UserFileDataSource();
    private UserList userList = userDataSource.readData();

    @FXML public void initialize() {
        messageLabel.setManaged(false);
        messageLabel.setText("");
        setButtonEffect(signUpButton);
        setButtonEffect(signInButton);
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

    @FXML public void handleSignInButton(ActionEvent event) throws IOException {
        messageLabel.setManaged(false);
        messageLabel.setText("");
        try {
            com.github.saacsos.FXRouter.goTo("sign_in", null);
        } catch (IOException e) {
            System.err.println("ไปที่หน้า sign_in ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    @FXML public void handleSignUpButton(ActionEvent event) throws IOException {
        String firstName = firstNameTextField.getText();
        String lastName = lastNameTextField.getText();
        String username = usernameTextField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        if (firstName.equals("") || lastName.equals("") || username.equals("") || password.equals("") || confirmPassword.equals("")) {
            messageLabel.setManaged(true);
            messageLabel.setText("กรุณากรอกข้อมูลให้ครบ");
        }
        else {
            if (userList.checkUsername(username)) {
                messageLabel.setManaged(true);
                messageLabel.setText("ชื่อผู้ใช้นี้ถูกใช้แล้ว");
            }
            else if (!password.equals(confirmPassword)) {
                messageLabel.setManaged(true);
                messageLabel.setText("รหัสผ่านไม่ตรงกัน");
            }
            else {
                userList.addUser(new User(username, password, firstName, lastName));
                userDataSource.writeData(userList);
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ku/cs/popup/sign_up_successful.fxml"));
                    Parent root = fxmlLoader.load();
                    Stage stage = new Stage();
                    Scene scene = new Scene(root);
                    scene.setFill(Color.TRANSPARENT);
                    stage.setScene(scene);
                    stage.initStyle(StageStyle.TRANSPARENT);
                    SignUpSuccessController controller = fxmlLoader.getController();
                    controller.initialize();
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

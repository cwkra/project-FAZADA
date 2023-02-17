package ku.cs.controllers.user;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.util.Duration;
import ku.cs.models.User;
import ku.cs.models.UserList;
import ku.cs.services.DataSource;
import ku.cs.services.UserFileDataSource;

import java.io.IOException;

public class ChangePasswordController {
    @FXML private Label messageLabel;
    @FXML private PasswordField passwordTextField;
    @FXML private PasswordField newPasswordTextField;
    @FXML private PasswordField confirmPasswordTextField;
    @FXML private Button backButton;
    @FXML private Button saveButton;
    private DataSource<UserList> userDataSource = new UserFileDataSource();
    private UserList userList = userDataSource.readData();
    private User user;

    @FXML public void initialize() {
        user = (User) com.github.saacsos.FXRouter.getData();
        messageLabel.setManaged(false);
        messageLabel.setText("");
        setButtonEffect(backButton);
        setButtonEffect(saveButton);
    }

    public void setButtonEffect(Button button) {
        button.setCursor(Cursor.HAND);
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

    @FXML public void saveChangePassword(ActionEvent event) throws IOException {
        String currentPassword = passwordTextField.getText();
        String newPassword = newPasswordTextField.getText();
        String confirmPassword = confirmPasswordTextField.getText();
        if (currentPassword.equals("") || newPassword.equals("") || confirmPassword.equals("")) {
            messageLabel.setManaged(true);
            messageLabel.setText("กรุณากรอกข้อมูลให้ครบ");
        }
        else {
            if (!user.isPassword(currentPassword)) {
                messageLabel.setManaged(true);
                messageLabel.setText("รหัสผ่านไม่ถูกต้อง กรุณากรอกรหัสผ่านปัจจุบันให้ถูกต้อง");
            }
            else if (currentPassword.equals(newPassword)) {
                messageLabel.setManaged(true);
                messageLabel.setText("ไม่สามารถใช้รหัสผ่านเดิมได้ กรุณาเปลี่ยนรหัสผ่านใหม่");
            }
            else if (!newPassword.equals(confirmPassword)) {
                messageLabel.setManaged(true);
                messageLabel.setText("รหัสผ่านไม่ตรงกัน กรุณากรอกรหัสผ่านใหม่อีกครั้ง");
            }
            else {
                user.setPassword(newPassword);
                userList.changePassword(user);
                userDataSource.writeData(userList);
                messageLabel.setManaged(true);
                messageLabel.setText("เปลี่ยนรหัสผ่านใหม่สำเร็จ");
                messageLabel.setStyle("-fx-background-color: #91C9C7");
            }
        }
        passwordTextField.setText("");
        newPasswordTextField.setText("");
        confirmPasswordTextField.setText("");
    }

    @FXML public void handleBackButton(ActionEvent event) throws IOException {
        try {
            com.github.saacsos.FXRouter.goTo("marketplace", user);
        } catch (IOException e) {
            System.err.println("ไปที่หน้า marketplace ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    @FXML public void goToMyProfile(ActionEvent event) throws IOException {
        try {
            com.github.saacsos.FXRouter.goTo("my_profile", user);
        } catch (IOException e) {
            System.err.println("ไปที่หน้า my_profile ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    @FXML public void goToMyAddress(ActionEvent event) throws IOException {
        try {
            com.github.saacsos.FXRouter.goTo("my_address", user);
        } catch (IOException e) {
            System.err.println("ไปที่หน้า my_address ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    @FXML public void goToMyPurchase(ActionEvent event) throws IOException {
        try {
            com.github.saacsos.FXRouter.goTo("my_purchase", user);
        } catch (IOException e) {
            System.err.println("ไปที่หน้า my_purchase ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }
}

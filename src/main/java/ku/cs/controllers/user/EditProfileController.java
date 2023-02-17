package ku.cs.controllers.user;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import ku.cs.models.User;
import ku.cs.models.UserList;
import ku.cs.services.DataSource;
import ku.cs.services.UserFileDataSource;

import java.io.IOException;

public class EditProfileController {
    @FXML private Label usernameLabel;
    @FXML private Label firstNameLabel;
    @FXML private Label lastNameLabel;
    @FXML private TextField emailTextField;
    @FXML private TextField telNumberTextField;
    @FXML private Label shopNameLabel;
    @FXML private Button backButton;
    @FXML private Button saveButton;
    @FXML private Button cancelButton;
    @FXML private Label messageLabel;
    @FXML private Circle imageCircle;
    @FXML private Button editImageButton;
    private DataSource<UserList> userDataSource = new UserFileDataSource();
    private UserList userList = userDataSource.readData();
    private User user;

    @FXML public void initialize() {
        messageLabel.setManaged(false);
        messageLabel.setText("");
        user = (User) com.github.saacsos.FXRouter.getData();
        setButtonEffect(backButton);
        setButtonEffect(saveButton);
        setButtonEffect(cancelButton);
        setButtonEffect(editImageButton);
        showInfo(user);
    }

    public void showInfo(User user) {
        usernameLabel.setText(user.getUsername());
        firstNameLabel.setText(user.getFirstName());
        lastNameLabel.setText(user.getLastName());
        emailTextField.setText(user.getEmail());
        telNumberTextField.setText(user.getTelephoneNumber());
        shopNameLabel.setText(user.getShopName());
//        System.out.println("User ImagePath: "+user.getImagePath() );
//        Image image = new Image("file:" + user.getImagePath(), true);
//        System.out.println("IMAGE TO STRING: " + image.toString());
//        System.out.println("IMAGE: " + image);
//        imageCircle.setFill(new ImagePattern(image));
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

    @FXML public void saveEditProfile(ActionEvent event) throws IOException {
        String email = emailTextField.getText();
        String telNumber = telNumberTextField.getText();
        user.setEmail(email);
        user.setTelephoneNumber(telNumber);
        userList.editProfile(user);
        userDataSource.writeData(userList);
        messageLabel.setManaged(true);
        messageLabel.setText("แก้ไขข้อมูลสำเร็จ");
    }

    @FXML public void cancelEditProfile(ActionEvent event) throws IOException {
        try {
            com.github.saacsos.FXRouter.goTo("my_profile", user);
        } catch (IOException e) {
            System.err.println("ไปที่หน้า my_profile ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    @FXML public void handleBackButton(ActionEvent event) throws IOException {
        try {
            com.github.saacsos.FXRouter.goTo("my_profile", user);
        } catch (IOException e) {
            System.err.println("ไปที่หน้า my_profile ไม่ได้");
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

    @FXML public void goToChangePassword(ActionEvent event) throws IOException {
        try {
            com.github.saacsos.FXRouter.goTo("change_password", user);
        } catch (IOException e) {
            System.err.println("ไปที่หน้า change_password ไม่ได้");
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

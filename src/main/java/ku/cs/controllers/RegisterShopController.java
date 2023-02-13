package ku.cs.controllers;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.Duration;
import ku.cs.models.User;
import ku.cs.models.UserList;
import ku.cs.services.DataSource;
import ku.cs.services.UserFileDataSource;

import java.io.IOException;

public class RegisterShopController {
    @FXML private Label messageLabel;
    @FXML private TextField shopNameTextField;
    @FXML private Button backButton;
    @FXML private Button registerShopButton;
    private DataSource<UserList> userDataSource = new UserFileDataSource();
    private UserList userList = userDataSource.readData();
    private User user;

    @FXML public void initialize() {
        user = (User) com.github.saacsos.FXRouter.getData();
        messageLabel.setManaged(false);
        messageLabel.setText("");
        setButtonEffect(backButton);
        setButtonEffect(registerShopButton);
    }

    @FXML public void createShop(ActionEvent event) throws IOException {
        String shopName = shopNameTextField.getText();
        if (shopName.equals("")) {
            messageLabel.setManaged(true);
            messageLabel.setText("กรุณากรอกชื่อร้านค้า");
        }
        else {
            if (userList.checkShopName(shopName)) {
                messageLabel.setManaged(true);
                messageLabel.setText("ไม่สามารถกรอกชื่อร้านค้าซ้ำได้ กรุณาเปลี่ยนชื่อร้านค้า");
            }
            else {
                user.setShopName(shopName);
                userList.setShopName(user);
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

    @FXML public void handleBackButton(ActionEvent event) throws IOException {
        try {
            com.github.saacsos.FXRouter.goTo("marketplace", user);
        } catch (IOException e) {
            System.err.println("ไปที่หน้า marketplace ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }
}

package ku.cs.controllers.shop;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.util.Duration;
import ku.cs.models.User;
import ku.cs.models.UserList;
import ku.cs.services.DataSource;
import ku.cs.services.UserFileDataSource;

import java.io.IOException;

public class ProductDetailsAlertController {
    @FXML private Button addAddressButton;
    @FXML private Button marketplaceButton;
    private DataSource<UserList> userDataSource = new UserFileDataSource();
    private UserList userList = userDataSource.readData();
    private User user;

    public void initialize(User user) {
        this.user = user;
        setButtonEffect(addAddressButton);
        setButtonEffect(marketplaceButton);
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

    @FXML public void goToAddAddress(ActionEvent event) {
        Stage stage = (Stage) addAddressButton.getScene().getWindow();
        stage.close();
        try {
            com.github.saacsos.FXRouter.goTo("add_address", user);
        } catch (IOException e) {
            System.err.println("ไปที่หน้า add_address ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    @FXML public void goToMarketplace(ActionEvent event) {
        Stage stage = (Stage) marketplaceButton.getScene().getWindow();
        stage.close();
        try {
            com.github.saacsos.FXRouter.goTo("marketplace", user);
        } catch (IOException e) {
            System.err.println("ไปที่หน้า marketplace ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }
}

package ku.cs.controllers;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.util.Duration;

import java.io.IOException;

public class ManageDeliveryController {
    @FXML
    private Button backButton;

    @FXML public void initialize() {
        setButtonEffect(backButton);
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
            com.github.saacsos.FXRouter.goTo("marketplace");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า marketplace ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    @FXML public void goToMyShop(ActionEvent event) throws IOException {
        try {
            com.github.saacsos.FXRouter.goTo("my_shop");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า my_shop ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    @FXML public void goToOrderList(ActionEvent event) throws IOException {
        try {
            com.github.saacsos.FXRouter.goTo("order_list");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า order_list ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

}

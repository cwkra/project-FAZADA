package ku.cs.controllers;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class MarketplaceController {

    @FXML private Button menuButton;
    @FXML private Button menuCloseButton;
    @FXML private Button helpButton;
    @FXML private VBox menuVbox;

    @FXML public void initialize() {
        setMenuVbox(menuVbox);
        menuVbox.setVisible(false);
        menuButton.setVisible(true);
        menuCloseButton.setVisible(false);
    }

    public void setMenuVbox(VBox vBox) {
        TranslateTransition transition = new TranslateTransition();
        transition.setNode(vBox);
        transition.setToX(200);
        transition.play();
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

    @FXML public void clickOpenMenuButton(ActionEvent event) {
        menuVbox.setVisible(true);
        TranslateTransition slideOut = new TranslateTransition(Duration.millis(300));
        slideOut.setNode(menuVbox);
        slideOut.setToX(0);
        slideOut.play();
        menuButton.setVisible(false);
        menuCloseButton.setVisible(true);
    }

    @FXML public void clickCloseMenuButton(ActionEvent event) {
        TranslateTransition slideIn = new TranslateTransition(Duration.millis(300));
        slideIn.setNode(menuVbox);
        slideIn.setToX(200);
        slideIn.play();
        menuButton.setVisible(true);
        menuCloseButton.setVisible(false);
    }

    @FXML public void handleLogoutButton(ActionEvent event) throws IOException {
        try {
            com.github.saacsos.FXRouter.goTo("sign_in");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า sign_in ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    @FXML public void goToMyProfile(ActionEvent event) throws IOException {
        try {
            com.github.saacsos.FXRouter.goTo("my_profile");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า my_profile ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    @FXML public void goToMyPurchase(ActionEvent event) throws IOException {
        try {
            com.github.saacsos.FXRouter.goTo("my_purchase");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า my_purchase ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    @FXML public void goToRegisterShop(ActionEvent event) throws IOException {
        try {
            com.github.saacsos.FXRouter.goTo("shop_setup");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า shop_setup ไม่ได้");
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

    @FXML public void goToAdminSystems(ActionEvent event) throws IOException {
        try {
            com.github.saacsos.FXRouter.goTo("user_list");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า user_list ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    @FXML public void handleHelpButton(ActionEvent event) throws IOException {
        try {
            com.github.saacsos.FXRouter.goTo("help");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า help ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }
}

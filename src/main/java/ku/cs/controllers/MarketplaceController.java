package ku.cs.controllers;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import ku.cs.models.User;
import ku.cs.models.UserList;
import ku.cs.services.DataSource;
import ku.cs.services.UserFileDataSource;

import java.io.IOException;

public class MarketplaceController {

    @FXML private Button menuButton;
    @FXML private Button menuCloseButton;
    @FXML private Button helpButton;
    @FXML private Button myProfileButton;
    @FXML private Button myPurchaseButton;
    @FXML private Button registerShopButton;
    @FXML private Button myShopButton;
    @FXML private Button adminSystemsButton;
    @FXML private Button logoutButton;
    @FXML private Button exitButton;
    @FXML private VBox menuVbox;
    @FXML private Button searchButton;
    private DataSource<UserList> userDataSource = new UserFileDataSource();
    private UserList userList = userDataSource.readData();
    private User user;

    @FXML public void initialize() {
        user = (User) com.github.saacsos.FXRouter.getData();
        setButtonEffect(searchButton);
        setMenuVbox(menuVbox);
        menuVbox.setVisible(false);
        menuButton.setVisible(true);
        menuCloseButton.setVisible(false);
        if (user.isUser() && user.getShopName().equals("")) {
            registerShopButton.setManaged(true);
            myShopButton.setManaged(false);
            adminSystemsButton.setManaged(false);
        }
        else if (user.isUser() && !user.getShopName().equals("")) {
            registerShopButton.setManaged(false);
            myShopButton.setManaged(true);
            adminSystemsButton.setManaged(false);
        }
        else {
            registerShopButton.setManaged(false);
            myShopButton.setManaged(false);
            adminSystemsButton.setManaged(true);
        }
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
            com.github.saacsos.FXRouter.goTo("sign_in", null);
        } catch (IOException e) {
            System.err.println("ไปที่หน้า sign_in ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    @FXML public void handleExitButton() {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

    @FXML public void goToMyProfile(ActionEvent event) throws IOException {
        try {
            com.github.saacsos.FXRouter.goTo("my_profile", user);
        } catch (IOException e) {
            System.err.println("ไปที่หน้า my_profile ไม่ได้");
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

    @FXML public void goToRegisterShop(ActionEvent event) throws IOException {
        try {
            com.github.saacsos.FXRouter.goTo("shop_setup", user);
        } catch (IOException e) {
            System.err.println("ไปที่หน้า shop_setup ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    @FXML public void goToMyShop(ActionEvent event) throws IOException {
        try {
            com.github.saacsos.FXRouter.goTo("my_shop", user);
        } catch (IOException e) {
            System.err.println("ไปที่หน้า my_shop ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    @FXML public void goToAdminSystems(ActionEvent event) throws IOException {
        try {
            com.github.saacsos.FXRouter.goTo("user_list", user);
        } catch (IOException e) {
            System.err.println("ไปที่หน้า user_list ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    @FXML public void handleHelpButton(ActionEvent event) throws IOException {
        try {
            com.github.saacsos.FXRouter.goTo("help", user);
        } catch (IOException e) {
            System.err.println("ไปที่หน้า help ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }
}

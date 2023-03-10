package ku.cs.controllers;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import ku.cs.controllers.shop.ProductController;
import ku.cs.models.Product;
import ku.cs.models.ProductList;
import ku.cs.models.User;
import ku.cs.models.UserList;
import ku.cs.services.DataSource;
import ku.cs.services.ProductFileDataSource;
import ku.cs.services.UserFileDataSource;

import java.io.IOException;

public class MarketplaceController {
    @FXML private ScrollPane scroll;
    @FXML private GridPane grid;
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
    private DataSource<ProductList> productListDataSource = new ProductFileDataSource();
    private ProductList productList = productListDataSource.readData();
    private Product product;

    @FXML public void initialize() {
        user = (User) com.github.saacsos.FXRouter.getData();
        setButtonEffect(searchButton);
        setMenuVbox(menuVbox);
        menuVbox.setVisible(false);
        menuButton.setVisible(true);
        menuCloseButton.setVisible(false);
        if (user.isUser()) {
            registerShopButton.setManaged(true);
            myShopButton.setManaged(false);
            adminSystemsButton.setManaged(false);
        }
        else if (user.isSeller()) {
            registerShopButton.setManaged(false);
            myShopButton.setManaged(true);
            adminSystemsButton.setManaged(false);
        }
        else {
            registerShopButton.setManaged(false);
            myShopButton.setManaged(false);
            adminSystemsButton.setManaged(true);
        }
        showProducts();
    }

    private void showProducts() {
        int column = 0;
        int row = 1;
        try {
            for (int i = 0; i < productList.getAllProducts().size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(this.getClass().getResource("/ku/cs/shop/product.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();
                ProductController ProductController = fxmlLoader.getController();
                product = productList.getAllProducts().get(i);
                ProductController.initialize(product);
                if (column == 3) {
                    column = 0;
                    row++;
                }
                grid.add(anchorPane, column++, row);
                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_COMPUTED_SIZE);

                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid.setMaxHeight(Region.USE_COMPUTED_SIZE);
                GridPane.setMargin(anchorPane, new Insets(10));
            }
        } catch (IOException e) {
            e.printStackTrace();
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
            System.err.println("??????????????????????????? sign_in ??????????????????");
            System.err.println("?????????????????????????????????????????????????????? route");
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
            System.err.println("??????????????????????????? my_profile ??????????????????");
            System.err.println("?????????????????????????????????????????????????????? route");
        }
    }

    @FXML public void goToMyPurchase(ActionEvent event) throws IOException {
        try {
            com.github.saacsos.FXRouter.goTo("my_purchase", user);
        } catch (IOException e) {
            System.err.println("??????????????????????????? my_purchase ??????????????????");
            System.err.println("?????????????????????????????????????????????????????? route");
        }
    }

    @FXML public void goToRegisterShop(ActionEvent event) throws IOException {
        try {
            com.github.saacsos.FXRouter.goTo("shop_setup", user);
        } catch (IOException e) {
            System.err.println("??????????????????????????? shop_setup ??????????????????");
            System.err.println("?????????????????????????????????????????????????????? route");
        }
    }

    @FXML public void goToMyShop(ActionEvent event) throws IOException {
        try {
            com.github.saacsos.FXRouter.goTo("my_shop", user);
        } catch (IOException e) {
            System.err.println("??????????????????????????? my_shop ??????????????????");
            System.err.println("?????????????????????????????????????????????????????? route");
        }
    }

    @FXML public void goToAdminSystems(ActionEvent event) throws IOException {
        try {
            com.github.saacsos.FXRouter.goTo("user_list", user);
        } catch (IOException e) {
            System.err.println("??????????????????????????? user_list ??????????????????");
            System.err.println("?????????????????????????????????????????????????????? route");
        }
    }

    @FXML public void handleHelpButton(ActionEvent event) throws IOException {
        try {
            com.github.saacsos.FXRouter.goTo("help", user);
        } catch (IOException e) {
            System.err.println("??????????????????????????? help ??????????????????");
            System.err.println("?????????????????????????????????????????????????????? route");
        }
    }
}

package ku.cs.controllers.shop;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import ku.cs.models.*;
import ku.cs.services.AddressFileDataSource;
import ku.cs.services.DataSource;
import ku.cs.services.ProductFileDataSource;
import ku.cs.services.UserFileDataSource;

import java.io.IOException;

public class ProductDetailsController {
    @FXML private Button buyButton;
    @FXML private Button backButton;
    @FXML private Button increaseQuantityButton;
    @FXML private Button decreaseQuantityButton;
    @FXML private Label shopNameLabel;
    @FXML private Label messageLabel;
    @FXML private Label nameLabel;
    @FXML private Label priceLabel;
    @FXML private Label categoryLabel;
    @FXML private TextArea detailsTextArea;
    @FXML private Label quantityLabel;
    @FXML private Circle imageCircle;
    private DataSource<UserList> userDataSource = new UserFileDataSource();
    private UserList userList = userDataSource.readData();
    private User user;
    private DataSource<ProductList> productListDataSource = new ProductFileDataSource();
    private ProductList productList = productListDataSource.readData();
    private Product product;
    private DataSource<AddressList> addressDataSource = new AddressFileDataSource();
    private AddressList addressList = addressDataSource.readData();
    private Address address;
    private ShoppingCart shoppingCart;
    private int quantity = 0;


    public void initialize() {
        shoppingCart = (ShoppingCart) com.github.saacsos.FXRouter.getData();
        user = shoppingCart.getUser();
        product = shoppingCart.getProduct();
        messageLabel.setManaged(false);
        messageLabel.setText("");
        setButtonEffect(backButton);
        setButtonEffect(buyButton);
        setButtonEffect(increaseQuantityButton);
        setButtonEffect(decreaseQuantityButton);
        showProductDetails();
    }

    public void showProductDetails() {
        shopNameLabel.setText(product.getShopName());
        nameLabel.setText(product.getName());
        priceLabel.setText(String.format("%.2f ฿", product.getPrice()));
        categoryLabel.setText(product.getCategory());
        detailsTextArea.setEditable(false);
        detailsTextArea.setText(product.getDetails());
        quantityLabel.setText(String.format("%d", quantity));
        Image image = new Image("file:" + product.getImagePath(), false);
        imageCircle.setFill(new ImagePattern(image));
        if (quantity>0 && quantity != product.getStock()) {
            decreaseQuantityButton.setDisable(false);
            increaseQuantityButton.setDisable(false);
        }
        else if (quantity == product.getStock()){
            increaseQuantityButton.setDisable(true);
            decreaseQuantityButton.setDisable(false);
        }
        else if (quantity == 0) {
            increaseQuantityButton.setDisable(false);
            decreaseQuantityButton.setDisable(true);
        }
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

    @FXML public void increaseQuantity(ActionEvent event) throws IOException {
        quantity++;
        quantityLabel.setText(String.format("%d", quantity));
        if (quantity>0 && quantity != product.getStock()) {
            decreaseQuantityButton.setDisable(false);
            increaseQuantityButton.setDisable(false);
        }
        else if (quantity == product.getStock()){
            increaseQuantityButton.setDisable(true);
            decreaseQuantityButton.setDisable(false);
        }
        else if (quantity == 0) {
            increaseQuantityButton.setDisable(false);
            decreaseQuantityButton.setDisable(true);
        }
    }

    @FXML public void decreaseQuantity(ActionEvent event) throws IOException {
        quantity--;
        quantityLabel.setText(String.format("%d", quantity));
        if (quantity>0 && quantity != product.getStock()) {
            decreaseQuantityButton.setDisable(false);
            increaseQuantityButton.setDisable(false);
        }
        else if (quantity == product.getStock()){
            increaseQuantityButton.setDisable(true);
            decreaseQuantityButton.setDisable(false);
        }
        else if (quantity == 0) {
            increaseQuantityButton.setDisable(false);
            decreaseQuantityButton.setDisable(true);
        }
    }

    @FXML public void handleBackButton(ActionEvent event) throws IOException {
        try {
            com.github.saacsos.FXRouter.goTo("marketplace", user);
        } catch (IOException e) {
            System.err.println("ไปที่หน้า marketplace ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    @FXML public void buyProduct(ActionEvent event) throws IOException {
        if (quantity == 0) {
            messageLabel.setManaged(true);
            messageLabel.setText("กรุณาเลือกจำนวนสินค้าที่ต้องการซื้อ");
        }
        else if (addressList.getMyAddresses(user.getUsername()).size() == 0) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ku/cs/popup/product_details_alert.fxml"));
                Parent root = fxmlLoader.load();
                Stage stage = new Stage();
                Scene scene = new Scene(root);
                scene.setFill(Color.TRANSPARENT);
                stage.setScene(scene);
                stage.initStyle(StageStyle.TRANSPARENT);
                ProductDetailsAlertController controller = fxmlLoader.getController();
                controller.initialize(user);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            shoppingCart.setQuantity(quantity);
            try {
                com.github.saacsos.FXRouter.goTo("confirm_purchase", shoppingCart);
            } catch (IOException e) {
                System.err.println("ไปที่หน้า confirm_purchase ไม่ได้");
                System.err.println("ให้ตรวจสอบการกำหนด route");
            }
        }
    }
}

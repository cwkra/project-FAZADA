package ku.cs.controllers.user;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import ku.cs.models.*;
import ku.cs.services.DataSource;
import ku.cs.services.OrderFileDataSource;
import ku.cs.services.ProductFileDataSource;
import ku.cs.services.UserFileDataSource;

import java.io.IOException;
import java.time.LocalDateTime;

public class PurchaseDetailsController {
    @FXML private Button backButton;
    @FXML private Button acceptButton;
    @FXML private Circle imageCircle;
    @FXML private Label productNameLabel;
    @FXML private Label shopNameLabel;
    @FXML private Label quantityLabel;
    @FXML private Label cumulativePurchaseLabel;
    @FXML private Label statusLabel;
    @FXML private Label buyDateTimeLabel;
    @FXML private TextArea addressDetailsTextArea;
    private DataSource<UserList> userDataSource = new UserFileDataSource();
    private UserList userList = userDataSource.readData();
    private User user;
    private DataSource<OrderList> orderDataSource = new OrderFileDataSource();
    private OrderList orderList = orderDataSource.readData();
    private Order myOrders;
    private DataSource<ProductList> productDataSource = new ProductFileDataSource();
    private ProductList productList = productDataSource.readData();
    private Product product;

    public void initialize() {
        this.myOrders = (Order) com.github.saacsos.FXRouter.getData();
        this.user = userList.searchUsername(myOrders.getCustomerUsername());
        this.product = productList.searchProductById(myOrders.getProductId());
        setButtonEffect(backButton);
        if (myOrders.isDelivered()) {
            acceptButton.setManaged(true);
        }
        else {
            acceptButton.setManaged(false);
        }
        setButtonEffect(acceptButton);
        showInfo(myOrders);
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

    public void showInfo(Order order) {
        shopNameLabel.setText(myOrders.getShopName());
        statusLabel.setText(myOrders.getStatus());
        productNameLabel.setText(product.getName());
        quantityLabel.setText(String.format("%d ชิ้น", myOrders.getProductQuantity()));
        cumulativePurchaseLabel.setText(String.format("%.2f ฿", myOrders.getCumulativePurchase()));
        LocalDateTime orderDate = LocalDateTime.parse(myOrders.getCreateOrderTime());
        buyDateTimeLabel.setText(myOrders.localDateTimeToDateWithSlash(orderDate));
        addressDetailsTextArea.setText(myOrders.toStringAddressDetails());
        addressDetailsTextArea.setEditable(false);
        Image image = new Image("file:" + product.getImagePath(), false);
        imageCircle.setFill(new ImagePattern(image));
    }

    @FXML
    public void handleBackButton(ActionEvent event) throws IOException {
        try {
            com.github.saacsos.FXRouter.goTo("my_purchase", user);
        } catch (IOException e) {
            System.err.println("ไปที่หน้า my_purchase ไม่ได้");
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

    @FXML public void accept(ActionEvent event) throws IOException {
        myOrders.setStatus("สำเร็จ");
        orderList.editOrder(myOrders);
        orderDataSource.writeData(orderList);
        try {
            com.github.saacsos.FXRouter.goTo("my_purchase", user);
        } catch (IOException e) {
            System.err.println("ไปที่หน้า my_purchase ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }
}

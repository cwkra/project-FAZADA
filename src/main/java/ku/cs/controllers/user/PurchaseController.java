package ku.cs.controllers.user;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import ku.cs.models.*;
import ku.cs.services.DataSource;
import ku.cs.services.OrderFileDataSource;
import ku.cs.services.ProductFileDataSource;
import ku.cs.services.UserFileDataSource;

import java.io.IOException;

public class PurchaseController {
    @FXML private AnchorPane orderPane;
    @FXML private Button acceptButton;
    @FXML private Button detailsButton;
    @FXML private Button shopButton;
    @FXML private Circle imageCircle;
    @FXML private Label shopNameLabel;
    @FXML private Label statusLabel;
    @FXML private Label productNameLabel;
    @FXML private Label quantityLabel;
    @FXML private Label cumulativePurchaseLabel;
    private DataSource<UserList> userDataSource = new UserFileDataSource();
    private UserList userList = userDataSource.readData();
    private User user;
    private DataSource<OrderList> orderDataSource = new OrderFileDataSource();
    private OrderList orderList = orderDataSource.readData();
    private Order myOrders;
    private DataSource<ProductList> productDataSource = new ProductFileDataSource();
    private ProductList productList = productDataSource.readData();
    private Product product;

    public void initialize(Order order) {
        this.myOrders = order;
        this.user = userList.searchUsername(myOrders.getCustomerUsername());
        this.product = productList.searchProductById(myOrders.getProductId());
        setButtonEffect(acceptButton);
        setButtonEffect(detailsButton);
        setButtonEffect(shopButton);
        showInfo(myOrders);
        setEffect();
        if (myOrders.isDelivered()) {
            acceptButton.setManaged(true);
        }
        else {
            acceptButton.setManaged(false);
        }
    }

    public void showInfo(Order order) {
        shopNameLabel.setText(myOrders.getShopName());
        statusLabel.setText(myOrders.getStatus());
        productNameLabel.setText(product.getName());
        quantityLabel.setText(String.format("จำนวนสินค้า %d ชิ้น", myOrders.getProductQuantity()));
        cumulativePurchaseLabel.setText(String.format("รวมการสั่งซื้อทั้งสิ้น %.2f ฿", myOrders.getCumulativePurchase()));
        Image image = new Image("file:" + product.getImagePath(), false);
        imageCircle.setFill(new ImagePattern(image));
    }
    public void setEffect() {
        orderPane.setCursor(Cursor.DEFAULT);
        orderPane.setOnMouseEntered(mouseEvent -> {
            TranslateTransition transition = new TranslateTransition(Duration.millis(300));
            transition.setNode(orderPane);
            transition.setToY(-2);
            orderPane.setScaleX(1.025);
            orderPane.setScaleY(1.025);
            transition.playFromStart();
        });
        orderPane.setOnMouseExited(mouseEvent -> {
            TranslateTransition transition = new TranslateTransition(Duration.millis(300));
            transition.setNode(orderPane);
            transition.setToY(2);
            orderPane.setScaleX(1);
            orderPane.setScaleY(1);
            transition.playFromStart();
        });
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

    @FXML
    public void goToPurchaseDetails(ActionEvent event) throws IOException {
        try {
            com.github.saacsos.FXRouter.goTo("purchase_details", myOrders);
        } catch (IOException e) {
            System.err.println("ไปที่หน้า purchase_details ไม่ได้");
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
        acceptButton.setManaged(false);
    }
}

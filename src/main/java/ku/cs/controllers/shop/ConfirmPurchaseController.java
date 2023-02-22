package ku.cs.controllers.shop;

import javafx.animation.TranslateTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import ku.cs.models.*;
import ku.cs.services.*;

import java.io.IOException;

public class ConfirmPurchaseController {
    @FXML private Button backButton;
    @FXML private Circle circleImage;
    @FXML private Label messageLabel;
    @FXML private Label shopNameLabel;
    @FXML private Label productNameLabel;
    @FXML private Label quantityLabel;
    @FXML private Label cumulativePurchaseLabel;
    @FXML private Label categoryLabel;
    @FXML private TextArea productDetailsTextArea;
    @FXML private ListView<Address> addressListView;
    @FXML private TextField nameTextField;
    @FXML private TextField telNumTextField;
    @FXML private TextField districtTextField;
    @FXML private TextField provinceTextField;
    @FXML private TextField zipCodeTextField;
    @FXML private TextArea addressDetailsTextArea;
    @FXML private Button confirmPurchaseButton;
    private DataSource<UserList> userDataSource = new UserFileDataSource();
    private UserList userList = userDataSource.readData();
    private User user;
    private DataSource<AddressList> addressDataSource = new AddressFileDataSource();
    private AddressList addressList = addressDataSource.readData();
    private Address address;
    private Address selectedAddress;
    private DataSource<ProductList> productListDataSource = new ProductFileDataSource();
    private ProductList productList = productListDataSource.readData();
    private Product product;
    private DataSource<OrderList> orderListDataSource = new OrderFileDataSource();
    private OrderList orderList = orderListDataSource.readData();
    private Order order;
    private ShoppingCart shoppingCart;


    public void initialize() {
        shoppingCart = (ShoppingCart) com.github.saacsos.FXRouter.getData();
        product = productList.searchProductById(shoppingCart.getProduct().getId());
        user = userList.searchUsername(shoppingCart.getUser().getUsername());
        messageLabel.setManaged(false);
        messageLabel.setText("");
        setButtonEffect(backButton);
        setButtonEffect(confirmPurchaseButton);
        showProductInfo();
        showListView();
        clearSelectedAddress();
        handleSelectedListView();
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

    public void showListView() {
        addressListView.getItems().addAll(addressList.getMyAddresses(user.getUsername()));
        addressListView.refresh();
    }

    public void handleSelectedListView() {
        addressListView.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<Address>() {
                    @Override
                    public void changed(ObservableValue<? extends Address> observable,
                                        Address oldValue, Address newValue) {
                        System.out.println(newValue + " is selected");
                        selectedAddress = newValue;
                        showAddress(newValue);
                    }
                });
    }

    public void showProductInfo() {
        shopNameLabel.setText(product.getShopName());
        productNameLabel.setText(product.getName());
        quantityLabel.setText(String.format("%d ชิ้น", shoppingCart.getQuantity()));
        double cumulativePurchase = product.getPrice()*shoppingCart.getQuantity();
        cumulativePurchaseLabel.setText(String.format("%.2f ฿", cumulativePurchase));
        categoryLabel.setText(product.getCategory());
        productDetailsTextArea.setEditable(false);
        productDetailsTextArea.setText(product.getDetails());
        Image image = new Image("file:" + product.getImagePath(), false);
        circleImage.setFill(new ImagePattern(image));
    }

    private void showAddress(Address address) {
        nameTextField.setEditable(false);
        nameTextField.setText(address.getName());
        telNumTextField.setEditable(false);
        telNumTextField.setText(address.getTelephoneNumber());
        districtTextField.setEditable(false);
        districtTextField.setText(address.getDistrict());
        provinceTextField.setEditable(false);
        provinceTextField.setText(address.getProvince());
        zipCodeTextField.setEditable(false);
        zipCodeTextField.setText(address.getZipCode());
        addressDetailsTextArea.setEditable(false);
        addressDetailsTextArea.setText(address.getAddressDetails());
    }

    private void clearSelectedAddress() {
        nameTextField.setEditable(false);
        nameTextField.setText("");
        telNumTextField.setEditable(false);
        telNumTextField.setText("");
        districtTextField.setEditable(false);
        districtTextField.setText("");
        provinceTextField.setEditable(false);
        provinceTextField.setText("");
        zipCodeTextField.setEditable(false);
        zipCodeTextField.setText("");
        addressDetailsTextArea.setEditable(false);
        addressDetailsTextArea.setText("");
    }

    @FXML public void confirmPurchase(ActionEvent event) throws IOException {
        if (selectedAddress == null) {
            messageLabel.setManaged(true);
            messageLabel.setText("กรุณาเลือกที่อยู่ในการจัดส่ง");
        }
        else {
            product.addSold(shoppingCart.getQuantity());
            product.reduceStock(shoppingCart.getQuantity());
            productList.editProduct(product);
            productListDataSource.writeData(productList);
            double cumulativePurchase = product.getPrice()*shoppingCart.getQuantity();
            order = new Order(product.getId(), user.getUsername(), selectedAddress.getId(), product.getShopName(), shoppingCart.getQuantity(), cumulativePurchase);
            orderList.addOrder(order);
            orderListDataSource.writeData(orderList);
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ku/cs/popup/purchase_successful.fxml"));
                Parent root = fxmlLoader.load();
                Stage stage = new Stage();
                Scene scene = new Scene(root);
                scene.setFill(Color.TRANSPARENT);
                stage.setScene(scene);
                stage.initStyle(StageStyle.TRANSPARENT);
                PurchaseSuccessfulController controller = fxmlLoader.getController();
                controller.initialize(user);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML public void handleBackButton(ActionEvent event) throws IOException{
        shoppingCart.setQuantity(0);
        try {
            com.github.saacsos.FXRouter.goTo("product_details", shoppingCart);
        } catch (IOException e) {
            System.err.println("ไปที่หน้า product_details ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }
}

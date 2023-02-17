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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import ku.cs.App;
import ku.cs.models.*;
import ku.cs.services.AddressFileDataSource;
import ku.cs.services.DataSource;
import ku.cs.services.ProductFileDataSource;
import ku.cs.services.UserFileDataSource;

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
    @FXML private Label userNameLabel;
    @FXML private Label userTelLabel;
    @FXML private Label userDistrictLabel;
    @FXML private Label userProvinceLabel;
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
                        showSelectedAddress(newValue);
                    }
                });
    }

    public void showProductInfo() {
        shopNameLabel.setText(product.getShopName());
        productNameLabel.setText(product.getName());
        quantityLabel.setText(String.format("%d ชิ้น", shoppingCart.getQuantity()));
        cumulativePurchaseLabel.setText(String.format("%.2f ฿", product.getPrice()*shoppingCart.getQuantity()));
        categoryLabel.setText(product.getCategory());
        productDetailsTextArea.setEditable(false);
        productDetailsTextArea.setText(product.getDetails());
        Image image = new Image("file:" + product.getImagePath(), false);
        circleImage.setFill(new ImagePattern(image));
    }

    private void showSelectedAddress(Address address) {
        userNameLabel.setText(address.getName());
        userTelLabel.setText(address.getTelephoneNumber());
        userDistrictLabel.setText(address.getDistrict());
        userProvinceLabel.setText(address.getProvince());
        addressDetailsTextArea.setEditable(false);
        addressDetailsTextArea.setText(address.getAddressDetails());
    }

    private void clearSelectedAddress() {
        userNameLabel.setText("");
        userTelLabel.setText("");
        userDistrictLabel.setText("");
        userProvinceLabel.setText("");
        addressDetailsTextArea.setEditable(false);
        addressDetailsTextArea.setText("");
    }

    @FXML public void confirmPurchase(ActionEvent event) throws IOException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ku/cs/order/purchase_successful.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            scene.setFill(Color.TRANSPARENT);
            stage.setScene(scene);
            stage.initStyle(StageStyle.TRANSPARENT);
            PurchaseSuccessfulController controller = fxmlLoader.getController();
            controller.initialize(shoppingCart);
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
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

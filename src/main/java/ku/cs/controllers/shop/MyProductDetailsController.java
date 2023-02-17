package ku.cs.controllers.shop;

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
import ku.cs.models.Product;
import ku.cs.models.ProductList;
import ku.cs.models.User;
import ku.cs.models.UserList;
import ku.cs.services.DataSource;
import ku.cs.services.ProductFileDataSource;
import ku.cs.services.UserFileDataSource;

import java.io.IOException;

public class MyProductDetailsController {
    @FXML private Button backButton;
    @FXML private Label categoryLabel;
    @FXML private TextArea detailsTextArea;
    @FXML private Circle imageCircle;
    @FXML private Label nameLabel;
    @FXML private Label priceLabel;
    @FXML private Label stockLabel;
    @FXML private Button editProductButton;
    @FXML private Button addStockButton;
    private DataSource<UserList> userDataSource = new UserFileDataSource();
    private UserList userList = userDataSource.readData();
    private User user;
    private DataSource<ProductList> productListDataSource = new ProductFileDataSource();
    private ProductList productList = productListDataSource.readData();
    private Product product;

    @FXML public void initialize() {
        product = (Product) com.github.saacsos.FXRouter.getData();
        user = userList.searchShopName(product.getShopName());
        nameLabel.setText(product.getName());
        priceLabel.setText(String.format("%.2f ฿", product.getPrice()));
        categoryLabel.setText(product.getCategory());
        detailsTextArea.setEditable(false);
        detailsTextArea.setText(product.getDetails());
        stockLabel.setText(String.format("%d", product.getStock()));
        Image image = new Image("file:" + product.getImagePath(), false);
        imageCircle.setFill(new ImagePattern(image));
        setButtonEffect(editProductButton);
        setButtonEffect(addStockButton);
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
            com.github.saacsos.FXRouter.goTo("my_shop", user);
        } catch (IOException e) {
            System.err.println("ไปที่หน้า my_shop ไม่ได้");
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

    @FXML public void goToOrderList(ActionEvent event) throws IOException {
        try {
            com.github.saacsos.FXRouter.goTo("order_list", user);
        } catch (IOException e) {
            System.err.println("ไปที่หน้า order_list ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    @FXML public void goToDeliveryList(ActionEvent event) throws IOException {
        try {
            com.github.saacsos.FXRouter.goTo("delivery_list", user);
        } catch (IOException e) {
            System.err.println("ไปที่หน้า delivery_list ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    @FXML public void goToEditProduct(ActionEvent event) throws IOException {

        try {
            com.github.saacsos.FXRouter.goTo("edit_product", product);
        } catch (IOException e) {
            System.err.println("ไปที่หน้า edit_product ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    @FXML public void goToAddStock(ActionEvent event) throws IOException {
        try {
            com.github.saacsos.FXRouter.goTo("add_stock", product);
        } catch (IOException e) {
            System.err.println("ไปที่หน้า add_stock ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }
}

package ku.cs.controllers.shop;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import ku.cs.models.*;
import ku.cs.services.DataSource;
import ku.cs.services.MyListener;
import ku.cs.services.ProductFileDataSource;
import ku.cs.services.UserFileDataSource;

import java.io.IOException;

public class ProductController {

    @FXML private AnchorPane productPane;
    @FXML private Label nameLabel;
    @FXML private Label priceLabel;
    @FXML private Label soldLabel;
    @FXML private ImageView productImageView;
    private DataSource<UserList> userDataSource = new UserFileDataSource();
    private UserList userList = userDataSource.readData();
    private User user;
    private DataSource<ProductList> productListDataSource = new ProductFileDataSource();
    private ProductList productList = productListDataSource.readData();
    private Product product;


    public void initialize(Product product) {
        this.product = product;
        user = (User) com.github.saacsos.FXRouter.getData();
        showProduct(product);
        setEffect();
    }

    public void showProduct(Product product) {
        nameLabel.setText(product.getName());
        priceLabel.setText(String.format("%.2f ", product.getPrice()) + "฿");
        soldLabel.setText("ขายแล้ว " + String.format("%d", product.getSold()) + " ชิ้น");
        productImageView.setImage(new Image("file:"+product.getImagePath()));
    }

    public void setEffect() {
        productPane.setCursor(Cursor.HAND);
        productPane.setOnMouseEntered(mouseEvent -> {
            TranslateTransition transition = new TranslateTransition(Duration.millis(300));
            transition.setNode(productPane);
            transition.setToY(-2);
            productPane.setScaleX(1.025);
            productPane.setScaleY(1.025);
            transition.playFromStart();
        });
        productPane.setOnMouseExited(mouseEvent -> {
            TranslateTransition transition = new TranslateTransition(Duration.millis(300));
            transition.setNode(productPane);
            transition.setToY(2);
            productPane.setScaleX(1);
            productPane.setScaleY(1);
            transition.playFromStart();
        });
    }

    @FXML
    public void goToProductDetails(MouseEvent mouseEvent) {
        System.out.println("USER: " + user.toCSV());
        System.out.println("PRODUCT: " + product.toCSV());
        ShoppingCart shoppingCart = new ShoppingCart(user, product);
        try {
            com.github.saacsos.FXRouter.goTo("product_details", shoppingCart);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

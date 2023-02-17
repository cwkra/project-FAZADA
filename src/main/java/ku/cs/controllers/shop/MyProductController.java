package ku.cs.controllers.shop;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import ku.cs.models.Product;
import ku.cs.models.ProductList;
import ku.cs.services.DataSource;
import ku.cs.services.MyListener;
import ku.cs.services.ProductFileDataSource;

import java.io.IOException;

public class MyProductController {
    @FXML private AnchorPane productPane;
    @FXML private Label nameLabel;
    @FXML private Label priceLabel;
    @FXML private Label stockLabel;
    @FXML private ImageView productImageView;
    @FXML private Button editProductButton;
    @FXML private Button addStockButton;
    private DataSource<ProductList> productListDataSource = new ProductFileDataSource();
    private ProductList productList = productListDataSource.readData();
    private Product myProduct;

    public void initialize(Product product) {
        setButtonEffect(editProductButton);
        setButtonEffect(addStockButton);
        showProduct(product);
        this.myProduct = product;
        setEffect();
    }

    public void showProduct(Product product) {
        nameLabel.setText(product.getName());
        priceLabel.setText(String.format("%.2f ฿", product.getPrice()));
        stockLabel.setText(String.format("มีจำนวน %d ชิ้น", product.getStock()));
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

    public void goToMyProductDetails(MouseEvent mouseEvent) throws IOException {
        try {
            com.github.saacsos.FXRouter.goTo("my_product_details", myProduct);
        } catch (IOException e) {
            System.err.println("ไปที่หน้า my_product_details ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    @FXML public void goToEditProduct(ActionEvent event) throws IOException {
        try {
            com.github.saacsos.FXRouter.goTo("edit_product", myProduct);
        } catch (IOException e) {
            System.err.println("ไปที่หน้า edit_product ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    @FXML public void goToAddStock(ActionEvent event) throws IOException {
        try {
            com.github.saacsos.FXRouter.goTo("add_stock", myProduct);
        } catch (IOException e) {
            System.err.println("ไปที่หน้า add_stock ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }
}

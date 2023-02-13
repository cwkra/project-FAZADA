package ku.cs.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import ku.cs.models.Order;
import ku.cs.services.MyListener;

import java.io.IOException;

public class ProductController {

    @FXML private Label nameLabel;
    @FXML private Label priceLabel;
    @FXML private Label soldLabel;
    @FXML private ImageView productImageView;
    private MyListener myListener;

    public void initialize() {
    }

    @FXML
    public void goToProductDetails(MouseEvent mouseEvent) {
//        Order order = new Order(product, userAccount);
        try {
            com.github.saacsos.FXRouter.goTo("product_details");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

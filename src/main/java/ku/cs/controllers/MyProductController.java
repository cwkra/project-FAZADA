package ku.cs.controllers;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import ku.cs.services.MyListener;

import java.io.IOException;

public class MyProductController {
    @FXML private Label nameLabel;
    @FXML private Label priceLabel;
    @FXML private Label amountLabel;
    @FXML private ImageView productImageView;
    @FXML private Button editProductButton;
    @FXML private Button addStockButton;
    private MyListener myListener;

    public void initialize() {
        setButtonEffect(editProductButton);
        setButtonEffect(addStockButton);
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

    @FXML public void goToEditProduct(ActionEvent event) throws IOException {
        try {
            com.github.saacsos.FXRouter.goTo("edit_product");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า edit_product ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }
}

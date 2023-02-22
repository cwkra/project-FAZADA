package ku.cs.controllers.shop;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
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
import ku.cs.controllers.RegisterShopSuccessfulController;
import ku.cs.models.Product;
import ku.cs.models.ProductList;
import ku.cs.models.User;
import ku.cs.models.UserList;
import ku.cs.services.DataSource;
import ku.cs.services.ProductFileDataSource;
import ku.cs.services.UserFileDataSource;

import java.io.IOException;

public class AddStockController {
    @FXML private Button backButton;
    @FXML private Button cancelButton;
    @FXML private Label categoryLabel;
    @FXML private Button decreaseAddStockButton;
    @FXML private TextArea detailsTextArea;
    @FXML private Circle imageCircle;
    @FXML private Button increaseAddStockButton;
    @FXML private Label messageLabel;
    @FXML private Label nameLabel;
    @FXML private Label priceLabel;
    @FXML private Button saveButton;
    @FXML private Label stockLabel;
    private DataSource<ProductList> productListDataSource = new ProductFileDataSource();
    private ProductList productList = productListDataSource.readData();
    private Product product;
    private DataSource<UserList> userDataSource = new UserFileDataSource();
    private UserList userList = userDataSource.readData();
    private User user;
    private int stock = 0;
    @FXML public void initialize() {
        product = (Product) com.github.saacsos.FXRouter.getData();
        user = userList.searchShopName(product.getShopName());
        messageLabel.setText("");
        messageLabel.setManaged(false);
        showProductData(product);
        setButtonEffect(backButton);
        setButtonEffect(saveButton);
        setButtonEffect(cancelButton);
        setButtonEffect(increaseAddStockButton);
        setButtonEffect(decreaseAddStockButton);
        if (stock>0) {
            decreaseAddStockButton.setDisable(false);
        } else {
            decreaseAddStockButton.setDisable(true);
        }
    }

    public void showProductData(Product product) {
        nameLabel.setText(product.getName());
        priceLabel.setText(String.format("%.2f ", product.getPrice()) + "฿");
        categoryLabel.setText(product.getCategory());
        detailsTextArea.setText(product.getDetails());
        detailsTextArea.setEditable(false);
        stockLabel.setText(String.format("%d", stock));
        decreaseAddStockButton.setDisable(stock == 0);
        Image image = new Image("file:" + product.getImagePath(), false);
        imageCircle.setFill(new ImagePattern(image));
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

    @FXML void saveAddStock(ActionEvent event) {
        if (stock == 0) {
            messageLabel.setManaged(true);
            messageLabel.setText("กรุณาเลือกจำนวนที่ต้องการเพิ่มสินค้า");
        }
        else {
            product.addStock(stock);
            productList.editProduct(product);
            productListDataSource.writeData(productList);
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ku/cs/popup/add_stock_successful.fxml"));
                Parent root = fxmlLoader.load();
                Stage stage = new Stage();
                Scene scene = new Scene(root);
                scene.setFill(Color.TRANSPARENT);
                stage.setScene(scene);
                stage.initStyle(StageStyle.TRANSPARENT);
                AddStockSuccessfulController controller = fxmlLoader.getController();
                controller.initialize(product);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML void cancelAddStock(ActionEvent event) throws IOException{
        try {
            com.github.saacsos.FXRouter.goTo("my_shop", user);
        } catch (IOException e) {
            System.err.println("ไปที่หน้า my_shop ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    @FXML void increaseStock(ActionEvent event) {
        stock++;
        stockLabel.setText(String.format("%d", stock));
        if (stock>0) {
            decreaseAddStockButton.setDisable(false);
        } else {
            decreaseAddStockButton.setDisable(true);
        }
    }
    @FXML void decreaseStock(ActionEvent event) {
        stock--;
        stockLabel.setText(String.format("%d", stock));
        if (stock>0) {
            decreaseAddStockButton.setDisable(false);
        } else {
            decreaseAddStockButton.setDisable(true);
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
}

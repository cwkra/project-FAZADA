package ku.cs.controllers.shop;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import ku.cs.models.Product;
import ku.cs.models.ProductList;
import ku.cs.models.User;
import ku.cs.models.UserList;
import ku.cs.services.DataSource;
import ku.cs.services.ProductFileDataSource;
import ku.cs.services.UserFileDataSource;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;

public class EditProductController {

    @FXML private Button backButton;
    @FXML private Button cancelButton;
    @FXML private Button decreaseStockButton;
    @FXML private Button increaseStockButton;
    @FXML private Button editImageButton;
    @FXML private Button saveButton;
    @FXML private TextArea detailsTextArea;
    @FXML private TextField nameTextField;
    @FXML private TextField priceTextField;
    @FXML private MenuButton categoryMenuButton;
    @FXML private Circle imageCircle;
    @FXML private Label messageLabel;
    @FXML private Label stockLabel;
    private DataSource<ProductList> productListDataSource = new ProductFileDataSource();
    private ProductList productList = productListDataSource.readData();
    private Product product;
    private DataSource<UserList> userDataSource = new UserFileDataSource();
    private UserList userList = userDataSource.readData();
    private User user;
    private int stock;

    @FXML public void initialize() {
        product = (Product) com.github.saacsos.FXRouter.getData();
        user = userList.searchShopName(product.getShopName());
        initializeListener();
        messageLabel.setText("");
        messageLabel.setManaged(false);
        setButtonEffect(backButton);
        setButtonEffect(saveButton);
        setButtonEffect(cancelButton);
        setButtonEffect(editImageButton);
        setButtonEffect(increaseStockButton);
        setButtonEffect(decreaseStockButton);
        showProductData(product);
        user = userList.searchShopName(product.getShopName());
        if (stock>0) {
            decreaseStockButton.setDisable(false);
        } else {
            decreaseStockButton.setDisable(true);
        }
    }

    public void showProductData(Product product) {
        nameTextField.setText(product.getName());
        priceTextField.setText(String.format("%.2f" ,product.getPrice()));
        categoryMenuButton.setText(product.getCategory());
        detailsTextArea.setText(product.getDetails());
        detailsTextArea.setEditable(true);
        stock = product.getStock();
        stockLabel.setText(String.format("%d", stock));
        decreaseStockButton.setDisable(stock == 0);
        Image image = new Image("file:" + product.getImagePath(), false);
        imageCircle.setFill(new ImagePattern(image));
        setMenuItem(categoryMenuButton);
    }

    private void initializeListener() {
        priceTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d{0,13}([\\.]\\d{0,2})?")) {
                priceTextField.setText(oldValue);
            }
        });
    }

    @FXML void increaseStock(ActionEvent event) {
        stock++;
        stockLabel.setText(String.format("%d", stock));
        if (stock>0) {
            decreaseStockButton.setDisable(false);
        } else {
            decreaseStockButton.setDisable(true);
        }
    }

    @FXML void decreaseStock(ActionEvent event) {
        stock--;
        stockLabel.setText(String.format("%d", stock));
        if (stock>0) {
            decreaseStockButton.setDisable(false);
        } else {
            decreaseStockButton.setDisable(true);
        }
    }

    public void setMenuItem(MenuButton menuButton) {
        MenuItem item1 = new MenuItem("เสื้อผ้าแฟชั่น");
        MenuItem item2 = new MenuItem("มือถือและอุปกรณ์");
        MenuItem item3 = new MenuItem("กระเป๋า");
        MenuItem item4 = new MenuItem("อาหารและเครื่องดื่ม");
        MenuItem item5 = new MenuItem("สัตว์เลี้ยง");
        MenuItem item6 = new MenuItem("อื่น ๆ");
        categoryMenuButton.getItems().add(item1);
        categoryMenuButton.getItems().add(item2);
        categoryMenuButton.getItems().add(item3);
        categoryMenuButton.getItems().add(item4);
        categoryMenuButton.getItems().add(item5);
        categoryMenuButton.getItems().add(item6);
        EventHandler<ActionEvent> event1 = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
                menuButton.setText(((MenuItem)e.getSource()).getText());
            }
        };
        // add action events to the menuitems
        item1.setOnAction(event1);
        item2.setOnAction(event1);
        item3.setOnAction(event1);
        item4.setOnAction(event1);
        item5.setOnAction(event1);
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

    @FXML public void saveEditProduct(ActionEvent event) throws IOException {
        String name = nameTextField.getText();
        String category = categoryMenuButton.getText();
        String details = detailsTextArea.getText();
        if (name.equals("") ||
                priceTextField.getText().equals("") ||
                category.equals("") ||
                details.equals("")) {
            messageLabel.setManaged(true);
            messageLabel.setText("กรุณากรอกข้อมูลให้ครบ");
        }
        else {
            product.setName(name);
            double price = Double.parseDouble(priceTextField.getText());
            product.setPrice(price);
            product.setCategory(category);
            product.setDetails(details);
            product.setStock(stock);
            productList.editProduct(product);
            productListDataSource.writeData(productList);
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ku/cs/popup/edit_product_successful.fxml"));
                Parent root = fxmlLoader.load();
                Stage stage = new Stage();
                Scene scene = new Scene(root);
                scene.setFill(Color.TRANSPARENT);
                stage.setScene(scene);
                stage.initStyle(StageStyle.TRANSPARENT);
                EditProductSuccessfulController controller = fxmlLoader.getController();
                controller.initialize(product);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML void editImage(ActionEvent actionEvent) {
        FileChooser chooser = new FileChooser();
        // SET FILE CHOOSER INITIAL DIRECTORY
        chooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        // DEFINE ACCEPTABLE FILE EXTENSION
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("images PNG JPG", "*.png", "*.jpg", "*.jpeg"));
        // GET FILE FROM FILE CHOOSER WITH JAVAFX COMPONENT WINDOW
        Node source = (Node) actionEvent.getSource();
        File file = chooser.showOpenDialog(source.getScene().getWindow());
        if (file != null) {
            try {
                // CREATE FOLDER IF NOT EXIST
                File destDir = new File("images");
                if (!destDir.exists()) destDir.mkdirs();
                // RENAME FILE
                String[] fileSplit = file.getName().split("\\.");
                String filename = LocalDate.now() + "_" + System.currentTimeMillis() + "."
                        + fileSplit[fileSplit.length - 1];
                Path target = FileSystems.getDefault().getPath(
                        destDir.getAbsolutePath() + System.getProperty("file.separator") + filename
                );
                // COPY WITH FLAG REPLACE FILE IF FILE IS EXIST
                Files.copy(file.toPath(), target, StandardCopyOption.REPLACE_EXISTING);
                // SET NEW FILE PATH TO IMAGE
                Image image = new Image(target.toUri().toString());
                imageCircle.setFill(new ImagePattern(image));
                product.setImagePath(destDir + "/" + filename);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML public void cancelEditProduct(ActionEvent event) throws IOException {
        try {
            com.github.saacsos.FXRouter.goTo("my_shop", user);
        } catch (IOException e) {
            System.err.println("ไปที่หน้า my_shop ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
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
}

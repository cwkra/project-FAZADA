package ku.cs.controllers.shop;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.util.Duration;
import ku.cs.models.Product;
import ku.cs.models.User;
import ku.cs.models.UserList;
import ku.cs.services.DataSource;
import ku.cs.services.UserFileDataSource;

import java.io.IOException;

public class EditProductSuccessfulController {

    @FXML
    private Button submitButton;
    private DataSource<UserList> userDataSource = new UserFileDataSource();
    private UserList userList = userDataSource.readData();
    private User user;

    public void initialize(Product product) {
        product = (Product) com.github.saacsos.FXRouter.getData();
        this.user = userList.searchShopName(product.getShopName());
        setButtonEffect(submitButton);
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

    @FXML public void submit(ActionEvent event) {
        Stage stage = (Stage) submitButton.getScene().getWindow();
        stage.close();
        try {
            com.github.saacsos.FXRouter.goTo("my_shop", user);
        } catch (IOException e) {
            System.err.println("ไปที่หน้า my_shop ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }
}

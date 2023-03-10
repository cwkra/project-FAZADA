package ku.cs.controllers.user;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.util.Duration;
import ku.cs.models.Order;
import ku.cs.models.OrderList;
import ku.cs.models.User;
import ku.cs.models.UserList;
import ku.cs.services.DataSource;
import ku.cs.services.OrderFileDataSource;
import ku.cs.services.UserFileDataSource;

import java.io.IOException;

public class MyPurchaseController {
    private DataSource<UserList> userDataSource = new UserFileDataSource();
    private UserList userList = userDataSource.readData();
    private User user;
    private DataSource<OrderList> orderDataSource = new OrderFileDataSource();
    private OrderList orderList = orderDataSource.readData();
    private Order order;

    @FXML private GridPane grid;
    @FXML private ScrollPane scroll;
    @FXML private Button backButton;

    @FXML public void initialize() {
        clear();
        user = (User) com.github.saacsos.FXRouter.getData();
        setButtonEffect(backButton);
        showPurchases();
    }
    private void clear() {
        grid.getChildren().clear();
    }
    private void showPurchases() {
        System.out.println(orderList.getMyOrders(user.getUsername()).size());
        int column = 0;
        int row = 1;
        try {
            for (int i = 0; i < orderList.getMyOrders(user.getUsername()).size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(this.getClass().getResource("/ku/cs/user/purchase.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();
                PurchaseController controller = fxmlLoader.getController();
                order = orderList.getMyOrders(user.getUsername()).get(i);
                System.out.println("ORDER: " + order);
                controller.initialize(order);
                if (column == 1) {
                    column = 0;
                    row++;
                }
                grid.add(anchorPane, column++, row);
                grid.setMinWidth(800);
                grid.setPrefWidth(800);
                grid.setMaxWidth(800);

                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid.setMaxHeight(Region.USE_COMPUTED_SIZE);
                GridPane.setMargin(anchorPane, new Insets(0, 0, 25, 25));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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
            com.github.saacsos.FXRouter.goTo("marketplace", user);
        } catch (IOException e) {
            System.err.println("??????????????????????????? marketplace ??????????????????");
            System.err.println("?????????????????????????????????????????????????????? route");
        }
    }

    @FXML public void goToMyProfile(ActionEvent event) throws IOException {
        try {
            com.github.saacsos.FXRouter.goTo("my_profile", user);
        } catch (IOException e) {
            System.err.println("??????????????????????????? my_profile ??????????????????");
            System.err.println("?????????????????????????????????????????????????????? route");
        }
    }

    @FXML public void goToMyAddress(ActionEvent event) throws IOException {
        try {
            com.github.saacsos.FXRouter.goTo("my_address", user);
        } catch (IOException e) {
            System.err.println("??????????????????????????? my_address ??????????????????");
            System.err.println("?????????????????????????????????????????????????????? route");
        }
    }

    @FXML public void goToChangePassword(ActionEvent event) throws IOException {
        try {
            com.github.saacsos.FXRouter.goTo("change_password", user);
        } catch (IOException e) {
            System.err.println("??????????????????????????? change_password ??????????????????");
            System.err.println("?????????????????????????????????????????????????????? route");
        }
    }
}

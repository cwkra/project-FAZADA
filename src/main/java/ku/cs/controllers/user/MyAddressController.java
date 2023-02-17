package ku.cs.controllers.user;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.util.Duration;
import ku.cs.controllers.user.AddressController;
import ku.cs.models.Address;
import ku.cs.models.AddressList;
import ku.cs.models.User;
import ku.cs.models.UserList;
import ku.cs.services.AddressFileDataSource;
import ku.cs.services.DataSource;
import ku.cs.services.UserFileDataSource;

import java.io.IOException;

public class MyAddressController {
    @FXML private Label headerLabel;
    @FXML private ScrollPane scroll;
    @FXML private GridPane grid;
    @FXML private Button backButton;
    @FXML private Button addAddressButton;
    private DataSource<UserList> userDataSource = new UserFileDataSource();
    private UserList userList = userDataSource.readData();
    private User user;
    private DataSource<AddressList> addressDataSource = new AddressFileDataSource();
    private AddressList addressList = addressDataSource.readData();
    private Address address;

    @FXML public void initialize() {
        clear();
        user = (User) com.github.saacsos.FXRouter.getData();
        scroll.setCursor(Cursor.HAND);
        setButtonEffect(backButton);
        setButtonEffect(addAddressButton);
        if (addressList.getMyAddresses(user.getUsername()).size()>=3) {
            addAddressButton.setManaged(false);
        }
        headerLabel.setText("ที่อยู่ของฉัน "+addressList.getMyAddresses(user.getUsername()).size()+"/3");
        showAddresses();
    }

    private void clear() {
        grid.getChildren().clear();
    }

    private void showAddresses() {
        int column = 0;
        int row = 1;
        try {
            for (int i = 0; i < addressList.getMyAddresses(user.getUsername()).size(); i++) {
//                System.out.println("SIZE: " + addressList.getMyAddresses(user.getUsername()).size());
//                System.out.println("ADDRESS: " + addressList.getAllAddresses().get(i).toCSV());
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(this.getClass().getResource("/ku/cs/user/address.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();
                AddressController addressController = fxmlLoader.getController();
                address = addressList.getMyAddresses(user.getUsername()).get(i);
//                addressController.showAddress(address);
                addressController.initialize(address);
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
            System.err.println("ไปที่หน้า marketplace ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    @FXML public void goToMyProfile(ActionEvent event) throws IOException {
        try {
            com.github.saacsos.FXRouter.goTo("my_profile", user);
        } catch (IOException e) {
            System.err.println("ไปที่หน้า my_profile ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    @FXML public void goToAddAddress(ActionEvent event) throws IOException {
        try {
            com.github.saacsos.FXRouter.goTo("add_address", user);
        } catch (IOException e) {
            System.err.println("ไปที่หน้า add_address ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    @FXML public void goToChangePassword(ActionEvent event) throws IOException {
        try {
            com.github.saacsos.FXRouter.goTo("change_password", user);
        } catch (IOException e) {
            System.err.println("ไปที่หน้า change_password ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    @FXML public void goToMyPurchase(ActionEvent event) throws IOException {
        try {
            com.github.saacsos.FXRouter.goTo("my_purchase", user);
        } catch (IOException e) {
            System.err.println("ไปที่หน้า my_purchase ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }
}

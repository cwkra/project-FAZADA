package ku.cs.controllers.user;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import ku.cs.controllers.shop.AddStockSuccessfulController;
import ku.cs.models.Address;
import ku.cs.models.AddressList;
import ku.cs.models.User;
import ku.cs.services.AddressFileDataSource;
import ku.cs.services.DataSource;
import ku.cs.services.MyListener;

import java.io.IOException;

public class AddressController {
    @FXML private AnchorPane addressPane;
    @FXML private Label nameLabel;
    @FXML private Label telephoneNumberLabel;
    @FXML private Label addressDetailsLabel1;
    @FXML private Label addressDetailsLabel2;
    @FXML private Button editAddressButton;
    @FXML private Button deleteAddressButton;
    private DataSource<AddressList> addressDataSource = new AddressFileDataSource();
    private AddressList addressList = addressDataSource.readData();
    User user = (User) com.github.saacsos.FXRouter.getData();
    private Address myAddress;

    public void initialize(Address address) {
        setButtonEffect(editAddressButton);
        setButtonEffect(deleteAddressButton);
        this.myAddress = address;
        showAddress(address);
        setEffect();
    }

    public void setEffect() {
        addressPane.setCursor(Cursor.DEFAULT);
        addressPane.setOnMouseEntered(mouseEvent -> {
            TranslateTransition transition = new TranslateTransition(Duration.millis(300));
            transition.setNode(addressPane);
            transition.setToY(-2);
            addressPane.setScaleX(1.025);
            addressPane.setScaleY(1.025);
            transition.playFromStart();
        });
        addressPane.setOnMouseExited(mouseEvent -> {
            TranslateTransition transition = new TranslateTransition(Duration.millis(300));
            transition.setNode(addressPane);
            transition.setToY(2);
            addressPane.setScaleX(1);
            addressPane.setScaleY(1);
            transition.playFromStart();
        });
    }

    public void showAddress(Address address) {
        nameLabel.setText(address.getName());
        telephoneNumberLabel.setText("("+address.getTelephoneNumber()+")");
        addressDetailsLabel1.setText(address.getAddressDetails());
        addressDetailsLabel2.setText(address.getDistrict() + ", " + address.getProvince() + ", " + address.getZipCode());
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


    @FXML public void goToEditAddress(ActionEvent event) throws IOException {
        try {
            com.github.saacsos.FXRouter.goTo("edit_address", myAddress);
        } catch (IOException e) {
            System.err.println("ไปที่หน้า edit_address ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    @FXML public void deleteAddress(ActionEvent event) throws IOException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ku/cs/popup/confirm_delete_address.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            scene.setFill(Color.TRANSPARENT);
            stage.setScene(scene);
            stage.initStyle(StageStyle.TRANSPARENT);
            ConfirmDeleteAddressController controller = fxmlLoader.getController();
            controller.initialize(myAddress);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

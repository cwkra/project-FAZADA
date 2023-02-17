package ku.cs.controllers.user;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
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
        addressList.removeAddress(myAddress);
        addressDataSource.writeData(addressList);
        try {
            com.github.saacsos.FXRouter.goTo("my_address", user);
        } catch (IOException e) {
            System.err.println("ไปที่หน้า edit_address ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }
}

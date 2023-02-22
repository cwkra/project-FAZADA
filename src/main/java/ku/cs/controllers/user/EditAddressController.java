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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import ku.cs.controllers.RegisterShopSuccessfulController;
import ku.cs.models.Address;
import ku.cs.models.AddressList;
import ku.cs.models.User;
import ku.cs.models.UserList;
import ku.cs.services.AddressFileDataSource;
import ku.cs.services.DataSource;
import ku.cs.services.UserFileDataSource;

import java.io.IOException;

public class EditAddressController {
    @FXML private Label messageLabel;
    @FXML private TextField nameTextField;
    @FXML private TextField telNumTextField;
    @FXML private TextField districtTextField;
    @FXML private TextField provinceTextField;
    @FXML private TextField zipCodeTextField;
    @FXML private TextArea addressDetailsTextArea;
    @FXML private Button backButton;
    @FXML private Button saveButton;
    @FXML private Button cancelButton;
    private DataSource<UserList> userDataSource = new UserFileDataSource();
    private UserList userList = userDataSource.readData();
    private User user;
    private DataSource<AddressList> addressDataSource = new AddressFileDataSource();
    private AddressList addressList = addressDataSource.readData();
    private Address address;
    @FXML public void initialize() {
        messageLabel.setManaged(false);
        messageLabel.setText("");
        address = (Address) com.github.saacsos.FXRouter.getData();
        showAddress(address);
        setButtonEffect(backButton);
        setButtonEffect(saveButton);
        setButtonEffect(cancelButton);
        this.user = userList.searchUsername(address.getUsername());
    }

    public void showAddress(Address address) {
        nameTextField.setText(address.getName());
        telNumTextField.setText(address.getTelephoneNumber());
        districtTextField.setText(address.getDistrict());
        provinceTextField.setText(address.getProvince());
        zipCodeTextField.setText(address.getZipCode());
        addressDetailsTextArea.setText(address.getAddressDetails());
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

    @FXML
    public void saveEditAddress(ActionEvent event) throws IOException {
        String name = nameTextField.getText();
        String telNumber = telNumTextField.getText();
        String district = districtTextField.getText();
        String province = provinceTextField.getText();
        String zipCode = zipCodeTextField.getText();
        String details = addressDetailsTextArea.getText();
        if (name.equals("") || telNumber.equals("") || district.equals("") || province.equals("") || zipCode.equals("") || details.equals("")) {
            messageLabel.setManaged(true);
            messageLabel.setText("กรุณากรอกข้อมูลให้ครบ");
        } else {
            address.setName(name);
            address.setTelephoneNumber(telNumber);
            address.setDistrict(district);
            address.setProvince(province);
            address.setZipCode(zipCode);
            address.setAddressDetails(details);
            addressList.editAddress(address);
            addressDataSource.writeData(addressList);
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ku/cs/popup/edit_address_successful.fxml"));
                Parent root = fxmlLoader.load();
                Stage stage = new Stage();
                Scene scene = new Scene(root);
                scene.setFill(Color.TRANSPARENT);
                stage.setScene(scene);
                stage.initStyle(StageStyle.TRANSPARENT);
                EditAddressSuccessfulController controller = fxmlLoader.getController();
                controller.initialize(user);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void cancelEditAddress(ActionEvent event) throws IOException {
        try {
            com.github.saacsos.FXRouter.goTo("my_address", user);
        } catch (IOException e) {
            System.err.println("ไปที่หน้า my_address ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    @FXML public void handleBackButton(ActionEvent event) throws IOException {
        try {
            com.github.saacsos.FXRouter.goTo("my_address", user);
        } catch (IOException e) {
            System.err.println("ไปที่หน้า my_address ไม่ได้");
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

    @FXML public void goToMyAddress(ActionEvent event) throws IOException {
        try {
            com.github.saacsos.FXRouter.goTo("my_address", user);
        } catch (IOException e) {
            System.err.println("ไปที่หน้า my_address ไม่ได้");
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

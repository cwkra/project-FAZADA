package ku.cs.controllers.user;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import ku.cs.models.User;
import ku.cs.models.UserList;
import ku.cs.services.DataSource;
import ku.cs.services.UserFileDataSource;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;

public class MyProfileController {
    @FXML private Label usernameLabel;
    @FXML private Label firstNameLabel;
    @FXML private Label lastNameLabel;
    @FXML private Label emailLabel;
    @FXML private Label telephoneNumberLabel;
    @FXML private Label shopNameLabel;
    @FXML private Button backButton;
    @FXML private Button editProfileButton;
    @FXML private Circle imageCircle;
    @FXML private Button editImageButton;
    private DataSource<UserList> userDataSource = new UserFileDataSource();
    private UserList userList = userDataSource.readData();
    private User user;

    @FXML public void initialize() {
        user = (User) com.github.saacsos.FXRouter.getData();
        setButtonEffect(backButton);
        setButtonEffect(editProfileButton);
        setButtonEffect(editImageButton);
        showInfo(user);
    }

    public void showInfo(User user) {
        usernameLabel.setText(user.getUsername());
        firstNameLabel.setText(user.getFirstName());
        lastNameLabel.setText(user.getLastName());
        emailLabel.setText(user.getEmail());
        telephoneNumberLabel.setText(user.getTelephoneNumber());
        shopNameLabel.setText(user.getShopName());
        Image image = new Image("file:" + user.getImagePath(), false);
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

    public void editImage (ActionEvent actionEvent) throws IOException{
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
                user.setImagePath(destDir + "/" + filename);
                userList.setImagePath(user);
                userDataSource.writeData(userList);
            } catch (IOException e) {
                e.printStackTrace();
            }
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

    @FXML public void goToEditProfile(ActionEvent event) throws IOException {
        try {
            com.github.saacsos.FXRouter.goTo("edit_profile", user);
        } catch (IOException e) {
            System.err.println("ไปที่หน้า edit_profile ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }
}

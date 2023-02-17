package ku.cs;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.github.saacsos.FXRouter;
import javafx.stage.StageStyle;

import java.io.IOException;


/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        FXRouter.bind(this, stage, "FAZADA.exe", 1280, 800);
        configRoute();
        stage.initStyle(StageStyle.TRANSPARENT);
        FXRouter.goTo("sign_in");
    }

    private static void configRoute() {
        String packageStr = "ku/cs/";
        FXRouter.when("sign_in", packageStr+"sign_in.fxml");
        FXRouter.when("sign_up", packageStr+"sign_up.fxml");
        FXRouter.when("marketplace", packageStr+"marketplace.fxml");

//        Profile Page
        FXRouter.when("my_profile", packageStr+"user/my_profile.fxml");
        FXRouter.when("edit_profile", packageStr+"user/edit_profile.fxml");

//        Address Page
        FXRouter.when("my_address", packageStr+"user/my_address.fxml");
        FXRouter.when("add_address", packageStr+"user/add_address.fxml");
        FXRouter.when("edit_address", packageStr+"user/edit_address.fxml");

//        Change Password Page
        FXRouter.when("change_password", packageStr+"user/change_password.fxml");

//        Purchase Page
        FXRouter.when("my_purchase", packageStr+"user/my_purchase.fxml");
        FXRouter.when("confirm_purchase", packageStr+"order/confirm_purchase.fxml");

//        Shop Page
        FXRouter.when("shop_setup", packageStr+"shop/shop_setup.fxml");
        FXRouter.when("my_shop", packageStr+"shop/my_shop.fxml");
        FXRouter.when("order_list", packageStr+"shop/order_list.fxml");
        FXRouter.when("delivery_list", packageStr+"shop/delivery_list.fxml");
        FXRouter.when("add_product", packageStr+"shop/add_product.fxml");
        FXRouter.when("edit_product", packageStr+"shop/edit_product.fxml");
        FXRouter.when("add_stock", packageStr+"shop/add_stock.fxml");
        FXRouter.when("my_product_details", packageStr+"shop/my_product_details.fxml");
        FXRouter.when("product_details", packageStr+"shop/product_details.fxml");

//        Admin Page
        FXRouter.when("user_list", packageStr+"admin/user_list.fxml");
        FXRouter.when("report_list", packageStr+"admin/report_list.fxml");

//        Help Page
        FXRouter.when("help", packageStr+"help.fxml");
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}
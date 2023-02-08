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
        FXRouter.when("my_profile", packageStr+"user/my_profile.fxml");
        FXRouter.when("my_purchase", packageStr+"user/my_purchase.fxml");
        FXRouter.when("shop_setup", packageStr+"shop/shop_setup.fxml");
        FXRouter.when("my_shop", packageStr+"user/my_shop.fxml");
        FXRouter.when("user_list", packageStr+"admin/user_list.fxml");
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
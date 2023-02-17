module ku.cs {
    requires javafx.controls;
    requires javafx.fxml;

    opens ku.cs to javafx.fxml;
    exports ku.cs;
    exports ku.cs.controllers;
    opens ku.cs.controllers to javafx.fxml;
    exports ku.cs.controllers.user;
    opens ku.cs.controllers.user to javafx.fxml;
    exports ku.cs.controllers.shop;
    opens ku.cs.controllers.shop to javafx.fxml;
    exports ku.cs.controllers.admin;
    opens ku.cs.controllers.admin to javafx.fxml;
}

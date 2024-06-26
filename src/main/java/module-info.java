module org.example.demo2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.graphics;
    requires mysql.connector.java;
    requires java.desktop;

opens org.example.demo2.helper to javafx.fxml;

exports org.example.demo2.helper to javafx.graphics;

    opens org.example.demo2.UserPanel to javafx.fxml;
    exports org.example.demo2.UserPanel;

    opens org.example.demo2 to javafx.fxml;
    opens org.example.demo2.DB to javafx.base;
    exports org.example.demo2.Registration to javafx.graphics;
    exports org.example.demo2;
    exports org.example.demo2.auth;
    opens org.example.demo2.auth to javafx.fxml;
    exports org.example.demo2.update;
    opens org.example.demo2.update to javafx.fxml;
    opens org.example.demo2.Registration to javafx.fxml;

}

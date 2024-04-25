module com.example.cubex {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires java.desktop;
    requires java.sql;

    opens com.example.cubex to javafx.fxml;
    exports com.example.cubex;
    exports com.example.cubex.Controller;
    opens com.example.cubex.Controller to javafx.fxml;
}
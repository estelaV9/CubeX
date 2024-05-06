module com.example.cubex {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires java.desktop;
    requires java.sql;

    opens com.example.cubex to javafx.fxml;
    exports com.example.cubex;
    exports com.example.cubex.Controller;
    exports com.example.cubex.model;
    opens com.example.cubex.Controller to javafx.fxml;
    opens com.example.cubex.model to javafx.fxml;
    exports com.example.cubex.DAO;
    opens com.example.cubex.DAO to javafx.fxml;
}
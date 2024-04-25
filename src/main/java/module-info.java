module com.example.cubex {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens com.example.cubex to javafx.fxml;
    exports com.example.cubex;
}
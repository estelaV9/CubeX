package com.example.cubex.Controller;

import com.example.cubex.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;

public class StartCtrller {
    @FXML private Button logBtt;
    @FXML private Button signBtt;

    // SI HA INICIADO LA APLICACION COMO DEMO ALGUNAS FUNCIONES NO PODRA HACER
    public static boolean optionRegistrer = false;
    public static boolean isDemo = false; // ATRIBUTO QUE GUARDA SI UN USUARIO ES DEMO

    @FXML
    void onLogAction() {
        optionRegistrer = false;
        isDemo = false;
        try {
            FXMLLoader fxmlLoader = new
                    FXMLLoader(Main.class.getResource("Registration.fxml"));
            Parent root = fxmlLoader.load();
            RegistrationCtrller controller = fxmlLoader.getController();
            Scene scene = new Scene(root);
            Stage stage = (Stage) this.logBtt.getScene().getWindow();
            stage.setTitle("Registration Page");
            stage.setScene(scene);
            if (!stage.isShowing()) {
                stage.show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onSignAction() {
        optionRegistrer = true;
        isDemo = false;
        try {
            FXMLLoader fxmlLoader = new
                    FXMLLoader(Main.class.getResource("Registration.fxml"));
            Parent root = fxmlLoader.load();
            RegistrationCtrller controller = fxmlLoader.getController();
            Scene scene = new Scene(root);
            Stage stage = (Stage) this.signBtt.getScene().getWindow();
            stage.setTitle("Registration Page");
            stage.setScene(scene);
            if (!stage.isShowing()) {
                stage.show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onTryAction() {
        isDemo = true;
        try {
            FXMLLoader fxmlLoader = new
                    FXMLLoader(Main.class.getResource("Page.fxml"));
            Parent root = fxmlLoader.load();
            PageCtrller controller = fxmlLoader.getController();
            Scene scene = new Scene(root);
            Stage stage = (Stage) this.logBtt.getScene().getWindow();
            stage.setTitle("Timer Page");
            stage.setScene(scene);
            if (!stage.isShowing()) {
                stage.show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

package com.example.cubex.Controller;

import com.example.cubex.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class StartCtrller {

    @FXML
    private Button logBtt;

    @FXML
    private Button signBtt;

    @FXML
    private Button tryBtt;
    public static boolean optionRegistrer = false;

    @FXML
    void onLogAction(ActionEvent event) {
        optionRegistrer = false;
        try {
            FXMLLoader fxmlLoader = new
                    FXMLLoader(Main.class.getResource("Registration.fxml"));
            Parent root = fxmlLoader.load();
            RegistrationCtrller controller = fxmlLoader.getController();
            Scene scene = new Scene(root);
            Stage stage = (Stage) this.logBtt.getScene().getWindow();
            stage.setTitle("Registration");
            stage.setScene(scene);
            if (!stage.isShowing()) {
                stage.show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onSignAction(ActionEvent event) {
        optionRegistrer = true;
        try {
            FXMLLoader fxmlLoader = new
                    FXMLLoader(Main.class.getResource("Registration.fxml"));
            Parent root = fxmlLoader.load();
            RegistrationCtrller controller = fxmlLoader.getController();
            Scene scene = new Scene(root);
            Stage stage = (Stage) this.signBtt.getScene().getWindow();
            stage.setTitle("Registration");
            stage.setScene(scene);
            if (!stage.isShowing()) {
                stage.show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onTryAction(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new
                    FXMLLoader(Main.class.getResource("Page.fxml"));
            Parent root = fxmlLoader.load();
            PageCtrller controller = fxmlLoader.getController();
            Scene scene = new Scene(root);
            Stage stage = (Stage) this.logBtt.getScene().getWindow();
            stage.setTitle("Application Page");
            stage.setScene(scene);
            if (!stage.isShowing()) {
                stage.show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

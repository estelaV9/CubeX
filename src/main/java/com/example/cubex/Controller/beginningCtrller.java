package com.example.cubex.Controller;

import com.example.cubex.Main;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class beginningCtrller {
    @FXML
    private Button startBtt;

    @FXML
    void onStartAction(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new
                    FXMLLoader(Main.class.getResource("Start.fxml"));
            Parent root = fxmlLoader.load();
            StartCtrller controller = fxmlLoader.getController();
            Scene scene = new Scene(root);
            Stage stage = (Stage) this.startBtt.getScene().getWindow();
            stage.setTitle("Start Application");
            stage.setScene(scene);
            if (!stage.isShowing()) {
                stage.show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
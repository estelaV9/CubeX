package com.example.cubex.Controller;

import com.example.cubex.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class SettingCtrller extends CodeGeneral{
    @FXML
    private Button generalBtt;

    @FXML
    private Button generalBtt1;

    @FXML
    private Button manualBtt;

    @FXML
    private Button profileBtt;

    @FXML
    private Pane settingGMenu;

    @FXML
    private Button backBtt;
    @FXML
    private Button githubBtt;

    @FXML
    void onBackAction(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new
                    FXMLLoader(Main.class.getResource("Page.fxml"));
            Parent root = fxmlLoader.load();
            PageCtrller controller = fxmlLoader.getController();
            Scene scene = new Scene(root);
            Stage stage = (Stage) this.backBtt.getScene().getWindow();
            stage.setTitle("Application Page");
            stage.setScene(scene);
            if (!stage.isShowing()) {
                stage.show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }// PAGINA PRINCIPAL
    @FXML
    void onGeneralBtt(ActionEvent event) {

    }

    @FXML
    void onManualAction(ActionEvent event) {

    }

    @FXML
    void onProfileAction(ActionEvent event) {

    }
    @FXML
    void onGithubAction() {
        // githubBtt.setOnAction(event -> 'google.com');
    }

}

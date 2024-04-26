package com.example.cubex.Controller;

import com.example.cubex.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SettingCtrller extends CodeGeneral implements Initializable {
    @FXML
    private Pane accountPane;

    @FXML
    private TextField accountTxt;
    @FXML
    private Button generalBtt;
    @FXML
    private Button passwordBtt;

    @FXML
    private Button personalBtt;

    @FXML
    private Button proBtt;

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
    private Button deleteBtt;
    @FXML
    private Pane demoProfilePane;

    @FXML
    private Pane personalPane;
    @FXML
    private ToggleGroup RoleUser;


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
    void onUpdateAction(ActionEvent event) {

    }
    @FXML
    void onGithubAction() {
        // githubBtt.setOnAction(event -> 'google.com');
    }


    /** ACCOUNT **/

    @FXML
    void onDeleteAction(ActionEvent event) {
        deleteBtt.setStyle("-fx-background-color :  #b1c8a3");
        proBtt.setStyle("-fx-background-color :  #325743");
        personalBtt.setStyle("-fx-background-color :  #325743");
        passwordBtt.setStyle("-fx-background-color :  #325743");
    }

    @FXML
    void onPasswordAction(ActionEvent event) {
        deleteBtt.setStyle("-fx-background-color :   #325743");
        proBtt.setStyle("-fx-background-color :  #325743");
        personalBtt.setStyle("-fx-background-color :  #325743");
        passwordBtt.setStyle("-fx-background-color :   #b1c8a3");
    }

    @FXML
    void onPersonalAction(ActionEvent event) {
        deleteBtt.setStyle("-fx-background-color :   #325743");
        proBtt.setStyle("-fx-background-color :  #325743");
        personalBtt.setStyle("-fx-background-color :  #b1c8a3");
        passwordBtt.setStyle("-fx-background-color :   #325743");

    }

    @FXML
    void onProAction(ActionEvent event) {
        deleteBtt.setStyle("-fx-background-color :   #325743");
        proBtt.setStyle("-fx-background-color :  #b1c8a3");
        personalBtt.setStyle("-fx-background-color :  #325743");
        passwordBtt.setStyle("-fx-background-color :   #325743");
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(StartCtrller.isDemo){
            demoProfilePane.setVisible(true);
            accountPane.setVisible(false);
        } else{
            demoProfilePane.setVisible(false);
            accountPane.setVisible(true);
            deleteBtt.setStyle("-fx-background-color :   #325743");
            proBtt.setStyle("-fx-background-color :  #325743");
            personalBtt.setStyle("-fx-background-color :  #b1c8a3");
            passwordBtt.setStyle("-fx-background-color :   #325743");
        }

    }

}

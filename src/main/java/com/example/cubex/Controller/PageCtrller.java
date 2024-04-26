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
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PageCtrller implements Initializable {
    @FXML
    private TextField chronoPane;
    @FXML
    private Button closeBtt;
    @FXML
    private Button closeOpBtt;
    @FXML
    private Button closeSeBtt;
    @FXML
    private Button compeBtt;
    @FXML
    private Button exitBtt;
    @FXML
    private Button openBtt;
    @FXML
    private Button optionBtt;
    @FXML
    private Pane optionMenu;
    @FXML
    private Button profileBtt;
    @FXML
    private TextField scramblePane;
    @FXML
    private Button screenBtt;
    @FXML
    private Button sessionBtt;
    @FXML
    private Pane settingMenu;
    @FXML
    private Button settingsBtt;

    @FXML
    private Button settingsMenuBtt;
    @FXML
    private Button solvesBtt;
    @FXML
    private Button timerBtt;
    @FXML
    private Pane timesMenu;

    @FXML
    void manejarTeclaPresionada(KeyEvent event) {

    }

    @FXML
    void manejarTeclaSoltada(KeyEvent event) {

    }

    @FXML
    void onCloseMenuAction(ActionEvent event) {
        timesMenu.setVisible(false);
    }

    @FXML
    void onCloseOptionAction(ActionEvent event) {
        optionMenu.setVisible(false);
    }

    @FXML
    void onCloseSettingAction(ActionEvent event) {
        settingMenu.setVisible(false);
    }

    @FXML
    void onCompeAction(ActionEvent event) {

    }

    @FXML
    void onExitAction(ActionEvent event) {
        int opcion = JOptionPane.showConfirmDialog(null,
                "¿Está seguro de que desea salir?", "Confirmación", JOptionPane.YES_NO_OPTION);
        if (opcion == JOptionPane.YES_OPTION) {
            // CERRAR APLICACIÓN
            System.exit(0);
        }
    }

    @FXML
    void onOpenMenuAction(ActionEvent event) {
        timesMenu.setVisible(true);
    }

    @FXML
    void onOptionAction(ActionEvent event) {
        optionMenu.setVisible(true);
    }

    @FXML
    void onProfileAction(ActionEvent event) {

    }

    @FXML
    void onScreenAction(ActionEvent event) {

    }

    @FXML
    void onSessionAction(ActionEvent event) {

    }

    @FXML
    void onSettingsAction(ActionEvent event) {
        settingMenu.setVisible(true);
    }

    @FXML
    void onSettingsMenuAction(ActionEvent event) {

    }

    @FXML
    void onSolvesAction(ActionEvent event) {

    }

    @FXML
    void onTimerAction(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new
                    FXMLLoader(Main.class.getResource("Page.fxml"));
            Parent root = fxmlLoader.load();
            PageCtrller controller = fxmlLoader.getController();
            Scene scene = new Scene(root);
            Stage stage = (Stage) this.timerBtt.getScene().getWindow();
            stage.setTitle("Application Page");
            stage.setScene(scene);
            if (!stage.isShowing()) {
                stage.show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } // IR A LA PAGINA PRINCIPAL
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        settingMenu.setVisible(false);
        optionMenu.setVisible(false);
        timesMenu.setVisible(false);
        closeBtt.setVisible(false);
    }
}

package com.example.cubex.Controller;

import com.example.cubex.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;

public class CodeGeneral {
                            /********* GENERAL ATRIBUTTES ***********/
    @FXML public Button closeBtt;
    @FXML public Button closeOpBtt;
    @FXML public Button closeSeBtt;
    @FXML public Button compeBtt;
    @FXML public Button exitBtt;
    @FXML public Button openBtt;
    @FXML public Button optionBtt;
    @FXML public Pane optionMenu;
    @FXML public Button profileBtt;
    @FXML public TextField scramblePane;
    @FXML public Button screenBtt;
    @FXML public Button sessionBtt;
    @FXML public Pane settingMenu;
    @FXML public Button settingsBtt;
    @FXML public Button settingsMenuBtt;
    @FXML public Button solvesBtt;
    @FXML public Button timerBtt;
    @FXML public Pane timesMenu;
    @FXML public TextField sessionName;

                            /********* GENERAL METHODS ***********/
    @FXML void manejarTeclaPresionada(KeyEvent event) {

    }

    @FXML void manejarTeclaSoltada(KeyEvent event) {

    }

    @FXML void onExitAction(ActionEvent event) {
        int opcion = JOptionPane.showConfirmDialog(null,
                "¿Está seguro de que desea salir?", "Confirmación", JOptionPane.YES_NO_OPTION);
        if (opcion == JOptionPane.YES_OPTION) {
            System.exit(0); // CERRAR APLICACIÓN
        }
    } // SALIR DE LA APLICACIÓN

    @FXML void onProfileAction(ActionEvent event) {

    }
                                /********* TIMES MENU ***********/
    @FXML void onOpenMenuAction(ActionEvent event) {
        timesMenu.setVisible(true);
        closeBtt.setVisible(true);
    }// SE ABRE EL PANEL DE TIEMPOS Y EL BOTON DE CERRAR

    @FXML void onCloseMenuAction(ActionEvent event) {

        timesMenu.setVisible(false);
        closeBtt.setVisible(false);
    }// SE CIERRA EL PANEL DE TIEMPOS Y EL BOTON DE CERRAR


                                /********* OPTIONS MENU ***********/
    @FXML void onOptionAction(ActionEvent event) {
        optionMenu.setVisible(true);
    } // ABRIR EL MENU DE OPCIONES

    @FXML void onCloseOptionAction(ActionEvent event) {
        optionMenu.setVisible(false);
    } // CERRAR EL MENU DE OPCIONES

    @FXML void onSettingsMenuAction(ActionEvent event) {

    }

    @FXML void onScreenAction(ActionEvent event) {

    }

                                /********* SETTINGS MENU ***********/
    @FXML void onSettingsAction(ActionEvent event) {
        settingMenu.setVisible(true);
    }// ABRIR EL MENU DE AJUSTES

    @FXML void onCloseSettingAction(ActionEvent event) {
        settingMenu.setVisible(false);
    } // CERRAR EL MENU DE AJUSTES

    @FXML void onCompeAction(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new
                    FXMLLoader(Main.class.getResource("Competition.fxml"));
            Parent root = fxmlLoader.load();
            CompetitionCtrller controller = fxmlLoader.getController();
            Scene scene = new Scene(root);
            Stage stage = (Stage) this.timerBtt.getScene().getWindow();
            stage.setTitle("Competition Page");
            stage.setScene(scene);
            if (!stage.isShowing()) {
                stage.show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }// IR A LA PAGINA COMPETITION

    @FXML void onSessionAction(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new
                    FXMLLoader(Main.class.getResource("Session.fxml"));
            Parent root = fxmlLoader.load();
            SessionCtrller controller = fxmlLoader.getController();
            Scene scene = new Scene(root);
            Stage stage = (Stage) this.timerBtt.getScene().getWindow();
            stage.setTitle("Session Page");
            stage.setScene(scene);
            if (!stage.isShowing()) {
                stage.show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }// IR A LA PAGINA DE SESIONES

    @FXML void onSolvesAction(ActionEvent event) {

    } // IR A LA PAGINA DE RESOLUCIONES

    @FXML void onTimerAction(ActionEvent event) {
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
        }
    }// IR A LA PAGINA PRINCIPAL
}

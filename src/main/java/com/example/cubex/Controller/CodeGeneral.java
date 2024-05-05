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

public class CodeGeneral implements Initializable {
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
    @FXML public Button champBtt;
    @FXML public Pane optionDemoPane;
    @FXML public Button signOutBtt;
    @FXML
    private Pane demoProfilePane;
    @FXML
    private Button editProfileBtt;


    @FXML
    private Pane profilePage;
    // ATRIBUTOS SEMAFOROS PARA ABRIR Y CERRAR DESDE EL MISMO BOTON
    boolean pulsarOption = false;
    boolean pulsarSettings = false;
    boolean pulsarProfile = false;

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
        if(!pulsarProfile){
            if(StartCtrller.isDemo){
                demoProfilePane.setVisible(true);
                pulsarProfile = true;
            } else {
                profilePage.setVisible(true);
                pulsarProfile = true;
            }
        } else {
            if(StartCtrller.isDemo){
                demoProfilePane.setVisible(false);
                pulsarProfile = false;
            } else {
                profilePage.setVisible(false);
                pulsarProfile = false;
            }
        }
    }

    @FXML
    void onEditProfileAction(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new
                    FXMLLoader(Main.class.getResource("Setting.fxml"));
            Parent root = fxmlLoader.load();
            SettingCtrller controller = fxmlLoader.getController();
            Scene scene = new Scene(root);
            Stage stage = (Stage) this.profileBtt.getScene().getWindow();
            stage.setTitle("Settings");
            stage.setScene(scene);
            if (!stage.isShowing()) {
                stage.show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        if(!pulsarOption){ // SI NO SE HA PULSADO, SE ABRE EL MENU
            if(StartCtrller.isDemo){
                optionMenu.setVisible(false);
                optionDemoPane.setVisible(true);
                pulsarOption = true;
            } else {
                optionMenu.setVisible(true);
                optionDemoPane.setVisible(false);
                pulsarOption = true;
            }
        } else { // SI SE HA PULSADO, SE CIERRA EL MENU
            onCloseOptionAction();
            pulsarOption = false;
        }
    } // MENU DE OPCIONES

    @FXML void onCloseOptionAction() {
        if(StartCtrller.isDemo){
            optionDemoPane.setVisible(false);
        } else {
            optionMenu.setVisible(false);
        }
    } // CERRAR EL MENU DE OPCIONES

    @FXML void onSettingsMenuAction(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new
                    FXMLLoader(Main.class.getResource("Setting.fxml"));
            Parent root = fxmlLoader.load();
            SettingCtrller controller = fxmlLoader.getController();
            Scene scene = new Scene(root);
            Stage stage = (Stage) this.settingMenu.getScene().getWindow();
            stage.setTitle("Settings");
            stage.setScene(scene);
            if (!stage.isShowing()) {
                stage.show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML void onSignOutAction(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new
                    FXMLLoader(Main.class.getResource("Start.fxml"));
            Parent root = fxmlLoader.load();
            StartCtrller controller = fxmlLoader.getController();
            Scene scene = new Scene(root);
            Stage stage = (Stage) this.signOutBtt.getScene().getWindow();
            stage.setTitle("Start Application");
            stage.setScene(scene);
            if (!stage.isShowing()) {
                stage.show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML void onScreenAction(ActionEvent event) {

    }

                                /********* SETTINGS MENU ***********/
    @FXML void onSettingsAction(ActionEvent event) {
        if(!pulsarSettings){ // SI NO SE HA PULSADO, SE ABRE EL MENU
            settingMenu.setVisible(true);
            pulsarSettings = true;
        } else { // SI SE HA PULSADO, SE CIERRA EL MENU
            onCloseSettingAction();
            pulsarSettings = false;
        }

    }// ABRIR EL MENU DE AJUSTES

    @FXML void onCloseSettingAction() {
        settingMenu.setVisible(false);
    } // CERRAR EL MENU DE AJUSTES

    @FXML void onCompeAction(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new
                    FXMLLoader(Main.class.getResource("Competition.fxml"));
            Parent root = fxmlLoader.load();
            CompetitionCtrller controller = fxmlLoader.getController();
            Scene scene = new Scene(root);
            Stage stage = (Stage) this.compeBtt.getScene().getWindow();
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
            Stage stage = (Stage) this.sessionBtt.getScene().getWindow();
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
    }// PAGINA PRINCIPAL

    @FXML void onChampAction(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}

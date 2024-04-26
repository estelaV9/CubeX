package com.example.cubex.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.Pane;

import javax.swing.*;
import java.net.URL;
import java.util.ResourceBundle;

public class SessionCtrller extends CodeGeneral implements Initializable {
    @FXML private Button addSessionBtt;
    @FXML private Button cancelSessionBtt;
    @FXML private Button createSessionBtt;
    @FXML private Pane nameSession;
    @FXML private ChoiceBox<?> methodCube;
    @FXML private ChoiceBox<?> typeCube;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        settingMenu.setVisible(false);
        optionMenu.setVisible(false);
        nameSession.setVisible(false);
        sessionName.setPromptText("Name of Session");
        sessionName.setStyle("-fx-prompt-text-fill: #9B9B9B; -fx-background-color: #b1c8a3;");
    }
    @FXML void onAddSessionAction(ActionEvent event) {
        nameSession.setVisible(true);
    }
    @FXML void onCreateSessionAction(ActionEvent event) {
        nameSession.setVisible(false);
    }
    @FXML void onCancelSessionCreate(ActionEvent event) {
        int opcion = JOptionPane.showConfirmDialog(null,
                "¿Estás seguro de que quieres cancelar la creación de la sesión?\n" +
                        "Todos los datos ingresados hasta ahora se perderán.", "Confirmación", JOptionPane.YES_NO_OPTION);
        if (opcion == JOptionPane.YES_OPTION) {
            nameSession.setVisible(false);
        }
    }
}

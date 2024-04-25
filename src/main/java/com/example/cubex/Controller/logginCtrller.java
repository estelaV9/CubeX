package com.example.cubex.Controller;

import com.example.cubex.Database.DatabaseConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import javax.swing.*;

public class logginCtrller {

    @FXML
    private Button cancelBtt;

    @FXML
    private Button closeBtt;

    @FXML
    private TextField emailTxt;

    @FXML
    private Button logBtt;

    @FXML
    private Label loginMessage;

    @FXML
    private PasswordField passwordTxt;

    @FXML
    private Button signBtt;

    @FXML
    void onCancelAction(ActionEvent event) {
        emailTxt.clear();
        passwordTxt.clear();
    }

    @FXML
    void onCloseAction(ActionEvent event) {
        int opcion = JOptionPane.showConfirmDialog(null,
                "¿Está seguro de que desea salir?", "Confirmación", JOptionPane.YES_NO_OPTION);
        if (opcion == JOptionPane.YES_OPTION) {
            // CERRAR APLICACIÓN
            System.exit(0);
        }
    }

    @FXML
    void onEnterAction(ActionEvent event) {
        System.out.println(DatabaseConnection.conectar());
    }

    @FXML
    void onSignAction(ActionEvent event) {

    }

}

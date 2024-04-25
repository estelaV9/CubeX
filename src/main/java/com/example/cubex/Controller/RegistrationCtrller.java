package com.example.cubex.Controller;

import com.example.cubex.Database.DatabaseConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;

import javax.swing.*;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class RegistrationCtrller implements Initializable {
    @FXML
    private Button cancelBtt;

    @FXML
    private TextField emailTxt;

    @FXML
    private Button logBtt;

    @FXML
    private Pane logginVision;

    @FXML
    private Label loginMessage;

    @FXML
    private PasswordField passwordTxt;

    @FXML
    private Button signBtt;

    @FXML
    private Pane signVision;

    @FXML
    private PasswordField txtConfirmPsswd;

    @FXML
    private TextField txtEmailUser;

    @FXML
    private TextField txtNameUser;

    @FXML
    private PasswordField txtPasswdUser;

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
    void onEnterAction(ActionEvent event) throws SQLException {
        if (emailTxt.getText().equals("") || passwordTxt.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Campos vacíos");
            alert.setHeaderText(null);
            alert.setContentText("Los campos a rellenar están vacíos");
            alert.showAndWait();
        } else {
            Connection connection = DatabaseConnection.conectar();
            Statement statement = connection.createStatement();
            String sqlInsert = "INSERT INTO cube_users VALUES ();";
            int rowsUpdated = statement.executeUpdate(sqlInsert); // NOS DEVUELVE EL NUMERO DE FILAS QUE HAN SIDO MODIFICADAS
        }
    }

    @FXML
    void onSignAction(ActionEvent event) {
        signVision.setVisible(true);
        logginVision.setVisible(false);
        // PONGO LOS PROMPT TEXT MANUALMENTE
        txtNameUser.setPromptText("Name of user");
        txtEmailUser.setPromptText("example@example.com");
        txtPasswdUser.setPromptText("Password");
        txtConfirmPsswd.setPromptText("Confirm Password");
        txtNameUser.setStyle("-fx-prompt-text-fill: #9B9B9B; -fx-background-color: #b1c8a3;");
        txtEmailUser.setStyle("-fx-prompt-text-fill: #9B9B9B; -fx-background-color: #b1c8a3;");
        txtPasswdUser.setStyle("-fx-prompt-text-fill: #9B9B9B; -fx-background-color: #b1c8a3;");
        txtConfirmPsswd.setStyle("-fx-prompt-text-fill: #9B9B9B; -fx-background-color: #b1c8a3;");

    }

    @FXML
    void onLogAction(ActionEvent event) {
        signVision.setVisible(false);
        logginVision.setVisible(true);
        emailTxt.setPromptText("example@example.com");
        passwordTxt.setPromptText("Password");
        emailTxt.setStyle("-fx-prompt-text-fill: #9B9B9B; -fx-background-color: #b1c8a3;");
        passwordTxt.setStyle("-fx-prompt-text-fill: #9B9B9B; -fx-background-color: #b1c8a3;");
    }

    @FXML
    void onSignUpAction(ActionEvent event) throws SQLException {
        LocalDate currentDate = LocalDate.now();
        if (txtNameUser.getText().equals("") || txtEmailUser.getText().equals("") || txtPasswdUser.getText().equals("")
                || txtConfirmPsswd.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Campos vacíos.");
            alert.setHeaderText("¡ERROR!");
            alert.setContentText("Por favor, completa todos los campos antes de continuar.");
            alert.showAndWait();
        } else if (!txtPasswdUser.getText().equals(txtConfirmPsswd.getText())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Contraseñas no coinciden.");
            alert.setHeaderText("¡ERROR!");
            alert.setContentText("Las contraseñas no coinciden. Por favor, verifica e intenta nuevamente.");
            alert.showAndWait();
            //comprobacion del mail
            //comprobacion de que un usuario no se llame igual que otro
        } else {
            Connection connection = DatabaseConnection.conectar();
            String sqlInsert = "INSERT INTO cube_users (NAME_USER, PASSWORD_USER, MAIL, REGISTRATION_DATE) " +
                    "VALUES (?, ?, ?, ?);";
            PreparedStatement statement = connection.prepareStatement(sqlInsert);
            statement.setString(1, txtNameUser.getText());
            statement.setString(2, txtPasswdUser.getText());
            statement.setString(3, txtEmailUser.getText());
            statement.setString(4, currentDate.toString());

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                // SI SE INSERTO EL USUARIO, MOSTRAR UN MENSAJE DE EXITO
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Creación de usuario");
                alert.setHeaderText("Creación exitosa");
                alert.setContentText("Se ha creado el usuario correctamente.");
                alert.showAndWait();
            } else {
                // SI NO SE INSERTO EL USUARIO, MOSTRAR UN MENSAJE DE ERROR
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error al crear usuario");
                alert.setHeaderText("No se pudo crear el usuario");
                alert.setContentText("No se pudo crear el usuario. Por favor, inténtelo de nuevo.");
                alert.showAndWait();
            }
            connection.close();
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        signVision.setVisible(false);
        logginVision.setVisible(true);
        emailTxt.setPromptText("example@example.com");
        passwordTxt.setPromptText("Password");
        emailTxt.setStyle("-fx-prompt-text-fill: #9B9B9B; -fx-background-color: #b1c8a3;");
        passwordTxt.setStyle("-fx-prompt-text-fill: #9B9B9B; -fx-background-color: #b1c8a3;");
    }
}

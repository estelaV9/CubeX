package com.example.cubex.Controller;

import com.example.cubex.Database.DatabaseConnection;
import com.example.cubex.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class RegistrationCtrller implements Initializable {

    @FXML
    private Label labelLog;

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
            alert.setTitle("Campos vacíos.");
            alert.setHeaderText("¡ERROR!");
            alert.setContentText("Por favor, completa todos los campos antes de continuar.");
            alert.showAndWait();
        } else {
            Connection connection = DatabaseConnection.conectar();

            // COMPROBAR SI EL USUARIO INTRODUCIDO YA EXISTE
            String sqlQuery = "SELECT * FROM CUBE_USERS WHERE MAIL = ? AND PASSWORD_USER = ?;";
            PreparedStatement statementQuery = connection.prepareStatement(sqlQuery);
            statementQuery.setString(1, emailTxt.getText());
            statementQuery.setString(2, passwordTxt.getText());
            ResultSet resultSet = statementQuery.executeQuery();
            try {
                if (!resultSet.next()) {
                    // SI EL USUARIO NO EXISTE, MOSTRAR UN MENSAJE DE ERROR
                    String message = "Please, verify your email and password and try again.";
                    labelLog.setText(message);
                } else{
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
                    } // IR A LA PAGINA PRINCIPAL DESPUES DE HABER INICIADO SESION
                }
            } catch (SQLException e) {
                System.out.println("Error " + e);
            }
            connection.close();
        }
    }

    @FXML
    void onSignAction() {
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
    void onLogAction() {
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
            //comprobar contraseña

        }
        /*if(!txtEmailUser.getText().equals()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Correo electrónico inválido");
            alert.setHeaderText("¡ERROR!");
            alert.setContentText("Por favor, introduzca un correo electrónico válido " +
                    "en el formato 'example@example.com'.");
            alert.showAndWait();
        }*/
        else {
            Connection connection = DatabaseConnection.conectar();
            try { // EXCEPCION PARA CONTROLAR QUE NO HAYA DOS NOMBRES DE USUARIOS IGUALES
                String sqlInsert = "INSERT INTO cube_users (NAME_USER, PASSWORD_USER, MAIL, REGISTRATION_DATE) " +
                        "VALUES (?, ?, ?, ?);";
                PreparedStatement statement = connection.prepareStatement(sqlInsert);
                statement.setString(1, txtNameUser.getText());
                statement.setString(2, txtPasswdUser.getText());
                statement.setString(3, txtEmailUser.getText());
                statement.setString(4, currentDate.toString());

                int rowsInserted = statement.executeUpdate();

                // COMPROBAR SI EL NOMBRE INTRODUCIDO YA EXISTE
                String sqlQuery = "SELECT NAME_USER FROM CUBE_USERS WHERE NAME_USER = ?;";
                PreparedStatement statementQuery = connection.prepareStatement(sqlQuery);
                statementQuery.setString(1, txtNameUser.getText());
                if (rowsInserted > 0) {
                    // SI SE INSERTO EL USUARIO, MOSTRAR UN MENSAJE DE EXITO
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Creación de usuario");
                    alert.setHeaderText("Creación exitosa");
                    alert.setContentText("Se ha creado el usuario correctamente.");
                    alert.showAndWait();

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
                    } // IR A LA PAGINA PRINCIPAL DESPUES DE HABER CREADO EL USUARIO
                }
            } catch (SQLException e) {
                // SI EL NOMBRE YA EXISTE, MOSTRAR UN MENSAJE DE ERROR
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error al crear usuario");
                alert.setHeaderText("Nombre ya existente");
                alert.setContentText("Ese nombre ya existe. Por favor, elija otro.");
                alert.showAndWait();
            }
            connection.close();
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(!StartCtrller.optionRegistrer){
            onLogAction();
        } else {
            onSignAction();
        }
    }
}

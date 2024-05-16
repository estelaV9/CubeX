package com.example.cubex.Controller;

import com.example.cubex.Validator.Validator;
import com.example.cubex.model.CacheStatic;
import com.example.cubex.model.CubeUser;
import com.example.cubex.DAO.CubeUserDAO;
import com.example.cubex.Main;
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
import java.time.LocalDate;
import java.util.ResourceBundle;

public class RegistrationCtrller implements Initializable {
    @FXML private Button backBtt;
    @FXML private TextField emailTxt;
    @FXML private Button logBtt;
    @FXML private Pane logginVision;
    @FXML private Label loginMessage;
    @FXML private PasswordField passwordTxt;
    @FXML private Button signBtt;
    @FXML private Pane signVision;
    @FXML private PasswordField txtConfirmPsswd;
    @FXML private TextField txtEmailUser;
    @FXML private TextField txtNameUser;
    @FXML private PasswordField txtPasswdUser;

    @FXML
    void onEnterAction() {
        if (emailTxt.getText().isEmpty() || passwordTxt.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Campos vacíos.");
            alert.setHeaderText("¡ERROR!");
            alert.setContentText("Por favor, completa todos los campos antes de continuar.");
            alert.showAndWait();
        } else if (!Validator.isValidMail(emailTxt.getText())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Correo no válido.");
            alert.setHeaderText("¡ERROR!");
            alert.setContentText("Por favor, ingrese un correo válido.\nFor example : example@example.com");
            alert.showAndWait();
        } else if (Validator.isValidPassword(passwordTxt.getText())) {
            // SE CREA EL USUARIO STATICO DE ESA SESSION
            CacheStatic.cubeUser = new CubeUser(passwordTxt.getText(), emailTxt.getText());
            if(CubeUserDAO.logearUsuario(emailTxt.getText(), passwordTxt.getText())){
                loginMessage.setText("Invalid login");
            } else {
                openMainPage();
            } // SI NO COINCIDE EL LOGIN SE MOSTRAR UN MENSAJE DE ERROR, SI NO SE VA A LA PAGINA PRINCIPAL
        }
    } // INICIAR SESION

    @FXML
    void onSignUpAction() {
        LocalDate currentDate = LocalDate.now();
        if (txtNameUser.getText().isEmpty() || txtEmailUser.getText().isEmpty() || txtPasswdUser.getText().isEmpty()
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
        } else if (!Validator.isValidMail(txtEmailUser.getText())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Correo no válido.");
            alert.setHeaderText("¡ERROR!");
            alert.setContentText("Por favor, ingrese un correo válido.\nFor example : example@example.com");
            alert.showAndWait();
        } else if (Validator.isValidPassword(txtPasswdUser.getText())) {
            CacheStatic.cubeUser = new CubeUser(txtNameUser.getText(), txtPasswdUser.getText(), txtEmailUser.getText(), currentDate);
            if(CubeUserDAO.insertarUsuarios(CacheStatic.cubeUser.getNameUser(), CacheStatic.cubeUser.getPasswordUser(),
                    CacheStatic.cubeUser.getMail(), CacheStatic.cubeUser.getRegistrationDate())){
                // SI SE INSERTO EL USUARIO, MOSTRAR UN MENSAJE DE EXITO
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Creación de usuario");
                alert.setHeaderText("Creación exitosa");
                alert.setContentText("Se ha creado el usuario correctamente.");
                alert.showAndWait();
                // IR A LA PAGINA PRINCIPAL DESPUES DE HABER CREADO EL USUARIO
                openMainPage();
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Creación de usuario");
                alert.setHeaderText("Creación fallida");
                alert.setContentText("No se ha creado el usuario correctamente.");
                alert.showAndWait();
            }
        }
    } // CREAR UNA CUENTA

    @FXML
    void onSignAction() {
        signVision.setVisible(true);
        logginVision.setVisible(false);
    } // VISUALIZAR EL SIGN UP

    @FXML
    void onLogAction() {
        signVision.setVisible(false);
        logginVision.setVisible(true);
    } // VISUALIZAR EL LOGIN

    @FXML
    void onCancelAction() {
        emailTxt.clear();
        passwordTxt.clear();
    } // LIMPIAR LOS CAMPOS DEL LOGIN

    @FXML
    void onCloseAction() {
        int opcion = JOptionPane.showConfirmDialog(null,
                "¿Está seguro de que desea salir?", "Confirmación", JOptionPane.YES_NO_OPTION);
        if (opcion == JOptionPane.YES_OPTION) {
            System.exit(0); // CERRAR APLICACIÓN
        }
    } // SALIR DE LA APLICACION

    @FXML
    void onBackAction() {
        try {
            FXMLLoader fxmlLoader = new
                    FXMLLoader(Main.class.getResource("Start.fxml"));
            Parent root = fxmlLoader.load();
            StartCtrller controller = fxmlLoader.getController();
            Scene scene = new Scene(root);
            Stage stage = (Stage) this.backBtt.getScene().getWindow();
            stage.setTitle("Start Application");
            stage.setScene(scene);
            if (!stage.isShowing()) {
                stage.show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    } // VOLVER A LA PAGINA START


    public void openMainPage() {
        try {
            FXMLLoader fxmlLoader = new
                    FXMLLoader(Main.class.getResource("Page.fxml"));
            Parent root = fxmlLoader.load();
            PageCtrller controller = fxmlLoader.getController();
            Scene scene = new Scene(root);
            Stage stage = (Stage) this.signBtt.getScene().getWindow();
            stage.setTitle("Timer Page");
            stage.setScene(scene);
            if (!stage.isShowing()) {
                stage.show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    } // PAGINA PRINCIPAL

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (!StartCtrller.optionRegistrer) {
            onLogAction();
            emailTxt.setText("eljoaki@gmail.com");
            passwordTxt.setText("Ps.contains(8)");

            // SE PONE MANUALMENTE LOS PROMPTTEXT PORQUE LUEGO NO SE PUEDE DARLE UN ESTILO
            emailTxt.setPromptText("example@example.com");
            passwordTxt.setPromptText("Ps.contains(8)");
            //SE LE DA UN ESTILO
            emailTxt.setStyle("-fx-prompt-text-fill: #1e3728; -fx-background-color: #b1c8a3;");
            passwordTxt.setStyle("-fx-prompt-text-fill: #1e3728; -fx-background-color: #b1c8a3;");
        } else {
            onSignAction();
            // SE PONE MANUALMENTE LOS PROMPTTEXT PORQUE LUEGO NO SE PUEDE DARLE UN ESTILO
            txtNameUser.setPromptText("NAME OF USER");
            txtEmailUser.setPromptText("example@example.com");
            txtPasswdUser.setPromptText("Ps.contains(8)");
            txtConfirmPsswd.setPromptText("CONFIRM PASSWORD");

            // SE LE DA UN ESTILO
            txtNameUser.setStyle("-fx-prompt-text-fill: #9B9B9B; -fx-background-color: #b1c8a3;");
            txtEmailUser.setStyle("-fx-prompt-text-fill: #9B9B9B; -fx-background-color: #b1c8a3;");
            txtPasswdUser.setStyle("-fx-prompt-text-fill: #9B9B9B; -fx-background-color: #b1c8a3;");
            txtConfirmPsswd.setStyle("-fx-prompt-text-fill: #9B9B9B; -fx-background-color: #b1c8a3;");
        } // SEGUN LA OPCION QUE HAYA ELEGIDO EN EL START SE MOSTRARA UN PANEL Y OTRO
    } // SE INICIALIZA CON LA VISTA QUE HAYA ELEGIDO Y CON ESTILOS EN LOS TEXTFIELD
}

package com.example.cubex.Controller;

import com.example.cubex.model.CacheStatic;
import com.example.cubex.model.CubeUser;
import com.example.cubex.DAO.CubeUserDAO;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class RegistrationCtrller implements Initializable {
    @FXML private Button cancelBtt;
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
    public static String nameUser;


    @FXML
    void onEnterAction(ActionEvent event) {
        if (emailTxt.getText().isEmpty() || passwordTxt.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Campos vacíos.");
            alert.setHeaderText("¡ERROR!");
            alert.setContentText("Por favor, completa todos los campos antes de continuar.");
            alert.showAndWait();
        } else if (!isValidMail(emailTxt.getText())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Correo no válido.");
            alert.setHeaderText("¡ERROR!");
            alert.setContentText("Por favor, ingrese un correo válido.\nFor example : example@example.com");
            alert.showAndWait();
        } else if (isValidPassword(passwordTxt.getText())) {
            CacheStatic.cubeUser = new CubeUser(passwordTxt.getText(), emailTxt.getText());
            CubeUserDAO.logearUsuario(emailTxt.getText(), passwordTxt.getText());
            if(CubeUserDAO.invalidLogin){
                loginMessage.setText("Invalid login");
            } else {
                openMainPage();
            }
        }
    } // INICIAR SESION

    @FXML
    void onSignUpAction(ActionEvent event) throws SQLException {
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
        } else if (!isValidMail(txtEmailUser.getText())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Correo no válido.");
            alert.setHeaderText("¡ERROR!");
            alert.setContentText("Por favor, ingrese un correo válido.\nFor example : example@example.com");
            alert.showAndWait();
        } else if (isValidPassword(txtPasswdUser.getText())) {
            CacheStatic.cubeUser = new CubeUser(txtNameUser.getText(), txtPasswdUser.getText(), txtEmailUser.getText(), currentDate);
            CubeUserDAO.insertarUsuarios(CacheStatic.cubeUser.getNameUser(), CacheStatic.cubeUser.getPasswordUser(),
                    CacheStatic.cubeUser.getMail(), CacheStatic.cubeUser.getRegistrationDate());
            if(CubeUserDAO.successfulCreation){
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

    private boolean isValidMail(String mail) {
        Pattern pattern =
                Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)" + // INDICAMOS QUE PUEDA TENER CARACTERES MAY,MIN. NUM, ETC
                        "*@" // LUEGO DEBE CONTENER UN @
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]{2,})$"); //DEBE TENER UN PUNTO Y LUEGO TENER MIN 2 CARACTERES DESPUES DE ESE PUNTO
        // LLAMAMOS A LA CLASE PATTERN Y USAMOS EL METODO MATCHER. Y PASAMOS COMO PARAMETRO EL CORREO
        Matcher matcher = pattern.matcher(mail);
        // SI ENCUENTRA COINCIDENCIA CON EL CORREO Y LA EXPRESION REGULAR
        return matcher.find();
    } // VALIDAR CON EXPRESIONES REGULARES EL MAIL (texto) @ (texto) . (texto)

    public boolean isValidPassword(String password) {
        if (password.length() >= 8) { // TIENE QUE TENER MINIMO 8 CARACTERES
            boolean mayuscula = false;
            boolean numero = false;
            boolean especial = false;

            // TIENE QUE CONTENER CARACTERES ESPECIALES
            Pattern special = Pattern.compile("[?!¡@¿.,´)]");
            Matcher hasSpecial = special.matcher(password);

            // RECORRER LA CONTRASEÑA ARA VALIDAR QUE TIENE TODOS LOS REQUISITOS
            for (int i = 0; i < password.length(); i++) {
                char l = password.charAt(i);
                // SE USA LA CLASE Character PARA OBTENER INFORMACION SOBRE LOS CARACTERES
                if (Character.isDigit(l)) {// CONTIENE MINIMO UN NUMERO.
                    numero = true;
                }
                if (Character.isUpperCase(l)) { // CONTIENE MINIMO UNA MAYUSCULA
                    mayuscula = true;
                }
                if (hasSpecial.find()) { // CONTIENE CARACTERES ESPECIALES
                    especial = true;
                }
            }
            return numero && mayuscula && especial;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Contraseña no válida.");
            alert.setHeaderText("¡ERROR!");
            alert.setContentText("Por favor, ingrese una contraseña válida.\nFor example : Ps.contains(8).");
            alert.showAndWait();
            return false;
        }
    } // VALIDAR LA CONTRASEÑA

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
    } // APARIENCIA DEL SIGN UP

    @FXML
    void onLogAction() {
        signVision.setVisible(false);
        logginVision.setVisible(true);
        emailTxt.setPromptText("example@example.com");
        passwordTxt.setPromptText("Password");
        emailTxt.setStyle("-fx-prompt-text-fill: #9B9B9B; -fx-background-color: #b1c8a3;");
        passwordTxt.setStyle("-fx-prompt-text-fill: #9B9B9B; -fx-background-color: #b1c8a3;");
    } // APARIENCIA DEL LOGIN

    @FXML
    void onCancelAction(ActionEvent event) {
        emailTxt.clear();
        passwordTxt.clear();
    } // LIMPIAR LOS CAMPOS DEL LOGIN

    @FXML
    void onCloseAction(ActionEvent event) {
        int opcion = JOptionPane.showConfirmDialog(null,
                "¿Está seguro de que desea salir?", "Confirmación", JOptionPane.YES_NO_OPTION);
        if (opcion == JOptionPane.YES_OPTION) {
            System.exit(0); // CERRAR APLICACIÓN
        }
    } // SALIR DE LA APLICACION

    @FXML
    void onBackAction(ActionEvent event) {
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
    } // IR A LA PAGINA START


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
    } // IR A LA PAGINA PRINCIPAL

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // SEGUN LA OPCION QUE HAYA ELEGIDO EN EL START SE MOSTRARA UN PANEL Y OTRO
        if (!StartCtrller.optionRegistrer) {
            onLogAction();
        } else {
            onSignAction();
        }
    }
}

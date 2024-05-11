package com.example.cubex.Controller;

import com.example.cubex.DAO.CubeUserDAO;
import com.example.cubex.DAO.MemberDAO;
import com.example.cubex.Database.DatabaseConnection;
import com.example.cubex.Main;
import com.example.cubex.Validator.Validator;
import com.example.cubex.model.CacheStatic;
import com.example.cubex.model.CubeUser;
import com.example.cubex.model.Member;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class SettingCtrller extends CodeGeneral implements Initializable {
    @FXML
    private ToggleGroup RoleUser;
    @FXML
    private Pane accountPane;
    @FXML
    private Label accountTitle;
    @FXML
    private Button backBtt;
    @FXML
    private Button communityBtt;
    @FXML
    private Button deleteBtt;
    @FXML
    private Button deleteBtt1;
    @FXML
    private Pane deletePane;
    @FXML
    private Pane demoProfilePane;
    @FXML
    private Button editBtt;
    @FXML
    private Button generalBtt;
    @FXML
    private Button githubBtt;
    @FXML
    private Button manualBtt;
    @FXML
    private Button onCubexProBtt;
    @FXML
    private Button passwordBtt;
    @FXML
    private Pane passwordPane;
    @FXML
    private Button personalBtt;
    @FXML
    private Pane personalPane;
    @FXML
    private Button proBtt;
    @FXML
    private Pane proPane;
    @FXML
    private Pane settingGMenu;
    @FXML
    private TextField txtEmailUser;
    @FXML
    private TextField txtNameUser;
    @FXML
    private PasswordField txtNewPasswordConfirm;
    @FXML
    private PasswordField txtNewPasswordUp;
    @FXML
    private PasswordField txtPasswdUser;
    @FXML
    private Button upPasswordBtt;
    @FXML
    private Button deleteAccountBtt;

    @FXML
    private TextField cardNumberTxt;

    @FXML
    private TextField cvcTxt;

    @FXML
    private TextField fullNameTxt;

    @FXML
    private Label loginMessage;

    @FXML
    private TextField mmyyTxt;

    @FXML
    private Pane startProPane;
    @FXML
    private Label invalidProLabel;


    @FXML
    void onEditAction(ActionEvent event) {

    }


    @FXML
    void onBackAction(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new
                    FXMLLoader(Main.class.getResource("Page.fxml"));
            Parent root = fxmlLoader.load();
            PageCtrller controller = fxmlLoader.getController();
            Scene scene = new Scene(root);
            Stage stage = (Stage) this.backBtt.getScene().getWindow();
            stage.setTitle("Timer Page");
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
    void onCommunityAction(ActionEvent event) {
        if (StartCtrller.isDemo) {
            demoProfilePane.setVisible(true);
        } else {

        }
    }

    @FXML
    void onProfileAction() {
        if (StartCtrller.isDemo) {
            demoProfilePane.setVisible(true);
            accountPane.setVisible(false);
        } else {
            System.out.println(accountTitle);
            accountTitle.setStyle("-fx-fill: red;"); // -fx-stroke: black; -fx-stroke-width: 2px;");
            demoProfilePane.setVisible(false);
            accountPane.setVisible(true);
            personalPane.setVisible(true);
            deletePane.setVisible(false);
            passwordPane.setVisible(false);
            proPane.setVisible(false);

            txtNameUser.setPromptText("New name of user.");
            txtNameUser.setStyle("-fx-prompt-text-fill: #1e3728; -fx-background-color: #6b9979;");
            txtEmailUser.setPromptText("New example@example.com");
            txtEmailUser.setStyle("-fx-prompt-text-fill: #1e3728; -fx-background-color: #6b9979;");

            deleteBtt.setStyle("-fx-background-color :   #325743");
            proBtt.setStyle("-fx-background-color :  #325743");
            personalBtt.setStyle("-fx-background-color :  #b1c8a3");
            passwordBtt.setStyle("-fx-background-color :   #325743");
        }
    }


    @FXML
    void onGithubAction() {
        try {
            Desktop.getDesktop().browse(new URI("https://github.com/estelaV9"));
            /*SE USA LA CLASE DESKTOP QUE PERMITE HACER COSAS RELACIONADAS CON EL ESCRITORIO DEL ORDENADOR
            getDesktop() ES UN METODO QUE PROPORCIONA UNA INSTANCIA, ES DECIR, UN OBJETO DE LA CLASE DESKTOP.
            EL METODO browse() NOS PERMITE ABRIR UNA URL EN EL NAVEGADOR WEB PREDETERMINADO*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    } // REDIRIGE A MI GITHUB


    /**
     * ACCOUNT
     **/

    @FXML
    void onDeleteAction(ActionEvent event) {
        personalPane.setVisible(false);
        passwordPane.setVisible(false);
        proPane.setVisible(false);
        deletePane.setVisible(true);
        deleteBtt.setStyle("-fx-background-color :  #b1c8a3");
        proBtt.setStyle("-fx-background-color :  #325743");
        personalBtt.setStyle("-fx-background-color :  #325743");
        passwordBtt.setStyle("-fx-background-color :  #325743");
    }

    @FXML
    void onDeleteAccountAction(ActionEvent event) throws SQLException {
        int opcion = JOptionPane.showConfirmDialog(null,
                "¿Está seguro de que desea eliminar la cuenta?", "Confirmación", JOptionPane.YES_NO_OPTION);
        if (opcion == JOptionPane.YES_OPTION) {
            CubeUserDAO.deleteUser(CacheStatic.cubeUser.getMail());
            if (CubeUserDAO.successfulDelete) {
                // SI SE ELIMINA EL USUARIO SE MUESTRA EL MENSAJE
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Eliminación de usuario");
                alert.setHeaderText("Eliminación exitosa");
                alert.setContentText("Se ha eliminado el usuario correctamente.");
                alert.showAndWait();
                openMainPage(); // SE VA A LA PAGINA PARA QUE SE REGISTRE
            } else {
                // SI NO SE ENCONTRO USUARIO SE MUESTRA UN MENSAJE (POR SI ACASO)
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error al eliminar usuario");
                alert.setHeaderText("Error");
                alert.setContentText("No se encontró el usuario para eliminar.");
                alert.showAndWait();
            }
        }
    }

    @FXML
    void onPasswordAction(ActionEvent event) {
        personalPane.setVisible(false);
        proPane.setVisible(false);
        passwordPane.setVisible(true);
        deletePane.setVisible(false);
        txtNewPasswordUp.setPromptText("New password.");
        txtNewPasswordUp.setStyle("-fx-prompt-text-fill: #1e3728; -fx-background-color: #6b9979;");
        txtNewPasswordConfirm.setPromptText("Confirm password.");
        txtNewPasswordConfirm.setStyle("-fx-prompt-text-fill: #1e3728; -fx-background-color: #6b9979;");
        deleteBtt.setStyle("-fx-background-color :   #325743");
        proBtt.setStyle("-fx-background-color :  #325743");
        personalBtt.setStyle("-fx-background-color :  #325743");
        passwordBtt.setStyle("-fx-background-color :   #b1c8a3");
    }

    @FXML
    void onUpPasswordAction(ActionEvent event) {
        if (txtNewPasswordUp.getText().isEmpty() || txtNewPasswordConfirm.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Campos vacíos.");
            alert.setHeaderText("¡ERROR!");
            alert.setContentText("Por favor, completa todos los campos antes de continuar.");
            alert.showAndWait();
        }
        if (!txtNewPasswordUp.getText().equals(txtNewPasswordConfirm.getText())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Contraseñas no coinciden.");
            alert.setHeaderText("¡ERROR!");
            alert.setContentText("Las contraseñas no coinciden. Por favor, verifica e intenta nuevamente.");
            alert.showAndWait();
        } else if (Validator.isValidPassword(txtNewPasswordUp.getText())) {
            CubeUserDAO.modifyPassword(txtNewPasswordUp.getText(), CacheStatic.cubeUser.getMail());
            if (CubeUserDAO.successfulModify) {
                // SI SE ACTUALIZO EL USUARIO, MOSTRAR UN MENSAJE DE EXITO
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Actualizacion de usuario");
                alert.setHeaderText("Actualizacion exitosa");
                alert.setContentText("Se ha actualizado el usuario correctamente.");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error al modificar usuario");
                alert.setHeaderText("Error");
                alert.setContentText("No se pudo modificar la contraseña.");
                alert.showAndWait();
            }
        }
    }


    @FXML
    void onPersonalAction(ActionEvent event) {
        personalPane.setVisible(true);
        deletePane.setVisible(false);
        proPane.setVisible(false);
        passwordPane.setVisible(false);
        deleteBtt.setStyle("-fx-background-color :   #325743");
        proBtt.setStyle("-fx-background-color :  #325743");
        personalBtt.setStyle("-fx-background-color :  #b1c8a3");
        passwordBtt.setStyle("-fx-background-color :   #325743");

    }

    @FXML
    void onUpdateAction(ActionEvent event) {
        RadioButton seleccionado = (RadioButton) RoleUser.getSelectedToggle();
        if (txtNameUser.getText().isEmpty() || txtEmailUser.getText().isEmpty() || RoleUser.getSelectedToggle() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Campos vacíos.");
            alert.setHeaderText("¡ERROR!");
            alert.setContentText("Por favor, completa todos los campos antes de continuar.");
            alert.showAndWait();
        } else if (!Validator.isValidMail(txtEmailUser.getText())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Correo no válido.");
            alert.setHeaderText("¡ERROR!");
            alert.setContentText("Por favor, ingrese un correo válido.\nFor example : example@example.com");
            alert.showAndWait();
        } else {
            /****COMPROBACIONES PARA CONVERTIR EN PRO****/
            if (seleccionado.getText().equals("MEMBER")) {
                onProAction();
                if (CubeUserDAO.successfulModifyProUser) {
                    CubeUserDAO.modifyUser(txtNameUser.getText(), txtEmailUser.getText(), seleccionado.getText(),
                            CacheStatic.cubeUser.getMail());
                    if (CubeUserDAO.successfulModifyUser) {
                        // SI SE ACTUALIZO EL USUARIO, MOSTRAR UN MENSAJE DE EXITO
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Actualizacion de usuario");
                        alert.setHeaderText("Actualizacion exitosa");
                        alert.setContentText("Se ha actualizado el usuario correctamente.");
                        alert.showAndWait();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Actualizacion de usuario");
                        alert.setHeaderText("Actualizacion fallida");
                        alert.setContentText("No se ha actualizado el usuario correctamente.");
                        alert.showAndWait();
                    }
                }
            } else {

            }
        }
    }

    @FXML
    void onProAction() {
        personalPane.setVisible(false);
        startProPane.setVisible(false);
        deletePane.setVisible(false);
        proPane.setVisible(true);
        passwordPane.setVisible(false);
        deleteBtt.setStyle("-fx-background-color :   #325743");
        proBtt.setStyle("-fx-background-color :  #b1c8a3");
        personalBtt.setStyle("-fx-background-color :  #325743");
        passwordBtt.setStyle("-fx-background-color :   #325743");
    }

    @FXML
    void onCubexProAction(ActionEvent event) {
        startProPane.setVisible(true);
        cardNumberTxt.setStyle("-fx-prompt-text-fill: #1e3728; -fx-background-color: #6b9979;");
        fullNameTxt.setStyle("-fx-prompt-text-fill: #1e3728; -fx-background-color: #6b9979;");
        mmyyTxt.setStyle("-fx-prompt-text-fill: #1e3728; -fx-background-color: #6b9979;");
        cvcTxt.setStyle("-fx-prompt-text-fill: #1e3728; -fx-background-color: #6b9979;");
    }

    @FXML
    void onStartProAction(ActionEvent event) {
        if (cardNumberTxt.getText().isEmpty() || fullNameTxt.getText().isEmpty() || cvcTxt.getText().isEmpty()
                || mmyyTxt.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Campos vacíos.");
            alert.setHeaderText("¡ERROR!");
            alert.setContentText("Por favor, completa todos los campos antes de continuar.");
            alert.showAndWait();
        } else if (!Validator.isValidNameCard(fullNameTxt.getText())){
            invalidProLabel.setText("Invalid name");
        } /*else if (!Validator.noNumber) {
            // COMPROBAR QUE EN EL NUMERO DE LA TARJETA Y EL CVC SOLO TENGA NUMEROS
            invalidProLabel.setText("Only numbers");
        }*/ else if (!Validator.isValidCard(Long.parseLong(cardNumberTxt.getText()))) {
            invalidProLabel.setText("Invalid card");
        } else if (!Validator.isValidCvc(Integer.parseInt(cvcTxt.getText()))) {
            invalidProLabel.setText("Invalid cvc");
        } else if(!Validator.isValidMY(mmyyTxt.getText())) {
            invalidProLabel.setText("Invalid MM/YY");
        } else {
            invalidProLabel.setText("");
            CubeUserDAO.modifyPro(CacheStatic.cubeUser.getMail());
            if (CubeUserDAO.successfulModifyProUser) {
                // SI SE ACTUALIZO EL USUARIO, MOSTRAR UN MENSAJE DE EXITO
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Actualizacion de usuario");
                alert.setHeaderText("Actualizacion exitosa");
                alert.setContentText("Se ha convertido en member correctamente.");
                alert.showAndWait();
                String nombreUsuario = CacheStatic.cubeUser.getNameUser(); // Obtener el nombre de usuario actual
                String contraseña = CacheStatic.cubeUser.getPasswordUser(); // Obtener la contraseña del usuario actual
                String correoElectronico = CacheStatic.cubeUser.getMail(); // Obtener el correo electrónico del usuario actual
                LocalDate fechaActual = LocalDate.now(); // Obtener la fecha actual

                // Llamar al método para insertar un nuevo miembro en la base de datos
                MemberDAO.insertNewMember(nombreUsuario, contraseña, correoElectronico, fechaActual);

                startProPane.setVisible(false);
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Actualizacion de user pro");
                alert.setHeaderText("Actualizacion fallida");
                alert.setContentText("No se ha podido actualizar porque ese usuario ya es miembro.");
                alert.showAndWait();
            }
        }
    }

    @FXML
    void onExitStartProAction(ActionEvent event) {
        startProPane.setVisible(false);
    }


    public void openMainPage() {
        try {
            FXMLLoader fxmlLoader = new
                    FXMLLoader(Main.class.getResource("Start.fxml"));
            Parent root = fxmlLoader.load();
            StartCtrller controller = fxmlLoader.getController();
            Scene scene = new Scene(root);
            Stage stage = (Stage) this.deleteAccountBtt.getScene().getWindow();
            stage.setTitle("Start Application");
            stage.setScene(scene);
            if (!stage.isShowing()) {
                stage.show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        demoProfilePane.setVisible(false);
        accountPane.setVisible(false);
        onProfileAction();
    }

}

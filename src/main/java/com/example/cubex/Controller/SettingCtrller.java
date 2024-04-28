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
import java.util.ResourceBundle;

public class SettingCtrller extends CodeGeneral implements Initializable {
    @FXML private ToggleGroup RoleUser;
    @FXML private Pane accountPane;
    @FXML private Label accountTitle;
    @FXML private Button backBtt;
    @FXML private Button communityBtt;
    @FXML private Button deleteBtt;
    @FXML private Button deleteBtt1;
    @FXML private Pane deletePane;
    @FXML private Pane demoProfilePane;
    @FXML private Button editBtt;
    @FXML private Button generalBtt;
    @FXML private Button githubBtt;
    @FXML private Button manualBtt;
    @FXML private Button onCubexProBtt;
    @FXML private Button passwordBtt;
    @FXML private Pane passwordPane;
    @FXML private Button personalBtt;
    @FXML private Pane personalPane;
    @FXML private Button proBtt;
    @FXML private Pane proPane;
    @FXML private Pane settingGMenu;
    @FXML private TextField txtEmailUser;
    @FXML private TextField txtNameUser;
    @FXML private PasswordField txtNewPasswordConfirm;
    @FXML private PasswordField txtNewPasswordUp;
    @FXML private PasswordField txtPasswdUser;
    @FXML private Button upPasswordBtt;
    @FXML private Button deleteAccountBtt;
    int rowsNameUp;


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
            stage.setTitle("Application Page");
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
        if(StartCtrller.isDemo){
            demoProfilePane.setVisible(true);
        } else {}
    }

    @FXML
    void onProfileAction(ActionEvent event) {
        if(StartCtrller.isDemo){
            demoProfilePane.setVisible(true);
            accountPane.setVisible(false);
        } else{
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
            txtPasswdUser.setPromptText("New password.");
            txtPasswdUser.setStyle("-fx-prompt-text-fill: #1e3728; -fx-background-color: #6b9979;");

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


    /** ACCOUNT **/

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
        Connection connection = DatabaseConnection.conectar();
        int opcion = JOptionPane.showConfirmDialog(null,
                "¿Está seguro de que desea eliminar la cuenta?", "Confirmación", JOptionPane.YES_NO_OPTION);
        if (opcion == JOptionPane.YES_OPTION) {
            String sqlDelete = "DELETE FROM CUBE_USERS WHERE NAME_USER = ?";
            PreparedStatement statement = connection.prepareStatement(sqlDelete);
            statement.setString(1, RegistrationCtrller.nameUser);
            int rowsDelete = statement.executeUpdate();
            if (rowsDelete > 0) {
                // SI SE BORRO EL USUARIO, MOSTRAR UN MENSAJE DE EXITO
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Eliminacion de usuario");
                alert.setHeaderText("Eliminacion exitosa");
                alert.setContentText("Se ha eliminado el usuario correctamente.");
                alert.showAndWait();

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
                } // DESPUES DE ELIMINAR LA CUENTA, IR A LA PAGINA PRINCIAPL
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
        txtNewPasswordUp .setStyle("-fx-prompt-text-fill: #1e3728; -fx-background-color: #6b9979;");
        txtNewPasswordConfirm.setPromptText("Confirm password.");
        txtNewPasswordConfirm.setStyle("-fx-prompt-text-fill: #1e3728; -fx-background-color: #6b9979;");
        deleteBtt.setStyle("-fx-background-color :   #325743");
        proBtt.setStyle("-fx-background-color :  #325743");
        personalBtt.setStyle("-fx-background-color :  #325743");
        passwordBtt.setStyle("-fx-background-color :   #b1c8a3");
    }
    @FXML
    void onUpPasswordAction(ActionEvent event) throws SQLException{
        if (txtNewPasswordUp.getText().equals("") || txtNewPasswordConfirm.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Campos vacíos.");
            alert.setHeaderText("¡ERROR!");
            alert.setContentText("Por favor, completa todos los campos antes de continuar.");
            alert.showAndWait();
        } if(!txtNewPasswordUp.getText().equals(txtNewPasswordConfirm.getText())){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Contraseñas no coinciden.");
            alert.setHeaderText("¡ERROR!");
            alert.setContentText("Las contraseñas no coinciden. Por favor, verifica e intenta nuevamente.");
            alert.showAndWait();
        }else {
            Connection connection = DatabaseConnection.conectar();

            String sqlUpdate = "UPDATE CUBE_USERS SET PASSWORD_USER = ? WHERE NAME_USER = ?";
            PreparedStatement statement = connection.prepareStatement(sqlUpdate);
            if(rowsNameUp > 0){ // SI ACTUALIZO PRIMERO SU CUENTA, SE PONE EL NUEVO NOMBRE QUE HA ACTUALIZADO
                statement.setString(1, txtNewPasswordUp.getText());
                statement.setString(2, txtNameUser.getText());
            } else { // SINO, SE COGE EL NOMBRE DEL LOGIN
                statement.setString(1, txtNewPasswordUp.getText());
                statement.setString(2, RegistrationCtrller.nameUser);
            }

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                // SI SE ACTUALIZO EL USUARIO, MOSTRAR UN MENSAJE DE EXITO
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Actualizacion de usuario");
                alert.setHeaderText("Actualizacion exitosa");
                alert.setContentText("Se ha actualizado el usuario correctamente.");
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
        if (txtNameUser.getText().equals("") || txtEmailUser.getText().equals("") || txtPasswdUser.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Campos vacíos.");
            alert.setHeaderText("¡ERROR!");
            alert.setContentText("Por favor, completa todos los campos antes de continuar.");
            alert.showAndWait();
        } else {
            Connection connection = DatabaseConnection.conectar();
            try {
                String sqlInsert = "UPDATE CUBE_USERS SET NAME_USER = ?, PASSWORD_USER = ?, MAIL = ?" +
                        " WHERE NAME_USER = ?";
                PreparedStatement statement = connection.prepareStatement(sqlInsert);
                statement.setString(1, txtNameUser.getText());
                statement.setString(2, txtPasswdUser.getText());
                statement.setString(3, txtEmailUser.getText());
                statement.setString(4, RegistrationCtrller.nameUser);

                rowsNameUp = statement.executeUpdate();
                if (rowsNameUp > 0) {
                    // SI SE ACTUALIZO EL USUARIO, MOSTRAR UN MENSAJE DE EXITO
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Actualizacion de usuario");
                    alert.setHeaderText("Actualizacion exitosa");
                    alert.setContentText("Se ha actualizado el usuario correctamente.");
                    alert.showAndWait();
                }
            } catch (SQLException e) {
                // SI EL NOMBRE YA EXISTE, MOSTRAR UN MENSAJE DE ERROR
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error al actualizar usuario");
                alert.setHeaderText("Nombre ya existente");
                alert.setContentText("Ese nombre ya existe. Por favor, elija otro.");
                alert.showAndWait();
            }
        }
    }

    @FXML
    void onProAction(ActionEvent event) {
        personalPane.setVisible(false);
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

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        demoProfilePane.setVisible(false);
        accountPane.setVisible(false);
    }

}

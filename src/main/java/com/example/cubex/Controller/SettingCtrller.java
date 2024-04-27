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

import java.io.IOException;
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

    @FXML
    void onUpPasswordAction(ActionEvent event) {
        if (txtNameUser.getText().equals("") || txtEmailUser.getText().equals("") || txtPasswdUser.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Campos vacíos.");
            alert.setHeaderText("¡ERROR!");
            alert.setContentText("Por favor, completa todos los campos antes de continuar.");
            alert.showAndWait();
        } else {
            Connection connection = DatabaseConnection.conectar();
            try {
                String sqlInsert = "UPDATE CUBE_USERS SET NAME_USER = ?, ";
                PreparedStatement statement = connection.prepareStatement(sqlInsert);
                statement.setString(1, txtNameUser.getText());
                statement.setString(2, txtPasswdUser.getText());
                statement.setString(3, txtEmailUser.getText());

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
    void onCubexProAction(ActionEvent event) {

    }
    @FXML
    void onGeneralBtt(ActionEvent event) {

    }

    @FXML
    void onManualAction(ActionEvent event) {

    }
    @FXML
    void onCommunityAction(ActionEvent event) {

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
    void onUpdateAction(ActionEvent event) {

    }
    @FXML
    void onGithubAction() {
        // githubBtt.setOnAction(event -> 'google.com');
    }


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
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        demoProfilePane.setVisible(false);
        accountPane.setVisible(false);
    }

}

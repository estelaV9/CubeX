package com.example.cubex.Controller;

import com.example.cubex.DAO.*;
import com.example.cubex.Database.DatabaseConnection;
import com.example.cubex.Main;
import com.example.cubex.Validator.Validator;
import com.example.cubex.model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
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
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.geometry.Insets;

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
    private ComboBox<?> categoriesCB;

    @FXML
    private Button closeFiBtt;

    @FXML
    public Pane communityPane;

    @FXML
    private Button expFilterBtt;

    @FXML
    private Button filterBtt;
    @FXML
    private Button userTops;

    @FXML
    private Pane filterMenu;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private Label invalidProLabel;

    @FXML
    private ImageView imageProfile;

    @FXML
    private ImageView imageProfileEdit;

    LocalDate localDate = LocalDate.now();
    static boolean pulsarFilter;
    public static boolean isModifyImagen;


    @FXML
    void onAvgFilterAction(ActionEvent event) {
        onCloseFilterAction();
        String category = String.valueOf(categoriesCB.getSelectionModel().getSelectedItem());
        int idCategory = SessionDAO.idCategory(category);
        int contador = 0;

        // SE CREAN 4 VBOX PARA CADA COLUMNS
        VBox vbox1 = new VBox();
        VBox vbox2 = new VBox();
        VBox vbox3 = new VBox();
        VBox vbox4 = new VBox();

        //ESPACIO VERTICAL ENTRE LOS ELEMENTOS DE LAS COLUMNAS
        vbox1.setSpacing(10);
        vbox2.setSpacing(10);
        vbox3.setSpacing(10);
        vbox4.setSpacing(10);

        // ITERAR SOBRE LA LISTA DE MINUTOS
        for (Average average : AverageDAO.avgMinutesType(idCategory)) {
            contador++;
            // SE CREA UN LABEL POR CADA COLUMNA
            Label label1 = new Label("   " + contador + ")");
            Label label2 = new Label(average.getAvgMinutes() + " : ");

            // SE LES DA UN ESTILO
            label1.setStyle("-fx-font-size: 17px; -fx-text-fill: black;");
            label2.setStyle("-fx-font-size: 17px; -fx-text-fill: black;");

            // SE AGREGAN LOS LABELS A LAS COLUMNAS CORRESPONDIENTES
            vbox1.getChildren().add(label1);
            vbox2.getChildren().add(label2);
        }

        // ITERAR SOBRE LA LISTA DE SEGUNDOS Y AÑADIRLE
        for (Average average : AverageDAO.avgSecondsType(idCategory)) {
            Label label3 = new Label(String.valueOf(average.getAvgSeconds()));
            label3.setStyle("-fx-font-size: 17px; -fx-text-fill: black;");
            vbox3.getChildren().addAll(label3);
        }

        // ITERAR SOBRE LA LISTA DE USUARIOS QUE TIENEN ESE TIEMPO
        for (CubeUser cubeUser : AverageDAO.selectNameAvg(AverageDAO.avgMinutesType(idCategory), AverageDAO.avgSecondsType(idCategory))) {
            Label label4 = new Label(cubeUser.getMail());
            label4.setStyle("-fx-font-size: 17px; -fx-text-fill: black;");
            vbox4.getChildren().add(label4);
        }

        // SE CREA UN HBOX PARA JUNTAR LOS MINUTOS CON LOS SEGUNDOS SIN ESPACIOS
        HBox hBox1 = new HBox(vbox2, vbox3);
        hBox1.setSpacing(0);

        // SE CREAN UN HBOX QUE CONTENGAN LAS COLUMNAS Y EL HBOX DEL TIEMPOS
        HBox hbox = new HBox(vbox1, hBox1, vbox4);
        hbox.setSpacing(20); // ESPACIO HORIZONTAL ENTRE LAS COLUMNAS
        hbox.setStyle("-fx-background-color :  #325743");

        // SE AGREGA EL HBOX AL SCROLLPANE
        scrollPane.setContent(hbox);
        scrollPane.setFitToWidth(true);
    }

    @FXML
    void onBestFilterAction(ActionEvent event) {
        onCloseFilterAction();
        String category = String.valueOf(categoriesCB.getSelectionModel().getSelectedItem());
        int idCategory = SessionDAO.idCategory(category);
        int contador = 0;

        // SE CREAN 4 VBOX PARA CADA COLUMNS
        VBox vbox1 = new VBox();
        VBox vbox2 = new VBox();
        VBox vbox3 = new VBox();
        VBox vbox4 = new VBox();

        //ESPACIO VERTICAL ENTRE LOS ELEMENTOS DE LAS COLUMNAS
        vbox1.setSpacing(10);
        vbox2.setSpacing(10);
        vbox3.setSpacing(10);
        vbox4.setSpacing(10);

        // ITERAR SOBRE LA LISTA DE MINUTOS
        for (TimeTraining timeTraining : TimeTrainingDAO.pbMinutesType(idCategory)) {
            contador++;
            // SE CREA UN LABEL POR CADA COLUMNA
            Label label1 = new Label(String.valueOf(contador));
            Label label2 = new Label(timeTraining.getMinutes() + " : ");

            // SE LES DA UN ESTILO
            label1.setStyle("-fx-font-size: 17px; -fx-text-fill: black;");
            label2.setStyle("-fx-font-size: 17px; -fx-text-fill: black;");

            // SE AGREGAN LOS LABELS A LAS COLUMNAS CORRESPONDIENTES
            vbox1.getChildren().add(label1);
            vbox2.getChildren().add(label2);
        }

        // ITERAR SOBRE LA LISTA DE SEGUNDOS Y AÑADIRLE
        for (TimeTraining timeTraining : TimeTrainingDAO.PbSecondsType(idCategory)) {
            Label label3 = new Label(String.valueOf(timeTraining.getSeconds()));
            label3.setStyle("-fx-font-size: 17px; -fx-text-fill: black;");
            vbox3.getChildren().addAll(label3);
        }

        // ITERAR SOBRE LA LISTA DE USUARIOS QUE TIENEN ESE TIEMPO
        for (CubeUser cubeUser : TimeTrainingDAO.selectNamePbTime(TimeTrainingDAO.pbMinutesType(idCategory), TimeTrainingDAO.PbSecondsType(idCategory))) {
            Label label4 = new Label(cubeUser.getMail());
            label4.setStyle("-fx-font-size: 17px; -fx-text-fill: black;");
            vbox4.getChildren().add(label4);
        }

        // SE CREA UN HBOX PARA JUNTAR LOS MINUTOS CON LOS SEGUNDOS SIN ESPACIOS
        HBox hBox1 = new HBox(vbox2, vbox3);
        hBox1.setSpacing(0);

        // SE CREAN UN HBOX QUE CONTENGAN LAS COLUMNAS Y EL HBOX DEL TIEMPOS
        HBox hbox = new HBox(vbox1, hBox1, vbox4);
        hbox.setSpacing(20); // ESPACIO HORIZONTAL ENTRE LAS COLUMNAS
        hbox.setStyle("-fx-background-color :  #325743");

        // SE AGREGA EL HBOX AL SCROLLPANE
        scrollPane.setContent(hbox);
        scrollPane.setFitToWidth(true);
    } // FILTRAR POR MEJOR TIEMPO

    @FXML
    void onCloseFilterAction() {
        filterMenu.setVisible(false);
    } // CERRAR EL MENU DE FILTROS

    @FXML
    void onChampFilterAction(ActionEvent event) {
        onCloseFilterAction();
        String category = String.valueOf(categoriesCB.getSelectionModel().getSelectedItem());
        int contador = 0;
        CubeUserDAO.users = CubeUserDAO.listUser("average", category);

        // SE CREAN 4 VBOX PARA CADA COLUMNS
        VBox vbox1 = new VBox();
        VBox vbox2 = new VBox();
        VBox vbox3 = new VBox();
        VBox vbox4 = new VBox();


        //ESPACIO VERTICAL ENTRE LOS ELEMENTOS DE LAS COLUMNAS
        vbox1.setSpacing(10);
        vbox2.setSpacing(10);
        vbox3.setSpacing(10);
        vbox4.setSpacing(10);

        // ITERAR SOBRE LA LISTA DE USUARIOS Y CREAR UN LABEL POR CADA UNO
        for (CubeUser user : CubeUserDAO.users) {
            contador++;
            // SE CREA UN LABEL POR CADA COLUMNA
            Label label1 = new Label(String.valueOf(contador));
            Label label2 = new Label(user.getNameUser());
            Label label3 = new Label(String.valueOf(user.getLevelUser()));
            Label label4 = new Label(String.valueOf(user.getRoleUser()));

            // SE LES DA UN ESTILO
            label1.setStyle("-fx-font-size: 17px; -fx-text-fill: black;");
            label2.setStyle("-fx-font-size: 17px; -fx-text-fill: black;");
            label3.setStyle("-fx-font-size: 17px; -fx-text-fill: black;");
            label4.setStyle("-fx-font-size: 17px; -fx-text-fill: black;");

            // SE AGREGAN LOS LABELS A LAS COLUMNAS CORRESPONDIENTES
            vbox1.getChildren().add(label1);
            vbox2.getChildren().add(label2);
            vbox3.getChildren().add(label3);
            vbox4.getChildren().add(label4);
        }

        // SE CREAN UN HBOX QUE CONTENGAN LAS 4 COLUMNAS
        HBox hbox = new HBox(vbox1, vbox2, vbox3, vbox4);
        hbox.setSpacing(20); // ESPACIO HORIZONTAL ENTRE LAS COLUMNAS
        hbox.setStyle("-fx-background-color :  #325743");


        // SE AGREGA EL HBOX AL SCROLLPANE
        scrollPane.setContent(hbox);
        scrollPane.setFitToWidth(true);
    } // FILTRAR POR CAMPEONATO

    @FXML
    void onFilterAction(ActionEvent event) {
        if(!pulsarFilter){
            filterMenu.setVisible(true);
            pulsarFilter = true;
        } else {
            onCloseFilterAction();
            pulsarFilter = false;
        }
    }


    @FXML
    void onEditAction(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new
                    FXMLLoader(Main.class.getResource("imagenUrl.fxml"));
            Parent root = fxmlLoader.load();
            ImagenCtrller controller = fxmlLoader.getController();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            // stage.show();
            stage.setTitle("Imagen Page");
            Stage myStage = (Stage) this.editBtt.getScene().getWindow();
            myStage.show();
            myStage.setOnCloseRequest(event1 -> {
                myStage.requestFocus();
            });
            if (!stage.isShowing()) {
                stage.show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    } // ABRIR OTRO STAGE PARA EDITAR FOTO


    @FXML
    void onUpdateImgAction(){
        imageProfile.setImage(ImagenCtrller.returnImagen(ImagenCtrller.selectedFile));
        imageProfileEdit.setImage(ImagenCtrller.returnImagen(ImagenCtrller.selectedFile));
        isModifyImagen = true;
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
    void onManualAction(ActionEvent event) {

    }

    @FXML
    void onCommunityAction(ActionEvent event) {
        if (StartCtrller.isDemo) {
            demoProfilePane.setVisible(true);
        } else {
            communityPane.setVisible(true);
            accountPane.setVisible(false);
            filterMenu.setVisible(false);
            userTops.setStyle("-fx-background-color: #b1c8a3;");
        }
    }

    @FXML
    void onProfileSetAction() {
        if (StartCtrller.isDemo) {
            demoProfilePane.setVisible(true);
            accountPane.setVisible(false);
            communityPane.setVisible(false);
        } else {
            communityPane.setVisible(false);
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
            if (CubeUserDAO.deleteUser(CacheStatic.cubeUser.getMail())) {
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
            if (CubeUserDAO.modifyPassword(txtNewPasswordUp.getText(), CacheStatic.cubeUser.getMail())) {
                // SI SE ACTUALIZO EL USUARIO, MOSTRAR UN MENSAJE DE EXITO
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Actualizacion de usuario");
                alert.setHeaderText("Actualizacion exitosa");
                alert.setContentText("Se ha actualizado la contraseña correctamente.");
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
            CubeUser.Role role;
            if(seleccionado.getText().equals("USER")){
                role = CubeUser.Role.USER;
            } else {
                role = CubeUser.Role.MEMBER;
            }

            if (seleccionado.getText().equals("MEMBER") && !MemberDAO.selectMember(CacheStatic.cubeUser.getMail())) {
                onProAction();
            } else {
                if(CubeUserDAO.modifyUser(txtNameUser.getText(), txtEmailUser.getText(), String.valueOf(role), CacheStatic.cubeUser.getMail())){
                    CacheStatic.cubeUser.setMail(txtEmailUser.getText());
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
                    alert.setContentText("No se pudo modificar el usuario.");
                    alert.showAndWait();
                }
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
    void onCubexProAction() {
        startProPane.setVisible(true);
        cardNumberTxt.setStyle("-fx-prompt-text-fill: #1e3728; -fx-background-color: #6b9979;");
        fullNameTxt.setStyle("-fx-prompt-text-fill: #1e3728; -fx-background-color: #6b9979;");
        mmyyTxt.setStyle("-fx-prompt-text-fill: #1e3728; -fx-background-color: #6b9979;");
        cvcTxt.setStyle("-fx-prompt-text-fill: #1e3728; -fx-background-color: #6b9979;");
    }

    @FXML
    void onStartProAction() {
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
            if (CubeUserDAO.isMember && MemberDAO.insertMember(MemberDAO.insertIdUser(CacheStatic.cubeUser.getMail()), localDate)) {
                // SI SE ACTUALIZO EL USUARIO, MOSTRAR UN MENSAJE DE EXITO
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Actualizacion de usuario");
                alert.setHeaderText("Actualizacion exitosa");
                alert.setContentText("Se ha convertido en member correctamente.");
                alert.showAndWait();
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

    @FXML
    void onProfileEditAction(ActionEvent event) {
        if(!StartCtrller.isDemo){
            accountPane.setVisible(true);
            communityPane.setVisible(false);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        demoProfilePane.setVisible(false);
        accountPane.setVisible(false);
        communityPane.setVisible(false);
        onProfileSetAction();
        CodeGeneral.cubeCategory(categoriesCB);
        if(isModifyImagen){
            onUpdateImgAction();
        }
    }

}

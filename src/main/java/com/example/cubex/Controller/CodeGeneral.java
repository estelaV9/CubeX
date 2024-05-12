package com.example.cubex.Controller;

import com.example.cubex.DAO.CubeUserDAO;
import com.example.cubex.Database.DatabaseConnection;
import com.example.cubex.Main;
import com.example.cubex.model.CacheStatic;
import com.example.cubex.model.CubeUser;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CodeGeneral implements Initializable {
                            /********* GENERAL ATRIBUTTES ***********/
    @FXML public Button closeBtt;
    @FXML public Button closeOpBtt;
    @FXML public Button closeSeBtt;
    @FXML public Button compeBtt;
    @FXML public Button exitBtt;
    @FXML public Button openBtt;
    @FXML public Button optionBtt;
    @FXML public Pane optionMenu;
    @FXML public Button profileBtt;
    @FXML public TextField scramblePane;
    @FXML public Button sessionBtt;
    @FXML public Pane settingMenu;
    @FXML public Button settingsBtt;
    @FXML public Button settingsMenuBtt;
    @FXML public Button solvesBtt;
    @FXML public Button timerBtt;
    @FXML public Pane timesMenu;
    @FXML public TextField sessionName;
    @FXML public Button champBtt;
    @FXML public Pane optionDemoPane;
    @FXML public Button signOutBtt;
    @FXML
    private Button editProfileBtt;
    @FXML
    public Pane demoProfilePane;
    @FXML
    public Pane profilePage;
    @FXML
    public Pane communityPane;
    @FXML
    private TextField levelProfileTxt;

    @FXML
    private Label userProfiletxt;
    @FXML
    private ImageView proProfileImg;

    @FXML
    private Label proProfileTxt;

    @FXML
    private Label dateProfileTxt;


    // ATRIBUTOS SEMAFOROS PARA ABRIR Y CERRAR DESDE EL MISMO BOTON
    boolean pulsarOption = false;
    boolean pulsarSettings = false;
    boolean pulsarProfile = false;
    static ArrayList<String> categories = new ArrayList(); // ARRAYLIST CON CATEGORIAS
    static int min = 0;
    static int seg = 0;
    static int cent = 0;
    static Timeline timeline;


                            /********* GENERAL METHODS ***********/
    @FXML void manejarTeclaPresionada(KeyEvent event) {

    }

    @FXML void manejarTeclaSoltada(KeyEvent event) {

    }

    @FXML void onExitAction(ActionEvent event) {
        int opcion = JOptionPane.showConfirmDialog(null,
                "¿Está seguro de que desea salir?", "Confirmación", JOptionPane.YES_NO_OPTION);
        if (opcion == JOptionPane.YES_OPTION) {
            System.exit(0); // CERRAR APLICACIÓN
        }
    } // SALIR DE LA APLICACIÓN

    @FXML void onProfileAction(ActionEvent event) {
        if(!pulsarProfile){
            if(StartCtrller.isDemo){
                demoProfilePane.setVisible(true);
                pulsarProfile = true;
            } else {
                profilePage.setVisible(true);
                CubeUserDAO.dataProfile(CacheStatic.cubeUser.getMail());
                if(CacheStatic.cubeUser.getRoleUser() == CubeUser.Role.valueOf("MEMBER")){
                    proProfileImg.setVisible(true);
                    proProfileTxt.setVisible(true);
                } else {
                    proProfileImg.setVisible(false);
                    proProfileTxt.setVisible(false);
                }
                userProfiletxt.setText(CacheStatic.cubeUser.getNameUser());
                dateProfileTxt.setText("Joined on " + CacheStatic.cubeUser.getRegistrationDate());
                levelProfileTxt.setText(String.valueOf(CacheStatic.cubeUser.getLevelUser()));
                pulsarProfile = true;
            }
        } else {
            if(StartCtrller.isDemo){
                demoProfilePane.setVisible(false);
                pulsarProfile = false;
            } else {
                profilePage.setVisible(false);
                pulsarProfile = false;
            }
        }
    }

    @FXML
    void onEditProfileAction(ActionEvent event) {
        onSettingsMenuAction();
    }

                                /********* TIMES MENU ***********/
    @FXML void onOpenMenuAction(ActionEvent event) {
        timesMenu.setVisible(true);
        closeBtt.setVisible(true);
    }// SE ABRE EL PANEL DE TIEMPOS Y EL BOTON DE CERRAR

    @FXML void onCloseMenuAction(ActionEvent event) {
        timesMenu.setVisible(false);
        closeBtt.setVisible(false);
    }// SE CIERRA EL PANEL DE TIEMPOS Y EL BOTON DE CERRAR


                                /********* OPTIONS MENU ***********/

    @FXML void onOptionAction(ActionEvent event) {
        if(!pulsarOption){ // SI NO SE HA PULSADO, SE ABRE EL MENU
            if(StartCtrller.isDemo){
                optionMenu.setVisible(false);
                optionDemoPane.setVisible(true);
                pulsarOption = true;
            } else {
                optionMenu.setVisible(true);
                optionDemoPane.setVisible(false);
                pulsarOption = true;
            }
        } else { // SI SE HA PULSADO, SE CIERRA EL MENU
            onCloseOptionAction();
            pulsarOption = false;
        }
    } // MENU DE OPCIONES

    @FXML void onCloseOptionAction() {
        if(StartCtrller.isDemo){
            optionDemoPane.setVisible(false);
        } else {
            optionMenu.setVisible(false);
        }
    } // CERRAR EL MENU DE OPCIONES

    @FXML void onSettingsMenuAction() {
        try {
            FXMLLoader fxmlLoader = new
                    FXMLLoader(Main.class.getResource("Setting.fxml"));
            Parent root = fxmlLoader.load();
            SettingCtrller controller = fxmlLoader.getController();
            Scene scene = new Scene(root);
            Stage stage = (Stage) this.settingMenu.getScene().getWindow();
            stage.setTitle("Settings Page");
            stage.setScene(scene);
            if (!stage.isShowing()) {
                stage.show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML void onSignOutAction(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new
                    FXMLLoader(Main.class.getResource("Start.fxml"));
            Parent root = fxmlLoader.load();
            StartCtrller controller = fxmlLoader.getController();
            Scene scene = new Scene(root);
            Stage stage = (Stage) this.signOutBtt.getScene().getWindow();
            stage.setTitle("Start Application");
            stage.setScene(scene);
            if (!stage.isShowing()) {
                stage.show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


                                /********* SETTINGS MENU ***********/
    @FXML void onSettingsAction(ActionEvent event) {
        if(!pulsarSettings){ // SI NO SE HA PULSADO, SE ABRE EL MENU
            settingMenu.setVisible(true);
            pulsarSettings = true;
        } else { // SI SE HA PULSADO, SE CIERRA EL MENU
            onCloseSettingAction();
            pulsarSettings = false;
        }
    }// ABRIR EL MENU DE AJUSTES

    @FXML void onCloseSettingAction() {
        settingMenu.setVisible(false);
    } // CERRAR EL MENU DE AJUSTES

    @FXML void onCompeAction(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new
                    FXMLLoader(Main.class.getResource("Competition.fxml"));
            Parent root = fxmlLoader.load();
            CompetitionCtrller controller = fxmlLoader.getController();
            Scene scene = new Scene(root);
            Stage stage = (Stage) this.compeBtt.getScene().getWindow();
            stage.setTitle("Competition Page");
            stage.setScene(scene);
            if (!stage.isShowing()) {
                stage.show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }// IR A LA PAGINA COMPETITION

    @FXML void onSessionAction(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new
                    FXMLLoader(Main.class.getResource("Session.fxml"));
            Parent root = fxmlLoader.load();
            SessionCtrller controller = fxmlLoader.getController();
            Scene scene = new Scene(root);
            Stage stage = (Stage) this.sessionBtt.getScene().getWindow();
            stage.setTitle("Session Page");
            stage.setScene(scene);
            if (!stage.isShowing()) {
                stage.show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }// IR A LA PAGINA DE SESIONES


    @FXML void onTimerAction(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new
                    FXMLLoader(Main.class.getResource("Page.fxml"));
            Parent root = fxmlLoader.load();
            PageCtrller controller = fxmlLoader.getController();
            Scene scene = new Scene(root);
            Stage stage = (Stage) this.timerBtt.getScene().getWindow();
            stage.setTitle("Timer Page");
            stage.setScene(scene);
            if (!stage.isShowing()) {
                stage.show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }// PAGINA PRINCIPAL

    @FXML void onChampAction(ActionEvent event) {

    }

    public static String scramble(){
        Scramble scramble = new Scramble();
        int random = (int) (Math.random() * (25 - 20 + 1) + 20);
        return scramble.generateScramble(random);
    } // GENERAR EL SCRAMBLE

   public static void cubeCategory(ComboBox categoriesCB){
        categoriesCB.setPromptText("CATEGORY");
        categoriesCB.setStyle("-fx-background-color: #325743; -fx-text-fill: red;");

        // METER EN UN ARRAYLIST LAS CATEGORIAS
        Connection connection = DatabaseConnection.conectar();
        try {
            String sqlCount = "SELECT COUNT(ID_TYPE) FROM CUBE_TYPE";
            PreparedStatement statement1 = connection.prepareStatement(sqlCount);
            ResultSet resultSet = statement1.executeQuery();

            int count = 0;
            if (resultSet.next()) {
                count = resultSet.getInt(1);
            }

            String sqlCat = "SELECT NAME_TYPE FROM CUBE_TYPE";
            PreparedStatement statement = connection.prepareStatement(sqlCat);
            ResultSet resultSet1 = statement.executeQuery();
            while (resultSet1.next()) {
                categories.add(resultSet1.getString(1));
            }
            categoriesCB.getItems().addAll(categories);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    } // METER LAS CATEGORIAS DE LOS CUBOS EN UN ARRAYLIST

    public static void start(Label chrono) {
        timeline = new Timeline(new KeyFrame(Duration.millis(10), event1 -> {
            cent++;
            // CUANDO LLEGUE A 100 MILISEGUNDOS SE HACE UN SEGUNDO
            if (cent > 99) {
                seg++;
                cent = 0;
                if (seg > 59) {
                    min++;
                    seg = 0;
                }
            }
            /*PONER LOS CEROS*/
            String timeString; // ALMACENAR EL TIEMPO
            if (seg < 10) {
                timeString = min + ":0" + seg + "," + cent;
            } else {
                timeString = min + ":" + seg + "," + cent;
            }

            // CUANDO LLEGUE A 10 MINUTOS EL TIEMPO SE PARA
            if (min == 10) {
                timeString = min + ":0" + seg + "," + cent;
                timeline.stop();
            }
            chrono.setText(timeString); // MOSTRAR EL TIEMPO
        }));
        timeline.setCycleCount(Timeline.INDEFINITE); // DE TIEMPO INDEFINIDO
        timeline.play(); // COMENZAMOS NUESTRO TIMELINE
    }

    public static void parar() throws SQLException {
        timeline.stop();


    }

    public static void onFalseMenus
            (Pane demoProfilePane, Pane profilePage, Pane settingMenu, Pane optionMenu,
             Pane optionDemoPane, Pane nameSession){
        demoProfilePane.setVisible(false);
        profilePage.setVisible(false);
        settingMenu.setVisible(false);
        optionMenu.setVisible(false);
        optionDemoPane.setVisible(false);
        nameSession.setVisible(false);
    } // CERRAR LOS POPUPS


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}

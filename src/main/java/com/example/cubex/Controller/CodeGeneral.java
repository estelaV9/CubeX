package com.example.cubex.Controller;

import com.example.cubex.DAO.CubeUserDAO;
import com.example.cubex.DAO.TimeCompetitionDAO;
import com.example.cubex.DAO.TimeTrainingDAO;
import com.example.cubex.Database.DatabaseConnection;
import com.example.cubex.Main;
import com.example.cubex.model.CacheStatic;
import com.example.cubex.model.CubeUser;
import com.example.cubex.model.TimeCompetition;
import com.example.cubex.model.TimeTraining;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
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
    @FXML public Label scrambleLabel;
    @FXML public Button sessionBtt;
    @FXML public Pane settingMenu;
    @FXML public Button settingsBtt;
    @FXML public Button settingsMenuBtt;
    @FXML public Button timerBtt;
    @FXML public Button closeTimesCompe;
    @FXML public Button openTimesCompe;
    @FXML public Pane timesMenu;
    @FXML public Pane compePaneScroll;
    @FXML public Pane timesMenuCompe;
    @FXML public TextField sessionName;
    @FXML public Button champBtt;
    @FXML public Pane optionDemoPane;
    @FXML public Button signOutBtt;
    @FXML private Button editProfileBtt;
    @FXML public Pane demoProfilePane;
    @FXML public Pane profilePage;
    @FXML public AnchorPane panelTimesScroll;
    @FXML private TextField levelProfileTxt;
    @FXML private Label userProfiletxt;
    @FXML private ImageView proProfileImg;
    @FXML private Label proProfileTxt;
    @FXML private Label dateProfileTxt;
    @FXML private Label nameSessionLabel;
    @FXML private ScrollPane timesMenuScroll;
    @FXML private ScrollPane timesCompeScroll;
    @FXML public ImageView imageProfileGeneral;
    @FXML public ImageView imageProfileEditGeneral;


    // ATRIBUTOS SEMAFOROS PARA ABRIR Y CERRAR DESDE EL MISMO BOTON
    boolean pulsarOption = false;
    boolean pulsarSettings = false;
    boolean pulsarProfile = false;
    static ArrayList<String> categories = new ArrayList(); // ARRAYLIST CON CATEGORIAS
    static int min = 0;
    static int seg = 0;
    static int cent = 0;


                            /********* GENERAL METHODS ***********/
    @FXML void manejarTeclaPresionada() {

    }

    @FXML void manejarTeclaSoltada() {

    }

    @FXML void onExitAction() {
        int opcion = JOptionPane.showConfirmDialog(null,
                "¿Está seguro de que desea salir?", "Confirmación", JOptionPane.YES_NO_OPTION);
        if (opcion == JOptionPane.YES_OPTION) {
            System.exit(0); // CERRAR APLICACIÓN
        }
    } // SALIR DE LA APLICACIÓN

    @FXML void onProfileAction() {
        // CUANDO PULSES AL PERFIL SE CIERRAN LAS VENTANAS EMERGENTES DE SETTING Y OPTION
        settingMenu.setVisible(false);
        onCloseOptionAction();
        if(!pulsarProfile){
            if(StartCtrller.isDemo){
                demoProfilePane.setVisible(true);
                pulsarProfile = true;
            } else {
                int level = CubeUserDAO.levelNumber(CacheStatic.cubeUser.getMail());
                levelProfileTxt.setText(String.valueOf(level));
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
    void onEditProfileAction() {
        onSettingsMenuAction();
    }

                                /********* TIMES MENU ***********/
    @FXML void onOpenMenuAction() {
        // CIERRAR TODAS LAS VENTANAS EMERGENTES QUE HAYA Y MOSTRAR LA QUE SE PULSO
        if(StartCtrller.isDemo){
            demoProfilePane.setVisible(false);
        } else {
            profilePage.setVisible(false);
        }
        onCloseOptionAction();
        settingMenu.setVisible(false);

        timesMenu.setVisible(true);
        closeBtt.setVisible(true);
        nameSessionLabel.setText(SessionCtrller.isUsing);
        if(!StartCtrller.isDemo){
            mostrarTiempos();
        } else {
            nameSessionLabel.setText("DEMO MODE");
            panelTimesScroll.getChildren().add(messageDemoPage());
        } // SI NO ES DEMO SE MUESTRAN SUS TIEMPOS, SI NO, SE MUESTRA UN MENSAJE
    }// SE ABRE EL PANEL DE TIEMPOS Y EL BOTON DE CERRAR

    public VBox messageDemoPage () {
        VBox mainContainer = new VBox();
        mainContainer.setSpacing(10);

        Label label1 = new Label(" To access all features\n" +
                " of the profile, please\n" +
                " register or log in.\n" +
                " Thank you!");
        label1.setStyle("-fx-font-size: 17px; -fx-text-fill: #6d7b64;");
        mainContainer.getChildren().add(label1);
        mainContainer.setStyle("-fx-background-color :  #325743");
        return mainContainer;
    }

    public void mostrarTiempos() {
        if (SessionCtrller.isUsing == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No hay sesion en uso.");
            alert.setHeaderText("¡ERROR!");
            alert.setContentText("Por favor, elige una sesion para poder guardar los tiempos.");
            alert.showAndWait();
        } else {
            int contador = 0;

            // CONTENEDOR PRINCIPAL
            VBox mainContainer = new VBox();
            mainContainer.setSpacing(10);

            // ITERAR SOBRE LA LISTA DE TIEMPOS
            for (TimeTraining timeTraining : TimeTrainingDAO.listTimesTraining(SessionCtrller.idSessions)) {
                contador++;

                // CREAR UN HBOX PARA CADA TIEMPO
                HBox timeBox = new HBox();
                timeBox.setSpacing(10);

                // LABELS PARA LOS TIEMPOS
                Label label1 = new Label("  " + contador);
                Label label2 = new Label(timeTraining.getMinutes() + ":" + timeTraining.getSeconds());


                // BOTONES
                Button plusTwo = new Button("+2");
                Button deleteTime = new Button("X");

                // ESTILOS
                label1.setStyle("-fx-font-size: 17px; -fx-text-fill: black;");
                label2.setStyle("-fx-font-size: 17px; -fx-text-fill: black;");
                plusTwo.setStyle("-fx-background-color: #6d7b64; -fx-font-family: DejaVu Sans; -fx-font-weight: bold; -fx-font-size: 15px;");
                deleteTime.setStyle("-fx-background-color: #a82f2a; -fx-font-family: DejaVu Sans; -fx-font-weight: bold; -fx-font-size: 15px;");

                // EVENTOS PARA LOS BOTONES
                plusTwo.setOnAction(e -> {
                    TimeTrainingDAO.plusTwoTime(timeTraining.getMinutes(), timeTraining.getSeconds());
                });

                deleteTime.setOnAction(e -> {

                });

                // AGREGAR ELEMENTOS AL HBOX
                timeBox.getChildren().addAll(label1, label2, plusTwo, deleteTime);

                // AGREGAR EL HBOX AL CONTENEDOR PRINCIPAL
                mainContainer.getChildren().add(timeBox);
                mainContainer.setStyle("-fx-background-color :  #325743");
            }


            // AGREGAR EL CONTENEDOR PRINCIPAL AL SCROLLPANE
            timesMenuScroll.setContent(mainContainer);
            timesMenuScroll.setFitToWidth(true);
        }
    }

    @FXML void onCloseMenuAction() {
        timesMenu.setVisible(false);
        closeBtt.setVisible(false);
    }// SE CIERRA EL PANEL DE TIEMPOS Y EL BOTON DE CERRAR

    @FXML void onCloseMenuCompeAction(){
        timesMenuCompe.setVisible(false);
        closeTimesCompe.setVisible(false);
        openTimesCompe.setVisible(true);
    }
    @FXML void onOpenMenuCompeAction(){
        timesMenuCompe.setVisible(true);
        closeTimesCompe.setVisible(true);
        openTimesCompe.setVisible(false);
        if(!StartCtrller.isDemo){
            timesCompe();
        } else {
            compePaneScroll.getChildren().add(messageDemoCompe());
        } // SI NO ES DEMO SE MUESTRAN SUS TIEMPOS, SI NO, SE MUESTRA UN MENSAJE
    }
    public VBox messageDemoCompe () {
        VBox mainContainer = new VBox();
        mainContainer.setSpacing(10);

        Label label1 = new Label("         DEMO MODE    \n");
        Label label2 = new Label(" To access all features\n" +
                " of the profile, please\n" +
                " register or log in.\n" +
                " Thank you!");
        label1.setStyle("-fx-underline: true; -fx-font-family: DejaVu Sans; -fx-font-weight: bold; -fx-font-size: 21px;");
        label2.setStyle("-fx-font-size: 17px; -fx-text-fill: #6d7b64;");
        mainContainer.getChildren().addAll(label1, label2);
        mainContainer.setStyle("-fx-background-color :  #325743");
        return mainContainer;
    }

    public void timesCompe () {
        int contador = 0;

        // CONTENEDOR PRINCIPAL
        VBox mainContainer = new VBox();
        mainContainer.setSpacing(10);

        // ITERAR SOBRE LA LISTA DE TIEMPOS
        for (TimeCompetition timeCompetition : TimeCompetitionDAO.listTimesCompe(CubeUserDAO.selectIdUser(CacheStatic.cubeUser.getMail()))) {
            contador++;

            // CREAR UN HBOX PARA CADA TIEMPO
            HBox timeBox = new HBox();
            timeBox.setSpacing(10);

            // LABELS PARA LOS TIEMPOS
            Label label1 = new Label("  " + contador + ")");
            Label label2 = new Label(timeCompetition.getMinutes1() + ":" + timeCompetition.getSeconds1());
            Label label3 = new Label("   |   ");
            Label label4 = new Label(timeCompetition.getMinutes2() + ":" + timeCompetition.getSeconds2());

            // ESTILOS
            label1.setStyle("-fx-font-size: 17px; -fx-text-fill: black;");
            label2.setStyle("-fx-font-size: 17px; -fx-text-fill: black;");
            label3.setStyle("-fx-font-size: 17px; -fx-text-fill: black;");
            label4.setStyle("-fx-font-size: 17px; -fx-text-fill: black;");


            // AGREGAR ELEMENTOS AL HBOX
            timeBox.getChildren().addAll(label1, label2,label3, label4);

            // AGREGAR EL HBOX AL CONTENEDOR PRINCIPAL
            mainContainer.getChildren().add(timeBox);
            mainContainer.setStyle("-fx-background-color :  #325743");
        }


        // AGREGAR EL CONTENEDOR PRINCIPAL AL SCROLLPANE
        timesCompeScroll.setContent(mainContainer);
        timesCompeScroll.setFitToWidth(true);
    }


                                /********* OPTIONS MENU ***********/

    @FXML void onOptionAction() {
        // SE CIERRAN TODAS LAS VENTANAS EMERGENTES DE PERFIL Y SETTING
        if(StartCtrller.isDemo){
            demoProfilePane.setVisible(false);
        } else {
            profilePage.setVisible(false);
        }
        settingMenu.setVisible(false);

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
    @FXML void onSignOutAction() {
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
    @FXML void onSettingsAction() {
        // CIERRAR TODAS LAS VENTANAS EMERGENTES QUE HAYA Y MOSTRAR LA QUE SE PULSO
        if(StartCtrller.isDemo){
            demoProfilePane.setVisible(false);
        } else {
            profilePage.setVisible(false);
        }
        onCloseOptionAction();

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

    @FXML void onCompeAction() {
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

    @FXML void onSessionAction() {
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


    @FXML void onTimerAction() {
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

    @FXML void onChampAction() {

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


    public static Timeline timeline1; // CRONOMETRO PARA MOSTRAR EL TIEMPO DE CUBER1
    public static Timeline timeline2; // CRONOMETRO PARA MOSTRAR EL TIEMPO DE CUBER2

    public static void start(Label chrono) {
        // CUANDO PULSE DE NUEVO SE REINICIARAN LOS CRONOMETROS
        min = 0;
        seg = 0;
        cent = 0;
        //SE CREA UNA INSTANCIA DE TIMELINE PARA CONTROLAR EL TIEMPO
        // EL KEYFRAME SE EJECUTARA CADA 10 MILISEGUNDOS Y HARA QUE EL CRONOMETRO SE INICIE
        timeline1 = new Timeline(new KeyFrame(Duration.millis(10), event1 -> {
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
                // DETENER EL TIMELINE CORRESPONDIENTE
                if (chrono.getId().equals("chrono1Label")) {
                    timeline1.stop();
                } else if (chrono.getId().equals("chrono2Label")) {
                    timeline2.stop();
                }
            }
            chrono.setText(timeString); // MOSTRAR EL TIEMPO
        }));

        timeline1.setCycleCount(Timeline.INDEFINITE); // DE TIEMPO INDEFINIDO
        timeline1.play(); // COMENZAMOS NUESTRO TIMELINE
        if (chrono.getId().equals("chrono2Label")) {
            // SI HAY UN SEGUNDO CRONOMETRO (COMPETITION) SE LE ASIGNA EL VALOR DEL TIMELINE1 A TIMELINE2
            timeline2 = timeline1;
        }
    }

    public static void parar(String chrono) {
        // DETENER EL TIMELINE DEL CHRONOMETRO QUE HAYA PULSADO
        if (chrono.equals("chrono1Label")) {
            timeline1.stop();
        } else if (chrono.equals("chrono2Label")) {
            timeline2.stop();
        }
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

    @FXML
    void onUpdateImgAction(){
        imageProfileEditGeneral.setImage(CubeUserDAO.imgUrlSelect(CacheStatic.cubeUser.getMail()));
        imageProfileGeneral.setImage(CubeUserDAO.imgUrlSelect(CacheStatic.cubeUser.getMail()));
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}

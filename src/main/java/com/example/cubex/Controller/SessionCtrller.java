package com.example.cubex.Controller;

import com.example.cubex.DAO.SessionDAO;
import com.example.cubex.Database.DatabaseConnection;
import com.example.cubex.model.CacheStatic;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import javax.print.attribute.standard.PrinterName;
import javax.swing.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class SessionCtrller extends CodeGeneral implements Initializable {
    @FXML
    private Button addSessionBtt;
    @FXML
    private Button cancelSessionBtt;
    @FXML
    private Button createSessionBtt;
    @FXML
    private Button reloadBtt;
    @FXML
    private Pane nameSession;
    @FXML
    private AnchorPane paneScroll;

    @FXML
    private Pane demoProfilePane;
    @FXML
    private Pane profilePage;
    @FXML
    private Pane SessionPane;
    @FXML
    private ComboBox categoriesCB;
    @FXML
    private ScrollPane scroll;
    private double nextPaneY = 10; // Posición Y del próximo panel
    private static int contadorCreate = 0; // CONTADOR PARA VER CUANTAS VECES CREA UNA SESION EL USUARIO DEMO
    LocalDate localDate = LocalDate.now();
    String category, sessionToDelete;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        CodeGeneral.onFalseMenus(demoProfilePane, profilePage, settingMenu, optionMenu, optionDemoPane, nameSession);
        sessionName.setPromptText("Name of Session");
        sessionName.setStyle("-fx-prompt-text-fill: #9B9B9B; -fx-background-color: #b1c8a3;");
        paneScroll.prefWidth(50);
        CodeGeneral.cubeCategory(categoriesCB);
        if(!StartCtrller.isDemo){
            // SI NO ES UN USUARIO DEMO, SE CARGAN LAS SESIONES DEL USUARIO
            loadSession();
            reloadBtt.setVisible(true);
        } else {
            reloadBtt.setVisible(false);
        }
    }


    @FXML
    void onAddSessionAction(ActionEvent event) {
        demoProfilePane.setVisible(false);
        nameSession.setVisible(true);
    }

    @FXML
    void onCreateSessionAction() {
        if (sessionName.getText().isEmpty() || categoriesCB.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Campos vacíos.");
            alert.setHeaderText("¡ERROR!");
            alert.setContentText("Por favor, completa todos los campos antes de continuar.");
            alert.showAndWait();
        } else {
            if (StartCtrller.isDemo) {
                contadorCreate++;
            }
            if (contadorCreate > 1) {
                // EL USUARIO QUE ENTRE DESDE LA DEMO NO PODRA CREAR MAS SESIONES, SOLO LE APARECERA LA SESION GENERAL
                demoProfilePane.setLayoutX(144);
                demoProfilePane.setLayoutY(101);
                demoProfilePane.setVisible(true);
                nameSession.setVisible(false);
            } else {
                category = String.valueOf(categoriesCB.getSelectionModel().getSelectedItem());
                nameSession.setVisible(false);
                if (!StartCtrller.isDemo) {
                    int idType = SessionDAO.insertIdTypeSession(category);
                    int idUser = SessionDAO.insertIdUserSession(CacheStatic.cubeUser.getMail());
                    if (SessionDAO.insertSession(idUser, sessionName.getText(), localDate, idType)) {
                        paneScroll.getChildren().add(createSessions(sessionName.getText(), category));
                    } else {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Sesion fallida.");
                        alert.setHeaderText("Creación de sesion fallida");
                        alert.setContentText("No se ha podido crear la sesion correctamente");
                        alert.showAndWait();
                    }
                } else {
                    //SE CREA LA SESION PERO NO SE GUARDA
                    paneScroll.getChildren().add(createSessions(sessionName.getText(), category));
                }
            }
        }

    } // CREAR LAS SESIONES CON UN PANEL DE SESION


    public Pane createSessions(String sessionName, String category) {
        Pane newPane = new Pane();
        newPane.setLayoutX(10); // ESPACIADO HORIZONTAL
        newPane.setLayoutY(nextPaneY);
        // TAMANIO DEL PANEL
        newPane.setPrefHeight(187);
        newPane.setPrefWidth(220);
        newPane.setStyle("-fx-background-color:  #325743; -fx-background-radius: 24; -fx-border-color: black; -fx-border-radius: 24;");

        // SE CREAN LOS ELEMENTOS
        Label createSessionLabel = new Label(sessionName);
        createSessionLabel.setLayoutX(79);
        createSessionLabel.setLayoutY(8);
        createSessionLabel.setStyle("-fx-underline: true; -fx-font-family: DejaVu Sans; -fx-font-weight: bold; -fx-font-size: 21px;");

        Label typeCubeLabel = new Label("Cube : " + category);
        typeCubeLabel.setLayoutX(14);
        typeCubeLabel.setLayoutY(39);
        typeCubeLabel.setStyle("-fx-font-family: DejaVu Sans; -fx-font-size: 15px;");

        Button details = new Button("DETAILS");
        details.setLayoutX(17);
        details.setLayoutY(68);
        details.setPrefWidth(186);
        details.setPrefHeight(31);
        details.setStyle("-fx-background-color: #b1c8a3; -fx-font-family: DejaVu Sans; -fx-font-weight: bold; -fx-font-size: 15px;");

        RadioButton manualRadio = new RadioButton("MANUAL");
        manualRadio.setLayoutX(17);
        manualRadio.setLayoutY(110);
        manualRadio.setStyle("-fx-font-family: DejaVu Sans; -fx-font-weight: bold; -fx-font-size: 15px;");

        RadioButton autoRadio = new RadioButton("AUTO");
        autoRadio.setLayoutX(118);
        autoRadio.setLayoutY(110);
        autoRadio.setStyle("-fx-font-family: DejaVu Sans; -fx-font-weight: bold; -fx-font-size: 15px;");

        ToggleGroup manualAuto = new ToggleGroup();
        manualRadio.setToggleGroup(manualAuto);
        autoRadio.setToggleGroup(manualAuto);
        autoRadio.setSelected(true);

        Button use = new Button("USE");
        use.setLayoutX(17);
        use.setLayoutY(143);
        use.setPrefWidth(99);
        use.setPrefHeight(31);
        use.setStyle("-fx-background-color: #74cc5e; -fx-font-family: DejaVu Sans; -fx-font-weight: bold; -fx-font-size: 15px;");

        Button delete = new Button("DELETE");
        delete.setLayoutX(118);
        delete.setLayoutY(143);
        delete.setPrefWidth(84);
        delete.setPrefHeight(31);
        delete.setStyle("-fx-background-color: #a82f2a; -fx-font-family: DejaVu Sans; -fx-font-weight: bold; -fx-font-size: 15px;");

        // PARA PODER ELIMINAR DE LA BASE DE DATOS LA SESION, PRIMERO SE LE ASIGNA EL NOMBRE DE LA SESION A USERDATA
        // USERDATA ES UNA PROPIEDAD QUE PERMITE ADJUNTAR CUALQUIER OBJETO A UN NODO, EN ESTE CASO, EL BOTON DELETE AL LABEL
        // QUE CONTIENE EL NOMBRE DE LA SESSION
        // COMO ESTOY CREANDO UN BOTON POR CADA SESION Y EL METODO setUserData SE LLAMA DENTRO DE ESTE CICLO, CADA BOTON DELETE
        // TENDRA ASOCIADO UN NOMBRE DE LA SESION YA QUE CADA INSTANCIA DEL BOTON ES INDEPENDIENTE Y ASI NO SE SOBREESCRIBEN VALORES
        delete.setUserData(sessionName);

        delete.setOnAction(event -> {
            // OBTENER EL NODO QUE DISPARO EL EBVENTO, SE OBTIENE LE VALOR USERDATA, RECUPERANDO ASI EL NOMBRE DE LA SESION
            // PARA SABER QUE SESION ELIMINAR
            sessionToDelete = (String) ((Button) event.getSource()).getUserData();
            onDeleteSession(sessionToDelete); // LLAMAR AL METODO PARA ELIMINARLO
            Button deleteButton = (Button) event.getSource(); // OBTENER EL BOTON DELETE QUE ACTIVO EL EVENTO
            Pane sessionPane = (Pane) deleteButton.getParent(); // OBTENER EL PANEL DE SESION QUE CONTIENE EL BOTON DELETE
            paneScroll.getChildren().remove(sessionPane);
        }); // ELIMINAR EL PANEL DEL SCROLLPANE


        // SE AGREGAN TODOS AL NUEVO PANEL
        newPane.getChildren().addAll(
                typeCubeLabel, autoRadio, manualRadio,
                createSessionLabel,
                details, use, delete
        );

        // SE AUMENTA LA POSICION Y EN EL PROXIMO PANEL
        nextPaneY += newPane.getPrefHeight() + 10;

        // SI EL NUEVO PANEL ESTA FUERA DEL AREA VISIBLE, SE AJUSTA EL TAMAÑO DEL PANE DEL SCROLLPANE
        if (nextPaneY > paneScroll.getPrefHeight()) {
            paneScroll.setPrefHeight(nextPaneY + 10); // 10 es el espacio adicional para evitar bordes cortados
        }
        return newPane;
    } // CREAR UN NUEVO PANEL DE SESSION

    public void loadSession() {
        int minIdSession = SessionDAO.minIdSession(CacheStatic.cubeUser.getMail());
        int maxIdSession = SessionDAO.maxIdSession(CacheStatic.cubeUser.getMail());
        String sql = "SELECT NAME_SESSION, ID_TYPE FROM SESSIONS WHERE ID_SESSION = ?";
        String nameSession;
        int idType;
        try {
            Connection con = DatabaseConnection.conectar();
            for (int i = minIdSession; i <= maxIdSession; i++) {
                PreparedStatement statement = con.prepareStatement(sql);
                statement.setInt(1, i);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    nameSession = resultSet.getString("NAME_SESSION");
                    idType = resultSet.getInt("ID_TYPE");
                    paneScroll.getChildren().add(createSessions(nameSession, SessionDAO.nameCategory(idType)));
                }
            }
            con.close();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error de conexión.");
            alert.setHeaderText("¡ERROR!");
            alert.setContentText("Error al conectar a la base de datos: " + e.getMessage());
            alert.showAndWait();
        }
    } // CARGAR LAS SESIONES QUE TENIA ESE USUARIO

    @FXML
    static void onDeleteSession(String nameSession) {
        int opcion = JOptionPane.showConfirmDialog(null,
                "¿Está seguro de que desea eliminar la sesión?", "Confirmación", JOptionPane.YES_NO_OPTION);
        if (opcion == JOptionPane.YES_OPTION) {
            if (StartCtrller.isDemo) {
                contadorCreate = 0;
            } else {
                if (SessionDAO.deleteSession(nameSession)) {
                    System.out.println(nameSession);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Sesion eliminada.");
                    alert.setHeaderText("Eliminación exitosa");
                    alert.setContentText("Se ha eliminado la sesion correctamente");
                    alert.showAndWait();
                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Sesion fallida.");
                    alert.setHeaderText("Eliminación fallida");
                    alert.setContentText("No se ha podido eliminar la sesion correctamente");
                    alert.showAndWait();
                }
            }
        }
    } // ELIMINAR SESION

    @FXML
    void onCancelSessionCreate(ActionEvent event) {
        int opcion = JOptionPane.showConfirmDialog(null,
                "¿Estás seguro de que quieres cancelar la creación de la sesión?\n" +
                        "Todos los datos ingresados hasta ahora se perderán.", "Confirmación", JOptionPane.YES_NO_OPTION);
        if (opcion == JOptionPane.YES_OPTION) {
            nameSession.setVisible(false);
        }
    } // CANCELAR SESSION

    @FXML
    void onReloadAction(ActionEvent event) {
        paneScroll.getChildren().clear();
        nextPaneY = 10;
        loadSession();
    } // ACTUALIZAR SESIONES (POR SI HA BORRADO NO TENER UN HUECO VACIO



}

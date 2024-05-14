package com.example.cubex.Controller;

import com.example.cubex.DAO.AverageDAO;
import com.example.cubex.DAO.MemberDAO;
import com.example.cubex.DAO.SessionDAO;
import com.example.cubex.Database.DatabaseConnection;
import com.example.cubex.model.CacheStatic;
import com.example.cubex.model.Member;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.print.PageOrientation;
import javafx.scene.Node;
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
    public static String isUsing;
    private double nextPaneY = 10; // POSICION Y DEL PROXIMO PANEL
    double newHeight = 0; // NUEVA POSICION Y SI SE ELIMINA ALGUN PANEL
    private static int contadorCreate = 0; // CONTADOR PARA VER CUANTAS VECES CREA UNA SESION EL USUARIO DEMO

    LocalDate localDate = LocalDate.now();
    String category, sessionToDelete, sessionToUse;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        CodeGeneral.onFalseMenus(demoProfilePane, profilePage, settingMenu, optionMenu, optionDemoPane, nameSession);
        sessionName.setPromptText("Name of Session");
        sessionName.setStyle("-fx-prompt-text-fill: #9B9B9B; -fx-background-color: #b1c8a3;");
        paneScroll.prefWidth(50);
        CodeGeneral.cubeCategory(categoriesCB);
        if (!StartCtrller.isDemo) {
            // SI NO ES UN USUARIO DEMO, SE CARGAN LAS SESIONES DEL USUARIO
            loadSession();
        }
    }


    @FXML
    void onAddSessionAction(ActionEvent event) {
        demoProfilePane.setVisible(false);
        nameSession.setVisible(true);
    }
    public static int idSessions;

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
                    // SI ES USUARIO SE LIMITA A 15 SESIONES, SI ES MEMBERS PUEDE HACER INFINITAS
                    if (SessionDAO.numberSession(CacheStatic.cubeUser.getMail()) == 15 && !MemberDAO.selectMember(CacheStatic.cubeUser.getMail())
                            && !StartCtrller.isDemo) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Sesion fallida.");
                        alert.setHeaderText("Has sobrepasado el limite de sesiones por usuario");
                        alert.setContentText("¡Conviertete en miembro para disfrutar de todas las funcionalidades de la aplicación!");
                        alert.showAndWait();
                    } else {
                        int idType = SessionDAO.insertIdTypeSession(category);
                        int idUser = SessionDAO.insertIdUserSession(CacheStatic.cubeUser.getMail());

                        if (SessionDAO.insertSession(idUser, sessionName.getText(), localDate, idType)) {
                            paneScroll.getChildren().add(createSessions(sessionName.getText(), category));
                            idSessions = SessionDAO.selectNumberSession(sessionName.getText());
                            AverageDAO.createAverageSession(idSessions);
                        } else {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Sesion fallida.");
                            alert.setHeaderText("Creación de sesion fallida");
                            alert.setContentText("No se ha podido crear la sesion correctamente");
                            alert.showAndWait();
                        }
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

        use.setUserData(sessionName);
        use.setOnAction(event -> {
            sessionToUse = (String) ((Button) event.getSource()).getUserData(); // SELECCIONAR CUAL ESTA USUANDO
            isUsing = sessionName; // SE ESTABLECE EL NOMBRE DE LA SESION QUE QUIERA USAR
        });
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
            paneScroll.getChildren().remove(sessionPane); // SE ELIMINA
            onReloadAction(); // CUANDO SE ELIMINA SE ACTUALIZA LAS SESIONES
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
            paneScroll.setPrefHeight(nextPaneY + 10); // ESPACIO ADIVIONAL PRA EVITAR BORDES CORTADOS
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
                if (AverageDAO.deleteAverage(nameSession) && SessionDAO.deleteSession(nameSession) ) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Sesion eliminada.");
                    alert.setHeaderText("Eliminación exitosa");
                    alert.setContentText("Se ha eliminado la sesion correctamente");
                    alert.showAndWait();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
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
                        "Todos los datos ingresados se perderán.", "Confirmación", JOptionPane.YES_NO_OPTION);
        if (opcion == JOptionPane.YES_OPTION) {
            nameSession.setVisible(false);
        }
    } // CANCELAR SESSION

    @FXML
    void onReloadAction() {
        if (!StartCtrller.isDemo) {
            loadSession();
        }
        paneScroll.setStyle("-fx-background-color :  #325743");
        nextPaneY = 10;
        paneScroll.setPrefHeight(alturaPanel()); // SE ESTABLECE LA NUEVA ALTURA


    } // ACTUALIZAR SESIONES (POR SI HA BORRADO NO TENER UN HUECO VACIO

    public double alturaPanel() {
        boolean isPanel = false;
        int contadorPanels = 0; // SI HAY SOLO UN PANEL SE HACE EL PANEL Y UN POCO DE LO QUE QUEDA
        double restoEntrePanel; // SI HAY UN PANEL, SE COGE LA ALTURA DE ESE PANEL MAS LA RESTA ENTRE
        // EL TOTAL DEL PANEL PRINCIPAL Y LA ALTURA DEL PANEL DE SESSION
        // PARA DARLE UNA ALTURA NUEVA Y QEU NO QEUDE EL ESPACIO DEL PANEL ELIMINADO :
        // SE RECORRE LA LISTA DE NODOS QUE TIENE EL PANE SCROLL, COMO YA NO TIENE EL PANEL ELIMINADO, ENTONCES NO
        // VA A CONTAR LA ALTURA DE ESE PANEL, ENTONCES QUEDARIA LA ALTURA DE SOLO LOS PANELES DE SESIONES QEU NO
        // ESTAN ELIMINADOS
        for (Node child : paneScroll.getChildren()) {
            // POR CADA HIJO SE VERIFICA SI ES UNA INSTANCIA DEL PANEL
            if (child instanceof Pane) {
                // SI EL NODO ES UN HIJO , SE OBTIENE SU ALTURA Y SE LE SUMA A LA NUEVA ALTURA CON UN ESPACIO ADICIONAL
                newHeight += ((Pane) child).getPrefHeight() + 10; // 10 es el espacio adicional entre paneles
                isPanel = true;
                contadorPanels++;
            }
        }

        if (isPanel && contadorPanels == 1) {
            restoEntrePanel = 294 - newHeight;
            return newHeight + restoEntrePanel;
        } else if (isPanel && contadorPanels > 1) {
            return newHeight;
        } else {
            return 294; // SI NO HAY NINGUN PANEL SE QUEDA EL TAMAÑO ESTANDAR
        }

    }


}

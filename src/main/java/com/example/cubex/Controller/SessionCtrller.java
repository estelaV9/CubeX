package com.example.cubex.Controller;

import com.example.cubex.DAO.*;
import com.example.cubex.Database.DatabaseConnection;
import com.example.cubex.Validator.Validator;
import com.example.cubex.model.CacheStatic;
import com.example.cubex.model.TimeTraining;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javax.swing.*;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class SessionCtrller extends CodeGeneral implements Initializable {
    @FXML private Pane nameSession;
    @FXML private AnchorPane paneScroll;
    @FXML private Pane demoProfilePane;
    @FXML private Pane profilePage;
    @FXML private ComboBox categoriesCB;
    @FXML private ComboBox categoriesCB1;
    @FXML private AnchorPane panelAllTimesScroll;
    @FXML private ScrollPane allTimesScroll;
    @FXML private ScrollPane scroll;
    @FXML private Label DetailsAvg;
    @FXML private Label DetailsNameSessionLabel;
    @FXML private Label DetailsPbTime;
    @FXML private Label DetailsTotalTimes;
    @FXML private Label DetailsWorstTime;
    @FXML private Button closeDetailsPane;
    @FXML private Pane detailsPane;
    @FXML private Label loginMessage1;
    @FXML private Label DetailsNameSessionLabel1;
    @FXML private Pane detailsManualPane;
    @FXML private Label loginMessage11;
    @FXML private Label scrambleLabel;
    @FXML private TextField detailTimeTxt;

    int idSession;
    public static String isUsing; // SABER QUE SESSION SE ESTA USANDO
    private double nextPaneY = 10; // POSICION Y DEL PROXIMO PANEL PARA LOS PANELES DE LAS SESSIONES
    double newHeight = 0; // NUEVA POSICION Y SI SE ELIMINA ALGUN PANEL
    private static int contadorCreate = 0; // CONTADOR PARA VER CUANTAS VECES CREA UNA SESION EL USUARIO DEMO
    public static int idSessions;
    LocalDate localDate = LocalDate.now();
    String category, sessionToDelete, sessionToUse, SessionDetails;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        CodeGeneral.onFalseMenus(demoProfilePane, profilePage, settingMenu, optionMenu, optionDemoPane, nameSession);
        sessionName.setPromptText("Name of Session");
        sessionName.setStyle("-fx-prompt-text-fill: #9B9B9B; -fx-background-color: #b1c8a3;");
        paneScroll.prefWidth(50);
        CuberTypeDAO.cubeCategory(categoriesCB);
        CuberTypeDAO.cubeCategory(categoriesCB1);

        if (!StartCtrller.isDemo) {
            loadSession(); // SI NO ES UN USUARIO DEMO, SE CARGAN LAS SESIONES DEL USUARIO
            allTimesScroll.setVisible(true);
            categoriesCB1.setVisible(true);
            if(CubeUserDAO.selectUrl(CacheStatic.cubeUser.getMail())){
                onUpdateImgAction();
            } // SI LA IMAGEN SE MODIFICO ENTONCES SE CARGA LA IMAGEN, SOLO SE CARGA EN USER NO EN USER DEMO
        } else {
            allTimesScroll.setVisible(false);
            categoriesCB1.setVisible(false);
        } // SI ES USER DEMO NO SE LE PERMITIRA VER LOS TIEMPOS SEGUN LOS CUBOS QUE HA HECHO
        detailsPane.setVisible(false);
        detailsManualPane.setVisible(false);
    }

    @FXML
    void onShowTimes() {
        nameSession.setVisible(false);
        String category = String.valueOf(categoriesCB1.getSelectionModel().getSelectedItem());
        int idCategory = SessionDAO.idCategory(category);
        int contador = 0;

        // CONTENEDOR PRINCIPAL
        VBox mainContainer = new VBox();
        mainContainer.setSpacing(10);

        // ITERAR SOBRE LA LISTA DE TIEMPOS
        for (TimeTraining timeTraining : TimeTrainingDAO.listTimesCategory(idCategory, CubeUserDAO.selectIdUser(CacheStatic.cubeUser.getMail()))) {
            contador++;

            // CREAR UN HBOX PARA CADA TIEMPO
            HBox timeBox = new HBox();
            timeBox.setSpacing(10);

            // LABELS PARA LOS TIEMPOS
            Label label1 = new Label("  " + contador + ")");
            Label label2 = new Label(timeTraining.getMinutes() + ":" + timeTraining.getSeconds());
            Label label3 = new Label(String.valueOf(timeTraining.getRegistrationDate()));

            // ESTILOS
            label1.setStyle("-fx-font-size: 17px; -fx-text-fill: black;");
            label2.setStyle("-fx-font-size: 17px; -fx-text-fill: black;");
            label3.setStyle("-fx-font-size: 17px; -fx-text-fill: black;");

            // AGREGAR ELEMENTOS AL HBOX
            timeBox.getChildren().addAll(label1, label2, label3);

            // AGREGAR EL HBOX AL CONTENEDOR PRINCIPAL
            mainContainer.getChildren().add(timeBox);
        }
        mainContainer.setStyle("-fx-background-color : #204338");
        // AGREGAR EL CONTENEDOR PRINCIPAL AL SCROLLPANE
        allTimesScroll.setContent(mainContainer);
        allTimesScroll.setFitToWidth(true);
    } // MOSTRAR LOS TIEMPOS DE LAS SESIONES SEGUN LA CATEGORIA

    @FXML void onAddSessionAction() {
        nameSession.setVisible(true);
    } // MOSTRAR POPUP PARA CREAR SESSION

    @FXML void onCloseLimitAccess(ActionEvent event) {
        demoProfilePane.setVisible(false);
    } // CERRAR EL PANEL DE DEMO

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
            } // SI ES USER DEMO, SE CUENTA LA SESSION QUE HA CREADO
            if (contadorCreate > 1 && StartCtrller.isDemo) {
                // EL USER DEMO NO PODRA CREAR MAS SESIONES, SOLO PODRA CREAR UNA
                demoProfilePane.setLayoutX(144);
                demoProfilePane.setLayoutY(101);
                demoProfilePane.setVisible(true);
                nameSession.setVisible(false);
            } else {
                category = String.valueOf(categoriesCB.getSelectionModel().getSelectedItem());
                nameSession.setVisible(false);
                if (!StartCtrller.isDemo) {
                    // SI ES USUARIO SE LIMITA A 15 SESIONES, SI ES MEMBERS PUEDE HACER INFINITAS
                    if (SessionDAO.numberSession(CacheStatic.cubeUser.getMail()) == 15 && !MemberDAO.selectMember(CacheStatic.cubeUser.getMail())) {
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
        Pane newPane = new Pane(); // SE CREA UN NUEVO PANEL PARA LA SESSION
        newPane.setLayoutX(10); // ESPACIADO HORIZONTAL
        newPane.setLayoutY(nextPaneY); // ESPACIADO VERTICAL
        // TAMAÑO DEL PANEL
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

        details.setOnAction(event -> {
            detailsPane.setVisible(true);
            nameSession.setVisible(false);
            if(!StartCtrller.isDemo) {
                idSession = SessionDAO.selectNumberSession(sessionName);
                // ACTUALIZAR TODOS LAS COLUMNAS DE AVERAGE DE ESA SESSION
                AverageDAO.worstMinutes(idSession);
                AverageDAO.worstSecond(idSession);
                AverageDAO.pbMinutes(idSession);
                AverageDAO.pbSecond(idSession);
                AverageDAO.countTimes(idSession);
                AverageDAO.avgMinutes(idSession);
                AverageDAO.avgSeconds(idSession);

                // MOSTRAR LOS DATOS
                DetailsNameSessionLabel.setText(sessionName.toUpperCase());
                DetailsTotalTimes.setText(String.valueOf(AverageDAO.timesTotalSession(idSession)));
                DetailsWorstTime.setText(AverageDAO.worstMinutesTotalSession(idSession) + ":" + AverageDAO.worstSecondTotalSession(idSession));
                DetailsPbTime.setText(AverageDAO.pbMinutesTotalSession(idSession) + ":" + AverageDAO.pbSecondTotalSession(idSession));
                DetailsAvg.setText(AverageDAO.avgMinutesTotalSession(idSession) + ":" + AverageDAO.avgSecondTotalSession(idSession));
            } else {
                DetailsNameSessionLabel.setText(sessionName);
                DetailsTotalTimes.setText(String.valueOf(0));
                DetailsWorstTime.setText(0 + ":" + 00.00);
                DetailsPbTime.setText(0 + ":" + 00.00);
                DetailsAvg.setText(0 + ":" + 00.00);
            } // SI ES USUARIO DEMO SE SETTEA TIEMPOS EN 0 Y NO SE PODRA CAMBIAR
        });

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

        use.setOnAction(event -> {
            isUsing = sessionName; // SE ESTABLECE EL NOMBRE DE LA SESION QUE SE VA A USAR
            idSessions = SessionDAO.selectNumberSession(isUsing);
            if(manualAuto.getSelectedToggle() == manualRadio){
                detailsManualPane.setVisible(true);
                DetailsNameSessionLabel1.setText(sessionName);
                scrambleLabel.setText(CodeGeneral.scramble());
            }
        });

        Button delete = new Button("DELETE");
        delete.setLayoutX(118);
        delete.setLayoutY(143);
        delete.setPrefWidth(84);
        delete.setPrefHeight(31);
        delete.setStyle("-fx-background-color: #a82f2a; -fx-font-family: DejaVu Sans; -fx-font-weight: bold; -fx-font-size: 15px;");

        delete.setOnAction(event -> {
            onDeleteSession(sessionName); // LLAMAR AL METODO PARA ELIMINARLO
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

        nextPaneY += newPane.getPrefHeight() + 10; // SE AUMENTA LA POSICION Y EN EL PROXIMO PANEL

        // SI EL NUEVO PANEL ESTA FUERA DEL AREA VISIBLE, SE AJUSTA EL TAMAÑO DEL PANE DEL SCROLLPANE
        if (nextPaneY > paneScroll.getPrefHeight()) {
            paneScroll.setPrefHeight(nextPaneY + 10); // SE HACE MAS GRANDE EL PANEL QUE CONTIENE LAS SESSIONES
        }
        return newPane;
    } // CREAR UN NUEVO PANEL DE SESSION

    @FXML void onCloseDetailsPaneAction() {
        detailsPane.setVisible(false);
    }

    @FXML
    void onCloseTimesDetailsAction(ActionEvent event) {
        detailsManualPane.setVisible(false);
    } // CERRAR EL PANEL DE INSERTAR TIEMPO MANUALMENTE

    @FXML
    void onInserTimeDetailsAction(ActionEvent event) {
        if (detailTimeTxt.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Campos vacíos.");
            alert.setHeaderText("¡ERROR!");
            alert.setContentText("Por favor, inserte un tiempo antes de continuar.");
            alert.showAndWait();
        } else if (StartCtrller.isDemo) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error al insertar tiempo");
            alert.setHeaderText("Inserción fallida");
            alert.setContentText("Lo sentimos, no se puede insertar tiempos en la sesión como usuario demo.\n" +
                    "Para acceder a las funcionalidades de la aplicacion, registrate o inicia sesión. ¡Gracias!");
            alert.showAndWait();
        } else if (!Validator.isValidTime(detailTimeTxt)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Tiempo no válido.");
            alert.setHeaderText("¡ERROR!");
            alert.setContentText("Por favor, ingrese un tiempo válido.\nFor example : 00:12,11");
            alert.showAndWait();
        } else {
            if (!StartCtrller.isDemo) {
                String tiempo = detailTimeTxt.getText(); // GUARDAR EL TIEMPO QUE HA HECHO
                tiempo = tiempo.replace(',', '.'); // PARA QUE LAS DECIMAS DE LA BASE DE DATOS SE ESTABLEZCA BIEN

                /* ESTABLECES MINUTOS Y SEGUNDOS */
                int indiceMinutos = tiempo.indexOf(":"); // INDICE QUE INDICA EN QUE POSICION ESTA :
                String subMinutos = tiempo.substring(0, indiceMinutos); // ESTABLECE EL VALOR ANTES DEL :
                // PARA ESTABLECER EL VALOR DE LOS SEGUNDOS, SE COJE EL VALOR DESPUES DEL : HASTA EL FINAL
                String subSeconds = tiempo.substring(indiceMinutos + 1, indiceMinutos + (tiempo.length() - indiceMinutos));

                if (!TimeTrainingDAO.createTimeTraining(scrambleLabel.getText(), subMinutos, subSeconds, localDate, idSessions)) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Tiempos fallidos.");
                    alert.setHeaderText("¡ERROR!");
                    alert.setContentText("No se ha podido insertar el tiempo.");
                    alert.showAndWait();
                } // MENSAJE POR SI ALGO FALLA AL INSERTAR LOS DATOS
            } // SI NO ES USER DEMO, SE LE GUARDA LOS TIEMPOS EN ESA SESSION QUE ESTA SIENDO UTILIZADA
            scrambleLabel.setText(CodeGeneral.scramble()); // VOLVER A GENERAR EL SCRAMBLE
        }
    }// INSERTAR TIEMPOS MANUALMENTE

    @FXML
    void onEditNameDetailsAction() {
        if(!StartCtrller.isDemo) {
            String nombre = JOptionPane.showInputDialog(null, "Nombre sesion");
            if (nombre != null) {
                if (SessionDAO.changeNameSesssion(nombre, idSession)) {
                    detailsPane.setVisible(false);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Nombre de Sesion cambiada.");
                    alert.setHeaderText("Actualizacion exitosa");
                    alert.setContentText("Se ha actualizado el nombre de la sesion correctamente");
                    alert.showAndWait();
                    onReloadAction();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Nombre de Sesion no cambiada.");
                    alert.setHeaderText("Actualizacion fallida");
                    alert.setContentText("No se ha actualizado el nombre de la sesion correctamente");
                    alert.showAndWait();
                }
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error al actualizar el nombre de la sesión");
            alert.setHeaderText("Actualizacion fallida");
            alert.setContentText("Lo sentimos, no puedes actualizar el nombre de la sesión como usuario demo.\n" +
                    "Para acceder a las funcionalidades de la aplicacion, registrate o inicia sesión. ¡Gracias!");
            alert.showAndWait();
        } // SI ES USUARIO DEMO NO PODRA ACTUALIZAR EL NOMBRE DE LA SESION
    }

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
                contadorCreate = 0; // SI LO ELIMINA, VUELVE A 0 EL CONTADOR
            } else {
                boolean average = AverageDAO.deleteAverage(nameSession);
                TimeTrainingDAO.deleteTimesSession(nameSession);
                boolean sessions = SessionDAO.deleteSession(nameSession);
                if (average && sessions) { // SOLO AVERAGE Y SESSIONS POR SI NO HAY NINGUN TIEMPO
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
    void onCancelSessionCreate() {
        int opcion = JOptionPane.showConfirmDialog(null,
                "¿Estás seguro de que quieres cancelar la creación de la sesión?\n" +
                        "Todos los datos ingresados se perderán.", "Confirmación", JOptionPane.YES_NO_OPTION);
        if (opcion == JOptionPane.YES_OPTION) {
            nameSession.setVisible(false);
            // BORRAR LOS VALORES ESTABLECIDOS
            sessionName.clear();
            categoriesCB.setValue(null);
        }
    } // CANCELAR SESSION


    void onReloadAction() {
        paneScroll.setStyle("-fx-background-color :  #325743");
        nextPaneY = 10;
        paneScroll.getChildren().clear();
        paneScroll.setPrefHeight(alturaPanel()); // SE ESTABLECE LA NUEVA ALTURA
        if (!StartCtrller.isDemo) {
            loadSession();
        }
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

package com.example.cubex.Controller;

import com.example.cubex.DAO.*;
import com.example.cubex.Database.DatabaseConnection;
import com.example.cubex.Main;
import com.example.cubex.Validator.Validator;
import com.example.cubex.model.CacheStatic;
import com.example.cubex.model.CubeUser;
import com.example.cubex.model.TimesChampionship;
import com.example.cubex.model.UserChampCompete;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Camera;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChampionshipCtrller extends CodeGeneral implements Initializable {
    @FXML
    private ComboBox<?> categoriesCB;

    @FXML
    private ComboBox<?> categoriesCB1;
    @FXML
    private ComboBox<?> categoryChamp;
    @FXML
    private ComboBox<?> nameChamp;

    @FXML
    private Pane champMenu;

    @FXML
    private TextField championNameTxt;

    @FXML
    private ScrollPane championshipScroll;

    @FXML
    private DatePicker datePickerTxxt;

    @FXML
    private TextArea descriptionChampTxt;

    @FXML
    private Pane generalChampPane;

    @FXML
    private Button githubBtt;

    @FXML
    private ImageView imageProfile;

    @FXML
    private Pane newChampPane;

    @FXML
    private TextField numberPartTxt;

    @FXML
    private AnchorPane paneChampScroll;

    @FXML
    private TextField priceChampTxt;

    @FXML
    private Pane yourChampsPane;
    @FXML
    private Label priceLabel;
    @FXML
    private ScrollPane champJoinScroll;
    @FXML
    private Label categoriTimesChamp;
    @FXML
    private AnchorPane scrollChampJoinPane;


    @FXML
    private Pane listUserChampPane;

    @FXML
    private Label nameChampUserLabel;

    @FXML
    private AnchorPane scrollUserChampPane;

    @FXML
    private ScrollPane userChampScroll;


    @FXML
    private Label categoryChampLabel;

    @FXML
    private Label categoryLabel;

    @FXML
    private TextField champTimeTxt;

    @FXML
    private Label countTimesLabel;

    @FXML
    private Label scrambleLabel;

    @FXML
    private Pane timeChampPane;


    LocalDate localDate = LocalDate.now(); // ATRIBUTO DIA ACTUAL
    public static int idChampActual; // CAMPEONATO ACTUAL
    private double nextPaneY = 10; // POSICION Y DEL PROXIMO PANEL PARA LOS PANELES DE LOS CAMPEONATOS
    private double nextPaneYJoin = 10; // POSICION Y DEL PROXIMO PANEL PARA LOS PANELES DE LOS CAMPEONATOS

    @FXML
    void onYourChampionshipAction(ActionEvent event) {
        yourChampsPane.setVisible(true);
        generalChampPane.setVisible(false);
        newChampPane.setVisible(false);
        timeChampPane.setVisible(false);
        listUserChampPane.setVisible(false);
        nextPaneYJoin = 10;
        scrollChampJoinPane.getChildren().clear();
    }

    @FXML
    void onShowCreatePane(ActionEvent event) {
        newChampPane.setVisible(true);
        if (MemberDAO.selectMember(CacheStatic.cubeUser.getMail())) {
            priceLabel.setVisible(true);
            priceChampTxt.setVisible(true);
        } else {
            priceLabel.setVisible(false);
            priceChampTxt.setVisible(false);
        }
    }

    @FXML
    void onCreateChampAction(ActionEvent event) {
        int idUser = CubeUserDAO.selectIdUser(CacheStatic.cubeUser.getMail());
        String description;
        if (championNameTxt.getText().isEmpty() || (categoriesCB.getValue() == null && categoriesCB1.getValue() == null)
                || datePickerTxxt.getValue() == null || numberPartTxt.getText().isEmpty() || descriptionChampTxt.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Campos vacíos.");
            alert.setHeaderText("¡ERROR!");
            alert.setContentText("Por favor, rellene todos los datos antes de continuar.");
            alert.showAndWait();
        } else if (!Validator.isNumeric(numberPartTxt.getText())) {
            // SI EL NUMERO DE PARTICIPANTES CONTIENE UNA CADENA ENTONCES SALTA UN ERROR
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Formato no válido.");
            alert.setHeaderText("¡ERROR!");
            alert.setContentText("Por favor, rellene el campo de numero de participantes con un numero.");
            alert.showAndWait();
        } else if (datePickerTxxt.getValue().isBefore(localDate)) {
            // SI LA FECHA SELECCIONADA ES MENOR AL DIA ACTUAL SALTA UN ERROR
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Formato no válido.");
            alert.setHeaderText("¡ERROR!");
            alert.setContentText("Por favor, elija una fecha mayor o igual a " + localDate + ".");
            alert.showAndWait();
        } else if (ChampionshipDAO.existsNameChamp(championNameTxt.getText())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Campeonato existente.");
            alert.setHeaderText("¡ERROR!");
            alert.setContentText("Ese nombre de campeonato ya se escogio.\nPor favor, elige otro nombre.");
            alert.showAndWait();
        } else {
            if (!MemberDAO.selectMember(CacheStatic.cubeUser.getMail())) {
                if (categoriesCB.getValue() == null) {
                    description = categoriesCB1.getValue().toString();
                } else if (categoriesCB1.getValue() == null) {
                    description = categoriesCB.getValue().toString();
                } else {
                    description = categoriesCB.getValue().toString() + ", " + categoriesCB1.getValue().toString();
                }

                if (!ChampionshipDAO.insertChampionship(idUser, championNameTxt.getText(), 0,
                        Integer.parseInt(numberPartTxt.getText()), descriptionChampTxt.getText(), datePickerTxxt.getValue(),
                        false, description)) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Creación fallida.");
                    alert.setHeaderText("¡ERROR!");
                    alert.setContentText("No se ha podido crear el campeonato correctamente");
                    alert.showAndWait();
                } // SI NO SE HA INSERTADO BIEN SE MUESTRA UN MENSAJE DE ERROR
                newChampPane.setVisible(false);
            } else {
                if (priceLabel.getText().isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Campos vacíos.");
                    alert.setHeaderText("¡ERROR!");
                    alert.setContentText("Por favor, rellene todos los datos antes de continuar.");
                } else if (!Validator.isNumeric(numberPartTxt.getText()) || !Validator.isNumeric(priceChampTxt.getText())) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Formato no válido.");
                    alert.setHeaderText("¡ERROR!");
                    alert.setContentText("Por favor, rellene el campo de numero de participantes o el precio con un numero.");
                    alert.showAndWait();
                } else {
                    if (categoriesCB.getValue() == null) {
                        description = categoriesCB1.getValue().toString();
                    } else if (categoriesCB1.getValue() == null) {
                        description = categoriesCB.getValue().toString();
                    } else {
                        description = categoriesCB.getValue().toString() + ", " + categoriesCB1.getValue().toString();
                    }
                    boolean isMember = true;
                    if (Integer.parseInt(priceChampTxt.getText()) == 0) {
                        isMember = false;
                    }
                    if (!ChampionshipDAO.insertChampionship(idUser, championNameTxt.getText(), Integer.parseInt(priceChampTxt.getText()),
                            Integer.parseInt(numberPartTxt.getText()), descriptionChampTxt.getText(), datePickerTxxt.getValue(),
                            isMember, description)) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Creación fallida.");
                        alert.setHeaderText("¡ERROR!");
                        alert.setContentText("No se ha podido crear el campeonato correctamente");
                        alert.showAndWait();
                    } // SI NO SE HA INSERTADO BIEN SE MUESTRA UN MENSAJE DE ERROR
                    newChampPane.setVisible(false);
                }
            }
        }
    }

    @FXML
    void onCancelChampCreate(ActionEvent event) {
        int opcion = JOptionPane.showConfirmDialog(null,
                "¿Estás seguro de que quieres cancelar la creación del campeonato?\n" +
                        "Todos los datos ingresados se perderán.", "Confirmación", JOptionPane.YES_NO_OPTION);
        if (opcion == JOptionPane.YES_OPTION) {
            newChampPane.setVisible(false);
            // BORRAR LOS VALORES ESTABLECIDOS
            championNameTxt.clear();
            datePickerTxxt.setValue(null);
            categoriesCB.setValue(null);
            categoriesCB1.setValue(null);
            priceChampTxt.clear();
            numberPartTxt.clear();
            descriptionChampTxt.clear();
        }
    }

    @FXML
    void onSearchTimesChampAction(ActionEvent event) {
        String nameChampionship = String.valueOf(nameChamp.getSelectionModel().getSelectedItem());
        ChampionshipDAO.categoryChamp(categoryChamp, nameChampionship);
    }

    @FXML
    void onSearchCategoryChampAction(ActionEvent event) {
        String nameChampionship = String.valueOf(nameChamp.getSelectionModel().getSelectedItem());
        int idChamp = ChampionshipDAO.selectIdChamp(nameChampionship);
        ChampionshipDAO.categoryChamp(categoryChamp, nameChampionship);
        if (categoryChamp.getValue() != null) {
            int idType = SessionDAO.idCategory(categoryChamp.getSelectionModel().getSelectedItem().toString());
            int contador = 0, minutes;
            double seconds;
            String category, nameUser, nameUserWinner;
            // CONTENEDOR PRINCIPAL
            VBox mainContainer = new VBox();
            mainContainer.setSpacing(20);
            for (TimesChampionship timesChampionship : ChampionshipDAO.listTimesChamp(nameChampionship, String.valueOf(categoryChamp.getSelectionModel().getSelectedItem()))) {
                contador++;

                // CREAR UN HBOX PARA CADA TIEMPO
                HBox timeBox = new HBox();
                timeBox.setSpacing(20);

                // LABELS PARA LOS TIEMPOS
                nameUser = CubeUserDAO.selectNameUser(timesChampionship.getIdUser());

                Label label1 = new Label("  " + contador + ")");
                Label label2 = new Label(timesChampionship.getMinutes() + ":" + timesChampionship.getSeconds());
                Label label3 = new Label(nameUser);

                // ESTILOS
                label1.setStyle("-fx-font-size: 17px; -fx-text-fill: black;");
                label2.setStyle("-fx-font-size: 17px; -fx-text-fill: black;");
                label3.setStyle("-fx-font-size: 17px; -fx-text-fill: black;");


                // AGREGAR ELEMENTOS AL HBOX
                timeBox.getChildren().addAll( label1, label2, label3);

                // AGREGAR EL HBOX AL CONTENEDOR PRINCIPAL
                mainContainer.getChildren().add(timeBox);
            }
            minutes = TimeChampionshipDAO.dataWinnerCategory(idChamp, idType).getMinutes();
            seconds = TimeChampionshipDAO.dataWinnerCategory(idChamp, idType).getSeconds();
            nameUserWinner = CubeUserDAO.selectNameUser(TimeChampionshipDAO.dataWinnerCategory(idChamp, idType).getIdUser());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Ganador.");
            alert.setHeaderText("¡GANADOR!");
            alert.setContentText("WINNER : " + nameUserWinner + " TIMES : " + minutes + ":" + seconds);
            alert.showAndWait();

            if(!CubeChampPerteneceDAO.isPertenece(idType, idChamp, nameUserWinner)){
                CubeChampPerteneceDAO.createWinnerPertenece(idType, idChamp, nameUserWinner);
            }

            mainContainer.setStyle("-fx-background-color : #204338");
            // AGREGAR EL CONTENEDOR PRINCIPAL AL SCROLLPANE
            champJoinScroll.setContent(mainContainer);
        }
    }

    @FXML
    void onGeneralChampAction(ActionEvent event) {
        generalChampPane.setVisible(true);
        listUserChampPane.setVisible(false);
        yourChampsPane.setVisible(false);
        nextPaneY = 10;
        paneChampScroll.getChildren().clear();
        loadChampionship();
    }


    public Pane createChampionship(String nameChamp, LocalDate dateChamp,
                                   int participantsChamp, int priceChamp, String descriptionCham, boolean member_only, String category) {
        Pane newPane;
        int idChamp = ChampionshipDAO.selectIdChamp(nameChamp);
        // SE CREA UN NUEVO PANEL PARA LOS CAMPEONATO SEGUN SI ES USUARIO O NO
        if (member_only) {
            newPane = new Pane();
            newPane.setLayoutX(5); // ESPACIADO HORIZONTAL
            newPane.setLayoutY(nextPaneY); // ESPACIADO VERTICAL
            // TAMAÑO DEL PANEL
            newPane.setPrefHeight(290);
            newPane.setPrefWidth(240);
            newPane.setStyle("-fx-background-color:  #325743; -fx-background-radius: 24; -fx-border-color: black; -fx-border-radius: 24;");

            // SE CREAN LOS ELEMENTOS
            ImageView imagePro = new ImageView();
            /** SE CREA LA CARPETA Controller Y SE METE AHI LA IMAGEN PARA QUE ENCUENTRE EL RECURSO **/
            imagePro.setImage(new Image(this.getClass().getResource("cubexIcon.png").toString()));
            imagePro.setLayoutX(-14);
            imagePro.setLayoutY(-8);
            imagePro.setFitHeight(72);
            imagePro.setFitWidth(78);

            Label proLabel = new Label("PRO");
            proLabel.setLayoutX(43);
            proLabel.setLayoutY(9);
            proLabel.setStyle("-fx-font-family: DejaVu Sans; -fx-font-weight: bold; -fx-font-size: 19px; -fx-text-fill: #74cc5e;");


            Label nameChampionship = new Label(nameChamp);
            nameChampionship.setLayoutX(12);
            nameChampionship.setLayoutY(33);
            nameChampionship.setStyle("-fx-underline: true; -fx-font-family: DejaVu Sans; -fx-font-weight: bold; -fx-font-size: 21px;");

            Label administrator = new Label("Administrator : " + ChampionshipDAO.administratorCham(nameChampionship.getText()));
            administrator.setLayoutX(12);
            administrator.setLayoutY(64);
            administrator.setStyle("-fx-font-family: System; -fx-font-size: 15px;");

            Label categoriesLabel = new Label("Category : " + category);
            categoriesLabel.setLayoutX(12);
            categoriesLabel.setLayoutY(90);
            categoriesLabel.setStyle("-fx-font-family: System; -fx-font-size: 15px;");


            Label dateChampionship = new Label("Date : " + dateChamp);
            dateChampionship.setLayoutX(12);
            dateChampionship.setLayoutY(106);
            dateChampionship.setStyle("-fx-font-family: System; -fx-font-size: 15px;");

            int numberUser = UserChampCompeteDAO.countUserChamp(idChamp);
            Label numberPartChamp = new Label("Participants : " + numberUser + "/" + participantsChamp);
            numberPartChamp.setLayoutX(12);
            numberPartChamp.setLayoutY(127);
            numberPartChamp.setStyle("-fx-font-family: System; -fx-font-size: 15px;");

            Button show = new Button("SHOW");
            show.setLayoutX(160);
            show.setLayoutY(105);
            show.setPrefWidth(70);
            show.setPrefHeight(31);
            show.setStyle("-fx-background-color: #b1c8a3; -fx-font-family: DejaVu Sans; -fx-font-weight: bold; -fx-font-size: 15px;");

            show.setOnAction(event -> {
                idChampActual = idChamp;
                int contador = 0;
                nameChampUserLabel.setText(nameChamp);
                listUserChampPane.setVisible(true);
                scrollUserChampPane.getChildren().clear();
                VBox vBox = new VBox();
                UserChampCompeteDAO.selectIdUsersChamp(idChamp);
                for (UserChampCompete userChampCompete : UserChampCompeteDAO.selectIdUsersChamp(idChamp)) {
                    for (CubeUser cubeUser : CubeUserDAO.listUserChamp(userChampCompete.getIdUser())) {
                        Label label1 = new Label("  " + contador + ")  " + cubeUser.getMail());
                        label1.setStyle("-fx-font-size: 17px; -fx-text-fill: black;");
                        vBox.getChildren().addAll(label1);
                        contador++;
                    }
                }
                vBox.setStyle("-fx-background-color :  #325743;");
                userChampScroll.setContent(vBox);
                userChampScroll.setFitToWidth(true);
            });

            Label priceChampionship = new Label("Price : " + priceChamp);
            priceChampionship.setLayoutX(12);
            priceChampionship.setLayoutY(149);
            priceChampionship.setStyle("-fx-font-family: System; -fx-font-size: 15px;");

            TextArea descriptionChamp = new TextArea(descriptionCham);
            descriptionChamp.setLayoutX(12);
            descriptionChamp.setLayoutY(174);
            descriptionChamp.setPrefWidth(218);
            descriptionChamp.setPrefHeight(63);
            descriptionChamp.setStyle("-fx-font-family: System; -fx-font-size: 15px; -fx-control-inner-background : #b1c8a3");
            descriptionChamp.setEditable(false);

            int idUser = CubeUserDAO.selectIdUser(CacheStatic.cubeUser.getMail());
            String nameUser = CubeUserDAO.selectNameUser(idUser);
            if (!ChampionshipDAO.administratorCham(nameChampionship.getText()).equals(nameUser)) {
                Button insertTimes = new Button("INSERT");
                insertTimes.setLayoutX(12);
                insertTimes.setLayoutY(218);
                insertTimes.setPrefWidth(109);
                insertTimes.setPrefHeight(31);
                insertTimes.setStyle("-fx-background-color: #6d7b64; -fx-font-family: DejaVu Sans; -fx-font-weight: bold; -fx-font-size: 15px;");

                insertTimes.setOnAction(event -> {
                    idChampActual = idChamp;
                    yourChampsPane.setVisible(true);
                    scrambleLabel.setText(CodeGeneral.scramble());
                    timeChampPane.setVisible(true);
                    newChampPane.setVisible(false);
                });

                Button join = new Button("JOIN");
                join.setLayoutX(123);
                join.setLayoutY(218);
                join.setPrefWidth(109);
                join.setPrefHeight(31);
                join.setStyle("-fx-background-color: #74cc5e; -fx-font-family: DejaVu Sans; -fx-font-weight: bold; -fx-font-size: 15px;");

                join.setOnAction(event -> {

                    if (UserChampCompeteDAO.selectUserChamp(idChamp, idUser)) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error al unirse al campeonato");
                        alert.setHeaderText("¡ERROR!");
                        alert.setContentText("No se ha podido unirse al campeonato porque ya está inscrito.");
                        alert.showAndWait();
                    } else {
                        if (numberUser == participantsChamp) {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Error al unirse al campeonato");
                            alert.setHeaderText("¡ERROR!");
                            alert.setContentText("No se ha podido unirse al campeonato porque está completo.");
                            alert.showAndWait();
                        } else {
                            if (!UserChampCompeteDAO.insertUserInChamp(idUser, idChamp)) {
                                Alert alert = new Alert(Alert.AlertType.ERROR);
                                alert.setTitle("Unión fallida.");
                                alert.setHeaderText("¡ERROR!");
                                alert.setContentText("No se ha podido unirse al campeonato correctamente");
                                alert.showAndWait();
                            } else {
                                nextPaneY = 10;
                                paneChampScroll.getChildren().clear();
                                loadChampionship();
                            }// SI ALGO FALLA, MOSTRAR UN ERROR SINO SE RECARGA LOS CAMPEONATOS
                        } // SI ESTA COMPLETO EL CAMPEONATO SE MUESTRA UN ERROR, SI NO SE UNE AL CAMPEONATO
                    } //SI YA HAY UN USUARIO DENTRO DE ESE CAMPEONATO SALTAR UN MENSAJE DE ERROR
                });

                // SE AGREGAN TODOS AL NUEVO PANEL
                newPane.getChildren().addAll(
                        imagePro, proLabel, nameChampionship, administrator, categoriesLabel, dateChampionship, numberPartChamp,
                        show, priceChampionship, descriptionChamp, join, insertTimes
                );
            } else {
                Button delete = new Button("DELETE");
                delete.setLayoutX(123);
                delete.setLayoutY(218);
                delete.setPrefWidth(109);
                delete.setPrefHeight(31);
                delete.setStyle("-fx-background-color: #a82f2a; -fx-font-family: DejaVu Sans; -fx-font-weight: bold; -fx-font-size: 15px;");

                delete.setOnAction(event -> {
                    TimeChampionshipDAO.deleteTimesChamp(idChamp);
                    UserChampCompeteDAO.deleteTimesChamp(idChamp);
                    if (!ChampionshipDAO.deleteChamp(idChamp)) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error al eliminar el campeonato");
                        alert.setHeaderText("¡ERROR!");
                        alert.setContentText("No se ha podido eliminar el campeonato.");
                        alert.showAndWait();
                    }
                    nextPaneY = 0;
                    paneChampScroll.getChildren().clear();
                    loadChampionship();
                });

                Button updateName = new Button("UPDATE");
                updateName.setLayoutX(12);
                updateName.setLayoutY(218);
                updateName.setPrefWidth(109);
                updateName.setPrefHeight(31);
                updateName.setStyle("-fx-background-color: #6d7b64; -fx-font-family: DejaVu Sans; -fx-font-weight: bold; -fx-font-size: 15px;");

                updateName.setOnAction(event -> {
                    String nombre = JOptionPane.showInputDialog(null, "Nuevo nombre del campeonato");
                    if (nombre != null) {
                        if (ChampionshipDAO.updateName(nombre, idChamp)) {
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Nombre de Sesion cambiada.");
                            alert.setHeaderText("Actualizacion exitosa");
                            alert.setContentText("Se ha actualizado el nombre de la sesion correctamente");
                            alert.showAndWait();
                            nextPaneY = 0;
                            paneChampScroll.getChildren().clear();
                            loadChampionship();
                        } else {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Nombre de Sesion no cambiada.");
                            alert.setHeaderText("Actualizacion fallida");
                            alert.setContentText("No se ha actualizado el nombre de la sesion correctamente");
                            alert.showAndWait();
                        }
                    }
                });

                // SE AGREGAN TODOS AL NUEVO PANEL
                newPane.getChildren().addAll(
                        imagePro, proLabel, nameChampionship, administrator, categoriesLabel, dateChampionship, numberPartChamp,
                        show, priceChampionship, descriptionChamp, delete, updateName
                );
            } // SI ES EL CREADOR DE LA SESION ENTONCES LE APARECERA UN BOTON DE ELIMINAR

            nextPaneY += newPane.getPrefHeight() + 10; // SE AUMENTA LA POSICION Y EN EL PROXIMO PANEL

            // SI EL NUEVO PANEL ESTA FUERA DEL AREA VISIBLE, SE AJUSTA EL TAMAÑO DEL PANE DEL SCROLLPANE
            if (nextPaneY > paneChampScroll.getPrefHeight()) {
                paneChampScroll.setPrefHeight(nextPaneY + 10); // SE HACE MAS GRANDE EL PANEL QUE CONTIENE LAS SESSIONES
            }
        } else {
            newPane = new Pane();
            newPane.setLayoutX(5); // ESPACIADO HORIZONTAL
            newPane.setLayoutY(nextPaneY); // ESPACIADO VERTICAL
            // TAMAÑO DEL PANEL
            newPane.setPrefHeight(262);
            newPane.setPrefWidth(242);
            newPane.setStyle("-fx-background-color:  #325743; -fx-background-radius: 24; -fx-border-color: black; -fx-border-radius: 24;");

            Label nameChampionship = new Label(nameChamp);
            nameChampionship.setLayoutX(12);
            nameChampionship.setLayoutY(6);
            nameChampionship.setStyle("-fx-underline: true; -fx-font-family: DejaVu Sans; -fx-font-weight: bold; -fx-font-size: 21px;");

            Label administrator = new Label("Administrator : " + ChampionshipDAO.administratorCham(nameChampionship.getText()));
            administrator.setLayoutX(12);
            administrator.setLayoutY(37);
            administrator.setStyle("-fx-font-family: System; -fx-font-size: 15px;");

            Label categoriesLabel = new Label("Category : " + category);
            categoriesLabel.setLayoutX(12);
            categoriesLabel.setLayoutY(59);
            categoriesLabel.setStyle("-fx-font-family: System; -fx-font-size: 15px;");

            Label dateChampionship = new Label("Date : " + dateChamp);
            dateChampionship.setLayoutX(12);
            dateChampionship.setLayoutY(79);
            dateChampionship.setStyle("-fx-font-family: System; -fx-font-size: 15px;");

            int numberUser = UserChampCompeteDAO.countUserChamp(idChamp);
            Label numberPartChamp = new Label("Participants : " + numberUser + "/" + participantsChamp);
            numberPartChamp.setLayoutX(12);
            numberPartChamp.setLayoutY(100);
            numberPartChamp.setStyle("-fx-font-family: System; -fx-font-size: 15px;");

            Button show = new Button("SHOW");
            show.setLayoutX(160);
            show.setLayoutY(105);
            show.setPrefWidth(70);
            show.setPrefHeight(31);
            show.setStyle("-fx-background-color: #b1c8a3; -fx-font-family: DejaVu Sans; -fx-font-weight: bold; -fx-font-size: 15px;");

            show.setOnAction(event -> {
                idChampActual = idChamp;
                int contador = 0;
                nameChampUserLabel.setText(nameChamp);
                listUserChampPane.setVisible(true);
                scrollUserChampPane.getChildren().clear();
                VBox vBox = new VBox();
                UserChampCompeteDAO.selectIdUsersChamp(idChamp);
                for (UserChampCompete userChampCompete : UserChampCompeteDAO.selectIdUsersChamp(idChamp)) {
                    for (CubeUser cubeUser : CubeUserDAO.listUserChamp(userChampCompete.getIdUser())) {
                        Label label1 = new Label("  " + contador + ")  " + cubeUser.getMail());
                        label1.setStyle("-fx-font-size: 17px; -fx-text-fill: black;");
                        vBox.getChildren().addAll(label1);
                        contador++;
                    }
                }
                vBox.setStyle("-fx-background-color :  #325743;");
                userChampScroll.setContent(vBox);
                userChampScroll.setFitToWidth(true);
            });

            Label priceChampionship = new Label("Price : " + priceChamp);
            priceChampionship.setLayoutX(12);
            priceChampionship.setLayoutY(122);
            priceChampionship.setStyle("-fx-font-family: System; -fx-font-size: 15px;");

            TextArea descriptionChamp = new TextArea(descriptionCham);
            descriptionChamp.setLayoutX(12);
            descriptionChamp.setLayoutY(147);
            descriptionChamp.setPrefWidth(218);
            descriptionChamp.setPrefHeight(63);
            descriptionChamp.setStyle("-fx-font-family: System; -fx-font-size: 15px; -fx-control-inner-background : #b1c8a3");
            descriptionChamp.setEditable(false);

            int idUser = CubeUserDAO.selectIdUser(CacheStatic.cubeUser.getMail());
            String nameUser = CubeUserDAO.selectNameUser(idUser);
            if (!ChampionshipDAO.administratorCham(nameChampionship.getText()).equals(nameUser)) {
                Button insertTimes = new Button("INSERT");
                insertTimes.setLayoutX(12);
                insertTimes.setLayoutY(218);
                insertTimes.setPrefWidth(109);
                insertTimes.setPrefHeight(31);
                insertTimes.setStyle("-fx-background-color: #6d7b64; -fx-font-family: DejaVu Sans; -fx-font-weight: bold; -fx-font-size: 15px;");

                insertTimes.setOnAction(event -> {
                    idChampActual = idChamp;
                    yourChampsPane.setVisible(true);
                    scrambleLabel.setText(CodeGeneral.scramble());
                    timeChampPane.setVisible(true);
                    newChampPane.setVisible(false);
                });

                Button join = new Button("JOIN");
                join.setLayoutX(123);
                join.setLayoutY(218);
                join.setPrefWidth(109);
                join.setPrefHeight(31);
                join.setStyle("-fx-background-color: #74cc5e; -fx-font-family: DejaVu Sans; -fx-font-weight: bold; -fx-font-size: 15px;");

                join.setOnAction(event -> {

                    if (UserChampCompeteDAO.selectUserChamp(idChamp, idUser)) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error al unirse al campeonato");
                        alert.setHeaderText("¡ERROR!");
                        alert.setContentText("No se ha podido unirse al campeonato porque ya está inscrito.");
                        alert.showAndWait();
                    } else {
                        if (numberUser == participantsChamp) {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Error al unirse al campeonato");
                            alert.setHeaderText("¡ERROR!");
                            alert.setContentText("No se ha podido unirse al campeonato porque está completo.");
                            alert.showAndWait();
                        } else {
                            if (!UserChampCompeteDAO.insertUserInChamp(idUser, idChamp)) {
                                Alert alert = new Alert(Alert.AlertType.ERROR);
                                alert.setTitle("Unión fallida.");
                                alert.setHeaderText("¡ERROR!");
                                alert.setContentText("No se ha podido unirse al campeonato correctamente");
                                alert.showAndWait();
                            } else {
                                nextPaneY = 10;
                                paneChampScroll.getChildren().clear();
                                loadChampionship();
                            }// SI ALGO FALLA, MOSTRAR UN ERROR SINO SE RECARGA LOS CAMPEONATOS
                        } // SI ESTA COMPLETO EL CAMPEONATO SE MUESTRA UN ERROR, SI NO SE UNE AL CAMPEONATO
                    } //SI YA HAY UN USUARIO DENTRO DE ESE CAMPEONATO SALTAR UN MENSAJE DE ERROR
                });

                // SE AGREGAN TODOS AL NUEVO PANEL
                newPane.getChildren().addAll(
                        nameChampionship, administrator, categoriesLabel, dateChampionship, numberPartChamp,
                        show, priceChampionship, descriptionChamp, join, insertTimes
                );
            } else {
                Button delete = new Button("DELETE");
                delete.setLayoutX(123);
                delete.setLayoutY(218);
                delete.setPrefWidth(109);
                delete.setPrefHeight(31);
                delete.setStyle("-fx-background-color: #a82f2a; -fx-font-family: DejaVu Sans; -fx-font-weight: bold; -fx-font-size: 15px;");

                delete.setOnAction(event -> {
                    TimeChampionshipDAO.deleteTimesChamp(idChamp);
                    UserChampCompeteDAO.deleteTimesChamp(idChamp);
                    if (!ChampionshipDAO.deleteChamp(idChamp)) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error al eliminar el campeonato");
                        alert.setHeaderText("¡ERROR!");
                        alert.setContentText("No se ha podido eliminar el campeonato.");
                        alert.showAndWait();
                    }
                    nextPaneY = 0;
                    paneChampScroll.getChildren().clear();
                    loadChampionship();
                });
                Button updateName = new Button("UPDATE");
                updateName.setLayoutX(12);
                updateName.setLayoutY(218);
                updateName.setPrefWidth(109);
                updateName.setPrefHeight(31);
                updateName.setStyle("-fx-background-color: #6d7b64; -fx-font-family: DejaVu Sans; -fx-font-weight: bold; -fx-font-size: 15px;");

                updateName.setOnAction(event -> {
                    String nombre = JOptionPane.showInputDialog(null, "Nuevo nombre del campeonato");
                    if (nombre != null) {
                        if (ChampionshipDAO.updateName(nombre, idChamp)) {
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Nombre de Sesion cambiada.");
                            alert.setHeaderText("Actualizacion exitosa");
                            alert.setContentText("Se ha actualizado el nombre de la sesion correctamente");
                            alert.showAndWait();
                            nextPaneY = 0;
                            paneChampScroll.getChildren().clear();
                            loadChampionship();
                        } else {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Nombre de Sesion no cambiada.");
                            alert.setHeaderText("Actualizacion fallida");
                            alert.setContentText("No se ha actualizado el nombre de la sesion correctamente");
                            alert.showAndWait();
                        }
                    }
                });
                // SE AGREGAN TODOS AL NUEVO PANEL
                newPane.getChildren().addAll(
                        nameChampionship, administrator, categoriesLabel, dateChampionship, numberPartChamp,
                        show, priceChampionship, descriptionChamp, delete, updateName
                );
            } // SI ES EL CREADOR DE LA SESION ENTONCES LE APARECERA UN BOTON DE ELIMINAR

            nextPaneY += newPane.getPrefHeight() + 10; // SE AUMENTA LA POSICION Y EN EL PROXIMO PANEL

            // SI EL NUEVO PANEL ESTA FUERA DEL AREA VISIBLE, SE AJUSTA EL TAMAÑO DEL PANE DEL SCROLLPANE
            if (nextPaneY > paneChampScroll.getPrefHeight()) {
                paneChampScroll.setPrefHeight(nextPaneY + 10); // SE HACE MAS GRANDE EL PANEL QUE CONTIENE LAS SESSIONES
            }

            nextPaneYJoin += newPane.getPrefHeight() + 10; // SE AUMENTA LA POSICION Y EN EL PROXIMO PANEL

            // SI EL NUEVO PANEL ESTA FUERA DEL AREA VISIBLE, SE AJUSTA EL TAMAÑO DEL PANE DEL SCROLLPANE
            if (nextPaneYJoin > scrollChampJoinPane.getPrefHeight()) {
                scrollChampJoinPane.setPrefHeight(nextPaneYJoin + 10); // SE HACE MAS GRANDE EL PANEL QUE CONTIENE LAS SESSIONES
            }
        }
        return newPane;
    } // CREAR UN NUEVO PANEL DE SESSION


    @FXML
    void onCloseTimesChampAction(ActionEvent event) {
        timeChampPane.setVisible(false);
    }

    @FXML
    void onInserTimeChampAction(ActionEvent event) {
        int idUser = CubeUserDAO.selectIdUser(CacheStatic.cubeUser.getMail());
        String cubes = ChampionshipDAO.selectCategory(idChampActual);
        // SI TIENE MAS DE UNA CATEGORIA ENTONCES SE COGEN LAS DOS CATEGORIAS
        if (cubes.contains(",")) {
            int indiceCategoria = cubes.indexOf(",");
            String categoria1 = cubes.substring(0, indiceCategoria);
            String categoria2 = cubes.substring(indiceCategoria + 2, indiceCategoria + (cubes.length() - indiceCategoria));

            categoriTimesChamp.setText(categoria1);
            int idCategoria1 = SessionDAO.idCategory(categoria1);
            int idCategoria2 = SessionDAO.idCategory(categoria2);
            if (champTimeTxt.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Campos vacíos.");
                alert.setHeaderText("¡ERROR!");
                alert.setContentText("Por favor, inserte un tiempo antes de continuar.");
                alert.showAndWait();
            } else {
                if (ChampionshipDAO.countTimesChamp(idChampActual, idUser, idCategoria1) != 5) {
                    String tiempo = champTimeTxt.getText(); // GUARDAR EL TIEMPO QUE HA HECHO
                    tiempo = tiempo.replace(',', '.'); // PARA QUE LAS DECIMAS DE LA BASE DE DATOS SE ESTABLEZCA BIEN

                    /*ESTABLECES MINUTOS Y SEGUNDOS*/
                    int indiceMinutos = tiempo.indexOf(":"); // INDICE QUE INDICA EN QUE POSICION ESTA :
                    String subMinutos = tiempo.substring(0, indiceMinutos); // ESTABLECE EL VALOR ANTES DEL :
                    // PARA ESTABLECER EL VALOR DE LOS SEGUNDOS, SE COJE EL VALOR DESPUES DEL : HASTA EL FINAL
                    String subSeconds = tiempo.substring(indiceMinutos + 1, indiceMinutos + (tiempo.length() - indiceMinutos));

                    if (!TimeChampionshipDAO.createTimeChampionship(idUser, scrambleLabel.getText(), subMinutos, subSeconds, idChampActual, idCategoria1)) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Tiempos fallidos.");
                        alert.setHeaderText("¡ERROR!");
                        alert.setContentText("No se ha podido insertar el tiempo.");
                        alert.showAndWait();
                    } else {
                        countTimesLabel.setText(ChampionshipDAO.countTimesChamp(idChampActual, idUser, idCategoria1) + "/5");
                    }// MENSAJE POR SI ALGO FALLA AL INSERTAR LOS DATOS
                    scrambleLabel.setText(CodeGeneral.scramble()); // VOLVER A GENERAR EL SCRAMBLE*/
                } else {

                    categoriTimesChamp.setText(categoria2);
                    if (ChampionshipDAO.countTimesChamp(idChampActual, idUser, idCategoria2) != 5) {
                        String tiempo = champTimeTxt.getText(); // GUARDAR EL TIEMPO QUE HA HECHO
                        tiempo = tiempo.replace(',', '.'); // PARA QUE LAS DECIMAS DE LA BASE DE DATOS SE ESTABLEZCA BIEN

                        /*ESTABLECES MINUTOS Y SEGUNDOS*/
                        int indiceMinutos = tiempo.indexOf(":"); // INDICE QUE INDICA EN QUE POSICION ESTA :
                        String subMinutos = tiempo.substring(0, indiceMinutos); // ESTABLECE EL VALOR ANTES DEL :
                        // PARA ESTABLECER EL VALOR DE LOS SEGUNDOS, SE COJE EL VALOR DESPUES DEL : HASTA EL FINAL
                        String subSeconds = tiempo.substring(indiceMinutos + 1, indiceMinutos + (tiempo.length() - indiceMinutos));

                        if (!TimeChampionshipDAO.createTimeChampionship(idUser, scrambleLabel.getText(), subMinutos, subSeconds, idChampActual, idCategoria2)) {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Tiempos fallidos.");
                            alert.setHeaderText("¡ERROR!");
                            alert.setContentText("No se ha podido insertar el tiempo.");
                            alert.showAndWait();
                        } else {
                            countTimesLabel.setText(ChampionshipDAO.countTimesChamp(idChampActual, idUser, idCategoria2) + "/5");
                        }// MENSAJE POR SI ALGO FALLA AL INSERTAR LOS DATOS
                        scrambleLabel.setText(CodeGeneral.scramble()); // VOLVER A GENERAR EL SCRAMBLE*/

                    } else {

                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Maximo de tiempos alcanzados.");
                        alert.setHeaderText("¡ERROR!");
                        alert.setContentText("No se puede insertar mas tiempos porque ya has hecho los cinco tiempos.");
                        alert.showAndWait();


                    }
                } // SI NO ES USER DEMO, SE LE GUARDA LOS TIEMPOS EN ESA SESSION QUE ESTA SIENDO UTILIZADA

            }
        } else {
            int idCategoria = SessionDAO.idCategory(cubes);
            if (champTimeTxt.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Campos vacíos.");
                alert.setHeaderText("¡ERROR!");
                alert.setContentText("Por favor, inserte un tiempo antes de continuar.");
                alert.showAndWait();
            } else {
                categoriTimesChamp.setText(cubes);
                if (ChampionshipDAO.countTimesChamp(idChampActual, idUser, idCategoria) != 5) {
                    String tiempo = champTimeTxt.getText(); // GUARDAR EL TIEMPO QUE HA HECHO
                    tiempo = tiempo.replace(',', '.'); // PARA QUE LAS DECIMAS DE LA BASE DE DATOS SE ESTABLEZCA BIEN

                    /*ESTABLECES MINUTOS Y SEGUNDOS*/
                    int indiceMinutos = tiempo.indexOf(":"); // INDICE QUE INDICA EN QUE POSICION ESTA :
                    String subMinutos = tiempo.substring(0, indiceMinutos); // ESTABLECE EL VALOR ANTES DEL :
                    // PARA ESTABLECER EL VALOR DE LOS SEGUNDOS, SE COJE EL VALOR DESPUES DEL : HASTA EL FINAL
                    String subSeconds = tiempo.substring(indiceMinutos + 1, indiceMinutos + (tiempo.length() - indiceMinutos));

                    if (!TimeChampionshipDAO.createTimeChampionship(idUser, scrambleLabel.getText(), subMinutos, subSeconds, idChampActual, idCategoria)) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Tiempos fallidos.");
                        alert.setHeaderText("¡ERROR!");
                        alert.setContentText("No se ha podido insertar el tiempo.");
                        alert.showAndWait();
                    } else {
                        countTimesLabel.setText(ChampionshipDAO.countTimesChamp(idChampActual, idUser, idCategoria) + "/5");
                    }// MENSAJE POR SI ALGO FALLA AL INSERTAR LOS DATOS
                    scrambleLabel.setText(CodeGeneral.scramble()); // VOLVER A GENERAR EL SCRAMBLE*/
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Maximo de tiempos alcanzados.");
                    alert.setHeaderText("¡ERROR!");
                    alert.setContentText("No se puede insertar mas tiempos porque ya has hecho los cinco tiempos.");
                    alert.showAndWait();
                }
            }
        }

    }

    @FXML
    void onCloseUserChampList(ActionEvent event) {
        listUserChampPane.setVisible(false);
    }

    @FXML
    void onLeaveChampAction(ActionEvent event) {
        int idUser = CubeUserDAO.selectIdUser(CacheStatic.cubeUser.getMail());
        if (UserChampCompeteDAO.selectUserChamp(idChampActual, idUser)) {
            if (UserChampCompeteDAO.deleteUserChamp(idUser, idChampActual) && TimeChampionshipDAO.deleteUserChamp(idChampActual, idUser)) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Se ha abandonado el campeonato");
                alert.setHeaderText("¡ÉXITO!");
                alert.setContentText("Se ha abandonado el campeonato correctamente.");
                alert.showAndWait();
                listUserChampPane.setVisible(false);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error al abandonar el campeonato");
                alert.setHeaderText("¡ERROR!");
                alert.setContentText("No se ha podido abandonar el campeonato.");
                alert.showAndWait();
            } // MENSAJE PARA CUNADO SE ELIMINE UN USUARIO DE UN CAMPEONATO
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error al abandonar el campeonato");
            alert.setHeaderText("¡ERROR!");
            alert.setContentText("No se ha podido abandonar el campeonato porque no está inscrito.");
            alert.showAndWait();
        } // SI EL USUARIO NO ESTA EN EL CAMPEONATO SE MOSTRAR UN MENSAJE DE ERROR
    }


    public void loadChampionship() {
        String sql = "SELECT NAME_CHAMP, PRICE, NUMBER_PART, DESCRIPTION_CHAMP, REGISTRATION_DATE, MEMBERS_ONLY, DESCRIPTION_CATEGORY" +
                " FROM CHAMPIONSHIP";
        String nameChamp, descriptionChamp, descriptionCategory;
        int price, numberPart;
        LocalDate date;
        boolean membersOnly;
        try {
            Connection con = DatabaseConnection.conectar();
            PreparedStatement statement = con.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                nameChamp = resultSet.getString("NAME_CHAMP");
                price = resultSet.getInt("PRICE");
                numberPart = resultSet.getInt("NUMBER_PART");
                descriptionChamp = resultSet.getString("DESCRIPTION_CHAMP");
                date = resultSet.getDate("REGISTRATION_DATE").toLocalDate();
                membersOnly = resultSet.getBoolean("MEMBERS_ONLY");
                descriptionCategory = resultSet.getString("DESCRIPTION_CATEGORY");
                if (MemberDAO.selectMember(CacheStatic.cubeUser.getMail()) && membersOnly) {
                    paneChampScroll.getChildren().addAll(createChampionship(nameChamp, date, numberPart, price, descriptionChamp, membersOnly, descriptionCategory));
                } else if (MemberDAO.selectMember(CacheStatic.cubeUser.getMail()) && !membersOnly) {
                    paneChampScroll.getChildren().addAll(createChampionship(nameChamp, date, numberPart, price, descriptionChamp, membersOnly, descriptionCategory));
                } else if (!MemberDAO.selectMember(CacheStatic.cubeUser.getMail()) && !membersOnly) {
                    paneChampScroll.getChildren().addAll(createChampionship(nameChamp, date, numberPart, price, descriptionChamp, membersOnly, descriptionCategory));
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
    }


    @FXML
    void onGithubAction(ActionEvent event) {
        SettingCtrller.loadGithub();
    }

    @FXML
    void onProfileEditAction(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new
                    FXMLLoader(Main.class.getResource("Setting.fxml"));
            Parent root = fxmlLoader.load();
            SettingCtrller controller = fxmlLoader.getController();
            Scene scene = new Scene(root);
            Stage stage = (Stage) this.profileBtt.getScene().getWindow();
            stage.setTitle("Settings Page");
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
        yourChampsPane.setVisible(false);
        generalChampPane.setVisible(true);
        listUserChampPane.setVisible(false);
        if (CubeUserDAO.selectUrl(CacheStatic.cubeUser.getMail())) {
            imageProfile.setImage(CubeUserDAO.imgUrlSelect(CacheStatic.cubeUser.getMail()));
        } // SI LA IMAGEN SE MODIFICO ENTONCES SE CARGA LA IMAGEN
        CuberTypeDAO.cubeCategory(categoriesCB);
        CuberTypeDAO.cubeCategory(categoriesCB1);
        paneChampScroll.getChildren().clear();
        loadChampionship();
        ChampionshipDAO.nameChamp(nameChamp);
    }

}

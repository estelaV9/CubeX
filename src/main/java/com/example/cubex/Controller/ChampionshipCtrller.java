package com.example.cubex.Controller;

import com.example.cubex.DAO.*;
import com.example.cubex.Database.DatabaseConnection;
import com.example.cubex.Main;
import com.example.cubex.Validator.Validator;
import com.example.cubex.model.CacheStatic;
import com.example.cubex.model.CubeUser;
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

public class ChampionshipCtrller extends CodeGeneral implements Initializable {
    @FXML
    private ComboBox<?> categoriesCB;

    @FXML
    private ComboBox<?> categoriesCB1;

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
        int idUser = CubeUserDAO.selectIdUser(CacheStatic.cubeUser.getMail());
        loadJoinChampionship(idUser);
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
                    description = descriptionChampTxt.getText() + "\n" + categoriesCB1.getValue() + " CUBE CATEGORIES.";
                } else if (categoriesCB1.getValue() == null) {
                    description = descriptionChampTxt.getText() + "\n" + categoriesCB.getValue() + " CUBE CATEGORIES.";
                } else {
                    description = descriptionChampTxt.getText() + "\n" + categoriesCB.getValue() + " AND " +
                            categoriesCB1.getValue() + " CUBE CATEGORIES.";
                }

                if (!ChampionshipDAO.insertChampionship(idUser, championNameTxt.getText(), 0,
                        Integer.parseInt(numberPartTxt.getText()), description, datePickerTxxt.getValue(), false)) {
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
                        description = descriptionChampTxt.getText() + "\n" + categoriesCB1.getValue() + " CUBE CATEGORY.";
                    } else if (categoriesCB1.getValue() == null) {
                        description = descriptionChampTxt.getText() + "\n" + categoriesCB.getValue() + " CUBE CATEGORY.";
                    } else {
                        description = descriptionChampTxt.getText() + "\n" + categoriesCB.getValue() + " AND " +
                                categoriesCB1.getValue() + " CUBE CATEGORIES.";
                    }
                    boolean isMember = true;
                    if (Integer.parseInt(priceChampTxt.getText()) == 0) {
                        isMember = false;
                    }
                    if (!ChampionshipDAO.insertChampionship(idUser, championNameTxt.getText(), Integer.parseInt(priceChampTxt.getText()),
                            Integer.parseInt(numberPartTxt.getText()), description, datePickerTxxt.getValue(), isMember)) {
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
    void onGeneralChampAction(ActionEvent event) {
        generalChampPane.setVisible(true);
        listUserChampPane.setVisible(false);
        yourChampsPane.setVisible(false);
        nextPaneY = 10;
        paneChampScroll.getChildren().clear();
        loadChampionship();
    }


    public Pane createChampionship(String nameChamp, LocalDate dateChamp,
                                   int participantsChamp, int priceChamp, String descriptionCham, boolean member_only) {
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
                Button join = new Button("JOIN");
                join.setLayoutX(12);
                join.setLayoutY(245);
                join.setPrefWidth(218);
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
                        imagePro, proLabel, nameChampionship, administrator, dateChampionship, numberPartChamp,
                        show, priceChampionship, descriptionChamp, join
                );
            } else {
                Button delete = new Button("DELETE");
                delete.setLayoutX(12);
                delete.setLayoutY(245);
                delete.setPrefWidth(218);
                delete.setPrefHeight(31);
                delete.setStyle("-fx-background-color: #a82f2a; -fx-font-family: DejaVu Sans; -fx-font-weight: bold; -fx-font-size: 15px;");

                // SE AGREGAN TODOS AL NUEVO PANEL
                newPane.getChildren().addAll(
                        imagePro, proLabel, nameChampionship, administrator, dateChampionship, numberPartChamp,
                        show, priceChampionship, descriptionChamp, delete
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
                Button join = new Button("JOIN");
                join.setLayoutX(12);
                join.setLayoutY(218);
                join.setPrefWidth(218);
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
                        nameChampionship, administrator, dateChampionship, numberPartChamp,
                        show, priceChampionship, descriptionChamp, join
                );
            } else {
                Button delete = new Button("DELETE");
                delete.setLayoutX(12);
                delete.setLayoutY(218);
                delete.setPrefWidth(218);
                delete.setPrefHeight(31);
                delete.setStyle("-fx-background-color: #a82f2a; -fx-font-family: DejaVu Sans; -fx-font-weight: bold; -fx-font-size: 15px;");

                // SE AGREGAN TODOS AL NUEVO PANEL
                newPane.getChildren().addAll(
                        nameChampionship, administrator, dateChampionship, numberPartChamp,
                        show, priceChampionship, descriptionChamp, delete
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


    public Pane createJoinPane(String nameChamp) {
        Pane newPane;
        int idChamp = ChampionshipDAO.selectIdChamp(nameChamp);
        newPane = new Pane();
        newPane.setLayoutX(5); // ESPACIADO HORIZONTAL
        newPane.setLayoutY(nextPaneYJoin); // ESPACIADO VERTICAL
        // TAMAÑO DEL PANEL
        newPane.setPrefHeight(82);
        newPane.setPrefWidth(214);
        newPane.setStyle("-fx-background-color:  #325743; -fx-background-radius: 24; -fx-border-color: black; -fx-border-radius: 24;");

        Label nameChampionship = new Label(nameChamp);
        nameChampionship.setLayoutX(12);
        nameChampionship.setLayoutY(6);
        nameChampionship.setStyle("-fx-underline: true; -fx-font-family: DejaVu Sans; -fx-font-weight: bold; -fx-font-size: 21px;");

        Button insertTimers = new Button("INSERT TIMES");
        insertTimers.setPrefWidth(191);
        insertTimers.setPrefHeight(31);
        insertTimers.setLayoutX(12);
        insertTimers.setLayoutY(41);
        insertTimers.setStyle("-fx-background-color: #b1c8a3; -fx-font-family: DejaVu Sans; -fx-font-weight: bold; -fx-font-size: 15px;");

        insertTimers.setOnAction(event -> {
            scrambleLabel.setText(CodeGeneral.scramble());
            timeChampPane.setVisible(true);
        });
        newPane.getChildren().addAll(nameChampionship, insertTimers);
        return newPane;
    } // CREAR LOS PANELES DE LOS CAMPEONATOS EN EL QUE ESTA UN USUARIO

    @FXML
    void onCloseTimesChampAction(ActionEvent event) {
        timeChampPane.setVisible(false);
    }

    @FXML
    void onInserTimeChampAction(ActionEvent event) {
        if (champTimeTxt.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Campos vacíos.");
            alert.setHeaderText("¡ERROR!");
            alert.setContentText("Por favor, inserte un tiempo antes de continuar.");
            alert.showAndWait();
        } else {
            String tiempo = champTimeTxt.getText(); // GUARDAR EL TIEMPO QUE HA HECHO
            tiempo = tiempo.replace(',', '.'); // PARA QUE LAS DECIMAS DE LA BASE DE DATOS SE ESTABLEZCA BIEN

            /* ESTABLECES MINUTOS Y SEGUNDOS */
            int indiceMinutos = tiempo.indexOf(":"); // INDICE QUE INDICA EN QUE POSICION ESTA :
            String subMinutos = tiempo.substring(0, indiceMinutos); // ESTABLECE EL VALOR ANTES DEL :
            // PARA ESTABLECER EL VALOR DE LOS SEGUNDOS, SE COJE EL VALOR DESPUES DEL : HASTA EL FINAL
            String subSeconds = tiempo.substring(indiceMinutos + 1, indiceMinutos + (tiempo.length() - indiceMinutos));
            int idUser = CubeUserDAO.selectIdUser(CacheStatic.cubeUser.getMail());
            /*if (!TimeChampionshipDAO.createTimeChampionship(idUser, scrambleLabel.getText(), subMinutos, subSeconds, , )) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Tiempos fallidos.");
                alert.setHeaderText("¡ERROR!");
                alert.setContentText("No se ha podido insertar el tiempo.");
                alert.showAndWait();
            } // MENSAJE POR SI ALGO FALLA AL INSERTAR LOS DATOS*/
            scrambleLabel.setText(CodeGeneral.scramble()); // VOLVER A GENERAR EL SCRAMBLE
        } // SI NO ES USER DEMO, SE LE GUARDA LOS TIEMPOS EN ESA SESSION QUE ESTA SIENDO UTILIZADA


    }


    @FXML
    void onCloseUserChampList(ActionEvent event) {
        listUserChampPane.setVisible(false);
    }

    @FXML
    void onLeaveChampAction(ActionEvent event) {
        int idUser = CubeUserDAO.selectIdUser(CacheStatic.cubeUser.getMail());
        if (UserChampCompeteDAO.selectUserChamp(idChampActual, idUser)) {
            if (UserChampCompeteDAO.deleteUserChamp(idUser, idChampActual)) {
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
        String sql = "SELECT NAME_CHAMP, PRICE, NUMBER_PART, DESCRIPTION_CHAMP, REGISTRATION_DATE, MEMBERS_ONLY FROM CHAMPIONSHIP";
        String nameChamp, descriptionChamp;
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
                if (MemberDAO.selectMember(CacheStatic.cubeUser.getMail()) && membersOnly) {
                    paneChampScroll.getChildren().addAll(createChampionship(nameChamp, date, numberPart, price, descriptionChamp, membersOnly));
                } else if (MemberDAO.selectMember(CacheStatic.cubeUser.getMail()) && !membersOnly) {
                    paneChampScroll.getChildren().addAll(createChampionship(nameChamp, date, numberPart, price, descriptionChamp, membersOnly));
                } else if (!MemberDAO.selectMember(CacheStatic.cubeUser.getMail()) && !membersOnly) {
                    paneChampScroll.getChildren().addAll(createChampionship(nameChamp, date, numberPart, price, descriptionChamp, membersOnly));
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

    public void loadJoinChampionship(int idUser) {
        String sql = "SELECT NAME_CHAMP FROM CHAMPIONSHIP WHERE ID_CHAMP IN (SELECT ID_CHAMP FROM USER_CHAMP_COMPETE WHERE ID_USER = ?);";
        String nameChamp;
        try {
            Connection con = DatabaseConnection.conectar();
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, idUser);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                nameChamp = resultSet.getString("NAME_CHAMP");
                scrollChampJoinPane.getChildren().add(createJoinPane(nameChamp));
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
    }

}

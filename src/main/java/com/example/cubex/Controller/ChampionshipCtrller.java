package com.example.cubex.Controller;

import com.example.cubex.DAO.ChampionshipDAO;
import com.example.cubex.DAO.CubeUserDAO;
import com.example.cubex.DAO.CuberTypeDAO;
import com.example.cubex.DAO.MemberDAO;
import com.example.cubex.Main;
import com.example.cubex.Validator.Validator;
import com.example.cubex.model.CacheStatic;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
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
    LocalDate localDate = LocalDate.now(); // ATRIBUTO DIA ACTUAL

    @FXML
    void onCreateChampAction(ActionEvent event) {
        if (!MemberDAO.selectMember(CacheStatic.cubeUser.getMail())) {
            if (championNameTxt.getText().isEmpty() || (categoriesCB.getValue() == null && categoriesCB1.getValue() == null)
                    || datePickerTxxt.getValue() == null || numberPartTxt.getText().isEmpty() || descriptionChampTxt.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Campos vacíos.");
                alert.setHeaderText("¡ERROR!");
                alert.setContentText("Por favor, rellene todos los datos antes de continuar.");
                alert.showAndWait();
            } else if(!Validator.isNumeric(numberPartTxt.getText())){
                // SI EL NUMERO DE PARTICIPANTES CONTIENE UNA CADENA ENTONCES SALTA UN ERROR
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Formato no válido.");
                alert.setHeaderText("¡ERROR!");
                alert.setContentText("Por favor, rellene el campo de numero de participantes con un numero.");
                alert.showAndWait();
            } else if(datePickerTxxt.getValue().isBefore(localDate)){
                // SI LA FECHA SELECCIONADA ES MENOR AL DIA ACTUAL SALTA UN ERROR
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Formato no válido.");
                alert.setHeaderText("¡ERROR!");
                alert.setContentText("Por favor, elija una fecha mayor o igual a " + localDate + ".");
                alert.showAndWait();
            } else {
                int idUser = CubeUserDAO.selectIdUser(CacheStatic.cubeUser.getMail());
                if(!ChampionshipDAO.insertChampionship(idUser, championNameTxt.getText(), 0, Integer.parseInt(numberPartTxt.getText()),
                        descriptionChampTxt.getText(), datePickerTxxt.getValue(), false)) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Creación fallida.");
                    alert.setHeaderText("¡ERROR!");
                    alert.setContentText("No se ha podido crear el campeonato correctamente");
                    alert.showAndWait();
                } // SI NO SE HA INSERTADO BIEN SE MUESTRA UN MENSAJE DE ERROR
                newChampPane.setVisible(false);
            }
        } else {
            if (championNameTxt.getText().isEmpty() || (categoriesCB.getValue() == null || categoriesCB1.getValue() == null)
                    || datePickerTxxt.getValue() == null || numberPartTxt.getText().isEmpty() || descriptionChampTxt.getText().isEmpty()
                    || priceChampTxt.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Campos vacíos.");
                alert.setHeaderText("¡ERROR!");
                alert.setContentText("Por favor, rellene todos los datos antes de continuar.");
                alert.showAndWait();
            } else if(!Validator.isNumeric(numberPartTxt.getText()) || !Validator.isNumeric(priceChampTxt.getText())){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Formato no válido.");
                alert.setHeaderText("¡ERROR!");
                alert.setContentText("Por favor, rellene el campo de numero de participantes o el precio con un numero.");
                alert.showAndWait();
            }  else if(datePickerTxxt.getValue().isBefore(localDate)){
                // SI LA FECHA SELECCIONADA ES MENOR AL DIA ACTUAL SALTA UN ERROR
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Formato no válido.");
                alert.setHeaderText("¡ERROR!");
                alert.setContentText("Por favor, elija una fecha mayor o igual a " + localDate + ".");
                alert.showAndWait();
            } else {
                int idUser = CubeUserDAO.selectIdUser(CacheStatic.cubeUser.getMail());
                if(!ChampionshipDAO.insertChampionship(idUser, championNameTxt.getText(), Integer.parseInt(priceChampTxt.getText()),
                        Integer.parseInt(numberPartTxt.getText()), descriptionChampTxt.getText(), datePickerTxxt.getValue(), true)) {
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
    void onGeneralChampAction(ActionEvent event) {
        generalChampPane.setVisible(true);
        yourChampsPane.setVisible(false);
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

    @FXML
    void onYourChampionshipAction(ActionEvent event) {
        yourChampsPane.setVisible(true);
        generalChampPane.setVisible(false);
        newChampPane.setVisible(false);
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        yourChampsPane.setVisible(false);
        generalChampPane.setVisible(true);
        if (CubeUserDAO.selectUrl(CacheStatic.cubeUser.getMail())) {
            imageProfile.setImage(CubeUserDAO.imgUrlSelect(CacheStatic.cubeUser.getMail()));
        } // SI LA IMAGEN SE MODIFICO ENTONCES SE CARGA LA IMAGEN
        CuberTypeDAO.cubeCategory(categoriesCB);
        CuberTypeDAO.cubeCategory(categoriesCB1);
    }

}

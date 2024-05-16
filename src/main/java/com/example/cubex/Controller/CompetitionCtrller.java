package com.example.cubex.Controller;

import com.example.cubex.DAO.*;
import com.example.cubex.model.CacheStatic;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.sql.SQLException;
import java.sql.SQLOutput;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CompetitionCtrller extends CodeGeneral implements Initializable {
    @FXML
    private TextField times1Pane;
    @FXML
    private TextField times2Pane;
    @FXML
    private TextField chrono1Pane;
    @FXML
    private TextField chrono2Pane;
    @FXML
    private TextField cuber1Txt;
    @FXML
    private TextField cuber2Txt;
    @FXML
    private ComboBox categoriesCB;
    @FXML
    private Label chrono1Label;
    @FXML
    private Pane demoProfilePane;
    @FXML
    private Pane profilePage;

    @FXML
    private Label chrono2Label;
    LocalDate localDate = LocalDate.now();
    boolean isStopCuber1 = false; // ATRIBUTO PARA SABER CUANDO HAN PARADO EL TIEMPO
    boolean isStopCuber2 = false; // ATRIBUTO PARA SABER CUANDO HAN PARADO EL TIEMPO
    int numberCategoria, contador = CompetitionDAO.countCompetition();

    @FXML
    void onStartCuberAction() {
        if (cuber1Txt.getText().isEmpty() || cuber2Txt.getText().isEmpty() || categoriesCB.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Campos vacíos.");
            alert.setHeaderText("¡ERROR!");
            alert.setContentText("Por favor, completa todos los campos antes de continuar.");
            alert.showAndWait();
        } else {
            isStopCuber2 = false;
            isStopCuber1 = false;
            if (!StartCtrller.isDemo) {
                String nameCategory = String.valueOf(categoriesCB.getSelectionModel().getSelectedItem());
                numberCategoria = SessionDAO.idCategory(nameCategory);
            }
            // CUANDO SE PULSE START EL CRONOMETRO SE EMPEZARAN EN LAS DOS
            CodeGeneral.start(chrono2Label);
            CodeGeneral.start(chrono1Label);
        }

    }

    @FXML
    void onStopCuber1Action(ActionEvent event) {
        CodeGeneral.parar("chrono1Label");
        isStopCuber1 = true;
        insertTimesCompe();
    }

    @FXML
    void onStopCuber2Action(ActionEvent event) {
        CodeGeneral.parar("chrono2Label");
        isStopCuber2 = true;
        insertTimesCompe();
    }

    public void insertTimesCompe() {
        String scramble;
        if (isStopCuber1 && isStopCuber2) {
            if (!StartCtrller.isDemo) {
                scramble = scrambleLabel.getText();
                contador++;
                // GUARDAR EL TIEMPO QUE HAN HECHO
                String tiempo1 = chrono1Label.getText();
                String tiempo2 = chrono2Label.getText();
                // PARA QUE LAS DECIMAS DE LA BASE DE DATOS SE ESTABLEZCA BIEN REEMPLAZAMOS LA COMA POR PUNTO
                tiempo1 = tiempo1.replace(',', '.');
                tiempo2 = tiempo2.replace(',', '.');

                /* ESTABLECES MINUTOS Y SEGUNDOS */
                // INDICE QUE INDICA EN QUE POSICION ESTA :
                int indiceMinutosCuber1 = tiempo1.indexOf(":");
                int indiceMinutosCuber2 = tiempo2.indexOf(":");

                // ESTABLECE EL VALOR ANTES DEL :
                String subMinutos1 = tiempo1.substring(0, indiceMinutosCuber1);
                String subMinutos2 = tiempo2.substring(0, indiceMinutosCuber2);

                // PARA ESTABLECER EL VALOR DE LOS SEGUNDOS, SE COJE EL VALOR DESPUES DEL : HASTA EL FINAL
                String subSeconds1 = tiempo1.substring(indiceMinutosCuber1 + 1, indiceMinutosCuber1 + (tiempo1.length() - indiceMinutosCuber1));
                String subSeconds2 = tiempo2.substring(indiceMinutosCuber2 + 1, indiceMinutosCuber2 + (tiempo2.length() - indiceMinutosCuber2));

                int idUser = CubeUserDAO.selectIdUser(CacheStatic.cubeUser.getMail());
                // SE QUE NO ES OPTIMO PONER EL CONTADOR, PERO NO SABIA COMO SELECIONAR LA COMPETENCIA ASIQUE
                // CUENTO LAS COMPETICIONES QUE HAY Y SUMO UNO MAS Y ESE ES EL ID
                boolean compe = CompetitionDAO.insertCompe(contador, idUser, cuber1Txt.getText(), cuber2Txt.getText(), localDate);
                boolean timesCompe = TimeCompetitionDAO.createTimeCompe(scramble, subMinutos1, subSeconds1, subMinutos2, subSeconds2, contador, numberCategoria);
                boolean winnerCompe = CompetitionDAO.insertWinner(contador);
                System.out.println(winnerCompe);
                if (!compe || !timesCompe || !winnerCompe) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Tiempos fallidos.");
                    alert.setHeaderText("¡ERROR!");
                    alert.setContentText("No se ha podido insertar los tiempos.");
                    alert.showAndWait();
                }
            }
            scrambleLabel.setText(CodeGeneral.scramble()); // SE VUELVE A GENERAR EL SCRAMBLE
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        CompetitionDAO.countCompetition();
        CodeGeneral.onFalseMenus(demoProfilePane, profilePage, settingMenu, optionMenu, optionDemoPane, timesMenuCompe);
        closeTimesCompe.setVisible(false);
        scrambleLabel.setText(CodeGeneral.scramble());
        CodeGeneral.cubeCategory(categoriesCB);

        chrono1Label.setText("0:00,00");
        chrono2Label.setText("0:00,00");
        if(!StartCtrller.isDemo){
            if(SettingCtrller.isModifyImagen){
                onUpdateImgAction();
            }
        }
    }
}

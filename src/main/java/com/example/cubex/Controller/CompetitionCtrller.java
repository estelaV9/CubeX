package com.example.cubex.Controller;

import com.example.cubex.DAO.*;
import com.example.cubex.model.CacheStatic;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class CompetitionCtrller extends CodeGeneral implements Initializable {
    @FXML private TextField cuber1Txt;
    @FXML private TextField cuber2Txt;
    @FXML private ComboBox categoriesCB;
    @FXML private Label chrono1Label;
    @FXML private Label chrono2Label;

    LocalDate localDate = LocalDate.now(); // ATRIBUTO QUE GUARDA EL DIA ACTUAL
    boolean isStopCuber1 = true; // ATRIBUTO PARA SABER CUANDO HAN PARADO EL TIEMPO
    boolean isStopCuber2 = true; // ATRIBUTO PARA SABER CUANDO HAN PARADO EL TIEMPO
    boolean isStarted = false; // ATRIBUTO QUE GUARDA SI HA EMPEZADO EL CRONOMETRO
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
            if(isStopCuber2 && isStopCuber1) {
                if (!StartCtrller.isDemo) {
                    String nameCategory = String.valueOf(categoriesCB.getSelectionModel().getSelectedItem());
                    numberCategoria = SessionDAO.idCategory(nameCategory);
                } // SI NO ES USUARIO DEMO, SE GUARDA EL ID DE LA CATEGORIA
                // CUANDO SE PULSE START EL CRONOMETRO SE EMPEZARAN EN LAS DOS
                CodeGeneral.start(chrono2Label);
                CodeGeneral.start(chrono1Label);
                isStarted = true;
                isStopCuber2 = false;
                isStopCuber1 = false;
            } // SI LOS DOS HAN PARADO ENTONCES SE INICIA EL CRONOMETRO
        }

    }

    @FXML
    void onStopCuber1Action() {
        if(isStarted){
            CodeGeneral.parar("chrono1Label");
            isStopCuber1 = true;
            insertTimesCompe();
            if(isStopCuber1 && isStopCuber2){
                isStarted = false;
            } // SI LOS DOS HAN PARADO EN TODOS EL CRONOMETRO SE PARA
        } // SI INICIARON EL CRONOMETRO ENTONCES, SE PARA
    }

    @FXML
    void onStopCuber2Action() {
        if(isStarted) {
            CodeGeneral.parar("chrono2Label");
            isStopCuber2 = true;
            insertTimesCompe();
            if(isStopCuber1 && isStopCuber2){
                isStarted = false;
            } // SI LOS DOS HAN PARADO EN TODOS EL CRONOMETRO SE PARA
        } // SI INICIARON EL CRONOMETRO ENTONCES, SE PARA
    }

    public void insertTimesCompe() {
        String scramble;
        if (isStopCuber1 && isStopCuber2) { // SI HAN PARADO LOS DOS CRONOMETROS ENTONCES SE GUARDA
            if (!StartCtrller.isDemo) { // SE GUARDAN LOS TIEMPOS SI NO ES UN USUARIO DEMO
                scramble = scrambleLabel.getText(); // SE COGE EL SCRAMBLE ACTUAL
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

                /* INSERTAR TIEMPOS */
                int idUser = CubeUserDAO.selectIdUser(CacheStatic.cubeUser.getMail());
                // SE QUE NO ES OPTIMO PONER EL CONTADOR, PERO NO SABIA COMO SELECIONAR LA COMPETENCIA ASIQUE
                // CUENTO LAS COMPETICIONES QUE HAY Y SUMO UNO MAS Y ESE ES EL ID, COMO CADA COMPETICION SE CREA UNA NUEVA
                // NUNCA SE VA A REPETIR ID
                boolean compe = CompetitionDAO.insertCompe(contador, idUser, cuber1Txt.getText(), cuber2Txt.getText(), localDate);
                boolean timesCompe = TimeCompetitionDAO.createTimeCompe(scramble, subMinutos1, subSeconds1, subMinutos2, subSeconds2, contador, numberCategoria);
                boolean winnerCompe = CompetitionDAO.insertWinner(contador);
                if (!compe || !timesCompe || !winnerCompe) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Tiempos fallidos.");
                    alert.setHeaderText("¡ERROR!");
                    alert.setContentText("No se ha podido insertar los tiempos.");
                    alert.showAndWait();
                } // MENSAJE DE ERROR POR SI ALGO FALLA
            }
            scrambleLabel.setText(CodeGeneral.scramble()); // SE VUELVE A GENERAR EL SCRAMBLE
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        CodeGeneral.onFalseMenus(demoProfilePane, profilePage, settingMenu, optionMenu, optionDemoPane, timesMenuCompe);
        closeTimesCompe.setVisible(false);
        scrambleLabel.setText(CodeGeneral.scramble());
        CodeGeneral.cubeCategory(categoriesCB);
        chrono1Label.setText("0:00,00");
        chrono2Label.setText("0:00,00");
        if(!StartCtrller.isDemo){
            CompetitionDAO.countCompetition();
            if(CubeUserDAO.selectUrl(CacheStatic.cubeUser.getMail())){
                onUpdateImgAction();
            } // SI LA IMAGEN SE MODIFICO ENTONCES SE CARGA LA IMAGEN
        } // SI NO ES USUARIO DEMO SE CARGA LA FOTO QUE SE HAYA PUESTO
    }// CUANDO SE INICIA, SE CUENTA LOS TIEMPOS DE LA COMPETENCIA, SE ELIMINA TODAS LAS VENTANAS EMERGENTES,
     // SE ESTABLECE UN SCRAMBLE, SE CARGAN LAS CATEGORIAS Y LA IMAGEN SI LA ACTUALIZO EL USUARIO ANTERIORMENTE
}

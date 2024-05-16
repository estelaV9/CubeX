package com.example.cubex.Controller;

import com.example.cubex.DAO.SessionDAO;
import com.example.cubex.DAO.TimeTrainingDAO;
import com.example.cubex.model.CacheStatic;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class PageCtrller extends CodeGeneral implements Initializable {
    @FXML private Pane demoProfilePane;
    @FXML private Label chrono1Label;
    @FXML private Pane profilePage;

    int idSession;
    LocalDate localDate = LocalDate.now(); // ATRIBUTO QUE GUARDA EL DIA ACTUAL

    @FXML
    void start() {
        if (!StartCtrller.isDemo) {
            if (SessionDAO.numberSession(CacheStatic.cubeUser.getMail()) == 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("No hay sesiones.");
                alert.setHeaderText("¡ERROR!");
                alert.setContentText("Por favor, crea una sesion para poder guardar los tiempos.");
                alert.showAndWait();
            } else if (SessionCtrller.isUsing == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("No hay sesion en uso.");
                alert.setHeaderText("¡ERROR!");
                alert.setContentText("Por favor, elige una sesion para poder guardar los tiempos.");
                alert.showAndWait();
            } else {
                idSession = TimeTrainingDAO.selectSession(SessionCtrller.isUsing); // GUARDAR EL ID_SESSION DE LA SESSION UTILIZADA
            }
        } // SI NO ES USER DEMO, SE LE OBLIGARA A USAR UNA SESION PARA GUARDAR LOS TIEMPOS EN ESA SESSION
        CodeGeneral.start(chrono1Label);
    } // EMPEZAR EL CRONOMETRO

    @FXML
    void parar() {
        CodeGeneral.parar("chrono1Label");
        if (!StartCtrller.isDemo) {
            String tiempo = chrono1Label.getText(); // GUARDAR EL TIEMPO QUE HA HECHO
            tiempo = tiempo.replace(',', '.'); // PARA QUE LAS DECIMAS DE LA BASE DE DATOS SE ESTABLEZCA BIEN

            /* ESTABLECES MINUTOS Y SEGUNDOS */
            int indiceMinutos = tiempo.indexOf(":"); // INDICE QUE INDICA EN QUE POSICION ESTA :
            String subMinutos = tiempo.substring(0, indiceMinutos); // ESTABLECE EL VALOR ANTES DEL :
            // PARA ESTABLECER EL VALOR DE LOS SEGUNDOS, SE COJE EL VALOR DESPUES DEL : HASTA EL FINAL
            String subSeconds = tiempo.substring(indiceMinutos + 1, indiceMinutos + (tiempo.length() - indiceMinutos));

            if (!TimeTrainingDAO.createTimeTraining(scrambleLabel.getText(), subMinutos, subSeconds, localDate, idSession)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Tiempos fallidos.");
                alert.setHeaderText("¡ERROR!");
                alert.setContentText("No se ha podido insertar el tiempo.");
                alert.showAndWait();
            } // MENSAJE POR SI ALGO FALLA AL INSERTAR LOS DATOS
        } // SI NO ES USER DEMO, SE LE GUARDA LOS TIEMPOS EN ESA SESSION QUE ESTA SIENDO UTILIZADA
        scrambleLabel.setText(CodeGeneral.scramble()); // SE VUELVE A GENERAR EL SCRAMBLE
    } // PARAR EL CRONOMETRO

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        closeBtt.setVisible(false);
        CodeGeneral.onFalseMenus(demoProfilePane, profilePage, settingMenu, optionMenu, optionDemoPane, timesMenu);
        scrambleLabel.setText(CodeGeneral.scramble());
        if(!StartCtrller.isDemo){
            if(SettingCtrller.isModifyImagen){
                onUpdateImgAction();
            } // SI SE HA PUESTO UNA FOTO SE CARGA ESA FOTO
        } // SI NO ES USUARIO DEMO SE CARGA LA FOTO QUE SE HAYA PUESTO
    } // CUANDO SE INICIA, SE ELIMINA TODAS LAS VENTANAS EMERGENTES, SE ESTABLECE UN SCRAMBLE Y SE CARGA LA IMAGEN SI
      // LA ACTUALIZO EL USUARIO ANTERIORMENTE
}

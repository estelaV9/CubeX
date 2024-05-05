package com.example.cubex.Controller;

import com.example.cubex.Database.DatabaseConnection;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.util.Duration;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

public class PageCtrller extends CodeGeneral implements Initializable {

    static ArrayList<String> categories = new ArrayList();
    @FXML
    private TextField chronoPane;
    @FXML
    private ComboBox categoriesCB;
    @FXML
    private Label chrono;
    int min = 0, seg = 0, cent = 0;
    Timeline timeline;

    @FXML
    void start(ActionEvent event) {
        timeline = new Timeline(new KeyFrame(Duration.millis(10), event1 -> {
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
                timeString = min + ":0" + seg + ":" + cent;
            } else {
                timeString = min + ":" + seg + ":" + cent;
            }

            // CUANDO LLEGUE A 10 MINUTOS EL TIEMPO SE PARA
            if (min == 10) {
                timeString = min + ":0" + seg + ":" + cent;
                timeline.stop();
            }
            chrono.setText(timeString); // MOSTRAR EL TIEMPO
        }));
        timeline.setCycleCount(Timeline.INDEFINITE); // DE TIEMPO INDEFINIDO
        timeline.play(); // COMENZAMOS NUESTRO TIMELINE
    }

    @FXML
    void parar(ActionEvent event) throws SQLException {
        timeline.stop();
        /*Connection connection = DatabaseConnection.conectar();
        String sqlInsert = "INSERT TIMER_TRAINING VALUES (?, ?, ?, ?);";
        PreparedStatement statement = connection.prepareStatement(sqlInsert);
        statement.setString(1, scramblePane.getText());*/

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        settingMenu.setVisible(false);
        optionMenu.setVisible(false);
        optionDemoPane.setVisible(false);
        timesMenu.setVisible(false);
        closeBtt.setVisible(false);
        Scramble scramble = new Scramble();
        int random = (int) (Math.random() * (25 - 20 + 1) + 20);
        scramblePane.setText(scramble.generateScramble(random));
        chrono.setText("0:00,00");
        categoriesCB.setPromptText("CATEGORY");

        cubeCategory();
        categoriesCB.getItems().addAll(categories);
        categoriesCB.setStyle("-fx-background-color: #325743; ");


}


    public static void cubeCategory(){
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


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

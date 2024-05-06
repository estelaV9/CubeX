package com.example.cubex.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CompetitionCtrller extends CodeGeneral implements Initializable {
    @FXML private TextField times1Pane;
    @FXML private TextField times2Pane;
    @FXML private TextField chrono1Pane;
    @FXML private TextField chrono2Pane;
    @FXML private TextField cuber1Txt;
    @FXML private TextField cuber2Txt;
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
    @FXML
    void start(ActionEvent event) {
        CodeGeneral.start(chrono2Label);
        CodeGeneral.start(chrono1Label);
    }

    @FXML
    void parar(ActionEvent event) throws SQLException {
        CodeGeneral.parar();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        CodeGeneral.onFalseMenus(demoProfilePane, profilePage, settingMenu, optionMenu, optionDemoPane, timesMenu);
        scramblePane.setText(CodeGeneral.scramble());
        CodeGeneral.cubeCategory(categoriesCB);

        chrono1Label.setText("0:00,00");
        chrono1Label.setStyle("-fx-font-size: 45px; -fx-padding: 5px; ");
        chrono2Label.setText("0:00,00");
        chrono2Label.setStyle("-fx-font-size: 45px; -fx-padding: 5px; ");
    }
}

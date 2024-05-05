package com.example.cubex.Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class CompetitionCtrller extends CodeGeneral implements Initializable {
    @FXML private TextField times1Pane;
    @FXML private TextField times2Pane;
    @FXML private TextField chrono1Pane;
    @FXML private TextField chrono2Pane;
    @FXML private TextField cuber1Txt;
    @FXML private TextField cuber2Txt;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        settingMenu.setVisible(false);
        optionMenu.setVisible(false);
        optionDemoPane.setVisible(false);
        timesMenu.setVisible(false);
        closeBtt.setVisible(false);
    }
}

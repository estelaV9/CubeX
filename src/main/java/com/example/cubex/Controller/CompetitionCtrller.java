package com.example.cubex.Controller;

import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class CompetitionCtrller extends codeGeneral implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        settingMenu.setVisible(false);
        optionMenu.setVisible(false);
        timesMenu.setVisible(false);
        closeBtt.setVisible(false);
    }
}

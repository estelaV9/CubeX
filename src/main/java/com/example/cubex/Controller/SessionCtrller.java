package com.example.cubex.Controller;

import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import javax.swing.*;
import java.net.URL;
import java.util.ResourceBundle;

public class SessionCtrller extends CodeGeneral implements Initializable {
    @FXML private Button addSessionBtt;
    @FXML private Button cancelSessionBtt;
    @FXML private Button createSessionBtt;
    @FXML private Pane nameSession;
    @FXML private ChoiceBox<?> methodCube;
    @FXML private ChoiceBox<?> typeCube;
    @FXML
    private AnchorPane paneScroll;

    @FXML
    private Pane demoProfilePane;
    @FXML
    private Pane profilePage;
    @FXML
    private Pane SessionPane;
    @FXML
    private ScrollPane scroll;
    private double nextPaneY = 10; // Posición Y del próximo panel



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        CodeGeneral.onFalseMenus(demoProfilePane, profilePage, settingMenu, optionMenu, optionDemoPane, nameSession);
        sessionName.setPromptText("Name of Session");
        sessionName.setStyle("-fx-prompt-text-fill: #9B9B9B; -fx-background-color: #b1c8a3;");
        scroll.setFitToWidth(false);
        paneScroll.prefWidth(50);
    }


    @FXML void onAddSessionAction(ActionEvent event) {
        nameSession.setVisible(true);
    }
    @FXML void onCreateSessionAction(ActionEvent event) {
        nameSession.setVisible(false);
        Pane newPane = new Pane();
        newPane.setLayoutX(10); // ESPACIADO HORIZONTAL
        newPane.setLayoutY(nextPaneY);
        // TAMANIO DEL PANEL
        newPane.setPrefHeight(220);
        newPane.setPrefWidth(220);
        newPane.setStyle("-fx-background-color:  #325743; -fx-background-radius: 24; -fx-border-color: black; -fx-border-radius: 24;");

        // Agregar elementos al nuevo panel
        Label createSessionLabel = new Label("NAME");
        createSessionLabel.setLayoutX(79);
        createSessionLabel.setLayoutY(8);
        createSessionLabel.setStyle("-fx-underline: true; -fx-font-family: DejaVu Sans; -fx-font-weight: bold; -fx-font-size: 21px;");

        Label typeCubeLabel = new Label("Type of cube :");
        typeCubeLabel.setLayoutX(14);
        typeCubeLabel.setLayoutY(39);
        typeCubeLabel.setStyle("-fx-font-family: System; -fx-font-size: 15px;");

        Label methodCubeLabel = new Label("Method of cube:");
        methodCubeLabel.setLayoutX(14);
        methodCubeLabel.setLayoutY(60);
        methodCubeLabel.setStyle("-fx-font-family: System; -fx-font-size: 15px;");

        Button details = new Button("DETAILS");
        details.setLayoutX(17);
        details.setLayoutY(93);
        details.setPrefWidth(186);
        details.setPrefHeight(31);
        details.setStyle("-fx-background-color: #b1c8a3; -fx-font-family: DejaVu Sans; -fx-font-weight: bold; -fx-font-size: 15px;");

        Button use = new Button("USE");
        use.setLayoutX(17);
        use.setLayoutY(160);
        use.setPrefWidth(99);
        use.setPrefHeight(31);
        use.setStyle("-fx-background-color: #74cc5e; -fx-font-family: DejaVu Sans; -fx-font-weight: bold; -fx-font-size: 15px;");

        Button delete = new Button("DELETE");
        delete.setLayoutX(118);
        delete.setLayoutY(160);
        delete.setPrefWidth(84);
        delete.setPrefHeight(31);
        delete.setStyle("-fx-background-color: #a82f2a; -fx-font-family: DejaVu Sans; -fx-font-weight: bold; -fx-font-size: 15px;");



        // Agregar los elementos al nuevo panel
        newPane.getChildren().addAll(
                typeCubeLabel,
                methodCubeLabel,
                createSessionLabel,
                details, use, delete
        );

        // Aumentar la posición Y para el próximo panel
        nextPaneY += newPane.getPrefHeight() + 10; // 10 es el espacio entre paneles

        // Agregar el nuevo panel al panel scrollable
        paneScroll.getChildren().add(newPane);

        // Si el nuevo panel está fuera del área visible, ajustar el tamaño del panel scrollable
        if (nextPaneY > paneScroll.getPrefHeight()) {
            paneScroll.setPrefHeight(nextPaneY + 10); // 10 es el espacio adicional para evitar bordes cortados
        }
    }
    @FXML void onCancelSessionCreate(ActionEvent event) {
        int opcion = JOptionPane.showConfirmDialog(null,
                "¿Estás seguro de que quieres cancelar la creación de la sesión?\n" +
                        "Todos los datos ingresados hasta ahora se perderán.", "Confirmación", JOptionPane.YES_NO_OPTION);
        if (opcion == JOptionPane.YES_OPTION) {
            nameSession.setVisible(false);
        }
    }









}

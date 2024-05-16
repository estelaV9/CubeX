package com.example.cubex.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class ImagenCtrller {
    @FXML private ImageView imagenView;
    @FXML private Button backSettingBtt;

    public static File selectedFile; // SE HACE ESTATICO PARA QUE LAS OTRAS VISTAS PUEDAN ACCEDER AL ARCHIVO

    @FXML
    void onLoadImagenAction() {
        /*ABRIR EL EXPLORADOR DE ARCHIVOS Y ELEGIR UNA IMAGEN*/
        // SE CREA UN OBJETO FILECHOOSER
        FileChooser fileChooser = new FileChooser();

        // SE CONFIGURA PARA QUE SOLO MUESTRE ARCHIVOS DE IMAGEN
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Imágenes", "*.png", "*.jpg"));

        // SE MUESTRA EL EXPLORADOR DE ARCHIVOS Y SE OBTIENE EL ARCHIVO SELECCIONADO POR EL USUARIO
        selectedFile = fileChooser.showOpenDialog(null);

        // SE MUESTRA LA IMAGEN EN EL IMAGENVIEW
        imagenView.setImage(returnImagen(selectedFile));
    }

    public static Image returnImagen(File selectedFile) {
        Image image = null;
        // SI EL USUARIO SELECIONO UN ARCHIVO
        if (selectedFile != null) {
            // A PARTIR DEL ARCHIVO QUE HA SELECIONADO SE GUARDA LA URL
            String fileUrl = selectedFile.toURI().toString();
            // SE CREA LA IMAGEN A PARTIR DE LA URL
            image = new Image(fileUrl);
        }
        return image;
    } // PARA QEU RETORNE LA MISMA IMAGEN EN LAS DISTINTAS VISTAS SE HACE UN METODO ESTATICO QUE DEVUELVE UNA IMAGEN CON
      // EL ARCHIVO SELECCIONADO

    @FXML
    void onBackSettingAction(ActionEvent event) {
        Stage myStage = (Stage) this.backSettingBtt.getScene().getWindow();
        myStage.close();
    } // SE CIERRA SOLO LA VISTA DE LA IMAGEN
}

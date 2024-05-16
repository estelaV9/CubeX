package com.example.cubex.Controller;

import com.example.cubex.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;

public class ImagenCtrller {

    @FXML
    private ImageView imagenView;

    @FXML
    private Button loadImagenBtt;

    @FXML
    private Button backSettingBtt;




    public static File selectedFile;
    @FXML
    void onLoadImagenAction(ActionEvent event) {
        // Crear un objeto FileChooser
        FileChooser fileChooser = new FileChooser();

        // Configurar el FileChooser para que solo muestre archivos de imagen
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Imágenes", "*.png", "*.jpg", "*.gif")
        );

        // Mostrar el diálogo de selección de archivo y obtener el archivo seleccionado
        selectedFile = fileChooser.showOpenDialog(null);

        // Mostrar la imagen en el ImageView
        imagenView.setImage(returnImagen(selectedFile));
    }

    public static Image returnImagen (File selectedFile) {
        Image image = null;
        // Si el usuario seleccionó un archivo
        if (selectedFile != null) {
            // Crear una URL a partir del archivo seleccionado
            String fileUrl = selectedFile.toURI().toString();
            // Crear una imagen a partir de la URL
            image = new Image(fileUrl);
        }
        return image;
    }



    @FXML
    void onBackSettingAction(ActionEvent event) {
        Stage myStage = (Stage) this.backSettingBtt.getScene().getWindow();
        myStage.close();


    }

}

package com.example.cubex.Controller;

import com.example.cubex.DAO.CubeUserDAO;
import com.example.cubex.model.CacheStatic;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
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
    boolean isOpenChooser = false; // CONTADOR DE VECES QUE HA TOCADO LOAD IMAGEN

     /** NOTA : preferiblemente elegir una imagen redondeada. He investidado el como redondearlo (con paneles, con circle,
     * la propia imagen...) pero no he conseguido modificarlo.
     * Para que quede más visual elige una redondeada (en la carpeta Image dejo 2 por si quieres probar).
     * Funciona bien con una imagen cualquiera, aunque me hubiese gustado que cuando se cargara cualquier imagen se
     * redondeara */

    @FXML
    void onLoadImagenAction() {
        /*ABRIR EL EXPLORADOR DE ARCHIVOS Y ELEGIR UNA IMAGEN*/
        // SE CREA UN OBJETO FILECHOOSER
        FileChooser fileChooser = null;
        if(!isOpenChooser) {
            fileChooser = new FileChooser();

            // SE CONFIGURA PARA QUE SOLO MUESTRE ARCHIVOS DE IMAGEN
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Imágenes", "*.png", "*.jpg"));
            isOpenChooser = true;
        } // SI YA ESTA ABIERTO EL EXPLORADOR DE ARCHIVOS QUE NO SE VUELVA A ABRIR
        if(fileChooser != null) {

            // SE MUESTRA EL EXPLORADOR DE ARCHIVOS Y SE OBTIENE EL ARCHIVO SELECCIONADO POR EL USUARIO
            selectedFile = fileChooser.showOpenDialog(null);


            // SE MUESTRA LA IMAGEN EN EL IMAGENVIEW
            imagenView.setImage(returnImagen(selectedFile));

            if (CubeUserDAO.urlUpdate(selectedFile, CacheStatic.cubeUser.getMail())) {
                isOpenChooser = false;
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Imagen actualizada");
                alert.setHeaderText("¡Éxito!");
                alert.setContentText("La imagen se ha actualizado correctamente.");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error al actualizar la imagen");
                alert.setHeaderText("¡ERROR!");
                alert.setContentText("La imagen no se ha podido actualizar.");
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Imagen no seleccionada");
            alert.setHeaderText("¡ERROR!");
            alert.setContentText("Por favor, eliga una imagen para cargar.");
            alert.showAndWait();
        }
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

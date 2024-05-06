package com.example.cubex.model;

import com.example.cubex.Controller.RegistrationCtrller;
import com.example.cubex.Database.DatabaseConnection;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class CubeUserDAO {
    public static boolean successfulCreation = false;
    public static boolean invalidLogin = false;

    public static void insertarUsuarios(CubeUser cubeUser) {
        String sqlInsert = "INSERT INTO cube_users (NAME_USER, PASSWORD_USER, MAIL, REGISTRATION_DATE) " +
                "VALUES (?, ?, ?, ?);";
        try { // EXCEPCION PARA CONTROLAR QUE NO HAYA DOS NOMBRES DE USUARIOS IGUALES
            Connection connection = DatabaseConnection.conectar();
            if (connection != null) {
                PreparedStatement statement = connection.prepareStatement(sqlInsert);
                statement.setString(1, cubeUser.getNameUser());
                statement.setString(2, cubeUser.getPasswordUser());
                statement.setString(3, cubeUser.getMail());
                statement.setString(4, String.valueOf(cubeUser.getRegistrationDate()));

                int rowsInserted = statement.executeUpdate();

                // COMPROBAR SI EL NOMBRE INTRODUCIDO YA EXISTE
                if (rowsInserted > 0) {
                    RegistrationCtrller.nameUser = cubeUser.getNameUser();
                    // SI SE INSERTO EL USUARIO, MOSTRAR UN MENSAJE DE EXITO
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Creación de usuario");
                    alert.setHeaderText("Creación exitosa");
                    alert.setContentText("Se ha creado el usuario correctamente.");
                    alert.showAndWait();
                    // IR A LA PAGINA PRINCIPAL DESPUES DE HABER CREADO EL USUARIO
                    successfulCreation = true;
                }
                connection.close();
            } else {
                // SI LA CONEXION ES NULL MOSTRAR UN ERROR
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error de conexión.");
                alert.setHeaderText("¡Error al conectar con la base de datos!");
                alert.setContentText("No se pudo establecer una conexión con la base de datos." +
                        " \nPor favor, asegúrese de que el servidor de la base de datos esté en funcionamiento " +
                        "y que la configuración de conexión sea correcta.");
                alert.showAndWait();
            }
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error de conexión.");
            alert.setHeaderText("¡ERROR!");
            alert.setContentText("Error al conectar a la base de datos: " + e.getMessage());
            alert.showAndWait();
        }
    }

    public static void logearUsuario(CubeUser cubeUser) {
        try {
            Connection connection = DatabaseConnection.conectar();
            // SI LA CONEXION ES NULA MANDAR UN MENSAJE
            if (connection != null) {
                // COMPROBAR SI EL USUARIO INTRODUCIDO YA EXISTE
                String sqlQuery = "SELECT * FROM CUBE_USERS WHERE MAIL = ? AND PASSWORD_USER = ?;";
                PreparedStatement statementQuery = connection.prepareStatement(sqlQuery);
                statementQuery.setString(1, cubeUser.getMail());
                statementQuery.setString(2, cubeUser.getPasswordUser());
                ResultSet resultSet = statementQuery.executeQuery();
                if (!resultSet.next()) {
                    // SI EL USUARIO NO EXISTE, MOSTRAR UN MENSAJE DE ERROR
                    invalidLogin = true;
                } else {
                    RegistrationCtrller.nameUser = resultSet.getString("NAME_USER");
                    // IR A LA PAGINA PRINCIPAL DESPUES DE HABER INICIADO SESION
                }
                connection.close();
            } else {
                // SI LA CONEXION ES NULL MOSTRAR UN ERROR
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error de conexión.");
                alert.setHeaderText("¡Error al conectar con la base de datos!");
                alert.setContentText("No se pudo establecer una conexión con la base de datos." +
                        " \nPor favor, asegúrese de que el servidor de la base de datos esté en funcionamiento " +
                        "y que la configuración de conexión sea correcta.");
                alert.showAndWait();
            }
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error de conexión.");
            alert.setHeaderText("¡ERROR!");
            alert.setContentText("Error al conectar a la base de datos: " + e.getMessage());
            alert.showAndWait();
        }
    }

}

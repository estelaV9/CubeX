package com.example.cubex.DAO;

import com.example.cubex.Controller.RegistrationCtrller;
import com.example.cubex.Controller.SettingCtrller;
import com.example.cubex.Controller.StartCtrller;
import com.example.cubex.Database.DatabaseConnection;
import com.example.cubex.Main;
import com.example.cubex.model.CacheStatic;
import com.example.cubex.model.CubeUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.invoke.StringConcatFactory;
import java.security.PublicKey;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CubeUserDAO {
    public static boolean successfulCreation = false;
    public static boolean successfulDelete = false;
    public static boolean successfulModify = false;
    public static boolean successfulModifyUser = false;
    public static boolean successfulModifyProUser = false;
    public static boolean invalidLogin = false;
    public static List<CubeUser> users = new ArrayList<>();

    public static void insertarUsuarios(String nameUser, String passwdUser, String mailUser, LocalDate registration) {
        String sqlInsert = "INSERT INTO cube_users (NAME_USER, PASSWORD_USER, MAIL, REGISTRATION_DATE) " +
                "VALUES (?, ?, ?, ?);";
        try { // EXCEPCION PARA CONTROLAR QUE NO HAYA DOS NOMBRES DE USUARIOS IGUALES
            Connection connection = DatabaseConnection.conectar();
            PreparedStatement statement = connection.prepareStatement(sqlInsert);
            statement.setString(1, nameUser);
            statement.setString(2, passwdUser);
            statement.setString(3, mailUser);
            statement.setString(4, String.valueOf(registration));
            int rowsInserted = statement.executeUpdate();
            // COMPROBAR SI EL NOMBRE INTRODUCIDO YA EXISTE
            if (rowsInserted > 0) {
                successfulCreation = true;
            } else {
                successfulCreation = false;
            }
            connection.close();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error de conexión.");
            alert.setHeaderText("¡ERROR!");
            alert.setContentText("Error al conectar a la base de datos: " + e.getMessage());
            alert.showAndWait();
        }
    }

    public static void logearUsuario(String mail, String password) {
        try {
            Connection connection = DatabaseConnection.conectar();
            // SI LA CONEXION ES NULA MANDAR UN MENSAJE
            // COMPROBAR SI EL USUARIO INTRODUCIDO YA EXISTE
            String sqlQuery = "SELECT * FROM CUBE_USERS WHERE MAIL = ? AND PASSWORD_USER = ?";
            PreparedStatement statementQuery = connection.prepareStatement(sqlQuery);
            statementQuery.setString(1, mail);
            statementQuery.setString(2, password);
            ResultSet resultSet = statementQuery.executeQuery();
            if (!resultSet.next()) {
                // SI EL USUARIO NO EXISTE, MOSTRAR UN MENSAJE DE ERROR
                invalidLogin = true;
            } else {
                invalidLogin = false;
            }
            connection.close();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error de conexión.");
            alert.setHeaderText("¡ERROR!");
            alert.setContentText("Error al conectar a la base de datos: " + e.getMessage());
            alert.showAndWait();
        }
    }


    public static void deleteUser(String mailUser) {
        try {
            Connection connection = DatabaseConnection.conectar();
            String sqlDelete = "DELETE FROM CUBE_USERS WHERE MAIL = ?";
            PreparedStatement statement = connection.prepareStatement(sqlDelete);
            statement.setString(1, mailUser);
            int rowsDelete = statement.executeUpdate();
            if (rowsDelete > 0) {
                // INVALIDAR LA SESION
                CacheStatic.cubeUser = null;
                successfulDelete = true;
            } else {
                successfulDelete = false;
            }
            connection.close(); // Cerrar conexión a la base de datos
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error de conexión.");
            alert.setHeaderText("¡ERROR!");
            alert.setContentText("Error al conectar a la base de datos: " + e.getMessage());
            alert.showAndWait();
        }
    }


    public static void modifyPassword(String newPassword, String mailUser) {
        try {
            Connection connection = DatabaseConnection.conectar();
            String sqlUpdate = "UPDATE CUBE_USERS SET PASSWORD_USER = ? WHERE MAIL = ?";
            PreparedStatement statement = connection.prepareStatement(sqlUpdate);
            statement.setString(1, newPassword);
            statement.setString(2, mailUser);
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                successfulModify = true;
            } else {
                successfulModify = false;
            }
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error de conexión.");
            alert.setHeaderText("¡ERROR!");
            alert.setContentText("Error al conectar a la base de datos: " + e.getMessage());
            alert.showAndWait();
        }
    }

    public static void modifyUser(String nameUser, String newMailUser, String roleUser, String mailUser) {
        try {
            Connection connection = DatabaseConnection.conectar();
            String sqlUpdate = "UPDATE CUBE_USERS SET NAME_USER = ?, MAIL = ?, ROLE_USER = ?" +
                    " WHERE MAIL = ?";
            PreparedStatement statement = connection.prepareStatement(sqlUpdate);
            statement.setString(1, nameUser);
            statement.setString(2, newMailUser);
            statement.setString(3, roleUser);
            statement.setString(4, mailUser);
            int rowsUpdate = statement.executeUpdate();
            if (rowsUpdate > 0) {
                successfulModifyUser = true;
            } else {
                successfulModifyUser = false;
            }
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error de conexión.");
            alert.setHeaderText("¡ERROR!");
            alert.setContentText("Error al conectar a la base de datos: " + e.getMessage());
            alert.showAndWait();
        }
    }


    public static void modifyPro(String mailUser) {
        try {
            Connection connection = DatabaseConnection.conectar();
            String sqlUpdate = "UPDATE CUBE_USERS SET ROLE_USER = 'MEMBER' WHERE MAIL = ?" +
                    " AND ROLE_USER = 'USER'";
            PreparedStatement statement = connection.prepareStatement(sqlUpdate);
            statement.setString(1, mailUser);
            int rowsUpdate = statement.executeUpdate();
            if (rowsUpdate > 0) {
                successfulModifyProUser = true;
            } else {
                successfulModifyProUser = false;
            }
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error de conexión.");
            alert.setHeaderText("¡ERROR!");
            alert.setContentText("Error al conectar a la base de datos: " + e.getMessage());
            alert.showAndWait();
        }
    }

    public static List<CubeUser> listUser(String option , String category) {
        try {
            Connection connection = DatabaseConnection.conectar();
            String sql = "SELECT NAME_USER, LEVEL_USER, ROLE_USER FROM CUBE_USERS";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String nameUser = resultSet.getString("NAME_USER");
                int level = resultSet.getInt("LEVEL_USER");
                String roleUser = resultSet.getString("ROLE_USER");
                CubeUser cubeUser = new CubeUser(nameUser, level, roleUser);
                users.add(cubeUser);

            }
        } catch (SQLException e) {
            System.out.println("error " + e);
        }
        return users;
    }

}

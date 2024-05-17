package com.example.cubex.DAO;

import com.example.cubex.Database.DatabaseConnection;
import com.example.cubex.model.CacheStatic;
import com.example.cubex.model.CubeUser;
import javafx.scene.control.Alert;
import java.awt.*;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.image.Image;

public class CubeUserDAO {
    public static boolean isMember = false;

    public static boolean insertarUsuarios(String nameUser, String passwdUser, String mailUser, LocalDate registration) {
        boolean successfulCreation = false;
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
            }
            connection.close();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error de conexión.");
            alert.setHeaderText("¡ERROR!");
            alert.setContentText("Error al conectar a la base de datos: " + e.getMessage());
            alert.showAndWait();
        }
        return successfulCreation;
    }

    public static boolean logearUsuario(String mail, String password) {
        boolean invalidLogin = false;
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
            }
            connection.close();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error de conexión.");
            alert.setHeaderText("¡ERROR!");
            alert.setContentText("Error al conectar a la base de datos: " + e.getMessage());
            alert.showAndWait();
        }
        return invalidLogin;
    }


    public static boolean deleteUser(String mailUser) {
        boolean successfulDelete = false;
        try {
            Connection connection = DatabaseConnection.conectar();
            String sqlDelete = "DELETE FROM CUBE_USERS WHERE MAIL = ?";
            PreparedStatement statement = connection.prepareStatement(sqlDelete);
            statement.setString(1, mailUser);
            int rowsDelete = statement.executeUpdate();
            if (rowsDelete > 0) {
                CacheStatic.cubeUser = null; // INVALIDAR LA SESION
                successfulDelete = true;
            }
            connection.close(); // Cerrar conexión a la base de datos
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error de conexión.");
            alert.setHeaderText("¡ERROR!");
            alert.setContentText("Error al conectar a la base de datos: " + e.getMessage());
            alert.showAndWait();
        }
        return successfulDelete;
    }


    public static boolean modifyPassword(String newPassword, String mailUser) {
        boolean successfulModify = false;
        try {
            Connection connection = DatabaseConnection.conectar();
            String sqlUpdate = "UPDATE CUBE_USERS SET PASSWORD_USER = ? WHERE MAIL = ?";
            PreparedStatement statement = connection.prepareStatement(sqlUpdate);
            statement.setString(1, newPassword);
            statement.setString(2, mailUser);
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                successfulModify = true;
            }
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error de conexión.");
            alert.setHeaderText("¡ERROR!");
            alert.setContentText("Error al conectar a la base de datos: " + e.getMessage());
            alert.showAndWait();
        }
        return successfulModify;
    }

    public static boolean modifyUser(String nameUser, String newMailUser, String roleUser, String mailUser) {
        boolean successfulModifyUser = false;
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
            }
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error de conexión.");
            alert.setHeaderText("¡ERROR!");
            alert.setContentText("Error al conectar a la base de datos: " + e.getMessage());
            alert.showAndWait();
        }
        return successfulModifyUser;
    }


    public static boolean modifyPro(String mailUser) {
        try {
            Connection connection = DatabaseConnection.conectar();
            String sqlUpdate = "UPDATE CUBE_USERS SET ROLE_USER = 'MEMBER' WHERE MAIL = ?" +
                    " AND ROLE_USER = 'USER'";
            PreparedStatement statement = connection.prepareStatement(sqlUpdate);
            statement.setString(1, mailUser);
            int rowsUpdate = statement.executeUpdate();
            if (rowsUpdate > 0) {
                isMember = true;
            } else {
                isMember = false;
            }
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error de conexión.");
            alert.setHeaderText("¡ERROR!");
            alert.setContentText("Error al conectar a la base de datos: " + e.getMessage());
            alert.showAndWait();
        }
        return isMember;
    }

    public static List<CubeUser> listUser() {
        List<CubeUser> users = new ArrayList<>();
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
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error de conexión.");
            alert.setHeaderText("¡ERROR!");
            alert.setContentText("Error al conectar a la base de datos: " + e.getMessage());
            alert.showAndWait();
        }
        return users;
    }

    public static void dataProfile (String mail){
        try {
            Connection connection = DatabaseConnection.conectar();
            String sql = "SELECT NAME_USER, LEVEL_USER, ROLE_USER, REGISTRATION_DATE FROM CUBE_USERS" +
                    " WHERE MAIL = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, mail);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String nameUser = resultSet.getString("NAME_USER");
                int level = resultSet.getInt("LEVEL_USER");
                String roleUser = resultSet.getString("ROLE_USER");
                Date date = resultSet.getDate("REGISTRATION_DATE");
                CacheStatic.cubeUser.setNameUser(nameUser);
                CacheStatic.cubeUser.setLevelUser(level);
                CacheStatic.cubeUser.setRoleUser(CubeUser.Role.valueOf(roleUser));
                CacheStatic.cubeUser.setRegistrationDate(date.toLocalDate());
            }
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error de conexión.");
            alert.setHeaderText("¡ERROR!");
            alert.setContentText("Error al conectar a la base de datos: " + e.getMessage());
            alert.showAndWait();
        }
    }


    public static boolean levelUpdate (){
        String sql = "UPDATE CUBE_USERS SET LEVEL_USER = " +
                "(SELECT TRUNCATE(COUNT(DESCRIPTION_SCRAMBLE) * 25 / 100, 0)  FROM TIMES_TRAINING)";
        boolean isUpdate = false;
        try {
            Connection connection = DatabaseConnection.conectar();
            PreparedStatement statement = connection.prepareStatement(sql);
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                isUpdate = true;
            }
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error de conexión.");
            alert.setHeaderText("¡ERROR!");
            alert.setContentText("Error al conectar a la base de datos: " + e.getMessage());
            alert.showAndWait();
        }
        return isUpdate;
    }

    public static int levelNumber (String mailUser) {
        levelUpdate();
        String sql = "SELECT LEVEL_USER FROM CUBE_USERS WHERE MAIL = ?";
        int levelNumber = 0;
        try {
            Connection connection = DatabaseConnection.conectar();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, mailUser);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                levelNumber = resultSet.getInt("LEVEL_USER");
            }
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error de conexión.");
            alert.setHeaderText("¡ERROR!");
            alert.setContentText("Error al conectar a la base de datos: " + e.getMessage());
            alert.showAndWait();
        }
        return levelNumber;

    }

    public static int selectIdUser (String mail){
        String sql = "SELECT ID_USER FROM CUBE_USERS WHERE MAIL = ?";
        int idUser = 0;
        try {
            Connection connection = DatabaseConnection.conectar();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, mail);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                idUser = resultSet.getInt("ID_USER");
            }
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error de conexión.");
            alert.setHeaderText("¡ERROR!");
            alert.setContentText("Error al conectar a la base de datos: " + e.getMessage());
            alert.showAndWait();
        }
        return idUser;
    }

    public static boolean urlUpdate (File selectedFile, String mail){
        String sql = "UPDATE CUBE_USERS SET URL_IMAGEN = ? WHERE MAIL = ?";
        boolean isUpdate = false;
        try {
            Connection connection = DatabaseConnection.conectar();
            PreparedStatement statement = connection.prepareStatement(sql);
            String fileUrl = selectedFile.toURI().toString(); // EXTRAER LA URL
            statement.setString(1, fileUrl);
            statement.setString(2, mail);
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                isUpdate = true;
            }
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error de conexión.");
            alert.setHeaderText("¡ERROR!");
            alert.setContentText("Error al conectar a la base de datos: " + e.getMessage());
            alert.showAndWait();
        }
        return isUpdate;
    } // ACTUALIZAR LA URL


    public static Image imgUrlSelect (String mail){
        String sql = "SELECT URL_IMAGEN FROM CUBE_USERS WHERE MAIL = ?";
        Image image = null; //SE CREA UNA IMAGEN CON LA URL
        try {
            Connection connection = DatabaseConnection.conectar();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, mail);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                String fileUrl = resultSet.getString("URL_IMAGEN");
                URL url = new URL (fileUrl); // SE CREA UN URL PORQUE CON EL STRING DE LA URL NO SE PUEDE
                image = new Image(url.toString());
            }
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error de conexión.");
            alert.setHeaderText("¡ERROR!");
            alert.setContentText("Error al conectar a la base de datos: " + e.getMessage());
            alert.showAndWait();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        return image;
    } // SELECCIONAR LA URL Y CONVERTIRLA EN IMAGEN

    public static boolean selectUrl (String mail){
        String sql = "SELECT URL_IMAGEN FROM CUBE_USERS WHERE MAIL = ?";
        boolean haveUrl = false;
        try {
            Connection connection = DatabaseConnection.conectar();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, mail);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                String fileUrl = resultSet.getString("URL_IMAGEN");
                if(fileUrl != null){
                    haveUrl = true;
                }
            }
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error de conexión.");
            alert.setHeaderText("¡ERROR!");
            alert.setContentText("Error al conectar a la base de datos: " + e.getMessage());
            alert.showAndWait();
        }
        return haveUrl;
    } // SELECCIONAR LA URL




}

package com.example.cubex.DAO;

import com.example.cubex.Database.DatabaseConnection;
import javafx.scene.control.Alert;

import java.sql.*;
import java.time.LocalDate;

public class SessionDAO {
    public static int selectNumberSession (String nameSession) {
        int idSession = -1;
        String sql = "SELECT ID_SESSION FROM SESSIONS WHERE NAME_SESSION = ?";
        try {
            Connection connection = DatabaseConnection.conectar();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, nameSession);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                idSession = resultSet.getInt("ID_SESSION");
            }
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error de conexión.");
            alert.setHeaderText("¡ERROR!");
            alert.setContentText("Error al conectar a la base de datos: " + e.getMessage());
            alert.showAndWait();
        }
        return idSession;
    }
    public static int insertIdUserSession(String mail) {
        String sql = "SELECT ID_USER FROM CUBE_USERS WHERE MAIL = ?";
        int idUser = -1;
        try {
            Connection con = DatabaseConnection.conectar();
            PreparedStatement sentencia = con.prepareStatement(sql);
            sentencia.setString(1, mail);
            ResultSet consulta = sentencia.executeQuery();
            if (consulta.next()) {
                idUser = consulta.getInt("ID_USER");
            }
            con.close();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error de conexión.");
            alert.setHeaderText("¡ERROR!");
            alert.setContentText("Error al conectar a la base de datos: " + e.getMessage());
            alert.showAndWait();
        }
        return idUser;
    }
    public static int insertIdTypeSession(String cubeType) {
        String sql = "SELECT ID_TYPE FROM CUBE_TYPE WHERE NAME_TYPE = ?";
        int idType = -1;
        try {
            Connection con = DatabaseConnection.conectar();
            PreparedStatement sentencia = con.prepareStatement(sql);
            sentencia.setString(1, cubeType);
            ResultSet consulta = sentencia.executeQuery();
            if (consulta.next()) {
                idType = consulta.getInt("ID_TYPE");
            }
            con.close();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error de conexión.");
            alert.setHeaderText("¡ERROR!");
            alert.setContentText("Error al conectar a la base de datos: " + e.getMessage());
            alert.showAndWait();
        }
        return idType;
    }
    public static boolean insertSession(int idUser, String nameSession, LocalDate date, int idType) {
        String sql = "INSERT INTO SESSIONS (ID_USER, NAME_SESSION, CREATION_DATE, ID_TYPE) VALUES (?, ?, ?, ?)";
        boolean isInserted = false;
        try {
            Connection con = DatabaseConnection.conectar();
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, idUser);
            statement.setString(2, nameSession);
            statement.setString(3, String.valueOf(date));
            statement.setInt(4, idType);
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Sesion creada.");
                alert.setHeaderText("Creación exitosa");
                alert.setContentText("Se ha creado la sesion correctamente");
                alert.showAndWait();
                isInserted = true;
            }
            con.close();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error de conexión.");
            alert.setHeaderText("¡ERROR!");
            alert.setContentText("Error al conectar a la base de datos: " + e.getMessage());
            alert.showAndWait();
        }
        return isInserted;
    }

    public static boolean deleteSession (String nameSession) {
        String sql = "DELETE FROM SESSIONS WHERE NAME_SESSION = ?";
        boolean isDelete = false;
        try {
            Connection con = DatabaseConnection.conectar();
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, nameSession);
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                isDelete = true;
            }
            con.close();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error de conexión.");
            alert.setHeaderText("¡ERROR!");
            alert.setContentText("Error al conectar a la base de datos: " + e.getMessage());
            alert.showAndWait();
        }
        return isDelete;
    }

    public static int minIdSession (String mail) {
        String sql = "SELECT MIN(ID_SESSION) FROM SESSIONS WHERE ID_USER = (SELECT ID_USER FROM CUBE_USERS WHERE MAIL = ?) ";
        int minIdSession = -1;
        try {
            Connection con = DatabaseConnection.conectar();
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, mail);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                minIdSession = resultSet.getInt("MIN(ID_SESSION)");
            }
            con.close();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error de conexión.");
            alert.setHeaderText("¡ERROR!");
            alert.setContentText("Error al conectar a la base de datos: " + e.getMessage());
            alert.showAndWait();
        }
        return minIdSession;
    }

    public static int maxIdSession (String mail) {
        String sql = "SELECT MAX(ID_SESSION) FROM SESSIONS WHERE ID_USER = (SELECT ID_USER FROM CUBE_USERS WHERE MAIL = ?) ";
        int maxIdSession = -1;
        try {
            Connection con = DatabaseConnection.conectar();
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, mail);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                maxIdSession = resultSet.getInt("MAX(ID_SESSION)");
            }
            con.close();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error de conexión.");
            alert.setHeaderText("¡ERROR!");
            alert.setContentText("Error al conectar a la base de datos: " + e.getMessage());
            alert.showAndWait();
        }
        return maxIdSession;
    }


    public static String nameCategory (int idType){
        String sql = "SELECT NAME_TYPE FROM CUBE_TYPE WHERE ID_TYPE = ?";
        String nameCategory = null;
        try {
            Connection con = DatabaseConnection.conectar();
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, idType);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                nameCategory = resultSet.getString("NAME_TYPE");
            }
            con.close();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error de conexión.");
            alert.setHeaderText("¡ERROR!");
            alert.setContentText("Error al conectar a la base de datos: " + e.getMessage());
            alert.showAndWait();
        }
        return nameCategory;
    }

    public static int idCategory (String nameCategory){
        String sql = "SELECT ID_TYPE FROM CUBE_TYPE WHERE NAME_TYPE = ?";
        int idCategory = -1;
        try {
            Connection con = DatabaseConnection.conectar();
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, nameCategory);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                idCategory = resultSet.getInt("ID_TYPE");
            }
            con.close();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error de conexión.");
            alert.setHeaderText("¡ERROR!");
            alert.setContentText("Error al conectar a la base de datos: " + e.getMessage());
            alert.showAndWait();
        }
        return idCategory;
    }

    public static int numberSession (String mail) {
        String sql = "SELECT COUNT(ID_SESSION) FROM SESSIONS WHERE ID_USER = (SELECT ID_USER FROM CUBE_USERS WHERE MAIL = ?)";
        int number = -1;
        try {
            Connection con = DatabaseConnection.conectar();
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, mail);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                number = resultSet.getInt("COUNT(ID_SESSION)");
            }
            con.close();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error de conexión.");
            alert.setHeaderText("¡ERROR!");
            alert.setContentText("Error al conectar a la base de datos: " + e.getMessage());
            alert.showAndWait();
        }
        return number;
    }

    public static boolean changeNameSesssion (String newName, int idSession) {
        String sql = "UPDATE SESSIONS SET NAME_SESSION = ? WHERE ID_SESSION = ?";
        boolean isUpdate = false;
        try {
            Connection con = DatabaseConnection.conectar();
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, newName);
            statement.setInt(2, idSession);
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                isUpdate = true;
            }
            con.close();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error de conexión.");
            alert.setHeaderText("¡ERROR!");
            alert.setContentText("Error al conectar a la base de datos: " + e.getMessage());
            alert.showAndWait();
        }
        return isUpdate;
    }

}

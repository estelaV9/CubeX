package com.example.cubex.DAO;

import com.example.cubex.Database.DatabaseConnection;
import com.example.cubex.model.CacheStatic;
import com.example.cubex.model.CubeUser;
import com.example.cubex.model.UserChampCompete;
import javafx.scene.control.Alert;

import java.net.IDN;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserChampCompeteDAO {
    public static boolean insertUserInChamp (int idUser, int idChamp) {
        String sql = "INSERT INTO USER_CHAMP_COMPETE VALUES (?, ?)";
        boolean isInserted = false;
        try {
            Connection con = DatabaseConnection.conectar();
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, idUser);
            statement.setInt(2, idChamp);
            int rowsAffected = statement.executeUpdate();
            if(rowsAffected > 0){
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
        return  isInserted;
    }

    public static List<UserChampCompete> selectIdUsersChamp (int idChamp) {
        String sql = "SELECT ID_USER FROM USER_CHAMP_COMPETE WHERE ID_CHAMP = ?;";
        List<UserChampCompete> idUserChamp = new ArrayList<>();
        try {
            Connection con = DatabaseConnection.conectar();
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, idChamp);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                int idUser = resultSet.getInt("ID_USER");
                UserChampCompete userChampCompete = new UserChampCompete(idUser);
                idUserChamp.add(userChampCompete);
            }
            con.close();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error de conexión.");
            alert.setHeaderText("¡ERROR!");
            alert.setContentText("Error al conectar a la base de datos: " + e.getMessage());
            alert.showAndWait();
        }
        return idUserChamp;
    }

    public static int countUserChamp (int idChamp) {
        String sql = "SELECT COUNT(ID_USER) FROM USER_CHAMP_COMPETE WHERE ID_CHAMP = ?";
        int idUser = 0;
        try {
            Connection connection = DatabaseConnection.conectar();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, idChamp);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                idUser = resultSet.getInt("COUNT(ID_USER)");
            }
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error de conexión.");
            alert.setHeaderText("¡ERROR!");
            alert.setContentText("Error al conectar a la base de datos: " + e.getMessage());
            alert.showAndWait();
        }
        return idUser;
    } // SABER CUANTOS USUARIOS HAY EN UNA COMPETICION

    public static boolean selectUserChamp (int idChamp, int idUser) {
        String sql = "SELECT ID_USER FROM USER_CHAMP_COMPETE WHERE ID_CHAMP = ? AND ID_USER = ?";
        boolean isExists = false;
        try {
            Connection connection = DatabaseConnection.conectar();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, idChamp);
            statement.setInt(2, idUser);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                isExists = true;
            }
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error de conexión.");
            alert.setHeaderText("¡ERROR!");
            alert.setContentText("Error al conectar a la base de datos: " + e.getMessage());
            alert.showAndWait();
        }
        return isExists;
    } // SABER SI UN USUARIO EXISTE EN ESA COMPETICION

    public static boolean deleteUserChamp (int idUser, int idChamp) {
        String sql = "DELETE FROM USER_CHAMP_COMPETE WHERE ID_USER = ? AND ID_CHAMP = ?";
        boolean successfulDelete = false;
        try {
            Connection connection = DatabaseConnection.conectar();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, idUser);
            statement.setInt(2, idChamp);
            int rowsDelete = statement.executeUpdate();
            if (rowsDelete > 0) {
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
    } // ELIMINAR UN USUARIO DE UNA COMPETICION

    public static boolean deleteTimesChamp (int idChamp) {
        String sql = "DELETE FROM USER_CHAMP_COMPETE WHERE ID_CHAMP = ?";
        boolean successfulDelete = false;
        try {
            Connection connection = DatabaseConnection.conectar();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, idChamp);
            int rowsDelete = statement.executeUpdate();
            if (rowsDelete > 0) {
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
    } // ELIMINAR UN USUARIO DE UNA COMPETICION

}

package com.example.cubex.DAO;

import com.example.cubex.Database.DatabaseConnection;
import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CubeChampPerteneceDAO {
    public static boolean createWinnerPertenece (int idType, int idChamp, String winner) {
        boolean isCreate = false;
        String sql = "INSERT INTO CUBE_CHAMP_PERTENECE VALUES (?, ?, ?)";
        try {
            Connection connection = DatabaseConnection.conectar();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, idType);
            statement.setInt(2, idChamp);
            statement.setString(3, winner);
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                isCreate = true;
            }
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error de conexión.");
            alert.setHeaderText("¡ERROR!");
            alert.setContentText("Error al conectar a la base de datos: " + e.getMessage());
            alert.showAndWait();
        }
        return isCreate;
    }
    public static boolean isPertenece (int idType, int idChamp, String winner) {
        boolean isExists = false;
        String sql = "SELECT ID_TYPE, ID_CHAMP, WINNER FROM CUBE_CHAMP_PERTENECE WHERE ID_TYPE = ? AND ID_CHAMP = ? AND WINNER = ?";
        try {
            Connection connection = DatabaseConnection.conectar();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, idType);
            statement.setInt(2, idChamp);
            statement.setString(3, winner);
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
    }
}

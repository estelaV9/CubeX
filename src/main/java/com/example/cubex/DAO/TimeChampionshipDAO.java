package com.example.cubex.DAO;

import com.example.cubex.Database.DatabaseConnection;
import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class TimeChampionshipDAO {
    public static boolean createTimeChampionship (int idUser, String scramble, String min, String second, int idChamp, int idType){
        boolean isCreate = false;
        String sql = "INSERT INTO TIMES_CHAMPIONSHIP (ID_USER, DESCRIPTION_SCRAMBLE, MINUTES1, SECONDS1, ID_CHAMP, ID_TYPE)" +
                " VALUES (?, ?, ?, ?, ?, ?)";
        try {
            Connection connection = DatabaseConnection.conectar();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, idUser);
            statement.setString(2, scramble);
            statement.setString(3, min);
            statement.setString(4, second);
            statement.setInt(5, idChamp);
            statement.setInt(6, idType);
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

}

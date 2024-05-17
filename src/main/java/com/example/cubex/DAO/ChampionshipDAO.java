package com.example.cubex.DAO;

import com.example.cubex.Database.DatabaseConnection;
import javafx.scene.control.Alert;

import java.sql.*;
import java.time.LocalDate;

public class ChampionshipDAO {
    public static boolean insertChampionship (int idUser, String nameChamp, int price, int numberPart, String descriptionChamp,
                                       LocalDate registrationDate, boolean membersOnly) {
        String sql = "INSERT INTO CHAMPIONSHIP (ID_USER, NAME_CHAMP, PRICE, NUMBER_PART, DESCRIPTION_CHAMP, " +
                "REGISTRATION_DATE, MEMBERS_ONLY) VALUES (?, ?, ?, ?, ?, ?, ?);";
        boolean isInsert = false;
        try {
            Connection connection = DatabaseConnection.conectar();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, idUser);
            statement.setString(2, nameChamp);
            statement.setInt(3, price);
            statement.setInt(4, numberPart);
            statement.setString(5, descriptionChamp);
            statement.setDate(6, Date.valueOf(registrationDate));
            statement.setBoolean(7, membersOnly);
            int rowsAffected = statement.executeUpdate();
            if(rowsAffected > 0){
                isInsert = true;
            }
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error de conexión.");
            alert.setHeaderText("¡ERROR!");
            alert.setContentText("Error al conectar a la base de datos: " + e.getMessage());
            alert.showAndWait();
        }
        return isInsert;
    } // CREAR UN CAMPEONATO
}

package com.example.cubex.DAO;

import com.example.cubex.Database.DatabaseConnection;
import com.example.cubex.model.TimesChampionship;
import javafx.scene.control.Alert;

import java.sql.*;
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

    public static boolean deleteUserChamp (int idChamp, int idUser){
        String sql = "DELETE FROM TIMES_CHAMPIONSHIP WHERE ID_CHAMP = ? AND ID_USER = ?";
        boolean isDelete = false;
        try {
            Connection con = DatabaseConnection.conectar();
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, idChamp);
            statement.setInt(2, idUser);
            int rowsAffected = statement.executeUpdate();
            if(rowsAffected > 0){
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

    public static boolean deleteTimesChamp (int idChamp){
        String sql = "DELETE FROM TIMES_CHAMPIONSHIP WHERE ID_CHAMP = ? ";
        boolean isDelete = false;
        try {
            Connection con = DatabaseConnection.conectar();
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, idChamp);
            int rowsAffected = statement.executeUpdate();
            if(rowsAffected > 0){
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

    public static TimesChampionship dataWinnerCategory (int idChamp, int idType){
        String sql = "SELECT ID_USER, TRUNCATE((SUM(MINUTES1 * 60 + SECONDS1) - MIN(MINUTES1 * 60 + SECONDS1) - MAX(MINUTES1 * 60 + SECONDS1)) / (COUNT(MINUTES1) - 2)/60, 0) as minutos, \n" +
                "\tMOD(TRUNCATE((SUM(MINUTES1 * 60 + SECONDS1) - MIN(MINUTES1 * 60 + SECONDS1) - MAX(MINUTES1 * 60 + SECONDS1)) / (COUNT(MINUTES1) - 2), 3), 60) as seconds\n" +
                "FROM TIMES_CHAMPIONSHIP \n" +
                "WHERE ID_CHAMP = ? AND ID_TYPE = ?\n" +
                "GROUP BY ID_USER\n" +
                "ORDER BY minutos, seconds\n" +
                "LIMIT 1;";
        TimesChampionship timesChampionship = null;
        try {
            Connection con = DatabaseConnection.conectar();
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, idChamp);
            statement.setInt(2, idType);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                int idUser = resultSet.getInt("ID_USER");
                int minutes = resultSet.getInt("minutos");
                double seconds = resultSet.getDouble("seconds");
                timesChampionship = new TimesChampionship(idUser, minutes, seconds);
            }
            con.close();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error de conexión.");
            alert.setHeaderText("¡ERROR!");
            alert.setContentText("Error al conectar a la base de datos: " + e.getMessage());
            alert.showAndWait();
        }
        return timesChampionship;
    }








}

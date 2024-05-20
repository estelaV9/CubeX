package com.example.cubex.DAO;

import com.example.cubex.Database.DatabaseConnection;
import com.example.cubex.model.TimeTraining;
import com.example.cubex.model.TimesChampionship;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.SelectionMode;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ChampionshipDAO {
    public static boolean insertChampionship (int idUser, String nameChamp, int price, int numberPart, String descriptionChamp,
                                       LocalDate registrationDate, boolean membersOnly, String category) {
        String sql = "INSERT INTO CHAMPIONSHIP (ID_USER, NAME_CHAMP, PRICE, NUMBER_PART, DESCRIPTION_CHAMP, " +
                "REGISTRATION_DATE, MEMBERS_ONLY, DESCRIPTION_CATEGORY) VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
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
            statement.setString(8, category);
            int rowsAffected = statement.executeUpdate();
            if(rowsAffected > 0){
                isInsert = true;
            }
            connection.close();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error de conexión.");
            alert.setHeaderText("¡ERROR!");
            alert.setContentText("Error al conectar a la base de datos: " + e.getMessage());
            alert.showAndWait();
        }
        return isInsert;
    } // CREAR UN CAMPEONATO

    public static String administratorCham (String nameChamp){
        String sql = "SELECT NAME_USER FROM CUBE_USERS WHERE ID_USER = (SELECT ID_USER FROM CHAMPIONSHIP WHERE NAME_CHAMP = ?);";
        String adminCreator = "";
        try {
            Connection con = DatabaseConnection.conectar();
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, nameChamp);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                adminCreator = resultSet.getString("NAME_USER");
            }
            con.close();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error de conexión.");
            alert.setHeaderText("¡ERROR!");
            alert.setContentText("Error al conectar a la base de datos: " + e.getMessage());
            alert.showAndWait();
        }
        return adminCreator;
    }

    public static int selectIdChamp (String nameChamp){
        String sql = "SELECT ID_CHAMP FROM CHAMPIONSHIP WHERE NAME_CHAMP = ?;";
        int idChamp = -1;
        try {
            Connection con = DatabaseConnection.conectar();
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, nameChamp);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                idChamp = resultSet.getInt("ID_CHAMP");
            }
            con.close();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error de conexión.");
            alert.setHeaderText("¡ERROR!");
            alert.setContentText("Error al conectar a la base de datos: " + e.getMessage());
            alert.showAndWait();
        }
        return idChamp;
    }

    public static boolean existsNameChamp (String nameChamp){
        String sql = "SELECT NAME_CHAMP FROM CHAMPIONSHIP WHERE NAME_CHAMP = ?;";
        boolean isExists = false;
        try {
            Connection con = DatabaseConnection.conectar();
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, nameChamp);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                isExists = true;
            }
            con.close();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error de conexión.");
            alert.setHeaderText("¡ERROR!");
            alert.setContentText("Error al conectar a la base de datos: " + e.getMessage());
            alert.showAndWait();
        }
        return isExists;
    } // NOMBRE DEL CAMPEONATO


    public static boolean updateName (String newNameChamp, int idChamp) {
        String sql = "UPDATE CHAMPIONSHIP SET NAME_CHAMP = ? WHERE ID_CHAMP = ?";
        boolean isUpdate = false;
        try {
            Connection con = DatabaseConnection.conectar();
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, newNameChamp);
            statement.setInt(2, idChamp);
            int rowsAffected = statement.executeUpdate();
            if(rowsAffected > 0){
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
    }// ACTUALIZAR NOMBRE DEL CAMPEONATO


    public static String selectCategory (int idChamp) {
        String sql = "SELECT DESCRIPTION_CATEGORY FROM CHAMPIONSHIP WHERE ID_CHAMP = ?;";
        String category = "";
        try {
            Connection con = DatabaseConnection.conectar();
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, idChamp);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                category = resultSet.getString("DESCRIPTION_CATEGORY");
            }
            con.close();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error de conexión.");
            alert.setHeaderText("¡ERROR!");
            alert.setContentText("Error al conectar a la base de datos: " + e.getMessage());
            alert.showAndWait();
        }
        return category;

    } // SELECCIONAR LA CATEGORIA QUE SE GUARDA EN LA DESCRIPCION


    public static int countTimesChamp (int idChamp, int idUser, int idType ) {
        String sql = "SELECT COUNT(ID_TIMES_VERSUS) FROM TIMES_CHAMPIONSHIP WHERE ID_CHAMP = ? AND ID_USER = ? AND ID_TYPE = ?;";
        int totalTimes = 0;
        try {
            Connection con = DatabaseConnection.conectar();
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, idChamp);
            statement.setInt(2, idUser);
            statement.setInt(3, idType);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                totalTimes = resultSet.getInt("COUNT(ID_TIMES_VERSUS)");
            }
            con.close();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error de conexión.");
            alert.setHeaderText("¡ERROR!");
            alert.setContentText("Error al conectar a la base de datos: " + e.getMessage());
            alert.showAndWait();
        }
        return totalTimes;

    }

    public static boolean deleteChamp (int idChamp){
        String sql = "DELETE FROM CHAMPIONSHIP WHERE ID_CHAMP = ?";
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


    public static void nameChamp(ComboBox nameChampCB){
        nameChampCB.setPromptText("NAME CHAMPIOSHIP");
        try {
            Connection connection = DatabaseConnection.conectar();
            String sqlCat = "SELECT NAME_CHAMP FROM CHAMPIONSHIP";
            PreparedStatement statement = connection.prepareStatement(sqlCat);
            ResultSet resultSet1 = statement.executeQuery();
            while (resultSet1.next()) {
                nameChampCB.getItems().addAll(resultSet1.getString("NAME_CHAMP"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<TimesChampionship> listTimesChamp (String nameChamp, String category){
        List<TimesChampionship> timesChamp = new ArrayList<>();
        try {
            Connection connection = DatabaseConnection.conectar();
            String sql = "SELECT  MINUTES1, SECONDS1, ID_USER \n" +
                    "    FROM TIMES_CHAMPIONSHIP WHERE ID_CHAMP = (SELECT ID_CHAMP FROM CHAMPIONSHIP WHERE NAME_CHAMP = ?)" +
                    " AND ID_TYPE = (SELECT ID_TYPE FROM CUBE_TYPE WHERE NAME_TYPE = ?);";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, nameChamp);
            statement.setString(2, category);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int minutes = resultSet.getInt("MINUTES1");
                double seconds = resultSet.getDouble("SECONDS1");
                int idUser = resultSet.getInt("ID_USER");
                TimesChampionship timesChampionship = new TimesChampionship(idUser, minutes, seconds);
                timesChamp.add(timesChampionship);
            }
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error de conexión.");
            alert.setHeaderText("¡ERROR!");
            alert.setContentText("Error al conectar a la base de datos: " + e.getMessage());
            alert.showAndWait();
        }
        return timesChamp;
    }

    public static void categoryChamp(ComboBox nameChampCB, String nameChamp){
        nameChampCB.setPromptText("CATEGORY CHAMPIONSHIP");
        try {
            Connection connection = DatabaseConnection.conectar();
            String sqlCat = "SELECT NAME_TYPE FROM CUBE_TYPE WHERE ID_TYPE IN (SELECT ID_TYPE FROM TIMES_CHAMPIONSHIP " +
                    "WHERE ID_CHAMP = (SELECT ID_CHAMP FROM CHAMPIONSHIP WHERE NAME_CHAMP = ?));";
            PreparedStatement statement = connection.prepareStatement(sqlCat);
            statement.setString(1, nameChamp);
            ResultSet resultSet1 = statement.executeQuery();
            while (resultSet1.next()) {
                nameChampCB.getItems().addAll(resultSet1.getString("NAME_TYPE"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }




}

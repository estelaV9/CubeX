package com.example.cubex.DAO;

import com.example.cubex.Database.DatabaseConnection;
import javafx.scene.control.Alert;
import javafx.scene.control.SelectionMode;

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


    public String selectCategory (int idChamp) {
        String sql = "SELECT\n" +
                "    CASE\n" +
                "        WHEN description_CHAMP LIKE '%2x2x2%' THEN '2x2x2'\n" +
                "        WHEN description_CHAMP LIKE '%3x3x3%' THEN '3x3x3'\n" +
                "        WHEN description_CHAMP LIKE '%3x3x3 BLIND%' THEN '3x3x3 BLIND'\n" +
                "        WHEN description_CHAMP LIKE '%3x3x3 FEWEST MOVES CHALLENGE%' THEN '3x3x3 FEWEST MOVES CHALLENGE'\n" +
                "        WHEN description_CHAMP LIKE '%3x3x3 MIRROR%' THEN '3x3x3 MIRROR'\n" +
                "        WHEN description_CHAMP LIKE '%3x3x3 MULTIBLIND%' THEN '3x3x3 MULTIBLIND'\n" +
                "        WHEN description_CHAMP LIKE '%3x3x3 ONE-HANDED%' THEN '3x3x3 ONE-HANDED'\n" +
                "        WHEN description_CHAMP LIKE '%4x4x4%' THEN '4x4x4'\n" +
                "        WHEN description_CHAMP LIKE '%4x4x4 BLIND%' THEN '4x4x4 BLIND'\n" +
                "        WHEN description_CHAMP LIKE '%5x5x5%' THEN '5x5x5'\n" +
                "        WHEN description_CHAMP LIKE '%5x5x5 BLIND%' THEN '5x5x5 BLIND'\n" +
                "        WHEN description_CHAMP LIKE '%6x6x6%' THEN '6x6x6'\n" +
                "        WHEN description_CHAMP LIKE '%7x7x7%' THEN '7x7x7'\n" +
                "        WHEN description_CHAMP LIKE '%CLOCK%' THEN 'CLOCK'\n" +
                "        WHEN description_CHAMP LIKE '%MASTERMORPHIX%' THEN 'MASTERMORPHIX'\n" +
                "        WHEN description_CHAMP LIKE '%MEGAMINX%' THEN 'MEGAMINX'\n" +
                "        WHEN description_CHAMP LIKE '%PYRAMINX%' THEN 'PYRAMINX'\n" +
                "        WHEN description_CHAMP LIKE '%PYRAMORPHIX%' THEN 'PYRAMORPHIX'\n" +
                "        WHEN description_CHAMP LIKE '%SKEWB%' THEN 'SKEWB'\n" +
                "        WHEN description_CHAMP LIKE '%SQUARE-1%' THEN 'SQUARE-1'\n" +
                "        ELSE NULL\n" +
                "    END AS cube_type\n" +
                "FROM CHAMPIONSHIP\n" +
                "WHERE ID_CHAMP = ?;";
        String category = "";
        try {
            Connection con = DatabaseConnection.conectar();
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, idChamp);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                category = resultSet.getString("D");
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



}

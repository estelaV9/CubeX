package com.example.cubex.DAO;

import com.example.cubex.Database.DatabaseConnection;
import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class CompetitionDAO {
    public static boolean insertCompe(int idCompe, int idUser, String cuber1, String cuber2, LocalDate date) {
        String sql = "INSERT INTO COMPETITION (ID_COMPE, ID_USER, CUBER1, CUBER2, REGISTRATION_DATE) VALUES (?, ?, ?, ?, ?)";
        boolean isInserted = false;
        try {
            Connection con = DatabaseConnection.conectar();
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, idCompe);
            statement.setInt(2, idUser);
            statement.setString(3, cuber1);
            statement.setString(4, cuber2);
            statement.setString(5, String.valueOf(date));
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
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
    public static boolean insertWinner (int idCompe) {
        String sql = "UPDATE COMPETITION C\n" +
                "SET WINNER = (\n" +
                "    SELECT \n" +
                "        CASE\n" +
                "            WHEN MENOR = T.MINUTES1 * 60 + T.SECONDS1 THEN C.CUBER1\n" +
                "            WHEN MENOR = T.MINUTES2 * 60 + T.SECONDS2 THEN C.CUBER2\n" +
                "        END      \n" +
                "    FROM \n" +
                "        (SELECT ID_COMPE, LEAST((MINUTES1 *60 + SECONDS1), (MINUTES2 * 60 + SECONDS2)) AS MENOR\n" +
                "         FROM TIMES_COMPETITION \n" +
                "         WHERE ID_COMPE = ?) MENOR_TIEMPO\n" +
                "    JOIN TIMES_COMPETITION T ON MENOR_TIEMPO.ID_COMPE = T.ID_COMPE\n" +
                ")\n" +
                "WHERE C.ID_COMPE = ?;";
        boolean isInserted = false;
        try {
            Connection con = DatabaseConnection.conectar();
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, idCompe);
            statement.setInt(2, idCompe);
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
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


    public static int countCompetition () {
        String sql = "SELECT COUNT(ID_COMPE) FROM COMPETITION";
        int numberCompe = 0;
        try {
            Connection connection = DatabaseConnection.conectar();
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                numberCompe = resultSet.getInt("COUNT(ID_COMPE)");
            }
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error de conexión.");
            alert.setHeaderText("¡ERROR!");
            alert.setContentText("Error al conectar a la base de datos: " + e.getMessage());
            alert.showAndWait();
        }
        return numberCompe;

    } // VER CUANTAS COMPETICIONES HAY PARA CONTADOR


}

package com.example.cubex.DAO;

import com.example.cubex.Database.DatabaseConnection;
import com.example.cubex.model.Competition;
import com.example.cubex.model.CubeUser;
import com.example.cubex.model.TimeCompetition;
import com.example.cubex.model.TimeTraining;
import javafx.scene.control.Alert;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TimeCompetitionDAO {
    public static boolean createTimeCompe (String scramble, String min1, String second1, String min2, String second2, int idCompe, int idType){
        boolean isCreate = false;
        String sql = "INSERT INTO TIMES_COMPETITION (DESCRIPTION_SCRAMBLE, MINUTES1, SECONDS1, MINUTES2, SECONDS2, ID_COMPE, ID_TYPE)" +
                " VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            Connection connection = DatabaseConnection.conectar();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, scramble);
            statement.setString(2, min1);
            statement.setString(3, second1);
            statement.setString(4, min2);
            statement.setString(5, second2);
            statement.setInt(6, idCompe);
            statement.setInt(7, idType);
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


    public static List<TimeCompetition> listTimesCompe(int idUser) {
        List<TimeCompetition> timesCompe = new ArrayList<>();
        try {
            Connection connection = DatabaseConnection.conectar();
            String sql = "SELECT MINUTES1, SECONDS1, MINUTES2, SECONDS2 FROM TIMES_COMPETITION" +
                    " WHERE ID_COMPE IN (SELECT ID_COMPE FROM COMPETITION WHERE ID_USER = ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, idUser);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int minutes1 = resultSet.getInt("MINUTES1");
                double seconds1 = resultSet.getDouble("SECONDS1");
                int minutes2 = resultSet.getInt("MINUTES2");
                double seconds2 = resultSet.getDouble("SECONDS2");
                TimeCompetition timeCompetition= new TimeCompetition(minutes1, seconds1, minutes2, seconds2);
                timesCompe.add(timeCompetition);

            }
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error de conexión.");
            alert.setHeaderText("¡ERROR!");
            alert.setContentText("Error al conectar a la base de datos: " + e.getMessage());
            alert.showAndWait();
        }
        return timesCompe;
    } // LISTAR TIEMPOS DE COMPE
}

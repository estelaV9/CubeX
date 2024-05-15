package com.example.cubex.DAO;

import com.example.cubex.Database.DatabaseConnection;
import com.example.cubex.model.CubeUser;
import com.example.cubex.model.TimeTraining;
import javafx.scene.control.Alert;

import java.security.PublicKey;
import java.security.spec.DSAPublicKeySpec;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TimeTrainingDAO {
    public static List<TimeTraining> timesTraining = new ArrayList<>();
    public static boolean createTimeTraining (String scramble, String min, String second, LocalDate registrationDate, int idSession){
        boolean isCreate = false;
            String sql = "INSERT INTO TIMES_TRAINING (DESCRIPTION_SCRAMBLE, MINUTES1, SECONDS1, REGISTRATION_DATE, ID_SESSION)" +
                    " VALUES (?, ?, ?, ?, ?)";
        try {
            Connection connection = DatabaseConnection.conectar();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, scramble);
            statement.setString(2, min);
            statement.setString(3, second);
            statement.setDate(4, Date.valueOf(registrationDate));
            statement.setInt(5, idSession);
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

    public static int selectSession(String nameSession){
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


    public static List<TimeTraining> listTimesTraining (int idSession){
        try {
            Connection connection = DatabaseConnection.conectar();
            String sql = "SELECT MINUTES1, SECONDS1 FROM TIMES_TRAINING WHERE ID_SESSION = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, idSession);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int minutes = resultSet.getInt("MINUTES1");
                double seconds = resultSet.getDouble("SECONDS1");
                TimeTraining timeTraining = new TimeTraining(minutes, seconds);
                timesTraining.add(timeTraining);
            }
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error de conexión.");
            alert.setHeaderText("¡ERROR!");
            alert.setContentText("Error al conectar a la base de datos: " + e.getMessage());
            alert.showAndWait();
        }
        return timesTraining;
    }


    public static boolean plusTwoTime (int minutes1, double seconds1) {
        // SE QUE PUEDEN HABER DOS TIEMPOS IGUALES PERO NO ME DA TIEMPO
        String sql = "UPDATE TIMES_TRAINING SET SECONDS1 = SECONDS1 + 2 WHERE MINUTES1 = ? AND SECONDS1 = ?";
        boolean isUpdatePlus = false;
        try {
            Connection connection = DatabaseConnection.conectar();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, minutes1);
            statement.setDouble(2, seconds1);
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                isUpdatePlus = true;
            }
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error de conexión.");
            alert.setHeaderText("¡ERROR!");
            alert.setContentText("Error al conectar a la base de datos: " + e.getMessage());
            alert.showAndWait();
        }
        return isUpdatePlus;
    }
}

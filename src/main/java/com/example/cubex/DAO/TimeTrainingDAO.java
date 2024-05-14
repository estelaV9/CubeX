package com.example.cubex.DAO;

import com.example.cubex.Database.DatabaseConnection;
import javafx.scene.control.Alert;

import java.security.PublicKey;
import java.security.spec.DSAPublicKeySpec;
import java.sql.*;
import java.time.LocalDate;

public class TimeTrainingDAO {
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
                System.out.println(idSession);
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
}

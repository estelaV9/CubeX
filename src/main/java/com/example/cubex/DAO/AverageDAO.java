package com.example.cubex.DAO;

import com.example.cubex.Controller.RegistrationCtrller;
import com.example.cubex.Database.DatabaseConnection;
import javafx.scene.control.Alert;

import java.sql.*;

public class AverageDAO {
    public static boolean createAverageSession(int idSession){
        boolean isCreate = false;
        String sql = "INSERT INTO AVERAGE (ID_SESSION) VALUES (?)";
        try {
            Connection connection = DatabaseConnection.conectar();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, idSession);
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

    public static boolean deleteAverage (String nameSession) {
        boolean isDelete = false;
        String sql = "DELETE FROM AVERAGE WHERE ID_SESSION = (SELECT ID_SESSION FROM SESSIONS WHERE NAME_SESSION = ?)";
        try {
            Connection connection = DatabaseConnection.conectar();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, nameSession);
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                isDelete = true;
            }
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error de conexión.");
            alert.setHeaderText("¡ERROR!");
            alert.setContentText("Error al conectar a la base de datos: " + e.getMessage());
            alert.showAndWait();
        }
        return isDelete;
    }

    public static int countTimes (int idSession) {
        String sql = "UPDATE AVERAGE SET PERIOD_AVG = " +
                "(SELECT COUNT(ID_AVERAGE) FROM AVERAGE WHERE ID_SESSION = ?)";
        int numberTimes = -1;
        try {
            Connection connection = DatabaseConnection.conectar();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, idSession);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                numberTimes = resultSet.getInt("COUNT(ID_AVERAGE)");
            }
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error de conexión.");
            alert.setHeaderText("¡ERROR!");
            alert.setContentText("Error al conectar a la base de datos: " + e.getMessage());
            alert.showAndWait();
        }
        return numberTimes;
    } // SABER CUANTOS TIEMPOS HAY EN CADA SESION

    public static int worstMinutes(int idSession) {
        String sql = "UPDATE AVERAGE SET WORST_MINUTES = " +
                "(SELECT TRUNCATE(MAX(MINUTES1 * 60 + SECONDS1)/60, 0) FROM TIMES_TRAINING WHERE ID_SESSION = ?)";
        int worstMinutes = -1;
        try {
            Connection connection = DatabaseConnection.conectar();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, idSession);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                worstMinutes = resultSet.getInt("TRUNCATE(MAX(MINUTES1 * 60 + SECONDS1)/60, 0)");
            }
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error de conexión.");
            alert.setHeaderText("¡ERROR!");
            alert.setContentText("Error al conectar a la base de datos: " + e.getMessage());
            alert.showAndWait();
        }
        return worstMinutes;
    } // PEOR TIEMPO DE LA SESION

    public static int worstSecond (int idSession) {
        String sql = "UPDATE AVERAGE SET PERIOD_AVG = " +
                "(SELECT MAX(MINUTES1 * 60 + SECONDS1) - MAX(MINUTES1 * 60) FROM TIMES_TRAINING WHERE ID_SESSION = ?)";
        int numberTimes = -1;
        try {
            Connection connection = DatabaseConnection.conectar();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, idSession);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                numberTimes = resultSet.getInt("MAX(MINUTES1 * 60 + SECONDS1) - MAX(MINUTES1 * 60)");
            }
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error de conexión.");
            alert.setHeaderText("¡ERROR!");
            alert.setContentText("Error al conectar a la base de datos: " + e.getMessage());
            alert.showAndWait();
        }
        return numberTimes;
    } // PEOR TIEMPO DE LA SESION


    public static int pbMinutes (int idSession) {
        String sql = "UPDATE AVERAGE SET WORST_MINUTES = " +
                "(SELECT TRUNCATE(MIN(MINUTES1 * 60 + SECONDS1)/60, 0) FROM TIMES_TRAINING WHERE ID_SESSION = ?)";
        int pbMinutes = -1;
        try {
            Connection connection = DatabaseConnection.conectar();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, idSession);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                pbMinutes = resultSet.getInt("TRUNCATE(MAX(MINUTES1 * 60 + SECONDS1)/60, 0)");
            }
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error de conexión.");
            alert.setHeaderText("¡ERROR!");
            alert.setContentText("Error al conectar a la base de datos: " + e.getMessage());
            alert.showAndWait();
        }
        return pbMinutes;
    } // PEOR TIEMPO DE LA SESION

    public static int pbSecond (int idSession) {
        String sql = "UPDATE AVERAGE SET PERIOD_AVG = " +
                "(SELECT MIN(MINUTES1 * 60 + SECONDS1) - MIN(MINUTES1 * 60) FROM TIMES_TRAINING WHERE ID_SESSION = ?)";
        int pbSecond = -1;
        try {
            Connection connection = DatabaseConnection.conectar();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, idSession);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                pbSecond = resultSet.getInt("MAX(MINUTES1 * 60 + SECONDS1) - MAX(MINUTES1 * 60)");
            }
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error de conexión.");
            alert.setHeaderText("¡ERROR!");
            alert.setContentText("Error al conectar a la base de datos: " + e.getMessage());
            alert.showAndWait();
        }
        return pbSecond;
    } // PEOR TIEMPO DE LA SESION


    //MEDIA TOTAL

}

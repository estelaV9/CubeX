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

    public static boolean countTimes (int idSession) {
        String sql = "UPDATE AVERAGE SET PERIOD_AVG = " +
                "(SELECT COUNT(ID_AVERAGE) FROM AVERAGE WHERE ID_SESSION = ?)";
        boolean isUpdate = false;
        try {
            Connection connection = DatabaseConnection.conectar();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, idSession);
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                isUpdate = true;
            }
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error de conexión.");
            alert.setHeaderText("¡ERROR!");
            alert.setContentText("Error al conectar a la base de datos: " + e.getMessage());
            alert.showAndWait();
        }
        return isUpdate;
    } // SABER CUANTOS TIEMPOS HAY EN CADA SESION

    public static boolean worstMinutes(int idSession) {
        String sql = "UPDATE AVERAGE SET WORST_MINUTES = " +
                "(SELECT TRUNCATE(MAX(MINUTES1 * 60 + SECONDS1)/60, 0) FROM TIMES_TRAINING WHERE ID_SESSION = ?)";
        boolean isUpdate = false;
        try {
            Connection connection = DatabaseConnection.conectar();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, idSession);
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                isUpdate = true;
            }
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error de conexión.");
            alert.setHeaderText("¡ERROR!");
            alert.setContentText("Error al conectar a la base de datos: " + e.getMessage());
            alert.showAndWait();
        }
        return isUpdate;
    } // PEOR MINUTO DE LA SESION

    public static boolean worstSecond (int idSession) {
        String sql = "UPDATE AVERAGE SET PERIOD_AVG = " +
                "(SELECT MAX(MINUTES1 * 60 + SECONDS1) - MAX(MINUTES1 * 60) FROM TIMES_TRAINING WHERE ID_SESSION = ?)";
        boolean isUpdate = false;
        try {
            Connection connection = DatabaseConnection.conectar();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, idSession);
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                isUpdate = true;
            }
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error de conexión.");
            alert.setHeaderText("¡ERROR!");
            alert.setContentText("Error al conectar a la base de datos: " + e.getMessage());
            alert.showAndWait();
        }
        return isUpdate;
    } // PEOR SEGUNDO DE LA SESION


    public static boolean pbMinutes (int idSession) {
        String sql = "UPDATE AVERAGE SET WORST_MINUTES = " +
                "(SELECT TRUNCATE(MIN(MINUTES1 * 60 + SECONDS1)/60, 0) FROM TIMES_TRAINING WHERE ID_SESSION = ?)";
        boolean isUpdate = false;
        try {
            Connection connection = DatabaseConnection.conectar();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, idSession);
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                isUpdate = true;
            }
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error de conexión.");
            alert.setHeaderText("¡ERROR!");
            alert.setContentText("Error al conectar a la base de datos: " + e.getMessage());
            alert.showAndWait();
        }
        return isUpdate;
    } // MEJOR MINUTO DE LA SESION

    public static boolean pbSecond (int idSession) {
        String sql = "UPDATE AVERAGE SET PERIOD_AVG = " +
                "(SELECT MIN(MINUTES1 * 60 + SECONDS1) - MIN(MINUTES1 * 60) FROM TIMES_TRAINING WHERE ID_SESSION = ?)";
        boolean isUpdate = false;
        try {
            Connection connection = DatabaseConnection.conectar();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, idSession);
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                isUpdate = true;
            }
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error de conexión.");
            alert.setHeaderText("¡ERROR!");
            alert.setContentText("Error al conectar a la base de datos: " + e.getMessage());
            alert.showAndWait();
        }
        return isUpdate;
    } // MEJOR SEGUNDO DE LA SESION

    public static void listTimes (int idSession){
        worstMinutes(idSession);
        worstSecond(idSession);
        pbMinutes(idSession);
        pbSecond(idSession);
        countTimes(idSession);


    }



    //MEDIA TOTAL

}

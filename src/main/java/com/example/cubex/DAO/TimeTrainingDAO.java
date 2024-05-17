package com.example.cubex.DAO;

import com.example.cubex.Database.DatabaseConnection;
import com.example.cubex.model.Average;
import com.example.cubex.model.CubeUser;
import com.example.cubex.model.TimeTraining;
import javafx.scene.control.Alert;

import java.security.PublicKey;
import java.security.spec.DSAPublicKeySpec;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
        List<TimeTraining> timesTraining = new ArrayList<>();
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


    public static List<TimeTraining> listTimesCategory (int idType, int idUser){
        List<TimeTraining> timesCategory = new ArrayList<>();
        try {
            Connection connection = DatabaseConnection.conectar();
            String sql = "SELECT  MINUTES1, SECONDS1, REGISTRATION_DATE\n" +
                    "FROM TIMES_TRAINING \n" +
                    "WHERE ID_SESSION IN (SELECT ID_SESSION FROM SESSIONS WHERE ID_TYPE = ? AND ID_USER = ?);";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, idType);
            statement.setInt(2, idUser);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int minutes = resultSet.getInt("MINUTES1");
                double seconds = resultSet.getDouble("SECONDS1");
                LocalDate date = resultSet.getDate("REGISTRATION_DATE").toLocalDate();
                TimeTraining timeTraining = new TimeTraining(minutes, seconds, date);
                timesCategory.add(timeTraining);
            }
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error de conexión.");
            alert.setHeaderText("¡ERROR!");
            alert.setContentText("Error al conectar a la base de datos: " + e.getMessage());
            alert.showAndWait();
        }
        return timesCategory;
    }




    public static List<TimeTraining> pbMinutesType(int idType) {
        List<TimeTraining> PbMinutesType = new ArrayList<>();
        String sql = "SELECT  MIN(MEDIA_MINUTOS)\n" +
                "FROM ( SELECT S.ID_SESSION,\n" +
                "    TRUNCATE(MIN(MINUTES1 * 60 + SECONDS1)/60, 0) AS MEDIA_MINUTOS\n" +
                "    FROM SESSIONS S\n" +
                "     JOIN TIMES_TRAINING T ON T.ID_SESSION = S.ID_SESSION\n" +
                "     JOIN CUBE_TYPE TY ON TY.ID_TYPE = S.ID_TYPE \n" +
                "     WHERE TY.ID_TYPE = 1\n" +
                "     GROUP BY S.ID_SESSION) AS MEDIA_MINUTOS\n" +
                " JOIN SESSIONS S ON S.ID_SESSION = MEDIA_MINUTOS.ID_SESSION\n" +
                " GROUP BY S.ID_SESSION\n" +
                " ORDER BY MIN(MEDIA_MINUTOS) ASC;";
        try {
            Connection connection = DatabaseConnection.conectar();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, idType);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int avgMinutes = resultSet.getInt("MIN(MEDIA_MINUTOS)");
                TimeTraining timeTraining = new TimeTraining(avgMinutes);
                PbMinutesType.add(timeTraining);
            }
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error de conexión.");
            alert.setHeaderText("¡ERROR!");
            alert.setContentText("Error al conectar a la base de datos: " + e.getMessage());
            alert.showAndWait();
        }
        return PbMinutesType;
    }

    public static List<TimeTraining> PbSecondsType(int idType) {
        List<TimeTraining> pbSeconds = new ArrayList<>();
        String sql = "SELECT  MIN(MEDIA_SEGUNDOS)\n" +
                "FROM ( SELECT S.ID_SESSION,\n" +
                "    MIN(MINUTES1 * 60 + SECONDS1) - MIN(MINUTES1 * 60) AS MEDIA_SEGUNDOS\n" +
                "    FROM SESSIONS S\n" +
                "     JOIN TIMES_TRAINING T ON T.ID_SESSION = S.ID_SESSION\n" +
                "     JOIN CUBE_TYPE TY ON TY.ID_TYPE = S.ID_TYPE \n" +
                "     WHERE TY.ID_TYPE = ? \n" +
                "     GROUP BY S.ID_SESSION) AS MEDIA_SEGUNDOS\n" +
                " JOIN SESSIONS S ON S.ID_SESSION = MEDIA_SEGUNDOS.ID_SESSION\n" +
                " GROUP BY S.ID_SESSION\n" +
                " ORDER BY MIN(MEDIA_SEGUNDOS) ASC;";
        try {
            Connection connection = DatabaseConnection.conectar();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, idType);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                double avgSeconds = resultSet.getDouble("MIN(MEDIA_SEGUNDOS)");
                TimeTraining timeTraining = new TimeTraining(avgSeconds);
                pbSeconds.add(timeTraining);
            }
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error de conexión.");
            alert.setHeaderText("¡ERROR!");
            alert.setContentText("Error al conectar a la base de datos: " + e.getMessage());
            alert.showAndWait();
        }
        return pbSeconds;
    }


    public static List<CubeUser> selectNamePbTime (List<TimeTraining> pbMinutes, List<TimeTraining> pbSeconds) {
        List<CubeUser> nameAverage = new ArrayList<>();
        String sql = "SELECT NAME_USER\n" +
                "FROM CUBE_USERS\n" +
                "WHERE ID_USER = (\n" +
                "    SELECT ID_USER\n" +
                "    FROM SESSIONS\n" +
                "    WHERE ID_SESSION IN (\n" +
                "        SELECT ID_SESSION\n" +
                "        FROM TIMES_TRAINING\n" +
                "        GROUP BY ID_SESSION\n" +
                "        HAVING (?) = MIN(MINUTES1 * 60 + SECONDS1) - MIN(MINUTES1 * 60) \n" +
                "        AND (?) = TRUNCATE(MIN(MINUTES1 * 60 + SECONDS1)/60, 0) \n" +
                "    ) \n" +
                ");";

        // ITERAR SOBRE EL PROMEDIO DE MINUTOS Y SEGUNDOS
        for (int i = 0; i < pbMinutes.size(); i++) {
            TimeTraining pbMinute = pbMinutes.get(i);
            TimeTraining pbSecond = pbSeconds.get(i);

            try {
                Connection connection = DatabaseConnection.conectar();
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setDouble(1, pbSecond.getSeconds());
                statement.setInt(2, pbMinute.getMinutes());

                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    String nameUser = resultSet.getString("NAME_USER");
                    CubeUser cubeUser = new CubeUser(nameUser);
                    nameAverage.add(cubeUser);
                }
            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error de conexión.");
                alert.setHeaderText("¡ERROR!");
                alert.setContentText("Error al conectar a la base de datos: " + e.getMessage());
                alert.showAndWait();
            }
        }
        return nameAverage;
    }


    public static boolean deleteTimesSession(String nameSession) {
        boolean isDelete = false;
        String sql = "DELETE FROM TIMES_TRAINING WHERE ID_SESSION = (SELECT ID_SESSION FROM SESSIONS WHERE NAME_SESSION = ?);";
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
}

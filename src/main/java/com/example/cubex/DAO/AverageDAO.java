package com.example.cubex.DAO;

import com.example.cubex.Controller.RegistrationCtrller;
import com.example.cubex.Database.DatabaseConnection;
import com.example.cubex.model.Average;
import com.example.cubex.model.CubeUser;
import com.example.cubex.model.TimeTraining;
import javafx.scene.control.Alert;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AverageDAO {
    public static boolean createAverageSession(int idSession) {
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

    public static boolean deleteAverage(String nameSession) {
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

    public static boolean countTimes(int idSession) {
        String sql = "UPDATE AVERAGE SET PERIOD_AVG = " +
                "(SELECT COUNT(ID_TIMES_TRAINING) FROM TIMES_TRAINING WHERE ID_SESSION = ?)" +
                " WHERE ID_SESSION = ?";
        boolean isUpdate = false;
        try {
            Connection connection = DatabaseConnection.conectar();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, idSession);
            statement.setInt(2, idSession);
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
                "(SELECT TRUNCATE(MAX(MINUTES1 * 60 + SECONDS1)/60, 0) FROM TIMES_TRAINING WHERE ID_SESSION = ?)" +
                " WHERE ID_SESSION = ?";
        boolean isUpdate = false;
        try {
            Connection connection = DatabaseConnection.conectar();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, idSession);
            statement.setInt(2, idSession);
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

    public static boolean worstSecond(int idSession) {
        String sql = "UPDATE AVERAGE SET WORST_SECONDS = " +
                "(SELECT MAX(MINUTES1 * 60 + SECONDS1) - MAX(MINUTES1 * 60) FROM TIMES_TRAINING WHERE ID_SESSION = ?)" +
                " WHERE ID_SESSION = ?";
        boolean isUpdate = false;
        try {
            Connection connection = DatabaseConnection.conectar();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, idSession);
            statement.setInt(2, idSession);
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


    public static boolean pbMinutes(int idSession) {
        String sql = "UPDATE AVERAGE SET PB_MINUTES = " +
                "(SELECT TRUNCATE(MIN(MINUTES1 * 60 + SECONDS1)/60, 0) FROM TIMES_TRAINING WHERE ID_SESSION = ?)" +
                " WHERE ID_SESSION = ?";
        boolean isUpdate = false;
        try {
            Connection connection = DatabaseConnection.conectar();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, idSession);
            statement.setInt(2, idSession);
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

    public static boolean pbSecond(int idSession) {
        String sql = "UPDATE AVERAGE SET PB_SECONDS = " +
                "(SELECT MIN(MINUTES1 * 60 + SECONDS1) - MIN(MINUTES1 * 60) FROM TIMES_TRAINING WHERE ID_SESSION = ?)" +
                " WHERE ID_SESSION = ?";
        boolean isUpdate = false;
        try {
            Connection connection = DatabaseConnection.conectar();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, idSession);
            statement.setInt(2, idSession);
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

    public static boolean avgMinutes(int idSession) {
        String sql = "UPDATE AVERAGE SET AVG_MINUTES = " +
                "(SELECT TRUNCATE((SUM(MINUTES1 * 60 + SECONDS1) - MIN(MINUTES1 * 60 + SECONDS1) - MAX(MINUTES1 * 60 + SECONDS1 ))/ (COUNT(MINUTES1) - 2) /60, 0) \n" +
                "    FROM  TIMES_TRAINING \n" +
                "    WHERE ID_SESSION = ?)" +
                "WHERE ID_SESSION = ?";
        boolean isUpdate = false;
        try {
            Connection connection = DatabaseConnection.conectar();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, idSession);
            statement.setInt(2, idSession);
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
    }

    public static boolean avgSeconds(int idSession) {
        String sql = "UPDATE AVERAGE SET AVG_SECONDS = " +
                "(SELECT MOD(TRUNCATE((SUM(MINUTES1 * 60 + SECONDS1) - MIN(MINUTES1 * 60 + SECONDS1) - MAX(MINUTES1 * 60 + SECONDS1 ))/ (COUNT(MINUTES1) - 2), 3), 60) \n" +
                "    FROM  TIMES_TRAINING \n" +
                "    WHERE ID_SESSION = ?)" +
                " WHERE ID_SESSION = ?";
        boolean isUpdate = false;
        try {
            Connection connection = DatabaseConnection.conectar();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, idSession);
            statement.setInt(2, idSession);
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
    }

    public static List<Average> avgMinutesType(int idType) {
        List<Average> averageMinutes = new ArrayList<>();
        String sql = "SELECT " +
                "TRUNCATE((SUM(MINUTES1 * 60 + SECONDS1) - MIN(MINUTES1 * 60 + SECONDS1) - MAX(MINUTES1 * 60 + SECONDS1 ))/ (COUNT(MINUTES1) - 2) / 60,0) AS MEDIA \n" +
                "FROM  SESSIONS S " +
                "JOIN TIMES_TRAINING T ON T.ID_SESSION = S.ID_SESSION " +
                "JOIN CUBE_TYPE TY ON TY.ID_TYPE = S.ID_TYPE " +
                "WHERE TY.ID_TYPE = ? AND S.ID_USER IN " +
                "   ( SELECT ID_USER FROM SESSIONS WHERE ID_TYPE = ?) " +
                "GROUP BY S.ID_USER;";
        try {
            Connection connection = DatabaseConnection.conectar();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, idType);
            statement.setInt(2, idType);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int avgMinutes = resultSet.getInt("MEDIA");
                System.out.println(avgMinutes);
                Average average = new Average(avgMinutes);
                averageMinutes.add(average);
            }
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error de conexión.");
            alert.setHeaderText("¡ERROR!");
            alert.setContentText("Error al conectar a la base de datos: " + e.getMessage());
            alert.showAndWait();
        }
        return averageMinutes;
    }

    public static List<Average> avgSecondsType(int idType) {
        List<Average> averageSeconds = new ArrayList<>();
        String sql = "SELECT " +
                "MOD(TRUNCATE((SUM(MINUTES1 * 60 + SECONDS1) - MIN(MINUTES1 * 60 + SECONDS1) - MAX(MINUTES1 * 60 + SECONDS1 ))/ (COUNT(MINUTES1) - 2), 3), 60) AS MEDIA " +
                "FROM  SESSIONS S " +
                "JOIN TIMES_TRAINING T ON T.ID_SESSION = S.ID_SESSION " +
                "JOIN CUBE_TYPE TY ON TY.ID_TYPE = S.ID_TYPE " +
                "WHERE TY.ID_TYPE = ? AND S.ID_USER IN " +
                "   ( SELECT ID_USER FROM SESSIONS WHERE ID_TYPE = ?) " +
                "GROUP BY S.ID_USER";
        try {
            Connection connection = DatabaseConnection.conectar();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, idType);
            statement.setInt(2, idType);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                double avgSeconds = resultSet.getDouble("MEDIA");
                Average average = new Average(avgSeconds);
                averageSeconds.add(average);
            }
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error de conexión.");
            alert.setHeaderText("¡ERROR!");
            alert.setContentText("Error al conectar a la base de datos: " + e.getMessage());
            alert.showAndWait();
        }
        return averageSeconds;
    }


    public static String selectNameAvg(double avgSeconds) {
        String sql = "SELECT NAME_USER\n" +
                "FROM CUBE_USERS\n" +
                "WHERE ID_USER = (\n" +
                "    SELECT ID_USER\n" +
                "    FROM SESSIONS\n" +
                "    WHERE ID_SESSION = (\n" +
                "        SELECT ID_SESSION\n" +
                "        FROM TIMES_TRAINING\n" +
                "        GROUP BY ID_SESSION\n" +
                "        HAVING MOD(TRUNCATE((SUM(MINUTES1 * 60 + SECONDS1) - MIN(MINUTES1 * 60 + SECONDS1) - MAX(MINUTES1 * 60 + SECONDS1 )) / (COUNT(MINUTES1) - 2), 3), 60) = ?\n" +
                "    ) \n" +
                ");";
        String nameUser = "";
        try {
            Connection connection = DatabaseConnection.conectar();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setDouble(1, avgSeconds);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                nameUser = resultSet.getString("NAME_USER");
                System.out.println(nameUser);
            }
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error de conexión.");
            alert.setHeaderText("¡ERROR!");
            alert.setContentText("Error al conectar a la base de datos: " + e.getMessage());
            alert.showAndWait();

        }
        return nameUser;
    }


    public static List<CubeUser> selectNameAvg(List<Average> avgMinutes, List<Average> avgSeconds) {
        List<CubeUser> nameAverage = new ArrayList<>();
        String sql = "SELECT NAME_USER\n" +
                "FROM CUBE_USERS\n" +
                "WHERE ID_USER = (\n" +
                "    SELECT ID_USER\n" +
                "    FROM SESSIONS\n" +
                "    WHERE ID_SESSION = (\n" +
                "        SELECT ID_SESSION\n" +
                "        FROM TIMES_TRAINING\n" +
                "        GROUP BY ID_SESSION\n" +
                "        HAVING MOD(TRUNCATE((SUM(MINUTES1 * 60 + SECONDS1) - MIN(MINUTES1 * 60 + SECONDS1) - MAX(MINUTES1 * 60 + SECONDS1 ))/ (COUNT(MINUTES1) - 2), 3), 60) = ? \n" +
                "        AND TRUNCATE((SUM(MINUTES1 * 60 + SECONDS1) - MIN(MINUTES1 * 60 + SECONDS1) - MAX(MINUTES1 * 60 + SECONDS1 ))/ (COUNT(MINUTES1) - 2) / 60, 0) = ?  \n" +
                "    )\n" +
                ")";
        // ITERAR SOBRE EL PROMEDIO DE MINUTOS Y SEGUNDOS
        for (int i = 0; i < avgMinutes.size(); i++) {
            Average avgMinute = avgMinutes.get(i);
            Average avgSecond = avgSeconds.get(i);

            try {
                Connection connection = DatabaseConnection.conectar();
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setDouble(1, avgSecond.getAvgSeconds()); // Valor promedio de segundos
                statement.setInt(2, avgMinute.getAvgMinutes()); // Valor promedio de minutos

                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    String nameUser = resultSet.getString("NAME_USER");
                    CubeUser cubeUser = new CubeUser(nameUser);
                    System.out.println(nameUser);
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




    /*MEJORES TIEMPOS*/
    public static List<Average> PbAvgMinutesType(int idType) {
        List<Average> PbAverageMinutes = new ArrayList<>();
        String sql = "SELECT MIN(MEDIA)\n" +
                "FROM ( SELECT \n" +
                "        TRUNCATE((SUM(MINUTES1 * 60 + SECONDS1) - MIN(MINUTES1 * 60 + SECONDS1) - MAX(MINUTES1 * 60 + SECONDS1)) / (COUNT(MINUTES1) - 2) / 60, 0) AS MEDIA\n" +
                "    FROM SESSIONS S\n" +
                "    JOIN TIMES_TRAINING T ON T.ID_SESSION = S.ID_SESSION\n" +
                "    JOIN CUBE_TYPE TY ON TY.ID_TYPE = S.ID_TYPE \n" +
                "    WHERE TY.ID_TYPE = ?\n" +
                ") AS MEDIA_MINUTES;";
        try {
            Connection connection = DatabaseConnection.conectar();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, idType);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int avgMinutes = resultSet.getInt("MIN(MEDIA)");
                System.out.println(avgMinutes);
                Average average = new Average(avgMinutes);
                PbAverageMinutes.add(average);
            }
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error de conexión.");
            alert.setHeaderText("¡ERROR!");
            alert.setContentText("Error al conectar a la base de datos: " + e.getMessage());
            alert.showAndWait();
        }
        return PbAverageMinutes;
    }

    public static List<Average> PbAvgSecondsType(int idType) {
        List<Average> pbAverageSeconds = new ArrayList<>();
        String sql = "SELECT MIN(MEDIA)\n" +
                "FROM ( SELECT \n" +
                "        MOD(TRUNCATE((SUM(MINUTES1 * 60 + SECONDS1) - MIN(MINUTES1 * 60 + SECONDS1) - MAX(MINUTES1 * 60 + SECONDS1)) / (COUNT(MINUTES1) - 2),3), 6) AS MEDIA\n" +
                "    FROM SESSIONS S\n" +
                "    JOIN TIMES_TRAINING T ON T.ID_SESSION = S.ID_SESSION\n" +
                "    JOIN CUBE_TYPE TY ON TY.ID_TYPE = S.ID_TYPE \n" +
                "    WHERE TY.ID_TYPE = ?\n" +
                ") AS MEDIA_SECONDS;";
        try {
            Connection connection = DatabaseConnection.conectar();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, idType);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                double avgSeconds = resultSet.getDouble("MIN(MEDIA)");
                Average average = new Average(avgSeconds);
                pbAverageSeconds.add(average);
            }
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error de conexión.");
            alert.setHeaderText("¡ERROR!");
            alert.setContentText("Error al conectar a la base de datos: " + e.getMessage());
            alert.showAndWait();
        }
        return pbAverageSeconds;
    }



    public static List<CubeUser> selectNamePbAvg(List<Average> avgMinutes, List<Average> avgSeconds) {
        List<CubeUser> nameAverage = new ArrayList<>();
        String sql = "SELECT NAME_USER\n" +
                "FROM CUBE_USERS\n" +
                "WHERE ID_USER = (\n" +
                "    SELECT ID_USER\n" +
                "    FROM SESSIONS\n" +
                "    WHERE ID_SESSION = (\n" +
                "        SELECT ID_SESSION\n" +
                "        FROM TIMES_TRAINING\n" +
                "        GROUP BY ID_SESSION\n" +
                "        HAVING MOD(TRUNCATE((SUM(MINUTES1 * 60 + SECONDS1) - MIN(MINUTES1 * 60 + SECONDS1) - MAX(MINUTES1 * 60 + SECONDS1)) / (COUNT(MINUTES1) - 2), 3), 6) = \n" +
                "            (?\n" +
                "        AND TRUNCATE((SUM(MINUTES1 * 60 + SECONDS1) - MIN(MINUTES1 * 60 + SECONDS1) - MAX(MINUTES1 * 60 + SECONDS1)) / (COUNT(MINUTES1) - 2) / 60, 0) =\n" +
                "            (?)\n" +
                "    ) \n" +
                ");";

        // ITERAR SOBRE EL PROMEDIO DE MINUTOS Y SEGUNDOS
        for (int i = 0; i < avgMinutes.size(); i++) {
            Average avgMinute = avgMinutes.get(i);
            Average avgSecond = avgSeconds.get(i);

            try {
                Connection connection = DatabaseConnection.conectar();
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setDouble(1, avgSecond.getAvgSeconds()); // Valor promedio de segundos
                statement.setInt(2, avgMinute.getAvgMinutes()); // Valor promedio de minutos

                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    String nameUser = resultSet.getString("NAME_USER");
                    CubeUser cubeUser = new CubeUser(nameUser);
                    System.out.println(nameUser);
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

    // SELECCIONAR LOS DATOS DE AVERAGE
    public static int timesTotalSession (int idSession){
        int timesTotal = -1;
        String sql = "SELECT PERIOD_AVG FROM AVERAGE WHERE ID_SESSION = ?";
        try {
            Connection connection = DatabaseConnection.conectar();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, idSession);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                timesTotal = resultSet.getInt("PERIOD_AVG");
                System.out.println(timesTotal);
            }
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error de conexión.");
            alert.setHeaderText("¡ERROR!");
            alert.setContentText("Error al conectar a la base de datos: " + e.getMessage());
            alert.showAndWait();
        }
        return timesTotal;
    }
    public static int worstMinutesTotalSession (int idSession){
        int worstMinutes = -1;
        String sql = "SELECT WORST_MINUTES FROM AVERAGE WHERE ID_SESSION = ?";
        try {
            Connection connection = DatabaseConnection.conectar();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, idSession);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                worstMinutes = resultSet.getInt("WORST_MINUTES");
            }
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error de conexión.");
            alert.setHeaderText("¡ERROR!");
            alert.setContentText("Error al conectar a la base de datos: " + e.getMessage());
            alert.showAndWait();
        }
        return worstMinutes;
    }

    public static double worstSecondTotalSession (int idSession){
        double worstSeconds = -1;
        String sql = "SELECT WORST_SECONDS FROM AVERAGE WHERE ID_SESSION = ?";
        try {
            Connection connection = DatabaseConnection.conectar();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setDouble(1, idSession);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                worstSeconds = resultSet.getDouble("WORST_SECONDS");
            }
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error de conexión.");
            alert.setHeaderText("¡ERROR!");
            alert.setContentText("Error al conectar a la base de datos: " + e.getMessage());
            alert.showAndWait();
        }
        return worstSeconds;
    }


    public static int pbMinutesTotalSession (int idSession){
        int pbMinutes = -1;
        String sql = "SELECT PB_MINUTES FROM AVERAGE WHERE ID_SESSION = ?";
        try {
            Connection connection = DatabaseConnection.conectar();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, idSession);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                pbMinutes = resultSet.getInt("PB_MINUTES");
            }
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error de conexión.");
            alert.setHeaderText("¡ERROR!");
            alert.setContentText("Error al conectar a la base de datos: " + e.getMessage());
            alert.showAndWait();
        }
        return pbMinutes;
    }

    public static double pbSecondTotalSession (int idSession){
        double pbSeconds = -1;
        String sql = "SELECT PB_SECONDS FROM AVERAGE WHERE ID_SESSION = ?";
        try {
            Connection connection = DatabaseConnection.conectar();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setDouble(1, idSession);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                pbSeconds = resultSet.getDouble("PB_SECONDS");
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

    public static int avgMinutesTotalSession (int idSession){
        int avgMinutes = -1;
        String sql = "SELECT AVG_MINUTES FROM AVERAGE WHERE ID_SESSION = ?";
        try {
            Connection connection = DatabaseConnection.conectar();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, idSession);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                avgMinutes = resultSet.getInt("AVG_MINUTES");
            }
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error de conexión.");
            alert.setHeaderText("¡ERROR!");
            alert.setContentText("Error al conectar a la base de datos: " + e.getMessage());
            alert.showAndWait();
        }
        return avgMinutes;
    }

    public static double avgSecondTotalSession (int idSession){
        double avgSeconds = -1;
        String sql = "SELECT AVG_SECONDS FROM AVERAGE WHERE ID_SESSION = ?";
        try {
            Connection connection = DatabaseConnection.conectar();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setDouble(1, idSession);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                avgSeconds = resultSet.getDouble("AVG_SECONDS");
            }
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error de conexión.");
            alert.setHeaderText("¡ERROR!");
            alert.setContentText("Error al conectar a la base de datos: " + e.getMessage());
            alert.showAndWait();
        }
        return avgSeconds;
    }



}



package com.example.cubex.DAO;

import com.example.cubex.Database.DatabaseConnection;
import javafx.scene.control.ComboBox;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CuberTypeDAO {
    public static void cubeCategory(ComboBox categoriesCB){
        categoriesCB.setPromptText("CATEGORY");
        try {
            Connection connection = DatabaseConnection.conectar();
            String sqlCat = "SELECT NAME_TYPE FROM CUBE_TYPE";
            PreparedStatement statement = connection.prepareStatement(sqlCat);
            ResultSet resultSet1 = statement.executeQuery();
            while (resultSet1.next()) {
                categoriesCB.getItems().addAll(resultSet1.getString("NAME_TYPE"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    } // METER LAS CATEGORIAS DE LOS CUBOS EN UN COMBOBOX
}

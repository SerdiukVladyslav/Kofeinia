package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class KofeiniaDatabase {
    private static final String URL = "jdbc:postgresql://localhost:5432/Kofeinia";
    private static final String USER = "postgres";
    private static final String PASSWORD = "your_password";

    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            System.out.println("Connected to the database!");

            // Пример вставки данных в таблицу Menu
            String insertMenuQuery = "INSERT INTO Menu (type, name_en, name_other_language, price) VALUES (?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertMenuQuery)) {
                preparedStatement.setString(1, "drink");
                preparedStatement.setString(2, "Coffee");
                preparedStatement.setString(3, "Кофе");
                preparedStatement.setDouble(4, 3.50);
                preparedStatement.executeUpdate();
                System.out.println("Data inserted into Menu table successfully.");
            }

            // Пример вставки данных в таблицу Staff
            String insertStaffQuery = "INSERT INTO Staff (full_name, phone, email, position) VALUES (?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertStaffQuery)) {
                preparedStatement.setString(1, "Иван Иванов");
                preparedStatement.setString(2, "+1234567890");
                preparedStatement.setString(3, "ivanov@example.com");
                preparedStatement.setString(4, "Бариста");
                preparedStatement.executeUpdate();
                System.out.println("Data inserted into Staff table successfully.");
            }

        } catch (SQLException e) {
            System.err.println("Error connecting to the database: " + e.getMessage());
        }
    }
}

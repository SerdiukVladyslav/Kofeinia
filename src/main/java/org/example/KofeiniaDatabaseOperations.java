package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class KofeiniaDatabaseOperations {
    private static final String URL = "jdbc:postgresql://localhost:5432/Kofeinia";
    private static final String USER = "postgres";
    private static final String PASSWORD = "your_password";

    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Scanner scanner = new Scanner(System.in)) {

            System.out.println("Connected to the database!");

            boolean continueLoop = true;
            while (continueLoop) {
                System.out.println("\nВыберите действие:");
                System.out.println("1. Добавить новую позицию в ассортимент кафе");
                System.out.println("2. Добавить информацию о новом бариста");
                System.out.println("3. Добавить информацию о новом кондитере");
                System.out.println("4. Добавить информацию о новом официанте");
                System.out.println("5. Добавить информацию о новом клиенте");
                System.out.println("6. Изменить цену на определенный вид кофе");
                System.out.println("7. Изменить контактный почтовый адрес кондитеру");
                System.out.println("8. Изменить контактный телефон бариста");
                System.out.println("9. Изменить процент скидки конкретного клиента");
                System.out.println("10. Удалить информацию о конкретном десерте");
                System.out.println("11. Удалить информацию об определенном официанте");
                System.out.println("12. Удалить информацию об определенном бариста");
                System.out.println("13. Удалить информацию о конкретном клиенте");
                System.out.println("14. Показать все напитки");
                System.out.println("15. Показать все десерты");
                System.out.println("16. Показать информацию обо всех бариста");
                System.out.println("17. Показать информацию обо всех официантах");
                System.out.println("0. Выход");
                System.out.print("Ваш выбор: ");

                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        addMenuItem(scanner, connection);
                        break;
                    case 2:
                        addStaffMember(scanner, connection, "Бариста");
                        break;
                    case 3:
                        addStaffMember(scanner, connection, "Кондитер");
                        break;
                    case 4:
                        addStaffMember(scanner, connection, "Официант");
                        break;
                    case 5:
                        addClient(scanner, connection);
                        break;
                    case 6:
                        updateCoffeePrice(scanner, connection);
                        break;
                    case 7:
                        updateConfectionerEmail(scanner, connection);
                        break;
                    case 8:
                        updateBaristaPhone(scanner, connection);
                        break;
                    case 9:
                        updateClientDiscount(scanner, connection);
                        break;
                    case 10:
                        deleteDessert(scanner, connection);
                        break;
                    case 11:
                        deleteStaffMember(scanner, connection, "Официант");
                        break;
                    case 12:
                        deleteStaffMember(scanner, connection, "Бариста");
                        break;
                    case 13:
                        deleteClient(scanner, connection);
                        break;
                    case 14:
                        showAllDrinks(connection);
                        break;
                    case 15:
                        showAllDesserts(connection);
                        break;
                    case 16:
                        showAllBaristas(connection);
                        break;
                    case 17:
                        showAllWaiters(connection);
                        break;
                    case 0:
                        continueLoop = false;
                        break;
                    default:
                        System.out.println("Неверный выбор. Попробуйте снова.");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error connecting to the database: " + e.getMessage());
        }
    }

    // Метод для добавления новой позиции в ассортимент кафе
    private static void addMenuItem(Scanner scanner, Connection connection) throws SQLException {
        System.out.print("Введите тип (drink/dessert): ");
        String type = scanner.nextLine();
        System.out.print("Введите название на английском: ");
        String nameEn = scanner.nextLine();
        System.out.print("Введите название на другом языке: ");
        String nameOther = scanner.nextLine();
        System.out.print("Введите цену: ");
        double price = scanner.nextDouble();
        scanner.nextLine();

        String query = "INSERT INTO Menu (type, name_en, name_other_language, price) VALUES (?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, type);
            preparedStatement.setString(2, nameEn);
            preparedStatement.setString(3, nameOther);
            preparedStatement.setDouble(4, price);
            preparedStatement.executeUpdate();
            System.out.println("Новая позиция в меню успешно добавлена.");
        }
    }

    // Метод для добавления информации о новом сотруднике
    private static void addStaffMember(Scanner scanner, Connection connection, String position) throws SQLException {
        System.out.print("Введите ФИО: ");
        String fullName = scanner.nextLine();
        System.out.print("Введите контактный телефон: ");
        String phone = scanner.nextLine();
        System.out.print("Введите контактный почтовый адрес: ");
        String email = scanner.nextLine();

        String query = "INSERT INTO Staff (full_name, phone, email, position) VALUES (?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, fullName);
            preparedStatement.setString(2, phone);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, position);
            preparedStatement.executeUpdate();
            System.out.println("Информация о новом сотруднике успешно добавлена.");
        }
    }

    // Метод для добавления информации о новом клиенте
    private static void addClient(Scanner scanner, Connection connection) throws SQLException {
        System.out.print("Введите ФИО: ");
        String fullName = scanner.nextLine();
        System.out.print("Введите дату рождения (yyyy-mm-dd): ");
        String birthDate = scanner.nextLine();
        System.out.print("Введите контактный телефон: ");
        String phone = scanner.nextLine();
        System.out.print("Введите контактный почтовый адрес: ");
        String email = scanner.nextLine();
        System.out.print("Введите скидку: ");
        double discount = scanner.nextDouble();
        scanner.nextLine();

        String query = "INSERT INTO Clients (full_name, birth_date, phone, email, discount) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, fullName);
            preparedStatement.setDate(2, java.sql.Date.valueOf(birthDate));
            preparedStatement.setString(3, phone);
            preparedStatement.setString(4, email);
            preparedStatement.setDouble(5, discount);
            preparedStatement.executeUpdate();
            System.out.println("Информация о новом клиенте успешно добавлена.");
        }
    }

    // Метод для изменения цены на определенный вид кофе
    private static void updateCoffeePrice(Scanner scanner, Connection connection) throws SQLException {
        System.out.print("Введите название кофе на английском: ");
        String coffeeName = scanner.nextLine();
        System.out.print("Введите новую цену: ");
        double newPrice = scanner.nextDouble();
        scanner.nextLine();

        String query = "UPDATE Menu SET price = ? WHERE name_en = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setDouble(1, newPrice);
            preparedStatement.setString(2, coffeeName);
            preparedStatement.executeUpdate();
            System.out.println("Цена на кофе успешно обновлена.");
        }
    }

    // Метод для изменения контактного почтового адреса кондитера
    private static void updateConfectionerEmail(Scanner scanner, Connection connection) throws SQLException {
        System.out.print("Введите ФИО кондитера: ");
        String fullName = scanner.nextLine();
        System.out.print("Введите новый почтовый адрес: ");
        String newEmail = scanner.nextLine();

        String query = "UPDATE Staff SET email = ? WHERE full_name = ? AND position = 'Кондитер'";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, newEmail);
            preparedStatement.setString(2, fullName);
            preparedStatement.executeUpdate();
            System.out.println("Контактный почтовый адрес кондитера успешно обновлен.");
        }
    }

    // Метод для изменения контактного телефона бариста
    private static void updateBaristaPhone(Scanner scanner, Connection connection) throws SQLException {
        System.out.print("Введите ФИО бариста: ");
        String fullName = scanner.nextLine();
        System.out.print("Введите новый контактный телефон: ");
        String newPhone = scanner.nextLine();

        String query = "UPDATE Staff SET phone = ? WHERE full_name = ? AND position = 'Бариста'";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, newPhone);
            preparedStatement.setString(2, fullName);
            preparedStatement.executeUpdate();
            System.out.println("Контактный телефон бариста успешно обновлен.");
        }
    }

    // Метод для изменения процента скидки конкретного клиента
    private static void updateClientDiscount(Scanner scanner, Connection connection) throws SQLException {
        System.out.print("Введите ФИО клиента: ");
        String fullName = scanner.nextLine();
        System.out.print("Введите новый процент скидки: ");
        double newDiscount = scanner.nextDouble();
        scanner.nextLine();

        String query = "UPDATE Clients SET discount = ? WHERE full_name = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setDouble(1, newDiscount);
            preparedStatement.setString(2, fullName);
            preparedStatement.executeUpdate();
            System.out.println("Процент скидки клиента успешно обновлен.");
        }
    }

    // Метод для удаления информации о конкретном десерте
    private static void deleteDessert(Scanner scanner, Connection connection) throws SQLException {
        System.out.print("Введите название десерта на английском языке: ");
        String dessertName = scanner.nextLine();

        String query = "DELETE FROM Menu WHERE name_en = ? AND type = 'dessert'";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, dessertName);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Информация о десерте успешно удалена.");
            } else {
                System.out.println("Десерт с таким названием не найден.");
            }
        }
    }

    // Метод для удаления информации об определенном сотруднике (официанте или бариста)
    private static void deleteStaffMember(Scanner scanner, Connection connection, String position) throws SQLException {
        System.out.print("Введите ФИО " + position.toLowerCase() + ": ");
        String fullName = scanner.nextLine();

        String query = "DELETE FROM Staff WHERE full_name = ? AND position = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, fullName);
            preparedStatement.setString(2, position);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Информация о " + position.toLowerCase() + " успешно удалена.");
            } else {
                System.out.println(position + " с таким ФИО не найден.");
            }
        }
    }

    // Метод для удаления информации о конкретном клиенте
    private static void deleteClient(Scanner scanner, Connection connection) throws SQLException {
        System.out.print("Введите ФИО клиента: ");
        String fullName = scanner.nextLine();

        String query = "DELETE FROM Clients WHERE full_name = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, fullName);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Информация о клиенте успешно удалена.");
            } else {
                System.out.println("Клиент с таким ФИО не найден.");
            }
        }
    }

    // Метод для показа всех напитков
    private static void showAllDrinks(Connection connection) throws SQLException {
        String query = "SELECT name_en, name_other_language, price FROM Menu WHERE type = 'drink'";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            System.out.println("Список всех напитков:");
            while (resultSet.next()) {
                String nameEn = resultSet.getString("name_en");
                String nameOther = resultSet.getString("name_other_language");
                double price = resultSet.getDouble("price");
                System.out.println("Название (EN): " + nameEn + ", Название (Local): " + nameOther + ", Цена: " + price);
            }
        }
    }

    // Метод для показа всех десертов
    private static void showAllDesserts(Connection connection) throws SQLException {
        String query = "SELECT name_en, name_other_language, price FROM Menu WHERE type = 'dessert'";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            System.out.println("Список всех десертов:");
            while (resultSet.next()) {
                String nameEn = resultSet.getString("name_en");
                String nameOther = resultSet.getString("name_other_language");
                double price = resultSet.getDouble("price");
                System.out.println("Название (EN): " + nameEn + ", Название (Local): " + nameOther + ", Цена: " + price);
            }
        }
    }

    // Метод для показа информации обо всех бариста
    private static void showAllBaristas(Connection connection) throws SQLException {
        String query = "SELECT full_name, phone, email FROM Staff WHERE position = 'Бариста'";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            System.out.println("Информация обо всех бариста:");
            while (resultSet.next()) {
                String fullName = resultSet.getString("full_name");
                String phone = resultSet.getString("phone");
                String email = resultSet.getString("email");
                System.out.println("ФИО: " + fullName + ", Телефон: " + phone + ", Email: " + email);
            }
        }
    }

    // Метод для показа информации обо всех официантах
    private static void showAllWaiters(Connection connection) throws SQLException {
        String query = "SELECT full_name, phone, email FROM Staff WHERE position = 'Официант'";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            System.out.println("Информация обо всех официантах:");
            while (resultSet.next()) {
                String fullName = resultSet.getString("full_name");
                String phone = resultSet.getString("phone");
                String email = resultSet.getString("email");
                System.out.println("ФИО: " + fullName + ", Телефон: " + phone + ", Email: " + email);
            }
        }
    }
}

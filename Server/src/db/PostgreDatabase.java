package db;

import java.sql.*;

public class PostgreDatabase implements Database {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USER = "postgres";
    private static final String PASS = "1337dflbv";
    private Connection connection;
    private Statement statement;

    public PostgreDatabase() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("PostgreSQL JDBC Driver не найден. Включите его в свой library path.");
            e.printStackTrace();
            return;
        }
        System.out.println("PostgreSQL JDBC Driver успешно подключен.");
        connection = null;
        connection = DriverManager.getConnection(DB_URL, USER, PASS);
        if (connection != null) {
            System.out.println("Успешное подключение к базе данных.");
        } else {
            System.out.println("Ошибка подключения к базе данных.");
        }
        statement = connection.createStatement();
    }

    public void execute(String command) throws SQLException {
        statement.execute(command);
    }

    public ResultSet get(String command) throws SQLException {
        return statement.executeQuery(command);
    }
}

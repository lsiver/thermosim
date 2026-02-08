package database;

import java.sql.*;


public class DatabaseManager {
    static String url = "jdbc:sqlite:thermodata.db";
    public static void createDB() {
        try (Connection conn = DriverManager.getConnection(url)){
            if (conn != null) {
                System.out.println("Database created/connected!");
            }
        } catch (SQLException e) {
            System.out.println("JDBC Error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        //createDB();
    }

}


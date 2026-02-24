package database;

import java.sql.*;
import java.util.ArrayList;

import database.CSVParser;
import database.AntoineRecord;


public class DatabaseManager {
    static String url = "jdbc:sqlite:thermodata.db";
    static CSVParser csvparser = new CSVParser();

    public static void createDB() {
        try (Connection conn = DriverManager.getConnection(url)){
            if (conn != null) {
                System.out.println("Database created/connected!");
            }
        } catch (SQLException e) {
            System.out.println("JDBC Error: " + e.getMessage());
        }
    }

    public static void createAntoineTable() {
        String sql = """
                CREATE TABLE IF NOT EXISTS antoine (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                name TEXT NOT NULL UNIQUE,
                formula TEXT NOT NULL,
                a_cons REAL NOT NULL,
                b_cons REAL NOT NULL,
                c_cons REAL NOT NULL,
                T_range TEXT,
                dH_vap REAL NOT NULL,
                tbp REAL NOT NULL
                );
                """;

        try (Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement()){

                stmt.execute(sql);
                System.out.println("Table created / connected successfully");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void seedAntoineTable() {
        String path = "src/main/resources/dbseed/antoine_constants.csv";
        AntoineRecord[] antoinerecords = csvparser.antoineParse(path);

        String sql = """
                INSERT INTO antoine (name, formula, a_cons, b_cons, c_cons, T_range, dH_vap, tbp)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?);
                """;

        try (Connection conn = DriverManager.getConnection(url);
            PreparedStatement ps = conn.prepareStatement(sql)) {

                conn.setAutoCommit(false);

                for (AntoineRecord arecord : antoinerecords) {
                    ps.setString(1, arecord.name());
                    ps.setString(2, arecord.formula());
                    ps.setDouble(3, arecord.a());
                    ps.setDouble(4,arecord.b());
                    ps.setDouble(5, arecord.c());
                    ps.setString(6,arecord.trange());
                    ps.setDouble(7,arecord.dhvap());
                    ps.setDouble(8, arecord.tbpnorm());
                    ps.executeUpdate();
                }

                conn.commit();
                conn.setAutoCommit(true);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public static void main(String[] args) {
        //createDB();
        //createAntoineTable();
        // seedAntoineTable();
    }

}


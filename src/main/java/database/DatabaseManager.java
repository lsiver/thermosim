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

    public static void createChemicalsTable() {
        String sql = """
        CREATE TABLE IF NOT EXISTS chemicals (
        id INTEGER PRIMARY KEY,
        name TEXT NOT NULL UNIQUE,
        preferred_name TEXT NOT NULL UNIQUE
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

    public static void createAntoineTable() {
        String sql = """
                CREATE TABLE IF NOT EXISTS antoine (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                chemical_id INTEGER NOT NULL,
                name TEXT NOT NULL UNIQUE,
                formula TEXT NOT NULL,
                a_cons REAL NOT NULL,
                b_cons REAL NOT NULL,
                c_cons REAL NOT NULL,
                T_range TEXT,
                dH_vap REAL NOT NULL,
                tbp REAL NOT NULL,
                FOREIGN KEY (chemical_id) REFERENCES chemicals(id)
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

    public static void seedChemicalsTable() {
        String path = "src/main/resources/dbseed/chemicals.csv";
        ChemicalRecord[] chemicalrecords = csvparser.chemicalParse(path);

        String sql = """
                INSERT INTO chemicals (id, name, preferred_name)
                VALUES (?, ?, ?);
                """;

        try (Connection conn = DriverManager.getConnection(url);
            PreparedStatement ps = conn.prepareStatement(sql)) {

                conn.setAutoCommit(false);

                for (ChemicalRecord arecord : chemicalrecords) {
                    ps.setInt(1, arecord.id());
                    ps.setString(2, arecord.name());
                    ps.setString(3, arecord.preferred_name());
                    ps.executeUpdate();
                }

                conn.commit();
                conn.setAutoCommit(true);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void seedAntoineTable() {
        String path = "src/main/resources/dbseed/antoine_constants.csv";
        AntoineRecord[] antoinerecords = csvparser.antoineParse(path);

        String sql = """
                INSERT INTO antoine (name, chemical_id, formula, a_cons, b_cons, c_cons, T_range, dH_vap, tbp)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);
                """;

        try (Connection conn = DriverManager.getConnection(url);
            PreparedStatement ps = conn.prepareStatement(sql)) {

                conn.setAutoCommit(false);

                for (AntoineRecord arecord : antoinerecords) {
                    ps.setString(1, arecord.name());
                    ps.setInt(2,arecord.chemical_id());
                    ps.setString(3, arecord.formula());
                    ps.setDouble(4, arecord.a());
                    ps.setDouble(5,arecord.b());
                    ps.setDouble(6, arecord.c());
                    ps.setString(7,arecord.trange());
                    ps.setDouble(8,arecord.dhvap());
                    ps.setDouble(9, arecord.tbpnorm());
                    ps.executeUpdate();
                }

                conn.commit();
                conn.setAutoCommit(true);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void createPureSpeciesTable() {
                String sql = """
                CREATE TABLE IF NOT EXISTS pure_species (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                chemical_id INTEGER NOT NULL,
                name TEXT NOT NULL UNIQUE,
                molar_mass REAL NOT NULL,
                acentric REAL NOT NULL,
                Tc REAL NOT NULL,
                Pc REAL NOT NULL,
                Zc REAL NOT NULL,
                Vc REAL NOT NULL,
                Tnbp REAL NOT NULL,
                FOREIGN KEY (chemical_id) REFERENCES chemicals(id)
                );
                """;

                /*
                molar mass g/gmol or lb/lbmol
                Tc K
                Pc bar
                Zc []
                Vc cm3 / mol
                Tn K
                 */

        try (Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement()){

                stmt.execute(sql);
                System.out.println("Table created / connected successfully");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void seedPureSpeciesTable() {
        String path = "src/main/resources/dbseed/pure_species_table.csv";
        PureSpeciesRecord[] purespeciesrecords = csvparser.pureSpeciesParse(path);

        String sql = """
                INSERT INTO pure_species (name, chemical_id, molar_mass, acentric, Tc, Pc, Zc, Vc, tnbp)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);
                """;

        try (Connection conn = DriverManager.getConnection(url);
            PreparedStatement ps = conn.prepareStatement(sql)) {

                conn.setAutoCommit(false);

                for (PureSpeciesRecord arecord : purespeciesrecords) {
                    ps.setString(1, arecord.name());
                    ps.setInt(2, arecord.chemical_id());
                    ps.setDouble(3, arecord.molar_mass());
                    ps.setDouble(4, arecord.acentric());
                    ps.setDouble(5,arecord.Tc());
                    ps.setDouble(6, arecord.Pc());
                    ps.setDouble(7,arecord.Zc());
                    ps.setDouble(8,arecord.Vc());
                    ps.setDouble(9, arecord.tbpnorm());
                    ps.executeUpdate();
                }

                conn.commit();
                conn.setAutoCommit(true);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }



    public static void createCpGasIdealTable() {
                String sql = """
                CREATE TABLE IF NOT EXISTS cpgasideal (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                chemical_id INTEGER NOT NULL,
                name TEXT NOT NULL UNIQUE,
                formula STRING NOT NULL,
                tmax REAL NOT NULL,
                cpig REAL NOT NULL,
                a REAL NOT NULL,
                b REAL NOT NULL,
                c REAL NOT NULL,
                d REAL NOT NULL,
                FOREIGN KEY (chemical_id) REFERENCES chemicals(id)
                );
                """;

                /*
                Tmax K
                cpig @ 298K / R
                Calculated Cp [=] Cp(ig) / R
                 */

        try (Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement()){

                stmt.execute(sql);
                System.out.println("Table created / connected successfully");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void seedCpGasIdealTable() {
        String path = "src/main/resources/dbseed/c1_heat_cap_of_gasess_ideal.csv";
        CpGasIdealRecord[] cpgasidealrecords = csvparser.CpGasIdealParse(path);

        String sql = """
                INSERT INTO cpgasideal (name, chemical_id, formula, tmax, cpig, a, b, c, d)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);
                """;

        try (Connection conn = DriverManager.getConnection(url);
            PreparedStatement ps = conn.prepareStatement(sql)) {

                conn.setAutoCommit(false);

                for (CpGasIdealRecord arecord : cpgasidealrecords) {
                    ps.setString(1, arecord.name());
                    ps.setInt(2, arecord.chemical_id());
                    ps.setString(3, arecord.formula());
                    ps.setDouble(4, arecord.Tmax());
                    ps.setDouble(5, arecord.Cpig());
                    ps.setDouble(6, arecord.A());
                    ps.setDouble(7, arecord.B());
                    ps.setDouble(8, arecord.C());
                    ps.setDouble(9, arecord.D());
                    ps.executeUpdate();
                }

                conn.commit();
                conn.setAutoCommit(true);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        // createDB();
        // createChemicalsTable();
        // seedChemicalsTable();
        // createAntoineTable();
        // seedAntoineTable();
        // createPureSpeciesTable();
        // seedPureSpeciesTable();
        // createCpGasIdealTable();
        // seedCpGasIdealTable();
    }

}


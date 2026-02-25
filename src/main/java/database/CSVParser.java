package database;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import database.AntoineRecord;

public class CSVParser {
    public ArrayList<String>  CSVParse(String path) {
        String line = "";

        ArrayList<String> returnList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(path))){
            String headerLine = br.readLine();

            while ((line = br.readLine()) != null) {
                returnList.add(line);

                String[] values = line.split(",");

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    return returnList;
    }

    public ChemicalRecord[] chemicalParse(String path) {
        ArrayList<String> inputtext = CSVParse(path);
        ChemicalRecord[] chemicalrecords = new ChemicalRecord[inputtext.size()];

        int i = 0;
        for (String chem : inputtext) {
            String[] values = chem.split(",");
            int id = Integer.parseInt(values[0]);
            String name = values[1];
            String preferred_name = values[2];

            chemicalrecords[i] = new ChemicalRecord(id, name, preferred_name);
            i++;
        }
        return chemicalrecords;
    }

    public AntoineRecord[] antoineParse(String path) {
        ArrayList<String> inputtext = CSVParse(path);
        AntoineRecord[] antoinerecords = new AntoineRecord[inputtext.size()];

        int i = 0;
        for (String chem : inputtext) {
            String[] values = chem.split(",");
            String name = values[0];
            String formula = values[1];
            double a = Double.parseDouble(values[2]);
            double b = Double.parseDouble(values[3]);
            double c = Double.parseDouble(values[4]);
            String T_range = values[5];
            double dH_vap = Double.parseDouble(values[6]);
            double tbp_norm = Double.parseDouble(values[7]);
            int chemical_id = Integer.parseInt(values[8]);

            antoinerecords[i] = new AntoineRecord(name, chemical_id, formula, a, b, c, T_range, dH_vap, tbp_norm);
            i++;
        }
        return antoinerecords;
    }

    public PureSpeciesRecord[] pureSpeciesParse(String path) {
        ArrayList<String> inputtext = CSVParse(path);
        PureSpeciesRecord[] purespeciesrecords = new PureSpeciesRecord[inputtext.size()];

        int i = 0;
        for (String chem : inputtext) {
            String[] values = chem.split(",");
            String name = values[0];
            double molar_mass = Double.parseDouble(values[1]);
            double acentric = Double.parseDouble(values[2]);
            double Tc = Double.parseDouble(values[3]);
            double Pc = Double.parseDouble(values[4]);
            double Zc = Double.parseDouble(values[5]);
            double Vc = Double.parseDouble(values[6]);
            double tbpnorm = Double.parseDouble(values[7]);
            int chemical_id = Integer.parseInt(values[8]);


            purespeciesrecords[i] = new PureSpeciesRecord(name, chemical_id, acentric, molar_mass, Tc, Pc, Zc, Vc, tbpnorm);
            i++;
        }
        return purespeciesrecords;
    }

    public CpGasIdealRecord[] CpGasIdealParse(String path) {
        ArrayList<String> inputtext = CSVParse(path);
        CpGasIdealRecord[] cpgasrecords = new CpGasIdealRecord[inputtext.size()];

        int i = 0;
        for (String chem : inputtext) {
            String[] values = chem.split(",");
            String name = values[0];
            String formula = values[1];
            double Tmax = Double.parseDouble(values[2]);
            double Cpig = Double.parseDouble(values[3]);
            double A = Double.parseDouble(values[4]);
            double B = Double.parseDouble(values[5]) / 1000.0;
            double C = Double.parseDouble(values[6]) / 1000000.0;
            double D = Double.parseDouble(values[7]) * 100000.0;
            int chemical_id = Integer.parseInt(values[8]);

            cpgasrecords[i] = new CpGasIdealRecord(name, chemical_id, formula, Tmax, Cpig, A, B, C, D);
            i++;

        }
        return cpgasrecords;
    }

    public static void main(String[] args) {

        String path = "src/main/resources/dbseed/antoine_constants.csv";
        String p_path = "src/main/resources/dbseed/pure_species_table.csv";
        String cp_path = "src/main/resources/dbseed/c1_heat_cap_of_gasess_ideal.csv";
        CSVParser test = new CSVParser();

        AntoineRecord[] antoinerecords = test.antoineParse(path);
        PureSpeciesRecord[] purespeciesrecords = test.pureSpeciesParse(p_path);
        CpGasIdealRecord[] cpgasidealrecords = test.CpGasIdealParse(cp_path);

    }
}
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
            //System.out.println(headerLine);

            while ((line = br.readLine()) != null) {
                returnList.add(line);

                String[] values = line.split(",");
                //System.out.println(values[0]);

//                System.out.println(values.length);
//                for (int i=0;i<values.length;i++) {
//                    System.out.println(values[i]);
//                }
                //System.out.println(values[0]);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    return returnList;
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

            antoinerecords[i] = new AntoineRecord(name, formula, a, b, c, T_range, dH_vap, tbp_norm);
            i++;
        }
        return antoinerecords;
    }

    public static void main(String[] args) {

        String path = "src/main/resources/dbseed/antoine_constants.csv";
        CSVParser test = new CSVParser();
        // ArrayList<String> tester = test.CSVParse(path);
        // System.out.println(tester.get(0).split(",")[0]);

        AntoineRecord[] antoinerecords = test.antoineParse(path);
        System.out.println(antoinerecords[0].formula());
        // System.out.println(test.CSVParse(path));
//        String line = "";
//
//        try (BufferedReader br = new BufferedReader(new FileReader(path))){
//            String headerLine = br.readLine();
//            //System.out.println(headerLine);
//
//            while ((line = br.readLine()) != null) {
//                String[] values = line.split(",");
//
//                System.out.println(values.length);
//                for (int i=0;i<values.length;i++) {
//                    System.out.println(values[i]);
//                }
//                //System.out.println(values[0]);
//            }
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}
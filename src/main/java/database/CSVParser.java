package database;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;

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

    public static void main(String[] args) {

        String path = "src/main/resources/dbseed/antoine_constants.csv";
        CSVParser test = new CSVParser();
        System.out.println(test.CSVParse(path));
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
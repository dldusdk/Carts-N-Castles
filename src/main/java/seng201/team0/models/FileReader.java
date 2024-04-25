package seng201.team0.models;

import java.util.ArrayList;
import java.io.*;
import java.util.Scanner;


//Class to read CSV File
public class FileReader {
    private ArrayList<Integer> list1;

    public FileReader(String path) {
        list1 = new ArrayList<Integer>(); //Intializes list to store value in CSV/Grid cells
        Scanner sc = null; //Intializing scanner to look through CSV file
        try {
            sc = new Scanner(new File(path));
            sc.useDelimiter(",");
            //Loops through values and appends to list
            while (sc.hasNext()) {
                String value = sc.next().trim();
                if(value.equals("")){
                    //First value is "" so skips it, might cause errors.
                    // Perhaps better to through exception???
                    continue;
                }
                int num = Integer.parseInt(value);
                list1.add(num);
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } finally {
            if (sc != null) {
                sc.close();
            }
        }
    }

    public ArrayList<Integer> getList() {
        return list1;
    }

    public static void main(String[] args) {
        boolean run = false;
        //Uncomment to run test and  demo of FileReader
        //run = true;
        if (run){
        FileReader file = new FileReader("src/main/resources/levelCSV/Level1/Level1Concept_Track.csv");
        ArrayList<Integer> list = file.getList();
        int count = 0;
        for (Integer num : list) {
            System.out.print(num + ",");
            count++;
            if (count % 20 == 0) {
                System.out.print("\n");}
            }
        }
    }

    }

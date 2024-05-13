package seng201.team0.services.fileReaders;

import java.util.ArrayList;
import java.io.*;
import java.util.Scanner;


//Class to read CSV File
public class FileReader {
    private ArrayList<ArrayList<Integer>> tileGridList;

    public FileReader(String path,int levelTilesWidth, int levelTilesHeight) {
        //Constructor that reads file of level in given dimensions
        this.tileGridList = new ArrayList<ArrayList<Integer>>(); //Intializes list to store value in CSV/Grid cells
        Scanner sc = null;
        try {
            sc = new Scanner(new File(path));
            sc.useDelimiter(",");

            for (int i = 0; i < levelTilesHeight; i++) {
                ArrayList<Integer> innerList = new ArrayList<>(levelTilesWidth);
                for (int j = 0; j < levelTilesWidth; j++) {
                    if (sc.hasNext()) {
                        String value = sc.next().trim();
                        if (!value.isEmpty()) {
                            int num = Integer.parseInt(value);
                            innerList.add(num);
                        }
                    }
                }
                tileGridList.add(innerList);
            }

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } finally {
            if(sc != null){
                sc.close();}

        }
    }

    public FileReader(String path,int levelTilesHeight) {
        System.out.print(path);
        //Constructor for loading path, sets to 2 because of 2 dimensions (x,y)
        int levelTilesWidth = 2;

        //Constructor that reads file of level
        this.tileGridList = new ArrayList<ArrayList<Integer>>(); //Intializes list to store value in CSV/Grid cells
        Scanner sc = null;
        try {
            sc = new Scanner(new File(path));
            sc.useDelimiter(",");

            for (int i = 0; i < levelTilesHeight; i++) {
                ArrayList<Integer> innerList = new ArrayList<>(levelTilesWidth);
                for (int j = 0; j < levelTilesWidth; j++) {
                    if (sc.hasNext()) {
                        String value = sc.next().trim();
                        if (!value.isEmpty()) {
                            int num = Integer.parseInt(value);
                            innerList.add(num);
                        }
                    }
                }
                tileGridList.add(innerList);
            }

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } finally {
            if(sc != null){
                sc.close();}

        }
    }



    public ArrayList<ArrayList<Integer>> getList() {
        return this.tileGridList;
    }

}


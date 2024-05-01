package seng201.team0.models;

import seng201.team0.services.Settings;

import java.util.ArrayList;
import java.io.*;
import java.util.Scanner;


//Class to read CSV File
public class FileReader {
    private ArrayList<ArrayList<Integer>> tileGridList;

    public FileReader(String path) {
        Settings settings = new Settings();
        int levelTilesWidth = settings.getTileWidth();
        int levelTilesHeight = settings.getTileHeight();
        int count = 0;
        tileGridList = new ArrayList<>();

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

    public static void main(String[] args) {
        boolean run = false;
        //Uncomment to run test and  demo of FileReader
        run = true;
        if (run) {
            FileReader file = new FileReader("src/main/resources/levelCSV/Level1/Level1Concept_Track.csv");
            ArrayList<ArrayList<Integer>> list = file.getList();
            for(ArrayList<Integer> element: list){
                System.out.println(element);
            }

                }
            }
        }


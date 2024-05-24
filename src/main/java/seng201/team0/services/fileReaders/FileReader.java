package seng201.team0.services.fileReaders;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

public class FileReader {

    private ArrayList<ArrayList<Integer>> list;

    public FileReader(InputStream fileStream, int rows, int columns) {
        this.list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(fileStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                ArrayList<Integer> innerList = new ArrayList<>();
                for (String value : values) {
                    innerList.add(Integer.parseInt(value));
                }
                this.list.add(innerList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public FileReader(InputStream inputStream, int levelTilesHeight) {
        int numCoords = 2;
        this.list = new ArrayList<>();

        try (Scanner sc = new Scanner(new BufferedReader(new InputStreamReader(inputStream)))) {
            sc.useDelimiter(",");

            for (int i = 0; i < levelTilesHeight; i++) {
                ArrayList<Integer> innerList = new ArrayList<>(numCoords);
                for (int j = 0; j < numCoords; j++) {
                    if (sc.hasNext()) {
                        String value = sc.next().trim();
                        if (!value.isEmpty()) {
                            int num = Integer.parseInt(value);
                            innerList.add(num);
                        }
                    }
                }
                this.list.add(innerList);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public ArrayList<ArrayList<Integer>> getList() {
        return this.list;
    }
}


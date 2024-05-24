package seng201.team0.services.fileReaders;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

public class FileReader {

    private ArrayList<ArrayList<Integer>> list;

    /**
     * This class uses the buffered reader class to read a file. This constrcuot is meant to read
     * level files that contain information about level layouts.
     * @param fileStream Input Stream so can work in jar files
     *
     * @author GGordon Homewood
     */
    public FileReader(InputStream fileStream) {
        this.list = new ArrayList<>();
        //Creat 2D array for x and y coords as it reads file
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

    /**
     * This construct is meant to read x and y coordinates of animations from a file
     * It uses the Java util scanner class for greater precision when iterating
     * through the specified 2 column 2D array.
     * @param inputStream Input Stream so can work in jar files
     * @param wayPoints number of x,y pairs to be evaulated.
     * @author Gordon Homewood
     */
    public FileReader(InputStream inputStream, int wayPoints) {
        int numCoords = 2;
        this.list = new ArrayList<>();

        try (Scanner sc = new Scanner(new BufferedReader(new InputStreamReader(inputStream)))) {
            sc.useDelimiter(",");

            for (int i = 0; i < wayPoints; i++) {
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

    /**
     * @return 2D array of coordinates
     * @author Gordon Homewood
     */
    public ArrayList<ArrayList<Integer>> getList() {
        return this.list;
    }
}


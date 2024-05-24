package seng201.team0.services.fileReaders;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class FileReaderTest {
    FileReader loadCSV;
    ArrayList<ArrayList<Integer>> expectedPath;
    @BeforeEach
    void setUp() {
        expectedPath = new ArrayList<>();
        ArrayList<Integer> point1 = new ArrayList<>();
        point1.add(0);
        point1.add(705);
        expectedPath.add(point1);

        ArrayList<Integer> point2 = new ArrayList<>();
        point2.add(320);
        point2.add(705);
        expectedPath.add(point2);

        ArrayList<Integer> point3 = new ArrayList<>();
        point3.add(320);
        point3.add(830);
        expectedPath.add(point3);

        ArrayList<Integer> point4 = new ArrayList<>();
        point4.add(705);
        point4.add(830);
        expectedPath.add(point4);

        ArrayList<Integer> point5 = new ArrayList<>();
        point5.add(705);
        point5.add(450);
        expectedPath.add(point5);

        ArrayList<Integer> point6 = new ArrayList<>();
        point6.add(1200);
        point6.add(450);
        expectedPath.add(point6);


        String load = "src/main/resources/levelCSV/Level1/Level1CartPath";
        int numWaypoints = 6;
        loadCSV = new FileReader(load, numWaypoints);


    }

    @Test
    void getList() {
        ArrayList<ArrayList<Integer>> list = loadCSV.getList();
        assertEquals(list,expectedPath);
    }
}
package seng201.team0.services.gameLoaders;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PathLoaderTest {
    PathLoader pathLoader;
    ArrayList<ArrayList<Integer>> expectedPath;
    ArrayList<ArrayList<Integer>> expectedRotates;

    @BeforeEach
    void setUp() {
        pathLoader = new PathLoader("src/main/resources/levelCSV/Level1/Level1CartPath", "src/main/resources/levelCSV/Level1/Level1RotatePath");
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

        expectedRotates = new ArrayList<>();

        ArrayList<Integer> point1A = new ArrayList<>();
        point1A.add(90);
        point1A.add(0);
        expectedRotates.add(point1A);

        ArrayList<Integer> point2A = new ArrayList<>();
        point2A.add(-90);
        point2A.add(0);
        expectedRotates.add(point2A);

        ArrayList<Integer> point3A = new ArrayList<>();
        point3A.add(-90);
        point3A.add(0);
        expectedRotates.add(point3A);

        ArrayList<Integer> point4A = new ArrayList<>();
        point4A.add(90);
        point4A.add(0);
        expectedRotates.add(point4A);

        ArrayList<Integer> point5A = new ArrayList<>();
        point5A.add(90);
        point5A.add(0);
        expectedRotates.add(point5A);

    }

    @Test
    void getPath() {
        assertEquals(pathLoader.getPath(),expectedPath);
    }

    @Test
    void getRotatePath() {
        assertEquals(pathLoader.getRotatePath(),expectedRotates);
    }
}
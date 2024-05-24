package seng201.team0.services.gameLoaders;

import seng201.team0.services.fileReaders.FileReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 *This class loads the rotations of carts into a file, using the provided FileReader
 * class.
 *
 * <p>This is useful because it allows the carts' turning direction on
 *  corners to be easily edited depending on the track layout. It also takes
 *  parameters based on the level loaded, making it very dynamic.<p/>
 *
 * @author Gordon Homewood
*/
public class PathLoader {

    private final ArrayList<ArrayList<Integer>> loadWaypoints = null;
    private final ArrayList<ArrayList<Integer>> loadRotatesDirections = null;

    public PathLoader(String wayPath,String rotatePath){
        int numWaypoints = 6; //Move this to settings
        int numRotates = 5;

        InputStream cartPathStream = getClass().getResourceAsStream(wayPath);
        InputStream rotatePathStream = getClass().getResourceAsStream(rotatePath);

        try (BufferedReader cartPathReader = new BufferedReader(new InputStreamReader(cartPathStream));
            BufferedReader rotatePathReader = new BufferedReader(new InputStreamReader(rotatePathStream))) {
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<ArrayList<Integer>> getPath()
    {return (loadWaypoints);}


    public ArrayList<ArrayList<Integer>> getRotatePath() {
        return(loadRotatesDirections);
    }
}

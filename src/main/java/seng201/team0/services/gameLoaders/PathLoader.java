package seng201.team0.services.gameLoaders;

import seng201.team0.services.fileReaders.FileReader;

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

    private ArrayList<ArrayList<Integer>> loadWaypoints;
    private ArrayList<ArrayList<Integer>> loadRotatesDirections;

    public PathLoader(String wayPath,String rotatePath){
        int numWaypoints = 6; //Move this to settings
        int numRotates = 5;

        FileReader cartLoader = new FileReader(wayPath, numWaypoints);
        loadWaypoints = cartLoader.getList();

        FileReader cartRotateLoader = new FileReader(rotatePath, numRotates);
        loadRotatesDirections = cartRotateLoader.getList();
    }

    public ArrayList<ArrayList<Integer>> getPath()
    {return (loadWaypoints);}


    public ArrayList<ArrayList<Integer>> getRotatePath() {
        return(loadRotatesDirections);
    }
}

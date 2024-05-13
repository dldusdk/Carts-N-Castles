package seng201.team0.services.gameLoaders;

import seng201.team0.services.fileReaders.FileReader;

import java.util.ArrayList;

public class PathLoader {

    private ArrayList<ArrayList<Integer>> loadWaypoints;
    private ArrayList<ArrayList<Integer>> loadRotatesDirections;

    public PathLoader(String wayPath,String rotatePath){
        int numWaypoints = 7; //Move this to settings
        int numRotates = 5;

        FileReader cartLoader = new FileReader(wayPath, numWaypoints);
        loadWaypoints = cartLoader.getList();

        FileReader cartRotateLoader = new FileReader(rotatePath, numRotates);
        loadRotatesDirections = cartRotateLoader.getList();
        //System.out.println(loadWaypoints);
    }



    public ArrayList<ArrayList<Integer>> getPath(){return (loadWaypoints);}


    public ArrayList<ArrayList<Integer>> getRotatePath() {return(loadRotatesDirections);
    }
}

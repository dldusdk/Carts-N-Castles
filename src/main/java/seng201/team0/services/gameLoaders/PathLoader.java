package seng201.team0.services.gameLoaders;

import seng201.team0.services.fileReaders.FileReader;

import java.util.ArrayList;

public class PathLoader {

    private ArrayList<ArrayList<Integer>> loadWaypoints;

    public PathLoader(String path){
        int numWaypoints = 7; //Move this to settings
        FileReader cartLoader = new FileReader(path, numWaypoints);
        loadWaypoints = cartLoader.getList();
        //System.out.println(loadWaypoints);
    }



    public ArrayList<ArrayList<Integer>> getPath(){return (loadWaypoints);}


}

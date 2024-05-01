package seng201.team0.services;

public class Settings {
    private int levelTilesWidth;
    private int levelTilesHeight;
    private int tilePixelSize;
    private int initalX;


    public Settings(){
        levelTilesHeight = 10;
        levelTilesWidth = 20;
        tilePixelSize = 64;
        initalX = 192;


    }

    public int getTileHeight(){
        return(levelTilesHeight);
    }

    public int getTileWidth(){
        return(levelTilesWidth);
    }

    public int getTilePixelSize(){
        return(tilePixelSize);
    }

    public int getIntialX() {
        return(initalX);
    }
}

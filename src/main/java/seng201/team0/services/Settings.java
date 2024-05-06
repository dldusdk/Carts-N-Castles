package seng201.team0.services;

public class Settings {
    private int levelTilesWidth;
    private int levelTilesHeight;
    private int tilePixelSize;
    private int initalX;
    private int cartNumber;
    private String difficulty;
    private int cartSpawnIndex;


    public Settings(){
        levelTilesHeight = 10;
        levelTilesWidth = 20;
        tilePixelSize = 64;
        initalX = 192;

    }

    public Settings(String setDifficulty){
        difficulty = setDifficulty;
        cartSpawnIndex = 0;


        if(difficulty == "Normal"){
            cartNumber = 10;
        }
        if(difficulty == "Hard"){
            cartNumber = 20;
        }
    }

    public int getCartSpawnX(){ return(0);}

    public int getCartSpawnY(){ return((320));}


    public int getCartNumber(){ return(cartNumber); }

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

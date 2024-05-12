package seng201.team0.services.settings;

public class Settings {
    private int levelTilesWidth;
    private int levelTilesHeight;
    private int tilePixelSize;
    private int initalX;
    private int cartNumber;
    private String difficulty;
    private int cartSpawnIndex;



    public Settings(){
        levelTilesHeight = 13;
        levelTilesWidth = 18;
        tilePixelSize = 64;
        initalX = 192;

    }

    public Settings(String setDifficulty){
        difficulty = setDifficulty;
        cartSpawnIndex = 0;

        if(difficulty == "Normal"){
            cartNumber = 1;
        }
        if(difficulty == "Hard"){
            cartNumber = 20;
        }
    }

    public int getCartSpawnX(){ return(0);}

    public int getCartSpawnY(){ return((728));}


    public int getCartNumber(int roundNumber){ return(roundNumber); }

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

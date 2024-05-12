package seng201.team0.models;

import seng201.team0.services.gameLoaders.LevelLoader;
import seng201.team0.services.settings.Settings;
import seng201.team0.services.gameLoaders.LoadRound;

public class Tower {

    String towerName;
    String towerType;
    String towerDescription;
    double towershootSpeed;
    int towerLevel;
    int towerPrice;
    int towerHealth;
}

//public defaultTowers(){
    // Allow 1 default silver tower upon spawn but allow the user to choose the placement



//public placeTower(int X, int Y){}
    // place the tower on the given coordinates
    // this should only be run if the coordinates correspond to a tile that is NOT invalid
    // meaning it should be a valid tile to run.
    // run the check if valid method
    // once TRUE is returned allow the placement of tower image on to map



//public bool checkifValid(double xCoord, double yCoord) {}
    // Checks if the coordinate the user clicked on is valid
    // Check what tiles the coordinate matches to, and as long as it is a grass or rock tile then return TRUE

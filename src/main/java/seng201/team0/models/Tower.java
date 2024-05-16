package seng201.team0.models;

import javafx.scene.control.Alert;

/**
 * ALLOW THREE TOWERS DEFAULT to buy when round starts after that it will be based on coins and
 */

public class Tower {

    String towerName;
    String towerType;
    String towerDescription;
    double towershootSpeed;
    int towerLevel;
    int towerPrice;
    int towerHealth;


    public Tower() {
    }
}

//public defaultTowers(){
    // Allow 1 default silver tower upon spawn but allow the user to choose the placement



//public placeTower(int X, int Y){}
    // place the tower on the given coordinates
    // this should only be run if the coordinates correspond to a tile that is NOT invalid
    // meaning it should be a valid tile to run.
    // run the check if valid method
    // once TRUE is returned allow the placement of tower image on to map

// Checks if the coordinate the user clicked on is valid
    // Check what tiles the coordinate matches to, and as long as it is a grass or rock tile then return TRUE


// public moveTower() {}
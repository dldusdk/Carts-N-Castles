package seng201.team0.models;

import java.util.ArrayList;
import java.util.List;


/**

 */

public class Tower {

    String towerName;
    String resourceType;
    String towerDescription;
    double reloadSpeed;
    double loadAmount;
    int towerLevel;
    int towerPrice;

    public Tower(String towerName, String resourceType, String towerDescription, double reloadSpeed, double loadAmount, int towerLevel, int towerPrice) {
        this.towerName = towerName;
        this.resourceType = resourceType;
        this.towerDescription = towerDescription;
        this.reloadSpeed = reloadSpeed;
        this.loadAmount = loadAmount;
        this.towerLevel = towerLevel;
        this.towerPrice = towerPrice;
    }

}

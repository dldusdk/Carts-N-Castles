package seng201.team0.services.gameLoaders;


import seng201.team0.models.towers.Tower;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class RandomEvent {
    private int destroyedChance;
    private ArrayList<Tower> mainTowers;
    private int buffChance;
    private int roundNumber;
    private int chance;
    private String type;


    public RandomEvent(ArrayList<Tower> mainTowers, String Difficulty, String type, int roundNumber){
        this.mainTowers = mainTowers;
        this.roundNumber = roundNumber;
        this.type = type;

    }

    public boolean getChance(){
        if(this.type.equals("Break")){
            return(true);
        }
        if(this.type.equals("Buff")){
            return(true);
        }


        return(false);
    }


    public Tower affectedTowerBreak(){
        if (!mainTowers.isEmpty()) {
            for (Tower tower : mainTowers) {
                if (tower.getRoundsPlayed().getLast() == this.roundNumber) {
                    //Prioritize most recent tower if used in last round
                    return (tower);
                }
            }
            //Return first tower placed if no tower used in last round
            return (mainTowers.getFirst());
        }
        //Return nothing if no towers are placed or all are in inactive state
        return(null);
    }

    public Tower affectedTowerBuff(){
        return(null);
    }
        //if (!mainTowers.isEmpty()) {

        //}
        //}

    public void startStuff(){
        if (destroyedChance == 1) {
            if (!mainTowers.isEmpty()) {
                Tower destroyedTower = mainTowers.get(0); //needs to be changed here based of round usage
                //if this tower is destroyed, needs to increment
                //Does destroying a tower, mean it has to wait a round to be repaired
                destroyedTower.setDestroyed(true);
        }
    }
        if (buffChance == 1) {
            if (!mainTowers.isEmpty()) {
            //need to check if destroyed or not
            Tower buffedTower = mainTowers.get(0);
        }
    }
}}

package seng201.team0.services.gameLoaders;


import seng201.team0.models.towers.Tower;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

public class RandomEvent {
    private boolean breakChance;
    private boolean buffChance;
    private ArrayList<Tower> mainTowers;
    private int roundNumber;
    private int chance;
    private String type;
    private String difficulty;


    public RandomEvent(ArrayList<Tower> mainTowers, String difficulty){
        this.mainTowers = mainTowers;
        this.roundNumber = roundNumber;
        this.type = type;
        this.difficulty = difficulty;

        this.buffChance = getBuffChance();


    }

    private boolean getBuffChance() {
        Random random = new Random();
        ArrayList<Integer>chanceList = new ArrayList<>();
        if(difficulty.equals("Easy")){
            chanceList.add(1);
            chanceList.add(1);
            chanceList.add(1);
            chanceList.add(1);
            int randomIndex = random.nextInt(chanceList.size());
            return(chanceList.get(randomIndex)==1);
        }
        if(difficulty.equals("Normal")){
            chanceList.add(1);
            chanceList.add(1);
            chanceList.add(0);
            chanceList.add(0);
            int randomIndex = random.nextInt(chanceList.size());
            return(chanceList.get(randomIndex)==1);
        }
        if(difficulty.equals("Hard")){
            chanceList.add(1);
            chanceList.add(0);
            chanceList.add(0);
            chanceList.add(0);
            int randomIndex = random.nextInt(chanceList.size());
            return(chanceList.get(randomIndex)==1);
        }
        return(false);
    }

    public boolean getBreakChance(){
        Random random = new Random();
        ArrayList<Integer>chanceList = new ArrayList<>();
        if(difficulty.equals("Easy")){
            chanceList.add(1);
            chanceList.add(0);
            chanceList.add(0);
            chanceList.add(0);
            int randomIndex = random.nextInt(chanceList.size());
            return(chanceList.get(randomIndex)==1);
        }
        if(difficulty.equals("Normal")){
            chanceList.add(1);
            chanceList.add(1);
            chanceList.add(0);
            chanceList.add(0);
            int randomIndex = random.nextInt(chanceList.size());
            return(chanceList.get(randomIndex)==1);
        }
        if(difficulty.equals("Hard")){
            chanceList.add(1);
            chanceList.add(1);
            chanceList.add(1);
            chanceList.add(0);
            int randomIndex = random.nextInt(chanceList.size());
            return(chanceList.get(randomIndex)==1);
    }
        return(false);
    }


    public Tower getAffectedTowerBreak(){
        if (!mainTowers.isEmpty() && getBreakChance()) {
            //If towers can be broken and break event is happening
            for (Tower tower : mainTowers) {
                if(!tower.getRoundsPlayed().isEmpty()){
                if (tower.getRoundsPlayed().getLast() == this.roundNumber) {
                    //Prioritize most recent tower if used in last round
                    return (tower);
                }}
            }
            //Return first tower placed if no tower used in last round
            return (mainTowers.getFirst());
        }
        //Return nothing if no towers are placed or all are in inactive state
        return(null);
    }

    public Tower getAffectedTowerBuff(){
        if (!mainTowers.isEmpty() && getBuffChance()) {
            for (Tower tower : mainTowers) {
                if (tower.getDestroyed()) {
                    continue;
                }
                return (tower);
            }}
        return(null);
    }


}




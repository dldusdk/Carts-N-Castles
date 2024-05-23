package seng201.team0.services.gameLoaders;


import seng201.team0.models.towers.Tower;

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


        public RandomEvent(ArrayList<Tower> mainTowers, String difficulty, int roundNumber){
        this.mainTowers = mainTowers;
        this.roundNumber = roundNumber;
        this.type = type;
        this.difficulty = difficulty;

        setBreakChance(calculateBreakChance());
        setBuffChance(calculateBuffChance());

    }

    private boolean calculateBuffChance() {
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

    public void setBreakChance(boolean chance){
            breakChance = chance;
    }

    public void setBuffChance(boolean chance){
            buffChance = chance;
    }

    public boolean getBreakChance(){
        return(breakChance);
    }

    public boolean getBuffChance(){
        return(buffChance);
    }

    public boolean calculateBreakChance(){
        Random random = new Random();
        ArrayList<Integer>chanceList = new ArrayList<>();
        if(difficulty.equals("Easy")){
            chanceList.add(1);
            chanceList.add(0);
            chanceList.add(0);
            chanceList.add(0);
            int randomIndex = random.nextInt(chanceList.size());
            return((chanceList.get(randomIndex)==1));
        }
        if(difficulty.equals("Normal")){
            chanceList.add(1);
            chanceList.add(1);
            chanceList.add(0);
            chanceList.add(0);
            int randomIndex = random.nextInt(chanceList.size());
            return((chanceList.get(randomIndex)==1));
        }
        if(difficulty.equals("Hard")){
            chanceList.add(1);
            chanceList.add(1);
            chanceList.add(1);
            chanceList.add(0);
            int randomIndex = random.nextInt(chanceList.size());
            return((chanceList.get(randomIndex)==1));
    }
        return(false);
    }


    public Tower getAffectedTowerBreak(){
        if (!mainTowers.isEmpty() && breakChance) {
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
        //Return null if no towers are placed or all are in inactive state
        //or if the random event isn't happening
        return(null);
    }

    public Tower getAffectedTowerBuff(){
        if (!mainTowers.isEmpty() && buffChance) {
            for (Tower tower : mainTowers) {
                if ((tower.getDestroyed()) | !(tower.getTowerState())){
                    continue;
                }
                return (tower);
            }}
        return(null);
    }


}




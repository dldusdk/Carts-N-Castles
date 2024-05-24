package seng201.team0.services.gameLoaders;


import seng201.team0.models.towers.Tower;

import java.util.ArrayList;
import java.util.Random;

/**
 * This class is in charge of returning the chance of a random event occuring on a new round and
 * also returns what tower should be affect by the random event.
 *
 * @author Gordon Homewood
 */
public class RandomEvent {
    private boolean breakChance;
    private boolean buffChance;
    private ArrayList<Tower> mainTowers;
    private int roundNumber;
    private int chance;
    private String type;
    private String difficulty;

    /**
     * Constructor for random event to occur.
     * @param mainTowers list of towers in main inventory
     * @param difficulty difficulty so event chance can be scaled
     * @param roundNumber round number to determine if tower was used in previous round
     */
    public RandomEvent(ArrayList<Tower> mainTowers, String difficulty, int roundNumber){
        this.mainTowers = mainTowers;
        this.roundNumber = roundNumber;
        this.type = type;
        this.difficulty = difficulty;

        setBreakChance(calculateBreakChance());
        setBuffChance(calculateBuffChance());

    }

    /**
     * This method calculates the buff chance by randomly selecting 0 ir 1 in a list. Each list is different based
     * of difficulty. A list was used here for easily readability and testing and doesn't affect the conciseness too
     * much, although it could be altered to an upper and lower bound if desired.
     * @return boolean of if buff should be applied
     * @author Gordon Homewood
     */
    private boolean calculateBuffChance() {
        Random random = new Random();
        ArrayList<Integer>chanceList = new ArrayList<>();
        if(difficulty.equals("Easy")){
            chanceList.add(1);
            chanceList.add(1);
            chanceList.add(1);
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
            chanceList.add(0);
            chanceList.add(0);
            chanceList.add(0);
            int randomIndex = random.nextInt(chanceList.size());
            return(chanceList.get(randomIndex)==1);
        }
        return(false);
    }

    /**
     * Overrides break chance. Should be called after chance is calculated.
     * @param chance boolean break should happen or not.
     *
     * @author Gordon Homewood
     */
    public void setBreakChance(boolean chance){
            breakChance = chance;
    }

    /**
     * Overrides buff chance. Should be called after chance is calculated.
     * @param chance boolean buff should happen or not.
     *
     * @author Gordon Homewood
     */
    public void setBuffChance(boolean chance){
            buffChance = chance;
    }

    /**
     * @return current break chance
     * @author Gordon Homewood
     */
    public boolean getBreakChance(){
        return(breakChance);
    }

    /**
     * @return current buff chance
     * @author Gordon Homewood
     */
    public boolean getBuffChance(){
        return(buffChance);
    }

    /**
     * This method calculates the break chance by randomly selecting 0 ir 1 in a list. Each list is different based
     * of difficulty. A list was used here for easily readability and testing and doesn't affect the conciseness too
     * much, although it could be altered to an upper and lower bound if desired.
     * @return boolean of if break should be applied
     * @author Gordon Homewood
     */
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


    /**
     * This method prioritizes the broken tower if it was used in the previous round, otherwise selects first
     * tower.
     * @return Returns what tower should be broken if the random event happens, otherwise return null
     *
     * @author Gordon Homewood
     */
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
        /* Return null if no towers are placed or all are in inactive state
        or if the random event isn't happening */
        return(null);
    }

    /**
     * This method returns the tower that should be buffed if the random event occurs.
     * Priority is based on first tower in the main tower list.
     * @return tower that should be buffed if all towers not destroyed and all towers not in reserve,
     * otherwise, return false.
     *
     * @author Gordon Homewood
     */
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




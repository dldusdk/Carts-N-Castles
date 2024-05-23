package seng201.team0.services.gameLoaders;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seng201.team0.models.towers.Tower;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class RandomEventTest {

    private RandomEvent randomEvent;
    private RandomEvent randomEventEmptyTowers;
    private ArrayList<Tower> towerList;
    private ArrayList<Tower> towerListEmpty;

    @BeforeEach
    void setUp() {
        Tower tower1 = new Tower("towerName", "Bronze", 2.0, 0.25, 1, 10,
                130, true, "Main");

        Tower tower2 = new Tower("towerName", "Bronze", 2.0, 0.25, 1, 10,
                130, true, "Main");

        Tower tower3 = new Tower("towerName", "Bronze", 2.0, 0.25, 1, 10,
                130, true, "Main");



        towerList = new ArrayList<>();
        towerList.add(tower1);
        towerList.add(tower2);

        tower1.incrementRound(1);
        tower2.incrementRound(2);
        tower3.incrementRound(1);

        towerListEmpty = new ArrayList<>();

        randomEvent = new RandomEvent(towerList,"Hard",2);
        randomEventEmptyTowers = new RandomEvent(towerListEmpty,"Hard,",2);
    }

    @Test
    void getBreakChanceTrue() {
        randomEvent.setBreakChance(true);
        assertTrue(randomEvent.getBreakChance());
    }
    @Test
    void getBreakChanceFalse() {
        randomEvent.setBreakChance(false);
        assertFalse(randomEvent.getBreakChance());
    }

    @Test
    void getBuffChanceTrue() {
        randomEvent.setBuffChance(true);
        assertTrue(randomEvent.getBuffChance());
    }
    @Test
    void getBuffChanceFalse() {
        randomEvent.setBuffChance(false);
        assertFalse(randomEvent.getBuffChance());
    }

    @Test
    void getAffectedTowerBreak() {
        // Checks if tower in most recent round is selected to be broken
        randomEvent.setBreakChance(true);
        assertEquals(randomEvent.getAffectedTowerBreak(),towerList.get(1));
    }

    @Test
    void getAffectedTowerBuff() {
        // Checks to see if first tower placed gets buffed
        randomEvent.setBuffChance(true);
        assertEquals(randomEvent.getAffectedTowerBuff(),towerList.get(0));
    }

    @Test
    void getAffectedTowerBuffEmptyList() {
        randomEventEmptyTowers.setBuffChance(true);
        Tower result = randomEventEmptyTowers.getAffectedTowerBuff();
        assertNull(result);
    }

    @Test
    void getAffectedTowerBreakEmptyList() {
        randomEventEmptyTowers.setBreakChance(true);
        Tower result = randomEventEmptyTowers.getAffectedTowerBreak();
        assertNull(result);
    }
}
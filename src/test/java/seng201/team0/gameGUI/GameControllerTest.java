package test.java.seng201.team0.gameGUI;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seng201.team0.gui.gameGUI.GameController;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;


class GameControllerTest {

    private GameController gameController;

    @BeforeEach
    void setUp() {
        gameController = new GameController();
    }
    @AfterEach
    void tearDown() {
        gameController = null;
    }

    @Test
    void init() {
        try {
            gameController.init(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertNotNull(gameController);
    }

    @Test
    void playMusic() {
        try {
            gameController.playMusic("src/main/resources/Music/bg/gameBGM.mp3");
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertNotNull(gameController);
    }

    @Test
    void towerUpgrades() {

    }

    @Test
    void generalShop() {
    }

    @Test
    void switchInventory() {
    }

    @Test
    void buyTower() {
    }

    @Test
    void buyUpgrade() {
    }

    @Test
    void sell() {
    }

    @Test
    void roundButtonClicked() {
    }
}
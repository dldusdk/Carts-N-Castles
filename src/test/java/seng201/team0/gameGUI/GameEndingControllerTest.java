package seng201.team0.gameGUI;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seng201.team0.gui.gameGUI.GameEndingController;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class GameEndingControllerTest {
    private GameEndingController gameEndingController;

    @BeforeEach
    void setUp() {
        GameEndingController gameEndingController = new GameEndingController();
    }

    @Test
    void init() {
        gameEndingController.init();
        assertNotNull(gameEndingController.primaryStage);
    }
}
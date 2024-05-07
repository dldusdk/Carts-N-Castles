package seng201.team0.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import seng201.team0.services.LevelLoader;
import seng201.team0.services.loadRound;

public class GameController {

    @FXML
    private ImageView trackDefault;
    @FXML
    private Button roundButton;
    @FXML
    private ImageView cartDefault;

    private Stage primaryStage;

    private int round = 0;

    private String difficulty;

    private LevelLoader levelGrid;

    /**
     * Initializes the controller with the primary stage
     * @param primaryStage The primary stage of the application
     */

    public void init(Stage primaryStage) {
        this.primaryStage = primaryStage;
        // Add any other initialization logic here
        difficulty = "Normal";

        roundButton.setText(String.valueOf("Play: "+round));
        levelGrid = new LevelLoader(trackDefault, "src/main/resources/levelCSV/Level1/Level1Concept_Track.csv");
    }

    @FXML
    public void roundButtonClicked(ActionEvent event){

        loadRound newRound = new loadRound(difficulty, cartDefault,levelGrid.getTileList());
        round++;
    }
    // Add other methods and properties as needed
}

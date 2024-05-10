package seng201.team0.gui.gameGUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import seng201.team0.services.gameLoaders.LevelLoader;
import seng201.team0.services.gameLoaders.LoadRound;
import seng201.team0.services.gameLoaders.PathLoader;

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
    private PathLoader path;


    /**
     * Initializes the controller with the primary stage
     * @param primaryStage The primary stage of the application
     */

    public void init(Stage primaryStage) {
        //Should put level path in Settings later


        this.primaryStage = primaryStage;
        // Add any other initialization logic here
        String levelPath =  "src/main/resources/levelCSV/Level1/Level1Concept_Track.csv";
        String levelDecor = "src/main/resources/levelCSV/Level1/Level1Concept_Decorations.csv";

        difficulty = "Normal";

        roundButton.setText(String.valueOf("Play: "+round));
        levelGrid = new LevelLoader(trackDefault,levelPath,levelDecor);
        path = new PathLoader("src/main/resources/levelCSV/Level1/Level1CartPath");
    }

    @FXML
    public void roundButtonClicked(ActionEvent event){

        LoadRound newRound = new LoadRound(round,difficulty, cartDefault,levelGrid,path);
        round++;
        roundButton.setText(String.valueOf(round));
        boolean roundState = true;
        roundButton.setDisable(roundState);
        while (roundState){
            int cartCount = newRound.getCartNumber();
            if (cartCount == 0){
                roundState = false;
            }
        }
        roundButton.setDisable(roundState);

    }
    // Add other methods and properties as needed
}

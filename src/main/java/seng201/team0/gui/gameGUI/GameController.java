package seng201.team0.gui.gameGUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
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
    private int round = 1;
    private String difficulty;
    private int totalRounds=10; //need to scale this on difficulty
    private LevelLoader levelGrid;
    private PathLoader path;
    @FXML
    private Pane testPane;


    /**
     * Initializes the controller with the primary stage
     * @param primaryStage The primary stage of the application
     */

    public void init(Stage primaryStage) {
        //Should put level path in Settings later

        testPane.setOnMouseClicked((MouseEvent event) -> {
            // Get the x and y coordinates of the mouse click event
            double x = event.getX();
            double y = event.getY();

            // Update the Text object to display the coordinates
            System.out.println("Mouse clicked at coordinates: (x=" + x + ", y=" + y + ")");
        });


        this.primaryStage = primaryStage;
        // Add any other initialization logic here
        String levelPath =  "src/main/resources/levelCSV/Level1/Level1Concept_Track.csv";
        String levelDecor = "src/main/resources/levelCSV/Level1/Level1Concept_Decorations.csv";

        difficulty = "Normal";

        roundButton.setText(String.valueOf("Play: "+round));
        levelGrid = new LevelLoader(trackDefault,levelPath,levelDecor);
        path = new PathLoader("src/main/resources/levelCSV/Level1/Level1CartPath");

        roundButton.setText((0 +"/"+String.valueOf(totalRounds)));


    }

    @FXML
    public void roundButtonClicked(ActionEvent event) {
        if (round > totalRounds) {
            roundButton.setDisable(true);
            //Should switch view to win screen.
        } else {
            LoadRound newRound = new LoadRound(round, difficulty, cartDefault, levelGrid, path,10);
            roundButton.setText((round + "/" + String.valueOf(totalRounds)));
            round++;
            boolean roundState = true;
            roundButton.setDisable(roundState);
            while (roundState) {
                int cartCount = 0;
                if (cartCount == 0) {
                    roundState = false;
                }
            }
        }
        roundButton.setDisable(false);
    }




    }
    // Add other methods and properties as needed

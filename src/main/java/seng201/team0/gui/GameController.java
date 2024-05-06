package seng201.team0.gui;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import seng201.team0.models.Cart;
import seng201.team0.services.LevelLoader;
import seng201.team0.services.loadRound;

import java.util.ArrayList;

/**
 * Controller for the main.fxml window
 * @author seng201 teaching team
 */
public class GameController {

    @FXML
    private ImageView trackDefault;
    @FXML
    private Button roundButton;
    @FXML
    private ImageView cartDefault;

    private int round = 0;
    private boolean roundState;
    private int cartNumber;

    private String difficulty;
    private ArrayList<ArrayList<Integer>> tileList;

    /**
     * Initialize the window
     *
     * @param stage Top level container for this window
     */
    public void init(Stage stage, String difficultySetting) {
         difficulty = difficultySetting;

        roundState = false;
        cartNumber = 10;

        roundButton.setText(String.valueOf("Play: "+round));

        LevelLoader level = new LevelLoader(trackDefault, "src/main/resources/levelCSV/Level1/Level1Concept_Track.csv");
        tileList = level.getTileList();
    }

    @FXML
    public void roundButtonClicked(ActionEvent event){
        loadRound newRound = new loadRound(difficulty, cartDefault,tileList);
        //Export this to a round class?
        //Create list of carts to be generated and handle stuff

        roundState = true;
        round++;
    }


}




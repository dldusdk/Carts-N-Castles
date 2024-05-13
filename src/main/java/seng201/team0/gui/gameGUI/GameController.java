package seng201.team0.gui.gameGUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
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

    @FXML
    private Button silverTower;
    @FXML
    private Button bronzeTower;
    @FXML
    private Button goldTower;
    @FXML
    private AnchorPane paneTest;
    // 0 Means not clicked
    private double buttonCounter = 0;
    private double paneX;
    private double paneY;


    private Stage primaryStage;
    private int round = 1;
    private String difficulty;
    private int totalRounds=10; //need to scale this on difficulty
    private LevelLoader levelGrid;
    private PathLoader path;
    @FXML
    //private Pane testPane;


    /**
     * Initializes the controller with the primary stage
     * @param primaryStage The primary stage of the application
     */

    public void init(Stage primaryStage) {
        //Should put level path in Settings later
        // Initialize Tower Instance

        this.primaryStage = primaryStage;
        // Add any other initialization logic here
        String levelPath =  "src/main/resources/levelCSV/Level1/Level1Concept_Track.csv";
        String levelDecor = "src/main/resources/levelCSV/Level1/Level1Concept_Decorations.csv";

        difficulty = "Normal";

        roundButton.setText(String.valueOf("Play: "+round));
        levelGrid = new LevelLoader(trackDefault,levelPath,levelDecor);
        path = new PathLoader("src/main/resources/levelCSV/Level1/Level1CartPath","src/main/resources/levelCSV/Level1/Level1RotatePath");

        ImageView goldMine = new ImageView("Art/Asset Pack/Resources/Gold Mine/GoldMine_Active.png");
        goldMine.setX(970);
        goldMine.setY(340);
        ((Pane) trackDefault.getParent()).getChildren().add(goldMine);


    }

    @FXML
    public void buyTower(MouseEvent event) {
        /***
         * Takes money away from currency
         * Tower placed where mouse clicked
         * - Checks if tower is in a valid point first then places
         * Removes item from shop
         */
        // When button is pressed, gets fx:id
        if (buttonCounter == 0) {
            Button pressedButton = (Button) event.getSource();
            // if fx:id = silverTower
            if (pressedButton == silverTower) {
                //buttonCounter += 1;
                System.out.println("Silver Tower button pressed!");
                paneClick(event);
                // Change button image to sold image and disable the button

                // Create new image view of silverTowerI,age
                pressedButton.setGraphic(new ImageView("Art/Shop/sold.png"));

                // Call method to check if tower can be placed
                //if (canPlaceTower()) {
                // Place tower if allowed
                //  System.out.println("Art/Asset Pack/Factions/Knights/Buildings/Tower/Tower_Blue.png");
                //canPlaceTower();
                // Minus money
                // Implement deduction of money here
                //} else {
                //  System.out.println("Cannot place tower on restricted tile!");
            }
            // if bronzeTower
            if (pressedButton == bronzeTower) {
                System.out.println("Bronze Tower button pressed!");
                // Implement logic for bronze tower placement
            }
            // if goldTower
            if (pressedButton == goldTower) {
                System.out.println("Gold Tower button pressed!");
                // Implement logic for gold tower placement
            }
        }
    }

    // Get tower placement coordinates on main Anchor Pane
    @FXML
    public void paneClick(MouseEvent event){
        paneX = event.getX();
        paneY = event.getY();
            // Set Cursor to tower image
            ImageView silverTowerImage = new ImageView("Art/Asset Pack/Factions/Knights/Buildings/Tower/Tower_Blue.png");
            silverTowerImage.setFitWidth(128);
            silverTowerImage.setFitHeight(256);
            paneTest.setCursor(new ImageCursor(silverTowerImage.getImage()));
            ((Pane) trackDefault.getParent()).getChildren().add(silverTowerImage);

            // Place cursor  in the middle of image
            silverTowerImage.setX(paneX - 64);
            silverTowerImage.setY(paneY - 128);
            // Disable silverTower button
            silverTower.setDisable(true);


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


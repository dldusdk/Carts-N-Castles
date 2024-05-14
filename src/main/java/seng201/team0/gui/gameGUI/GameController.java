package seng201.team0.gui.gameGUI;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.ImageCursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import seng201.team0.models.Shop;
import javafx.util.Duration;
import seng201.team0.models.carts.CartBasic;
import seng201.team0.services.gameLoaders.LevelLoader;
import seng201.team0.services.gameLoaders.LoadRound;
import seng201.team0.services.gameLoaders.PathLoader;

import java.util.ArrayList;
import java.util.Iterator;


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
    private int purchasedTowers = 0;
    private int availableTowers = 10;
    private double paneX;
    private double paneY;

    private boolean isPurchaseMode = false;
    private String selectedTowerType;


    private Stage primaryStage;
    private int round = 0;
    private String difficulty;
    private int totalRounds=10; //need to scale this on difficulty
    private LevelLoader levelGrid;
    private PathLoader path;
    private boolean roundState = false;
    private LoadRound newRound = null;
    private ArrayList<CartBasic> cartList;
    private int cartNumber;
    private boolean fail=false;

    private AnimationTimer collisionTimer = new AnimationTimer() {
        public void handle(long timestamp) {
            Iterator<CartBasic> iterator = cartList.iterator(); //So carts can safely be removed in loop
            while (iterator.hasNext()) {
                CartBasic cart = iterator.next();
                if (cart.getCartObject().getTranslateX() > 1025) {
                    iterator.remove();
                    cart.explode();
                    cartNumber--;
                }
            }
            if (newRound != null){
                if(cartNumber <= 0){
                    if(fail){
                        stopRound(false);
                    }
                    if(!fail){
                        stopRound(true);
                    }
                }
            }
        }
    };


    /**
     * Initializes the controller with the primary stage
     * @param primaryStage The primary stage of the application
     */

    public void init(Stage primaryStage) {
        //Should put level path in Settings later
        // Initialize Tower Instance
        roundButton.setText((0 +"/"+String.valueOf(totalRounds)));
        //save = new Save();
        //save.loadSave(new File("save/player1"));

        this.primaryStage = primaryStage;
        // Add any other initialization logic here
        String levelPath =  "src/main/resources/levelCSV/Level1/Level1Concept_Track.csv";
        String levelDecor = "src/main/resources/levelCSV/Level1/Level1Concept_Decorations.csv";

        difficulty = "Normal";

        roundButton.setText(String.valueOf("Play: "+ round));
        levelGrid = new LevelLoader(trackDefault,levelPath,levelDecor);
        path = new PathLoader("src/main/resources/levelCSV/Level1/Level1CartPath","src/main/resources/levelCSV/Level1/Level1RotatePath");

        //move this to own class
        ImageView goldMine = new ImageView("Art/Asset Pack/Resources/Gold Mine/GoldMine_Active.png");
        goldMine.setX(970);
        goldMine.setY(340);
        ((Pane) trackDefault.getParent()).getChildren().add(goldMine);


    }

    @FXML
    public void buyTower(MouseEvent event) {
        /**
         * @author Michelle Lee
         */
        // Initialize stock levels of tower
        Shop Shop = new Shop();
        availableTowers = Shop.towerStock();
        Button pressedButton = (Button) event.getSource();

        // If Towers are in stock
        if (availableTowers != 0) {
            // Tower Stock - 1
            availableTowers--;
            // if fx:id = bronzeTower
            if (pressedButton == bronzeTower) {
                selectedTowerType = "Bronze";
                isPurchaseMode = true;
                //bronzeTower stock - 1
                setupCursorForTower("Art/Asset Pack/Factions/Knights/Buildings/Tower/Tower_Red.png");
            }
            // if silverTower
            if (pressedButton == silverTower) {
                selectedTowerType = "Silver";
                isPurchaseMode = true;
                setupCursorForTower("Art/Asset Pack/Factions/Knights/Buildings/Tower/Tower_Blue.png");
            }
            // if goldTower
            if (pressedButton == goldTower) {
                selectedTowerType = "Gold";
                isPurchaseMode = true;
                setupCursorForTower("Art/Asset Pack/Factions/Knights/Buildings/Tower/Tower_Yellow.png");
            }
            else if (availableTowers == 0) {
                // Tower stock is 0, therefore sold out!!!
                pressedButton.setGraphic(new ImageView("Art/Shop/sold.png"));
            }
        }
    }

    private void setupCursorForTower(String imagePath) {
        /**
         * Changes the cursor depending on the button clicked
         * @author Michelle Lee
         */
        Image towerImage = new Image(imagePath);
        paneTest.setCursor(new ImageCursor(towerImage));
    }

    @FXML
    public void paneClick(MouseEvent event) {
        /**
         * If the towerButton is clicked and we have a tower type, get coordinates and check if able to place tower
         * If able to place, then place tower on map.
         * @author Michelle Lee
         */
        // If the tower button is clicked once get the coordinates
        if ((isPurchaseMode) && selectedTowerType != null) {
            paneX = event.getX();
            paneY = event.getY();
        }
        // if the coordinate is a valid point then we can place the tower
            if (canPlaceTower(paneX, paneY)) {
                placeTower(paneX, paneY, selectedTowerType);
                resetPurchaseMode();
            } else {
                invalidTowerPlacement();
            }
        }

    private void invalidTowerPlacement() {
        /**
         * Dialog box for any invalid tower placements
         * @author Michelle Lee
         */
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Invalid Tower Placement");
        alert.setHeaderText("You must not place the tower on the cart's track!");
        alert.setContentText(null);
        alert.showAndWait();
    }


    private boolean canPlaceTower(double x, double y) {
        /**
         * Checks whether the tower is able to be placed on selected tile
         * @author Michelle Lee
         */
        // Implement logic to check if the tower can be placed
        // For now, return true for simplicity
        return true;
    }

    private void placeTower(double x, double y, String towerType) {
        /**
         * Returns the correct image based on tower button chosen
         * @author Michelle Lee
         */
        String imagePath = "";
        switch (towerType) {
            case "Bronze":
                imagePath = "Art/Asset Pack/Factions/Knights/Buildings/Tower/Tower_Red.png";
                break;
            case "Silver":
                imagePath = "Art/Asset Pack/Factions/Knights/Buildings/Tower/Tower_Blue.png";
                break;
            case "Gold":
                imagePath = "Art/Asset Pack/Factions/Knights/Buildings/Tower/Tower_Yellow.png";
                break;
        }

        ImageView towerImage = new ImageView(new Image(imagePath));
        towerImage.setFitWidth(128);
        towerImage.setFitHeight(256);
        towerImage.setX(x - 64);
        towerImage.setY(y - 128);

        ((Pane) trackDefault.getParent()).getChildren().add(towerImage);
    }

    private void resetPurchaseMode() {
        /**
         * Once tower placed, reset all!
         * @author Michelle Lee
         */
        isPurchaseMode = false;
        selectedTowerType = null;
        paneTest.setCursor(null);  // Reset cursor to default
    }

    @FXML
    public void roundButtonClicked(ActionEvent event) {
        collisionTimer.start();
        roundState = true;
        if (round > totalRounds) {
            roundButton.setDisable(true);
            //Should switch view to win screen.
        } else {
            newRound = new LoadRound(round, difficulty, cartDefault, levelGrid, path,10);
            cartList = newRound.getCartList();
            cartNumber = newRound.getCartNumber();
            roundButton.setText((round + "/" + String.valueOf(totalRounds)));
            round++;
            roundButton.setDisable(roundState);

        }
    }
    private void stopRound(boolean state) {
        if(state){
            roundButton.setDisable(false);
        }
        else{
            roundButton.setDisable(true);
            gameOver();
        }
    }

    private void gameOver() {
        ImageView image = new ImageView("Art/Asset Pack/Factions/Knights/Troops/Dead/Dead.png");
        image.setX(250);
        image.setY(250);

        ((Pane) trackDefault.getParent()).getChildren().add(image);

        //Spawn New Button that resets Quits to mainScreen or reloads gamecontroller or just switch
        //this whole thing to new screen
    }


}
    // Add other methods and properties as needed


package seng201.team0.gui.gameGUI;

import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.ImageCursor;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javafx.stage.Window;
import seng201.team0.models.Shop;
import seng201.team0.models.carts.CartBasic;
import seng201.team0.models.towers.GoldMine;
import seng201.team0.services.gameLoaders.LevelLoader;
import seng201.team0.services.gameLoaders.LoadRound;
import seng201.team0.services.gameLoaders.PathLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import java.util.Optional;

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
    private AnchorPane gamePane;
    @FXML
    private ScrollPane generalShop;
    @FXML
    private ScrollPane upgradeShop;
    @FXML
    private Label instructionLabel;
    @FXML
    private Label playerCoins;
    @FXML
    private AnchorPane towerStats;
    @FXML
    private ImageView selectedTower;

    // Keeping track of placedTowers for inventory and selling purposes
    private List<ImageView> placedTowers = new ArrayList<>();

    // 0 Means not clicked
    private int purchasedTowers = 0;
    private int availableTowers = 10;
    private double paneX;
    private double paneY;

    private boolean isPurchaseMode = false;
    private boolean isSellMode = false;
    private String selectedTowerType;

    private Stage stage;
    private Stage primaryStage;
    private int roundNumber = 0;
    private String difficulty;
    private int totalRounds = 15; //need to scale this on player choice
    private LevelLoader levelGrid;
    private PathLoader path;
    private boolean roundState = false;
    private LoadRound newRound = null;
    private ArrayList<CartBasic> cartList;
    private int cartNumber;
    private boolean fail=false;
    private int coinBalance = 0;
    private GoldMine goldMine;

    private AnimationTimer collisionTimer = new AnimationTimer() {
        public void handle(long timestamp) {
            if(!cartList.isEmpty()){
                Iterator<CartBasic> iterator = cartList.iterator(); //So carts can safely be removed in loop
                while (iterator.hasNext()) {
                    CartBasic cart = iterator.next();
                    if(cart.getLoadPercent() >= 1 ){
                        iterator.remove();
                        cart.despawn();
                        cartNumber--;
                    }
                    if (cart.getCartObject().getTranslateX() > 1025 && cart.getLoadPercent() < 1) {
                        iterator.remove();
                        cart.explode();
                        cart.explode();
                        cartNumber--;
                        goldMine.decreaseHealth();
                        if(goldMine.getHealth() <= 0 ){
                            stopRound(false);
                            }
                        }
                        if(cartNumber <=0 && !(goldMine.getHealth() <=0 )){
                            stopRound(true);
                        }
                    }
                }} //should be possible to move to seperate class if needed

            //if (newRound != null){
               //if(cartNumber <= 0){
                    //if(fail){
                        //stopRound(false);
                    //}
                    //if(!fail){
                       //newRound = null;
                        //stopRound(true);
                    //}
                //}
            //}

    };

    public void init(Stage primaryStage) {
        /**
         * Initializes the controller with the primary stage
         * @param primaryStage The primary stage of the application
         */

        this.primaryStage = primaryStage;
        String levelPath = "src/main/resources/levelCSV/Level1/Level1Concept_Track.csv";
        String levelDecor = "src/main/resources/levelCSV/Level1/Level1Concept_Decorations.csv";

        playerCoins.setText(String.valueOf(coinBalance));
        difficulty = "Normal";

        roundButton.setText(String.valueOf("Start First Round!"));
        levelGrid = new LevelLoader(trackDefault,levelPath,levelDecor);
        path = new PathLoader("src/main/resources/levelCSV/Level1/Level1CartPath","src/main/resources/levelCSV/Level1/Level1RotatePath");

        //move this to own class

        goldMine = new GoldMine(trackDefault,2);

    }
    @FXML
    public void towerUpgrades(ActionEvent actionEvent) {
        /**
         * Makes Upgrades visible to buy for user
         * @author Michelle Lee
         */
        generalShop.setVisible(false);
        upgradeShop.setVisible(true);
        instructionLabel.setText("Select your upgrade and then the tower you would like to apply the upgrade to!");
    }

    @FXML
    public void generalShop(ActionEvent actionEvent) {
        /**
         *Makes Towers/Items visible to buy for user
         * @author Michelle Lee
         */
        upgradeShop.setVisible(false);
        generalShop.setVisible(true);
        instructionLabel.setText("Buy Towers and Boosters!");
    }

    @FXML
    public void buyTower(MouseEvent event) {
        /**
         * Method for buying Towers based on mouseClick!
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
            } else if (availableTowers == 0) {
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
        ImageView towerImage = new ImageView(imagePath);
        //towerImage.setFitWidth(128);
        //towerImage.setFitHeight(256);
        gamePane.setCursor(new ImageCursor(towerImage.getImage()));
    }

    @FXML
    public void paneClick(MouseEvent event) {
        /**
         * If the towerButton is clicked, and we have a tower type, get coordinates and check if able to place tower
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
            resetPurchaseMode();
        }
    }

    private boolean canPlaceTower(double x, double y) {
        /**
         * Checks whether the tower is able to be placed on selected tile
         * @author Michelle Lee
         */
        // If outside GamePane
        if (levelGrid.outsideGamePane(x,y)) {
            instructionLabel.setText("Please place the tower near the track");
            return false;
        }
        // If invalid tile
        if (levelGrid.invalidCoordChecker(x,y)){
            instructionLabel.setText("Please do not place the tower ON the track");
            return false;
        }
        else {
            return true;
        }
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
        // Tower image properties
        ImageView towerImage = new ImageView(new Image(imagePath));
        towerImage.setFitWidth(128);
        towerImage.setFitHeight(256);
        towerImage.setX(x - 64);
        towerImage.setY(y - 192);
        towerImage.setOnMouseClicked(this::checkTowerStats);
        // Add tower to map
        ((Pane) trackDefault.getParent()).getChildren().add(towerImage);
        // Add to placedTowers
        placedTowers.add(towerImage);
    }

    private void resetPurchaseMode() {
        /**
         * Once tower placed, reset all!
         * @author Michelle Lee
         */
        isPurchaseMode = false;
        selectedTowerType = null;
        gamePane.setCursor(null);  // Reset cursor to default
    }

    @FXML
    public void sell(ActionEvent actionEvent) {
        /**
         * If in sell mode, allow the deletion of towers
         * @author Michelle Lee
         */
        if (selectedTower != null) {
            // Remove the selected tower from the gamePane
            gamePane.getChildren().remove(selectedTower);

            // Remove the tower from the placedTowers list
            placedTowers.remove(selectedTower);

            // Reset the selected tower reference to null
            selectedTower = null;

            // Reset the cursor and sell mode
            gamePane.setCursor(null);
            isSellMode = false;

            // Reset the tower stats pane visibility
            towerStats.setVisible(false);
        }
    }


@FXML
    private void checkTowerStats(MouseEvent event) {
    ImageView newSelectedTower = (ImageView) event.getSource();

    // Check if a tower was previously selected
    if (selectedTower != null) {
        // Remove shadow effect from the previously selected tower
        selectedTower.setEffect(null);
    }
    // Apply shadow effect to the newly selected tower
    DropShadow dropShadow = new DropShadow();
    dropShadow.setColor(Color.RED);
    dropShadow.setRadius(20);
    newSelectedTower.setEffect(dropShadow);

    selectedTower = newSelectedTower;
    towerStats.setVisible(true);
}

    @FXML
    private void quitGame(ActionEvent actionEvent) {
        /**
         * A Method that allows the user to quit the application upon clicking a button
         * @author Michelle Lee
         */
        // Have a pop-up appear if the user clicks 'Quit' Button
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Quit");
        alert.setHeaderText("You are about to quit. Please be advised this game DOES NOT support saves! ");
        alert.setContentText("Are you sure you want to quit?");

        // If User clicks 'Confirm', Quit game
        if (alert.showAndWait().get() == ButtonType.OK) {
            stage = (Stage) gamePane.getScene().getWindow();
            System.out.println("You Successfully quit the game!");
            stage.close();
        }
    }

    @FXML
    public void roundButtonClicked(ActionEvent event) {
        /**
         *
         * @author Gordon Homewood
         */


        Dialog<ButtonType> userNameDialog = new Dialog<>();
        userNameDialog.setTitle("Choose Round Difficulty");
        int nextRound = roundNumber + 1;
        userNameDialog.setHeaderText("Round " + nextRound + " is about to start.");
        userNameDialog.setContentText("Select difficulty:\n\n" +
                "Easy Difficulty:\n" +
                "   50% of carts spawn at half full\n" +
                "   5 Lives\n" +
                "   5% chance for tower destroyed\n" +
                "   50% money awarded, 50% points\n\n" +
                "Normal Difficulty:\n" +
                "   25% of carts spawn at half full\n" +
                "   3 Lives\n" +
                "   10% chance for tower destroyed\n" +
                "   50% money awarded, 75% points\n\n" +
                "Hard Difficulty:\n" +
                "   0% of carts spawn full\n" +
                "   1 Life\n" +
                "   15% chance for tower destroyed\n" +
                "   100% money awarded, 100% points\n");

        ButtonType easyButton = new ButtonType("Easy", ButtonBar.ButtonData.OK_DONE);
        ButtonType mediumButton = new ButtonType("Normal", ButtonBar.ButtonData.OK_DONE);
        ButtonType hardButton = new ButtonType("Hard", ButtonBar.ButtonData.OK_DONE);
        ButtonType quitButton = new ButtonType("I'm Not Ready!", ButtonBar.ButtonData.CANCEL_CLOSE);

        userNameDialog.getDialogPane().getButtonTypes().addAll(easyButton, mediumButton, hardButton,quitButton);

        Optional<ButtonType> result = userNameDialog.showAndWait();

        if (result.isPresent()) {
            if (result.get() == easyButton) {
                difficulty = "Easy";
                launchRound();
            } else if (result.get() == mediumButton) {
                difficulty = "Normal";
                launchRound();
            } else if (result.get() == hardButton) {
                difficulty = "Hard";
                launchRound();
        }}

    }

    private void launchRound(){
        roundButton.setDisable(true);
        roundNumber++;
        collisionTimer.start();
        roundState = true;

        if (roundNumber > totalRounds) {
            roundButton.setDisable(true);
            // Should switch view to win screen.
        } else {
            ArrayList<Integer> cartTypeList = getCartNumber();
            newRound = new LoadRound(roundNumber, difficulty, cartDefault, levelGrid, path, cartTypeList);
            for (int num : cartTypeList) {
                cartNumber += num;
            }
            cartList = newRound.getCartList();
            roundButton.setText(roundNumber + "/" + totalRounds);
            roundState = true;
        }
    }

    private ArrayList<Integer> getCartNumber() {
        ArrayList<Integer> cartTypeNumbers= new ArrayList<>();
        if(roundNumber < 3){
            cartTypeNumbers.add(roundNumber + 1); //Bronze carts
            cartTypeNumbers.add(0);               //Silver carts
            cartTypeNumbers.add(0);               //Gold carts
            return(cartTypeNumbers);
        }
        if(roundNumber < 6){
            cartTypeNumbers.add(roundNumber + 1);
            cartTypeNumbers.add(roundNumber / 2 + 1);
            cartTypeNumbers.add(0);
            return(cartTypeNumbers);
        }
        if(roundNumber <= 10){
            cartTypeNumbers.add(roundNumber + 1);
            cartTypeNumbers.add(roundNumber / 2);
            cartTypeNumbers.add(roundNumber / 4);
            return(cartTypeNumbers);
        }
        cartTypeNumbers.add(roundNumber);
        cartTypeNumbers.add(roundNumber / 2);
        cartTypeNumbers.add(roundNumber / 3);
        return(cartTypeNumbers);

    }

    private void stopRound(boolean state) {
        if(state){
            roundButton.setDisable(false);
            coinBalance += roundNumber * 50;
            playerCoins.setText(String.valueOf(coinBalance));
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


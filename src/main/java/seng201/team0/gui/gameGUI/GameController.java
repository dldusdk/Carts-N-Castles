package seng201.team0.gui.gameGUI;

import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.ImageCursor;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import seng201.team0.models.Shop;
import seng201.team0.models.carts.CartBasic;
import seng201.team0.services.gameLoaders.LevelLoader;
import seng201.team0.services.gameLoaders.LoadRound;
import seng201.team0.services.gameLoaders.PathLoader;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    private AnchorPane gamePane;
    @FXML
    private ScrollPane generalShop;
    @FXML
    private ScrollPane upgradeShop;
    @FXML
    private Label instructionLabel;

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
    private int totalRounds = 10; //need to scale this on player choice
    private LevelLoader levelGrid;
    private PathLoader path;
    private boolean roundState = false;
    private LoadRound newRound = null;
    private ArrayList<CartBasic> cartList;
    private int cartNumber;
    private boolean fail=false;

    private AnimationTimer collisionTimer = new AnimationTimer() {
        public void handle(long timestamp) {
            if(!cartList.isEmpty()){
                Iterator<CartBasic> iterator = cartList.iterator(); //So carts can safely be removed in loop
                while (iterator.hasNext()) {
                    CartBasic cart = iterator.next();
                    if (cart.getCartObject().getTranslateX() > 1025) {
                        iterator.remove();
                        cart.explode();
                        cartNumber--;
                    }
                }} //should be possible to move to seperate class if needed

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

    public void init(Stage primaryStage) {
        /**
         * Initializes the controller with the primary stage
         * @param primaryStage The primary stage of the application
         */

        this.primaryStage = primaryStage;
        String levelPath = "src/main/resources/levelCSV/Level1/Level1Concept_Track.csv";
        String levelDecor = "src/main/resources/levelCSV/Level1/Level1Concept_Decorations.csv";

        difficulty = "Normal";

        roundButton.setText(String.valueOf("Play: "+ 0));
        levelGrid = new LevelLoader(trackDefault,levelPath,levelDecor);
        path = new PathLoader("src/main/resources/levelCSV/Level1/Level1CartPath","src/main/resources/levelCSV/Level1/Level1RotatePath");

        //move this to own class
        ImageView goldMine = new ImageView("Art/Asset Pack/Resources/Gold Mine/GoldMine_Active.png");
        goldMine.setX(970);
        goldMine.setY(340);
        ((Pane) trackDefault.getParent()).getChildren().add(goldMine);
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
            //placedTowerPoints.add( new Point((int) paneX, (int) paneY));
        }
        // if the coordinate is a valid point then we can place the tower
        if (canPlaceTower(paneX, paneY)) {
            placeTower(paneX, paneY, selectedTowerType);
            resetPurchaseMode();
        } else {
            resetPurchaseMode();
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
        // If outside of GamePane
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
        towerImage.setOnMouseClicked(this::selectTowerForSelling);
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
        if (!isSellMode && !isPurchaseMode) {
            // Enter sell mode and update Cursor
            instructionLabel.setText("Select one Tower you would like to sell, to sell another tower please click the Sell button after the first tower");
            isSellMode = true;
            gamePane.setCursor(new ImageCursor(new Image("Art/Shop/sell.png")));
        } else {
            // Exit sell mode if sell button is clicked again
            //instructionLabel.setText("Sell mode exited.");
            //isSellMode = false;
            gamePane.setCursor(null); // Reset cursor to default
        }
    }


    private void selectTowerForSelling(MouseEvent event) {
        /**
         * Delete image where user clicks
         * @author Michelle Lee
         */
        System.out.println("Tower clicked for selling.");
        if (isSellMode) {
            ImageView selectedTower = (ImageView) event.getSource();
            ((Pane) trackDefault.getParent()).getChildren().remove(selectedTower);
            placedTowers.remove(selectedTower);
            instructionLabel.setText("Tower sold successfully!");
            isSellMode = false; // Exit sell mode after selling
            gamePane.setCursor(null); // Reset cursor to default
        }
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
        roundNumber++;
        collisionTimer.start();
        roundState = true;
        if (roundNumber > totalRounds) {
            roundButton.setDisable(true);
            //Should switch view to win screen.
        } else {
            roundButton.setDisable(true);
            ArrayList<Integer>cartTypeList = getCartNumber(roundNumber);
            newRound = new LoadRound(roundNumber, difficulty, cartDefault, levelGrid, path,getCartNumber(roundNumber));
            for(int num: cartTypeList){
                cartNumber += num;
            }
            cartList = newRound.getCartList();
            roundButton.setText((roundNumber + "/" + String.valueOf(totalRounds)));
            boolean roundState = true;
        }
    }

    private ArrayList<Integer> getCartNumber(int round) {
        ArrayList<Integer> cartTypeNumbers= new ArrayList<>();
        if(roundNumber < 3){
            cartTypeNumbers.add(roundNumber + 20); //Bronze carts
            cartTypeNumbers.add(0);               //Silver carts
            cartTypeNumbers.add(0);               //Gold carts
            return(cartTypeNumbers);
        }
        if(roundNumber >= 3 && roundNumber < 6){
            cartTypeNumbers.add(roundNumber + 1);
            cartTypeNumbers.add(roundNumber / 2 + 1);
            cartTypeNumbers.add(0);
            return(cartTypeNumbers);
        }
        if(roundNumber >= 6 && roundNumber <= 10){
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


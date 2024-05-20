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
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import seng201.team0.models.Shop;
import seng201.team0.models.towers.Tower;
import seng201.team0.models.carts.CartBasic;
import seng201.team0.models.towers.GoldMine;
import seng201.team0.models.towers.Projectile;
import seng201.team0.services.gameLoaders.LevelLoader;
import seng201.team0.services.gameLoaders.LoadRound;
import seng201.team0.services.gameLoaders.PathLoader;

import java.util.*;


// CHANGE

public class GameController {

    // Main Pane and different Views
    @FXML
    private AnchorPane gamePane;
    @FXML
    private Label instructionLabel;

    // Track
    @FXML
    private ImageView trackDefault;
    @FXML
    private Button roundButton; //change name to be more specific
    @FXML
    private ImageView cartDefault;

    // Towers
    @FXML
    private ImageView selectedTowerImage;
    @FXML
    private Button silverTower;
    @FXML
    private Button bronzeTower;
    @FXML
    private Button goldTower;
    @FXML
    private Label towerNameLabel;
    @FXML
    private Label resourceTypeLabel;
    @FXML
    private Label reloadSpeedLabel;
    @FXML
    private Label loadAmountLabel;
    @FXML
    private Label levelLabel;
    @FXML
    private Label activeLabel;
    @FXML
    private Label inventoryLocationLabel;
    @FXML
    private AnchorPane towerStats;
    @FXML
    private ImageView selectedTower;

    //Shop
    @FXML
    private ScrollPane generalShop;
    @FXML
    private ScrollPane upgradeShop;
    @FXML
    private Label playerCoins;
    @FXML
    private Label playerLives;
    @FXML
    private Button upgradeshootSpeed;
    @FXML
    private Button upgradeRange;

    // GUI variables
    private Stage stage;
    private Stage primaryStage;

    // Tower variables
    private List<Tower> mainTowers = new ArrayList<>();
    private List<Tower> reserveTowers = new ArrayList<>();
    private Map<ImageView, Tower> towersMap = new HashMap<>();
    private double paneX;
    private double paneY;
    private boolean isPurchaseMode = false;
    private boolean isSellMode = false;
    private String selectedTowerType;
    private String userInputTowerName;
    private Circle radiusCircle;

    // Shop Variables
    private Shop shop;
    private int purchasedTowers = 0;
    private int coinBalance = 200;
    private boolean upgradePurchased = false;


    // Round and Animation Variables
    private int totalRounds = 15; //need to scale this on player choice
    private int roundNumber = 0;
    private LoadRound newRound = null;
    private boolean roundState = false;
    private String difficulty;
    private boolean fail = false;


    // Track Variables
    private LevelLoader levelGrid;
    private PathLoader path;
    private ArrayList<CartBasic> cartList;
    private int cartNumber;
    //private boolean fail=false;
    //private int coinBalance = 200;
    private GoldMine goldMine;
    private List<Projectile> projectiles = new ArrayList<>(); // Add this line to keep track of projectiles


    private AnimationTimer collisionTimer = new AnimationTimer() {
        public void handle(long timestamp) {
            //System.out.println(placedTowers);
            System.out.println(mainTowers);
            if (!cartList.isEmpty()) {
                for (Tower tower : mainTowers) {
                    // Only if tower is active
                    if (!tower.getTowerState()) {
                        continue;
                    }
                    double fireRate = tower.getFireRate(); //need to implement this
                    CartBasic towerTarget = tower.targetAcquisition(cartList);
                    if (timestamp - tower.getProjectileTime() >= fireRate && towerTarget.getCartObject().getTranslateX() > 0
                            && tower.getRadius() > tower.getDistance(towerTarget.getCartObject().getTranslateX(), towerTarget.getCartObject().getTranslateY())) {
                        //.out.println(tower.getDistance(towerTarget.getCartObject().getTranslateX(),towerTarget.getCartObject().getTranslateY()));
                        //System.out.println(tower.getRadius());

                        double damage = tower.getLoadAmount();
                        String type = tower.getResourceType();
                        int spawnX = (int) (tower.getX() - 30);
                        int spawnY = (int) (tower.getY() - 30);

                        Projectile projectile = new Projectile(spawnX, spawnY, type, trackDefault, towerTarget, damage);
                        projectile.spawn();
                        projectiles.add(projectile);
                        tower.setProjectileTime(timestamp);
                    }

                }
                Iterator<CartBasic> iterator = cartList.iterator(); //So carts can safely be removed in loop
                while (iterator.hasNext()) {
                    CartBasic cart = iterator.next();

                    if (cart.getCartObject().getTranslateX() >= 0) {
                        double previousX = cart.getCurrentX();
                        double previousY = cart.getCurrentY();
                        double currentX = cart.getCartObject().getTranslateX();
                        double currentY = cart.getCartObject().getTranslateY();

                        double deltaX = currentX - previousX;
                        double deltaY = currentY - previousY;

                        cart.incrementDistance(deltaX);
                        cart.incrementDistance(deltaY);
                        System.out.println(cart.getDistance());

                        // Update the current position in the cart
                        cart.setCurrentX(currentX);
                        cart.setCurrentY(currentY);
                        System.out.println(cart.getDistance());
                    }
                    if (cart.getLoadPercent() >= 1) {
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
                        playerLives.setText(String.valueOf(goldMine.getHealth()));
                        updatePlayerLives();
                        if (goldMine.getHealth() <= 0) {
                            stopRound(false);
                        }
                    }
                    if (cartNumber <= 0 && !(goldMine.getHealth() <= 0)) {
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
        // Load the stage and game track
        this.primaryStage = primaryStage;
        String levelPath = "src/main/resources/levelCSV/Level1/Level1Concept_Track.csv";
        String levelDecor = "src/main/resources/levelCSV/Level1/Level1Concept_Decorations.csv";

        //Initialize shop and player currency
        shop = new Shop();
        playerCoins.setText(String.valueOf(coinBalance));
        difficulty = "Normal";

        // Round initialization
        roundButton.setText(String.valueOf("Start First Round!"));
        levelGrid = new LevelLoader(trackDefault, levelPath, levelDecor);
        path = new PathLoader("src/main/resources/levelCSV/Level1/Level1CartPath", "src/main/resources/levelCSV/Level1/Level1RotatePath");

        //move this to own class
        goldMine = new GoldMine(trackDefault, 2);
    }


    private void updatePlayerLives() {
        /**
         * Method to update the playerLives
         * @author Michelle Lee
         */
        playerLives.setText(String.valueOf(goldMine.getHealth()));
    }


    @FXML
    public void towerUpgrades(ActionEvent actionEvent) {
        /**
         * Makes Upgrades Shop Panel visible for the user and updates the instruction label to provide guidance to user.
         * @author Michelle Lee
         */
        generalShop.setVisible(false);
        upgradeShop.setVisible(true);
        instructionLabel.setText("Select your upgrade and then the tower you would like to apply the upgrade to!");
    }

    @FXML
    public void generalShop(ActionEvent actionEvent) {
        /**
         *Makes Shop Panel visible for the user and updates the instruction label to provide guidance to user.
         * @author Michelle Lee
         */
        upgradeShop.setVisible(false);
        generalShop.setVisible(true);
        instructionLabel.setText("Buy Towers and Boosters!");
    }

    @FXML
    public void switchInventory(ActionEvent actionEvent) {
        /**
         * When button is clicked, it will switch Main -> Reserve and Reserve -> Main
         * Will check if the inventory is full first or not.
         * @author Michelle Lee
         */
        if (selectedTower != null) {
            Tower tower = towersMap.get(selectedTower);
            if (tower != null) {
                // if tower state is active and reserve inventory is not full, change to inactive
                if (tower.getTowerState()) {
                    if (reserveTowers.size() < 3) {
                        tower.setTowerState(false);
                        tower.setInventoryLocation("Reserve");
                        selectedTower.setOpacity(0.5);
                        mainTowers.remove(tower);
                        reserveTowers.add(tower);
                        instructionLabel.setText("Tower moved to Reserve Inventory");
                    } else {
                        instructionLabel.setText("Reserve Inventory is full");
                    }
                } else { // if tower state is inactive and main inventory is not full, change state to active
                    if (mainTowers.size() < 5) {
                        tower.setTowerState(true);
                        tower.setInventoryLocation("Main");
                        selectedTower.setOpacity(1.0);
                        reserveTowers.remove(tower);
                        mainTowers.add(tower);
                        instructionLabel.setText("Tower moved to Main Inventory");
                    } else {
                        instructionLabel.setText("Main Inventory is full, you will need to sell a tower to move into the Reserve Inventory");
                    }
                }
            } updateTowerStats(tower);
        }
    }

    public void buyTower(MouseEvent event) {
        /**
         * Checks if we currently have 8 towers including reserve and main inventory, if we have less than 8 towers,
         * prompt the user to input a name for the tower and check the stock level of the selected tower
         * Change mouse cursor to give user a preview of the tower(helper method below)
         *
         * @author Michelle Lee
         */

        Button pressedButton = (Button) event.getSource();

        if (purchasedTowers < 10) {
            String towerType = getTowerTypeFromButton(pressedButton);
            if (towerType != null && shop.getStock(towerType) > 0) {
                selectedTowerType = towerType;
                isPurchaseMode = true;
                setupCursorForTower(getTowerImagePath(towerType));

                // User inputs the tower name
                TextInputDialog dialog = new TextInputDialog("Tower Name");
                dialog.setTitle("Enter Tower Name");
                dialog.setHeaderText("Enter a name for your " + selectedTowerType + " tower:");
                dialog.setContentText("Tower Name:");
                Optional<String> result = dialog.showAndWait();
                // If 'Cancel' is clicked, do not take away from stock and reset the purchase mode
                if (result.isPresent()) {
                    userInputTowerName = result.get(); // Store the entered name
                } else {
                    resetPurchaseMode();
                }
            } else {
                instructionLabel.setText("Sorry! Currently SOLD OUT! Check again next round.");
            }
        }
    }

    private void setupCursorForTower(String imagePath) {
        /**
         * Changes the cursor depending on the button clicked
         * @author Michelle Lee
         */
        ImageView towerImage = new ImageView(imagePath);
        gamePane.setCursor(new ImageCursor(towerImage.getImage()));
    }


    private String getTowerTypeFromButton(Button button) {
        /**
         * returns the fxml:id of the button clicked
         * @author Michelle Lee
         */
        if (button == bronzeTower) {
            return "Bronze";
        } else if (button == silverTower) {
            return "Silver";
        } else if (button == goldTower) {
            return "Gold";
        } else {
            return null;
        }
    }

    private String getTowerImagePath(String towerType) {
        /**
         * Returns the image based on the fxid of the button clicked
         * @author Michelle Lee
         */
        switch (towerType) {
            case "Bronze":
                return "Art/Asset Pack/Factions/Knights/Buildings/Tower/bronzeTower.png";
            case "Silver":
                return "Art/Asset Pack/Factions/Knights/Buildings/Tower/silverTower.png";
            case "Gold":
                return "Art/Asset Pack/Factions/Knights/Buildings/Tower/goldTower.png";
            default:
                return null;
        }
    }

    private Button getButtonForTowerType(String towerType) {
        /**
         * Returns the button that was clicked
         * @author Michelle Lee
         */
        switch (towerType) {
            case "Bronze":
                return bronzeTower;
            case "Silver":
                return silverTower;
            case "Gold":
                return goldTower;
            default:
                return null;
        }
    }

    private void soldOut(Button button, Shop shop, String towerType) {
        /**
         * If item is sold out, update the button image to indicate it has been sold out.
         */
        if (shop.getStock(towerType) <= 0) {
            button.setGraphic(new ImageView("Art/Shop/sold.png"));
        }
    }


    @FXML
    public void paneClick(MouseEvent event) {
        /**
         * Get the coordinates of where the user clicks on the map, check if these coordinates are valid by running
         * them through canPlaceTower method. If valid then place the tower and reset the purchase mode to be able to
         * purchase the next item.
         * @author Michelle Lee
         */
        // If the tower button is clicked once get the coordinates
        if ((isPurchaseMode) && selectedTowerType != null) {
            paneX = event.getX();
            paneY = event.getY();

            // if the coordinate is a valid point then we can place the tower
            if (canPlaceTower(paneX, paneY)) {
                placeTower(paneX, paneY, selectedTowerType);
                resetPurchaseMode();
            } else {
                resetPurchaseMode();
            }
        } else {
            //towerStats.setVisible(false);
            instructionLabel.setText("Please select a tower to place.");

        }
    }

    private void updateTowerStats(Tower tower) {
        towerNameLabel.setText(tower.getName());
        resourceTypeLabel.setText(tower.getResourceType());
        reloadSpeedLabel.setText(String.valueOf(tower.getReloadSpeed()));
        loadAmountLabel.setText(String.valueOf(tower.getLoadAmount()));
        levelLabel.setText(String.valueOf(tower.getTowerLevel()));
        activeLabel.setText(tower.getTowerState() ? "Active" : "Inactive");
        inventoryLocationLabel.setText(tower.getInventoryLocation());
    }
    private boolean canPlaceTower(double x, double y) {
        /**
         * Checks whether the tower is able to be placed on selected tile by passing the values through to an external method
         * if valid returns true
         * @author Michelle Lee
         */
        // If outside GamePane
        if (levelGrid.outsideGamePane(x, y)) {
            instructionLabel.setText("Please place the tower within the game map");
            resetPurchaseMode();
            return false;
        }
        // If invalid tile
        if (levelGrid.invalidCoordChecker(x, y)) {
            instructionLabel.setText("Please do not place the tower ON the track");
            resetPurchaseMode();
            return false;
        } else {
            return true;
        }
    }


    private void placeTower(double x, double y, String towerType) {
        /**
         * Only able to be called if the canPlaceTower returns true
         * Creates initializations of towers based on the button clicked
         * Decreases the stock of the Tower and checks if there are more than 5 towers on the map
         * @author Michelle Lee
         */
        String imagePath = getTowerImagePath(towerType);
        Tower tower = null;

        switch (towerType) {
            case "Bronze":
                tower = new Tower(userInputTowerName, "Bronze", 1000000000, 1.0, 1, 10,
                        100, true, "Main");
                break;
            case "Silver":
                tower = new Tower(userInputTowerName, "Silver", 2.0, 1.5, 1, 25,
                        100, true, "Main");
                break;
            case "Gold":
                tower = new Tower(userInputTowerName, "Gold", 3.0, 2.0, 1, 45,
                        100, true, "Main");
                break;
        }

        if (tower != null) {
            tower.setX(x);
            tower.setY(y);
            tower.draw(x, y, imagePath);
            ImageView towerImage = tower.getImage();
            towerImage.setOnMouseClicked(this::checkTowerStats);

            // If total towers on map (incl reserve) < 9 then place tower
            if (mainTowers.size() < 5) {
                ((Pane) trackDefault.getParent()).getChildren().add(towerImage);
                mainTowers.add(tower);
                towersMap.put(towerImage, tower);
                // Decrease stock and update button graphic increase total tower counter by 1
                shop.decreaseStock(towerType);
                soldOut(getButtonForTowerType(towerType), shop, towerType);
                purchasedTowers++;
                // in the reserve tower
            } else if (reserveTowers.size() < 3) {
                ((Pane) trackDefault.getParent()).getChildren().add(towerImage);
                reserveTowers.add(tower);
                towersMap.put(towerImage, tower);

                towerImage.setOnMouseClicked(this::checkTowerStats);
                towerImage.setOpacity(0.5);
                towerImage.setMouseTransparent(false);

                DropShadow dropShadow = new DropShadow();
                dropShadow.setColor(Color.GREY);
                dropShadow.setRadius(20);
                towerImage.setEffect(dropShadow);

                // Update Inventory Location
                tower.setInventoryLocation("Reserve");
                tower.setTowerState(false);
                // Decrease Stock and update the Instruction Label
                shop.decreaseStock(towerType);
                soldOut(getButtonForTowerType(towerType), shop, towerType);
                instructionLabel.setText("No more active towers allowed, please either sell or move a tower to the Reserve Inventory.");
            } else {
                instructionLabel.setText("No more towers can be bought as your MAIN and RESERVE inventory are both full.");
            }
        }
    }

    @FXML
    private void upgradeShootSpeed(ActionEvent event) {
        if (selectedTower != null && !upgradePurchased) {
            Tower tower = towersMap.get(selectedTower);
            if (tower != null) {
                tower.upgradeReloadSpeed(); // Use the existing method in Tower class to upgrade reload speed
                upgradePurchased = true;
                upgradeshootSpeed.setDisable(true); // Disable the button to prevent multiple purchases in the same round
            }
        }
    }

    @FXML
    private void upgradeRange(ActionEvent event) {
        if (selectedTower != null && !upgradePurchased) {
            Tower tower = towersMap.get(selectedTower);
            if (tower != null) {
                tower.upgradeRange(); // Use the existing method in Tower class to upgrade range
                upgradePurchased = true;
                upgradeRange.setDisable(true); // Disable the button to prevent multiple purchases in the same round
            }
        }
    }

    private void resetPurchaseMode() {
        /**
         * Resets the purchase Mode
         * Helper Method
         * @author Michelle Lee
         */
        isPurchaseMode = false;
        selectedTowerType = null;
        gamePane.setCursor(null);  // Reset cursor to default
    }

    @FXML
    public void sell(ActionEvent actionEvent) {
        /**
         * Allows deletion of the tower
         * @author Michelle Lee
         */
        if (selectedTower != null) {
            ((Pane) trackDefault.getParent()).getChildren().remove(radiusCircle);
            // Remove the selected tower from the gamePane
            gamePane.getChildren().remove(selectedTower);

            Tower tower = towersMap.get(selectedTower);

            // Checks if it is a Main or Reserve tower and removes it from respective Array
            if (mainTowers.contains(tower)) {
                mainTowers.remove(tower);
            } else if (reserveTowers.contains(tower)) {
                reserveTowers.remove(tower);
            }

            towersMap.remove(selectedTower);
            selectedTower = null;
            gamePane.setCursor(null);
            isSellMode = false;
            towerStats.setVisible(false);
        }
    }


    @FXML
    private void checkTowerStats(MouseEvent event) {
        /**
         * When a tower is clicked, make the Tower Stats GUI visible and show the stats of the tower clicked
         * @author Michelle Lee
         */

        ImageView newSelectedTower = (ImageView) event.getSource();

        // Check if a tower was previously selected
        if (selectedTower != null) {
            ((Pane) trackDefault.getParent()).getChildren().remove(radiusCircle);
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

        Tower tower = towersMap.get(newSelectedTower);

        if (tower != null) {
            // Setter Methods
            towerNameLabel.setText(tower.getName());
            resourceTypeLabel.setText(tower.getResourceType());
            reloadSpeedLabel.setText(String.valueOf(tower.getReloadSpeed()));
            loadAmountLabel.setText(String.valueOf(tower.getLoadAmount()));
            levelLabel.setText(String.valueOf(tower.getTowerLevel()));
            activeLabel.setText(tower.getTowerState() ? "Active" : "Inactive");
            inventoryLocationLabel.setText(tower.getInventoryLocation());

            // RANGE?
            radiusCircle = new Circle(tower.getX(), tower.getY(), tower.getRadius());
            radiusCircle.setFill(null);
            radiusCircle.setStroke(Color.RED);
            ((Pane) trackDefault.getParent()).getChildren().add(radiusCircle);

        }
        String imagePath = getTowerImagePath(tower.getResourceType());
        selectedTowerImage.setImage(new Image(imagePath));

        System.out.println("Tower clicked: " + tower.getName());
        System.out.println("Tower Stats Pane visible: " + towerStats.isVisible());
    }


    public void makeInactive(boolean b) {
        /**
         * Makes the tower inactive (hence grescalyed)
         * Can't shoot and make radius 0
         * @author Michelle Lee
         */
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
        }
            shop.randomizeStock(); // Resets stock and randomizes it
        }

    }


    private void launchRound(){
        /**
         *
         * @author Gordom Homewood
         */
        roundButton.setDisable(true);
        roundNumber++;
        collisionTimer.start();
        roundState = true;
        updatePlayerLives();

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
        /**
         *
         * @author Gordon Homewood
         */
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
        /**
         *
         * @author Gordon Homewood
         */
        collisionTimer.stop();
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
        /**
         *
         * @author Gordon Homewood
         */
        ImageView image = new ImageView("Art/Asset Pack/Factions/Knights/Troops/Dead/Dead.png");
        image.setX(250);
        image.setY(250);

        ((Pane) trackDefault.getParent()).getChildren().add(image);

        //Spawn New Button that resets Quits to mainScreen or reloads game controller or just switch
        //this whole thing to new screen
    }


    @FXML
    private void quitGame(ActionEvent actionEvent) {
        /**
         * A Method that allows the user to quit the application upon clicking the quit button
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

}
    // Add other methods and properties as needed


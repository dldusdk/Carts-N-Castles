package seng201.team0.gui.gameGUI;

//JavaFX imports for display
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.ImageCursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

//Package imports
import seng201.team0.models.Shop;
import seng201.team0.models.towers.Tower;
import seng201.team0.models.carts.Cart;
import seng201.team0.models.towers.GoldMine;
import seng201.team0.services.animation.GameEventHandler;
import seng201.team0.services.gameLoaders.LevelLoader;
import seng201.team0.services.gameLoaders.LoadRound;
import seng201.team0.services.gameLoaders.PathLoader;
import seng201.team0.services.gameLoaders.RandomEvent;

//Imports for file reading
import java.io.IOException;
import java.io.InputStream;
import java.util.*;


/**
 * This class contains the logic of the game's Graphical User Interface
 * interaction,and is the initializer for the majority
 * of the game's animations, level loading, and round logic.
 *
 * <p>As Carts and Castles uses javaFX to animate and
 * display its game logic with a heavy emphasis on the visuals,
 * this controller is much more complex than others in the program.
 * Outsourcing logic is used where possible and practical, but because of
 * the nature of merging animations, collisions, game logic and player
 * interactions, a more modular design can sometimes seem impractical. It
 * also is the Controller for the FXML gameScreen file, which
 * provides key compatibility between the on screen GUI and the game logic.</p>
 *
 *
 * @author Gordon Homewood
 * @author Michelle Lee
 */


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
    @FXML
    private Button switchInventory;
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
    private Button upgradeSpeed;
    @FXML
    private Button upgradeRange;
    @FXML
    private Button upgradeFill;
    @FXML
    private Label bronzeStockLabel;
    @FXML
    private Label silverStockLabel;
    @FXML
    private Label goldStockLabel;
    @FXML
    private Label sellLabel;
    @FXML
    private Label pointsLabel;

    // GUI variables
    private Stage stage;
    private Stage primaryStage;
    private final String musicPath = "/Music/bg/gameBGM.mp3";
    private int totalCoins;

    // Tower variables
    private final ArrayList<Tower> mainTowers = new ArrayList<>();
    private final ArrayList<Tower> reserveTowers = new ArrayList<>();
    private final Map<ImageView, Tower> towersMap = new HashMap<>();
    private boolean isPurchaseMode = false;
    private String selectedTowerType;
    private String userInputTowerName;
    private Circle radiusCircle;
    private boolean gameOverInitiated = false;

    // Shop Variables
    private Shop shop;
    private int coinBalance;
    private int points = 0; // Can't be spent, but used to give player
                            // feedback on their Game


    // Round and Animation Variables
    private int totalRounds; //need to scale this on player choice
    private int roundNumber = 0;
    private boolean roundState = false;
    private String difficulty;

    // Track Variables
    private LevelLoader levelGrid;
    private PathLoader path;
    private ArrayList<Cart> cartList;
    private int cartNumber;

    private GoldMine goldMine;
    private MediaPlayer mediaPlayer;


    private AnimationTimer collisionTimer = new AnimationTimer() {
        /**
         * This method overrides the animationTimer handle method.
         * It calls a separate class, GameEventHandler to handle the logic.
         * This method is internal to handling the interaction between the
         * logic and the graphics, as it updates at around 60 times a second to
         * ensure the game runs smoothly. Only runs in rounds -
         * when game logic is needed.
         *
         * @param timestamp gives the time of each loop in the animation timer
         *
         * @author Gordon Homewood
         */
        @Override
        public void handle(long timestamp) {
            GameEventHandler gameEventHandler = new GameEventHandler(cartList);
            updatePlayerLives();
            if (!cartList.isEmpty()) {
                cartList = gameEventHandler.getCartList();
                gameEventHandler.handleTowerLogic(mainTowers, cartList,
                        timestamp, trackDefault);
                String updateType = gameEventHandler.handleCartLogic(goldMine);
                if (updateType != null) {
                    if (updateType.equals("Damaged")) {
                    cartNumber--;
                    goldMine.decreaseHealth();
                    playerLives.setText(String.valueOf(goldMine.getHealth()));
                    updatePlayerLives();
                    if (goldMine.getHealth() <= 0) {
                        // End game if gold mine destroyed
                        stopRound(false);
                    }
                }
                if (updateType.equals("RoundWon")) {
                    /* End round if gold mine still standing and all cart
                    destroyed */
                    collisionTimer.stop();
                    stopRound(true);
                }
                }
            }   else {
                //Fail-safe to stop round
                stopRound(true);
            }
        }
    };

    /**
     * Initialize the controller for the Game Window FXML.
     * Shop and player currency are initialized here
     * The methods updateStockDisplay, updatePlayercoins, playMusic,
     * showInstructions will run.updateStock display will refresh the
     * shop tower's label to show the correct stock
     * updatePlayerCoins will update the amount of coins the player has
     * playMusic will initialize and play the background music
     * showInstructions will display a pop-up information box dialogue to
     * give instructions to the user before the game runs
     * @param primaryStage The main stage which the game screen will run on
     * @author Michelle Lee
     */
    public void init(Stage primaryStage) {
        // Load the stage and game track
        this.primaryStage = primaryStage;
        //Initialize shop and player currency
        shop = new Shop();
        coinBalance = 200;
        updateStockDisplay();
        updatePlayerCoins();
        difficulty = "Normal";
        switchInventory.setDisable(false);
        // Game initialization
        playerLives.setText("");
        Font font = Font.font("Minecraft", 12);
        pointsLabel.setFont(font);
        roundButton.setText("Start First Round!");

        InputStream levelPath = getClass().getResourceAsStream("/levelCSV/Level1Concept_Track.csv") ;
        InputStream levelDecor = getClass().getResourceAsStream("/levelCSV/Level1Concept_Decorations.csv") ;
        InputStream cartPath = getClass().getResourceAsStream("/levelCSV/Level1CartPath") ;
        InputStream rotatePath = getClass().getResourceAsStream("/levelCSV/Level1RotatePath") ;

        levelGrid = new LevelLoader(trackDefault, levelPath, levelDecor);
        path = new PathLoader(cartPath, rotatePath);
        playMusic(musicPath);
        // Creates gold mine for visual display of lives
        goldMine = new GoldMine(trackDefault, 2);
        // Show instructions:
        showInstructionDialog();
        totalRounds = getRound();
    }

    /**
     * Before the main Game Screen is launched, an information
     * dialogue will pop up and show the dialogue box to the user
     * giving instructions on how the game is played. It will
     * wait for them to click 'OK' and then the gameScreen will start
     * and allow the user to play the game.
     * player
     * @author Michelle Lee
     */
    private void showInstructionDialog() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game Instructions");
        alert.setHeaderText("Welcome to Carts N' Castles");
        alert.setContentText(" Read below for instructions on how to win (or lose...) the game! \n\n" +
                "1. Use the 'Buy Tower' buttons to purchase towers with different stats (and prices!) to start off!\n" +
                "2. Click anywhere on the game map to place the towers! \n Note:You cannot place outside of the game screen or anywhere that is not a grass tile!\n" +
                "3. Once a tower is placed, you can click on it for numerous options!\n"  +
                "3.a Selling: You can sell the tower at a depreciated cost, you better sell quick to get your money's worth!\n" +
                "3.b Upgrading: You can click on the Upgrade Button to view available upgrades and purchase any upgrades for your tower!\n" +
                "3.c Switching Inventory: If you don't want a tower to be in your Main Inventory anymore, you can click the Switch Inventory button and the tower will be deactivated.\n" +
                "   You can also switch back to the Main Inventory from the Reserve\n\n" +
                "4. Click the 'Start Round' button and choose your difficulty to begin a round.\n" +
                "5. Defend your Gold Mine from the exploding carts.\n"+
                "6. Your score will be determined by how many rounds you complete and the number of coins and points earned!\n" +
                "7. Random events can occur at any round...\n" +
                "8. To win, ensure you fill up all carts and lose as little lives as possible and reach the end of all rounds!\n" +
                "Good luck have fun!");
        alert.showAndWait();
    }

    /**
     * Creates a dialog box and gets the user's choice of
     * total rounds to play and sets that number as the max round number.
     * @return total round number
     * @author Michelle Lee
     */
    public int getRound() {
        int roundNumberChosen = 0;
        boolean validRoundEntered = false;

        while (!validRoundEntered) {
            TextInputDialog roundDialog = new TextInputDialog();
            roundDialog.setTitle("Start new game");
            roundDialog.setHeaderText("Please enter the number of rounds you would like to play!");
            roundDialog.setContentText("Note: It must be a number between 5-15 Rounds");

            Optional<String> result = roundDialog.showAndWait();

            if (result.isPresent()) {
                try {
                    roundNumberChosen = Integer.parseInt(result.get());

                    if (roundNumberChosen >= 5 && roundNumberChosen <= 15) {
                        validRoundEntered = true;
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Invalid Input");
                        alert.setHeaderText("Please enter a number BETWEEN 5-15 Rounds");
                        alert.setContentText(null);
                        alert.showAndWait();
                    }
                } catch (NumberFormatException e) {
                    // if no value entered
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Invalid Input");
                    alert.setHeaderText("Please enter a valid number BETWEEN 5-15 Rounds");
                    alert.setContentText(null);
                    alert.showAndWait();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Input");
                alert.setHeaderText("Please enter a valid number BETWEEN 5-15 Rounds");
                alert.setContentText(null);
                alert.showAndWait();
            }
        }

        return roundNumberChosen;
    }

    /**
     * When this method is called, a String (path of the song) must be passed into the method.
     * The mediaPlayer import is initialized here and requires the path in order to play the music appropriately
     * As the musicPath variable is set when initializing this Controller, it will always pass in "src/main/resources/Music/bg/gameBGM.mp3";
     * This method will automatically play the music 1000 times before it stops.
     * @param musicPath path to the music file
     * @author Michelle Lee
     */
    public void playMusic(String musicPath) {
        Media media = new Media(Objects.requireNonNull(getClass().getResource(musicPath)).toExternalForm());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
        mediaPlayer.setCycleCount(1000);
    }

    /**
     * When this method is called, it will update the GUI (playerCoins Label) on the Game Screen to display
     * the correct number of coins the player has.
     * @author Michelle Lee
     */
    private void updatePlayerCoins() {
        playerCoins.setText(String.valueOf(coinBalance));
    }

    /**
     * When this method is called, it will update the GUI (playerLives Label) on the Game screen to display
     * correct live(s) amount
     * @author Michelle Lee
     */
    private void updatePlayerLives() {
        playerLives.setText(String.valueOf(goldMine.getHealth()));
    }

    /**
     * When this method is called, it will update the GUI (bronze,silver, gold Stock Labels) on the gameScreen and display
     * the correct amount of stock available
     * @author Michelle Lee
     */
    private void updateStockDisplay() {
        bronzeStockLabel.setText("Stock: " + shop.getStock("Bronze"));
        silverStockLabel.setText("Stock: " + shop.getStock("Silver"));
        goldStockLabel.setText("Stock: " + shop.getStock("Gold"));
    }

    /**
     * When this method is called (from clicking button action event), it will change the GUI and hide the upgrade shop and
     * make the general shop visible.
     * @param actionEvent When the button labelled 'SHOP' is clicked, it will run the generalShop method
     * @author Michelle Lee
     */
    @FXML
    public void generalShop(ActionEvent actionEvent) {
        upgradeShop.setVisible(false);
        generalShop.setVisible(true);
        instructionLabel.setText("Buy Towers and Boosters!");
    }

    /**
     * When this method is called (from clicking button action event), it will change the GUI and hide the general shop and
     * make the upgrade shop visible
     * @param actionEvent When the button labelled 'Upgrades' is clicked, it will run the towerUpgrades method
     * @author Michelle Lee
     */
    @FXML
    public void towerUpgrades(ActionEvent actionEvent) {
        generalShop.setVisible(false);
        upgradeShop.setVisible(true);
        instructionLabel.setText("Select your upgrade and then the tower you would like to apply the upgrade to!");
    }

    /**
     * When this method is called (from clicking button action event), it will make a new instance of the tower called brokenTower,
     * It checks if the tower is broken by calling .getDestroyed() and checks the user has enough coins. If they have enough coins
     * and the tower is destroyed, it will deduct 200 coins and update their coin balance on the GUI.
     * It will then update the image of the tower to show it has been repaired.
     *
     * @param actionEvent When the button labelled 'Repair' is clicked, it will run the repairTower method
     * @author Michelle Lee
     */
    @FXML
    private void repairTower(ActionEvent actionEvent) {
        Tower brokenTower = towersMap.get(selectedTower);
        if (brokenTower.getDestroyed() && coinBalance >= 200) {
            coinBalance -= 200;
            updatePlayerCoins();
            // set image, radius to normal // how to distinguish which tower it is
            String towerType = brokenTower.getResourceType();
            String imagePath = getTowerImagePath(towerType);
            brokenTower.repairTower(imagePath);
            instructionLabel.setText("Successfully repaired!");
        } else if (coinBalance < 200){
            instructionLabel.setText("You do not have enough coins to repair your tower");
        } else {
            instructionLabel.setText("Your selected tower is not broken!");
        }
    }

    /**
     * When this method is called (from clicking button action event), it will ensure a round is currently not commenced.
     * If not, then it will check if the tower is active, check the Reserve Inventory Capacity.
     * If active and reserve is not full, then it will change the towers state to inactive, remove it from the mainTower array and add it to the reserveTower array
     * and make the tower low opacity.
     * For inactive towers, the opposite will happen where it will check the Main Inventory Capacity and check if it is inactive,
     * as long as those conditions are true, it will remove the tower from the reservedTowers array, add it to the mainTowers array
     * and make the tower have full opacity.
     *
     * @param actionEvent When the button labelled 'Switch Inventory' is clicked, it will run the switchInventory method
     * @author Michelle Lee
     */
    @FXML
    public void switchInventory(ActionEvent actionEvent) {
        if (roundState) {
            instructionLabel.setText("Cannot switch inventory during a round.");
            return;
        }
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
            }
            assert tower != null;
            updateTowerStats(tower);
        }
    }

    /**
     * Stores the event of the pressed Button into variable called 'pressedButton' from that Button get a String of the
     * tower type the user has clicked. Depending on the case from the String, get different tower costs.
     * Checks if the user has enough money to buy the tower, if so then get the user to input a name for the tower.
     * If the name is entered and the user clicks 'OK' then deduct the appropriate amount.
     * If the user clicks 'Cancel' from the dialog box, then does not deduct money from the user's balance.
     *
     * @param event gets mouse click
     * @author Michelle Lee
     */
    public void buyTower(MouseEvent event) {
        // Store the button clicked fx:id
        Button pressedButton = (Button) event.getSource();
        int towerCost = 0;
        String towerType = getTowerTypeFromButton(pressedButton);
        // Depending on the Button clicked
        if (towerType != null && shop.getStock(towerType) > 0) {
            switch (towerType) {
                case "Bronze":
                    towerCost = shop.getBronzeTowerCost();
                    break;
                case "Silver":
                    towerCost = shop.getSilverTowerCost();
                    break;
                case "Gold":
                    towerCost = shop.getGoldTowerCost();
                    break;
            }
            // Checks if the User has enough money to purchse the tower
            if (coinBalance >= towerCost) {
                selectedTowerType = towerType;
                isPurchaseMode = true;
                setupCursorForTower(getTowerImagePath(towerType));

                // Dialogue Box to get User input for the tower name
                TextInputDialog dialog = new TextInputDialog("Tower Name");
                dialog.setTitle("Enter Tower Name");
                dialog.setHeaderText("Enter a name for your " + selectedTowerType + " tower:");
                dialog.setContentText("Tower Name:");
                Optional<String> result = dialog.showAndWait();
                updateStockDisplay();
                // If Name entered, deduct the appropriate coin amount and update coin display
                if (result.isPresent()) {
                    userInputTowerName = result.get(); // Store the entered name
                    coinBalance -= towerCost;
                    updatePlayerCoins();
                } else { // If 'Cancel' is clicked, do not take away from stock and reset the purchase mode
                    resetPurchaseMode();
                }
            } else {
                instructionLabel.setText("You don't have enough coins to buy this tower. Try again next round!");
            }
        }
    }
    /**
     * Stores the event of the presesdButton into variable called 'pressedButton' from that Button get a String of the
     * upgrade type the user has clicked. Depending on the case from the String, get different upgrae costs.
     * Checks if the user has enough money to buy the upgrade, then deduct the appropriate amount.
     * If the user clicks has not enough money, the instruction Label will change to inform the user.
     *
     * @param event When the
     * @author Michelle Lee
     */
    public void buyUpgrade(MouseEvent event) {
        // Store the clicked Button's fx:id
        Button pressedButton = (Button) event.getSource();
        int upgradeCost = 0;
        // As long as a tower is selected, store the tower in tower instance and get which upgrade the user Selected
        if (selectedTower != null) {
            Tower tower = towersMap.get(selectedTower);
            String upgradeType = getUpgradeType(pressedButton);
            // Depending on the String returned from upgradeType
            if (upgradeType != null) {
                switch (upgradeType) {
                    case "Upgrade Speed":
                        upgradeCost = shop.getSpeedUpgradeCost();
                        break;
                    case "Upgrade Range":
                        upgradeCost = shop.getRangeUpgradeCost();
                        break;
                    case "Upgrade Fill":
                        upgradeCost = shop.getFillingUpgradeCost();
                        break;
                    default:
                        break;
                }
                // If the player has enough money, take away the coins and then upgrade the tower
                if (coinBalance >= upgradeCost) {
                    coinBalance -= upgradeCost;
                    updatePlayerCoins();
                    // Apply the upgrade by calling applyUpgradeToTower method
                    applyUpgradeToTower(tower, upgradeType);
                    updateTowerStats(tower);
                    // No money for the upgrade!
                } else {
                    instructionLabel.setText("You don't have enough coins for this upgrade. Try again next round!");
                }
            }
        } else {
            instructionLabel.setText("You have no tower selected!");
        }
    }

    /**
     * Depending on the String passed from buyUpgrade, this method will call different methods in the Tower Class
     * @param tower The selected tower getting the upgrade
     * @param upgradeType The upgrade the user has clicked
     */
    private void applyUpgradeToTower(Tower tower, String upgradeType) {
        switch (upgradeType) {
            case "Upgrade Speed":
                tower.upgradeSpeed();
                updateTowerStats(tower);
                break;
            case "Upgrade Range":
                tower.upgradeRange();
                updateTowerStats(tower);
                break;
            case "Upgrade Fill":
                tower.upgradeFill();
                updateTowerStats(tower);
                break;
        }
    }

    /**
     * Depending on the tower clicked, it will set the cursor to carry the image of the tower when the tower button is clicked
     * @param imagePath contains a string of the image's path
     */
    private void setupCursorForTower(String imagePath) {
        ImageView towerImage = new ImageView(imagePath);
        gamePane.setCursor(new ImageCursor(towerImage.getImage()));
    }

    /**
     * Depending on button clicked, it will return a String corresponding to that button which will be passed through to buyUpgrade method
     * @param button gets the source (fx:id) from the Button clicked
     * @return String version of the upgrade clicked.
     */
    private String getUpgradeType(Button button) {
        if (button == upgradeSpeed) {
            return "Upgrade Speed";
        } else if (button == upgradeRange) {
            return "Upgrade Range";
        } else if (button == upgradeFill) {
            return "Upgrade Fill";
        } else {
            return null;
        }
    }

    /**
     * Depending on button clicked, it will return a String corresponding to that button which will be passed through to buyTower method
     * @param button gets the source (fx:id) from the Button clicked
     * @return String version of the tower clicked
     */
    private String getTowerTypeFromButton(Button button) {
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

    /**
     * This method returns the path of the image as a string depending on the Tower Button the user initially clicked
     * @param towerType passes in the string version of the selected tower
     * @return the image path of the tower type as a string
     */
    private String getTowerImagePath(String towerType) {
        switch (towerType) {
            case "Bronze":
                return "Art/Factions/Knights/Buildings/Tower/bronzeTower.png";
            case "Silver":
                return "Art/Factions/Knights/Buildings/Tower/silverTower.png";
            case "Gold":
                return "Art/Factions/Knights/Buildings/Tower/goldTower.png";
            default:
                return null;
        }
    }

    /**
     *This method returns the fx:id depending on the String passed into the method
     * @param towerType String version of the towerType the user selected
     * @return the fx:id value from the String passed in
     */
    private Button getButtonForTowerType(String towerType) {
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

    /**
     * Depending on the variables entered, this method will change the image of the button to a sold image when the stock
     * of the selected towerType is equal or less than 0
     * @param button The button clicked source
     * @param shop Instance of the Shop Class
     * @param towerType The towerType retrieved from the button clicked
     *
     * @author Michelle Lee
     */
    private void soldOut(Button button, Shop shop, String towerType) {
        if (shop.getStock(towerType) <= 0) {
            button.setGraphic(new ImageView("Art/Shop/sold.png"));
        }
    }

    /**
     * Get the coordinates of where the user clicks on the map, check if these coordinates are valid by running
     * them through canPlaceTower method. If valid then place the tower and reset the purchase mode to be able to
     * purchase the next item.
     * @author Michelle Lee
     */

    @FXML
    public void paneClick(MouseEvent event) {
        // If the tower button is clicked once get the coordinates
        if ((isPurchaseMode) && selectedTowerType != null) {
            double paneX = event.getX();
            double paneY = event.getY();
            // if the coordinate is a valid point then we can place the tower
            if (canPlaceTower(paneX, paneY)) {
                placeTower(paneX, paneY, selectedTowerType);
                resetPurchaseMode();
            } else {
                resetPurchaseMode();
            }
        } else {
            instructionLabel.setText("Please select a tower to place.");
        }
    }

    /**
     * This method updates the GUI (Labels) associated with the Tower to ensure all details of the tower is correct
     * @param tower fx:id of the selected tower
     * @author Michelle Lee
     */
    private void updateTowerStats(Tower tower) {
        towerNameLabel.setText(tower.getName());
        resourceTypeLabel.setText(tower.getResourceType());
        reloadSpeedLabel.setText(String.valueOf(tower.getReloadSpeed()));
        loadAmountLabel.setText(String.valueOf(tower.getLoadAmount()));
        levelLabel.setText(String.valueOf(tower.getTowerLevel()));
        activeLabel.setText(tower.getTowerState() ? "Active" : "Inactive");
        inventoryLocationLabel.setText(tower.getInventoryLocation());
    }

    /**
     * Checks whether the tower is able to be placed on selected tile by passing the values through to outsideGamePane and
     * invalidCoordChecker methods in LevelLoader class, if these methods return true, then the tower is placed in an invalid
     * spot and will update the label to advise the user to Place the tower within the game map.
     * @param x x coordinate of where the user has clicked
     * @param y y coordinate of where the user has clicked
     * @author Michelle Lee
     */
    private boolean canPlaceTower(double x, double y) {

        // If outside GamePane returns true
        if (levelGrid.outsideGamePane(x, y)) {
            instructionLabel.setText("Please place the tower within the game map");
            resetPurchaseMode();
            return false;
        }
        // If invalid tile returns true
        if (levelGrid.invalidCoordChecker(x, y)) {
            instructionLabel.setText("Please do not place the tower ON the track");
            resetPurchaseMode();
            return false;
            // if valid spot
        } else {
            return true;
        }
    }
    /**
     * Only able to be called if the canPlaceTower returns true
     * Creates initializations of towers based on the button clicked
     * Decreases the stock of the Tower and checks if there are more than 5 towers on the map
     * @param x x coordinate of where the user has clicked
     * @param y y coordinate of where the user has clicked
     * @param towerType Checks whether the tower is a bronze, silver or gold tower
     * @author Michelle Lee
     */
    private void placeTower(double x, double y, String towerType) {

        String imagePath = getTowerImagePath(towerType);
        Tower tower = null;

        // Create instance if the toewr in Tower.java
        switch (towerType) {
            case "Bronze":
                tower = new Tower(userInputTowerName, "Bronze", 2.0, 0.25, 1, 10,
                        130, true, "Main");
                break;
            case "Silver":
                tower = new Tower(userInputTowerName, "Silver", 1.5, 0.75, 1, 25,
                        130, true, "Main");
                break;
            case "Gold":
                tower = new Tower(userInputTowerName, "Gold", 1.0, 1.5, 1, 45,
                        130, true, "Main");
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
                updateStockDisplay();
                // If reserveTower is not full
            } else if (reserveTowers.size() < 3) {
                ((Pane) trackDefault.getParent()).getChildren().add(towerImage);
                reserveTowers.add(tower);
                towersMap.put(towerImage, tower);
                // Change the images opacity
                towerImage.setOnMouseClicked(this::checkTowerStats);
                towerImage.setOpacity(0.5);
                towerImage.setMouseTransparent(false);
                // Create a grey dropshadow on the tower
                DropShadow dropShadow = new DropShadow();
                dropShadow.setColor(Color.GREY);
                dropShadow.setRadius(20);
                towerImage.setEffect(dropShadow);
                // Update Inventory Location
                tower.setInventoryLocation("Reserve");
                tower.setTowerState(false);
                // Decrease Stock and update the Instruction Label
                updateStockDisplay();
                shop.decreaseStock(towerType);
                soldOut(getButtonForTowerType(towerType), shop, towerType);
                instructionLabel.setText("No more active towers allowed, please either sell or move a tower to the Reserve Inventory.");
            } else {
                instructionLabel.setText("No more towers can be bought as your MAIN and RESERVE inventory are both full.");
            }
        }
    }

    /**
     * Resets the purchase Mode to allow cancellations of purchases
     * Helper Method
     * @author Michelle Lee
     */
    private void resetPurchaseMode() {
        isPurchaseMode = false;
        selectedTowerType = null;
        gamePane.setCursor(null);  // Reset cursor to default
    }

    /**
     * When the Sell button is clicked, and we have a tower selected, it will sell the tower and add money to the users coin balance
     * It will remove the selectedTower from its respective array (main or reserve) and remove the tower's image from the game map
     * @param actionEvent When the 'Sell' Button is clicked this method is run
     * @author Michelle Lee
     */
    @FXML
    public void sell(ActionEvent actionEvent) {
        if (selectedTower != null) {
            // Refund the tower sell value to the player
            Tower tower = towersMap.get(selectedTower);
            if (tower != null) {
                String towerType = tower.getResourceType();
                int refund = shop.getSellValue(towerType, roundNumber);
                coinBalance += refund;
                updatePlayerCoins();
                // Remove the selected tower from the gamePane
                ((Pane) trackDefault.getParent()).getChildren().remove(radiusCircle);
                gamePane.getChildren().remove(selectedTower);
                // Checks if it is a Main or Reserve tower and removes it from respective Array
                if (mainTowers.contains(tower)) {
                    mainTowers.remove(tower);
                } else if (reserveTowers.contains(tower)) {
                    reserveTowers.remove(tower);
                }
                towersMap.remove(selectedTower);
                selectedTower = null;
                gamePane.setCursor(null);
                towerStats.setVisible(false);
            }
        }
    }
    /**
     * When a tower is clicked, this method is called and will make the Tower Stats GUI visible.
     * the Tower Stats GUI will show the stats of the tower selected only.
     * @param event passes through the tower that was clicked before the checkTowerStats method was called
     * @author Michelle Lee
     */
    @FXML
    private void checkTowerStats(MouseEvent event) {
        ImageView newSelectedTower = (ImageView) event.getSource();
        // Check if a tower is selected
        if (selectedTower != null) {
            ((Pane) trackDefault.getParent()).getChildren().remove(radiusCircle);
            Tower checkTower = towersMap.get(selectedTower);
            // Remove shadow effect from the previously selected tower
            selectedTower.setEffect(null);
            if (checkTower.getBuffState()) {
                //Keeps highlight on if buffed by random event
                checkTower.applyBuffHighlight(true);
            }
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
            // Setter Methods for the GUI of the Tower's Stats
            towerNameLabel.setText(tower.getName());
            resourceTypeLabel.setText(tower.getResourceType());
            reloadSpeedLabel.setText(String.valueOf(tower.getReloadSpeed()));
            loadAmountLabel.setText(String.valueOf(tower.getLoadAmount()));
            levelLabel.setText(String.valueOf(tower.getTowerLevel()));
            activeLabel.setFont(Font.font("Minecraft"));
            activeLabel.setText(tower.getTowerState() ? "Active" : "Inactive");
            inventoryLocationLabel.setFont(Font.font("Minecraft"));
            inventoryLocationLabel.setText(tower.getInventoryLocation());
            //Get and display sell value
            String towerType = tower.getResourceType();
            int sellPrice = shop.getSellValue(towerType, roundNumber);
            sellLabel.setText(String.valueOf(sellPrice));
            // RANGE
            radiusCircle = new Circle(tower.getX(), tower.getY(), tower.getRadius());
            radiusCircle.setFill(null);
            radiusCircle.setStroke(Color.RED);
            ((Pane) trackDefault.getParent()).getChildren().add(radiusCircle);
        }
        assert tower != null;
        String imagePath = getTowerImagePath(tower.getResourceType());
        assert imagePath != null;
        selectedTowerImage.setImage(new Image(imagePath));
    }

    /**
     * Prompts user to select desired difficulty when the round starts. Launches round based on
     * difficulty.
     * @author Gordon Homewood
     */
    @FXML
    public void roundButtonClicked(ActionEvent event) {
        Dialog<ButtonType> userNameDialog = new Dialog<>();
        userNameDialog.setTitle("Choose Round Difficulty");
        int nextRound = roundNumber + 1;
        userNameDialog.setHeaderText("Round " + nextRound + " is about to start.");

        // Displays stats for the user to make a decision
        userNameDialog.setContentText("""
                Select difficulty:

                Easy Difficulty:
                   5 Lives
                   25% chance for tower destroyed
                   75% chance for tower buff
                   50% money awarded, 50% points

                Normal Difficulty:
                   3 Lives
                   10% chance for tower destroyed
                   50% money awarded, 75% points

                Hard Difficulty:
                   1 Life
                   75% chance for tower destroyed
                   25% chance for tower buff
                   100% money awarded, 100% points
                """);
        // Initialize buttons for difficulty popup
        ButtonType easyButton = new ButtonType("Easy", ButtonBar.ButtonData.OK_DONE);
        ButtonType mediumButton = new ButtonType("Normal", ButtonBar.ButtonData.OK_DONE);
        ButtonType hardButton = new ButtonType("Hard", ButtonBar.ButtonData.OK_DONE);
        ButtonType quitButton = new ButtonType("I'm Not Ready!", ButtonBar.ButtonData.CANCEL_CLOSE);

        userNameDialog.setOnShowing(event1 -> {
            // Sets default button to quitButton, purely for aesthetics, so this appears as default, "blue color"
            Button button1 = (Button) userNameDialog.getDialogPane().lookupButton(easyButton);
            button1.setDefaultButton(false);
            Button button2 = (Button) userNameDialog.getDialogPane().lookupButton(quitButton);
            button2.setDefaultButton(true);
        });

        // Tracks user input
        userNameDialog.getDialogPane().getButtonTypes().addAll(easyButton, mediumButton, hardButton, quitButton);
        Optional<ButtonType> result = userNameDialog.showAndWait();

        //Gets difficulty from user button input, launches round
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

        }
        switchInventory.setDisable(false); // Disable switching towers between inventories until the end of the round
    }

    /**
     *When difficulty is selected, this method is called and the round in launched. This method is mainly
     * responsible for initializing the round, calling methods and other classes to increment rounds, update lives,
     * run random events and establishing general logic.
     *
     * @author Gordon Homewood
     */

    private void launchRound() {

        // Initializing variables for new round
        instructionLabel.setText("Don't let the carts destroy your goldmine!");
        roundButton.setDisable(true);
        roundNumber++;
        collisionTimer.start();
        roundState = true;
        updatePlayerLives();

        //Generate random events
        runRandomEvents();

        ArrayList<Integer> cartTypeList = getCartNumber();
        LoadRound newRound = new LoadRound(cartDefault, path, cartTypeList);
        for (Tower tower : mainTowers) {
            // Draws the towers on correct layer and increments list of full rounds tower is used in
            ((Pane) trackDefault.getParent()).getChildren().remove(tower.getImage());
            ((Pane) trackDefault.getParent()).getChildren().add(tower.getImage());
            tower.incrementRound(roundNumber);

        }
        for (int num : cartTypeList) {
            // Stores the total number of carts for round logic
            cartNumber += num;
        }
        cartList = newRound.getCartList();
        roundButton.setText(roundNumber + "/" + totalRounds);
        roundState = true;
        if (switchInventory != null) {
            switchInventory.setDisable(false); //Disables inventory swapping during rounds
        }
    }
    /** Creates a new RandomEvent class and applies the results of the random event
     * to the relevant impacted towers
     * @author Gordon Homewood
     */
    private void runRandomEvents() {
        RandomEvent towerBreaks = new RandomEvent(mainTowers, difficulty,roundNumber);
        Tower brokenTower = towerBreaks.getAffectedTowerBreak();
        if (brokenTower != null) {
            //Change the selected tower to broken if the random event happens
            brokenTower.setDestroyed(true);
        }
        Tower bufftower = towerBreaks.getAffectedTowerBuff();
        if (bufftower != null) {
            if (!bufftower.getDestroyed()) {
                //Buff the selected tower if the random event happens and checks again to make
                //sure it is not destroyed
                bufftower.setBuff(true);
            }
        }
    }
    /**
     * Generates how many carts of each type should be spawned in a round, based on round number. Also
     * sets goldMine to the correct health for the round.
     *
     * @return A list of cart numbers, where index 1 = bronze, index 2 = silver and index 3 = gold carts to
     * be spawned in the round level.
     * @author Gordon Homewood
     */
    private ArrayList<Integer> getCartNumber() {
        //Set health based on difficulty
        if (difficulty.equals("Easy")) {
            goldMine.setHealth(5);
        }
        if (difficulty.equals("Normal")) {
            goldMine.setHealth(3);
        }
        if (difficulty.equals("Hard")) {
            goldMine.setHealth(1);
        }
        //Update goldMine image based on health changing
        goldMine.checkHealth();

        ArrayList<Integer> cartTypeNumbers = new ArrayList<>();
        //Cart generation algorithm for rounds as they progress
        if (roundNumber < 3) {
            cartTypeNumbers.add(roundNumber + 1); //Bronze carts
            cartTypeNumbers.add(0);               //Silver carts
            cartTypeNumbers.add(0);               //Gold carts
            return (cartTypeNumbers);
        }
        if (roundNumber < 6) {
            cartTypeNumbers.add(roundNumber + 1);
            cartTypeNumbers.add(roundNumber / 2 + 1);
            cartTypeNumbers.add(0);
            return (cartTypeNumbers);
        }
        if (roundNumber <= 10) {
            cartTypeNumbers.add(roundNumber + 1);
            cartTypeNumbers.add(roundNumber / 2);
            cartTypeNumbers.add(roundNumber / 4);
            return (cartTypeNumbers);
        }
        //Keep increasing scale of carts beyond round 10
        cartTypeNumbers.add(roundNumber);
        cartTypeNumbers.add(roundNumber / 2);
        cartTypeNumbers.add(roundNumber / 3);
        return (cartTypeNumbers);

    }
    /**
     * Stops the round when called in the collisionTimer. This allows the game to handle
     * updates as the round finishes, such as awarding money, resetting tower buff, stopping
     * collisionTimer and renabling inventory.
     *
     * @param state which decides if the game should continue or not.
     *
     * @author Gordon Homewood
     */
    private void stopRound(boolean state) {
        shop.randomizeStock(); // Resets stock and randomizes it
        updateStockDisplay(); //update stock
        if (roundNumber > totalRounds - 1 && state) {
            // Switch view to win screen if they complete all rounds.
            roundButton.setDisable(true);
            launchEndScreen(true, "/Music/bg/winBGM.mp3" );
            }
        else {
            for (Tower tower : mainTowers) {
                if (tower.getBuffState()) {
                    //Resets buff state after round
                    tower.getImage().setEffect(null);
                    tower.setBuff(false);
                }
            }
            updatePlayerLives();
            instructionLabel.setText("Round " + roundNumber + " complete!");
            collisionTimer.stop();
            if (state) {
                // Awards money and allows next round to start if player is successful, updating money
                // on screen
                roundButton.setDisable(false);
                calculateIncome();
                playerCoins.setText(String.valueOf(coinBalance));
            } else {
                // If the gameState is failed, it will not allow any more rounds to be played.
                roundButton.setDisable(true);
                launchEndScreen(false, "/Music/bg/loseBGM.mp3");
            }
            switchInventory.setDisable(false);  // allows player to switch inventory
        }
    }
    /**
     * Decides how much money should be awarded based on the
     * difficulty of the round.
     * @author Gordon Homewood
     */
    private void calculateIncome() {


        if (difficulty.equals("Easy")) {
            int moneyAwarded = (int) ((roundNumber * 50) * 0.5);
            int roundedAward = (int) (Math.ceil((double) moneyAwarded / 5) * 5);
            points += roundedAward * 25;
            coinBalance += roundedAward;
            pointsLabel.setText(String.valueOf(points));
            totalCoins += roundedAward;
        }
        if (difficulty.equals("Normal")) {
            int moneyAwarded = (int) ((roundNumber * 50) * 0.75);
            int roundedAward = (int) (Math.ceil((double) moneyAwarded / 5) * 5);
            points += roundedAward * 25;
            coinBalance += roundedAward;
            pointsLabel.setText(String.valueOf(points));
            totalCoins += roundedAward;

        }
        if (difficulty.equals("Hard")) {
            int moneyAwarded = (roundNumber * 50);
            int roundedAward = (int) (Math.ceil((double) moneyAwarded / 5) * 5);
            points += roundedAward * 25;
            coinBalance += roundedAward;
            pointsLabel.setText(String.valueOf(points));
            totalCoins += roundedAward;
        }
        updatePlayerCoins();
    }


    /**
     * This method allows the GameEndingController to Run and sets up for the game Ending Controller to run with the correct
     * variables to display.
     * @param won A boolean value advising on whether the user has won (true) or lots (false)
     * @param songPath String of the path of the song
     * @author Michelle Lee
     */
    @SuppressWarnings("checkstyle:LineLength")
    @FXML
    private void launchEndScreen(boolean won, String songPath) {
        if (gameOverInitiated) {
            // If already in game over loop, don't let function cause
            // infinite loop of opening game over screen
            return;
        }
        gameOverInitiated = true;
        instructionLabel.setText("Game Over!");
        mediaPlayer.stop();
        //Clear track and animations
        collisionTimer = null;
        for (Cart cart : cartList) {
            cart.despawn();
        }
        //Display gameover image
        ImageView image = new ImageView("Art/Factions/Knights/Troops/Dead/Dead.png");
        image.setX(250);
        image.setY(250);
        ((Pane) trackDefault.getParent()).getChildren().add(image);
        // Close the game Screen
        stage = (Stage) gamePane.getScene().getWindow();
        stage.close();
        Stage endingScreen = this.primaryStage;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/gameEnd.fxml"));
        Parent gameEndingRoot;
        try {
            gameEndingRoot = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        // Set up the scene for the Game Ending Window
        Scene gameEnding = new Scene(gameEndingRoot);
        endingScreen.setScene(gameEnding);
        endingScreen.setTitle("Game Over!");
        // Pass the appropriate variable sthrough to gameendingController
        GameEndingController gameEndingController = loader.getController();
        gameEndingController.gameStats(won ? "Congratulations!" : "You lose..", totalRounds, roundNumber, totalCoins, points, won ? "Great job!" : "Try again next time!");
        // Play winning or losing song
        gameEndingController.playEndingSong(songPath);
        // Show Game End Stats Screen
        endingScreen.show();
    }

    /**
     * A Method that allows the user to quit the application upon clicking the quit button
     * It makes use of a dialog box to inform the user that they are about to quit
     * @param actionEvent When the 'Quit' Button is clicked this method is called
     * @author Michelle Lee
     */
    @FXML
    private void quitGame(ActionEvent actionEvent) {
        // Have a pop-up appear if the user clicks 'Quit' Button
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Quit");
        alert.setHeaderText("You are about to quit. Please be advised this game DOES NOT support saves! ");
        alert.setContentText("Are you sure you want to quit?");
        // If User clicks 'Confirm', Quit game
        if (alert.showAndWait().get() == ButtonType.OK) {
            stage = (Stage) gamePane.getScene().getWindow();
            stage.close();
        }
    }
}

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

import javafx.stage.Window;
import seng201.team0.models.Shop;
import seng201.team0.models.Tower;
import seng201.team0.models.carts.CartBasic;
import seng201.team0.models.towers.GoldMine;
import seng201.team0.models.towers.Projectile;
import seng201.team0.services.gameLoaders.LevelLoader;
import seng201.team0.services.gameLoaders.LoadRound;
import seng201.team0.services.gameLoaders.PathLoader;

import java.util.*;

public class GameController {

    @FXML
    private ImageView trackDefault;
    @FXML
    private Button roundButton;
    @FXML
    private ImageView cartDefault;
    @FXML
    private ImageView selectedTowerImage;

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
    private Label xpLabel;

    @FXML
    private AnchorPane towerStats;
    @FXML
    private ImageView selectedTower;

    // Keeping track of placedTowers for inventory and selling purposes
    private List<Tower> placedTowers = new ArrayList<>();
    private List<Tower> reserveTowers = new ArrayList<>();
    private Map<ImageView, Tower> towersMap = new HashMap<>();


    // 0 Means not clicked
    private int purchasedTowers = 0;
    private int maxTowersOnmap = 5;
    private int maxTowersPerRound = 10;
    private int availableTowers = 10;
    private double paneX;
    private double paneY;

    private boolean isPurchaseMode = false;
    private boolean isSellMode = false;
    private String selectedTowerType;
    private String userInputTowerName;
    private Circle radiusCircle;

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
    private int coinBalance = 200;
    private GoldMine goldMine;

    private long lastProjectileTime = 0;
    private static final long interval = 500000000L; // Fire every 0.5 seconds (500ms)

    private List<Projectile> projectiles = new ArrayList<>(); // Add this line to keep track of projectiles

    private AnimationTimer collisionTimer = new AnimationTimer() {
        public void handle(long timestamp) {
            //System.out.println(placedTowers);
                if (!cartList.isEmpty()) {
                    for(Tower tower:placedTowers){
                        double fireRate = tower.getFireRate(); //need to implement this
                        CartBasic towerTarget = tower.targetAcquisition(cartList);
                        if (timestamp - tower.getProjectileTime() >= interval && towerTarget.getCartObject().getTranslateX() > 0
                        && tower.getRadius() > tower.getDistance(towerTarget.getCartObject().getTranslateX(),towerTarget.getCartObject().getTranslateY())){
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

                    if(cart.getCartObject().getTranslateX() >= 0){
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
                    if(cart.getLoadPercent() >= 1){
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
                }}

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
         * @authoor Michelle Lee
         */

        Shop shop = new Shop();

        Button pressedButton = (Button) event.getSource();
        if (purchasedTowers <= maxTowersPerRound) {
            purchasedTowers++;

            // if fx:id = bronzeTower
            if (pressedButton == bronzeTower && shop.getStock("Bronze") > 0) {
                selectedTowerType = "Bronze";
                isPurchaseMode = true;
                shop.decreaseStock("Bronze");
                setupCursorForTower("Art/Asset Pack/Factions/Knights/Buildings/Tower/bronzeTower.png");
            } else if (pressedButton == silverTower && shop.getStock("Silver") > 0) {
                selectedTowerType = "Silver";
                isPurchaseMode = true;
                shop.decreaseStock("Silver");
                setupCursorForTower("Art/Asset Pack/Factions/Knights/Buildings/Tower/silverTower.png");
            } else if (pressedButton == goldTower && shop.getStock("Gold") > 0) {
                selectedTowerType = "Gold";
                isPurchaseMode = true;
                shop.decreaseStock("Gold");
                setupCursorForTower("Art/Asset Pack/Factions/Knights/Buildings/Tower/goldTower.png");
            } else {
                instructionLabel.setText("This tower type is sold out!");
                pressedButton.setGraphic(new ImageView("Art/Shop/sold.png"));

            }

            if (selectedTowerType != null) {
                TextInputDialog dialog = new TextInputDialog("Tower Name");
                dialog.setTitle("Enter Tower Name");
                dialog.setHeaderText("Enter a name for your " + selectedTowerType + " tower:");
                dialog.setContentText("Tower Name:");

                Optional<String> result = dialog.showAndWait();
                result.ifPresent(name -> userInputTowerName = name); // Store the entered name
            }

            if (purchasedTowers >= maxTowersPerRound) {
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
        Tower tower = null;

        switch (towerType) {
            case "Bronze":
                imagePath = "Art/Asset Pack/Factions/Knights/Buildings/Tower/bronzeTower.png";
                tower = new Tower(userInputTowerName, "Bronze",
                        1.0, 0.1,1,10,
                        100, 300);
                break;
            case "Silver":
                imagePath = "Art/Asset Pack/Factions/Knights/Buildings/Tower/silverTower.png";
                tower = new Tower(userInputTowerName,
                        "Silver", 2.0,
                        1.5,1,25,
                        100,100);
                break;
            case "Gold":
                imagePath = "Art/Asset Pack/Factions/Knights/Buildings/Tower/goldTower.png";
                tower = new Tower(userInputTowerName,
                        "Gold", 3.0,
                        2.0,1,45,
                        100,100);
                break;
        }

        if (placedTowers.size() < maxTowersOnmap){
            // Tower image properties
            tower.setX(x);
            tower.setY(y);
            tower.draw(x, y, imagePath);          //New method here (delete this comment)
            ImageView towerImage = tower.getImage();
            towerImage.setOnMouseClicked(this::checkTowerStats);
            // Add tower to map
            ((Pane) trackDefault.getParent()).getChildren().add(towerImage);
            // Add to placedTowers
            placedTowers.add(tower);
            towersMap.put(towerImage, tower);


        }
        else {
            reserveTowers.add(tower);
            instructionLabel.setText("You cannot add more than 5 towers on the map. The Tower has been added to the reserve inventory");
        }
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
            placedTowers.remove(towersMap.get(selectedTower));

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
        towerNameLabel.setText(tower.getName());
        resourceTypeLabel.setText(tower.getResourceType());
        reloadSpeedLabel.setText(String.valueOf(tower.getReloadSpeed()));
        loadAmountLabel.setText(String.valueOf(tower.getLoadAmount()));
        levelLabel.setText(String.valueOf(tower.getTowerLevel()));
        // RANGE?
        xpLabel.setText(String.valueOf(tower.getXp()));
        radiusCircle = new Circle(tower.getX() , tower.getY(), tower.getRadius());
        radiusCircle.setFill(null);
        radiusCircle.setStroke(Color.RED);
        ((Pane) trackDefault.getParent()).getChildren().add(radiusCircle);

    }
    switch (tower.getResourceType()) {
        case "Bronze":
            selectedTowerImage.setImage(new Image("Art/Asset Pack/Factions/Knights/Buildings/Tower/bronzeTower.png"));
            break;
        case "Silver":
            selectedTowerImage.setImage(new Image("Art/Asset Pack/Factions/Knights/Buildings/Tower/silverTower.png"));
            break;
        case "Gold":
            selectedTowerImage.setImage(new Image("Art/Asset Pack/Factions/Knights/Buildings/Tower/goldTower.png"));
            break;
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
            cartTypeNumbers.add(roundNumber+1); //Bronze carts
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

}
    // Add other methods and properties as needed


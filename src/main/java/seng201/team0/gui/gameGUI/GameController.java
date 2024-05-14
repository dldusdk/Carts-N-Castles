package seng201.team0.gui.gameGUI;

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
import seng201.team0.services.gameLoaders.LevelLoader;
import seng201.team0.services.gameLoaders.LoadRound;
import seng201.team0.services.gameLoaders.PathLoader;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

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
    private ImageView selectedTower = null;

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
    private int round = 0;
    private String difficulty;
    private int totalRounds = 10; //need to scale this on difficulty
    private LevelLoader levelGrid;
    private PathLoader path;

    public void init(Stage primaryStage) {
        /**
         * Initializes the controller with the primary stage
         * @param primaryStage The primary stage of the application
         */

        this.primaryStage = primaryStage;
        String levelPath = "src/main/resources/levelCSV/Level1/Level1Concept_Track.csv";
        String levelDecor = "src/main/resources/levelCSV/Level1/Level1Concept_Decorations.csv";

        difficulty = "Normal";

        roundButton.setText(String.valueOf("Play: " + round));
        levelGrid = new LevelLoader(trackDefault, levelPath, levelDecor);
        path = new PathLoader("src/main/resources/levelCSV/Level1/Level1CartPath", "src/main/resources/levelCSV/Level1/Level1RotatePath");

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
        // Tower image properties
        ImageView towerImage = new ImageView(new Image(imagePath));
        towerImage.setFitWidth(128);
        towerImage.setFitHeight(256);
        towerImage.setX(x - 64);
        towerImage.setY(y - 128);
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
        if (round > totalRounds) {
            roundButton.setDisable(true);
            //Should switch view to win screen.
        } else {
            LoadRound newRound = new LoadRound(round, difficulty, cartDefault, levelGrid, path, 10);
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

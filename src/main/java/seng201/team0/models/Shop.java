package seng201.team0.models;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class Shop {

    // Player info
    int currentStock;


    // Items
    String itemName;
    String itemDescription;
    int itemCost;

    // Towers
    String towerName;
    String towerDescription;
    int towerCost;
    int silvertowerStock;
    int bronzetowerStock;
    int goldtowerStock;

    // Upgrades
    String upgradeName;
    String upgradeDescription;
    int upgradeCost;

    public void items() {
        /**
         * Items class
         * Add any items here and use methods below
         */

    }

    public int towerStock() {
        /**
         * Set tower information here:
         * RE: TOWER STOCK. Tower stock resets per round??? Check with gordon
         * RANDOMISE TOWER STOCK???
         * MAX STOCK is 5
         * 5 on map
         * Error if more than 5" YOU CAN ONLY have 5 towers at a time, please purchase after you sell a tower.
         */
        silvertowerStock = 2;
        bronzetowerStock = 2;
        goldtowerStock = 1;

        return silvertowerStock + bronzetowerStock + goldtowerStock;
    }

    public void upgrades() {
        // upgrade tower shoot rate
        // upgrade tower shoot multiplier shoots twice
        // projectiles slow carts down


    }


}
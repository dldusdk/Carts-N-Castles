package seng201.team0.models;

import java.util.Random;

import static java.lang.Math.max;

public class Shop {

    // Towers
    private int bronzeTowerStock = 5;
    private int silverTowerStock = 3;
    private int goldTowerStock = 2;

    // Upgrades might not need this though...
    private int speedUpgradeStock = 500;
    private int rangeUpgradeStock = 500;
    private int fillingUpgradeStock = 500;

    // Define the cost of each shop item
    private int bronzeTowerCost = 50;
    private int silverTowerCost = 100;
    private int goldTowerCost = 200;

    private int speedUpgradeCost = 200;
    private int rangeUpgradeCost = 250;
    private int fillingUpgradeCost = 300;

    private Random random;

    public Shop() {
        random = new Random();
    }

    // GETTERS
    public int getBronzeTowerStock() {
        return bronzeTowerStock;
    }
    public int getSilverTowerStock() {
        return silverTowerStock;
    }
    public int getGoldTowerStock() {
        return goldTowerStock;
    }
    public int getBronzeTowerCost() { return bronzeTowerCost; }
    public int getSilverTowerCost() { return silverTowerCost; }
    public int getGoldTowerCost() { return goldTowerCost; }
    public int getSpeedUpgradeCost() { return speedUpgradeCost; }
    public int getRangeUpgradeCost() { return rangeUpgradeCost; }
    public int getFillingUpgradeCost() { return fillingUpgradeCost; }

    public int getSellValue(String towerType, int roundNumber) {
        int initialCost;
        switch (towerType) {
            case "Bronze":
                initialCost = bronzeTowerCost;
                break;
            case "Silver":
                initialCost = silverTowerCost;
                break;
            case "Gold":
                initialCost = goldTowerCost;
                break;
            default:
                return 0;
        }
        // Depreciate 10% per round
        double depreciationRate = 0.10;
        double depreciationFactor = Math.pow((1-depreciationRate), roundNumber);
        return (int) (initialCost * depreciationFactor);
    }

    // OTHER METHODS

    public void decreaseStock(String towerType) {
        /**
         * Decrease the stock
         * @author Michelle Lee
         */
        switch (towerType) {
            case "Bronze":
                if (bronzeTowerStock > 0) {
                    System.out.println(bronzeTowerStock);
                    bronzeTowerStock--;
                }
                break;
            case "Silver":
                if (silverTowerStock > 0) {
                    silverTowerStock--;
                }
                break;
            case "Gold":
                if (goldTowerStock > 0) {
                    goldTowerStock--;
                }
                break;
        }
    }

    public int getStock(String towerType) {
        /**
         * Depending on the passed String return the Stock
         * @author Michelle Lee
         */
        switch (towerType) {
            case "Bronze":
                return bronzeTowerStock;
            case "Silver":
                return silverTowerStock;
            case "Gold":
                return goldTowerStock;
            default:
                return 0;
        }
    }

    public void randomizeStock() {
        /**
         * After the first round the stock is randomized
         * The bronze tower will always be between 0 - 5
         * @uthor Michelle Lee
         */
        int totalStock = 10;
        int bronze_max_stock = 5;

        bronzeTowerStock = random.nextInt(totalStock + 1); // Random stock between 0 and bronze_max_stock
        int remainingStock = bronze_max_stock - bronzeTowerStock;

        remainingStock = Math.max(0,remainingStock); // Added this to stop negative value error - Gordon :)

        silverTowerStock = random.nextInt(remainingStock + 1); // Random stock between 0 and remainingStock
        remainingStock -= silverTowerStock;

        remainingStock = Math.max(0,remainingStock);  // Added this to stop negative value error - Gordon :)

        goldTowerStock = random.nextInt(remainingStock + 1); // Random stock between 0 and remainingStock
    }
}

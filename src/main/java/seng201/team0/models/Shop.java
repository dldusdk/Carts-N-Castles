package seng201.team0.models;

import java.util.Random;

import static java.lang.Math.max;

public class Shop {
    // Towers
    private int bronzeTowerStock = 5;
    private int silverTowerStock = 3;
    private int goldTowerStock = 2;
    // Define the cost of each shop item
    private int bronzeTowerCost = 50;
    private int silverTowerCost = 100;
    private int goldTowerCost = 200;
    private int speedUpgradeCost = 200;
    private int rangeUpgradeCost = 250;
    private int fillingUpgradeCost = 300;
    private Random random;

    /**
     * Initialize the random variable to use for randomizing stock levels
     */
    public Shop() {
        random = new Random();
    }

    // GETTERS

    /**
     * @return Bronze Tower Stock Level
     * @author Michelle Lee
     */
    public int getBronzeTowerStock() {
        return bronzeTowerStock;
    }
    /**
     * @return Silver Tower Stock Level
     * @author Michelle Lee
     */
    public int getSilverTowerStock() {
        return silverTowerStock;
    }
    /**
     * @return Gold Tower Stock Level
     * @author Michelle Lee
     */
    public int getGoldTowerStock() {
        return goldTowerStock;
    }

    /**
     * @return Bronze Tower Cost
     * @author Michelle Lee
     */
    public int getBronzeTowerCost() { return bronzeTowerCost; }
    /**
     * @return Silver Tower Cost
     * @author Michelle Lee
     */
    public int getSilverTowerCost() { return silverTowerCost; }
    /**
     * @return Gold Tower Cost
     * @author Michelle Lee
     */
    public int getGoldTowerCost() { return goldTowerCost; }
    /**
     * @return Speed Upgrade Cost
     * @author Michelle Lee
     */
    public int getSpeedUpgradeCost() { return speedUpgradeCost; }
    /**
     * @return Range Upgrade Cost
     * @author Michelle Lee
     */
    public int getRangeUpgradeCost() { return rangeUpgradeCost; }
    /**
     * @return Fill Cart Amount Upgrade Cost
     * @author Michelle Lee
     */
    public int getFillingUpgradeCost() { return fillingUpgradeCost; }

    /**
     * This method allows the user to sell the tower at a depreciated cost by calculating the depreciation based on the number
     * of rounds that have gone by
     * @param towerType String of the tower typ that we have selected
     * @param roundNumber which round the game is currently in
     * @return the sell value of the tower, will be depreciated depending on the round number
     * @author Michelle Lee
     */
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
        int moneyAwarded = (int) (initialCost * depreciationFactor);
        return (int) (Math.ceil(moneyAwarded/5)*5);
    }

    /**
     * Decreases the stock of the tower passed through
     * @param towerType String of the tower typ that we have selected
     * @author Michelle Lee
     */
    public void decreaseStock(String towerType) {
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
    /**
     * Get the stock of the tower passed through
     * @param towerType String of the tower typ that we have selected
     * @author Michelle Lee
     */
    public int getStock(String towerType) {
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

    /**
     * Randomizes the stock with the bronze tower stock always between 0 - 5
     * @author Michelle Lee
     */
    public void randomizeStock() {
        /**
         * After the first round the stock is randomized
         * The bronze tower will always be between 0 - 5
         * @uthor Michelle Lee
         */
        int totalStock = 10;
        int bronzeMaxStock = 5;

        // Random stock between 0 and bronzeMaxStock
        bronzeTowerStock = random.nextInt(totalStock + 1);
        int remainingStock = bronzeMaxStock - bronzeTowerStock;
        remainingStock = Math.max(0,remainingStock);
        // Set Silver Stock
        silverTowerStock = random.nextInt(remainingStock + 1);
        remainingStock -= silverTowerStock;
        // Set Gold Stock
        remainingStock = Math.max(0,remainingStock);
        goldTowerStock = random.nextInt(remainingStock + 1);
    }
}

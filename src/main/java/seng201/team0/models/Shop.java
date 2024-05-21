package seng201.team0.models;

import java.util.Random;

public class Shop {

    // Towers
    private int bronzeTowerStock = 5;
    private int silverTowerStock = 3;
    private int goldTowerStock = 2;

    // Define the cost of each tower type
    private int bronzeTowerCost = 50;
    private int silverTowerCost = 100;
    private int goldTowerCost = 200;

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

    public int getBronzeTowerCost() {
        return bronzeTowerCost;
    }

    public int getSilverTowerCost() {
        return silverTowerCost;
    }

    public int getGoldTowerCost() {
        return goldTowerCost;
    }

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
        int totalStock = 10;
        int max_stock = 5;

        bronzeTowerStock = random.nextInt(totalStock + 1); // Random stock between 0 and totalStock
        int remainingStock = max_stock - bronzeTowerStock;

        silverTowerStock = random.nextInt(remainingStock + 1); // Random stock between 0 and remainingStock
        remainingStock -= silverTowerStock;

        goldTowerStock = random.nextInt(remainingStock + 1); // Random stock between 0 and remainingStock
    }

}

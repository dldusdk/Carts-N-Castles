package seng201.team0.models;

import java.util.Random;

public class Shop {

    // Towers
    private int bronzeTowerStock = 5;
    private int silverTowerStock = 3;
    private int goldTowerStock = 2;
    private Random random;

    public Shop() {
        random = new Random();
    }

    public int getBronzeTowerStock() {
        return bronzeTowerStock;
    }

    public int getSilverTowerStock() {
        return silverTowerStock;
    }

    public int getGoldTowerStock() {
        return goldTowerStock;
    }

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
        int remainingStock = totalStock - bronzeTowerStock;

        silverTowerStock = random.nextInt(remainingStock + 1); // Random stock between 0 and remainingStock
        remainingStock -= silverTowerStock;

        goldTowerStock = random.nextInt(remainingStock + 1); // Random stock between 0 and remainingStock
    }
}

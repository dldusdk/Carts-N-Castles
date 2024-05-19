package seng201.team0.models;

public class Shop {

    // Towers
    private int bronzeTowerStock = 5;
    private int silverTowerStock = 3;
    private int goldTowerStock = 2;

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
}

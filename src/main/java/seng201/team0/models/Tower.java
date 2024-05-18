package seng201.team0.models;

public class Tower {
    String towerName;
    String resourceType;
    String towerDescription;
    double reloadSpeed;
    double loadAmount;
    int towerLevel;
    int range;
    int xp;

    // Constructor
    public Tower(String towerName, String resourceType, double reloadSpeed, double loadAmount, int towerLevel, int range, int xp) {
        this.towerName = towerName;
        this.resourceType = resourceType;
        this.reloadSpeed = reloadSpeed;
        this.loadAmount = loadAmount;
        this.towerLevel = towerLevel;
        this.range = range;
        this.xp = xp;
    }

    // Getters
    public String getName() { return towerName; }
    public String getResourceType() { return resourceType; }
    public double getReloadSpeed() { return reloadSpeed; }
    public double getLoadAmount() { return loadAmount; }
    public int getTowerLevel() { return towerLevel; }
    public int getRange() { return range; }
    public int getXp() { return xp; }

    // Upgrade methods
    public void upgradeReloadSpeed() {
        this.reloadSpeed *= 0.9; // Example: Decrease reload speed by 10%
    }

    public void upgradeLoadAmount() {
        this.loadAmount += 1; // Example: Increase load amount by 1
    }

    public void upgradeTowerLevel() {
        this.towerLevel += 1; // Example: Increase tower level
    }

    public void upgradeRange() {
        this.range += 10; // Example: Increase range by 10
    }
}

package seng201.team0.models;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import seng201.team0.models.carts.CartBasic;

import java.util.ArrayList;

public class Tower {

    // Tower Stats
    String towerName;
    String resourceType;
    double reloadSpeed;
    double loadAmount;
    int towerHealth;
    int towerLevel;
    int range;
    int xp;
    private ImageView towerImage;

    // Tower Projectiles
    private long projectileTime;
    double x;
    double y;
    private long fireRate = 500000000L;


    public Tower(String towerName, String resourceType, double reloadSpeed, double loadAmount, int towerLevel, int range, int xp) {
        /**
         * @author Michelle Lee
         */
        this.towerName = towerName;
        this.resourceType = resourceType;
        this.reloadSpeed = reloadSpeed;
        this.loadAmount = loadAmount;
        this.towerHealth = towerHealth;
        this.towerLevel = towerLevel;
        this.range = range;
        this.xp = xp;
    }

    //Setters
    public void setX(double xCoord){x = xCoord;}
    public void setY(double yCoord){y = yCoord;}
    public void setProjectileTime (long time){projectileTime = time;}


    // Getters
    public String getName() { return towerName; }
    public String getResourceType() { return resourceType; }
    public double getReloadSpeed() { return reloadSpeed; }
    public double getLoadAmount() { return loadAmount; }
    public int getTowerLevel() { return towerLevel; }
    public int getRange() { return range; }
    public int getXp() { return xp; }

    // For projectiles
    public double getX(){return x;}
    public double getY(){return y;}
    public long getProjectileTime(){
        return(projectileTime);
    }


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

    public void draw(double x, double y, String path) {
        /**
         * Creates image of tower based on parameters
         * Held in class, so it can be accessed if needed
         * @Author Gordon Homewood
        */
        towerImage = new ImageView(new Image(path));
        towerImage.setFitWidth(128);
        towerImage.setFitHeight(256);
        towerImage.setX(x - 64);
        towerImage.setY(y - 192);
    }
    public ImageView getImage()
        /**
        * Returns ImageView of tower
        * @Author Gordon Homewood
        */
    {return(towerImage);}

    public void delete(){
        towerImage = null;
    }

    public long getFireRate(){
        return(fireRate);
    }


    public CartBasic targetAcquisition(ArrayList<CartBasic> cartList) {
        return(cartList.get(0));
    }
}

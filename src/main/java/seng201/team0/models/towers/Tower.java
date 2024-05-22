package seng201.team0.models.towers;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import seng201.team0.models.carts.CartBasic;

import java.util.*;

public class Tower {
    // Projectiles
    private long projectileTime;
    double x;
    double y;

    // Tower
    String towerName;
    String resourceType;
    double reloadSpeed;
    double loadAmount;
    int towerHealth;
    int towerLevel;
    int range;
    boolean towerState;


    private ImageView towerImage;
    private boolean destroyed;

    private ArrayList<Integer> roundsPlayed;

    // Tower Upgrades
    private static final double SPEED_UPGRADE_FACTOR = 0.9;  // 10% faster
    private static final double RANGE_UPGRADE_INCREMENT = 10.0;  // Increase range by 10 units
    private static final double LOAD_AMOUNT_INCREMENT = 5.0;  // Increase load amount by 5 units



    // Other
    String inventoryLocation;
    //private long fireRate = 500000000L;

    private double radius;
    private double previousRadius = 0;
    private double bonusPercent;

    // Constructor
    public Tower(String towerName, String resourceType,
                 double reloadSpeed, double loadAmount,
                 int towerLevel, int range, double radius, boolean towerState, String inventoryLocation) {
        this.roundsPlayed = new ArrayList<>();
        this.radius = radius;
        this.towerName = towerName;
        this.resourceType = resourceType;
        this.reloadSpeed = reloadSpeed;
        this.loadAmount = loadAmount;
        this.towerHealth = towerHealth;
        this.towerLevel = towerLevel;
        this.range = range;
        this.destroyed = false;
        this.towerState = towerState;
        this.inventoryLocation = inventoryLocation;
        this.bonusPercent = 1.25;
    }

    //Setters
    public void setX(double xCoord) {
        x = xCoord;
    }

    public void setY(double yCoord) {
        y = yCoord;
    }

    public void setProjectileTime(long time) {
        projectileTime = time;
    }

    public void setInventoryLocation(String location) {
        this.inventoryLocation = location;
    }

    public void setTowerState(boolean state) {
        this.towerState = state;
    }


    // Getters
    public String getName() {
        return towerName;
    }

    public void incrementRound(int roundNumber){
        if(towerState) this.roundsPlayed.add(roundNumber);
    }

    public ArrayList<Integer> getRoundsPlayed(){
        return(this.roundsPlayed);
    }

    public String getResourceType() {
        return resourceType;
    }

    public double getReloadSpeed() {
        return reloadSpeed;
    }

    public double getLoadAmount() {
        return loadAmount;
    }

    public int getTowerLevel() {
        return towerLevel;
    }

    public int getRange() {
        return range;
    }

    public boolean getTowerState() {
        return towerState;
    }

    public String getInventoryLocation() {
        return inventoryLocation;
    }

    // For projectiles
    public double getX() {
        return x;
    }
    public double getY() { return y; }
    
    public long getProjectileTime() {
        return projectileTime;
    }

    public double getRadius() {
        return (radius);
    }

    public double getDistance(double targetX, double targetY) {
        return (Math.sqrt(Math.pow(this.x - targetX, 2) + Math.pow(this.y - targetY, 2)));
    }

    // Upgrade methods
    public void upgradeSpeed() {
        /**
         * Updates the reload speed by constant
         * @author Michelle Lee
         */
        reloadSpeed *= SPEED_UPGRADE_FACTOR;
    }

    public void upgradeRange() {
        /**
         * Updates the range by constant
         * @author Michelle Lee
         */
        radius += RANGE_UPGRADE_INCREMENT;
    }

    public void upgradeFill() {
        /**
         * Updates the load amount by constant
         * @author Michelle Lee
         */
        loadAmount += LOAD_AMOUNT_INCREMENT;
    }

    // Projectiles
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

    public void delete() {
        towerImage = null;
    }
    public long getFireRate() {
        return ((long) reloadSpeed);
    }

    public double getBonusPercent(){
        return(this.bonusPercent);
    }

    public double setBonusPercent(double bonus){
        return(this.bonusPercent += bonus);
    }


    public CartBasic targetAcquisition(ArrayList<CartBasic> cartList) {
        ArrayList<CartBasic> targetsInRange = new ArrayList<>();

        for (CartBasic cart : cartList) {
            double distanceToCart = this.getDistance(cart.getCartObject().getTranslateX(), cart.getCartObject().getTranslateY());
            if (distanceToCart <= this.getRadius()) {
                targetsInRange.add(cart);
            }
        }

        if (targetsInRange.isEmpty()) {
            return null;
        }

        Collections.sort(targetsInRange, new Comparator<CartBasic>() {
            @Override
            public int compare(CartBasic o1, CartBasic o2) {
                double distance1 = getDistance(o1.getCartObject().getTranslateX(), o1.getCartObject().getTranslateY());
                double distance2 = getDistance(o2.getCartObject().getTranslateX(), o2.getCartObject().getTranslateY());
                return Double.compare(distance1, distance2);
            }
        });

        return targetsInRange.get(0); // Return the closest target in range
    }

    public boolean getDestroyed() {
        return (this.destroyed);
    }
    public void setDestroyed(boolean destroyed) {
        this.destroyed = destroyed;
        if (this.destroyed) {
            this.previousRadius = this.radius;
            this.radius = 0;
            this.towerImage.setImage(new Image("Art/Asset Pack/Factions/Knights/Buildings/Tower/Tower_Destroyed.png"));
        }
    }
}

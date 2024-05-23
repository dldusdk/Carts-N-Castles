package seng201.team0.models.towers;

import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import seng201.team0.models.carts.Cart;

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
    private static final double SPEED_UPGRADE_FACTOR = 1.05;  // 10% faster
    private static final double RANGE_UPGRADE_FACTOR = 1.1;  // Increase range by 10%
    private static final double LOAD_AMOUNT_FACTOR = 1.1;  // Increase load amount by 10%



    // Other
    String inventoryLocation;
    //private long fireRate = 500000000L;

    private double radius;
    private double bonusPercent;
    boolean buffState;

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
        this.towerLevel = towerLevel;
        this.range = range;
        this.destroyed = false;
        this.towerState = towerState;
        this.inventoryLocation = inventoryLocation;
        this.bonusPercent = 1.25;
        this.buffState = false;
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
        radius *= RANGE_UPGRADE_FACTOR;
    }

    public void upgradeFill() {
        /**
         * Updates the load amount by constant
         * @author Michelle Lee
         */
        loadAmount *= LOAD_AMOUNT_FACTOR;
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


    public Cart targetAcquisition(ArrayList<Cart> cartList) {
        ArrayList<Cart> targetsInRange = new ArrayList<>();

        for (Cart cart : cartList) {
            double distanceToCart = this.getDistance(cart.getCartObject().getTranslateX(), cart.getCartObject().getTranslateY());
            if (distanceToCart <= this.getRadius()) {
                targetsInRange.add(cart);
            }
        }

        if (targetsInRange.isEmpty()) {
            return null;
        }

        Collections.sort(targetsInRange, new Comparator<Cart>() {
            @Override
            public int compare(Cart o1, Cart o2) {
                double distance1 = getDistance(o1.getCartObject().getTranslateX(), o1.getCartObject().getTranslateY());
                double distance2 = getDistance(o2.getCartObject().getTranslateX(), o2.getCartObject().getTranslateY());
                return Double.compare(distance1, distance2);
            }
        });

        return targetsInRange.getFirst(); // Return the closest target in range
    }

    public void setBuff(boolean buff){
        if(buff){
            this.buffState = true;
            this.loadAmount = loadAmount * 2;
            this.radius = radius * 1.5;
            applyBuffHighlight(true);
        }
        if(!buff){
            this.loadAmount = loadAmount / 2;
            this.radius = radius / 1.5;
            applyBuffHighlight(false);
        }

    }

    public boolean getBuffState(){
        return(this.buffState);
    }

    public void applyBuffHighlight(boolean apply){
        if(apply){
        DropShadow dropShadow = new DropShadow();
        dropShadow.setColor(Color.CORNFLOWERBLUE);
        dropShadow.setRadius(60);
        this.towerImage.setEffect(dropShadow);}

    }

    public boolean getDestroyed() {
        return (this.destroyed);
    }
    public void setDestroyed(boolean destroyed) {
        this.destroyed = destroyed;
        if (this.destroyed) {
            this.towerImage.setImage(new Image("Art/Factions/Knights/Buildings/Tower/Tower_Destroyed.png"));
        }
    }

    public void repairTower(String towerType) {
        this.destroyed = false;
        this.towerImage.setImage(new Image(towerType));
    }
}

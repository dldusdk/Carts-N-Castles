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
    private static final double SPEED_UPGRADE_FACTOR = 1.05;  // Increase firerate by 5%
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
        this.bonusPercent = 1.5; //50% load buff to cart of same size
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

    public double getReloadSpeed()
    {
        return Math.ceil(reloadSpeed * 100) / 100;
    }

    public double getLoadAmount() {
        return Math.ceil(loadAmount * 100) / 100;
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
         * Returns image view of tower, making it easily accessible for applying effects,
         * getting coordinates, etc.
         *
        * @return ImageView of tower
        * @Author Gordon Homewood
        */
    {return(towerImage);}

    public long getFireRate() {
        return ((long) reloadSpeed);
    }
    public double getBonusPercent(){
        return(this.bonusPercent);
    }


    public Cart targetAcquisition(ArrayList<Cart> cartList) {
        /**
         * This method sorts the carts that are in the tower's radius based on how far they are through the track
         * and if they are in the radius or not. This is useful for the player, as it allows the towers to
         * dynamically switch targets and always target the most immediate threat
         *
         * @param cartList takes the current list of carts on the track to be judged for target selection
         *
         * @return target that is best
         *
         * @author Gordon Homewood
         */
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
                //Sorts carts based on distance to the tower
                double distance1 = getDistance(o1.getCartObject().getTranslateX(), o1.getCartObject().getTranslateY());
                double distance2 = getDistance(o2.getCartObject().getTranslateX(), o2.getCartObject().getTranslateY());
                return Double.compare(distance1, distance2);
            }
        });

        return targetsInRange.getFirst(); // Return the best target in range
    }

    public void setBuff(boolean buff){
        /**
         * This method changes the buff status of the tower, and it's given effects. If it is buffed, then the
         * load amount and radius are scaled up accordingly, making the buffed tower a formidable force no
         * matter the difficulty or stats.
         * If the round is over, this method should be called with buff=false, so the tower can be reset to its
         * original state. setBuff(false) should not be called to weaken a tower, only to remove a buff.
         *
         * @param buff gives if tower should be buffed or have buff removed
         *
         * @author Gordon Homewood
         */
        if(buff){
            this.buffState = true;
            this.loadAmount = this.loadAmount * 2;
            this.radius = this.radius * 1.5;
            applyBuffHighlight(true);
        }
        if(!buff){
            this.buffState = false;
            this.loadAmount = this.loadAmount / 2;
            this.radius = this.radius / 1.5;
            applyBuffHighlight(false);
        }

    }

    public boolean getBuffState(){
        return(this.buffState);
    }

    public void applyBuffHighlight(boolean apply){
        /**
         * Adds a highlight to the buffed tower to give visual indication of buffed tower event
         * to player.
         *
         * @param apply if apply, then add the effect, otherwise set effect to null
         *
         * @author Gordon Homewood
         */
        if(apply){
        DropShadow dropShadow = new DropShadow();
        dropShadow.setColor(Color.CORNFLOWERBLUE);
        dropShadow.setRadius(60);
        this.towerImage.setEffect(dropShadow);}
        else{
            towerImage.setEffect(null);
        }

    }

    public boolean getDestroyed() {
        return (this.destroyed);
    }

    /**
     * This method should be called to change the destroyed state of the tower.
     * Also updates the image to the destroyed version of the tower.
     *
     * @param destroyed boolean value for destroyed state.
     * @author Gordon Homewood
     */
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

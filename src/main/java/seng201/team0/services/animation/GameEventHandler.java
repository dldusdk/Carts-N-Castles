package seng201.team0.services.animation;

import javafx.scene.image.ImageView;
import seng201.team0.models.carts.Cart;
import seng201.team0.models.towers.GoldMine;
import seng201.team0.models.towers.Projectile;
import seng201.team0.models.towers.Tower;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

/**
 * This class handles the Animation loop (defined in the GameController) logic, which makes the 
 * design of the code more modular. Although the loop is called in the GameController, separating it to this 
 * class makes it more readable.
 * 
 * @author Gordon Homewood
 */
public class GameEventHandler {
    private ArrayList<Cart> cartList;

    /**
     * Constructor that takes in an updated cart list when called in Game Controller
     * @param cartList list of carts that are on screen at the time of call.
     *                 
     * @author Gordon Homewood
     */
    public GameEventHandler(ArrayList<Cart> cartList){
        this.cartList = cartList;
    }

    /**
     * Returns cart list, useful for receiving the updated cartList in the gameController. 
     *
     * @author Gordon Homewood
     */
    public ArrayList<Cart> getCartList(){return(cartList);}


    /**
     * Handles tower logic based on what towers are active at the moment of call. Spawns projectiles if 
     * the tower's target is in tower's radius and the tower is active. Loop through the list of towers to 
     * check all of them individually. 
     * 
     * @param mainTowers tower list that is active in the round
     * @param cartList cart list of carts that are active in the round
     * @param timestamp time given by the timer in the GameController 
     * @param trackDefault passes to projectile, so it can be spawned and drawn on screen
     *                     
     * @author Gordon Homewood
     */
    public void handleTowerLogic(ArrayList<Tower> mainTowers,ArrayList<Cart> cartList,
                                 long timestamp, ImageView trackDefault) {
        for (Tower tower : mainTowers) {

            // If tower is inactive or destroyed skip the code, else continue if active
            if (!tower.getTowerState() || tower.getDestroyed()) {
                continue;
            }
            Cart towerTarget = tower.targetAcquisition(cartList);
            if (towerTarget == null) {
                continue;
            }

            long fireRate = 1000000000L / tower.getFireRate();
            long fireTime = timestamp - tower.getProjectileTime();

            double cartPosition = towerTarget.getCartObject().getTranslateX();

            double targetDistance =
                    tower.getDistance(towerTarget.getCartObject().getTranslateX(),
                            towerTarget.getCartObject().getTranslateY());

            if (fireTime >= fireRate && cartPosition > 0 && tower.getRadius() > targetDistance) {
                //If target in radius, tower can fire and cart is on screen
                double damage = tower.getLoadAmount();
                if (Objects.equals(towerTarget.getResourceType(), tower.getResourceType())) {
                    damage = damage * tower.getBonusPercent();
                }
                String type = tower.getResourceType();
                int spawnX = (int) (tower.getX() - 30);
                int spawnY = (int) (tower.getY() - 30);

                Projectile projectile = new Projectile(spawnX, spawnY, type, trackDefault, towerTarget, damage);
                projectile.spawn();
                tower.setProjectileTime(timestamp);
            }
        }
    }

    /**
     * This method handles the cart logic, reading all the carts in the cart list until they are
     * all destroyed.
     *
     * @param goldMine passes through the goldmine so the player's lives can be checked
     * @return string of different states (passed on if round should continue),
     * which are handled in the GameController
     *
     * @author Gordon Homewood
     */
    public String handleCartLogic(GoldMine goldMine){
        Iterator<Cart> iterator = cartList.iterator(); //So carts can safely be removed in loop
        while (iterator.hasNext()) {
            Cart cart = iterator.next();

            if (cart.getLoadPercent() >= 1) {
                //Carts get destroyed if at max load (explosion different color)
                cart.explode((int) cart.getCartObject().getTranslateX() - 60, (int) cart.getCartObject().getTranslateY() - 60, false);
                iterator.remove();
                cart.despawn();
                return("Continue");
            }
            if (cart.getCartObject().getTranslateX() > 1025 && cart.getLoadPercent() < 1) {
                //Cart damage gold mine if it reaches the end of track
                iterator.remove();
                cart.explode(965, 380, true);
                return("Damaged");
            }
            if ((cartList.size() <= 0) && !(goldMine.getHealth() <= 0)) {
                // Game should coninute if all carts are destroyed and gold min still good
                return("RoundWon");
            }
        }
        return(null);
    }
};

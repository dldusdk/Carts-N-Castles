package seng201.team0.services.animation;

import javafx.scene.image.ImageView;
import seng201.team0.models.carts.Cart;
import seng201.team0.models.towers.GoldMine;
import seng201.team0.models.towers.Projectile;
import seng201.team0.models.towers.Tower;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

//Class commenth
public class GameEventHandler {
    private ArrayList<Cart> cartList;

    public GameEventHandler(ArrayList<Cart> cartList){
        this.cartList = cartList;
    }

    public ArrayList<Cart> getCartList(){return(cartList);}


    public void handleTowerLogic(ArrayList<Tower> mainTowers,ArrayList<Cart> cartList,
                                 long timestamp, ImageView trackDefault) {
        for (Tower tower : mainTowers) {

            // If tower is inactive or destroyed skip the code, else continue if active
            if (!tower.getTowerState()) {
                continue;
            }
            Cart towerTarget = tower.targetAcquisition(cartList);
            if (towerTarget == null) {
                continue;
            }

            long fireRate = 1000000000L / tower.getFireRate();
            long fireTime = timestamp - tower.getProjectileTime();

            double cartOnTrack = towerTarget.getCartObject().getTranslateX();

            double targetDistance =
                    tower.getDistance(towerTarget.getCartObject().getTranslateX(),
                            towerTarget.getCartObject().getTranslateY());

            if (fireTime >= fireRate && cartOnTrack > 0 && tower.getRadius() > targetDistance) {

                double damage = tower.getLoadAmount();
                //System.out.println(towerTarget.getResourceType());
                //System.out.println(tower.getResourceType());
                if (Objects.equals(towerTarget.getResourceType(), tower.getResourceType())) {
                    damage = damage * tower.getBonusPercent();
                    //System.out.println("Damage "+ damage);
                    //System.out.println("Load "+towerTarget.getLoadPercent());
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
    public String handleCartLogic(GoldMine goldMine){
        Iterator<Cart> iterator = cartList.iterator(); //So carts can safely be removed in loop
        while (iterator.hasNext()) {
            Cart cart = iterator.next();

            if (cart.getLoadPercent() >= 1) {
                //Carts get destroyed if at max load (explosion different color)
                cart.explode((int) cart.getCartObject().getTranslateX() - 60, (int) cart.getCartObject().getTranslateY() - 60, false);
                iterator.remove();
                cart.despawn();
                return("Continue");//cartNumber--;
            }
            if (cart.getCartObject().getTranslateX() > 1025 && cart.getLoadPercent() < 1) {
                //Carts damage gold mine if reach end of track
                iterator.remove();
                cart.explode(965, 380, true);
                return("Damaged");
            }
            if ((cartList.size() <= 0) && !(goldMine.getHealth() <= 0)) {
                return("Fail");
            }
        }
        return(null);
    }
};

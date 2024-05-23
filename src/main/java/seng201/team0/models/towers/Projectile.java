package seng201.team0.models.towers;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import seng201.team0.models.carts.Cart;


/**
 * This class creates a projectile object for the tower to shoot and
 * fill up carts with.
 *
 * <p>The interaction between the gameController, tower, cart and this projectile class is
 * essential to the game logic. In this class, the projectile logic can be seperated from
 * those aforementioned classes, tending towards a more modular design </p>
 *
 * @author Gordon Homewood
 */

public class Projectile {
    private int xCoord;
    private int yCoord;
    private ImageView projectileDefault;
    private ImageView projectileObject;
    private Timeline timeline;
    private int framesPerSecond = 60;
    private Cart target;
    private boolean lock = false;
    private double damage;


    /**
     * Creates a new projectile based on parameters given. Also handles a timeline for the
     * projectile to be updated during its lifetime.
     *
     * @param xCoordStart indicates x start, based on tower position
     * @param yCoordStart indicates y start, based on tower position
     * @param type allows the projectile to be shown in the right image respective to the tower's type
     * @param projectileDefaultLoad allows the projectile to be spawned on the screen
     * @param cart takes in cart as a target
     * @param inputDamage scales the projectile's load amount with the towers' stats
     */
    public Projectile(int xCoordStart, int yCoordStart, String type, ImageView projectileDefaultLoad,
                      Cart cart, double inputDamage){

        String path = initType(type);

        target = cart;
        projectileDefault = projectileDefaultLoad;
        projectileObject = new ImageView(path);
        ((Pane) projectileDefault.getParent()).getChildren().add(projectileObject);

        damage = inputDamage;

        xCoord = xCoordStart;
        yCoord = yCoordStart;

        //Create timeline to update projectile based on framerate of 60 (standard animation timer refresh rate)
        timeline = new Timeline(
                new KeyFrame(Duration.seconds((double) 1 /framesPerSecond), event -> {
                    if (!lock && !cart.getDestroyed()){
                        updateProjectile();
                        spawn();
                    }
                    else{
                        destroy();
                    }
                }
                )
        );
       timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    /**
     * This method initializes the image of the projectile based on the tower's type. Returns a bronze image path
     * if string is not a given type.
     *
     * @param type (Bronze, Silver or Gold)
     * @author Gordon Homewood
     */
    private String initType(String type) {

        if(type.equals("Bronze")){
            return("Art/projectiles/bronze1.png");
        }
        if(type.equals("Silver")){
            return("Art/projectiles/silver1.png");
        }
        if(type.equals("Gold")){
            return("Art/projectiles/gold1.png");
        }
        return("Art/projectiles/bronze1.png");
    }


    /**
     * Returns current x coordinate of projectile.
     * @return x coordinate
     *
     * @author Gordon Homewood
     */
    private int getCurrentX(){return(xCoord);}

    /**
     * Return current y coordinate of projectile
     * @return y coordinate
     *
     * @author Gordon Homewood
     */
    private int getCurrentY(){return(yCoord);}

    /**
     * Uses getTranslateX to get the animated target's x location
     * @return x coordinate of target
     *
     * @author Gordon Homewood
     */
    private double getTargetX(){return(target.getCartObject().getTranslateX());}


    /**
     * Uses getTranslateY to get the animated target's y location
     * @return y coordinate of target.
     *
     * @author Gordon Homewood
     */
    private double getTargetY(){return(target.getCartObject().getTranslateY());}


    /**
     * This method destroys the projectile to hide from the screen and give null pointers so
     * it can be removed by the Java garbage collection.
     *
     * @author Gordon Homewood
     */
    public void destroy() {
        if (projectileObject != null && projectileObject.getParent() != null) {
            ((Pane) projectileObject.getParent()).getChildren().remove(projectileObject);
        }
        projectileDefault = null;
        projectileObject = null;
        target = null;
        timeline = null;
    }

    /**
     * This method uses trigonometry to update the position of the projectile relative to its
     * target.This translates to the user as an animation, where the projectile follows the cart
     * until it locks and is destroyed as it hits the target.
     *
     * @author Gordon Homewood
     */
    private void updateProjectile() {

        double velocity = 5;
        double changeX = getTargetX() - getCurrentX();
        double changeY = getTargetY() - getCurrentY();
        //Euclidean Formula for distance
        double distance = Math.sqrt(changeX * changeX + changeY * changeY);

        double newAngle = Math.atan2(changeY, changeX); //Gets angle to target

        if ((distance < velocity + 40) || lock) {
            //If hit the target, increase the load once
            lock = true;
            target.setLoad(damage);
            damage = 0;

        }
        else{
            if (projectileObject != null){
        // Update the position based on velocity and angle in y-axis and x-axis
        xCoord += (int) (velocity * Math.cos(newAngle));
        yCoord += (int) (velocity * Math.sin(newAngle));}
        else destroy();}

    }

    /**
     * Spawns the projectile based on the given coordinates of the tower.
     *
     * @author Gordon Homewood
     */
    public void spawn() {
        if(projectileObject != null){
        projectileObject.setX(xCoord);
        projectileObject.setY(yCoord);}
    }
}

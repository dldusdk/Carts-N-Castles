package seng201.team0.models.towers;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import seng201.team0.models.carts.CartBasic;

public class Projectile {
    private int speed;
    private int resources;
    private int size;
    private String resourceType;
    private int xCoord;
    private int yCoord;
    private int time = 0;
    private ImageView projectileDefault;
    private ImageView projectileObject;
    private Timeline timeline;
    private int framesPerSecond = 60;
    private CartBasic target;
    private double angle=0;
    private boolean lock = false;
    private double damage;
    private boolean state = true;

    public Projectile(int xCoordStart, int yCoordStart, String type, ImageView projectileDefaultLoad, CartBasic cart,double inputDamage){
        String path = initType(type);



        target = cart;
        projectileDefault = projectileDefaultLoad;
        projectileObject = new ImageView(path);
        ((Pane) projectileDefault.getParent()).getChildren().add(projectileObject);

        damage = inputDamage;

        xCoord = xCoordStart;
        yCoord = yCoordStart;
        timeline = new Timeline(
                new KeyFrame(Duration.seconds((double) 1 /framesPerSecond), event -> {
                    updateTime();
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

    public void updateTime(){
        time += 1;
    }

    private int getCurrentX(){return(xCoord);}
    private int getCurrentY(){return(yCoord);}

    private double getTargetX(){return(target.getCartObject().getTranslateX());}

    private double getTargetY(){return(target.getCartObject().getTranslateY());}

    public ImageView getProjectileImage(){
        return(projectileDefault);
    }

    public boolean getState(){return(state);}

    public void destroy() {
        if (projectileObject != null && projectileObject.getParent() != null) {
            ((Pane) projectileObject.getParent()).getChildren().remove(projectileObject);
        }
        projectileDefault = null;
        projectileObject = null;
        target = null;
        timeline = null;
    }
    private void updateProjectile() {
        double velocity = 5;
        double changeX = getTargetX() - getCurrentX();
        double changeY = getTargetY() - getCurrentY();
        double distance = Math.sqrt(changeX * changeX + changeY * changeY);

        double newAngle = Math.atan2(changeY, changeX); //Gets angle to target

        //Angle smoothing algorithm - tweaked to a point so it
        //produces a smooth animation (only adjusts or)
        //angle = (9 * angle + newAngle) / 10;

        if ((distance < velocity + 40) || lock) {
            lock = true;
            //projectileObject.setVisible(false);
            target.setLoad(damage);
            damage = 0;
            state = false;
            //destroy();
        }
        else{
            if (projectileObject != null){
        // Update the position based on velocity and angle
        xCoord += (int) (velocity * Math.cos(newAngle));
        yCoord += (int) (velocity * Math.sin(newAngle));}
        else destroy();}

    }

    public void spawn() {
        if(projectileObject != null){
        projectileObject.setX(xCoord);
        projectileObject.setY(yCoord);}
    }
}

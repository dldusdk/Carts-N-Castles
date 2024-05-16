package seng201.team0.models.towers;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
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

    public Projectile(int xCoordStart, int yCoordStart, String path, ImageView projectileDefaultLoad, CartBasic cart){
        target = cart;
        projectileDefault = projectileDefaultLoad;
        projectileObject = new ImageView(path);
        ((Pane) projectileDefault.getParent()).getChildren().add(projectileObject);



        xCoord = xCoordStart;
        yCoord = yCoordStart;
        timeline = new Timeline(
                new KeyFrame(Duration.seconds((double) 1 /framesPerSecond), event -> {
                    updateTime();
                    if (getTargetX() > 0){
                        updateProjectile();
                    }
                    spawn();

                }
                )
        );
       timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    public void updateTime(){
        time += 1;
    }
    private void loadY() {
        yCoord = (int) target.getCartObject().getTranslateY();
    }

    private void loadX() {
        xCoord = (int) target.getCartObject().getTranslateX();
    }

    private int getCurrentX(){return(xCoord);}
    private int getCurrentY(){return(yCoord);}

    private double getTargetX(){return(target.getCartObject().getTranslateX());}

    private double getTargetY(){return(target.getCartObject().getTranslateY());}

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
            xCoord = (int)getTargetX();
            yCoord = (int)getTargetY();
        }
        else{
        // Update the position based on velocity and angle
        xCoord += (int) (velocity * Math.cos(newAngle));
        yCoord += (int) (velocity * Math.sin(newAngle));}

    }

    public void spawn() {
        projectileObject.setX(xCoord);
        projectileObject.setY(yCoord);
    }
}

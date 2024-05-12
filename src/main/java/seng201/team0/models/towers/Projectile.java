package seng201.team0.models.towers;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class Projectile {
    int speed;
    int resources;
    int size;
    String resourceType;
    int xCoord;
    int yCoord;
    int count = 0;
    Timeline timeline = new Timeline();

    public Projectile(int xCoord, int yCoord){
        timeline = new Timeline(
                new KeyFrame(Duration.seconds(2), event -> loadProjectile())
        );
        count = 2;
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void loadProjectile() {
        System.out.println("Seconds = "+count+"   "+"Printing every 2 second :)");
        count += 2;
    }
}

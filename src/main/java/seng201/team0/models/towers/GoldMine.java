package seng201.team0.models.towers;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class GoldMine {
    int x;
    int y;
    ImageView goldMineObject;
    int health;

    public GoldMine(ImageView spawnDefault,int startHealth){
        health = startHealth;
        x = 970;
        y = 340;
        draw(x,y,spawnDefault);
        checkHealth();
    }
    public void draw(int x, int y, ImageView spawnDefault){
        goldMineObject = new ImageView("Art/Asset Pack/Resources/Gold Mine/GoldMine_Active.png");
        goldMineObject.setX(970);
        goldMineObject.setY(340);
        ((Pane) spawnDefault.getParent()).getChildren().add(goldMineObject);
    }
    public void checkHealth(){
        if(health == 1){
        Image damaged = new Image("Art/Asset Pack/Resources/Gold Mine/GoldMine_Inactive.png");
        goldMineObject.setImage(damaged);}
        if(health == 0){
            Image destroyed = new Image("Art/Asset Pack/Resources/Gold Mine/GoldMine_Destroyed.png");
            goldMineObject.setImage(destroyed);}
    }
    public int getHealth(){return(health);}

    public void decreaseHealth(){
        health--;
        checkHealth();
    }
}

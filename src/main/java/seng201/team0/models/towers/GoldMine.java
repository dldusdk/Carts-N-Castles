package seng201.team0.models.towers;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

/**
 * This class is responsible for generating a gold mine,
 * that the player must protect in order to win the game.
 *
 * <p>It functions completely differently from the Tower class,
 * as it's much more static and only exists to visually show
 * how many lives the player has left (in a round).</p>
 *
 * @author Gordon Homewood
 * */

public class GoldMine {
    int x;
    int y;
    int health;
    ImageView goldMineObject; //Stores the imageView in a variable so it's easily accessible

    public GoldMine(ImageView spawnDefault,int startHealth){
        /**
         * Creates a new gold mine, based on spawn location and health given by the players'
         * choice of difficulty. Also draws the initial image on the level and checks to see
         * if the image is appropriate for the health level.
         *
         * @author Gordon Homewood
         */
        health = startHealth;
        x = 970; //Convert to settings =====================================
        y = 340;
        draw(x,y,spawnDefault);
        checkHealth();
    }
    public void draw(int x, int y, ImageView spawnDefault){
        /**
         * Draws and updates the goldMine image on the screen, allowing it to be
         * shown on the level's pane.
         *
         * @param x for image location x coordinate
         * @param y for image location y coordinate
         * @param spawnDefault so the image can be placed on the same pane as the level
         * @author Gordon Homewood
         */
        goldMineObject = new ImageView("Art/Resources/Gold Mine/GoldMine_Active.png");
        goldMineObject.setX(x);
        goldMineObject.setY(y);
        ((Pane) spawnDefault.getParent()).getChildren().add(goldMineObject);
    }
    public void checkHealth(){
        /**
         * This method updates the gold mine's image based on it's health level
         * when called. Should be called after actions occur when the lives should
         * decrease, like when a cart reaches the end of the track
         *
         * @author Gordon Homewood
         */
        if(health > 1){
            Image damaged = new Image("Art/Resources/Gold Mine/GoldMine_Active.png");
            goldMineObject.setImage(damaged);}
        if(health == 1){
        Image damaged = new Image("Art/Resources/Gold Mine/GoldMine_Inactive.png");
        goldMineObject.setImage(damaged);}
        if(health == 0){
            Image destroyed = new Image("Art/Resources/Gold Mine/GoldMine_Destroyed.png");
            goldMineObject.setImage(destroyed);}
    }
    public int getHealth()
    /**
     * Returns current health. Should be called when checking if the
     * game could fail due to health reaching 0.
     *
     * @return current health of gold mine
     *
     * @author Gordon Homewood
     */
    {return(health);}

    public void setHealth(int lives)
    /**
     * Sets health to given lives the player should have in round.
     * Depends on difficulty selected by the player
     * @param lives amount of times cart has to reach the end before
     *              gold mine is destroyed.
     *
     * @author Gordon Homewood
     */
    {health = lives;}

    public void decreaseHealth(){
        /**
         * Decreases health by 1 and calls checkHealth() to update the image if needed.
         * Should be called when a cart reaches the end of the track and explodes to
         * damage the gold mine.
         *
         * @author Gordon Homewood
         */
        health--;
        checkHealth();
    }
}

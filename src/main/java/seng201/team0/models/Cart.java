package seng201.team0.models;

import javafx.animation.AnimationTimer;
import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import seng201.team0.services.Settings;


public class Cart {

    private int cartSpawnLocationX;
    private int cartSpawnLocationY;
    private ImageView cartImageSource;


    private AnimationTimer collisionTimer = new AnimationTimer() {
        @Override
        public void handle(long timestamp) {
            checkCollision(cartImageSource);
        }
    };


    public Cart(ImageView cartImageImport, int cartSize, String cartType, int cartSpeed) {
        collisionTimer.start();

        cartImageSource = cartImageImport;
        String difficulty = "Normal";
        Settings settings = new Settings(difficulty);

        cartSpawnLocationX = settings.getCartSpawnX();
        cartSpawnLocationY = settings.getCartSpawnY();

    }


    public ImageView loadCart(){
        //Change to cart class
        Image source = new Image("Art/Asset Pack/Carts/SilverEmpty.png");
        ImageView cartImage = new ImageView(source);
        cartImage.setX(Math.random() * (200 + cartSpawnLocationX));
        cartImage.setY(cartSpawnLocationY);
        cartImage.setImage(source);
        ((Pane) cartImageSource.getParent()).getChildren().add(cartImage);

        TranslateTransition movement = new TranslateTransition(Duration.seconds(2), cartImage);
        movement.setByX(100);
        movement.play();

        return(cartImage);

    }

    public void checkCollision(ImageView cart){
        //do something

    }
}

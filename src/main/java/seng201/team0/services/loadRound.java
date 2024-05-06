package seng201.team0.services;

import javafx.animation.Transition;
import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import seng201.team0.models.Cart;


import java.util.ArrayList;

public class loadRound {

    private String difficulty;
    private int cartNumber;
    private int cartSpawnLocationX;
    private int cartSpawnLocationY;
    private ArrayList<ArrayList<Integer>>tileList;

    public loadRound(String difficultySetting, ImageView cartDefault,ArrayList<ArrayList<Integer>>setTileList){

        tileList = setTileList;

        difficulty = difficultySetting;
        Settings settings = new Settings(difficulty);
        cartNumber = settings.getCartNumber();
        cartSpawnLocationX = settings.getCartSpawnX();
        cartSpawnLocationY = settings.getCartSpawnY();

        ArrayList<Cart> cartList = new ArrayList<>();
        for (int i=0;i < cartNumber; i++){
            Cart cart = new Cart(cartDefault,0,"bronze",0);
            System.out.println(i);
            loadCart(cartDefault);
        }
    }

    public void loadCart(ImageView cartImageSource){
        //Change to cart class
        Image source = new Image("Art/Asset Pack/Carts/PlainCartEmpty.png");
        ImageView cartImage = new ImageView(source);
        cartImage.setX(Math.random() * (200 + cartSpawnLocationX));
        cartImage.setY(cartSpawnLocationY);
        cartImage.setImage(source);
        ((Pane) cartImageSource.getParent()).getChildren().add(cartImage);
        TranslateTransition movement = new TranslateTransition(Duration.seconds(2), cartImage);
        movement.setByX(100);
        movement.play();


    }


}

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
        CartPathFinder cartPath = new CartPathFinder(tileList);


        difficulty = difficultySetting;
        Settings settings = new Settings(difficulty);
        cartNumber = settings.getCartNumber();
        cartSpawnLocationX = settings.getCartSpawnX();
        cartSpawnLocationY = settings.getCartSpawnY();

        ArrayList<Cart> cartList = new ArrayList<>();
        for (int i=0;i < cartNumber; i++){
            Cart cart = new Cart(cartDefault,0,"bronze",0);
            cart.loadCart();
            //loadCart(cartDefault);
        }
    }


}

package seng201.team0.services.gameLoaders;

import javafx.scene.image.ImageView;
import seng201.team0.models.carts.Cart;


import java.util.ArrayList;
import java.util.Random;

//Move animation timer into this class, move goldmine into own class with method for destruction based on
//found failure

public class LoadRound {

    private final ArrayList<ArrayList<Integer>>cartPath;
    private final ArrayList<ArrayList<Integer>>rotatePath;
    private final ImageView cartImage;
    private final ArrayList<Cart> cartList;


    public LoadRound(int round, String difficultySetting, ImageView cartDefault, LevelLoader gridData,PathLoader gridPath,
                     ArrayList<Integer> cartNum){

        int bronzeCartNum = cartNum.get(0);
        int silverCartNum = cartNum.get(1);
        int goldCartNum = cartNum.get(2);

        cartImage = cartDefault;
        cartPath = gridPath.getPath();
        rotatePath = gridPath.getRotatePath();

        cartList = new ArrayList<>();

        loadCarts("Bronze", bronzeCartNum, "Art/Carts/bronzeCarts/bronzeEmpty.png");
        loadCarts("Silver", silverCartNum, "Art/Carts/silverCarts/silverEmpty.png");
        loadCarts("Gold", goldCartNum, "Art/Carts/goldCarts/goldEmpty.png");

    }

    public void loadCarts(String type, int cartNumber, String imageSource){
        Random random = new Random();
        double sizeDouble = 1;
        System.out.println(cartNumber);
        for (int i=0;i < cartNumber; i++){
            int sizeFactor = random.ints(1,4).findFirst().getAsInt();
            if(sizeFactor == 1){
                sizeDouble = 0.75;
            }
            if(sizeFactor == 2){
                sizeDouble = 1;
            }
            if(sizeFactor == 1){
                sizeDouble = 1.25;
            }
            int upperSpawnBound = -100;
            int lowerSpawnBound = -1000;
            int cartSpawnX = random.ints(lowerSpawnBound, upperSpawnBound).findFirst().getAsInt();
            int upperSpeedBound = 150;
            int lowSpeedBound = 50;
            int cartSpeed = random.ints(lowSpeedBound, upperSpeedBound).findFirst().getAsInt();
            Cart cart = new Cart(cartImage,sizeDouble,type,cartSpeed,cartPath,rotatePath,cartSpawnX,imageSource);
            cartList.add(cart);

        }


    }

    public ArrayList<Cart> getCartList(){return(cartList);}
}

package seng201.team0.services.gameLoaders;

import javafx.scene.image.ImageView;
import seng201.team0.models.carts.CartBasic;


import java.util.ArrayList;
import java.util.Random;

//Move animation timer into this class, move goldmine into own class with method for destruction based on
//found failure

public class LoadRound {

    private String difficulty;
    private ArrayList<ArrayList<Integer>>cartPath;
    private ArrayList<ArrayList<Integer>>rotatePath;
    private ImageView cartImage;
    private ArrayList<CartBasic> cartList;
    private int lowerSpawnBound = -4000; //Scale based on difficutly
    private int upperSpawnBound = -100;
    private int lowSpeedBound = 50; //Scale based on difficulty
    private int upperSpeedBound = 200;
    private int bronzeCartNum;
    private int silverCartNum;
    private int goldCartNum;



    public LoadRound(int round, String difficultySetting, ImageView cartDefault, LevelLoader gridData,PathLoader gridPath,
                     ArrayList<Integer> cartNum){

        bronzeCartNum = cartNum.get(0);
        silverCartNum = cartNum.get(1);
        goldCartNum = cartNum.get(2);

        cartImage = cartDefault;
        cartPath = gridPath.getPath();
        rotatePath = gridPath.getRotatePath();

        difficulty = difficultySetting;
        cartList = new ArrayList<>();

        loadCarts("bronze",bronzeCartNum,"Art/Asset Pack/Carts/bronzeCarts/bronzeEmpty.png");
        loadCarts("silver",silverCartNum, "Art/Asset Pack/Carts/silverCarts/silverEmpty.png");
        loadCarts("gold",goldCartNum,"Art/Asset Pack/Carts/goldCarts/goldEmpty.png");

    }

    public void loadCarts(String type, int cartNumber, String imageSource){
        Random random = new Random();
        double sizeDouble = 1;
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
            int cartSpawnX = random.ints(lowerSpawnBound,upperSpawnBound).findFirst().getAsInt();
            int cartSpeed = random.ints(lowSpeedBound,upperSpeedBound).findFirst().getAsInt();
            CartBasic cart = new CartBasic(cartImage,sizeDouble,"bronze",cartSpeed,cartPath,rotatePath,cartSpawnX,imageSource);
            cartList.add(cart);
        }


    }

    public ArrayList<CartBasic> getCartList(){return(cartList);}
}

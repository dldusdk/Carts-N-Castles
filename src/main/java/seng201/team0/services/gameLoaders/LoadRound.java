package seng201.team0.services.gameLoaders;

import javafx.scene.image.ImageView;
import seng201.team0.models.carts.CartBasic;
import seng201.team0.models.towers.Projectile;
import seng201.team0.services.settings.Settings;


import java.util.ArrayList;
import java.util.Random;

//Move animation timer into this class, move goldmine into own class with method for destruction based on
//found failure

public class LoadRound {

    private String difficulty;
    private int cartNumber;
    private ArrayList<ArrayList<Integer>>cartPath;
    private ArrayList<ArrayList<Integer>>rotatePath;
    private int roundNumber;
    private ImageView cartImage;

    public LoadRound(int round,String difficultySetting, ImageView cartDefault, LevelLoader gridData,PathLoader gridPath,int cartAmount){
        roundNumber = round;
        cartImage = cartDefault;
        cartPath = gridPath.getPath();
        rotatePath = gridPath.getRotatePath();
        cartNumber = cartAmount;
        difficulty = difficultySetting;

        startRound();
    }

    public void startRound(){ArrayList<CartBasic> cartList = new ArrayList<>();
        int lowerBound = getSpawnLowerBound();
        int upperBound = getSpawnUpperBound();
        Random random = new Random();
        for (int i=0;i < 2; i++){
            int cartSpawnX = random.ints(lowerBound,upperBound).findFirst().getAsInt();
            double cartSpeed = Math.random();
            CartBasic cart = new CartBasic(cartImage,0,"bronze",30,cartPath,rotatePath,cartSpawnX);
        }


    }

    public int getSpawnLowerBound(){return(-4000);}
    public int getSpawnUpperBound(){return(-100);}
    public int getCartNumber() {
        return(cartNumber);
    }
}

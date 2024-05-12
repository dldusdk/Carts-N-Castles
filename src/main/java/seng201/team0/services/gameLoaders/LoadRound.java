package seng201.team0.services.gameLoaders;

import javafx.scene.image.ImageView;
import seng201.team0.models.carts.CartBasic;
import seng201.team0.models.towers.Projectile;
import seng201.team0.services.settings.Settings;


import java.util.ArrayList;
import java.util.Random;

public class LoadRound {

    private String difficulty;
    private int cartNumber;
    private int cartSpawnLocationX;
    private int cartSpawnLocationY;
    private ArrayList<ArrayList<Integer>>tileList;
    private ArrayList<ArrayList<Integer>>cartPath;
    private int roundNumber;
    private ImageView cartImage;
    private int roundCount;

    public LoadRound(int round,String difficultySetting, ImageView cartDefault, LevelLoader gridData,PathLoader gridPath,int cartAmount){
        roundNumber = round;

        tileList = gridData.getTileList();
        //cartPath = gridData.getPath();
        cartImage = cartDefault;
        cartPath = gridPath.getPath();
        cartNumber = cartAmount;

        difficulty = difficultySetting;
        Settings settings = new Settings(difficulty);

        cartSpawnLocationX = settings.getCartSpawnX();
        cartSpawnLocationY = settings.getCartSpawnY();
        Projectile p = new Projectile(1,2);

        startRound();


    }

    public void startRound(){ArrayList<CartBasic> cartList = new ArrayList<>();
        Random random = new Random();
        for (int i=0;i < 1; i++){
            int cartSpawnX = random.ints(-200,-100).findFirst().getAsInt();
            //System.out.println(cartSpawnX);
            double cartSpeed = Math.random();
            CartBasic cart = new CartBasic(cartImage,0,"bronze",10,cartPath,cartSpawnX);
            //loadCart(cartDefault);
        }


    }


    public int getCartNumber() {
        return(cartNumber);
    }
}

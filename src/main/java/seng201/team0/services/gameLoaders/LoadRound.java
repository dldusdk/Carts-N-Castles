package seng201.team0.services.gameLoaders;

import javafx.scene.image.ImageView;
import seng201.team0.models.carts.CartBasic;
import seng201.team0.models.towers.Projectile;
import seng201.team0.services.animation.CartAnimation;
import seng201.team0.services.settings.Settings;


import java.util.ArrayList;

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

    public LoadRound(int round,String difficultySetting, ImageView cartDefault, LevelLoader gridData,PathLoader gridPath){
        roundNumber = round;

        tileList = gridData.getTileList();
        //cartPath = gridData.getPath();
        cartImage = cartDefault;
        cartPath = gridPath.getPath();

        difficulty = difficultySetting;
        Settings settings = new Settings(difficulty);
        cartNumber = settings.getCartNumber();
        cartSpawnLocationX = settings.getCartSpawnX();
        cartSpawnLocationY = settings.getCartSpawnY();
        Projectile p = new Projectile(1,2);

        startRound();


    }

    public void startRound(){ArrayList<CartBasic> cartList = new ArrayList<>();
        for (int i=0;i < cartNumber; i++){
            CartBasic cart = new CartBasic(cartImage,0,"bronze",4,cartPath);

            //loadCart(cartDefault);
        }


    }


    public int getCartNumber() {
        return(0);
    }
}

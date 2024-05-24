package seng201.team0.services.gameLoaders;

import javafx.scene.image.ImageView;
import seng201.team0.models.carts.Cart;


import java.util.ArrayList;
import java.util.Random;

/**
 * This class is responsible for spawning carts in each round, given an argument of the number of each type
 * of cart that should be spawned
 * @author Gordon Homewood
 */

public class LoadRound {

    private String difficulty;
    private final ArrayList<ArrayList<Integer>>cartPath;
    private final ArrayList<ArrayList<Integer>>rotatePath;
    private final ImageView cartImage;
    private final ArrayList<Cart> cartList;
    private final int lowerSpawnBound = -1000;
    private final int upperSpawnBound = -100;
    private final int lowSpeedBound = 50;
    private final int upperSpeedBound = 150;


    /**
     * This constructor spawns the carts for the new round given in the GameController.
     * @param cartDefault default imageView so the carts can access the pane
     * @param loadedCartPath loaded cart's rotations and coordinates/movements
     * @param cartNum number of carts that should be spawned
     *
     * @author Gordon Homewood
     */
    public LoadRound(ImageView cartDefault, PathLoader loadedCartPath, ArrayList<Integer> cartNum){

        int bronzeCartNum = cartNum.get(0);
        int silverCartNum = cartNum.get(1);
        int goldCartNum = cartNum.get(2);

        cartImage = cartDefault;
        cartPath = loadedCartPath.getPath();
        rotatePath = loadedCartPath.getRotatePath();

        cartList = new ArrayList<>();

        loadCarts("Bronze", bronzeCartNum, "Art/Carts/bronzeCarts/bronzeEmpty.png");
        loadCarts("Silver", silverCartNum, "Art/Carts/silverCarts/silverEmpty.png");
        loadCarts("Gold", goldCartNum, "Art/Carts/goldCarts/goldEmpty.png");

    }

    /**
     * This method creates new carts for the number and type specified. It also gives them a random speed and
     * random spawn within a bound, to vary the player experience and stagger cart spawns.
     * @param type bronze,silver or gold type to pass through to cart object
     * @param cartNumber number of carts to be spawned
     * @param imageSource imageView so cart can be drawn on screen (able to access pane)
     *
     * @author Gordon Homewood
     */
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
            int cartSpawnX = random.ints(lowerSpawnBound,upperSpawnBound).findFirst().getAsInt();
            int cartSpeed = random.ints(lowSpeedBound,upperSpeedBound).findFirst().getAsInt();
            Cart cart = new Cart(cartImage,sizeDouble,type,cartSpeed,cartPath,rotatePath,cartSpawnX,imageSource);
            cartList.add(cart);

        }


    }

    /**
     * @return cart list of carts spawned in round
     * @author Gordon Homewood
     */
    public ArrayList<Cart> getCartList(){return(cartList);}
}

package seng201.team0.models.carts;

import javafx.animation.AnimationTimer;
import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import seng201.team0.services.animation.CartAnimation;
import seng201.team0.services.settings.Settings;

import java.util.ArrayList;


public class CartBasic {
    private int cartSpawnLocationX;
    private int cartSpawnLocationY;
    private ImageView cartImageSource;
    private ImageView cartObject;
    private TranslateTransition movement;
    private double speed ;
    private ArrayList<TranslateTransition> transitionList;
    private ArrayList<RotateTransition> rotationList;
    private int capicity = 0;

    //Might have to put in controller loop for animation and projectile logic
    private AnimationTimer collisionTimer = new AnimationTimer() {
        public void handle(long timestamp) {
                if (cartObject != null) {
                    //System.out.println("X: " + cartObject.getTranslateX());
                }
            }
    };


    public CartBasic(ImageView cartImageImport, int cartSize, String cartType, double cartSpeed, ArrayList<ArrayList<Integer>>cartPath,int cartX) {
        Settings settings = new Settings();
        //cartSpawnLocationX = settings.getCartSpawnX();
        cartSpawnLocationX = cartX;
        cartSpawnLocationY = settings.getCartSpawnY();

        collisionTimer.start();
        speed = cartSpeed;
        cartImageSource = cartImageImport;



        cartObject = loadCart();
        animateCart(cartPath,cartSpeed);

        //movement = new TranslateTransition(Duration.seconds(1), cartObject);
        //animateCart(cartObject);


    }


    public ImageView loadCart(){
        //Change to cart class
        Image source = new Image("Art/Asset Pack/Carts/SilverEmpty.png");
        ImageView cartImage = new ImageView(source);
        cartImage.setX(cartSpawnLocationX);
        cartImage.setY(cartSpawnLocationY);
        cartImage.setImage(source);
        System.out.println("========"+cartImageSource.getParent());
        ((Pane) cartImageSource.getParent()).getChildren().add(cartImage);
        return(cartImage);
    }

    public void animateCart(ArrayList<ArrayList<Integer>>cartPath,double cartSpeed) {
        //System.out.println(cartSpawnLocationX);
        CartAnimation cartAnimation = new CartAnimation(cartObject,cartPath,cartSpeed,cartSpawnLocationX);
        transitionList = cartAnimation.getAnimations();
        rotationList = cartAnimation.getRotations();

        transitionList.get(0).setByX(Math.abs(cartSpawnLocationX) + transitionList.get(0).getByX());


        SequentialTransition sequentialTransition = new SequentialTransition();
        for (int i=0; i < transitionList.size(); i++){
            sequentialTransition.getChildren().add(transitionList.get(i));
            if(i < rotationList.size()-1){
                sequentialTransition.getChildren().add(rotationList.get(i));
            }


        }


        //sequentialTransition.getChildren().addAll(transitionList);

        sequentialTransition.play();
        }

}

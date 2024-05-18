package seng201.team0.models.carts;

import javafx.animation.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import seng201.team0.services.animation.CartAnimation;
import seng201.team0.services.animation.GeneralAnimationKeyframing;
import seng201.team0.services.settings.Settings;

import java.util.ArrayList;
import java.util.Objects;


public class CartBasic {
    private int cartSpawnLocationX;
    private ImageView cartImageSource;
    private ImageView cartObject;
    private double speed ;
    private ArrayList<TranslateTransition> transitionList;
    private ArrayList<RotateTransition> rotationList;
    private String cartSource;
    private double cartSize;
    private double capacity;
    private double currentLoad=0;
    private String resourceType;
    private boolean destroyed = false;

    public CartBasic(ImageView cartImageImport, double cartSizeScale, String cartType, double cartSpeed,
                     ArrayList<ArrayList<Integer>>rotatePath,
                     ArrayList<ArrayList<Integer>>cartPath,int cartX,
                     String cartSourcePath) {

        resourceType = cartType;

        initializeCapacity();
        cartSize = cartSizeScale;
        cartSource = cartSourcePath;
        cartSpawnLocationX = cartX;
        speed = cartSpeed;
        cartImageSource = cartImageImport;

        cartObject = loadCart();
        animateCart(cartPath,rotatePath,speed);
    }

    private void initializeCapacity() {
        if(cartSize == 0.75){
            capacity = 1;
        }
        if(cartSize == 1){
            capacity = 2;
        }
        if(cartSize == 1.25){
            capacity = 3;
        }
    }

    public void setLoad(double damage){
        currentLoad += damage;
        updateImage();
        //checkStatus();
    }

    public double getLoadPercent() {
        return(currentLoad /capacity);
    }

    public void updateImage(){
        if(getLoadPercent() >= 0.5 && getLoadPercent() < 1){
            if(Objects.equals(resourceType, "bronze")){
            cartObject.setImage((new Image("Art/Asset Pack/Carts/bronzeCarts/bronzeHalf.png")));}
        }
        if(getLoadPercent() >= 1){
            if(Objects.equals(resourceType, "bronze")){
                cartObject.setImage((new Image("Art/Asset Pack/Carts/bronzeCarts/bronzeFull.png")));}}
    }

    public ImageView loadCart(){
        Image source = new Image(cartSource);
        ImageView cartImage = new ImageView(source);
        cartImage.setX(0);
        cartImage.setY(0);
        cartImage.setImage(source);
        cartImage.setScaleX(cartSize);
        cartImage.setScaleY(cartSize);
        ((Pane) cartImageSource.getParent()).getChildren().add(cartImage);
        return(cartImage);
    }

    public void animateCart(ArrayList<ArrayList<Integer>>cartPath,ArrayList<ArrayList<Integer>>rotatePath,double cartSpeed) {
        CartAnimation cartAnimation = new CartAnimation(cartObject,cartPath,rotatePath,cartSpeed,cartSpawnLocationX);
        transitionList = cartAnimation.getAnimations();
        rotationList = cartAnimation.getRotations();
        SequentialTransition sequentialTransition = new SequentialTransition();
        for (int i=0; i < transitionList.size(); i++){
            sequentialTransition.getChildren().add(transitionList.get(i));
            if(i < rotationList.size()-1){
                sequentialTransition.getChildren().add(rotationList.get(i));
                }
            }
        sequentialTransition.play();
        }
    public void despawn(){
        cartObject.setVisible(false);
        setDestroyed();

    }

    public void setDestroyed() {
        destroyed = true;
    }

    public boolean getDestroyed(){
        return(destroyed);
    }

    public void explode() {
        //Code is ugly, needs to be converted to a loop
        ImageView image = new ImageView();
        image.setX(965);
        image.setY(380);

        double scaleFactor = 1.25;
        image.setScaleX(scaleFactor);
        image.setScaleY(scaleFactor);

        ((Pane) cartImageSource.getParent()).getChildren().add(image);

        Image newImage1 = new Image("Art/Asset Pack/Effects/Explosion/explosionSplits/row-1-column-1.png");
        Image newImage2 = new Image("Art/Asset Pack/Effects/Explosion/explosionSplits/row-1-column-2.png");
        Image newImage3 = new Image("Art/Asset Pack/Effects/Explosion/explosionSplits/row-1-column-3.png");
        Image newImage4 = new Image("Art/Asset Pack/Effects/Explosion/explosionSplits/row-1-column-4.png");
        Image newImage5 = new Image("Art/Asset Pack/Effects/Explosion/explosionSplits/row-1-column-5.png");
        Image newImage6 = new Image("Art/Asset Pack/Effects/Explosion/explosionSplits/row-1-column-6.png");
        Image newImage7 = new Image("Art/Asset Pack/Effects/Explosion/explosionSplits/row-1-column-7.png");
        Image newImage8 = new Image("Art/Asset Pack/Effects/Explosion/explosionSplits/row-1-column-8.png");
        Image newImage9 = new Image("Art/Asset Pack/Effects/Explosion/explosionSplits/row-1-column-9.png");
        Duration delay = Duration.seconds(0.1);

        GeneralAnimationKeyframing.swapImagesWithDelay(image, newImage1, delay);
        GeneralAnimationKeyframing.swapImagesWithDelay(image, newImage2, delay.multiply(2));
        GeneralAnimationKeyframing.swapImagesWithDelay(image, newImage3, delay.multiply(3));
        GeneralAnimationKeyframing.swapImagesWithDelay(image, newImage4, delay.multiply(4));
        GeneralAnimationKeyframing.swapImagesWithDelay(image, newImage5, delay.multiply(5));
        GeneralAnimationKeyframing.swapImagesWithDelay(image, newImage6, delay.multiply(5));
        GeneralAnimationKeyframing.swapImagesWithDelay(image, newImage7, delay.multiply(5));
        GeneralAnimationKeyframing.swapImagesWithDelay(image, newImage8, delay.multiply(5));
        GeneralAnimationKeyframing.swapImagesWithDelay(image, newImage9, delay.multiply(5));

        GeneralAnimationKeyframing.addHideAnimation(image, delay.multiply(5).add(Duration.seconds(0.1)));


        cartObject.setVisible(false);
    }

    public ImageView getCartObject(){return(cartObject);}


}

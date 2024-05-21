package seng201.team0.models.carts;

import javafx.animation.*;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import seng201.team0.services.animation.CartAnimation;
import seng201.team0.services.animation.GeneralAnimationKeyframing;
import seng201.team0.services.settings.Settings;

import java.util.ArrayList;
import java.util.Objects;

import static java.lang.Math.abs;


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
    private double distanceTravelled;

    private double currentX=0;
    private double currentY=0;

    public CartBasic(ImageView cartImageImport, double cartSizeScale, String cartType, double cartSpeed,
                     ArrayList<ArrayList<Integer>>rotatePath,
                     ArrayList<ArrayList<Integer>>cartPath,int cartX,
                     String cartSourcePath) {

        resourceType = cartType;

        capacity = initializeCapacity();
        cartSize = cartSizeScale;
        cartSource = cartSourcePath;
        cartSpawnLocationX = cartX;
        speed = cartSpeed;
        cartImageSource = cartImageImport;

        cartObject = loadCart();
        animateCart(cartPath,rotatePath,speed);
    }

    private double initializeCapacity() {
        if(cartSize == 0.75){
            return(1);
        }
        if(cartSize == 1){
            return(2);
        }
        if(cartSize == 1.25){
            return(3);
        }
        return(1);
    }

    public void setLoad(double damage){
        currentLoad += damage;
        setDestroyed(true);
        updateImage();
        setDestroyed(false);
        //checkStatus();
    }

    public double getLoadPercent() {
        //System.out.println(capacity);
        return(currentLoad /capacity);
    }

    public void updateImage(){
        if(getLoadPercent() >= 0.5 && getLoadPercent() < 1){
            if(Objects.equals(resourceType, "bronze")){
                cartObject.setImage((new Image("Art/Asset Pack/Carts/bronzeCarts/bronzeHalf.png")));}
        }
        if(getLoadPercent() >= 0.85){
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
        setDestroyed(true);

    }

    public void setDestroyed(boolean hit) {
        destroyed = hit;
    }

    public boolean getDestroyed(){
        return(destroyed);
    }

    public void explode(int x, int y, boolean reachedEnd) {
        ImageView image = new ImageView();
        image.setX(x);
        image.setY(y);
        double scaleFactor = 1.25;
        image.setScaleX(scaleFactor);
        image.setScaleY(scaleFactor);
        ((Pane) cartImageSource.getParent()).getChildren().add(image);

        String[] imagePaths = {
                "Art/Asset Pack/Effects/Explosion/explosionSplits/row-1-column-1.png",
                "Art/Asset Pack/Effects/Explosion/explosionSplits/row-1-column-2.png",
                "Art/Asset Pack/Effects/Explosion/explosionSplits/row-1-column-3.png",
                "Art/Asset Pack/Effects/Explosion/explosionSplits/row-1-column-4.png",
                "Art/Asset Pack/Effects/Explosion/explosionSplits/row-1-column-5.png",
                "Art/Asset Pack/Effects/Explosion/explosionSplits/row-1-column-6.png",
                "Art/Asset Pack/Effects/Explosion/explosionSplits/row-1-column-7.png",
                "Art/Asset Pack/Effects/Explosion/explosionSplits/row-1-column-8.png",
                "Art/Asset Pack/Effects/Explosion/explosionSplits/row-1-column-9.png"
        };

        Duration delay = Duration.seconds(0.1);

        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setHue(1);
        colorAdjust.setSaturation(0.5);
        colorAdjust.setBrightness(0.5);
        colorAdjust.setContrast(0.5);

        for (int i = 0; i < imagePaths.length; i++) {
            Image newImage = new Image(imagePaths[i]);
            if (!reachedEnd) {
                image.setEffect(colorAdjust);
            }
            GeneralAnimationKeyframing.swapImagesWithDelay(image, newImage, delay.multiply(i + 1));
        }

        GeneralAnimationKeyframing.addHideAnimation(image, delay.multiply(imagePaths.length).add(Duration.seconds(0.1)));
        cartObject.setVisible(false);
    }

    public ImageView getCartObject(){return(cartObject);}

    public void incrementDistance(double distance){
        distanceTravelled += Math.abs(distance);
    }

    public double getDistance(){
        return(distanceTravelled);
    }


    public double getCurrentX(){
        return(currentX);
    }
    public double getCurrentY(){
        return(currentY);
    }

    public void setCurrentX(double x){
        currentX = x;
    }
    public void setCurrentY(double y){
        currentY = y;
    }
}

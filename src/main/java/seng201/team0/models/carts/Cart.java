package seng201.team0.models.carts;

import javafx.animation.*;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import seng201.team0.services.animation.CartAnimation;
import seng201.team0.services.animation.AnimationKeyframes;

import java.util.ArrayList;
import java.util.Objects;

import static java.lang.Math.abs;


/**
 * This class defines the Cart enemy type, integral to the game.
 *
 * <p>It takes in parameters of scale, type, speed and coordinates to create a Cart object, which
 * is then animated (handled by a seperate class). Here, the cart's charaistics like capacity, speed and
 * type provided the foundation for vital gameplay iteraction between the towers and carts</p>
 *
 * @author Gordon Homewood
 */

public class Cart {
    private final int cartSpawnLocationX;
    private final ImageView cartImageSource;
    private final ImageView cartObject;

    private final String cartSource; // Allows the cart to be drawn on screen
    private final double cartSize; // Determines cart's visual size
    private final double speed;
    private final double capacity;
    private final String resourceType;



    //Starts cart at empty, not destroyed
    private double currentLoad = 0;
    private boolean destroyed = false;

    /**
     * Constructs a new cart based on given parameters.
     * @param cartImageImport gives a way to access the pane so the cart can be drawn
     * @param cartSizeScale gives how much the image size should be changed, which is
     *                      also related to capacity
     * @param cartType either bronze,silver,gold which gives the cart a weakness to the towers of their
     *                 specific types
     * @param cartSpeed sets the carts travel speed, which will determine how fast the cart reaches the end of the
     *                  track
     * @param rotatePath gives direction of rotation for animation
     * @param cartPath gives coordinates for cart's travel across track
     * @param cartX defines a starting point for the cart
     * @param cartSourcePath defines what image should be used for the cart's spawn (related to type)
     *
     * @author Gordon Homewood
     */
    public Cart(ImageView cartImageImport, double cartSizeScale, String cartType, double cartSpeed,
                ArrayList<ArrayList<Integer>> rotatePath,
                ArrayList<ArrayList<Integer>> cartPath, int cartX,
                String cartSourcePath) {
        resourceType = cartType;
        cartSize = cartSizeScale;

        capacity = initializeCapacity(); // Calls method to scale visual size with resource capacity

        cartSource = cartSourcePath;
        cartSpawnLocationX = cartX;
        speed = cartSpeed;
        cartImageSource = cartImageImport;

        cartObject = loadCart(); //cartObject becomes a convenient way to access the cart's image properties related
        //to the screen.
        animateCart(cartPath, rotatePath);
    }


    /**
     * Gives a value for the capacity or 'health' of the cart which is
     * derived from its visual size to give the player a visual indication
     * of how many projectiles will be needed to fill it up
     *
     * @return cart's capacity
     * @author Gordon Homewood
     */
    private double initializeCapacity() {
        if (cartSize == 0.75) {
            return (2);
        }
        if (cartSize == 1) {
            return (3);
        }
        if (cartSize == 1.25) {
            return (4);
        }
        return (1);
    }


    /**
     * Updates the cart's load based on a projectiles' damage
     * Setting the destroyed means the cart is periodically hit,
     * so logic with the projectile enables it to despawn
     *
     * @param damage - determined by tower's and it's projectiles' damage
     *
     * @author Gordon Homewood
     */
    public void setLoad(double damage) {
        currentLoad += damage;
        setDestroyed(true);
        updateImage();
        setDestroyed(false);
    }

    /**
     * Returns the percentage of the carts' load, so it
     * can update its image if needed
     *
     * @return percentage of load
     */
    public double getLoadPercent() {

        if(capacity <= 0){
            //In case capacity is incorrect, return a
            //full cart
            return(1);
        }
        return (currentLoad / capacity);
    }


    /**
     * Updates the cart image based on load percent
     * This gives the player rough visual feedback on how much more a cart needs
     * to be hit to be destroyed. Also makes game more engaging
     *
     * @author Gordon Homewood
     */
    public void updateImage() {
        if (getLoadPercent() >= 0.5 && getLoadPercent() < 1) {
            if (Objects.equals(resourceType, "Bronze")) {
                cartObject.setImage((new Image("Art/Carts/bronzeCarts/bronzeHalf.png")));
            }
        }

        if (getLoadPercent() >= 0.8) {
            if (Objects.equals(resourceType, "Bronze")) {
                cartObject.setImage((new Image("Art/Carts/bronzeCarts/bronzeFull.png")));
            }
        }

        if (getLoadPercent() >= 0.5 && getLoadPercent() < 1) {
            if (Objects.equals(resourceType, "Silver")) {
                cartObject.setImage((new Image("Art/Carts/silverCarts/silverHalf.png")));
            }
        }
        if (getLoadPercent() >= 0.8) {
            if (Objects.equals(resourceType, "Silver")) {
                cartObject.setImage((new Image("Art/Carts/silverCarts/silverFull.png")));
            }
        }

        if (getLoadPercent() >= 0.5 && getLoadPercent() < 1) {
            if (Objects.equals(resourceType, "Gold")) {
                cartObject.setImage((new Image("Art/Carts/goldCarts/goldHalf.png")));
            }
        }
        if (getLoadPercent() >= 0.8) {
            if (Objects.equals(resourceType, "Gold")) {
                cartObject.setImage((new Image("Art/Carts/goldCarts/goldFull.png")));
            }
        }
    }

    /**
     * Method that builds the initial image for the cart
     * and draws it on the screen
     *
     * @return cart image to be animated
     *
     * @author Gordon Homewood
     */
    public ImageView loadCart() {
        Image source = new Image(cartSource);
        ImageView cartImage = new ImageView(source);
        cartImage.setX(0);
        cartImage.setY(0);
        cartImage.setImage(source);
        cartImage.setScaleX(cartSize);
        cartImage.setScaleY(cartSize);
        ((Pane) cartImageSource.getParent()).getChildren().add(cartImage); //Allows image to be drawn on screen
        return (cartImage);
    }

    /**
     * Method that creates new CartAnimation class, which is responsible for creating
     * modular animations for the cart.
     * When called, it plays that animations created in the CartAnimation class, in a sequence, which uses
     * the JavaFX SequentialTransition class to play back the animation in order.
     *
     * @cartPath which feeds the carts' destinations into the animation class
     * @rotatePath which feeds the carts' rotations and orientations into the animation
     *
     * @author Gordon Homewood
     */
    public void animateCart(ArrayList<ArrayList<Integer>> cartPath, ArrayList<ArrayList<Integer>> rotatePath) {

        CartAnimation cartAnimation = new CartAnimation(cartObject, cartPath, rotatePath, speed, cartSpawnLocationX);

        ArrayList<TranslateTransition> transitionList = cartAnimation.getAnimations();
        ArrayList<RotateTransition> rotationList = cartAnimation.getRotations();
        SequentialTransition sequentialTransition = new SequentialTransition();
        for (int i = 0; i < transitionList.size(); i++) {
            sequentialTransition.getChildren().add(transitionList.get(i));
            if (i < rotationList.size() - 1) {
                sequentialTransition.getChildren().add(rotationList.get(i));
            }
        }
        sequentialTransition.play();
    }

    /**
     * This method is responsible for removing the cart from the screen, which
     * is useful when it is full, the cart reaches the end, or the game is finishes
     *
     * @author Gordon Homewood
     */
    public void despawn() {
        cartObject.setVisible(false);
        setDestroyed(true);
    }

    /**
     * @return resource type (bronze,silver,gold). Useful for iteractions with towers and projectiles
     *
     * @author Gordon Homewood
     */
    public String getResourceType()

    {
        return (resourceType);
    }

    /**
     * If the cart is hit, destroyed is true, which is useful for
     * switching states of the cart depending on how much it's damaged
     * by a projectile
     *
     * @param hit to switch state
     *
     * @author Gordon Homewood
     */
    public void setDestroyed(boolean hit) {

        destroyed = hit;
    }

    /**
     * Gets state of cart if full or not
     * @return destroyed states
     * @author Gordon Homewood
     */
    public boolean getDestroyed() {return (destroyed);}

    /**
     * This method provides the explosion animation for the cart. It uses a more general class,
     * AnimationKeyframes to animate the explosion across a duration.
     *
     * @param x determines the x coordinate of the animation
     * @param y determines the y coordinate of the animation
     *
     * @param reachedEnd if true, will play a normal explosion, but if false, will
     *                   play a blue explosion, this difference provides vital feedback to the
     *                   player, so they can know if the cart reached the end (where it explodes
     *                   normally) or their towers managed to destroy it (where no lives will be
     *                   reduced)
     *
     * @author Gordon Homewood
     */
    public void explode(int x, int y, boolean reachedEnd) {
        ImageView image = new ImageView();
        image.setX(x);
        image.setY(y);
        double scaleFactor = 1.25;
        image.setScaleX(scaleFactor);
        image.setScaleY(scaleFactor);
        ((Pane) cartImageSource.getParent()).getChildren().add(image);

        //Image paths to explosion frames
        String[] imagePaths = {
                "Art/Effects/Explosion/explosionSplits/row-1-column-1.png",
                "Art/Effects/Explosion/explosionSplits/row-1-column-2.png",
                "Art/Effects/Explosion/explosionSplits/row-1-column-3.png",
                "Art/Effects/Explosion/explosionSplits/row-1-column-4.png",
                "Art/Effects/Explosion/explosionSplits/row-1-column-5.png",
                "Art/Effects/Explosion/explosionSplits/row-1-column-6.png",
                "Art/Effects/Explosion/explosionSplits/row-1-column-7.png",
                "Art/Effects/Explosion/explosionSplits/row-1-column-8.png",
                "Art/Effects/Explosion/explosionSplits/row-1-column-9.png"
        };
        Duration delay = Duration.seconds(0.1);

        //Color adjust for blue color
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setHue(1);
        colorAdjust.setSaturation(0.5);
        colorAdjust.setBrightness(0.5);
        colorAdjust.setContrast(0.5);

        for (int i = 0; i < imagePaths.length; i++) {
            Image newImage = new Image(imagePaths[i]);
            if (!reachedEnd) {
                //Plays blue coloured explosion for player feedback if
                //their towers managed to adjust the carts
                image.setEffect(colorAdjust);
            }
            //multiplies delay to play frames sequentially
            AnimationKeyframes.swapImagesWithDelay(image, newImage, delay.multiply(i + 1));
        }

        AnimationKeyframes.addHideAnimation(image, delay.multiply(imagePaths.length).add(Duration.seconds(0.1)));
        cartObject.setVisible(false);
    }

    /**
     * @return cartObject (imageView of the cart), useful for getting
     * screen coordinates and applying effects if needed.
     *
     * @Author Gordon Homewood
     */
    public ImageView getCartObject() {return (cartObject);}

}

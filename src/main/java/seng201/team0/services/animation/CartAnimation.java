package seng201.team0.services.animation;

import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Objects;

/**
 * This class is responsible for outsoyrcing the animation from the cart class and gives a list of
 * transitions to be played in a sequence from the carts' parameters.
 *
 * @author Gordon Homewood
 */
public class CartAnimation {
    private final ArrayList<TranslateTransition> transitionList;
    private final ArrayList<ArrayList<Integer>>rotationDirectionList;
    private final ArrayList<ArrayList<Integer>>cartPathList;
    private final ArrayList<RotateTransition> rotationList;
    private final double speed;
    private final ImageView cartSource;
    private final int cartSpawn;
    private int rotateCount = 0;

    /**
     * Creates a cart animation object, receiving parameters based on the cart it's animating.
     *
     * @param cart ImageView of the cart which is drawn onto the pane and is animated on the screen.
     * @param rotationPaths Rotation paths (direction and orientation) 2D int array read from a file
     * @param cartPaths Cart paths (x and y coords) 2D int array read from a file
     * @param cartSpeed Cart speed parameter which will determine the speed of the animation and thus, how far
     *                  the cart traverses across the screen.
     * @param cartSpawnCoord Starting point of the animation based on where the cart starts.
     */
    public CartAnimation(ImageView cart, ArrayList<ArrayList<Integer>>rotationPaths,
                         ArrayList<ArrayList<Integer>>cartPaths,double cartSpeed,int cartSpawnCoord){
        cartSpawn = cartSpawnCoord;

        cartSource = cart;
        cart.setTranslateX(0);
        cart.setTranslateY(0);
        speed = cartSpeed;
        transitionList = new ArrayList<>();
        rotationList = new ArrayList<>();

        rotationDirectionList = rotationPaths;
        cartPathList = cartPaths;

        buildTransitionList();
    }


    /**
     * This method reads a 2D array based on the provided 2D array of the coordinate list
     * given in the constructor. It calls the method CreateNewAnimation after it accesses each coordinate, giving it
     * a type and initial coordinates.
     *
     * @author Gordon Homewood
     */
        public void buildTransitionList() {

        for (int i = 0; i < cartPathList.size(); i++) {
            ArrayList<Integer> currentRow = cartPathList.get(i);
            if (i + 1 < cartPathList.size()) {
                ArrayList<Integer> nextRow = cartPathList.get(i + 1);
                for (int j = 0; j < currentRow.size(); j++) {
                    if (j < nextRow.size() && currentRow.get(j).equals(nextRow.get(j))) {
                        if (j == 0) {
                            createNewAnimation("y", currentRow.get(1), nextRow.get(1));
                        } else if (j == 1) {
                            createNewAnimation("x", currentRow.get(0), nextRow.get(0));

                        }
                    }
                }
            }
        }
    }


    /**
     * This method adds a new transition and rotatation to the transition list and computes the correct times,
     * coordinates and orientation of each cart based on the cart's speed and given coordinates.
     * For the purposes of the game, the animation can only either be in X or Y, as the cart does not have to
     * move diagonally.
     *
     * @param type should be either x or y
     * @param firstCoord gives the initial start of the translateTransition
     * @param secondCoord gives the end goal of the translateTransition
     *
     * @author Gordon Homewood
     */
    public void createNewAnimation(String type, int firstCoord, int secondCoord){

        if(transitionList.isEmpty()){
            firstCoord = cartSpawn;
        }

        TranslateTransition transition = new TranslateTransition();
        transition.setInterpolator(Interpolator.LINEAR); //Removes default speed ramp up on animation

        //Calculate distance based on physics t=d/v
        double distance = Math.abs(secondCoord - firstCoord);
        transition.setDuration(Duration.seconds(getTime(distance)));
        transition.setNode(cartSource);

        // Rotation between each TranslateTransition, as the cart hits corner
        RotateTransition rotation = new RotateTransition();
        rotation.setNode(cartSource);
        rotation.setByAngle(rotationDirectionList.get(rotateCount).getFirst());
        rotation.setDuration(Duration.seconds(0.2)); //rotation is constant for all carts
        rotationList.add(rotation);

        rotateCount++;
        if(Objects.equals(type, "x")){
            transition.setFromX(firstCoord);
            transition.setToX(secondCoord);
        }
        if(Objects.equals(type, "y")){

            transition.setFromY(firstCoord);
            transition.setToY(secondCoord);
        }
        transitionList.add(transition);
    }


    /**
     * This method returns distance/speed to get how long the cart should travel for to
     * maintain consistent speed
     *
     * @param distance absolutle distance cart is travelling
     * @return distance / cart's speed
     *
     *  * @author Gordon Homewood
     */
    public double getTime(double distance){
        //Returns time = distance/speed
        return(distance / speed);
    }


    /**
     * Getter method to return the list of translate Transitions for the cart
     * @return list of translate transitions.
     *
     * @author Gordon Homewood
     */
    public ArrayList<TranslateTransition> getAnimations(){return(transitionList);}


    /**
     * Getter method to return the list of rotate Transitions for the cart
     * @return list of rotate transitions.
     *
     * @author Gordon Homewood
     */
    public ArrayList<RotateTransition> getRotations() {return(rotationList);}
}



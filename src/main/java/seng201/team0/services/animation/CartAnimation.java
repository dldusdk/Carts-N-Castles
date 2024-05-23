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
     * @param cart
     * @param rotationPaths
     * @param cartPaths
     * @param cartSpeed
     * @param cartSpawnCoord
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

    public void buildTransitionList() {

        for (int i = 0; i < cartPathList.size(); i++) {
            ArrayList<Integer> currentRow = cartPathList.get(i);
            if (i + 1 < cartPathList.size()) {
                ArrayList<Integer> nextRow = cartPathList.get(i + 1);
                for (int j = 0; j < currentRow.size(); j++) {
                    if (j < nextRow.size() && currentRow.get(j).equals(nextRow.get(j))) {
                        if (j == 0) {
                            createNewAnimation("y", currentRow.get(j+1), nextRow.get(j+1));
                        } else if (j == 1) {
                            createNewAnimation("x", currentRow.get(j-1), nextRow.get(j-1));

                        }
                    }
                }
            }
        }
    }


    public void createNewAnimation(String type, int firstCoord, int secondCoord){
        if(transitionList.isEmpty()){
            firstCoord = cartSpawn;
        }

        TranslateTransition transition = new TranslateTransition();
        transition.setInterpolator(Interpolator.LINEAR); //Removes default speed ramp up on animation

        double distance = Math.abs(secondCoord - firstCoord);
        transition.setDuration(Duration.seconds(getTime(distance)));
        transition.setNode(cartSource);

        RotateTransition rotation = new RotateTransition();
        rotation.setNode(cartSource);
        rotation.setByAngle(rotationDirectionList.get(rotateCount).getFirst());
        rotation.setDuration(Duration.seconds(0.2));
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

    public double getTime(double distance){
        //Returns time = distance/speed
        return(distance / speed);
    }


    public ArrayList<TranslateTransition> getAnimations(){
        return(transitionList);
    }


    public ArrayList<RotateTransition> getRotations() {return(rotationList);}
}



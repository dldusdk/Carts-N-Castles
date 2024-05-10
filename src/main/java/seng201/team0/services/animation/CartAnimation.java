package seng201.team0.services.animation;

import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Objects;

public class CartAnimation {
    private ArrayList<TranslateTransition> transitionList;
    private ArrayList<RotateTransition> rotationList;
    private ArrayList<ArrayList<Integer>>cartPathList;
    private int waypointNumnber;
    private double speed;
    private ImageView cartSource;

    public CartAnimation(ImageView cart, ArrayList<ArrayList<Integer>>cartPaths,double cartSpeed){
        cartSource = cart;
        speed = cartSpeed;
        transitionList = new ArrayList<>();
        rotationList = new ArrayList<>();
        cartPathList = cartPaths;

        buildTransitionList();
    }

    public void buildTransitionList() {
        System.out.println(cartPathList);

        for (int i = 0; i < cartPathList.size(); i++) {
            ArrayList<Integer> currentRow = cartPathList.get(i);
            if (i + 1 < cartPathList.size()) {
                ArrayList<Integer> nextRow = cartPathList.get(i + 1);
                for (int j = 0; j < currentRow.size(); j++) {
                    if (j < nextRow.size() && currentRow.get(j).equals(nextRow.get(j))) {
                        if (j == 0) {
                            TranslateTransition transition = createNewAnimation("y", currentRow.get(j+1), nextRow.get(j+1));
                        } else if (j == 1) {
                            createNewAnimation("x", currentRow.get(j-1), nextRow.get(j-1));

                        }
                    }
                }
            }
        }
    }


    public TranslateTransition createNewAnimation(String type, int firstCoord, int secondCoord){
        System.out.println("First "+firstCoord+" Second "+secondCoord);
        double distance = Math.abs(secondCoord - firstCoord);
        TranslateTransition transition = new TranslateTransition();
        transition.setNode(cartSource);
        transition.setDuration(Duration.seconds(getTime(distance)));

        RotateTransition rotation = new RotateTransition();
        rotation.setNode(cartSource);

        if(Objects.equals(type, "x")){
            rotation.setByAngle(90);
            rotation.setDuration(Duration.seconds(1));
            rotationList.add(rotation);
            transition.setByX(secondCoord);
        }
        if(Objects.equals(type, "y")){
            rotation.setByAngle(90);
            rotation.setDuration(Duration.seconds(1));
            rotationList.add(rotation);
            transition.setByY(secondCoord);
        }

        System.out.println(transition.getDuration());

        transitionList.add(transition);
        //transitionList.add(rotateTransition);
        return(transition);
    }

    public double getTime(double distance){
        //Returns time = distance/speed
        return(speed);
    }



    public ArrayList<TranslateTransition> getAnimations(){
        return(transitionList);
    }


    public ArrayList<RotateTransition> getRotations() {return(rotationList);}
}

package seng201.team0.services.animation;

import javafx.animation.Interpolator;
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
    private double speed;
    private ImageView cartSource;
    private int count = 0;
    private int cartSpawnX;

    public CartAnimation(ImageView cart, ArrayList<ArrayList<Integer>>cartPaths,double cartSpeed,int cartX){
        cartSpawnX = cartX;
        cartSource = cart;
        speed = 100;
        transitionList = new ArrayList<>();
        rotationList = new ArrayList<>();
        cartPathList = cartPaths;

        buildTransitionList();
    }

    public void buildTransitionList() {
        //System.out.println(cartPathList);

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
        System.out.println("=======================");
        double value = Math.abs(firstCoord-secondCoord);
        if(!transitionList.isEmpty()){System.out.println("Experimenting "+transitionList.getLast().getFromX());}

        ArrayList<Integer> coords = new ArrayList<>();

        coords.add(Math.abs(350 + Math.abs(cartSpawnX)));
        coords.add(150);
        coords.add(360);
        coords.add(400);
        coords.add(450);


        double distance = coords.get(count);
        System.out.println("DISTANCE "+distance);
       // System.out.println("Distance: "+distance);
        TranslateTransition transition = new TranslateTransition();
        transition.setInterpolator(Interpolator.LINEAR);
        transition.setDuration(Duration.seconds(getTime(distance)));
        transition.setNode(cartSource);

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
        //System.out.println("First "+firstCoord+" Second "+secondCoord);
        //System.out.println(transition.getDuration());

        transitionList.add(transition);
        count++;
        //transitionList.add(rotateTransition);
    }

    public double getTime(double distance){
        //Returns time = distance/speed
        //System.out.println("TIME(SECONDS)" +distance/speed);
        ///System.out.println("DISTANCE: "+ distance);
        //System.out.println("SPEED: "+ speed);
        //.out.println("====================================================");
        return(distance / speed);
    }



    public ArrayList<TranslateTransition> getAnimations(){
        return(transitionList);
    }


    public ArrayList<RotateTransition> getRotations() {return(rotationList);}
}

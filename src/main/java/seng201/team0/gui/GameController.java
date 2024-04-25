package seng201.team0.gui;

import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;
import seng201.team0.services.CounterService;

/**
 * Controller for the main.fxml window
 * @author seng201 teaching team
 */
public class GameController {

    @FXML
    private ImageView cart1;
    /**
     * Initialize the window
     *
     * @param stage Top level container for this window
     */
    public void init(Stage stage) {
        TranslateTransition translate = new TranslateTransition();
        translate.setNode(cart1);
        translate.setDuration(Duration.millis(2500));
        translate.setByX(320);


        //Rotations for animation
        RotateTransition rotate = new RotateTransition(Duration.millis(300),cart1);
        rotate.setByAngle(90);
        RotateTransition rotate1 = new RotateTransition(Duration.millis(300),cart1);
        rotate1.setByAngle(90);
        RotateTransition rotate2 = new RotateTransition(Duration.millis(300),cart1);
        rotate2.setByAngle(90);
        RotateTransition rotate3 = new RotateTransition(Duration.millis(300),cart1);
        rotate3.setByAngle(90);

        //Translations for animation
        TranslateTransition translate1 = new TranslateTransition();
        translate1.setNode(cart1);
        translate1.setDuration(Duration.millis(2500));
        translate1.setByY(250);
        TranslateTransition translate2 = new TranslateTransition();
        translate2.setNode(cart1);
        translate2.setRate(0.1);
        translate2.setByX(635);
        TranslateTransition translate3 = new TranslateTransition();
        translate3.setNode(cart1);
        translate3.setRate(0.1);
        translate3.setByY(-130);
        TranslateTransition translate4 = new TranslateTransition();
        translate4.setNode(cart1);
        translate4.setRate(0.1);
        translate4.setByX(400);


        SequentialTransition sequentialTransition = new SequentialTransition(cart1,translate,rotate,translate1,rotate1, translate2,rotate2,translate3,rotate3,translate4);

        sequentialTransition.play();


    }
}



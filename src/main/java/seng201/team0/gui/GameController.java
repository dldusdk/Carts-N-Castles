package seng201.team0.gui;

import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
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
        loadNewImage(100);
        loadNewImage(300);

    }
    public void loadNewImage(int coordinates) {
        //Load and set new cart image
        Image cart10 = new Image("Art/Asset Pack/Carts/Cart1Concept.png");
        ImageView cartImage = new ImageView(cart10);
        cartImage.setX(coordinates);
        cartImage.setImage(cart10);

        //This makes all images share a parent with the imported ImageView cart1, dynamically adding to FXML
        ((Pane) cart1.getParent()).getChildren().add(cartImage);
    }
}



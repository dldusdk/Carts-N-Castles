package seng201.team0.gui;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import seng201.team0.models.Cart;
import seng201.team0.services.LevelLoader;

import java.util.ArrayList;

/**
 * Controller for the main.fxml window
 * @author seng201 teaching team
 */
public class GameController {

    @FXML
    private ImageView trackDefualt;
    @FXML
    private Button roundButton;

    private int round = 0;
    private boolean roundState;
    private int cartNumber;

    /**
     * Initialize the window
     *
     * @param stage Top level container for this window
     */
    public void init(Stage stage) {
        roundState = false;
        cartNumber = 10;

        roundButton.setText(String.valueOf("Play: "+round));
        System.out.println(round);
        LevelLoader level = new LevelLoader(trackDefualt, "src/main/resources/levelCSV/Level1/Level1Concept_Track.csv");

    }

    @FXML
    public void roundStart(ActionEvent event){
        //Export this to a round class?
        //Create list of carts to be generated and handle stuff

        ArrayList<Cart> cartList = new ArrayList<>();
        for (int i=0;i <= cartNumber; i++){
            Cart cart = new Cart();
        }
        for(Cart cart: cartList){
            //create a cart
        }

        roundState = true;
        round++;
    }


}




package seng201.team0.gui;


import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import seng201.team0.services.LevelLoader;

/**
 * Controller for the main.fxml window
 * @author seng201 teaching team
 */
public class GameController {

    @FXML
    public ImageView trackDefualt;

    /**
     * Initialize the window
     *
     * @param stage Top level container for this window
     */
    public void init(Stage stage) {

        LevelLoader level = new LevelLoader(trackDefualt, "src/main/resources/levelCSV/Level1/Level1Concept_Track.csv");


    }
    public void loadNewImage(int coordinates) {
        //Load and set new cart image
        //Image cart10 = new Image("resources/Art/Asset Pack/Terrain/Ground/splitTerrain/row-0-column-4.png");
        //ImageView cartImage = new ImageView(cart10);
        //cartImage.setX(coordinates);
        //cartImage.setImage(cart10);

        //This makes all images share a parent with the imported ImageView cart1, dynamically adding to FXML
        //((Pane) trackDefualt.getParent()).getChildren().add(cartImage);
    }
}



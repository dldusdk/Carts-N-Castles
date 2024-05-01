package seng201.team0.services;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import seng201.team0.models.FileReader;

import java.util.ArrayList;

public class LevelLoader {

    public LevelLoader(ImageView image, String level){
        FileReader loadCSV = new FileReader(level);
        ArrayList<ArrayList<Integer>> tileList = loadCSV.getList();
        Settings settings = new Settings();

        int initialX = settings.getIntialX();
        int tileSize = settings.getTilePixelSize();

        int column = 0;
        int row = 0;


        for(ArrayList<Integer> innerList: tileList){
            row = 0;
            for(int num: innerList){
                loadNewImage((column * tileSize + initialX), (row * tileSize), image);
                row++;
            }
            column++;
        }

        }



    public void loadNewImage(int coordY, int coordX,ImageView image){
        //Load and set new cart image
        Image source = new Image("Art/Asset Pack/Terrain/Ground/splitTerrain/row-2-column-2.png");
        ImageView trackImage = new ImageView(source);
        trackImage.setX(coordX);
        trackImage.setY(coordY);
        trackImage.setImage(source);
        //This makes all images share a parent with the imported ImageView trackImage, dynamically adding to FXML
        ((Pane) image.getParent()).getChildren().add(trackImage);
    }

    public String getTileType(int id){
        if (id == -1){
            return("Art/Asset Pack/Terrain/Ground/splitTerrain/row-0-column-4.png");
        }
        return null;
    }
}

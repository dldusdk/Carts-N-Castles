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

        String grassPath = "Art/Asset Pack/Terrain/Ground/splitTerrain/row-2-column-2.png";
        String trackPathRight = "Art/Asset Pack/Terrain/Ground/splitTerrain/row-4-column-7.png";
        String trackPathTurnDown = "Art/Asset Pack/Terrain/Ground/splitTerrain/row-1-column-8.png";
        String trackPathDown = "Art/Asset Pack/Terrain/Ground/splitTerrain/row-2-column-9.png";
        String trackPathTurnDownRight = "Art/Asset Pack/Terrain/Ground/splitTerrain/row-3-column-6.png";
        String trackPathTurnUpRight =  "Art/Asset Pack/Terrain/Ground/splitTerrain/row-3-column-8.png";
        String trackPathTurnDownLR =  "Art/Asset Pack/Terrain/Ground/splitTerrain/row-1-column-6.png";


        for(ArrayList<Integer> innerList: tileList){
            row = 0;
            for(int num: innerList){
                if (num == 36){
                    loadNewImage((column * tileSize + initialX), (row * tileSize), image,grassPath);
                    loadNewImage((column * tileSize + initialX), (row * tileSize), image,trackPathRight);
                }
                if (num == 7){
                    loadNewImage((column * tileSize + initialX), (row * tileSize), image,grassPath);
                    loadNewImage((column * tileSize + initialX), (row * tileSize), image,trackPathTurnDown);
                }
                if (num == 18){
                    loadNewImage((column * tileSize + initialX), (row * tileSize), image,grassPath);
                    loadNewImage((column * tileSize + initialX), (row * tileSize), image,trackPathDown);
                }
                if (num == 25){
                    loadNewImage((column * tileSize + initialX), (row * tileSize), image,grassPath);
                    loadNewImage((column * tileSize + initialX), (row * tileSize), image,trackPathTurnDownRight);
                }
                if (num == 27){
                    loadNewImage((column * tileSize + initialX), (row * tileSize), image,grassPath);
                    loadNewImage((column * tileSize + initialX), (row * tileSize), image,trackPathTurnUpRight);
                }
                if (num == 5){
                    loadNewImage((column * tileSize + initialX), (row * tileSize), image,grassPath);
                    loadNewImage((column * tileSize + initialX), (row * tileSize), image,trackPathTurnDownLR);
                }
                if (num == -1){
                    loadNewImage((column * tileSize + initialX), (row * tileSize), image,grassPath);
                }

                row++;
            }
            column++;
        }

        }



    public void loadNewImage(int coordY, int coordX,ImageView image,String path){
        //Load and set new cart image
        Image source = new Image(path);
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

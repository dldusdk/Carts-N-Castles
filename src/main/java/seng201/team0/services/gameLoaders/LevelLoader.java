package seng201.team0.services.gameLoaders;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import seng201.team0.services.settings.Settings;
import seng201.team0.services.fileReaders.FileReader;

import java.util.ArrayList;

public class LevelLoader {
    private ArrayList<ArrayList<Integer>>tileList;
    private ArrayList<ArrayList<Integer>>waypointsList;
    private ArrayList<ArrayList<Integer>>decorationList;
    private int levelTilesWidth;
    private int levelTilesHeight;
    private int tileSize;
    private int initialX;
    private ImageView base1Image;



    public LevelLoader(ImageView image, String level, String decor){
        base1Image = image;
        //Gets settings of spawn
        Settings settings = new Settings();
        initialX = settings.getIntialX();
        tileSize = settings.getTilePixelSize();

        //Builds a 2D array for locations of tiles from files
        levelTilesWidth = settings.getTileWidth();
        levelTilesHeight = settings.getTileHeight();

        FileReader loadCSV = new FileReader(level,levelTilesWidth,levelTilesHeight);
        tileList = loadCSV.getList();
        FileReader loadDecoration = new FileReader(decor,levelTilesWidth,levelTilesHeight);
        decorationList = loadDecoration.getList();
        loadLevel();
        loadDecorations();
        }

    private void loadLevel() {
        //Set a bunch of image sources
        String grassPath = "Art/Asset Pack/Terrain/Ground/splitTerrain/row-2-column-2.png";
        String trackPathRight = "Art/Asset Pack/Terrain/Ground/splitTerrain/row-4-column-7.png";
        String trackPathTurnDown = "Art/Asset Pack/Terrain/Ground/splitTerrain/row-1-column-8.png";
        String trackPathDown = "Art/Asset Pack/Terrain/Ground/splitTerrain/row-2-column-9.png";
        String trackPathTurnDownRight = "Art/Asset Pack/Terrain/Ground/splitTerrain/row-3-column-6.png";
        String trackPathTurnUpRight =  "Art/Asset Pack/Terrain/Ground/splitTerrain/row-3-column-8.png";
        String trackPathTurnDownLR =  "Art/Asset Pack/Terrain/Ground/splitTerrain/row-1-column-6.png";

        //build tiles using coordinates and images
        int column = 0;
        for(ArrayList<Integer> innerList: tileList){
            int row = 0;
            ArrayList<Integer> arrayList = new ArrayList<>(2);
            for(int num: innerList){
                if (num == 36){
                    loadNewImage((column * tileSize + initialX), (row * tileSize), base1Image,grassPath);
                    loadNewImage((column * tileSize + initialX), (row * tileSize), base1Image,trackPathRight);
                }
                if (num == 7){
                    loadNewImage((column * tileSize + initialX), (row * tileSize), base1Image,grassPath);
                    loadNewImage((column * tileSize + initialX), (row * tileSize), base1Image,trackPathTurnDown);
                }
                if (num == 18){
                    loadNewImage((column * tileSize + initialX), (row * tileSize), base1Image,grassPath);
                    loadNewImage((column * tileSize + initialX), (row * tileSize), base1Image,trackPathDown);
                }
                if (num == 25){
                    loadNewImage((column * tileSize + initialX), (row * tileSize), base1Image,grassPath);
                    loadNewImage((column * tileSize + initialX), (row * tileSize), base1Image,trackPathTurnDownRight);
                }
                if (num == 27){
                    loadNewImage((column * tileSize + initialX), (row * tileSize), base1Image,grassPath);
                    loadNewImage((column * tileSize + initialX), (row * tileSize), base1Image,trackPathTurnUpRight);
                }
                if (num == 5){
                    loadNewImage((column * tileSize + initialX), (row * tileSize), base1Image,grassPath);
                    loadNewImage((column * tileSize + initialX), (row * tileSize), base1Image,trackPathTurnDownLR);
                }
                if (num == -1){
                    loadNewImage((column * tileSize + initialX), (row * tileSize), base1Image,grassPath);
                }

                row++;
            }
            column++;
        }

    }

    private void loadDecorations(){
        String rockWallPath = "Art/Asset Pack/Deco/09.png";

        int column = 0;
        for(ArrayList<Integer> innerList: decorationList){
            int row = 0;
            ArrayList<Integer> arrayList = new ArrayList<>(2);
            for(int num: innerList){
                if (num == 9){
                    loadNewImage((column * tileSize + initialX), (row * tileSize), base1Image,rockWallPath);
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
        //This makes all images share a parent with the imported ImageView trackImage, dynamically images adding to FXML
        ((Pane) image.getParent()).getChildren().add(trackImage);
    }


    public String getTileType(int id){
        if (id == -1){
            return("Art/Asset Pack/Terrain/Ground/splitTerrain/row-0-column-4.png");
        }
        return null;
    }

    public ArrayList<ArrayList<Integer>> getTileList(){return (tileList);}


}

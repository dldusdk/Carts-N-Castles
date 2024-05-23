package seng201.team0.services.gameLoaders;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import seng201.team0.services.settings.Settings;
import seng201.team0.services.fileReaders.FileReader;

import java.util.ArrayList;

public class LevelLoader {
    private ArrayList<ArrayList<Integer>>tileList;
    private ArrayList<ArrayList<Integer>>decorationList;
    // List to store the coordinates (x, y) of the tiles with value -1
    private ArrayList<Integer> invalidCoordsListX = new ArrayList<>();
    private ArrayList<Integer> invalidCoordsListY  = new ArrayList<>();
    private static final double gamePaneWidth = 1152;
    private static final double gamePaneHeight = 768;

    private int levelTilesWidth;
    private int levelTilesHeight;
    private int tileSize;
    private int initialX;
    private ImageView base1Image;


    public LevelLoader(ImageView image, String level, String decor){
        base1Image = image;
        //Gets settings of spawn, dependent on which level is chosen
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
        String grassPath = "Art/Terrain/Ground/splitTerrain/row-2-column-2.png";
        String trackPathRight = "Art/Terrain/Ground/splitTerrain/row-4-column-7.png";
        String trackPathTurnDown = "Art/Terrain/Ground/splitTerrain/row-1-column-8.png";
        String trackPathDown = "Art/Terrain/Ground/splitTerrain/row-2-column-9.png";
        String trackPathTurnDownRight = "Art/Terrain/Ground/splitTerrain/row-3-column-6.png";
        String trackPathTurnUpRight = "Art/Terrain/Ground/splitTerrain/row-3-column-8.png";
        String trackPathTurnDownLR = "Art/Terrain/Ground/splitTerrain/row-1-column-6.png";


        //Build tiles using coordinates and images
        int column = 0;
        for(ArrayList<Integer> innerList: tileList){
            int row = 0;
            ArrayList<Integer> arrayList = new ArrayList<>(2);
            for(int num: innerList){
                int y = column * tileSize + initialX;
                int x = row * tileSize;

                if (num == 36){
                    loadNewImage((y), (x), base1Image,grassPath);
                    loadNewImage((column * tileSize + initialX), (row * tileSize), base1Image,trackPathRight);
                }
                if (num == 7){
                    loadNewImage((y), (x), base1Image,grassPath);
                    loadNewImage((column * tileSize + initialX), (row * tileSize), base1Image,trackPathTurnDown);
                }
                if (num == 18){
                    loadNewImage((y), (x), base1Image,grassPath);
                    loadNewImage((y), (x), base1Image,trackPathDown);
                }
                if (num == 25){
                    loadNewImage((y), (x), base1Image,grassPath);
                    loadNewImage((y), (x), base1Image,trackPathTurnDownRight);
                }
                if (num == 27){
                    loadNewImage((y), (x), base1Image,grassPath);
                    loadNewImage((y), (x), base1Image,trackPathTurnUpRight);
                }
                if (num == 5){
                    loadNewImage((y), (x), base1Image,grassPath);
                    loadNewImage((y), (x), base1Image,trackPathTurnDownLR);
                }
                if (num == -1){
                    loadNewImage((y), (x), base1Image,grassPath);
                }
                if (num != -1){
                    invalidCoordsListX.add(x);
                    invalidCoordsListY.add(y);
                }
                row++;
            }
            column++;
        }
    }

    private void loadDecorations(){
        String rockWallPath = "Art/Deco/09.png";

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
        //Load and set new cart image's parameters
        Image source = new Image(path);
        ImageView trackImage = new ImageView(source);
        trackImage.setX(coordX);
        trackImage.setY(coordY);
        trackImage.setImage(source);
        //This makes all images share a parent with the imported ImageView trackImage, dynamically images adding to FXML
        ((Pane) image.getParent()).getChildren().add(trackImage);
    }

    public boolean invalidCoordChecker(double x, double y) {
        /**
         * Checks if the user clicked coordinates are valid tile to place towers
         * If the coordinates are within the range of any valid coordinate and it's value + 64
         * It will return TRUE if the condition above is true meaning the
         * @param x: The x coordinate to check.
         * @param y: The y coordinate to check
         * @return True if the coordinates are invalid
         * @author Michelle Lee
         */
        if (x < gamePaneWidth | y < gamePaneHeight) {
            for (int i = 0; i < invalidCoordsListX.size(); i++) {
                int validX = invalidCoordsListX.get(i);
                int validY = invalidCoordsListY.get(i);

                // if x and y are not within tile range...
                if ((x >= validX && x <= validX + 64) && y >= validY && y <= validY + 64) {
                    return true;
                }
            }

        }
        return false;
    }

    public boolean outsideGamePane(double x, double y) {
        /**
         * Checks if the user clicked coordinates are valid tile to place towers
         * If the coordinates are within the range of any valid coordinate and it's value + 64
         * It will return TRUE if the condition above is true meaning the
         * @param x: The x coordinate to check.
         * @param y: The y coordinate to check
         * @return True if the coordinates are invalid
         * @author Michelle Lee
         */
        return x < 0 || x > gamePaneWidth || y < 300 || y > 1024;
    }

    public String getTileType(int id){
        //Currently unused but could be useful
        if (id == -1){
            return("Art/Terrain/Ground/splitTerrain/row-0-column-4.png");
        }
        return null;
    }

    public ArrayList<ArrayList<Integer>> getTileList(){return (tileList);}


}

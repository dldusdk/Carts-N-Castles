package seng201.team0.services.gameLoaders;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import seng201.team0.services.settings.Settings;
import seng201.team0.services.fileReaders.FileReader;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * This class the level graphics to be loaded based of CSV files.
 *
 * @author Gordon Homewood
 */
public class LevelLoader {
    private final ArrayList<ArrayList<Integer>>tileList;
    private final ArrayList<ArrayList<Integer>>decorationList;
    // List to store the coordinates (x, y) of the tiles with value -1
    private final ArrayList<Integer> invalidCoordsListX = new ArrayList<>();
    private final ArrayList<Integer> invalidCoordsListY  = new ArrayList<>();
    private static final double gamePaneWidth = 1152;
    private static final double gamePaneHeight = 768;

    private final int tileSize;
    private final int initialX;
    private final ImageView base1Image;


    /**
     * This creates the level loader object and gets coordinate where this list should start. It uses the FileReader
     * class to process the CSV files into 2D arrays.
     * @param image default imageView so the level can be drawn on the screen
     * @param level path to level CSV file
     * @param decor path to level decoration CVS file
     *
     * @author Gordon Homewood
     */
    public LevelLoader(ImageView image, InputStream level, InputStream decor){
        base1Image = image;
        //Gets settings of spawn, dependent on which level is chosen
        Settings settings = new Settings();
        initialX = settings.getIntialX();
        tileSize = settings.getTilePixelSize();


        FileReader loadCSV = new FileReader(level);
        tileList = loadCSV.getList();
        FileReader loadDecoration = new FileReader(decor);
        decorationList = loadDecoration.getList();
        loadLevel();
        loadDecorations();
        }


    /**
     * This method converts the 2D array into a grid of images on the screen, which draws the
     * level by calling the loadNewImage method for each image. It also adds the coordinates
     * of the track blocks to a list, which can be accessed when determining a valid coordinates
     * for tower placing.
     *
     * @author Gordon Homewood
     */
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
                int columnPixel = column * tileSize + initialX;
                int rowPixel = row * tileSize;

                if (num == 36){
                    loadNewImage((columnPixel), (rowPixel), base1Image,grassPath);
                    loadNewImage((column * tileSize + initialX), (row * tileSize), base1Image,trackPathRight);
                }
                if (num == 7){
                    loadNewImage((columnPixel), (rowPixel), base1Image,grassPath);
                    loadNewImage((column * tileSize + initialX), (row * tileSize), base1Image,trackPathTurnDown);
                }
                if (num == 18){
                    loadNewImage((columnPixel), (rowPixel), base1Image,grassPath);
                    loadNewImage((columnPixel), (rowPixel), base1Image,trackPathDown);
                }
                if (num == 25){
                    loadNewImage((columnPixel), (rowPixel), base1Image,grassPath);
                    loadNewImage((columnPixel), (rowPixel), base1Image,trackPathTurnDownRight);
                }
                if (num == 27){
                    loadNewImage((columnPixel), (rowPixel), base1Image,grassPath);
                    loadNewImage((columnPixel), (rowPixel), base1Image,trackPathTurnUpRight);
                }
                if (num == 5){
                    loadNewImage((columnPixel), (rowPixel), base1Image,grassPath);
                    loadNewImage((columnPixel), (rowPixel), base1Image,trackPathTurnDownLR);
                }
                if (num == -1){
                    loadNewImage((columnPixel), (rowPixel), base1Image,grassPath);
                }
                if (num != -1){
                    invalidCoordsListX.add(rowPixel);
                    invalidCoordsListY.add(columnPixel);

                }
                row++;
            }
            column++;
        }

    }


    /**
     * This method converts the 2D array into a grid of images on the screen, which draws the
     * level's decorations. These decorations ar purely aesthetic and have no effect on the player or
     * the game.
     *
     * @author Gordon Homewood
     */
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


    /**
     * This method converts the 2D array into a grid of images on the screen, which draws the
     * level's decorations. These decorations ar purely aesthetic and have no effect on the player or
     * the game.
     *
     * @author Gordon Homewood
     */
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
    /**
     * Checks if the user clicked coordinates are valid tiles to place the towers
     * If the coordinates are within the range of any invalid coordinate (from list invalidcoordsList(X)(Y) and it's value + 64
     * It will return TRUE meaning that it is invalid
     * @param x: The x coordinate to check
     * @param y: The y coordinate to check
     * @return True if the coordinates are invalid
     * @author Michelle Lee
     */
    public boolean invalidCoordChecker(double x, double y) {
        if (x < gamePaneWidth | y < gamePaneHeight) {
            for (int i = 0; i < invalidCoordsListX.size(); i++) {
                int validX = invalidCoordsListX.get(i);
                int validY = invalidCoordsListY.get(i);
                // if x and y are not within tile range
                if ((x >= validX && x <= validX + 64) && y >= validY && y <= validY + 64) {
                    return true;
                }
            }
        }
        return false;
    }
    /**
     * Checks if the user clicked coordinates are valid tiles to place towers
     * If the coordinates are within the range the gamePaneWidth and the specified y coordinates it will return True if
     * it is outside the gamePane
     * @param x: The x coordinate to check.
     * @param y: The y coordinate to check
     * @return True if the coordinates are invalid
     * @author Michelle Lee
     */
    public boolean outsideGamePane(double x, double y) {
        return x < 0 || x > gamePaneWidth || y < 300 || y > 1024;
    }

}

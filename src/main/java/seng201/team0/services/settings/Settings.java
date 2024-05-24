package seng201.team0.services.settings;


/**
 * Settings class to define level settings.
 *
 * @author Gordon Homewood
 */
public class Settings {
    private final int levelTilesWidth;
    private final int levelTilesHeight;
    private final int tilePixelSize;
    private final int initalX;


    /**
     * Creates settings, initializes what will become the size of the 2D arrays for the levels. These
     * are specifically defined because the game screen has a specific slot for perfectly displaying the level. This
     * class should only be changed if the whole game GUI is being reworked.
     *
     * @author Gordon Homewood
     */
    public Settings(){
        levelTilesHeight = 13;
        levelTilesWidth = 18;
        tilePixelSize = 64;
        initalX = 192;
    }

    /**
     * @return level display tile height.
     * @author Gordon Homewood
     */
    public int getTileHeight(){return(levelTilesHeight); }

    /**
     * @return level display tile width.
     * @author Gordon Homewood
     */
    public int getTileWidth(){return(levelTilesWidth); }

    /**
     * @return pixel size, based the asset pack tile size
     * @author Gordon Homewood
     */
    public int getTilePixelSize(){return(tilePixelSize); }

    /**
     * @return start x to generate level, based on asset pack tile size
     * @author Gordon Homewood
     */
    public int getIntialX() {return(initalX); }
}

package seng201.team0.services.gameLoaders;

import javafx.scene.image.ImageView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class LoadRoundTest {
    LoadRound newRound;

    @BeforeEach
    void setUp() {

        ImageView trackDefault = new ImageView();
        String levelPath = "src/main/resources/levelCSV/Level1/Level1Concept_Track.csv";
        String levelDecor = "src/main/resources/levelCSV/Level1/Level1Concept_Decorations.csv";

        LevelLoader levelLoader = new LevelLoader(trackDefault, levelPath, levelDecor);
        ArrayList<Integer> cartNums = new ArrayList<>();
        PathLoader pathLoader = new PathLoader("src/main/resources/levelCSV/Level1/Level1CartPath", "src/main/resources/levelCSV/Level1/Level1RotatePath");
        cartNums.add(3);
        cartNums.add(2);
        cartNums.add(1);

        newRound = new LoadRound(1, "Easy", trackDefault, levelLoader, pathLoader, cartNums);
    }

    @Test
    void loadCarts() {
    }

    @Test
    void getCartList() {
    }
}
package seng201.team0.gui.mainGUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Class starts the javaFX application window
 * @author seng201 teaching team
 */
public class MainWindow extends Application {

    /**
     * Opens the gui with the fxml content specified in resources/fxml/main.fxml
     * @param primaryStage The current fxml stage, handled by javaFX Application class
     * @throws IOException if there is an issue loading fxml file
     */

    @Override
    public void start(Stage primaryStage) throws IOException {
        Font minecraftFont = Font.loadFont(getClass().getResourceAsStream("/fonts/Minecraft.ttf"), 12);

        FXMLLoader baseLoader = new FXMLLoader(getClass().getResource("/fxml/main.fxml"));
        Parent root = baseLoader.load();

        MainController baseController = baseLoader.getController();
        baseController.init(primaryStage);

        Scene scene = new Scene(root,1472,1024);
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void launchWrapper(String[] args) {
        launch(args);
    }

}

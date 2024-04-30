package seng201.team0.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;

import java.io.IOException;

/**
 * Class starts the javaFX application window
 * @author seng201 teaching team
 */
public class GameWindow extends Application {

    /**
     * Opens the gui with the fxml content specified in resources/fxml/main.fxml
     * @param primaryStage The current fxml stage, handled by javaFX Application class
     * @throws IOException if there is an issue loading fxml file
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader baseLoader = new FXMLLoader(getClass().getResource("/fxml/game.fxml"));
        Parent root = baseLoader.load();

        GameController baseController = baseLoader.getController();
        baseController.init(primaryStage);
        int height = 1024;
        int width = 1280;


        primaryStage.setTitle("SENG201 Example App");
        Scene scene = new Scene(root, width, height);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.sizeToScene();



        primaryStage.show();
    }


    /**
     * Launches the FXML application, this must be called from another class (in this cass App.java) otherwise JavaFX
     * errors out and does not run
     * @param args command line arguments
     */
    public static void launchWrapper(String [] args) {
        launch(args);
    }

}

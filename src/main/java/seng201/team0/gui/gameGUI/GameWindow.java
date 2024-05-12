package seng201.team0.gui.gameGUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;
import seng201.team0.gui.gameGUI.GameController;

import java.io.IOException;
import java.util.Objects;

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
        FXMLLoader baseLoader = new FXMLLoader(getClass().getResource("/fxml/gameScreen.fxml"));
        Parent root = baseLoader.load();
        String diff = "Normal";

        GameController baseController = baseLoader.getController();
        baseController.init(primaryStage);

        Scene scene = new Scene(root,1472,1024);


        //Code so it can run on Gordon's mac
        String systemType = System.getProperty("os.name");
        if(Objects.equals(systemType, "Mac OS X")){
            System.out.println(systemType);
            double scaleFactor = 0.75;
            Scale scale = new Scale(scaleFactor, scaleFactor);
            scale.setPivotX(0);
            scale.setPivotY(0);
            scene.getRoot().getTransforms().setAll(scale);}

        primaryStage.setResizable(false);
        primaryStage.setScene(scene);


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

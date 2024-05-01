package seng201.team0.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Controller for the MainScreen
 * @author Michelle Lee
 */

public class MainController {

    @FXML
    private Button quitButton;
    @FXML
    private Button playButton;
    @FXML
    private AnchorPane mainscreenPane;

    Stage stage;

    @FXML
    public void quit(ActionEvent actionEvent)  {

        // Have a pop-up appear if the user clicks 'Quit' Button
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Quit");
        alert.setHeaderText("You are about to quit our game ˙◠˙ ");
        alert.setContentText("Are you sure you want to quit?");

        // if they want to quit, close program
        if (alert.showAndWait().get() == ButtonType.OK) {
            stage = (Stage) mainscreenPane.getScene().getWindow();
            System.out.println("You Successfully quit the game!");
            stage.close();
        }
    }

    @FXML
    public void play(ActionEvent actionEvent){
        try {
            stage = (Stage) mainscreenPane.getScene().getWindow();
            GameWindow gameWindow = new GameWindow();
            gameWindow.start(stage);
        } catch (IOException e) {
            e.printStackTrace();
    }}

    public void init(Stage primaryStage) {
    }

}

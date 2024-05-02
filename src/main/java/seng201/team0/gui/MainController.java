package seng201.team0.gui;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class MainController {
    /**
     * Main Controller for the initial start up screen of game.
     * @author Michelle Lee
     */

    @FXML
    private Button quitButton;
    @FXML
    private Button playButton;
    @FXML
    private Button normalButton;
    @FXML
    private Button hardButton;
    @FXML
    private Text headerText;

    @FXML
    private AnchorPane mainscreenPane;

    Stage stage;

    public void play(ActionEvent actionEvent) {
        /**
         * Method for when the Play button is clicked, to show difficulty buttons
         * @author Michelle Lee
         */

        playButton.setVisible(false); //hide play button
        quitButton.setVisible(false); // hide quit button
        normalButton.setVisible(true); // make difficulty buttons visible
        hardButton.setVisible(true);
        headerText.setText("Choose your difficulty!"); // change header text

        hardButton.setOnAction(e -> confirmDifficulty("HARD")); // If Hard is chosen, pass 'HARD' to confirmDifficulty method
        normalButton.setOnAction(e -> confirmDifficulty("NORMAL")); // If Normal is chosen, pass 'HARD' to confirmDifficulty method

        /*try {
            stage = (Stage) mainscreenPane.getScene().getWindow();
            GameWindow gameWindow = new GameWindow();
            gameWindow.start(stage);
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }

    private void confirmDifficulty(String difficulty) {
        // Show confirmation dialog
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.CONFIRMATION);
        alert.setTitle("Difficulty Selection");
        alert.setHeaderText(null);
        alert.setContentText("You've chosen " + difficulty + " difficulty.\nAre you sure?\n(Click 'OK' to continue)");
        // Show and wait for user response
        alert.showAndWait();
        //if (alert.getResult() == ButtonType.OK) {
        // try {
                   /*try {
            stage = (Stage) mainscreenPane.getScene().getWindow();
            GameWindow gameWindow = new GameWindow();
            gameWindow.start(stage);
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }


    @FXML
    public void normal(ActionEvent actionEvent) {
        /**
         * When Normal Button is clicked, run askName popup box and initialize game
         * @author Michelle Lee
         */
        hardButton.setVisible(false); // code for normal difficulty goes here visibility just place holder code
    }
    @FXML
    public void hard(ActionEvent actionEvent) {
        /**
         * When Hard Button is clicked, run askName popup box and initialize game
         * @author Michelle Lee
         */

        normalButton.setVisible(false); // code for hard difficulty goes here

    }

    @FXML
    public void quit(ActionEvent actionEvent) {


        // Have a pop-up appear if the user clicks 'Quit' Button
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Quit");
        alert.setHeaderText("You are about to quit our game ˙◠˙ ");
        alert.setContentText("Are you sure you want to quit?");

        // If User clicks 'Confirm', Quit game
        if (alert.showAndWait().get() == ButtonType.OK) {
            stage = (Stage) mainscreenPane.getScene().getWindow();
            System.out.println("You Successfully quit the game!");
            stage.close();
        }
    }

    public void init(Stage primaryStage) {
    }

}


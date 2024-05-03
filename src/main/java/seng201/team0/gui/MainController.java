package seng201.team0.gui;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Optional;

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

        // Open Text Input Dialog to get user Name
        TextInputDialog userNameDialog = new TextInputDialog();
        userNameDialog.setTitle("Your name");
        userNameDialog.setHeaderText("Please enter your name:");
        userNameDialog.setContentText("Note:Your name must be between 3-15 characters and contain no special characters:");

        // Store user response
        Optional<String> userName = userNameDialog.showAndWait();

        if (userName.isPresent()) {
            //Remove any unnecessary spaces infront and before the input
            String name = userName.get().trim();
            if (name.matches("^[a-zA-Z0-9 ]{3,15}$")) {
                headerText.setText("Welcome " + userName.get() + "!\nChoose your difficulty!"); // change header text
                headerText.setTextAlignment(TextAlignment.CENTER);

                // Hide quit and play button
                playButton.setVisible(false);
                quitButton.setVisible(false);

                // make difficulty buttons visible
                normalButton.setVisible(true);
                hardButton.setVisible(true);

                // If Hard is chosen, pass 'HARD' to confirmDifficulty method
                hardButton.setOnAction(e -> confirmDifficulty("HARD"));
                // If Normal is chosen, pass 'NORMAL' to confirmDifficulty method
                normalButton.setOnAction(e -> confirmDifficulty("NORMAL"));

            } else {
            // Show an error message for all invalid inputs
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Input");
                alert.setHeaderText("Please enter a name between 3-15 characters with no special characters.");
                alert.setContentText(null);
            }
        }
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
    }
    @FXML
    public void hard(ActionEvent actionEvent) {
        /**
         * When Hard Button is clicked, run askName popup box and initialize game
         * @author Michelle Lee
         */

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


package seng201.team0.gui;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Duration;
//import javafx.scene.media.Media;
//import javafx.scene.media.MediaPlayer;
import java.io.IOException;
import java.util.Optional;

public class MainController {
    /**
     * Main Controller for the initial start up screen of game.
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
         * Method for when the Play button is clicked.
         * Asks for the Username (must meet certain criteria
         * Asks for difficulty level
         * Asks to choose map leve
         * @author Michelle Lee
         */

        // Initialize bool to ensure we loop the pop-up for name until a valid name is given
        boolean validNameEntered = false;

        while (!validNameEntered) {

            // Opens Text Input Dialog to get userName
            TextInputDialog userNameDialog = new TextInputDialog();
            userNameDialog.setTitle("Start new game");
            userNameDialog.setHeaderText("To start the game please enter your name:");
            userNameDialog.setContentText("Note:Your name must be between 3-15 characters and contain no special characters:");

            // Store user's response in userName
            Optional<String> userName = userNameDialog.showAndWait();

            // If the userName is not empty space
            if (userName.isPresent()) {
                //Remove any unnecessary spaces in front and before the input and ensure is valid
                String name = userName.get().trim();
                if (name.matches("^[a-zA-Z0-9 ]{3,15}$")) {
                    // Capitalize the first letter of given input
                    name = name.substring(0,1).toUpperCase() + name.substring(1);

                    // Change header text to welcome the user with their inputted name
                    headerText.setText("Welcome " + name + "!\nChoose your difficulty!");
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

                    // Correct output, so stop showing the input dialog for name
                    validNameEntered = true;

                } else {
                    // Shows a POP-UP error message for all invalid inputs
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Invalid Input");
                    alert.setHeaderText("Please enter a name between 3-15 characters with no special characters.");
                    alert.setContentText(null);
                    alert.showAndWait();
                }
            } else {
                // When user cancels the input dialog box, does not request for the name
                validNameEntered = true;
            }
        }
    }

    private void confirmDifficulty(String difficulty) {
        /**
         * Method for confirming the Difficulty level and then opening up the mapSelector to allow the user to choose
         * their level
         * @author Michelle Lee
         */
        // Show confirmation dialog
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.CONFIRMATION);
        alert.setTitle("Difficulty Selection");
        alert.setHeaderText(null);
        alert.setContentText("You've chosen " + difficulty + " difficulty.\nAre you sure?\n(Click 'OK' to continue)");

        // Wait for user response and store it in a variable named chosenDifficulty
        Optional<ButtonType> chosenDifficulty = alert.showAndWait();

        // If user confirms the difficulty
        if (chosenDifficulty.isPresent() && chosenDifficulty.get() == ButtonType.OK) {
            // Close main window
            Stage mainWindow = (Stage) headerText.getScene().getWindow();
            mainWindow.close();
            // Run the mapSelect method to initialize mapSelection
            mapSelect();
        }
    }

    private void mapSelect() {
        /**
         * Allow the user to select the level they want to play on
         * @author Michelle Lee
         * */

         // Create new stage for the mapSelection Window
         Stage mapSelectionStage = new Stage();
         FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/mapSelector.fxml"));
         Parent mapSelectionRoot;

         try {
             mapSelectionRoot = loader.load();
         } catch (IOException e) {
             e.printStackTrace();
             return;
         }

         // Set up the scene for the mapSelection Window
         Scene mapSelectionScene = new Scene(mapSelectionRoot);
         mapSelectionStage.setScene(mapSelectionScene);
         mapSelectionStage.setTitle("Map Selection");

         // Show mapSelection window
         mapSelectionStage.show();
    }

    @FXML
    public void normal(ActionEvent actionEvent) {
        /**
         * When Normal Button is clicked...
         * Does nothing at the moment
         * @author Michelle Lee
         */
    }
    @FXML
    public void hard(ActionEvent actionEvent) {
        /**
         * When Hard Button is clicked...
         * Does nothing at the moment
         * @author Michelle Lee
         */

    }

    @FXML
    public void quit(ActionEvent actionEvent) {
        /**
         * A Method that allows the user to quit the application upon clicking a button
         * Confirms whether the user would like to quit or not.
         * @author Michelle Lee
         */

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


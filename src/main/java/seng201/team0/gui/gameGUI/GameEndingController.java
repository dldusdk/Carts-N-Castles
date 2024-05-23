package seng201.team0.gui.gameGUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

public class GameEndingController {
    /**G
     * Game Ending controller
     * Main Controller for the initial start up screen of game.
     */
    @FXML
    private Label endingMessage;
    @FXML
    private Label nameLabel;
    @FXML
    private Label selectedRoundsLabel;
    @FXML
    private Label roundsCompletedLabel;
    @FXML
    private Label totalMoneyLabel;
    @FXML
    private Label totalPointsLabel;
    @FXML
    private Label feedbackLabel;
    @FXML
    private Button retryButton;
    @FXML
    private Button quitGameButton;
    @FXML
    private Button toMainMenuButton;

    @FXML
    private AnchorPane endPane;



    Stage stage;
    String musicpath = "src/main/resources/Music/bg/bgmMain.mp3";
    private static MediaPlayer mediaPlayer;

    public void init(Stage primaryStage) {
    }

    @FXML
    private void retry(ActionEvent event) {
        /**
         *
         * Back to the Game!!!
         */

    }

    @FXML
    private void quitGame(ActionEvent event) {
        /**
         *
         * Quits the game
         */

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Quit");
        alert.setHeaderText("You are about to quit! ");
        alert.setContentText("Are you sure?");

        // If User clicks 'Confirm', Quit game
        if (alert.showAndWait().get() == ButtonType.OK) {
            stage = (Stage) endPane.getScene().getWindow();
            System.out.println("You Successfully quit the game!");
            stage.close();
        }
    }
    @FXML
    private void toMainMenu(ActionEvent event) {
        /**
         *
         * Goe sto the main Screen
         */




    }




}


package seng201.team0.gui.gameGUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

/**
 * This class holds the methods to close the game controller and to display the correct information and stats from the game
 * controller to the player
 * @author Michelle Lee
 */
public class GameEndingController {
    // GUI
    @FXML
    private Label endingMessage;
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

    // INITALIZE VARIABLES
    public Stage primaryStage;
    Stage stage;
    private String songPath;

    /**
     * initializes the Game End Screen
     * @author Michelle Lee
     */
    public void init() {
        this.primaryStage = (Stage) endPane.getScene().getWindow();
    }

    /**
     * Depending on whether the user has won or lost, the music for the Game Ending Controller will differ
     * @param songPath String variable passed in when the Game Ending Controller is called
     * @author Michelle Lee
     */
    public void playEndingSong(String songPath) {
        Media media = new Media(Objects.requireNonNull(getClass().getResource(songPath)).toExternalForm());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
    }


    /**
     * Set the Labels (GUI) for the game ending screen. Ensure the player Stats are displayed with the correct
     * values at the end of the game.
     * @param feedback provides message based on win or loss
     * @param selectedRounds the player chosen round number
     * @param totalRoundsCompeted the number of rounds the player completed before win / loes
     * @param totalMoney the total coins earned
     * @param totalPoints total points earned
     * @param message message changes depending on win or lose
     * @author Michelle Lee
     */
    public void gameStats(String feedback, int selectedRounds, int totalRoundsCompeted, int totalMoney, int totalPoints, String message) {
        endingMessage.setText(feedback);
        selectedRoundsLabel.setText(String.valueOf(selectedRounds));
        roundsCompletedLabel.setText(String.valueOf(totalRoundsCompeted));
        totalMoneyLabel.setText(String.valueOf(totalMoney));
        totalPointsLabel.setText(String.valueOf(totalPoints));
        feedbackLabel.setText(message);
    }

    /**
     * Close the current end Pane and restart and launch the game Screen to allow the player to replay the game
     * @param event when the Retry button is clicked
     * @author Michelle Lee
     */
    @FXML
    private void retry(ActionEvent event) {
        stage = (Stage) endPane.getScene().getWindow();
        stage.close();
        Font minecraftFont = Font.loadFont(getClass().getResourceAsStream("/fonts/Minecraft.ttf"), 12);


        FXMLLoader baseLoader = new FXMLLoader(getClass().getResource("/fxml/gameScreen.fxml"));
        Parent root;
        try {
            root = baseLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        GameController baseController = baseLoader.getController();
        baseController.init(stage);

        Scene scene = new Scene(root, 1472, 1024);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Allows the user to click a button and go back to the Main Menu
     * @param event allow the user to go back to the Main Menu once this event is run
     * @author Michelle Lee
     */
    @FXML
    private void toMainMenu(ActionEvent event) {
        stage = (Stage) endPane.getScene().getWindow();
        stage.close();
        Font minecraftFont = Font.loadFont(getClass().getResourceAsStream("/fonts/Minecraft.ttf"), 12);


        Stage mainMenu = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/main.fxml"));
        Parent mainMenuRoot;

        try {
            mainMenuRoot = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        // Set up the scene for the main menu
        Scene mainScene = new Scene(mainMenuRoot);
        mainMenu.setScene(mainScene);
        mainMenu.setTitle("Main Screen");
        // Show main menu
        mainMenu.show();
    }

    /**
     * Allows the user to click a button and quit the game
     * @param event allow the user to go back to the Main Menu once this event is run
     * @author Michelle Lee
     */
    @FXML
    private void quitGame(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Quit");
        alert.setHeaderText("You are about to quit! ");
        alert.setContentText("Are you sure?");

        if (alert.showAndWait().get() == ButtonType.OK) {
            stage = (Stage) endPane.getScene().getWindow();
            stage.close();
        }
    }
}
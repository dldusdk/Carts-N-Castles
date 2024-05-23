package seng201.team0.gui.gameGUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.IOException;

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

    private Stage primaryStage;



    Stage stage;
    String musicpath = "src/main/resources/Music/bg/bgmMain.mp3";
    private static MediaPlayer mediaPlayer;
   // private Stage primaryStage;

    public void init(Stage primaryStage) {
       this.primaryStage = (Stage) endPane.getScene().getWindow();;
    }

    @FXML
    private void retry(ActionEvent event) {
        /**
         *
         * Back to the Game!!!
         */

        stage = (Stage) endPane.getScene().getWindow();
        stage.close();

        FXMLLoader baseLoader = new FXMLLoader(getClass().getResource("/fxml/gameScreen.fxml"));
        Parent root = null;
        try {
            root = baseLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        GameController baseController = baseLoader.getController();
        baseController.init(stage);

        Scene scene = new Scene(root,1472,1024);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();

        // Show mapSelection window
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
            stage.close();
        }
    }
    @FXML
    private void toMainMenu(ActionEvent event) {
        /**
         *When button is clicked, it will close the gameEnd stage and open the main controller
         * @author Michelle Lee
         */
        stage = (Stage) endPane.getScene().getWindow();
        stage.close();

        Stage mainMenu = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/main.fxml"));
        Parent mainMenuRoot;

        try {
            mainMenuRoot = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        // Set up the scene for the mapSelection Window
        Scene mainScene = new Scene(mainMenuRoot);
        mainMenu.setScene(mainScene);
        mainMenu.setTitle("Main Screen");

        // Show mapSelection window
        mainMenu.show();




    }




}


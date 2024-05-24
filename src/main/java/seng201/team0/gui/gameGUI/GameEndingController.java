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
import seng201.team0.gui.mainGUI.MainController;

import java.io.File;
import java.io.IOException;

public class GameEndingController {
    // GUI

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

    // INITALIZE VARIABLES
    public Stage primaryStage;
    Stage stage;
    String musicpath = "src/main/resources/Music/bg/bgmMain.mp3";
    private static MediaPlayer mediaPlayer;
    private String songPath;

    public void init() {
        this.primaryStage = (Stage) endPane.getScene().getWindow();
    }
    public void playEndingSong(String songPath) {
        Media media = new Media(new File(songPath).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
    }


    public void gameStats(String feedback, int selectedRounds, int totalRoundsCompeted, int totalMoney, int totalPoints, String message) {
        endingMessage.setText(feedback);
        selectedRoundsLabel.setText(String.valueOf(selectedRounds));
        roundsCompletedLabel.setText(String.valueOf(totalRoundsCompeted));
        totalMoneyLabel.setText(String.valueOf(totalMoney));
        totalPointsLabel.setText(String.valueOf(totalPoints));
        feedbackLabel.setText(message);
    }

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
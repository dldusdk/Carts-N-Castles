package seng201.team0.gui.mainGUI;

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
import javafx.scene.text.Text;
import javafx.stage.Stage;
import seng201.team0.gui.gameGUI.GameController;


import java.io.*;
import java.util.Objects;
import java.util.Optional;

/**
 * Main Controller for the set-up of the Main Stat up Screen
 * @author Michelle Lee
 */
public class MainController {
    @FXML
    private Button quitButton;
    @FXML
    private Button playButton;
    @FXML
    private Text headerText;
    @FXML
    private AnchorPane mainscreenPane;
    Stage stage;
//    String musicPath = "src/main/resources/Music/bg/mainscreenbgm.mp3";
final String musicPath = "/Music/bg/mainscreenbgm.mp3";
    private boolean nameRecieved = false;

    /**
     * Initializes the Main Start-Up Screen
     * Calls the playMusic(musicPath) method to play the music when the stage starts
     * @param primaryStage primary stage of the Main Screen
     */
    public void init(Stage primaryStage) {
        playMusic(musicPath);
    }

    /**
     * Play the BGM for the Main Screen
     * @param musicPath String path of the song to play
     * @author Michelle Lee
     */
    public void playMusic(String musicPath) {

//        Media media = new Media(new File(musicPath).toURI().toString());
        Media media = new Media(Objects.requireNonNull(getClass().getResource(musicPath)).toExternalForm());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);
    }

    /**
     * Method for when the Play button is clicked.
     * Asks for the Username (must meet certain criteria
     * Asks to choose map leve
     * @author Michelle Lee
     */
    public void play(ActionEvent actionEvent) {
        // Initialize bool to ensure we loop the pop-up for name until a valid name is given
        boolean validNameEntered = false;

        while (!validNameEntered) {
            // Opens Text Input Dialog to get userName
            String name = getName();
            // If the input name is valid
            assert name != null;
            if (validNameTest(name)) {
                validNameEntered = true;
                startGame();
            } else {
                // Shows a POP-UP error message for all invalid inputs
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Input");
                alert.setHeaderText("Please enter a name between 3-15 characters with no special characters.");
                alert.setContentText(null);
                alert.showAndWait();
                }
            }
    }

    /**
     * Gets the name of the user from input text dialog and passes it through validNameTest to check if valid
     * @return the name input from the user
     * @author Michelle Lee
     */
    private String getName() {
        if (nameRecieved) {
            // If already in game over loop, don't let function cause
            // infinite loop of opening game over screen
            return(null);
        }
        nameRecieved = true;
        TextInputDialog userNameDialog = new TextInputDialog();
        userNameDialog.setTitle("Start new game");
        userNameDialog.setHeaderText("To start the game please enter your name:");
        userNameDialog.setContentText("Note:Your name must be between 3-15 characters and contain no special characters:");

        // Store user's response in userName
        Optional<String> userName = userNameDialog.showAndWait();
        validNameTest(String.valueOf(userName));
        return userName.get().trim();
    }

    /**
     * Tests if name is valid or not
     * @param name String passed from play method to check if the User's input is valid or not
     * @author Michelle Lee
     */
    public boolean validNameTest(String name) {
        return name.matches("^[a-zA-Z0-9 ]{3,15}$");
    }

    /**
     * Open the Game Window
     * @author Michelle Lee
     * */
    private void startGame() {
        stage = (Stage) mainscreenPane.getScene().getWindow();
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
     * A Method that allows the user to quit the application upon clicking a button
     * Confirms whether the user would like to quit or not.
     * @param actionEvent When the 'Quit' Button is clicked
     * @author Michelle Lee
     */
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
            stage.close();
        }
    }
}


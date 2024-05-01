package seng201.team0.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Popup;
import javafx.stage.Stage;

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
    private AnchorPane mainscreenPane;
    @FXML
    //private Label creditsLabel;

    Stage stage;

    public void play(ActionEvent actionEvent) {
        /**
         * Method for when the Play button is clicked, to show difficulty buttons
         * @author Michelle Lee
         */
        playButton.setVisible(false); //hide button
        quitButton.setVisible(false); // hide quit button
        //creditsLabel.setVisible(false); // hide label
        normalButton.setVisible(true);
        hardButton.setVisible(true);

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
        /**
         * Method that confirms whether a user wants to quit by opening a dialog box
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



    /**

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
    **/


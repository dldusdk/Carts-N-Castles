package seng201.team0.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import seng201.team0.services.CounterService;

/**
 * Controller for the main.fxml window
 * @author Michelle
 */

public class MainController {

    @FXML
    private Label defaultLabel;

    @FXML
    private Button defaultButton;

    private CounterService counterService;

    public void init(Stage primaryStage) {
    }
}
//test comment
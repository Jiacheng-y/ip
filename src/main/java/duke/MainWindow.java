package duke;

import duke.tools.Ui;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * Controller for MainWindow. Provides the layout for the other controls.
 */
public class MainWindow extends AnchorPane {
    // Solution below adapted from https://se-education.org/guides/tutorials/javaFx.html.
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Duke duke;

    private Image userImg = new Image(this.getClass().getResourceAsStream("/images/user.png"));
    private Image dukeImg = new Image(this.getClass().getResourceAsStream("/images/Duke.png"));

    /**
     * Initialises the GUI on starting the file.
     */
    @FXML
    public void initialize() {

        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        dialogContainer.getChildren().add(
                DialogBox.getDukeDialog(Ui.greet(), dukeImg)
        );
    }

    /**
     * Sets a Duke object as the Duke.
     * @param d Duke object.
     */
    public void setDuke(Duke d) {

        duke = d;
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = duke.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImg),
                DialogBox.getDukeDialog(response, dukeImg)
        );
        userInput.clear();
    }
}

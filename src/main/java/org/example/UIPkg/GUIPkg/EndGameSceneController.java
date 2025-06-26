package org.example.UIPkg.GUIPkg;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import org.example.ServerPkg.Model.ForView.GameView;

import java.net.URL;
import java.util.ResourceBundle;

public class EndGameSceneController extends GuiController implements Initializable {

    @FXML
    private VBox winnersContainer;

    @FXML
    private Label titleLabel;

    @FXML
    private Label subtitleLabel;

    private int i;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        i=0;
    }

    public void setUp(GameView game){
        displayWinner(game.getException().getMessage());
    }

    public void displayWinner(String winnerString) {
        winnersContainer.getChildren().clear();

        String medal = "";
        String color = switch (i) {
            case 0 -> {
                medal = "🥇";
                yield "#f1c40f";
            }
            case 1 -> {
                medal = "🥈";
                yield "#95a5a6";
            }
            case 2 -> {
                medal = "🥉";
                yield "#e67e22";
            }
            default -> {
                medal = "🏆";
                yield "#3498db";
            }
        };

        String message = medal + " " + winnerString;

        Label playerLabel = new Label(message);
        playerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        playerLabel.setStyle("-fx-text-fill: " + color + "; -fx-padding: 10;");
        playerLabel.setWrapText(true);
        playerLabel.setMaxWidth(600);

        winnersContainer.getChildren().add(playerLabel);
        i++;
    }
}
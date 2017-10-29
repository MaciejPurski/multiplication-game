package view;

import controller.GameController;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class InitView {
    GameView gameView;
    GameController gameController;

    @FXML
    private RadioButton player1Human, player2Human;

    @FXML
    private TextField player1Text, player2Text, pTextField, nTextField, xTextField;

    @FXML
    private HBox player1HBox, player2HBox;

    @FXML
    public void onStartClicked(MouseEvent event) {
        gameController.initGame(Integer.parseInt(pTextField.getText()), Integer.parseInt(nTextField.getText()),
                                Integer.parseInt(xTextField.getText()), player1Human.isSelected(), player2Human.isSelected());

        Stage dialog = (Stage) ((Node) event.getTarget()).getScene().getWindow();
        dialog.close();
    }

    @FXML
    public void onCancelClicked(MouseEvent event) {
        Stage dialog = (Stage) ((Node) event.getTarget()).getScene().getWindow();
        dialog.close();
    }

    @FXML
    public void onHumanPlayer1Clicked() {
        player1HBox.setDisable(true);
    }

    @FXML
    public void onAIPlayer1Clicked() {
        player1HBox.setDisable(false);
    }

    @FXML
    public void onHumanPlayer2Clicked() {
        player2HBox.setDisable(true);
    }

    @FXML
    public void onAIPlayer2Clicked() {
        player2HBox.setDisable(false);
    }

    public void setGameView(GameView gameView) { this.gameView = gameView; }

    public void setGameController(GameController gameController) { this.gameController = gameController; }
}

package view;

import controller.GameController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.Arrays;

public class InitView {
    private GameView gameView;
    private GameController gameController;

    @FXML
    private RadioButton player1Human, player2Human;

    @FXML
    private TextField player1Text, player2Text, pTextField, nTextField, xTextField;

    @FXML
    private HBox player1HBox, player2HBox;

    @FXML
    private Button startButton;

    @FXML
    private ListView xListView;

    ObservableList<Integer> items = FXCollections.observableArrayList();

    @FXML
    public void onStartClicked(MouseEvent event) {
        //TODO: handle wrong values
        int pValue = Integer.parseInt(pTextField.getText());
        int nValue = Integer.parseInt(nTextField.getText());

        if (pValue <= 0 || nValue <= pValue)
            return;

        gameController.initGame(pValue, nValue,
                                Arrays.stream(items.toArray(new Integer[items.size()])).mapToInt(Integer::intValue).toArray());

        gameController.initPlayer(0,player1Human.isSelected(), Integer.parseInt(player1Text.getText()));
        gameController.initPlayer(1,player2Human.isSelected(), Integer.parseInt(player2Text.getText()));

        Stage dialog = (Stage) ((Node) event.getTarget()).getScene().getWindow();
        dialog.close();
        gameView.updateUI();
        gameController.startGame();
        gameView.initializePlayersHistoryLists();
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

    @FXML
    public void onAddClicked(MouseEvent event){
        int nItem = Integer.parseInt(xTextField.getText());

        if(!items.contains(nItem) && nItem > 1) {
            items.add(Integer.parseInt(xTextField.getText()));
            items.sort( null);
            xListView.setItems(items);
        }

        if (!items.isEmpty())
            startButton.setDisable(false);
    }

    void setGameView(GameView gameView) { this.gameView = gameView; }

    void setGameController(GameController gameController) { this.gameController = gameController; }
}

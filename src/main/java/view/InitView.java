package view;

import controller.GameController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.Arrays;

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
    private ListView xListView;

    ObservableList<Integer> items = FXCollections.observableArrayList();

    @FXML
    public void onStartClicked(MouseEvent event) {
        //TODO: handle wrong values
        gameController.initGame(Integer.parseInt(pTextField.getText()), Integer.parseInt(nTextField.getText()),
                                Arrays.stream(items.toArray(new Integer[items.size()])).mapToInt(Integer::intValue).toArray());
        if(!player1Human.isSelected())
            gameController.initPlayer(0,false, Integer.parseInt(player1Text.getText()));
        else
            gameController.initPlayer(0,true, 0);

        if(!player2Human.isSelected())
            gameController.initPlayer(1,false, Integer.parseInt(player2Text.getText()));
        else
            gameController.initPlayer(1,true, 0);
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
        //  xListView = new ListView<String>();
        if(!items.contains(Integer.parseInt(xTextField.getText()))) {
            items.add(Integer.parseInt(xTextField.getText()));
            items.sort( null);
            xListView.setItems(items);
        }
    }

    void setGameView(GameView gameView) { this.gameView = gameView; }

    void setGameController(GameController gameController) { this.gameController = gameController; }
}

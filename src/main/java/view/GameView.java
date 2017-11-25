package view;

import controller.GameController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.GameModel;

import java.io.IOException;

public class GameView {
    private GameController gameController;

    @FXML
    private Button newGameButton, restartButton, exitButton, goButton;

    @FXML
    private Label player1Label, player2Label, player1Type, player2Type;

    @FXML
    private Label playerNP, playerP, pValue, nValue;

    @FXML
    private TextField playerTextField;

    @FXML
    private ListView xListView, player1List, player2List;

    ObservableList<String> player1HistoryList, player2HistoryList;

    public GameView() throws IOException {
        gameController = new GameController(this);
    }

    @FXML
    public void onNewGameClicked(MouseEvent event) {
        System.out.println("New Game");
        try {
            Stage stage = new Stage();
            ((Node) event.getSource()).getScene().getWindow().setOnCloseRequest(e -> {
                System.exit(0);
            });

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Init.fxml"));
            Parent root = loader.load();
            InitView init = loader.getController();

            init.setGameController(gameController);
            init.setGameView(this);
            stage.setScene(new Scene(root));
            stage.setTitle("Init");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(((Node) event.getSource()).getScene().getWindow());
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onRestartClicked() {
        System.out.println("Restart");
        gameController.restart();
        initializePlayersHistoryLists();
    }

    @FXML
    public void onExitClicked() {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void onGoClicked() {
        int x = Integer.parseInt(xListView.getSelectionModel().getSelectedItem().toString());

        if (x <= 1)
            return;

        gameController.makeMove(x);
    }

    public void updateUI() {
        nValue.setText(Integer.toString(gameController.getN()));
        pValue.setText(Integer.toString(gameController.getP()));

        ObservableList<Integer> items = FXCollections.observableArrayList();
        for(int x: gameController.getX())
            items.add(new Integer(x));
        xListView.setItems(items);

        if(getCurrentMove().equals(GameModel.State.PLAYER_1_MOVE)){
            player1Label.setUnderline(true);
            player2Label.setUnderline(false);
        }
        else if(getCurrentMove().equals(GameModel.State.PLAYER_2_MOVE)){
            player1Label.setUnderline(false);
            player2Label.setUnderline(true);
        }
        else {
            player1Label.setUnderline(false);
            player2Label.setUnderline(false);
        }

    }

    private GameModel.State getCurrentMove(){
        return gameController.getModel().getCurrentMove();
    }

    public void initializePlayersHistoryLists(){
        player1HistoryList = FXCollections.observableArrayList();
        player2HistoryList = FXCollections.observableArrayList();
        player1List.setItems(player1HistoryList);
        player2List.setItems(player2HistoryList);
    }

    public void addLastMoveToHistory(){
        if(getCurrentMove().equals(GameModel.State.PLAYER_2_MOVE) || getCurrentMove().equals(GameModel.State.PLAYER_1_WIN)){
            player1HistoryList.add(gameController.lastMoveToString());
        }
        else
            player2HistoryList.add(gameController.lastMoveToString());
    }

    public void showEndOfGameInfo(){
        if(getCurrentMove().equals(GameModel.State.PLAYER_1_WIN) || getCurrentMove().equals(GameModel.State.PLAYER_2_WIN)){
            player1HistoryList.add(getCurrentMove().toString());
            player2HistoryList.add(getCurrentMove().toString());
        }
    }
}

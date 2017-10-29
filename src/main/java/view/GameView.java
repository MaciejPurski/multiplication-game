package view;

import controller.GameController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class GameView {
    private GameController gameController;

    @FXML
    private Button newGameButton, restartButton, exitButton;

    @FXML
    private Label player1Label, player2Label, player1Type, player2Type;

    @FXML
    private Label player1N, player2N, player1P, player2P, pValue, nValue;

    @FXML
    private TextField player1TextField, player2TextField;

    public GameView() throws IOException {
        gameController = new GameController();
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
    }

    @FXML
    public void onExitClicked() {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void onGo1Clicked() {

    }

    @FXML
    void onGo2Clicked() {

    }
}

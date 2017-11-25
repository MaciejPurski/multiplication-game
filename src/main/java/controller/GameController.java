package controller;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import model.GameModel;
import model.GameModel.*;
import model.MultiGameState;
import view.GameView;

public class GameController {

    private GameModel gameModel;
    private GameView gameView;
    private int prev_p;
    private int prev_n;
    private int[] prev_x;
    private SimpleObjectProperty<State> currentState;
    private AIService aiserv;

    public GameController(GameView gameView) {
        this.gameView = gameView;
        gameModel = new GameModel();
        currentState = new SimpleObjectProperty<>(State.INIT);
        addStateListener();
        initAIService();
    }

    public void initGame(int p, int n, int[] x) {

        try {
            gameModel.initGame(n, p, x);
        } catch (Exception e) {
            e.printStackTrace();
        }
        prev_n = n;
        prev_p = p;
        prev_x = x;
    }

    public void initPlayer(int index, boolean isHuman, int AIDepth){
        this.gameModel.initPlayer(index, isHuman, AIDepth);
    }

    public void addStateListener(){
        this.currentState.addListener(new ChangeListener<State>() {
            @Override
            public void changed(ObservableValue<? extends State> observable, State oldValue, State newValue) {
                if(newValue == State.PLAYER_1_WIN || newValue == State.PLAYER_2_WIN)
                   gameView.showEndOfGameInfo();
                    // System.out.println("Ktoś tam wygrał");
                if(newValue == State.PLAYER_1_MOVE || newValue == State.PLAYER_2_MOVE){
                    if(gameModel.getCurrentPlayer().isHuman())
                        return;
                    else
                        makeAIMove();
                }
            }
        });
    }

    public void initAIService(){
        this.aiserv = new AIService();
        this.aiserv.valueProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue != null){
                System.out.println("AI");
                this.gameModel.setState((MultiGameState) newValue);
                System.out.printf("%d\n", gameModel.getState().getP());
                gameView.updateUI();
                updateState();

                gameView.addLastMoveToHistory();
            }
        });
    }

    public void restart() {
        initGame(prev_p, prev_n, prev_x);
        gameView.updateUI();
        startGame();
    }

    public void startGame() {
        this.gameModel.initCurrent();
        updateState();
    }

    public void makeAIMove(){
        this.aiserv.setState(this.gameModel.getState());
        this.aiserv.setAIDepth(this.gameModel.getCurrentPlayer().getAIDepth());
        this.aiserv.restart();
    }

    public void makeMove(int x) {
        if (gameModel.getState().isTerminated())
            return;
        if (x < gameModel.getState().minX() || x > gameModel.getState().maxX())
            return;
        gameModel.makeMove(x);
        gameView.updateUI();
        updateState();
        gameView.addLastMoveToHistory();
    }

    private void updateState(){
        this.currentState.set(gameModel.getCurrentMove());
    }

    public int getP() {
        return gameModel.getState().getP();
    }

    public int getN() {
        return gameModel.getState().getN();
    }

    public int[] getX() {
        return gameModel.getX();
    }

    public GameModel getModel() {
        return gameModel;
    }

    public GameView getGameView() {
        return gameView;
    }

    public String lastMoveToString(){
        return gameModel.getLastPValue() + " * " + gameModel.getLastXValue() + " = " + gameModel.getState().getP();
    }
}

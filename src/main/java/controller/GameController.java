package controller;

import algorithm.AlgorithmAI;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import model.GameModel;
import model.GameModel.State;
import model.Player.Character;
import view.GameView;

public class GameController {

    private GameModel gameModel;
    private GameView gameView;
    private int prev_p;
    private int prev_n;
    private int prev_x;
    private SimpleObjectProperty<State> currentState;

    public GameController(GameView gameView) {
        this.gameView = gameView;
        gameModel = new GameModel();
    }

    public void initGame(int p, int n, int x) {
        // temporary code
        int tab[] = new int[x];
        for (int i = 1; i <= x; i++)
            tab[i - 1] = i+1;//begins with 2 instead 1

        try {
            gameModel.initGame(n, p, tab);
        } catch (Exception e) {
            e.printStackTrace();
        }
        prev_n = n;
        prev_p = p;
        prev_x = x;
        currentState = new SimpleObjectProperty<>(State.INIT);
    }

    public void initPlayer1(boolean isHuman, int AIDepth){
        this.gameModel.initPlayer1(isHuman,AIDepth);
    }

    public void initPlayer2(boolean isHuman, int AIDepth){
        this.gameModel.initPlayer2(isHuman, AIDepth);
    }

    public void addStateListener(){
        this.currentState.addListener(new ChangeListener<State>() {
            @Override
            public void changed(ObservableValue<? extends State> observable, State oldValue, State newValue) {
                if(newValue == State.PLAYER_1_WIN || newValue == State.PLAYER_2_WIN)
                    System.out.println("Ktoś tam wygrał");
                if(newValue == State.PLAYER_1_MOVE || newValue == State.PLAYER_2_MOVE){
                    if(gameModel.getCurrentPlayer().getPchar() == Character.HUMAN)
                        return;
                    else
                        makeAIMove(gameModel.getCurrentPlayer().getAIDepth());
                }
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
        addStateListener();
        updateState();
    }

    public void makeAIMove(int depth){
        System.out.println("AI");
        gameModel.setState(AlgorithmAI.bestMove(gameModel.getState(), depth));
        System.out.printf("%d\n", gameModel.getState().getP());
        gameView.updateUI();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        updateState();

    }

    public void makeMove(int x) {
        if (gameModel.getState().isTerminated())
            return;
        if (x < gameModel.getState().minX() || x > gameModel.getState().maxX())
            return;
        gameModel.makeMove(x);
        gameView.updateUI();
        updateState();
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

    public GameModel getModel() {
        return gameModel;
    }

    public GameView getGameView() {
        return gameView;
    }
}

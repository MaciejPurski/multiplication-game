package controller;

import algorithm.AlgorithmAI;
import model.GameModel;
import view.GameView;

import java.util.concurrent.CountDownLatch;

public class GameController {

    private GameModel gameModel;
    private boolean isUsingAI;
    private GameView gameView;
    private int prev_p;
    private int prev_n;
    private int prev_x;
    private int[] treeDepth;
    private boolean[] isHumanPlayer;

    public GameController(GameView gameView) {
        this.gameView = gameView;
        gameModel = new GameModel();
    }

    public void initGame(int p, int n, int x) {
        // temporary code
        int tab[] = new int[x];
        for (int i = 1; i <= x; i++)
            tab[i - 1] = i;

        try {
            gameModel.initGame(n, p, tab);
        } catch (Exception e) {
            e.printStackTrace();
        }
        prev_n = n;
        prev_p = p;
        prev_x = x;
    }

    public void restart() {
        initGame(prev_p, prev_n, prev_x);
        startGame();
        gameView.updateUI();
    }

    public int initAI(boolean isHumanPlayer1, boolean isHumanPlayer2, int treeDepth1, int treeDepth2) {
        if (treeDepth1 < 0 || treeDepth2 < 0)
            return -1;

        if (!isUsingAI) {
            isUsingAI = true;
            isHumanPlayer = new boolean [] {isHumanPlayer1, isHumanPlayer2};
            treeDepth = new int [] {treeDepth1, treeDepth2};
        }

        return 0;
    }

    public void makeMove(int x) {
        if (gameModel.getState().isTerminated())
            return;
        if (x < gameModel.getState().minX() || x > gameModel.getState().maxX())
            return;
        gameModel.makeMove(x);

        if (isUsingAI) {
            System.out.println("AI");
            gameModel.setState(AlgorithmAI.bestMove(gameModel.getState(), treeDepth[1]));
            System.out.printf("%d\n", gameModel.getState().getP());
        }
        gameView.updateUI();
    }

    public void startGame() {
        // call AI
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

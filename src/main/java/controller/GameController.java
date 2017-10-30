package controller;

import model.GameModel;
import view.GameView;

import java.util.concurrent.CountDownLatch;

import static controller.GameController.GameMove.*;

public class GameController {
    public enum GameMove { PLAYER_1, PLAYER_2, NOT_INITIALIZED, FINISHED };

    private GameMove currentMove;
    private GameModel gameModel;
    private AIRunnable moduleAI;
    private boolean isUsingAI;
    private GameView gameView;

    public void initGame(GameView gameView, int p, int n, int x) {
        this.gameView = gameView;
        currentMove = NOT_INITIALIZED;
        this.isUsingAI = false;
    }

    public int initAI(boolean isHumanPlayer1, boolean isHumanPlayer2, int treeDepth1, int treeDepth2) {
        if (treeDepth1 < 0 || treeDepth2 < 0)
            return -1;

        if (!isUsingAI) {
            isUsingAI = true;
            moduleAI = new AIRunnable(this, isHumanPlayer1, isHumanPlayer2, treeDepth1, treeDepth2);
        }


        return 0;
    }

    public void startGame() {
        currentMove = PLAYER_1;

        // call AI
        if (isUsingAI)
            new Thread(moduleAI);
    }

    public GameMove getCurrentMove() {
        return currentMove;
    }

    public void setCurrentMove(GameMove currentMove) {
        this.currentMove = currentMove;
    }

    public GameModel getModel() {
        return gameModel;
    }

    public GameView getGameView() {
        return gameView;
    }
}

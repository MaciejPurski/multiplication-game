package controller;

import java.util.concurrent.CountDownLatch;

import static controller.GameController.GameMove.*;

public class AIRunnable implements Runnable {
    private GameController gameController;
    private int[] treeDepth;
    private boolean[] isHumanPlayer;
    private CountDownLatch latch;

    public AIRunnable (GameController gameController, boolean isHumanPlayer1, boolean isHumanPlayer2, int treeDepth1, int treeDepth2) {
        this.gameController = gameController;
        isHumanPlayer = new boolean [] {isHumanPlayer1, isHumanPlayer2};
        treeDepth = new int [] {treeDepth1, treeDepth2};
        latch = new CountDownLatch(1);
    }

    public void run() {
        while (gameController.getCurrentMove() != FINISHED) {
            if (!isHumanPlayer[0] && gameController.getCurrentMove() == PLAYER_1) {
                //call AI for player 1
                //check if game is finished
                if (gameController.getCurrentMove() == FINISHED)
                    break;
                else
                    gameController.setCurrentMove(PLAYER_2);
            }

            try {
                if (isHumanPlayer[1])
                    latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }

            if (!isHumanPlayer[1] && gameController.getCurrentMove() == PLAYER_2) {
                //call AI for player 2
                //check if game is finished
                if (gameController.getCurrentMove() == FINISHED)
                    break;
                else
                    gameController.setCurrentMove(PLAYER_1);
            }

            try {
                if (isHumanPlayer[0])
                    latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
    }

    public void wakeUp() {
        latch.countDown();
    }
}

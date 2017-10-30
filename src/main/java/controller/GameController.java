package controller;

public class GameController {
    private boolean isHumanPlayer1, isHumanPlayer2;

    public void initGame(int p, int n, int x, boolean isHumanPlayer1, boolean isHumanPlayer2) {
        this.isHumanPlayer1 = isHumanPlayer1;
        this.isHumanPlayer2 = isHumanPlayer2;
    }
}

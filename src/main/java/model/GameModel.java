package model;

public class GameModel {
    private GameState gameState;


    public GameModel(int n, int p, int[] x) throws Exception {
        gameState = new GameState(n, p, x);
    }

    public void makeMove(int x) throws Exception {
        if (gameState.isGameEnded())
            throw new Exception("Game is Ended");
        gameState = gameState.nextState(x);
    }

    public GameState getGameState() {
        return gameState;
    }

    public GameState.State getSimpleGameState() {
        return gameState.getGameState();
    }



}
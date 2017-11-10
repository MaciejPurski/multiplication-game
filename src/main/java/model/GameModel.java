package model;

public class GameModel {
    private GameState gameState;
    private State currentMove;

    public void initGame(int n, int p, int[] x) throws Exception{
        gameState = new GameState(n, p, x);
        currentMove = State.PLAYER_1_MOVE;
    }

    public GameModel() {
    }

    private void updateGameState() {
        if (currentMove == State.PLAYER_1_MOVE) {
            if (gameState.isTerminated())
                currentMove = State.PLAYER_1_WIN;
            else
                currentMove = State.PLAYER_2_MOVE;
        } else {
            if (gameState.isTerminated())
                currentMove = State.PLAYER_2_WIN;
            else
                currentMove = State.PLAYER_1_MOVE;
        }
    }

    public void makeMove(int x) {
        gameState = gameState.nextState(x);
        updateGameState();
    }

    public GameState getState() {
        return gameState;
    }


    public void setState(GameState nState) {
        gameState = nState;
        updateGameState();
    }

    public State getCurrentMove() {
        return currentMove;
    }

    public boolean isFinished() {
        return gameState.isTerminated();
    }

    public enum State {PLAYER_1_MOVE, PLAYER_2_MOVE, PLAYER_1_WIN, PLAYER_2_WIN}
}
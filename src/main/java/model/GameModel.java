package model;

public class GameModel {
    private GameState gameState;
    private State currentMove;
    private Player[] players;
    private Player currentPlayer;

    public void initGame(int n, int p, int[] x) throws Exception{
        gameState = new GameState(n, p, x);
        currentMove = State.PLAYER_1_MOVE;
    }

    public GameModel() {
        players = new Player[2];
    }

    public void initPlayer(int index, boolean isHuman, int AIDepth){
        if(isHuman)
            this.players[index] = new Player();
        else
            this.players[index] = new Player(AIDepth);
    }

    public void initCurrent(){
        this.currentPlayer = this.players[0];
    }

    private void updateGameState() {
        if (currentMove == State.PLAYER_1_MOVE) {
            if (gameState.isTerminated())
                currentMove = State.PLAYER_1_WIN;
            else {
                currentMove = State.PLAYER_2_MOVE;
                currentPlayer = players[1];
            }
        } else {
            if (gameState.isTerminated())
                currentMove = State.PLAYER_2_WIN;
            else {
                currentMove = State.PLAYER_1_MOVE;
                currentPlayer = players[0];
            }
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

    public boolean isAnyAI() {
        return (!(this.players[0].isHuman()) || !(this.players[1].isHuman()));
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public State getCurrentMove() {
        return currentMove;
    }

    public boolean isFinished() {
        return gameState.isTerminated();
    }

    public enum State {PLAYER_1_MOVE, PLAYER_2_MOVE, PLAYER_1_WIN, PLAYER_2_WIN,INIT}
}
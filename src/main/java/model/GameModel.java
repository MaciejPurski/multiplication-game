package model;

public class GameModel {
    private MultiGameState gameState;
    private State currentMove;
    private Player[] players;
    private Player currentPlayer;
    private int lastXValue;
    private int lastPValue;

    public void initGame(int n, int p, int[] x) throws Exception{
        gameState = new MultiGameState(n, p, x);
        currentMove = State.PLAYER_1_MOVE;
    }

    public GameModel() {
        players = new Player[2];
        lastXValue = 0;
        lastPValue = 0;
    }

    public void initPlayer(int index, boolean isHuman, int AIDepth){
        if (isHuman)
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
        lastPValue = gameState.getP();
        gameState = gameState.nextState(x);
        updateGameState();
        lastXValue = x;
    }

    public MultiGameState getState() {
        return gameState;
    }


    public void setState(MultiGameState nState) {
        lastPValue = gameState.getP();
        lastXValue = nState.getP() / gameState.getP();
        gameState = nState;
        updateGameState();
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

    public int[] getX(){
        return gameState.getX();
    }

    public int getLastXValue(){
        return lastXValue;
    }

    public int getLastPValue(){
        return lastPValue;
    }

    public Player getPlayer(int index){
        return this.players[index];
    }
}
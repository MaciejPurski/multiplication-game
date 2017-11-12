package model;

public class GameModel {
    private GameState gameState;
    private State currentMove;
    private Player player1;
    private Player player2;
    private Player currentPlayer;

    public void initGame(int n, int p, int[] x) throws Exception{
        gameState = new GameState(n, p, x);
        currentMove = State.PLAYER_1_MOVE;
    }

    public GameModel() {
    }

    public void initPlayer1(boolean isHuman, int AIDepth){
        if(isHuman)
            this.player1 = new Player();
        else
            this.player1 = new Player(AIDepth);
    }

    public void initPlayer2(boolean isHuman, int AIDepth){
        if(isHuman)
            this.player2 = new Player();
        else
            this.player2 = new Player(AIDepth);
    }

    public void initCurrent(){
        this.currentPlayer = this.player1;
    }

    private void updateGameState() {
        if (currentMove == State.PLAYER_1_MOVE) {
            if (gameState.isTerminated())
                currentMove = State.PLAYER_1_WIN;
            else {
                currentMove = State.PLAYER_2_MOVE;
                currentPlayer = player2;
            }
        } else {
            if (gameState.isTerminated())
                currentMove = State.PLAYER_2_WIN;
            else {
                currentMove = State.PLAYER_1_MOVE;
                currentPlayer = player1;
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

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
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
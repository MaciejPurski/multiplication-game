package model;


import java.util.Arrays;

public class GameState extends algorithm.GameState {
    private int n;
    private int p;
    private int[] x;
    private State gameState;


    public GameState(int n, int p, int[] x) throws Exception {
        if (p >= n)
            throw new Exception("Wrong starting values!!! (p>=n)");
        this.n = n;
        this.p = p;
        this.x = x;
        gameState = State.PLAYER_1_MOVE;
    }

    public GameState(GameState other) {
        n = other.n;
        p = other.p;
        x = other.x;
        gameState = other.gameState;
    }

    private int minX(){
        return x[0];
    }

    private int maxX(){
        return x[x.length-1];
    }

    /**
     * Method used to valuate given GameState.
     *
     * @return Valuation of given GameState - the higher, the better
     * @param isMaximizingState boolean that indicates who would perform next move from given GameState
     */
    public int valuate(boolean isMaximizingState) {
        if(this.isGameEnded()){
            if(isMaximizingState)
                return Integer.MIN_VALUE;// MIN_VALUE because it's a state reached after opponent's decision. If it's terminal it means that we lose.
            else
                return Integer.MAX_VALUE;
        }
        else{
            double n = (double)this.getN();
            double p = (double)this.getP();
            int toVictory = (int)(Math.ceil(n/p));
            if((toVictory >= this.minX()) && (toVictory <= this.maxX())){ //somebody can achieve victory from this state
                if(isMaximizingState)
                    return Integer.MAX_VALUE - toVictory;
                else
                    return Integer.MIN_VALUE + toVictory;
            }
            else
                return toVictory;
        }
    }

    /**
     * Method used to generate next state after multiplying by x
     *
     * @param x multiplier
     * @return State of game after multiplying by x
     */
    GameState nextState(int x) {
        GameState result = new GameState(this);
        result.p *= x;
        if (result.p >= n)
            result.endGame();
        else
            result.changePlayer();
        return result;
    }

    /**
     * Method generates all possible states, to which the game can move
     * from the given state.
     *
     * @return array of possible states
     */
    public GameState[] getPossibleStates() {
        GameState[] result = new GameState[x.length];
        for (int i = 0; i < x.length; ++i) {
            result[i] = nextState(x[i]);
        }
        return result;
    }

    /**
     * Method used to check if game is ended
     *
     * @return true if game is ended
     */
    public boolean isGameEnded() {
        return gameState == State.PLAYER_1_WIN || gameState == State.PLAYER_2_WIN;
    }

    private void changePlayer() {
        if (gameState == State.PLAYER_1_MOVE)
            gameState = State.PLAYER_2_MOVE;
        else
            gameState = State.PLAYER_1_MOVE;
    }

    private void endGame() {
        if (gameState == State.PLAYER_1_MOVE)
            gameState = State.PLAYER_1_WIN;
        else
            gameState = State.PLAYER_2_WIN;
    }

    public int getN() {
        return n;
    }

    public int getP() {
        return p;
    }

    public int[] getX() {
        return x;
    }

    public State getGameState() {
        return gameState;
    }

    @Override
    public String toString() {
        return "GameState{" +
                "gameState=" + gameState +
                ", n=" + n +
                ", p=" + p +
                ", x=" + Arrays.toString(x) +
                '}';
    }

    enum State {PLAYER_1_MOVE, PLAYER_2_MOVE, PLAYER_1_WIN, PLAYER_2_WIN}
}

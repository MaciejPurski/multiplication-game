package model;


import algorithm.GameState;

import java.util.Arrays;

public class MultiGameState extends GameState {
    private int n;
    private int p;
    private int[] x;

    public MultiGameState(int n, int p, int[] x) {
        this.n = n;
        this.p = p;
        this.x = x;
    }

    public MultiGameState(MultiGameState other) {
        n = other.n;
        p = other.p;
        x = other.x;
    }

    public int minX(){
        return x[0];
    }

    public int maxX(){
        return x[x.length-1];
    }

    /**
     * Method used to valuate given GameState.
     *
     * @return Valuation of given GameState - the higher, the better
     * @param isMaximizingState boolean that indicates who would perform next move from given GameState
     */
    public int valuate(boolean isMaximizingState) {
        if(this.isTerminated()){
            if(isMaximizingState) {
                return Integer.MIN_VALUE;// MIN_VALUE because it's a state reached after opponent's decision. If it's terminal it means that we lose.
            }
            else {
                return Integer.MAX_VALUE;
            }
        }
        else{
            double n = (double)this.getN();
            double p = (double)this.getP();
            int toVictory = (int)(Math.ceil(n/p));
            if(toVictory <= this.maxX()){ //somebody can achieve victory from this state
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
    MultiGameState nextState(int x) {
        MultiGameState result = new MultiGameState(this);
        result.p *= x;


        return result;
    }

    /**
     * Method generates all possible states, to which the game can move
     * from the given state.
     *
     * @return array of possible states
     */
    public MultiGameState[] getPossibleStates() {
        MultiGameState[] result = new MultiGameState[x.length];
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
    public boolean isTerminated() {
        return p >= n;
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

    @Override
    public String toString() {
        return "GameState{" +
                ", n=" + n +
                ", p=" + p +
                ", x=" + Arrays.toString(x) +
                '}';
    }
}

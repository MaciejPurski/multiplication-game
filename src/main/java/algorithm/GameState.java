package algorithm;

public abstract class GameState {
    /**
     * Method used to valuate given GameState.
     * @return Valuation of given GameState - the higher, the better
     */
    public abstract int valuate(boolean isMaximazingState);

    /**
     * Method generates all possible states, to which the game can move
     * from the given state.
     * @return array of possible states
     */
    public abstract GameState[] getPossibleStates();

    public abstract boolean isTerminated();
}

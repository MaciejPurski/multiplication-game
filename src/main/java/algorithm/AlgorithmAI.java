package algorithm;

public class AlgorithmAI {
    /**
     * Method, which performs min-max algorithm with alpha-beta cut. It recursively
     * searches for best possible move, while the game is in currentState.
     * @param currentState a parameter which fully describes current game state
     * @param player current player: true if it is a player for whom we want to perform
     *               the best move, false if it's a foe
     * @param levelsLeft number of levels of the search tree to perform the algorithm
     *                   if it's equal to 0, then we're on the last level
     * @return the new best GameState
     */
    public static GameState minMax(GameState currentState, boolean player, int levelsLeft) {
        GameState possibleStates[] = currentState.getPossibleStates(); //find possible moves

        //perform algorithm in order to find best state

        return null;
    }
}

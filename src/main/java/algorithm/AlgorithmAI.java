package algorithm;

public class AlgorithmAI {
    /**
     * Method, which performs minMax to find next best move
     * @param currentState a root of a game tree
     * @param depth depth of min-max search
     * @return next move to perform
     * */
    public static GameState bestMove(GameState currentState, int depth) {
        GameState possibleStates[] = currentState.getPossibleStates();
        GameState nextMove = possibleStates[0];
        // we're on a root level, so we will maximize the values from our children-nodes

        int alpha = Integer.MIN_VALUE;
        for (GameState state: possibleStates) {
            int value = minMax(state, false, depth-1, alpha, Integer.MAX_VALUE);
            //assign greater value to alpha
            if (value > alpha){
                alpha = value;
                nextMove = state;
            }
        }
        return nextMove;
    }

    /**
     * Method, which performs min-max algorithm with alpha-beta cut. It recursively
     * searches for best possible move, while the game is in currentState.
     * @param currentState a parameter which fully describes current game state
     * @param isMaximizingPlayer current player: true if it is a player for whom we want to perform
     *               the best move, false if it's a foe
     * @param levelsLeft number of levels of the search tree to perform the algorithm
     *                   if it's equal to 0, then we're on the last level
     * @param alpha actual alpha value passed from parent node
     * @param beta  actual beta value passed from parent node
     * @return value from min-max algorithm
     */
    private static int minMax(GameState currentState, boolean isMaximizingPlayer, int levelsLeft, int alpha, int beta) {
        if(currentState.isTerminated())
            return currentState.valuate(isMaximizingPlayer);

        if (levelsLeft == 0)
            return currentState.valuate(isMaximizingPlayer);

        //find possible moves
        GameState possibleStates[] = currentState.getPossibleStates();

        if (isMaximizingPlayer) {
            int best = Integer.MIN_VALUE;
            for (GameState state: possibleStates) {
                int value = minMax(state, false, levelsLeft - 1, alpha, beta);

                best = Math.max(best, value);
                alpha = Math.max(alpha, best);

                //cutting condition
                if(beta <= alpha)
                    break;
            }
            return best;
        }

        else {
            int best = Integer.MAX_VALUE;
            for (GameState state: possibleStates) {
                int value = minMax(state, true, levelsLeft - 1, alpha, beta);

                best = Math.min(best, value);
                beta = Math.min(beta, best);

                //cutting condition
                if (beta <= alpha)
                    break;
            }
            return best;
        }
    }
}

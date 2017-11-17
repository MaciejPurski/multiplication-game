package controller;

import algorithm.AlgorithmAI;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import model.GameState;

public class AIService extends Service<GameState> {

    private GameState state;
    private int AIDepth;

    public void setState(GameState state) {
        this.state = state;
    }

    public void setAIDepth(int AIDepth) {
        this.AIDepth = AIDepth;
    }

    @Override
    protected Task<GameState> createTask() {
        return new Task<GameState>() {
            @Override
            protected GameState call() throws Exception {
                return AlgorithmAI.bestMove(state,AIDepth);
            }
        };
    }
}

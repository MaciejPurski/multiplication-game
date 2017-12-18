package report;

import controller.AIService;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import model.GameModel;
import model.MultiGameState;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class GameControllerTestClass {
    private GameModel gameModel;
    private int prev_p;
    private int prev_n;
    private int[] prev_x;
    private SimpleObjectProperty<GameModel.State> currentState;
    private AIService aiserv;
    private PrintWriter report;
    private int attemptsNumber;
    private int loopCounter;
    private int deepeningStep = 0;
    private int deepeningCount = 0;
    private int outNumber = 1;
    private String outFilename;

    public GameControllerTestClass(PrintWriter report, int attemptsNumber) {
        gameModel = new GameModel();
        this.report = report;
        this.attemptsNumber = attemptsNumber;
        this.loopCounter = attemptsNumber;
        currentState = new SimpleObjectProperty<>(GameModel.State.INIT);
        addStateListener();
        initAIService();
    }

    public void initGame(int p, int n, int[] x) {
        try {
            gameModel.initGame(n, p, x);
        } catch (Exception e) {
            e.printStackTrace();
        }
        prev_n = n;
        prev_p = p;
        prev_x = x;
    }

    public void initPlayer(int index, boolean isHuman, int AIDepth){
        gameModel.initPlayer(index, isHuman, AIDepth);
    }

    private void endOfGame(){
        if(gameModel.getCurrentMove() == GameModel.State.PLAYER_1_WIN)
            report.println("Wygrana gracza 1 "+" (N = "+this.prev_n+")");
        else
            report.println("Wygrana gracza 2 "+" (N = "+this.prev_n+")");
        loopCounter--;
        if (loopCounter > 0) {
            prev_n++;
            restart();
        } else {
            if (deepeningCount > 0) {
                report.close();
                loopCounter = attemptsNumber;
                prev_n = prev_n - attemptsNumber + 1;
                try {
                    report = new PrintWriter(outFilename + outNumber + ".txt");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    System.exit(1);
                }
                outNumber++;
                deepeningCount--;
                int oldDepth = gameModel.getPlayer(1).getAIDepth();
                initPlayer(1, false, oldDepth + deepeningStep);
                printReportHeader();
                restart();
            }
            else {
                report.close();
                System.exit(0);
            }
        }
    }

    public void setDeepening(int step, int count){
        deepeningStep = step;
        deepeningCount = count;
    }

    public void setOutFilename(String outFilename) {
        this.outFilename = outFilename;
    }

    private void printReportHeader(){
        report.println("P1 tree depth:" + gameModel.getPlayer(0).getAIDepth()+" P2 tree depth:" + gameModel.getPlayer(1).getAIDepth());
        report.println("P = " + prev_p + " N = " + prev_n);
        report.print("X set: ");
        for (int i: prev_x) {
            report.print(i+" ");
        }
        report.println();
        report.println("Game results: ");
    }

    public void addStateListener(){
        this.currentState.addListener(new ChangeListener<GameModel.State>() {
            @Override
            public void changed(ObservableValue<? extends GameModel.State> observable, GameModel.State oldValue, GameModel.State newValue) {
                if(newValue == GameModel.State.PLAYER_1_WIN || newValue == GameModel.State.PLAYER_2_WIN)
                    endOfGame();
                if(newValue == GameModel.State.PLAYER_1_MOVE || newValue == GameModel.State.PLAYER_2_MOVE){
                    makeAIMove();
                }
            }
        });
    }

    public void initAIService(){
        this.aiserv = new AIService();
        this.aiserv.valueProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue != null){
                this.gameModel.setState((MultiGameState) newValue);
                updateState();
            }
        });
    }

    public void restart() {
        initGame(prev_p, prev_n, prev_x);
        startGame();
    }

    public void startGame() {
        gameModel.initCurrent();
        updateState();
    }

    public void makeAIMove(){
        this.aiserv.setState(this.gameModel.getState());
        this.aiserv.setAIDepth(this.gameModel.getCurrentPlayer().getAIDepth());
        this.aiserv.restart();
    }

    private void updateState(){
        this.currentState.set(gameModel.getCurrentMove());
    }

    public int getP() {
        return gameModel.getState().getP();
    }

    public int getN() {
        return gameModel.getState().getN();
    }

    public int[] getX() {
        return gameModel.getX();
    }

    public GameModel getModel() {
        return gameModel;
    }

    public String lastMoveToString(){
        return gameModel.getLastPValue() + " * " + gameModel.getLastXValue() + " = " + gameModel.getState().getP();
    }
}

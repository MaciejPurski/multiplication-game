package view;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import report.GameControllerTestClass;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ReportView {
    @FXML
    private TextField p1Depth, p2Depth, startingValue, endValue, attemptsCount, outputFile, xSet, deepeningStep, deepeningCount;

    private PrintWriter reportFile;
    private GameControllerTestClass testClass;
    private int[] x;

    @FXML
    public void onGeneratePressed() throws FileNotFoundException {
        this.reportFile = new PrintWriter(this.outputFile.getText()+".txt");
        this.reportFile.println("P1 tree depth:"+p1Depth.getText()+" P2 tree depth:"+p2Depth.getText());
        this.reportFile.println("P = "+startingValue.getText()+" N = "+endValue.getText());
        this.testClass = new GameControllerTestClass(this.reportFile, Integer.parseInt(this.attemptsCount.getText()));
        this.testClass.initPlayer(0, false, Integer.parseInt(this.p1Depth.getText()));
        this.testClass.initPlayer(1, false, Integer.parseInt(this.p2Depth.getText()));
        this.reportFile.print("X set: ");
        String xList = this.xSet.getText();
        String[] xValues = xList.split(",");
        this.x = new int[xValues.length];
        for (int i = 0; i < xValues.length; i++) {
            this.reportFile.print(xValues[i]+" ");
            x[i] = Integer.parseInt(xValues[i]);
        }
        if(!(this.deepeningCount.getText().isEmpty())){
            this.testClass.setDeepening(Integer.parseInt(this.deepeningStep.getText()),Integer.parseInt(this.deepeningCount.getText()));
            this.testClass.setOutFilename(this.outputFile.getText());
        }
        this.reportFile.println();
        this.reportFile.println("Game results: ");
        this.testClass.initGame(Integer.parseInt(this.startingValue.getText()), Integer.parseInt(this.endValue.getText()), this.x);
        this.testClass.startGame();
    }
}

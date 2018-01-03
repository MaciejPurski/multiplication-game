package view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import report.GameControllerTestClass;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
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
        if (checkIfEmpty())
            return;
        if (!xSetParse())
            return;
        reportFile = new PrintWriter(outputFile.getText()+".txt");
        reportFile.println("P1 tree depth:"+p1Depth.getText()+" P2 tree depth:"+p2Depth.getText());
        reportFile.println("P = "+startingValue.getText()+" N = "+endValue.getText());
        testClass = new GameControllerTestClass(reportFile, Integer.parseInt(attemptsCount.getText()));
        testClass.initPlayer(0, false, Integer.parseInt(p1Depth.getText()));
        testClass.initPlayer(1, false, Integer.parseInt(p2Depth.getText()));
        reportFile.print("X set: ");
        for (int i: x) {
            reportFile.print(i + " ");
        }
        if (!(this.deepeningCount.getText().isEmpty())) {
            testClass.setDeepening(Integer.parseInt(deepeningStep.getText()),Integer.parseInt(deepeningCount.getText()));
            testClass.setOutFilename(outputFile.getText());
        }
        reportFile.println();
        reportFile.println("Game results: ");
        testClass.initGame(Integer.parseInt(startingValue.getText()), Integer.parseInt(endValue.getText()), this.x);
        testClass.startGame();
    }

    private boolean checkIfEmpty(){
        boolean result = true;
        if (p1Depth.getText().isEmpty() || p2Depth.getText().isEmpty() || startingValue.getText().isEmpty() || endValue.getText().isEmpty()
                || attemptsCount.getText().isEmpty() || outputFile.getText().isEmpty() || xSet.getText().isEmpty()) {
            showAlert("Empty fields", "Some of the mandatory fields stay empty!. Fill in all missing fields and try again.");
            return result;
        }
        if (!(deepeningStep.getText().isEmpty()) && deepeningCount.getText().isEmpty()) {
            showAlert("Deepening count missing", "Deepening step is specified but deepening count is empty. Enter the deepening count and try again.");
            return result;
        }
        if (!(deepeningCount.getText().isEmpty()) && deepeningStep.getText().isEmpty()) {
            showAlert("Deepening step missing", "Deepening count is specified but deepening step is empty. Enter the deepening step and try again.");
            return result;
        }
        return false;
    }

    private void showAlert(String title, String message){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private boolean xSetParse(){
        String xList = xSet.getText();
        String[] xValues = xList.split(",");
        List<Integer> integerList = new ArrayList<Integer>();
        int temp;
        for (String s: xValues) {
            if (s.contains(" ")) {
                showAlert("Space", "List of x values contains spaces. Put your numbers separated with commas, without spaces!");
                return false;
            }
            try {
                temp = Integer.parseInt(s);
            } catch (Exception e) {
                showAlert("Parse error", "Cannot parse x values list. Check if all values are correct");
                return false;
            }
            if (temp < 2) {
                showAlert("Wrong value", "X should not be less than 2");
                return false;
            }
            if (integerList.contains(temp))
                continue;
            integerList.add(temp);
        }
        integerList.sort(null);
        x = Arrays.stream(integerList.toArray(new Integer[integerList.size()])).mapToInt(Integer::intValue).toArray();
        return true;
    }
}

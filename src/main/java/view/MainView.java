package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class MainView {

    @FXML
    private Button playButton, generateButton;

    @FXML
    public void playGame() throws IOException {
        Stage stage;
        stage = (Stage) this.playButton.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Game.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    public void generateReports() throws IOException {
        Stage stage;
        stage = (Stage) this.generateButton.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Report.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}

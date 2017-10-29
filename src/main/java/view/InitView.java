package view;

import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

public class InitView {
    @FXML
    private RadioButton player1Human, player2Human;

    @FXML
    private TextField player1Text, player2Text;

    @FXML
    public void onStartClicked() {}

    @FXML
    public void onCancelClicked() {}
}

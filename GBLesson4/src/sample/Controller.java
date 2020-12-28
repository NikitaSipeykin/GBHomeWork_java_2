package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class Controller {
    
    @FXML
    public TextField text;

    @FXML
    public TextArea chat;

    @FXML
    public void sendText(ActionEvent actionEvent) {
        chat.appendText(text.getText()+"\n");
        text.clear();
        text.requestFocus();
    }
}

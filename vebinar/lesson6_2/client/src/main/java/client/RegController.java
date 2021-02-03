package client;

import javafx.event.ActionEvent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class RegController {
    public TextField loginField;
    public PasswordField passwordField;
    public TextField nicknameField;
    public TextArea textArea;
    private Controller controller;

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void tryToReg(ActionEvent actionEvent) {
        String login = loginField.getText().trim();
        String password = passwordField.getText().trim();
        String nickname = nicknameField.getText().trim();

        controller.tryToReg(login, password, nickname);
    }

    public void regok(){
        textArea.appendText("Регистрация прошла успешно\n");
    }

    public void regno(){
        textArea.appendText("Логин или никнейм уже заняты\n");
    }
}

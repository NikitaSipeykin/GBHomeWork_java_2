package client;

import commands.Command;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

    // работает непосредственно с fxml окна клиентского чата
public class Controller implements Initializable {
    @FXML
    public HBox authPanel;
    @FXML
    public TextField loginField;
    @FXML
    public PasswordField passwordField;
    @FXML
    public HBox msgPanel;
    @FXML
    private TextArea textArea;  //эллемент fxml текстовое поле
    @FXML
    private TextField textField;   //эллемент fxml поле ввода текста

    private Socket socket;
    private DataInputStream in;   //байтовый поток ввода информации
    private DataOutputStream out;  //байтовый поток вывода информации
    private final String IP_ADDRESS = "localhost";   // айпишник
    private final int PORT = 8189;

    private boolean authenticated;
    private String nickname;

    private Stage stage;

    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
        msgPanel.setVisible(authenticated);
        msgPanel.setManaged(authenticated);
        authPanel.setVisible(!authenticated);
        authPanel.setManaged(!authenticated);
        if(!authenticated){
            nickname = "";
        }
        setTitle(nickname);
        textArea.clear();
    }

    @FXML
    public void sendMsg(ActionEvent actionEvent) {  //выступает после нажатия "отправки текста" из текстового поля
        try {
            out.writeUTF(textField.getText());  // выводит текст из поля ввода текста в текстовое поле
            textField.clear();   //очищает поле ввода текста
            textField.requestFocus();    //переводит фокус на предыдущий эллемент(поле ввода текста)
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(() -> {
            stage = (Stage) textArea.getScene().getWindow();
        });
        setAuthenticated(false);
    }

    private void connect(){
        try {
            socket = new Socket(IP_ADDRESS,PORT);
            in = new DataInputStream(socket.getInputStream());  //считывает информацию ввода
            out = new DataOutputStream(socket.getOutputStream());    //считывает информацию вывода

            new Thread(()->{
                try {

                    //цикл аутентификации
                    while(true){
                        String str = in.readUTF();    //считывает  вводимою информацию
                        if (str.startsWith("/")){
                            if (str.startsWith(Command.AUTH_OK)){
                                nickname = str.split("\\s")[1];
                                setAuthenticated(true);
                                break;
                            }

                            if(str.equals(Command.END)){    //команда отключения клиента от сервера
                                System.out.println("Client disconnected");
                                throw new RuntimeException("server disconnected us");
                            }
                        }else {
                            textArea.appendText(str + "\n");   //выводит текст в чат
                        }
                    }

                    //цикл работы
                    while(true){
                        String str = in.readUTF();    //считывает  вводимою информацию

                        if(str.equals(Command.END)){    //команда отключения клиента от сервера
                            System.out.println("Client disconnected");
                            break;
                        }

                        textArea.appendText(str + "\n");   //выводит текст в чат
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    setAuthenticated(false);
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void tryToAuth(ActionEvent actionEvent) {
        if (socket == null || socket.isClosed()){
            connect();
        }

        String msg = String.format("%s %s %s",Command.AUTH, loginField.getText().trim(), passwordField.getText().trim());

        try {
            out.writeUTF(msg);
            passwordField.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setTitle(String nickname){
        if(nickname.equals("")){
            Platform.runLater(()->{
                stage.setTitle("GeekChat");
            });
        }else {
            Platform.runLater(()-> {
                stage.setTitle(String.format("GeekChat [ %s ]", nickname));
            });
        }
    }
}

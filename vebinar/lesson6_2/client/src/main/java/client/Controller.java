package client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

                    // работает непосредственно с fxml окна клиентского чата
public class Controller implements Initializable {
    @FXML
    private TextArea textArea;  //эллемент fxml текстовое поле
    @FXML
    private TextField textField;   //эллемент fxml поле ввода текста

    private Socket socket;
    private DataInputStream in;   //байтовый поток ввода информации
    private DataOutputStream out;  //байтовый поток вывода информации
    private final String IP_ADDRESS = "localhost";   // айпишник
    private final int PORT = 8189;

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

        try {
            socket = new Socket(IP_ADDRESS,PORT);
            in = new DataInputStream(socket.getInputStream());  //считывает информацию ввода
            out = new DataOutputStream(socket.getOutputStream());    //считывает информацию вывода

            new Thread(()->{
                try {
                    while(true){
                        String str = in.readUTF();    //считывает  вводимою информацию

                        if(str.equals("/end")){    //команда отключения клиента от сервера
                            System.out.println("Client disconnected");
                            break;
                        }

                        textArea.appendText("Client: " + str + "\n");   //выводит текст в чат
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
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
}

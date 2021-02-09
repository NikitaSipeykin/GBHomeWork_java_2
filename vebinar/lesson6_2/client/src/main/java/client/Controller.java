package client;

import commands.Command;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

    // работает непосредственно с fxml окна клиентского чата
public class Controller implements Initializable {
    @FXML
    private HBox authPanel;
    @FXML
    private TextField loginField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private HBox msgPanel;
    @FXML
    private ListView<String> clientList;
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
    private Stage regStage;
    private RegController regController;

    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
        msgPanel.setVisible(authenticated);
        msgPanel.setManaged(authenticated);
        clientList.setVisible(authenticated);
        clientList.setManaged(authenticated);
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
            stage.setOnCloseRequest(event -> {
                System.out.println("bye");
                if(socket != null && !socket.isClosed()){
                    try {
                        out.writeUTF(Command.END);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
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
                    while (true) {
                        String str = in.readUTF();    //считывает  вводимою информацию
                        if (str.startsWith("/")) {
                            if (str.startsWith(Command.AUTH_OK)) {
                                nickname = str.split("\\s")[1];
                                setAuthenticated(true);
                                break;
                            }

                            if (str.equals(Command.END)) {    //команда отключения клиента от сервера
                                System.out.println("Client disconnected");
                                throw new RuntimeException("server disconnected us");
                            }

                            if (str.equals(Command.REG_OK)){
                                regController.regok();
                            }

                            if (str.equals(Command.NO_REG)){
                                regController.regno();
                            }

                        } else {
                            textArea.appendText(str + "\n");   //выводит текст в чат
                        }
                    }

                    //цикл работы
                    while (true) {
                        String str = in.readUTF();    //считывает  вводимою информацию

                        if (str.startsWith("/")){
                            if (str.equals(Command.END)) {    //команда отключения клиента от сервера
                                System.out.println("Client disconnected");
                                break;
                            }

                            if (str.startsWith(Command.CLIENT_LIST)){
                                String[] tokens = str.split("\\s");
                                Platform.runLater(()->{
                                    clientList.getItems().clear();
                                    for (int i = 1; i < tokens.length; i++) {
                                        clientList.getItems().add(tokens[i]);
                                    }
                                });
                            }

                            if (str.startsWith(Command.YOUR_NICK)){
                                nickname = str.split(" ")[1];
                                setTitle(nickname);
                            }
                        }else {
                            textArea.appendText(str + "\n");   //выводит текст в чат
                        }
                    }
                }catch (RuntimeException e){
                    System.out.println(e.getMessage());
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

    public void clientListClicked(MouseEvent mouseEvent) {
        System.out.println(clientList.getSelectionModel().getSelectedItem());
        String receiver = clientList.getSelectionModel().getSelectedItem();
        textField.setText(String.format("%s %s ", Command.PRV_MSG, receiver));
    }

    public void registration(ActionEvent actionEvent) {
        if (regStage == null){
            createRegWindow();
        }
        regStage.show();
    }

    private void createRegWindow() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/reg.fxml"));   //адресс fxml
            Parent root = fxmlLoader.load();
            regStage = new Stage();
            regStage.setTitle("GeekChat registration");                                      //наименование окна
            regStage.setScene(new Scene(root, 400, 350));    //размеры окна
            regController = fxmlLoader.getController();
            regController = fxmlLoader.getController();
            regController.setController(this);
            regStage.initModality(Modality.APPLICATION_MODAL);
            regStage.initStyle(StageStyle.UTILITY);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void tryToReg(String login, String password, String nickname){
        if (socket == null || socket.isClosed()){
            connect();
        }

        String msg = String.format("%s %s %s %s",Command.REG, login, password, nickname);
        try {
            out.writeUTF(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

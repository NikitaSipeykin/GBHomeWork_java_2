import commands.Command;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler {
    private Server server;
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private String nickName;

    //отвечает за работу конкретного клиента
    public ClientHandler(Server server, Socket socket) {
        try {
            this.server = server;
            this.socket = socket;
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

            new Thread(()->{
                try {
                    //цикл аутентификации
                    while(true){
                        String str = in.readUTF();

                        if (str.startsWith(Command.AUTH)){
                            String[] token = str.split("\\s");
                            String newNick = server.getAuthService().getNicknameByLoginAndPassword(token[1], token[2]);
                            if (newNick != null){
                                nickName = newNick;
                                sendMsg(Command.AUTH_OK+" " + nickName);
                                server.subscribe(this);
                                System.out.println("Client " + nickName+" connected " + socket.getRemoteSocketAddress());
                                break;
                            }else {
                                sendMsg("Неверный логин / пароль");
                            }
                        }

                        if(str.equals(Command.END)){   //команда отключения клиента от сервера
                            sendMsg(Command.END);
                            System.out.println("Client disconnected");
                            break;   //выход из безконечного цикла
                        }
                    }

                    //цикл работы
                    while(true){
                        String str = in.readUTF();

                        if(str.equals(Command.END)){   //команда отключения клиента от сервера
                            sendMsg(Command.END);
                            System.out.println("Client disconnected");
                            break;   //выход из безконечного цикла
                        }

                        server.broadcastMsg(this, str);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    server.unSubscribe(this);
                    try {
                        socket.close();   //закрывает клиентский сокет
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();    //Запускает поток

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMsg(String msg){
        try {
            out.writeUTF(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getNickName() {
        return nickName;
    }
}
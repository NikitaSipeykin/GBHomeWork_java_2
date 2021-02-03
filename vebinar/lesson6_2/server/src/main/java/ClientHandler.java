import commands.Command;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class ClientHandler {
    private Server server;
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private String nickName;
    private String login;

    //отвечает за работу конкретного клиента
    public ClientHandler(Server server, Socket socket) {
        try {
            this.server = server;
            this.socket = socket;
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

            new Thread(()->{
                try {
                    socket.setSoTimeout(120000);
                    //цикл аутентификации
                    while (true) {
                        String str = in.readUTF();

                        if (str.startsWith(Command.AUTH)) {
                            String[] token = str.split("\\s");
                            String newNick = server.getAuthService().getNicknameByLoginAndPassword(token[1], token[2]);
                            login = token[1];
                            if (newNick != null) {
                                if (!server.isLoginAuthenticated(login)) {
                                    nickName = newNick;
                                    sendMsg(Command.AUTH_OK + " " + nickName);
                                    server.subscribe(this);
                                    System.out.println("Client " + nickName + " connected " + socket.getRemoteSocketAddress());
                                    socket.setSoTimeout(0);
                                    break;
                                } else {
                                    sendMsg("Этот логин уже используется");
                                }

                            } else {
                                sendMsg("Неверный логин / пароль");
                            }
                        }

                        if (str.equals(Command.END)) {   //команда отключения клиента от сервера
                            sendMsg(Command.END);
                            throw new RuntimeException("Client disconnected");   //выход из безконечного цикла
                        }

                        if (str.startsWith(Command.REG)) {
                            String[] tokens = str.split("\\s");
                            if (tokens.length < 4) {
                                continue;
                            }
                            boolean regCompleted = server.getAuthService().registration(tokens[1], tokens[2], tokens[3]);
                            if (regCompleted) {
                                sendMsg(Command.REG_OK);
                            } else {
                                sendMsg(Command.NO_REG);
                            }
                        }
                    }

                    //цикл работы
                    while (true) {
                        String str = in.readUTF();

                        if (str.startsWith("/")) {
                            if (str.equals(Command.END)) {   //команда отключения клиента от сервера
                                sendMsg(Command.END);
                                System.out.println("Client disconnected");
                                break;   //выход из безконечного цикла
                            }
                            if (str.startsWith(Command.PRV_MSG)) {
                                String[] token = str.split("\\s", 3);
                                if (token.length < 3) {
                                    continue;
                                }
                                server.privateMsg(this, token[1], token[2]);
                            }
                        } else {
                            server.broadcastMsg(this, str);
                        }
                    }
                }catch (SocketTimeoutException e){
                    sendMsg(Command.END);
                    System.out.println(e.getMessage());
                }catch (RuntimeException e){
                    System.out.println(e.getMessage());
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

    public String getLogin() {
        return login;
    }
}
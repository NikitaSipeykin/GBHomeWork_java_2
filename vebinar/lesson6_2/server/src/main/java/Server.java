import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Server {
    private ServerSocket server;  //серверный сокет
    private Socket socket;   //клиентский сокет - связка ip и порта
    private final int PORT = 8189;   //номер порта
    private List<ClientHandler> clients;    // список подключеных клиентов
    private AuthService authService;

    public Server() {
        clients = new CopyOnWriteArrayList<>();
        authService = new SimpleAuthService();

        try {
            server = new ServerSocket(PORT);   //инициализация серверного сокета
            System.out.println("Server started");


            while(true){   //ждет в бесконечном цикле когда к нему подключатся
                socket = server.accept();   //при подключении клиента выдает клиентский сокет и выбрасывает accept. после, они могут общаться
                System.out.println("Client connected");
                new ClientHandler(this, socket);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                server.close();   //закрывает серверный сокет
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void broadcastMsg(ClientHandler clientHandler, String msg){
        String message = String.format("[ %s ]: %s", clientHandler.getNickName(), msg );
        for (ClientHandler c : clients){
            c.sendMsg(message);
        }
    }

    void subscribe(ClientHandler clientHandler){
        clients.add(clientHandler);
    }

    void unSubscribe(ClientHandler clientHandler){
        clients.remove(clientHandler);
    }

    public AuthService getAuthService() {
        return authService;
    }
}
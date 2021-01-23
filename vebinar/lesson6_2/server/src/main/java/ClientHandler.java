import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler {
    private Server server;
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    //отвечает за работу конкретного клиента
    public ClientHandler(Server server, Socket socket) {
        try {
            this.server = server;
            this.socket = socket;
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

            new Thread(()->{
                try {
                    while(true){
                        String str = in.readUTF();

                        if(str.equals("/end")){   //команда отключения клиента от сервера
                            System.out.println("Client disconnected");
                            break;   //выход из безконечного цикла
                        }

                        server.broadcastMsg(str);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
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
}
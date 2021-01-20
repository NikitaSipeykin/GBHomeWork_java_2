import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
    static ServerSocket server;
    static Socket socket;
    static DataInputStream in;
    static DataOutputStream out;
    static final int PORT = 8189;
    
    public static void main(String[] args) {
        try {
            server = new ServerSocket(PORT);
            System.out.println("Server started");
            socket = server.accept();
            System.out.println("Client connected");

            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

            while(true){
                String str = in.readUTF();

                if(str.equals("/end")){
                    System.out.println("Client disconnected");
                    break;
                }

                System.out.println("Client: " + str);
                out.writeUTF("ECHO: " + str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
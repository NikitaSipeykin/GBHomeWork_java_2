package ru.geekBrains.nikSipeykin.lesson6;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {

// Webinar

    static final int PORT = 8189;

    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(PORT)) {
            System.out.println("Server started");
            try(Socket socket = server.accept()){
                System.out.println("Client connected");
                try (Scanner scanner = new Scanner(socket.getInputStream());
                     PrintWriter out = new PrintWriter(socket.getOutputStream(),true);){
                    while(true){
                        String str = scanner.nextLine();

                        if(str.equals("/end")){
                            System.out.println("Client disconnected");
                            break;
                        }
                        
                        System.out.println("Client: " + str);
                        out.println("ECHO: " + str);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
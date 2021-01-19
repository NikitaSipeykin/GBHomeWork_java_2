package ru.geekBrains.nikSipeykin.lesson6;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {

//TODO:
// 1. Написать консольный вариант клиент\серверного приложения, в котором пользователь может писать сообщения,
// как на клиентской стороне, так и на серверной. Т.е. если на клиентской стороне написать "Привет", нажать Enter
// то сообщение должно передаться на сервер и там отпечататься в консоли. Если сделать то же самое на серверной
// стороне, сообщение соответственно передается клиенту и печатается у него в консоли. Есть одна особенность,
// которую нужно учитывать: клиент или сервер может написать несколько сообщений подряд, такую ситуацию необходимо
// корректно обработать
//    Разобраться с кодом с занятия, он является фундаментом проекта-чата
//    ВАЖНО! Сервер общается только с одним клиентом, т.е. не нужно запускать цикл, который будет ожидать
//    второго/третьего/n-го клиентов

    static ServerSocket server;
    static Socket socket;
    static final int PORT = 8189;
    
    public static void main(String[] args) {

        Thread tread1 = new Thread(() ->{
            Scanner consoleScanner = new Scanner(System.in);
            try {
                PrintWriter out = new PrintWriter(socket.getOutputStream(),true);

                while (true){
                    String str = consoleScanner.nextLine();

                    if(str.equals("/end")){
                        break;
                    }

                    out.println("SERVER: " + str);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        try {
            server = new ServerSocket(PORT);
            System.out.println("Server started");
            socket = server.accept();
            System.out.println("Client connected");

            Scanner scanner = new Scanner(socket.getInputStream());

            tread1.start();

            while(true){
                String str = scanner.nextLine();

                if(str.equals("/end")){
                    System.out.println("Client disconnected");
                    break;
                }

                System.out.println("Client: " + str);
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
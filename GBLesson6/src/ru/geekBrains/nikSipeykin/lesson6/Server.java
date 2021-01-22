package ru.geekBrains.nikSipeykin.lesson6;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
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
    static final int PORT = 8189;
    
    public static void main(String[] args) {
        Socket socket = null;
        Scanner consoleScanner = new Scanner(System.in);

        try (ServerSocket server = new ServerSocket(PORT)){
            System.out.println("Server started");
            socket = server.accept();
            System.out.println("Client connected" + socket.getRemoteSocketAddress());
            DataInputStream inputStream = new DataInputStream(socket.getInputStream());
            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());

            //stream to read
            Thread thread1 = new Thread(() ->{

                try {
                    while (true){
                        outputStream.writeUTF(consoleScanner.nextLine());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            thread1.setDaemon(true);
            thread1.start();

            while(true){
                String str = inputStream.readUTF();
                if (str.equals("/close")){
                    System.out.println("Client disconnected");
                    outputStream.writeUTF("/close");
                    break;
                }else {
                    System.out.println("Client: " + str);
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try {
                socket.close();
            } catch (IOException | NullPointerException e) {
                e.printStackTrace();
            }
        }
    }
}
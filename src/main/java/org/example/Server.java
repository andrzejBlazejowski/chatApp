package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable {

    @Override
    public void run() {
        try {
            ServerSocket socket = new ServerSocket(8989);
            Socket client = socket.accept();
            ConnectionHandler handler = new ConnectionHandler(client);
            connections.add(handler);
        } catch (IOException e) {
            System.out.println("could not open socket on serwer side.");
        }
    }

    class ConnectionHandler implements Runnable{
        private Socket client;
        private BufferedReader in;
        private PrintWriter out;
        private String login;
        public ConnectionHandler(Socket client){
            this.client = client;
        }
        @Override
        public void run() {
            try{
                out = new PrintWriter(client.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                out.println("Please enter a nickname");
                nickname = in.readLine();
                System.out.println(login + "has joined chat");
            }catch (IOException e){
                System.out.println("error");
            }
        }

        public void sendMessage(String msg){
            out.println(msg);
        }
    }
}
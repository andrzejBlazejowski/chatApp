package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server implements Runnable {
    private ArrayList<ConnectionHandler> connections;
    private ServerSocket socket;
    private boolean done;
    public Server(){
        connections = new ArrayList<>();
        done = false;
    }

    @Override
    public void run() {
        try {
            while(!done){
                socket = new ServerSocket(8989);
                Socket client = socket.accept();
                ConnectionHandler handler = new ConnectionHandler(client);
                connections.add(handler);

            }
        } catch (IOException e) {
            System.out.println("could not open socket on serwer side.");
        }
    }

    public void broadcast(String msg){
        for (ConnectionHandler connection : connections){
            if( connection != null){
                connection.sendMessage(msg);
            }
        }
    }

    public void shoutdown(){
        done = true;
        if (!socket.isClosed()){
            try{
                socket.close();
            }catch (IOException e){
                System.out.println(e);
            }

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
                login = in.readLine();
                System.out.println(login + "has joined chat");
                broadcast(login + "has joined chat");
                String msg;
                while((msg = in.readLine()) != null){
                    if(msg.startsWith("/login ")){
                        String[] messageSplit = msg.split(" ", 2);
                        if (messageSplit.length == 2){
                            broadcast(login + " changed name to " + messageSplit[1]);
                            System.out.println(login + " changed name to " + messageSplit[1]);
                            login = messageSplit[1];
                            System.out.println("changed name success");
                        }else{
                            System.out.println("no login entered");
                        }
                        // TODO: handle login
                    }else if(msg.startsWith("/logout ")) {
                        // TODO: handle logout
                    }else{
                        broadcast(login +": "+ msg);
                    }
                }
            }catch (IOException e){
                System.out.println("error");
            }
        }

        public void sendMessage(String msg){
            out.println(msg);
        }
    }
}
package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ConnectionHandler implements Runnable{
    private Socket client;
    private BufferedReader in;
    private PrintWriter out;
    private String login;
    private Server server;
    public ConnectionHandler(Socket client, Server server){
        this.client = client;
        this.server = server;
    }
    @Override
    public void run() {
        try{
            out = new PrintWriter(client.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            out.println("Please enter a login name");
            login = in.readLine();
            System.out.println(login + "has joined chat");
            server.broadcast(login + "has joined chat");
            String msg;
            while((msg = in.readLine()) != null){
                if(msg.startsWith("/login ")){
                    String[] messageSplit = msg.split(" ", 2);
                    if (messageSplit.length == 2){
                        server.broadcast(login + " changed name to " + messageSplit[1]);
                        System.out.println(login + " changed name to " + messageSplit[1]);
                        login = messageSplit[1];
                        System.out.println("changed name success");
                    }else{
                        System.out.println("no login entered");
                    }
                }else if(msg.startsWith("/logout ")) {
                    server.broadcast(login +" has left the chat ");
                    shoutdown();
                }else if(msg.startsWith("/register ")) {
                    server.broadcast(login +" has left the chat ");
                    shoutdown();
                }else{
                    server.broadcast(login +": "+ msg);
                }
            }
        }catch (IOException e){
            System.out.println("error");
            shoutdown();
        }
    }

    public void shoutdown(){
        try{
            in.close();
            out.close();
            if (!client.isClosed()) {
                client.close();
            }
        }catch(IOException e){
            System.out.println("error while closing");
        }
    }
    public void sendMessage(String msg){
        out.println(msg);
    }
}

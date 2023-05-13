package server;

import config.ServerConfig;
import server.Models.User;

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
    private User user;
    private Server server;
    public ConnectionHandler(Socket client, Server server){
        this.client = client;
        this.server = server;
        System.out.println("new handler");
    }
    @Override
    public void run() {
        try{
            System.out.println("run method executed ");
            out = new PrintWriter(client.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            out.println("Please login");
            login = in.readLine();
            System.out.println(login + "has joined chat");
            server.broadcast(login + "has joined chat");
            String msg;
            while((msg = in.readLine()) != null){

                System.out.println("new message");
                if(msg.startsWith(ServerConfig.LoginAction) || msg.startsWith(ServerConfig.RegisterAction)){
                    String[] messageSplit = msg.split(" ", 2);
                    if (msg.startsWith(ServerConfig.LoginAction)){
                        handleLogin(messageSplit[1]);
                    } else if (msg.startsWith(ServerConfig.RegisterAction)) {
                        handleRegister(messageSplit[1]);
                    }
                }else if(msg.startsWith(ServerConfig.LogoutAction)) {
                    handleLogout();
                }else{
                    if (user != null) {
                        server.broadcast(user.getLogin() + ServerConfig.Separator + msg);
                    }
                    else {
                        server.broadcast("unsucesfull atempt to send a message - please login ");
                    }
                }
            }
        }catch (IOException e){
            System.out.println("error");
            shoutdown();
        }
    }

    private void handleLogin( String message){
        String[] arr = message.split(ServerConfig.Separator, 2);
        if (arr.length == 2){
            user = new User(arr);
            int loginResponse = server.loginUser(user);
            if(loginResponse == 200){
                server.broadcast("logged in succesfully "+ user.getLogin());
            }else if(loginResponse == 403){
                server.broadcast("passed in wrong login or password "+ user.getLogin());
            } else if (loginResponse == 404) {
                server.broadcast("login not foundin database, please register or enter existing user "+ user.getLogin());
            }
        }else{
            System.out.println("no login entered or password");
        }
    }

    private void handleRegister(String msg){
        String[] arr = msg.split(ServerConfig.Separator, 2);
        if (arr.length == 2) {
            user = new User(arr);
            server.registerUser(user);
            server.broadcast("new user was registered, welcome "+ user.getLogin());
        }
    }
    private void handleLogout(){
        server.broadcast(login +" has left the chat ");
        user = null;
        shoutdown();
    }
    private void handleNewMessage(){

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

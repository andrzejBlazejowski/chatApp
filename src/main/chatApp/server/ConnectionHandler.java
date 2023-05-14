package server;

import config.ServerConfig;
import server.Models.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ConnectionHandler implements Runnable{
    public Socket getClient() {
        return client;
    }

    public void setClient(Socket client) {
        this.client = client;
    }

    public BufferedReader getIn() {
        return in;
    }

    public void setIn(BufferedReader in) {
        this.in = in;
    }

    public PrintWriter getOut() {
        return out;
    }

    public void setOut(PrintWriter out) {
        this.out = out;
    }
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
    }
    private Socket client;
    private BufferedReader in;
    private PrintWriter out;
    private User user;
    private Server server;
    public ConnectionHandler(Socket client, Server server){
        setClient(client);
        setServer(server);
    }
    @Override
    public void run() {
        try{
            setOut(new PrintWriter(getClient().getOutputStream(), true));
            setIn(new BufferedReader(new InputStreamReader(getClient().getInputStream())));
            String msg;
            while((msg = getIn().readLine()) != null){

                System.out.println("new message");
                if(msg.startsWith(ServerConfig.LoginAction) || msg.startsWith(ServerConfig.RegisterAction) || msg.startsWith(ServerConfig.ChangeLoginAction) ){
                    String[] messageSplit = msg.split(" ", 2);
                    if (msg.startsWith(ServerConfig.LoginAction)){
                        handleLogin(messageSplit[1]);
                    } else if (msg.startsWith(ServerConfig.RegisterAction)) {
                        handleRegister(messageSplit[1]);
                    }else if(msg.startsWith(ServerConfig.ChangeLoginAction)) {
                        handleLoginChange(messageSplit[1]);
                    }
                }else if(msg.startsWith(ServerConfig.LogoutAction)) {
                    handleLogout();
                }else{
                    if (getUser() != null) {
                        getServer().broadcast(getUser().getLogin() + ServerConfig.Separator + msg);
                    }
                    else {
                        getServer().broadcast("unsucesfull atempt to send a message - please login ");
                    }
                }
            }
        }catch (IOException e){
            System.out.println("error");
            shoutdown();
        }
    }

    private void handleLoginChange(String msg) {
        String[] arr = msg.split(ServerConfig.Separator, 2);
        if (arr.length == 2){
            if (arr[1].equals(getUser().getPassword())){
                getUser().setLogin(arr[0]);
            }
        }
    }

    private void handleLogin( String message){
        String[] arr = message.split(ServerConfig.Separator, 2);
        if (arr.length == 2){
            setUser(new User(arr));
            int loginResponse = getServer().loginUser(getUser());
            if(loginResponse == 200){
                getServer().broadcast("logged in succesfully "+ getUser().getLogin());
            }else if(loginResponse == 403){
                getServer().broadcast("passed in wrong login or password "+ getUser().getLogin());
                setUser(null);
            } else if (loginResponse == 404) {
                getServer().broadcast("login not found in database, please register or enter existing user "+ getUser().getLogin());
                setUser(null);
            }
        }else{
            System.out.println("no login entered or password");
            setUser(null);
        }
    }

    private void handleRegister(String msg){
        String[] arr = msg.split(ServerConfig.Separator, 2);
        if (arr.length == 2) {
            setUser(new User(arr));
            getServer().registerUser(getUser());
            getServer().broadcast("new user was registered, welcome "+ getUser().getLogin());
        }
    }
    private void handleLogout(){
        getServer().broadcast(getUser().getLogin() +" has left the chat ");
        setUser(null);
        shoutdown();
    }
    private void handleNewMessage(){

    }

    public void shoutdown(){
        try{
            getIn().close();
            getOut().close();
            if (!getClient().isClosed()) {
                getClient().close();
            }
        }catch(IOException e){
            System.out.println("error while closing");
        }
    }
    public void sendMessage(String msg){
        out.println(msg);
    }
}

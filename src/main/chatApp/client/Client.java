package client;

import client.Listeners.LoginActionListener;
import client.Listeners.LoginChangeActionListener;
import client.Listeners.RegisterActionListener;
import client.Listeners.SendMessageActionListener;
import client.Models.Message;
import client.Views.ChatView;
import config.ServerConfig;
import server.Models.User;

import java.io.*;
import java.net.Socket;

public class Client implements Runnable, LoginActionListener, LoginChangeActionListener, RegisterActionListener, SendMessageActionListener {
    private Socket client;
    private BufferedReader in;
    private PrintWriter out;
    private boolean done;
    private User user;
    public Client(){

        setDone(false);
        setUser(new User("", ""));
    }
    InputHandler inputHandler;

    @Override
    public void run() {
        try{
            ChatView window = new ChatView();
            window.setLoginActionListener(this);
            window.setLoginChangeActionListener(this);
            window.setRegisterActionListener(this);
            window.setSendMessageActionListener(this);
            window.setVisible(true);

            Socket client = new Socket(ServerConfig.HostAddress, ServerConfig.Port);
            out = new PrintWriter(client.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));

            inputHandler = new InputHandler(this);
            Thread t = new Thread(inputHandler);
            t.start();
            String inMsg;
            while ((inMsg = in.readLine()) != null){
                Message msg = Message.getMessageFromString(inMsg);
                if (!msg.getAuthor().isEmpty() && msg.getAuthor().equals(getUser().getLogin())){
                    msg.setAlignLeft(false);
                }
                window.setNewMessage(msg);
            }
        }catch (IOException e){
            shutdown();
        }
    }


    public void shutdown(){
        done = true;
        try{
            in.close();
            out.close();
            if (client.isClosed())
                client.close();
        }catch (IOException e){
            System.out.println("unable to close client");
        }
    }

    @Override
    public void onLoginActionListener(User user) {
        inputHandler.login(user.getLogin(), user.getPassword());
    }

    @Override
    public void onLoginChangeActionListener(User user) {
        inputHandler.changeLogin(user.getLogin(), user.getPassword());
    }

    @Override
    public void onRegisterActionListener(User user) {
        inputHandler.register(user.getLogin(), user.getPassword());
    }

    @Override
    public void onSendMessageActionListener(String msg) {
        inputHandler.sendMsg(msg);
    }
    public static void main (String[] args) {
        Client client = new Client();
        client.run();
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public PrintWriter getOut() {
        return out;
    }

    public void setOut(PrintWriter out) {
        this.out = out;
    }
    public BufferedReader getIn() {
        return in;
    }

    public void setIn(BufferedReader in) {
        this.in = in;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

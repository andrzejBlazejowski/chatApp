package client;

import config.ServerConfig;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class InputHandler implements Runnable{
    Client client;
    InputHandler(Client client){
        this.client = client;
    }
    @Override
    public void run() {
        try{
            BufferedReader inReader = new BufferedReader(new InputStreamReader(System.in));
            while(!client.isDone()){
                String msg = inReader.readLine();
                if (msg.equals(ServerConfig.LogoutAction)){
                    inReader.close();
                    client.shutdown();
                }else {
                    client.getOut().println(msg);
                }
            }
        } catch (IOException e){
            client.shutdown();
        }
    }

    public void register(String login, String password){
        client.getOut().println(ServerConfig.RegisterAction + login + ServerConfig.Separator + password);
    }

    public void login(String login, String password){
        client.getOut().println(ServerConfig.LoginAction + login + ServerConfig.Separator + password);
    }

    public void changeLogin(String login, String password){
        client.getOut().println(ServerConfig.LoginAction + login + ServerConfig.Separator + password);
    }

    public void sendMsg(String msg){
        client.getOut().println(msg);
    }
}

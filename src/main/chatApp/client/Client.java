package client;

import config.ServerConfig;
import server.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client implements Runnable {
    private Socket client;
    private BufferedReader in;
    private PrintWriter out;
    private boolean done;
    public Client(){
        done = false;
    }
    @Override
    public void run() {
        try{
            Socket client = new Socket(ServerConfig.HostAddress, ServerConfig.Port);
            out = new PrintWriter(client.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));

            InputHandler inputHandler = new InputHandler();
            Thread t = new Thread(inputHandler);
            t.start();
            String inMsg;
            while ((inMsg = in.readLine()) != null){
                System.out.println(inMsg);
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

    class InputHandler implements Runnable{

        @Override
        public void run() {
            try{
                BufferedReader inReader = new BufferedReader(new InputStreamReader(System.in));
                while(!done){
                    String msg = inReader.readLine();
                    if (msg.equals(ServerConfig.LogoutAction)){
                        inReader.close();
                        shutdown();
                    }else {
                        out.println(msg);
                    }
                }
            } catch (IOException e){
                shutdown();
            }
        }

        private void register(String login, String password){
            out.println(ServerConfig.RegisterAction + login + ServerConfig.Separator + password);
        }

        private void login(String login, String password){
            out.println(ServerConfig.LoginAction + login + ServerConfig.Separator + password);
        }

        private void sendMsg(String msg){
            out.println(msg);
        }
    }
    public static void main (String[] args) {
        Client client = new Client();
        client.run();
    }
}

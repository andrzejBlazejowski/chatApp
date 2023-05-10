package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server implements Runnable {
    private ArrayList<ConnectionHandler> connections;
    private ServerSocket socket;
    private boolean done;
    private ExecutorService pool;

    public Server(){
        connections = new ArrayList<>();
        done = false;
    }

    @Override
    public void run() {
        try {
            while(!done){
                socket = new ServerSocket(8989);
                pool = Executors.newCachedThreadPool();
                Socket client = socket.accept();
                ConnectionHandler handler = new ConnectionHandler(client, this);
                connections.add(handler);
                pool.execute(handler);
            }
        } catch (IOException e) {
            System.out.println("could not open socket on serwer side.");
            shoutdown();
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
            for(ConnectionHandler connection : connections){
                connection.shoutdown();
            }
        }
    }
    public static void main (String[] args) {
        Server srv = new Server();
        srv.run();
    }

}
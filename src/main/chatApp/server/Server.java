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
            socket = new ServerSocket(6633);
            pool = Executors.newCachedThreadPool();
            while(!done){
                Socket client = socket.accept();
                ConnectionHandler handler = new ConnectionHandler(client, this);
                connections.add(handler);
                pool.execute(handler);
            }
        } catch (Exception e) {
            System.out.println("could not open socket on serwer side.");
            System.out.println(e);

            throw new RuntimeException(e);

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
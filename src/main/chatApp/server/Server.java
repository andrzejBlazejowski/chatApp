package server;

import config.ServerConfig;
import server.Models.User;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server implements Runnable {


    private ArrayList<ConnectionHandler> connections;
    private ArrayList<User> users;
    private ServerSocket socket;
    private boolean done;
    private ExecutorService pool;
    public ExecutorService getPool() {
        return pool;
    }

    public void setPool(ExecutorService pool) {
        this.pool = pool;
    }
    public ArrayList<ConnectionHandler> getConnections() {
        return connections;
    }

    public void setConnections(ArrayList<ConnectionHandler> connections) {
        this.connections = connections;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public ServerSocket getSocket() {
        return socket;
    }

    public void setSocket(ServerSocket socket) {
        this.socket = socket;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public Server() {
        setConnections(new ArrayList<>());
        setUsers(new ArrayList<>());
        setDone(false);
    }

    @Override
    public void run() {
        try {
            setSocket(new ServerSocket(ServerConfig.Port));
            setPool(Executors.newCachedThreadPool());
            while(!isDone()){
                Socket client = getSocket().accept();
                ConnectionHandler handler = new ConnectionHandler(client, this);
                getConnections().add(handler);
                getPool().execute(handler);
            }
        } catch (Exception e) {
            System.out.println("could not open socket on serwer side.");
            System.out.println(e);

            throw new RuntimeException(e);

        }
    }

    public void broadcast(String msg){
        for (ConnectionHandler connection : getConnections()){
            if( connection != null){
                connection.sendMessage(msg);
            }
        }
    }

    public void shoutdown(){
        setDone(true);
        if (!getSocket().isClosed()){
            try{
                getSocket().close();
            }catch (IOException e){
                System.out.println(e);
            }
            for(ConnectionHandler connection : getConnections()){
                connection.shoutdown();
            }
        }
    }
    public static void main (String[] args) {
        Server srv = new Server();
        srv.run();
    }

    public void registerUser(User user) {
        getUsers().add(user);
    }

    public int loginUser(User user) {
        for (User usr:getUsers()) {
            if (usr.getLogin().equals(user.getLogin())){
                if (usr.getPassword().equals(user.getPassword())){
                    return 200;
                }else{
                    return 403;
                }
            }
        }
        return 404;
    }

}
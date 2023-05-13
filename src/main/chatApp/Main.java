import client.Client;
import server.Server;

import java.util.ArrayList;


public class Main {
    public static void main(String[] args) throws InterruptedException {
        Thread serverThread = new Thread(() -> {
            Server.main(new String[0]);
        });
        serverThread.start();

        Thread.sleep(1000);
        Thread clientThread = new Thread(() -> {
            Client.main(new String[0]);
        });
        clientThread.start();
        Client.main(new String[0]);
    }
}

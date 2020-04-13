package server;

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.util.LinkedHashSet;

public class Server {

    private static LinkedHashSet<String> indexes = new LinkedHashSet<String>();
    private static int port = 50000;

    public static void main(String[] args) throws IOException, Exception {
        for (String str : args) {
            indexes.add(str);
        }

        try (ServerSocket ss = new ServerSocket(port);) {
            while (true) {
                System.out.println("Waiting for client to connect");
                Thread t = new Thread(new ClientHandler(ss, indexes));
                t.start();
            }
        } catch (BindException exception) {
            System.out.println("Cannot start server, because the following port is already in use: " + port);
        }
    }
}

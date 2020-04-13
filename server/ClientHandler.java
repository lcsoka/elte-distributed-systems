package server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedHashSet;

public class ClientHandler implements AutoCloseable, IClientHandler, Runnable {

    private LinkedHashSet<String> files;
    private final BufferedReader from;
    private final PrintWriter to;
    private final Socket socket;

    ClientHandler(ServerSocket ss, LinkedHashSet<String> contents) throws IOException {
        this.socket = ss.accept();
        InputStream is = socket.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        this.from = new BufferedReader(isr);
        this.to = new PrintWriter(socket.getOutputStream(), true);
        this.files = contents;
        System.out.println("Client handler created");
    }

    @Override
    public void run() {
        System.out.println("Client Handler run");
        try {
            for (String line = this.from.readLine(); line != null; line = this.from.readLine()) {
                System.out.println(line);
            }
        } catch (IOException exception) {
            System.out.println("Client Handler error while running: " + exception.toString());
        }
        System.out.println("End of run");
        try {
            this.close();
        } catch (Exception e) {

        }
    }

    @Override
    public void close() throws Exception {
        System.out.println("Trying to close client handler");
        this.from.close();
        this.to.close();
    }

    @Override
    public void handleDownloadDocument(BufferedReader fromClient, PrintWriter toClient) {
        // TODO Auto-generated method stub

    }

    @Override
    public void handleListDocuments(PrintWriter toClient) {
        // TODO Auto-generated method stub

    }

    @Override
    public void handleUnknownRequest(PrintWriter toClient) {
        // TODO Auto-generated method stub

    }

    @Override
    public void handleUploadDocument(BufferedReader fromClient, PrintWriter toClient) {
        // TODO Auto-generated method stub

    }
}

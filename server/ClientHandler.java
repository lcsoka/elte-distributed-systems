package server;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.util.LinkedHashSet;

public class ClientHandler implements AutoCloseable, IClientHandler, Runnable {

    private ServerSocket serverSocket;
    private LinkedHashSet<String> files;

    ClientHandler(ServerSocket ss, LinkedHashSet<String> contents) {
        this.serverSocket = ss;
        this.files = contents;
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
    }

    @Override
    public void close() throws Exception {
        this.serverSocket.close();
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
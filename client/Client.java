package client;

import java.io.*;
import java.net.Socket;

public class Client implements AutoCloseable, IClient, Runnable {

    private static int port = 50000;

    private final Socket socket;
    private final BufferedReader from;
    private final PrintWriter to;
    private final BufferedReader fromUser;
    private final PrintWriter toUser;

    public static void main(String[] args) {

        try {
            Client client = new Client(System.in, System.out);
            client.run();
        } catch (IOException exception) {
            System.out.println("There was an error initializing the client.");
            exception.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            for (String input = fromUser.readLine(); input != null; input = fromUser.readLine()) {
                // Send command to server
                // TODO: With interface functions
                this.to.println(input);
            }
        } catch (IOException exception) {
            System.out.println("Error while running");
        }
    }

    Client(InputStream userInput, OutputStream userOutput) throws IOException {
        // Create socket
        this.socket = new Socket("localhost", port);

        // Create buffered reader
        InputStream is = this.socket.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        this.from = new BufferedReader(isr);

        // Create print writer
        this.to = new PrintWriter(this.socket.getOutputStream(), true);

        // Get user input reader
        InputStreamReader isr2 = new InputStreamReader(userInput);
        this.fromUser = new BufferedReader(isr2);

        // Create user print writer
        this.toUser = new PrintWriter(userOutput, true);
    }

    @Override
    public void handleDownloadDocument() throws IOException {
        // TODO Auto-generated method stub

    }

    @Override
    public void handleListDocuments() throws IOException {
        // TODO Auto-generated method stub

    }

    @Override
    public void handleUploadDocument() throws IOException {
        // TODO Auto-generated method stub

    }

    @Override
    public void close() throws Exception {
        System.out.println("Trying to close resources");
        this.from.close();
        this.to.close();
    }
}

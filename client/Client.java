package client;

import java.io.*;
import java.net.Socket;
import shared.*;

public class Client implements AutoCloseable, IClient, Runnable {

    private static int port = 50000;

    private final Socket socket;
    private final BufferedReader fromServer;
    private final PrintWriter toServer;
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
            this.printMenu();
            for (String input = this.fromUser.readLine(); input != null; input = this.fromUser.readLine()) {
                // Handle user commands
                switch (input) {
                    case "0":
                        this.handleDownloadDocument();
                        break;
                    case "1":
                        this.handleListDocuments();
                        break;
                    case "2":
                        this.handleUploadDocument();
                        break;
                    default:
                        this.toUser.println("Invalid command, please enter a new one!");
                        break;
                }
                this.printMenu();
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
        this.fromServer = new BufferedReader(isr);

        // Create print writer
        this.toServer = new PrintWriter(this.socket.getOutputStream(), true);

        // Get user input reader
        InputStreamReader isr2 = new InputStreamReader(userInput);
        this.fromUser = new BufferedReader(isr2);

        // Create user print writer
        this.toUser = new PrintWriter(userOutput, true);
    }

    @Override
    public void handleDownloadDocument() throws IOException {
        // Send DOWNLOAD_DOCUMENT command to the server
        this.toServer.println(Command.DOWNLOAD_DOCUMENT);
        // Get the document name to download
        this.toUser.println("|  Enter document name:");
        String fileName = this.fromUser.readLine();
        // Send the document name to the server
        this.toServer.println(fileName);

        // Wait for response from server
        for (String line = this.fromServer.readLine(); line != null; line = this.fromServer.readLine()) {
            if (line.equals(Command.NOT_FOUND.toString())) {
                // File not found
                this.toUser.println("File not found.");
                break;
            } else if (line.equals(Command.END_OF_DOCUMENT.toString())) {
                // End of document
                break;
            } else {
                // Print message
                this.toUser.println("| " + line);
            }
        }
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
        this.fromServer.close();
        this.toServer.close();
    }

    private void printMenu() {
        this.toUser.println("| 0 - download document\n| 1 - list documents\n| 2 - upload content");
    }
}

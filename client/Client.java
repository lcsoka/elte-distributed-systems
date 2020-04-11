package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class Client implements AutoCloseable, IClient, Runnable {

    private static int port = 50000;

    public static void main(String[] args) {

        try {
            Client client = new Client(System.in, System.out);
        } catch (IOException exception) {
            System.out.println("There was an error initializing the client.");
        }
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub

    }

    Client(InputStream userInput, OutputStream userOutput) throws IOException {
        System.out.println("Client initialized");
        try (Socket s = new Socket("localhost", port);
                InputStream is = s.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);
                PrintWriter pw = new PrintWriter(s.getOutputStream(), true);) {
            System.out.println("Connected to server");

            InputStream is2 = System.in;
            InputStreamReader isr2 = new InputStreamReader(is2);
            BufferedReader fromUser = new BufferedReader(isr2);

            for (String input = fromUser.readLine(); input != null; input = fromUser.readLine()) {
                pw.println(input);
                // pw.flush();

                String line = br.readLine();
                System.out.println(line);
            }
        }

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
        // TODO Auto-generated method stub

    }
}
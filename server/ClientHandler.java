package server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedHashSet;
import shared.Command;

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
        // System.out.println("Client handler created");
    }

    @Override
    public void run() {
        // System.out.println("Client Handler run");
        try {
            for (String line = this.from.readLine(); line != null; line = this.from.readLine()) {
                try {
                    Command cmd = Command.valueOf(line);
                    switch (cmd) {
                        case DOWNLOAD_DOCUMENT:
                            this.handleDownloadDocument(this.from, this.to);
                            break;
                        case LIST_DOCUMENT:
                            this.handleListDocuments(this.to);
                            break;
                        case UPLOAD_DOCUMENT:
                            this.handleUploadDocument(this.from, this.to);
                            break;
                        default:
                            System.out.println("Invalid command: " + line);
                            this.handleUnknownRequest(this.to);
                    }

                } catch (IllegalArgumentException exception) {
                    System.out.println("Invalid command: " + line);
                    this.handleUnknownRequest(this.to);
                }
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
        this.from.close();
        this.to.close();
    }

    @Override
    public void handleDownloadDocument(BufferedReader fromClient, PrintWriter toClient) throws IOException {
        String fileName = fromClient.readLine();
        // System.out.println("Got file name: " + fileName);
        synchronized (this.files) {
            if (this.files.contains(fileName)) {
                // System.out.println("File exists");
                synchronized (fileName.intern()) {
                    File input = new File(fileName);
                    try (FileInputStream fi = new FileInputStream(input);
                            InputStreamReader isr = new InputStreamReader(fi);
                            BufferedReader br = new BufferedReader(isr);) {
                        for (String line = br.readLine(); line != null; line = br.readLine()) {
                            toClient.println(line);
                        }
                        toClient.println(Command.END_OF_DOCUMENT.toString());
                    }
                }
            } else {
                System.out.println("File does not exist!");
                toClient.println(Command.NOT_FOUND.toString());
            }
        }
    }

    @Override
    public void handleUploadDocument(BufferedReader fromClient, PrintWriter toClient) throws IOException {
        String fileName = fromClient.readLine();

        // We should lock this block in case some other thread want's to write this file
        // too
        synchronized (fileName.intern()) {
            File output = new File(fileName);
            try (FileOutputStream fo = new FileOutputStream(output); PrintWriter pw = new PrintWriter(fo, true);) {
                // Read lines from client
                for (String line = fromClient.readLine(); line != null; line = fromClient.readLine()) {
                    if (line.equals(Command.END_OF_DOCUMENT.toString())) {
                        // It will close the file (i hope)
                        break;
                    } else {
                        // Write content to file
                        pw.println(line);
                    }
                }
            }

            synchronized (this.files) {
                if (this.files.contains(fileName)) {
                    System.out.println("File overwritten.");
                } else {
                    this.files.add(fileName);
                }
            }
        }

    }

    @Override
    public void handleListDocuments(PrintWriter toClient) throws IOException {
        synchronized (this.files) {
            this.files.forEach(file -> {
                toClient.println(file);
            });
            toClient.println(Command.END_OF_LIST.toString());
        }
    }

    @Override
    public void handleUnknownRequest(PrintWriter toClient) throws IOException {
        this.socket.close();
    }
}

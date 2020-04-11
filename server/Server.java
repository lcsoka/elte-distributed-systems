package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedHashSet;

public class Server {

    private static LinkedHashSet<String> indexes = new LinkedHashSet<String>();
    private static int port = 50000;

    public static void main(String[] args) throws IOException {
        for (String str : args) {
            indexes.add(str);
        }

        try (ServerSocket ss = new ServerSocket(port);) {
            while (true) {
                try (Socket s = ss.accept();
                        InputStream is = s.getInputStream();
                        InputStreamReader isr = new InputStreamReader(is);
                        BufferedReader br = new BufferedReader(isr);
                        PrintWriter pw = new PrintWriter(s.getOutputStream(), true);) {

                    System.out.println("client connected");
                    for (String line = br.readLine(); line != null; line = br.readLine()) {

                        // try {
                        // int i = Integer.parseInt(line);

                        // int counter = readCounter(f);
                        // counter += i;
                        // writeCounter(f,counter);

                        // PrintStream ps = System.out;
                        // ps.println("sent: " + counter);

                        // pw.println( counter );
                        // // pw.flush();

                        // } catch(NumberFormatException e){
                        // System.err.println("warning: not a number");
                        // }
                    }
                    System.out.println("client disconnected");
                }
            }
        }
    }
}
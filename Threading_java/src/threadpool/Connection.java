package threadpool;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * This class reads a line of text from the client and then writes the same line
 * back to the client.
 *
 * @author Bre Burd
 */
public class Connection implements Runnable {

    public Socket socket;
    public Scanner inputClient;
    public PrintWriter outputClient;

    /**
     * Initializes the client socket
     */
    public Connection(Socket socket) {
        this.socket = socket;
        try {
            inputClient = new Scanner(socket.getInputStream());
            outputClient = new PrintWriter(socket.getOutputStream(), true);
        }catch (IOException e) {
            // not handled
        }
    }

    @Override
    public void run() {
        String line;
        while(inputClient.hasNextLine()) {
            line = inputClient.nextLine();
            outputClient.println("Server: " + line);
        }
    }
}

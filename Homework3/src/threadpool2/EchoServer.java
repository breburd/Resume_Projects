package threadpool2;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * This class opens a server socket and listens for connections. When is
 * accepts a new connection, the server creates a Connection object and adds
 * it to the thread pool.
 *
 * @author Bre Burd
 */
public class EchoServer {
    public static final int PORT = 1234;

    public static void main(String[] args){
        try {
            ServerSocket socket = new ServerSocket(PORT);
            System.out.println("Server started.");
            ThreadPool pool = new ThreadPool();
            System.out.println("Thread pool created.");
            System.out.println("Awaiting clients.");

            while(true){
                Socket client = socket.accept();
                Connection connection = new Connection(client);
                pool.add(connection);
            }
        } catch (IOException e) {
            // not handled
        }
    }
}

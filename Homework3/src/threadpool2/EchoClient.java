package threadpool2;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * Client tries to open a connection on the local host. It reads a line of text
 * from the keyboard, writes it to the server and then outputs the server's
 * response to the terminal window. This continues until the user enters a
 * single period, which terminates the connection.
 *
 * @author Bre Burd
 */

public class EchoClient {
    public static void main(String[] args){
        final String host = "Localhost";

        System.out.println("Echo client starting.");
        try(Socket server = new Socket(host, EchoServer.PORT)) {
            System.out.println("Echo client connected");
            System.out.print("Client: ");
            Scanner in = new Scanner(System.in);
            String inputLine = in.nextLine();

            PrintWriter toServer = new PrintWriter(server.getOutputStream(), true);
            Scanner fromServer = new Scanner(server.getInputStream());

            while (!inputLine.equals(".")) {
                toServer.println(inputLine);
                if(fromServer.hasNextLine())
                    System.out.println(fromServer.nextLine());
                System.out.print("Client: ");
                inputLine = in.nextLine();
            }
            System.out.println("Echo client terminating.");
        } catch(IOException e){
            // not handled
        }
    }
}

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

/**
 * The client implementation for Assignment 2
 */
public class myClient {

    public static void main(String... args) {
        // Read from system input
        Scanner sc = new Scanner(System.in);
        try {

            //Some stuff around the sockets to listen for input and send output
            Socket socket = new Socket(args[0], Integer.parseInt(args[1]));
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
            PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true);

            //Read the user's first command
            out.println(sc.nextLine());

            String message;
            inputStream:
                //listen from messages from the server
                while ((message = in.readLine()) != null) {
                    switch (message) {
                        case "bye":
                            break inputStream;
                        case "invalid":
                            break;
                        default:
                            System.out.println(message);
                    }
                    //read the next command to send to the server
                    out.println(sc.nextLine());
                }

            //close the connection
            socket.close();
        } catch (IOException e) {
            System.out.println("[Server] An unexpected error has occurred");
        }
    }
}

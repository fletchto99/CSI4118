import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class myServer implements Runnable{

    private Socket client;

    private myServer(Socket client) {
        this.client = client;
    }

    public static void main(String... args) {
        ServerSocket server = null;
        try {
             server = new ServerSocket(Integer.parseInt(args[1]));
        } catch(Exception e) {

        }
        while (true) {
            try {
                assert server != null;
                Socket client = server.accept();
                new Thread(new myServer(client)).start();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("[Server] An unexpected error has occurred");
            }
        }
    }

    public void run() {
        try {
            boolean echo = false;
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream(), "UTF-8"));
            PrintWriter out = new PrintWriter(new OutputStreamWriter(client.getOutputStream(), "UTF-8"), true);

            String message;

            inputStream:
            while ((message = in.readLine()) != null) {

                if (echo && !message.equals("stopEcho")) {
                    out.println(message);
                    continue;
                }

                switch (message) {
                    case "whoAMi":
                        out.println(client.getRemoteSocketAddress().toString());
                        break;
                    case "startEcho":
                        out.println("listening");
                        echo = true;
                        break;
                    case "stopEcho":
                        if (echo) {
                            echo = false;
                            out.println("stopped");
                        }
                        break;
                    case "bye":
                        out.println("bye");
                        break inputStream;
                    default:
                        out.println("invalid");
                }
            }
            in.close();
            out.close();
            client.close();
        } catch(Exception e) {
            //ignored
        }

    }
}

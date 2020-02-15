
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class TCPEchoClient {
    public static void main(String[] args) {
        Socket socket = null;
        BufferedReader socketReader = null;
        BufferedWriter socketWriter = null;

        try {
            /* Create a socket that will connect to localhost
             * at port 12900. Note that server must also be
             * running at localhost and 12900. */
            socket = new Socket("localhost", 12900);
            System.out.println("Started client socket at " +
                socket.getLocalSocketAddress());

            // Create buffered reader/writer using IO streams
            socketReader = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
            socketWriter = new BufferedWriter(
                new OutputStreamWriter(socket.getOutputStream()));
            
            // Create buffered reader for user's input
            BufferedReader consoleReader = 
                new BufferedReader(new InputStreamReader(System.in));
            
            String promptMsg = "Please enter a message (Bye to quit):";
            String outMsg = null;

            System.out.print(promptMsg);
            while ((outMsg = consoleReader.readLine()) != null) {
                if (outMsg.equalsIgnoreCase("bye")) {
                    break;
                }

                socketWriter.write(outMsg + "\n");
                socketWriter.flush();

                String inMsg = socketReader.readLine();
                System.out.println("Server: " + inMsg);

                System.out.println();
                System.out.print(promptMsg);
            }
        } catch (IOException ex) {ex.printStackTrace();}
        finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException ex) {ex.printStackTrace();}
            }
        }
    }
}
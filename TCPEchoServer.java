import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPEchoServer {
    public static void main(String[] args) {
        try {
            /* Create ServerSocket, assign it port 12900, give it a
             *queue of 100, and bind it to IP address of localhost. */
            ServerSocket serverSocket = new ServerSocket(12900, 100,
                InetAddress.getByName("localhost"));
            System.out.println("Server started at: " + serverSocket);

            // Keep accepting clients in infinite loop
            while (true) {
                System.out.println("Waiting for a connection...");

                // Accept a connection and assign it to normal Socket
                final Socket activeSocket = serverSocket.accept();

                System.out.println("Received a connection from " + 
                    activeSocket);
                
                // Create a new thread to handle the new connection
                Runnable runnable = 
                    () -> handleClientRequest(activeSocket);
                new Thread(runnable).start();
            }
        } catch (IOException ex) {ex.printStackTrace();}
    }

    public static void handleClientRequest(Socket socket) {
        BufferedReader socketReader = null;
        BufferedWriter socketWriter = null;

        try {
            // Create a buffered reader/writer for the socket
            socketReader = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
            socketWriter = new BufferedWriter(
                new OutputStreamWriter(socket.getOutputStream()));
            
            String inMsg = null;
            while ((inMsg = socketReader.readLine()) != null) {
                System.out.println("Received from client: " + inMsg);

                // Echo the received message to the client
                String outMsg = inMsg;
                socketWriter.write(outMsg + "\n");
                socketWriter.flush();
            }
        } catch (IOException ex) {ex.printStackTrace();}
        finally {
            try {
                socket.close();
            } catch (IOException ex) {ex.printStackTrace();}
        }
    }
}
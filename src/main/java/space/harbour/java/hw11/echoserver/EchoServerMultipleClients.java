package space.harbour.java.hw11.echoserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/*
This echo server allows many clients to connect
at the same time and get an echo of
whatever they say.
Next step would be to allow the clients to talk to each other
 */

public class EchoServerMultipleClients {
    public static void main(String[] args) throws IOException {

        //this socket is able to listen for incoming connection
        //we use the IP address of our current machine, so no need to specify IP
        ServerSocket serverSocket = new ServerSocket(8008);

        //infinite cycle: keep running while we are listening
        while (true) {
            Socket clientSocket = serverSocket.accept();
            ChatHandler clientThread = new ChatHandler(clientSocket);
            clientThread.start();
        }
    }
}

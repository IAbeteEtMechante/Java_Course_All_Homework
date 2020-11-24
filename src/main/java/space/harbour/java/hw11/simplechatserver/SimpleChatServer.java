package space.harbour.java.hw11.simplechatserver;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import space.harbour.java.hw11.simplechatserver.SimpleChatHandler;

/*
This server braodcasts any message from one client to all the clients

one can test it on your own machine using
$ telnet 127.0.0.1 8008

Or you can test it on your local network
just by using the local IP of the server
in that local network


 */

public class SimpleChatServer {
    Set<SimpleChatHandler> clients = ConcurrentHashMap.newKeySet();

    public SimpleChatServer(int port) throws IOException {

        //this socket is able to listen for incoming connection
        //we use the IP address of our current machine, so no need to specify IP
        ServerSocket serverSocket = new ServerSocket(port);
        //infinite cycle: keep running while we are listening
        while (true) {
            Socket clientSocket = serverSocket.accept();
            SimpleChatHandler clientThread = new SimpleChatHandler(this, clientSocket);
            clients.add(clientThread);
            clientThread.start();
        }
    }

    public void broadcast(String message) {
        clients.parallelStream().forEach(client -> client.sendMessage(message));
    }

    public static void main(String[] args) throws IOException {
        new SimpleChatServer(8008);



    }
}


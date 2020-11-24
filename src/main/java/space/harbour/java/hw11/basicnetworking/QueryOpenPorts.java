package space.harbour.java.hw11.basicnetworking;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/*
This class is trying to contact a specific port of the host
A client socket is just a pair (ip, port)
example use:
$ java space.harbour.java.hw11.utils.QueryOpenPorts harbour.space
A server is running on port: 80.
 */

public class QueryOpenPorts {
    public static void main(String[] args) {
        String hostname = args[0];
        try {

            InetAddress ip = InetAddress.getByName(hostname);

            int port = 80; // http port
            try (Socket clientSocket = new Socket(hostname, port)) {
                clientSocket.setSoTimeout(5000);
                System.out.println("A server is running on port: "
                        + port + ".");
            } catch (IOException e) {
                System.out.println("No server on port" + port + ".");
            }
        } catch (UnknownHostException e) {
            System.out.println("Could not find host: " + hostname);
        }
    }
}

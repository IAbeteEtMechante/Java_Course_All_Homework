package space.harbour.java.hw11.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/*
This class is an Echo Server
whatever you send to this server, it will send it back
can be used with telnet as a client, or another java program as a client

if use with telnet:
$ telnet 127.0.0.1 8008
Trying 127.0.0.1...
Connected to 127.0.0.1.
Escape character is '^]'.
Hello. This is Java EchoServer
Enter BYE to exit.

 */

public class EchoServer {
    public static void main(String[] args) throws IOException {

        //this socket is able to listen for incoming connection
        //we use the IP address of our current machine, so no need to specify IP
        ServerSocket serverSocket = new ServerSocket(8008);

        //infinite cycle: keep running while we are listening
        while (true) {
            Socket incoming = serverSocket.accept(); //need to throw IOException
            //get input stream from incoming client
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(incoming.getInputStream()));
            //prtinwriter to reply back to the client
            PrintWriter out = new PrintWriter(new OutputStreamWriter(
                    incoming.getOutputStream()));
            out.println("Hello. This is Java EchoServer");
            out.println("Enter BYE to exit.");
            out.flush();
            while (true) {
                String str = in.readLine();
                if (str == null) { //client closed connection
                    break; //break the cycle to get another client
                }
                out.println("Echo: " + str);
                out.flush();
                if (str.trim().equals("BYE")) {
                    break; //break the cycle to get another client
                }

            }
            in.close();
            out.close();
            incoming.close();
        }
    }
}

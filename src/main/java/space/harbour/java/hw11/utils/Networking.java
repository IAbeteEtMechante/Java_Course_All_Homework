package space.harbour.java.hw11.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;



public class Networking {
    public static void main(String[] args) {
        //in the browser it will look like: http://www.columbia.edu/~fdc/sample.edu
        String host = "www.columbia.edu";
        String file = "/~fdc/sample.html";
        int port = 80;

        try (Socket clientSocket = new Socket(host, port)) {
            PrintWriter out = new PrintWriter(clientSocket
                    .getOutputStream(), false);
            out.print("GET " + file + " HTTP/1.0\r\n");
            //accept any kind of text as an answer:
            out.print("Accept: text/plain, text/html, text/*\r\n");
            out.print("\r\n");
            out.flush();

            InputStreamReader inr = new InputStreamReader(clientSocket.getInputStream());
            BufferedReader br = new BufferedReader(inr);
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (UnknownHostException e) {
            System.out.println("Could not find host: " + host);
        } catch (IOException e) {
            System.out.println("No server on port" + port + ".");
        }
    }
}

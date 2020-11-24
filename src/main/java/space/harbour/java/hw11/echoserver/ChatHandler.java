package space.harbour.java.hw11.echoserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;


public class ChatHandler extends Thread {
    private Socket client;

    public ChatHandler(Socket client) throws IOException {
        this.client = client;
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(
                      new InputStreamReader(client.getInputStream()));
                //PrintWriter to reply back to the client
                PrintWriter out = new PrintWriter(new OutputStreamWriter(
                        client.getOutputStream()));) {
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
            client.close();
            //in and out will be closed automatically because they are in the try block

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

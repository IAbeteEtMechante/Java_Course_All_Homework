package space.harbour.java.hw11.servermongo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.function.Function;

import com.mongodb.BasicDBObject;
import org.bson.Document;


public class SimpleChatHandler extends Thread {
    private Socket client;
    private SimpleChatServer server;
    private PrintWriter out;

    public SimpleChatHandler(SimpleChatServer server, Socket client) throws IOException {
        this.client = client;
        this.server = server;
    }

    @Override
    public void run() {
        try {
            out = new PrintWriter(new OutputStreamWriter(
                    client.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(client.getInputStream()))) {
            String name = in.readLine();
            setName(name);
            server.broadcast("Welcome " + name + " to the chat!");
            MongoExecutor executor = new MongoExecutor();
            BasicDBObject searchQuery = new BasicDBObject();
            searchQuery.put("type", "romance");
            Function<Document, String> handler = document -> String.valueOf(document);
            String result = (String) executor.execFindOne("chatHistory", searchQuery, handler);
            System.out.println(result);
            while (true) {
                String str = in.readLine();
                if (str == null) { //client closed connection
                    break; //break the cycle to get another client
                }
                server.broadcast(getName() + " says: " + str);
                MongoExecutor executor = new MongoExecutor();
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                LocalDateTime now = LocalDateTime.now();
                Document messageDocument = new Document("name", name)
                        .append("message", str)
                        .append("time", String.valueOf(dtf.format(now)));
                executor.execStoreMovie(messageDocument);
                if (str.trim().equals("BYE")) {
                    break; //break the cycle to get another client
                }

            }
            server.broadcast("User " + name + " left the chat.");
            out.close();
            client.close();

            //in and out will be closed automatically because they are in the try block

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message) {

        out.println(message);
        out.flush();

    }
}

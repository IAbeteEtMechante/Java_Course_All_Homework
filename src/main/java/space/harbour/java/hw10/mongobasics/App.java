package space.harbour.java.hw10.mongobasics;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import com.mongodb.client.model.ReturnDocument;
import com.mongodb.client.model.Updates;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bson.Document;
import org.bson.conversions.Bson;

/*
Based on the tutorial:
https://www.youtube.com/watch?v=bkhXHiracs8

It is an easy explanation of the series of blog posts:

https://www.mongodb.com/blog/post/
quick-start-java-and-mongodb--starting-and-setup?
utm_campaign=javaquickstart&utm_source
=twitter&utm_medium=organic_social

 */


public class App {
    public static void main(String[] args) {
        Logger.getLogger("org.mongodb.driver").setLevel(Level.WARNING);
        String connectionString = "mongodb+srv://twitch:supersafe@twitchcluster."
                + "nyccz.mongodb.net/test?retryWrites=true&w=majority";
        try (MongoClient mongoClient = MongoClients.create(connectionString)) {
            //note that the following code creates the database and collection
            // automatically if they dont exist
            MongoCollection<Document> cookies = mongoClient
                    .getDatabase("xmas").getCollection("cookies");

            printDatabases(mongoClient);
            //lessGoodWayToPrintDatabases(mongoClient);
            deleteDocuments(cookies);
            createDocuments(cookies);
            updateDocuments(cookies);
            findDocuments(cookies);
        }
    }

    private static void findDocuments(MongoCollection<Document> cookies) {
        System.out.println("Find Operations");
        ArrayList<Document> lowCaloriesCookies =
                cookies.find(Filters.lte("calories", 500)).into(new ArrayList<>());
        lowCaloriesCookies.forEach(d -> System.out.println(d.toJson()));
    }

    private static void updateDocuments(MongoCollection<Document> cookies) {
        System.out.println("Update Operations");
        Random random = new Random();
        //cookies.updateMany(new Document(), Updates.set("calories", random.nextInt(1000)));

        List<Document> cookiesList = cookies.find().into(new ArrayList<>());
        cookiesList.forEach(c -> {
            Object id = c.get("_id");
            Document filter = new Document("_id", id);
            Bson update = Updates.set("calories", random.nextInt(1000));
            FindOneAndUpdateOptions findOneAndUpdateOptions = new FindOneAndUpdateOptions()
                    .returnDocument(ReturnDocument.AFTER);
            Document cookie = cookies.findOneAndUpdate(filter, update, findOneAndUpdateOptions);
            System.out.println(cookie.toJson());
        });
    }

    private static void createDocuments(MongoCollection<Document> cookies) {

        List<Document> cookiesList = new ArrayList<>();

        //List<String> ingredients = new ArrayList<>();
        //ingredients.add("flour");
        List<String> ingredients = List.of("flour", "eggs", "butter", "sugar", "red food coloring");

        for (int i = 0; i < 10; i++) {
            cookiesList.add(new Document("cookie_id", i)
                    .append("color", "pink").append("ingredients", ingredients));
        }
        cookies.insertMany(cookiesList);
    }

    private static void deleteDocuments(MongoCollection<Document> cookies) {
        cookies.deleteMany(new Document());
    }

    private static void createOneDocument(MongoClient mongoClient) {

        //note that the following code creates the database and collection
        // automatically if they dont exist
        MongoCollection<Document> cookies = mongoClient
                .getDatabase("xmas").getCollection("cookies");

        ////empty the collection first
        //cookies.deleteMany(new Document());

        Document doc = new Document("name", "chocolate chips");
        cookies.insertOne(doc);
    }

    //private static void lessGoodWayToPrintDatabases(MongoClient mongoClient) {
    //    MongoIterable<String> strings = mongoClient.listDatabaseNames();
    //    MongoCursor<String> cursor = strings.cursor();
    //    while (cursor.hasNext()) {
    //        System.out.println(cursor.next());
    //    }
    //}

    private static void printDatabases(MongoClient mongoClient) {
        List<Document> dbDocuments = mongoClient.listDatabases().into(new ArrayList<>());
        dbDocuments.forEach(document -> System.out.println(document.toJson()));
    }
}

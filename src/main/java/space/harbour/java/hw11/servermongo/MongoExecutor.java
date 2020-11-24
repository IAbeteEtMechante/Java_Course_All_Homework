package space.harbour.java.hw11.servermongo;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.util.Iterator;
import java.util.function.Function;
import org.bson.Document;

public class MongoExecutor {
    MongoClient mongoClient;
    MongoDatabase database;
    MongoClientURI uri;

    public MongoExecutor() {
        uri = new MongoClientURI(
                "mongodb+srv://Pierre:IiHM4lPwBlQLSkcD@cluster0.xc0or.mongodb.net/"
                        + "movies?retryWrites=true&w=majority");

        mongoClient = new MongoClient(uri);
        database = mongoClient.getDatabase("chatroom");
    }

    public <T> T execFindOne(String collection,
                             BasicDBObject searchQuery,
                             Function<Document, T> handler) {
        MongoCollection<Document> mongoCollection = database.getCollection(collection);
        FindIterable<Document> result = mongoCollection.find(searchQuery);
        return handler.apply(result.first());
    }

    public void displayAll(String collection) {
        //Creating a collection object
        MongoCollection<Document> mongoCollection = database.getCollection(collection);
        //Retrieving the documents
        FindIterable<Document> iterDoc = mongoCollection.find();
        Iterator it = iterDoc.iterator();
        while (it.hasNext()) {
            System.out.println(it.next());
        }

    }

    public <T> T execGetAll(String collection,
                             BasicDBObject searchQuery,
                             Function<Document, T> handler) {
        MongoCollection<Document> mongoCollection = database.getCollection(collection);
        FindIterable<Document> result = mongoCollection.find(searchQuery);
        return handler.apply(result.first());
    }

    public void execStoreMovie(Document document) {
        MongoCollection<Document> mongoCollection = database.getCollection("chatHistory");
        mongoCollection.insertOne(document);
    }

    public void execDeleteMovie(Document document) {
        MongoCollection<Document> mongoCollection = database.getCollection("chatHistory");
        mongoCollection.deleteOne(document);
    }

    public void deleteChatHistory() {
        MongoCollection<Document> mongoCollection = database.getCollection("chatHistory");
        mongoCollection.deleteMany(new Document());
    }

}

package space.harbour.java.hw10;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
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
        database = mongoClient.getDatabase("movies");
    }

    public <T> T execFindOne(String collection,
                             BasicDBObject searchQuery,
                             Function<Document, T> handler) {
        MongoCollection<Document> mongoCollection = database.getCollection(collection);
        FindIterable<Document> result = mongoCollection.find(searchQuery);
        return handler.apply(result.first());
    }

    public void execStoreMovie(Document document) {
        MongoCollection<Document> mongoCollection = database.getCollection("movies");
        mongoCollection.insertOne(document);
    }

    public void execDeleteMovie(Document document) {
        MongoCollection<Document> mongoCollection = database.getCollection("movies");
        mongoCollection.deleteOne(document);
    }
}

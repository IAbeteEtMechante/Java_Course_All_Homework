package space.harbour.java.hw10;

import com.mongodb.BasicDBObject;
import java.util.function.Function;
import org.bson.Document;

public class MongoMovies {
    public static BasicDBObject searchQuery = new BasicDBObject();
    public static Function<Document, String> handler;

    public static void main(String[] args) {

        MongoExecutor executor = new MongoExecutor();
        Document titanic = new Document("title", "Titanic")
                .append("year", "1997")
                .append("runtime", "195")
                .append("type", "romance")
                .append("director", "James Cameron")
                .append("actors", "Leonardo di Caprio")
                .append("ratings", "7.8");
        executor.execStoreMovie(titanic);

        searchQuery.put("type", "romance");
        Function<Document, String> handler = document -> String.valueOf(document);
        String result = (String) executor.execFindOne("movies", searchQuery, handler);
        System.out.println(result);

        executor.execDeleteMovie(titanic);
    }
}

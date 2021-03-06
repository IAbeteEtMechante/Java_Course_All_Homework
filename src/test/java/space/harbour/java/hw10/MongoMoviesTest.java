package space.harbour.java.hw10;

import com.mongodb.BasicDBObject;
import java.util.function.Function;
import org.bson.Document;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;



public class MongoMoviesTest {

    Document titanic = new Document("title", "Titanic")
            .append("year", "1997")
            .append("runtime", "195")
            .append("type", "romance")
            .append("director", "James Cameron")
            .append("actors", "Leonardo di Caprio")
            .append("ratings", "7.8");
    MongoExecutor executor = new MongoExecutor();
    BasicDBObject searchQuery = new BasicDBObject();
    Function<Document, String> handler = str -> String.valueOf(str);

    @Before
    public void setUp() throws Exception {
        executor.execStoreMovie(titanic);
    }

    @After
    public void tearDown() throws Exception {
        executor.execDeleteMovie(titanic);
    }

    //test01: check that we can add a movie
    //@Test
    //public void addMovie() {
    //    searchQuery.put("title", "Titanic");
    //    String result = (String) executor.execFindOne("movies", searchQuery, handler);
    //    //Assert.assertTrue(result.contains("title=Titanic, year=1997, runtime=195, "
    //    //        + "type=romance, director=James Cameron,"
    //    + "actors=Leonardo di Caprio, ratings=7.8"));
    //    Assert.assertTrue(result.contains("Titanic"));
    //}
}
package space.harbour.java.hw12;

import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.put;
import static spark.Spark.staticFileLocation;

import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.Function;
import freemarker.template.Configuration;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.bson.Document;
import org.eclipse.jetty.http.HttpStatus;
import spark.ModelAndView;
import spark.Request;
import spark.template.freemarker.FreeMarkerEngine;

/**
 * A simple CRUD example showing how to create, get, update and delete movie resources.
 */

public class WebApp
{
    public WebApp() throws UnknownHostException {
    }

    public static void main(String[] args) {

        MongoExecutor executor = new MongoExecutor();


        final Gson gson = new Gson();
        final Random random = new Random();
        staticFileLocation("public");

        //Fill up the mongoDb
        //we run Movies Class one time first
        //in order to fill up the database of movies

        // Creates a new movie resource, will return the ID to the created resource
        // author and title are sent in the post body as x-www-urlencoded values
        // e.g. author=Foo&title=Bar
        // you get them by using request.queryParams("valuename")
        post("/movies", (request, response) -> {
            Movies movie = gson.fromJson(request.body(), Movies.class);
            executor.insertOneMovie(movie);
            response.status(HttpStatus.CREATED_201);
            return movie.title;
        });

        // Gets the movie resource for the provided id
        get("/movies/:title", (request, response) -> {
            String title = request.params(":title");
            Function<Document, Movies> handler = doc -> gson.fromJson(doc.toJson(), Movies.class);
            Document document = executor.findMovieFromTitle(title);
            Movies movie = handler.apply(document);

            if (movie == null) {
                response.status(HttpStatus.NOT_FOUND_404);
                return "Movie not found";
            }
            if (clientAcceptsHtml(request)) {
                Map<String, Object> movieMap = new HashMap<>();
                movieMap.put("movie", movie);
                return render(movieMap, "movie.ftl");
            } else if (clientAcceptsJson(request)) {
                return gson.toJson(movie);
            }
            return null;
        });

        // Updates the movie resource for the provided title with new information
        // author and title are sent in the request body
        // as x-www-urlencoded values e.g. author=Foo&title=Bar
        // you get them by using request.queryParams("valuename")
        put("/movies/:title", (request, response) -> {
            String title = request.params(":title");


            String newTitle = request.queryParams("title");

            Function<Document, Movies> handler = doc -> gson.fromJson(doc.toJson(), Movies.class);
            Document document = executor.findMovieFromTitle(title);
            Movies movie = handler.apply(document);

            if (movie == null) {
                response.status(HttpStatus.NOT_FOUND_404);
                return "Movie not found";
            }
            if (newTitle != null) {

                //executor.updateMovie(title);
            }
            return "Movie with title '" + title + "' updated";
        });

        //delete the movie for the provided title
        delete("/movies/:title", (request, response) -> {
            String title = request.params(":title");
            Function<Document, Movies> handler = doc -> gson.fromJson(doc.toJson(), Movies.class);
            Document movieDocument = executor.findMovieFromTitle(title);
            if (movieDocument == null) {
                response.status(HttpStatus.NOT_FOUND_404);
                return "Movie not found";
            }
            executor.removeMovie(title);
            return "Movie with title '" + title + "' deleted";
        });

        //get all available movies resources
        get("/movies", (request, response) -> {
            Function<List<Movies>, List<Movies>> handler = x -> x;
            List<Movies> movies = (List<Movies>) executor.findAllMovies();
            if (clientAcceptsHtml(request)) {
                Map<String, Object> moviesMap = new HashMap<>();
                moviesMap.put("movies", movies);
                System.out.println(moviesMap);
                return render(moviesMap, "movies.ftl");
            } else if (clientAcceptsJson(request)) {
                return gson.toJson(movies);
            }

            return null;
        });
    }


    public static String render(Map values, String template) {
        FreeMarkerEngine freeMarkerEngine = new FreeMarkerEngine();
        Configuration configuration = new Configuration();
        configuration.setClassForTemplateLoading(WebApp.class, "/spark.template.freemarker/");
        freeMarkerEngine.setConfiguration(configuration);

        return freeMarkerEngine.render(new ModelAndView(values, template));
    }

    public static boolean clientAcceptsHtml(Request request) {
        String accept = request.headers("Accept");
        return accept != null && accept.contains("text/html");
    }

    public static boolean clientAcceptsJson(Request request) {
        String accept = request.headers("Accept");
        return accept != null && (accept.contains("application/json") || accept.contains("*/*"));
    }

}

package space.harbour.java.hw12;

import com.google.gson.Gson;
import org.eclipse.jetty.http.HttpStatus;
import spark.ModelAndView;
import spark.Request;
import spark.template.freemarker.FreeMarkerEngine;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static spark.Spark.*;

/**
 * A simple CRUD example showing how to create, get, update and delete movies resources.
 */
public class MoviesWebApp {

    /**
     * Map holding the movies.
     */
    private static Map<Integer, Movie> movies = new HashMap<>();

    public static void main(String[] args) {
        // Let's add some movies to the HashMap
        // You should read them from the MongoDB
        movies.put(1, new Movie("The Little Prince", "Antouan De Saint Exupery", 100));
        movies.put(2, new Movie("The Oxford Dictionary", "The British Crown", 2000));
        movies.put(3, new Movie("Intro to Java + OOP", "Harbour.Space", 15));

        final Gson gson = new Gson();
        final Random random = new Random();

        staticFileLocation("public");

        // Creates a new movie resource, will return the ID to the created resource
        // author and title are sent in the post body as x-www-urlencoded values
        // e.g. author=Foo&title=Bar
        // you get them by using request.queryParams("valuename")
        post("/movies", (request, response) -> {
            String author = request.queryParams("author");
            String title = request.queryParams("title");
            Integer pages = Integer.valueOf(request.queryParams("pages"));
            Movie movie = new Movie(author, title, pages);

            int id = random.nextInt(Integer.MAX_VALUE);
            movies.put(id, movie);

            response.status(HttpStatus.CREATED_201);
            return id;
        });

        // Gets the movie resource for the provided id
        get("/movies/:id", (request, response) -> {
            Integer id = Integer.valueOf(request.params(":id"));
            Movie movie = movies.get(id);
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

        // Updates the movie resource for the provided id with new information
        // author and title are sent in the request body
        // as x-www-urlencoded values e.g. author=Foo&title=Bar
        // you get them by using request.queryParams("valuename")
        put("/movies/:id", (request, response) -> {
            Integer id = Integer.valueOf(request.params(":id"));
            Movie movie = movies.get(id);
            if (movie == null) {
                response.status(HttpStatus.NOT_FOUND_404);
                return "Movie not found";
            }
            String newAuthor = request.queryParams("author");
            String newTitle = request.queryParams("title");
            String newPages = request.queryParams("pages");
            if (newAuthor != null) {
                movie.setAuthor(newAuthor);
            }
            if (newTitle != null) {
                movie.setTitle(newTitle);
            }
            if (newPages != null) {
                movie.setPages(Integer.valueOf(newPages));
            }
            return "Movie with id '" + id + "' updated";
        });

        // Deletes the movie resource for the provided id
        delete("/movies/:id", (request, response) -> {
            Integer id = Integer.valueOf(request.params(":id"));
            Movie movie = movies.remove(id);
            if (movie == null) {
                response.status(HttpStatus.NOT_FOUND_404);
                return "Movie not found";
            }
            return "Movie with id '" + id + "' deleted";
        });

        // Gets all available movie resources
        get("/movies", (request, response) -> {
            if (clientAcceptsHtml(request)) {
                Map<String, Object> moviesMap = new HashMap<>();
                moviesMap.put("movies", movies);
                return render(moviesMap, "movies.ftl");
            } else if (clientAcceptsJson(request)) {
                return gson.toJson(movies);
            }

            return null;
        });
    }

    public static String render(Map values, String template) {
        return new FreeMarkerEngine().render(new ModelAndView(values, template));
    }

    public static boolean clientAcceptsHtml(Request request) {
        String accept = request.headers("Accept");
        return accept != null && accept.contains("text/html");
    }

    public static boolean clientAcceptsJson(Request request) {
        String accept = request.headers("Accept");
        return accept != null && accept.contains("application/json");
    }

    public static class Movie {

        public String author;
        public String title;
        public Integer pages;

        public Movie(String author, String title, Integer pages) {
            this.author = author;
            this.title = title;
            this.pages = pages;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public Integer getPages() {
            return pages;
        }

        public void setPages(Integer pages) {
            this.pages = pages;
        }
    }
}
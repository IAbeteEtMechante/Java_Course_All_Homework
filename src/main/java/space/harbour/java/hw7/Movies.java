package space.harbour.java.hw7;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
// import java.lang.ClassNotFoundException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonWriter;


public class Movies {
    String title;
    Integer year;
    String released;
    Integer runtime;
    String[] genres;
    String director;
    String[] writers;
    String[] actors;
    String plot;
    String[] languages;
    String[] countries;
    String awards;
    String poster;
    double ratings;

    public Movies(String title,
                  int year,
                  int runtime,
                  String[] genres,
                  String director,
                  String[] actors,
                  double ratings) {
        this.title = title;
        this.year = year;
        this.runtime = runtime;
        this.genres = genres;
        this.director = director;
        this.actors = actors;
        this.ratings = ratings;
    }



    public static void main(String[] args) throws Exception {

        //Create some sample movies with the fields we need

        Movies titanic = new Movies("Titanic", 1997,
                195, new String[]{"romance", "documentary"}, "James Cameron",
                new String[]{"Leonardo Di Caprio", "Kate Winsley"}, 7.8);

        Movies fightClub = new Movies("Fight Club", 1999,
                185, new String[]{"psychological thriller", "action", "satire"}, "David Fincher",
                new String[]{"Brad Pitt", "Edward Norton", "Helena Bonham"}, 8.8);

        Movies pulpFiction = new Movies("Pulp Fiction", 1995,
                154, new String[]{"adventure", "action", "comedy"}, "Quentin Tarantino",
                new String[]{"John Travolta", "Samuel L. Jackson", "Uma Thurman"}, 9.2);

        Movies ingloriousBasterds = new Movies("Inglorious Basterds", 2009,
                153, new String[]{"adventure", "documentary", "comedy", "action"},
                "Quentin Tarantino", new String[]{"Brad Pitt", "Christopher Waltz"}, 8.1);

        //put those movies in a list
        ArrayList<Movies> myMovies = new ArrayList<>();
        myMovies.add(pulpFiction);
        myMovies.add(titanic);
        myMovies.add(fightClub);
        myMovies.add(ingloriousBasterds);

        //sort by release year
        System.out.println("\nSort movies by release year:");
        myMovies.stream()
                .sorted((movie1, movie2) -> movie1.year.compareTo(movie2.year))
                .map(movie -> "Released in " + movie.year + ": " + movie.title)
                .forEach(System.out::println);

        //sort by length
        System.out.println("\nSort movies by length:");
        myMovies.stream()
                .sorted((movie1, movie2) -> movie1.runtime.compareTo(movie2.runtime))
                .map(movie -> movie.runtime + " min, " + movie.title)
                .forEach(System.out::println);

        //sort by ratings
        System.out.println("\nSort movies by ratings:");
        myMovies.stream()
                .sorted((movie1, movie2) -> Double.compare(movie1.ratings, movie2.ratings))
                .map(movie -> "Rating of: " + movie.ratings + " ---->" + movie.title)
                .forEach(System.out::println);

        //filter by director
        System.out.println("\nFilter movies directed by Quentin Tarentino:");
        myMovies.stream()
                .filter(x -> x.director.equals("Quentin Tarantino"))
                .map(movie -> movie.title)
                .forEach(System.out::println);

        //filter by actor
        System.out.println("\nFilter movies with Brad Pitt:");
        myMovies.stream()
                .filter(movies -> Arrays.asList(movies.actors).contains("Brad Pitt"))
                .map(movie -> movie.title)
                .forEach(System.out::println);

        //filter by genre
        System.out.println("\nFilter action movies:");
        myMovies.stream()
                .filter(movies -> Arrays.asList(movies.genres).contains("action"))
                .map(movie -> movie.title)
                .forEach(System.out::println);

    }

}

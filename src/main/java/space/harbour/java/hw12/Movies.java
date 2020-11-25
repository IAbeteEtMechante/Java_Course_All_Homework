package space.harbour.java.hw12;


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
    String linkImage;

    public Movies(String title,
                  int year,
                  int runtime,
                  String[] genres,
                  String director,
                  String[] actors,
                  double ratings,
                  String linkImage) {
        this.title = title;
        this.year = year;
        this.runtime = runtime;
        this.genres = genres;
        this.director = director;
        this.actors = actors;
        this.ratings = ratings;
        this.linkImage = linkImage;
    }



    public static void main(String[] args) throws Exception {

        //Create some sample movies with the fields we need

        Movies titanic = new Movies("Titanic", 1997,
                195, new String[]{"romance", "documentary"}, "James Cameron",
                new String[]{"Leonardo Di Caprio", "Kate Winsley"}, 7.8,
                "https://en.wikipedia.org/wiki/"
                        + "Titanic_(1997_film)#/media/File:Titanic_(Official_Film_Poster).png");

        Movies fightClub = new Movies("Fight Club", 1999,
                185, new String[]{"psychological thriller", "action", "satire"}, "David Fincher",
                new String[]{"Brad Pitt", "Edward Norton", "Helena Bonham"}, 8.8,
                "https://thestandard.co/wp-content/uploads/2019/09/Fight-Club-768x402.jpg");

        Movies pulpFiction = new Movies("Pulp Fiction", 1995,
                154, new String[]{"adventure", "action", "comedy"}, "Quentin Tarantino",
                new String[]{"John Travolta", "Samuel L. Jackson", "Uma Thurman"}, 9.2,
                "https://media.vanityfair.com/photos/5420615f1019a3955fea4051/1:1/w_800%2Cc_limit/image.png");

        Movies ingloriousBasterds = new Movies("Inglorious Basterds", 2009,
                153, new String[]{"adventure", "documentary", "comedy", "action"},
                "Quentin Tarantino", new String[]{"Brad Pitt", "Christopher Waltz"}, 8.1,
                "https://www.imdb.com/title/tt0361748/mediaviewer/rm3163035648/");

        //put those movies in a list
        ArrayList<Movies> myMovies = new ArrayList<>();
        myMovies.add(pulpFiction);
        myMovies.add(titanic);
        myMovies.add(fightClub);
        myMovies.add(ingloriousBasterds);

        MongoExecutor executor = new MongoExecutor();

        for (Movies movie : myMovies) {
            executor.insertOneMovie(movie);
        }


    }


}

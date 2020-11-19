package space.harbour.java.hw4;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
// import java.lang.ClassNotFoundException;
import java.io.StringReader;
import java.util.Arrays;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonWriter;


public class Movies implements Jsonable {
    String title;
    Integer year;
    String released;
    Integer runtime;
    String[] genres;
    Director director;
    Writer[] writers;
    Actor[] actors;
    String plot;
    String[] languages;
    String[] countries;
    String awards;
    String poster;
    Rating[] ratings;


    public static class Director implements Jsonable {
        String name;

        @Override
        public String toString() {
            return "Director{"
                    + "name='" + name + '\''
                    + '}';
        }

        @Override
        public JsonObject toJsonObject() {
            return Json.createObjectBuilder()
                    .add("Name", name)
                    .build();
        }

        @Override
        public String toJsonString() {
            return this.toJsonObject().toString();
        }

        

    }

    public static class Writer implements Jsonable {
        String name;
        String type;

        @Override
        public String toString() {
            return "Writer{"
                    + "name='" + name + '\''
                    + ", type=" + type
                    + '}';
        }

        @Override
        public JsonObject toJsonObject() {
            return Json.createObjectBuilder()
                    .add("Name", name)
                    .add("Type", type)
                    .build();
        }

        @Override
        public String toJsonString() {
            return this.toJsonObject().toString();
        }
    }

    public static class Actor implements Jsonable {
        String name;
        String as;

        @Override
        public String toString() {
            return "Actor{"
                    + "name='" + name + '\''
                    + ", as='" + as + '\''
                    + '}';
        }

        @Override
        public JsonObject toJsonObject() {
            return Json.createObjectBuilder()
                    .add("Name", name)
                    .add("As", as)
                    .build();
        }

        @Override
        public String toJsonString() {
            return this.toJsonObject().toString();
        }
    }

    public static class Rating implements Jsonable {
        String source;
        String value;
        int votes;

        @Override
        public String toString() {
            return "Rating{"
                    + "source='" + source + '\''
                    + ", value='" + value + '\''
                    + ", votes='" + votes + '\''
                    + '}';
        }

        @Override
        public JsonObject toJsonObject() {
            return Json.createObjectBuilder()
                    .add("Source", source)
                    .add("Value", value)
                    .add("Votes", votes)
                    .build();
        }

        @Override
        public String toJsonString() {
            return this.toJsonObject().toString();
        }
    }

    @Override
    public JsonObject toJsonObject() {
        JsonArrayBuilder genresBuilder = Json.createArrayBuilder();
        for (String genre : genres) {
            genresBuilder.add(genre.replace("\"", ""));
        }

        JsonArrayBuilder writersBuilder = Json.createArrayBuilder();
        for (Writer writer : writers) {
            writersBuilder.add(writer.toJsonObject());
        }

        JsonArrayBuilder actorsBuilder = Json.createArrayBuilder();
        for (Actor actor : actors) {
            actorsBuilder.add(actor.toJsonObject());
        }

        JsonArrayBuilder languagesBuilder = Json.createArrayBuilder();
        for (String language : languages) {
            languagesBuilder.add(language.replace("\"", ""));
        }

        JsonArrayBuilder countriesBuilder = Json.createArrayBuilder();
        for (String country : countries) {
            countriesBuilder.add(country.replace("\"", ""));
        }

        JsonArrayBuilder ratingsBuilder = Json.createArrayBuilder();
        for (Rating rating : ratings) {
            ratingsBuilder.add(rating.toJsonObject());
        }
        return Json.createObjectBuilder()
                .add("Title", title)
                .add("Year", year)
                .add("Released", released)
                .add("Runtime", runtime)
                .add("Genres", genresBuilder.build())
                .add("Director", director.toJsonObject())
                .add("Writers", writersBuilder.build())
                .add("Actors", actorsBuilder.build())
                .add("Plot", plot)
                .add("Languages", languagesBuilder.build())
                .add("Countries", countriesBuilder.build())
                .add("Awards", awards)
                .add("Poster", poster)
                .add("Ratings", ratingsBuilder.build())
                .build();
    }

    @Override
    public String toJsonString() {
        return this.toJsonObject().toString();
    }

    public static void writeJsonToFile(JsonObject jsonObj, String fileName) throws IOException {
        fileName = "./src/main/java/space/harbour/java/hw4/" + fileName;
        FileOutputStream fout = new FileOutputStream(fileName);
        // BufferedOutputStream bout = new BufferedOutputStream(fout);
        JsonWriter jout = Json.createWriter(fout);
        jout.writeObject(jsonObj);
        jout.close();
        // jout.flush();

    }

    public void fromJson(JsonObject jsonObject) {
        this.title = jsonObject.getString("Title");
        this.year = jsonObject.getInt("Year");
        this.released = jsonObject.getString("Released");
        this.runtime = jsonObject.getInt("Runtime");

        JsonArray jasonArray = jsonObject.getJsonArray("Genres");
        this.genres = new String[jasonArray.size()];
        for (int i = 0; i < this.genres.length; i++) {
            this.genres[i] = jasonArray.get(i).toString();
        }

        JsonObject myJsonObject = jsonObject.getJsonObject("Director");
        this.director = new Director();
        this.director.name = myJsonObject.getString("Name");

        jasonArray = jsonObject.getJsonArray("Writers");
        this.writers = new Writer[jasonArray.size()];
        for (int i = 0; i < this.writers.length; i++) {
            this.writers[i] = new Writer();
            this.writers[i].name = jasonArray.getJsonObject(i).getString("Name");
            this.writers[i].type = jasonArray.getJsonObject(i).getString("Type");
        }

        jasonArray = jsonObject.getJsonArray("Actors");
        this.actors = new Actor[jasonArray.size()];
        for (int i = 0; i < this.actors.length; i++) {
            this.actors[i] = new Actor();
            this.actors[i].name = jasonArray.getJsonObject(i).getString("Name");
            this.actors[i].as = jasonArray.getJsonObject(i).getString("As");
        }
        this.plot = jsonObject.getString("Plot");

        jasonArray = jsonObject.getJsonArray("Languages");
        this.languages = new String[jasonArray.size()];
        for (int i = 0; i < this.languages.length; i++) {
            this.languages[i] = jasonArray.get(i).toString();
        }

        jasonArray = jsonObject.getJsonArray("Countries");
        this.countries = new String[jasonArray.size()];
        for (int i = 0; i < this.countries.length; i++) {
            this.countries[i] = jasonArray.get(i).toString();
        }

        this.awards = jsonObject.getString("Awards");
        this.poster = jsonObject.getString("Poster");

        jasonArray = jsonObject.getJsonArray("Ratings");
        this.ratings = new Rating[jasonArray.size()];
        for (int i = 0; i < this.ratings.length; i++) {
            this.ratings[i] = new Rating();
            this.ratings[i].source = jasonArray.getJsonObject(i).getString("Source");
            this.ratings[i].value = jasonArray.getJsonObject(i).getString("Value");
            this.ratings[i].votes = jasonArray.getJsonObject(i).getInt("Votes", 0);
        }


    }

    @Override
    public String toString() {
        return "Movie{"
                + "title='" + title + '\''
                + ", year='" + year + '\''
                + ", released='" + released + '\''
                + ", runtime='" + runtime + '\''
                + ", genres=" + Arrays.toString(genres)
                + ", director=" + director.toString()
                + ", writers=" + Arrays.toString(writers)
                + ", actors=" + Arrays.toString(actors)
                + ", plot='" + plot + '\''
                + ", languages=" + Arrays.toString(languages)
                + ", countries=" + Arrays.toString(countries)
                + ", awards='" + awards + '\''
                + ", poster='" + poster + '\''
                + ", ratings=" + Arrays.toString(ratings)
                + '}';
    }




    public static JsonObject readJsonFromFile(String fileName) 
                            throws IOException, ClassNotFoundException {
        fileName = "./src/main/java/space/harbour/java/hw4/" + fileName;
        FileInputStream fin = new FileInputStream(fileName);
        BufferedInputStream bin = new BufferedInputStream(fin);
        JsonReader jin = Json.createReader(bin);
        return jin.readObject();
    }

    public static void main(String[] args) throws Exception {


        //We deserialize the inital file and print the resulting Movies object as a String
        JsonObject jsonObject
                = readJsonFromFile("BladeRunner.json");
        Movies movieFromFile = new Movies();
        movieFromFile.fromJson(jsonObject);
        System.out.println(movieFromFile.toString());

        //We serialize the resulting object into a new file
        writeJsonToFile(movieFromFile.toJsonObject(), "MovieInfo.json");

        //We deserialize that new file into a Movies object again
        // to check that we still get the same as before
        jsonObject
                = readJsonFromFile("MovieInfo.json");
        movieFromFile.fromJson(jsonObject);
        System.out.println(movieFromFile.toString());

    }

}

package space.harbour.java.hw7;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.Before;
import org.junit.Test;

public class MoviesTest {

    ArrayList<Movies> myMovies = new ArrayList<>();

    @Before
    public void createListOfMovies() {
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
        myMovies.add(titanic);
        myMovies.add(fightClub);
        myMovies.add(pulpFiction);
        myMovies.add(ingloriousBasterds);
    }

    //test the correct ordering of year
    @Test
    public void testYearSort() {

        String oldestMovie =
                String.valueOf(myMovies.stream()
                        .sorted((movie1, movie2) -> movie1.year.compareTo(movie2.year))
                        .map(x -> x.title)
                        .findFirst()
                        .get());

        assertEquals(oldestMovie, "Pulp Fiction");

        String latestMovie =
                String.valueOf(myMovies.stream()
                        .sorted((movie1, movie2) -> movie1.year.compareTo(movie2.year))
                        .map(x -> x.title)
                        .reduce((first, second) -> second)
                        .get());

        assertEquals(latestMovie, "Inglorious Basterds");
    }

    //test the correct ordering of length
    @Test
    public void testLengthSort() {

        String shortestMovie =
                String.valueOf(myMovies.stream()
                        .sorted((movie1, movie2) -> movie1.runtime.compareTo(movie2.runtime))
                        .map(x -> x.title)
                        .findFirst()
                        .get());

        assertEquals(shortestMovie, "Inglorious Basterds");

        String longuestMovie =
                String.valueOf(myMovies.stream()
                        .sorted((movie1, movie2) -> movie1.runtime.compareTo(movie2.runtime))
                        .map(x -> x.title)
                        .reduce((first, second) -> second)
                        .get());

        assertEquals(longuestMovie, "Titanic");
    }

    //test the correct ordering of ratings
    @Test
    public void testRatingsSort() {

        String worstMovie =
                String.valueOf(myMovies.stream()
                        .sorted((movie1, movie2) -> Double.compare(movie1.ratings, movie2.ratings))
                        .map(x -> x.title)
                        .findFirst()
                        .get());

        assertEquals(worstMovie, "Titanic");

        String bestMovie =
                String.valueOf(myMovies.stream()
                        .sorted((movie1, movie2) -> Double.compare(movie1.ratings, movie2.ratings))
                        .map(x -> x.title)
                        .reduce((first, second) -> second)
                        .get());

        assertEquals(bestMovie, "Pulp Fiction");
    }

    //test the director filter
    @Test
    public void testDirectorFilter() {
        List<Movies> moviesFromTarentino =
                myMovies.stream()
                        .filter(x -> x.director.equals("Quentin Tarantino"))
                        .collect(Collectors.toList());
        assertTrue(moviesFromTarentino.contains(myMovies.get(3)));
        assertTrue(moviesFromTarentino.contains(myMovies.get(2)));
    }

    //test the actor filter
    @Test
    public void testActorFilter() {
        List<Movies> moviesWithBradPitt =
                myMovies.stream()
                        .filter(movies -> Arrays.asList(movies.actors).contains("Brad Pitt"))
                        .collect(Collectors.toList());
        assertTrue(moviesWithBradPitt.contains(myMovies.get(1)));
        assertTrue(moviesWithBradPitt.contains(myMovies.get(3)));
    }

    //test the genre filter
    @Test
    public void testGenreFilter() {
        List<Movies> moviesWithAction =
                myMovies.stream()
                        .filter(movies -> Arrays.asList(movies.genres).contains("action"))
                        .collect(Collectors.toList());
        assertTrue(moviesWithAction.contains(myMovies.get(1)));
        assertTrue(moviesWithAction.contains(myMovies.get(2)));
        assertTrue(moviesWithAction.contains(myMovies.get(3)));
    }
}

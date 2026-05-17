package library.drome.data;

import library.drome.models.Availability;
import library.drome.models.FilmList;
import library.drome.models.Movie;
import library.drome.models.User;

import java.util.List;
import java.util.Objects;

public class TestDataHelper {
    public static User existingUser() {
        return new User(1, "a@a.com", "a");
    }

    public static User existingUserFromDatabase() {
        return new User(1, "a@a.com", "128");
    }

    public static User userToCreate() {
        return new User(0, "c@c.com", "c");
    }

    public static User userAfterCreate() {
        User user = userToCreate();
        user.setUserId(3);
        user.setPassword(String.valueOf(Objects.hash(user.getPassword())));
        return user;
    }

    public static FilmList existingList() {
        return new FilmList(1, "list 1", 1);
    }

    public static FilmList existingListWithMoviesAttached() {
        FilmList list = new FilmList(1, "list 1", 1);
        list.setMovies(list1());
        return list;
    }

    public static FilmList listToCreate() {
        return new FilmList(0, "list 3", 1);
    }

    public static FilmList listAfterCreate() {
        FilmList list = listToCreate();
        list.setListId(3);
        return list;
    }

    public static Movie theDoomGeneration() {
        return new Movie(1, "The Doom Generation", 1995, Availability.NONE, "https://media.themoviedb.org/t/p/w220_and_h330_face/cRzRj2UBvIH8ryWhu5PNL2PzV7j.jpg");
    }

    public static Movie movieToCreate() {
        return new Movie(0, "Thelma & Louise", 1991, Availability.BOTH, "https://media.themoviedb.org/t/p/w220_and_h330_face/gQSUVGR80RVHxJywtwXm2qa1ebi.jpg");
    }

    public static Movie movieAfterCreate() {
        Movie movie = movieToCreate();
        movie.setMovieId(5);
        return movie;
    }

    public static List<Movie> list1() {
        return List.of(
                theDoomGeneration(),
                new Movie(2, "Lady Bird", 2017, Availability.BOTH, "https://media.themoviedb.org/t/p/w220_and_h330_face/gl66K7zRdtNYGrxyS2YDUP5ASZd.jpg"),
                new Movie(3, "Bones and All", 2022, Availability.BOTH, "https://media.themoviedb.org/t/p/w220_and_h330_face/ayfr4iL0jVV9mquN7SKvjOidvRH.jpg"),
                new Movie(4, "Women on the Verge of a Nervous Breakdown", 1988, Availability.DVD, "https://media.themoviedb.org/t/p/w220_and_h330_face/8C5FJlUo96pj1xAs2BKnB58PYzi.jpg")
        );
    }
}

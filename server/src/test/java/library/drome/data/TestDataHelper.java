package library.drome.data;

import library.drome.models.Availability;
import library.drome.models.FilmList;
import library.drome.models.Movie;
import library.drome.models.User;

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
}

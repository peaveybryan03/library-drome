package library.drome.data;

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
}
